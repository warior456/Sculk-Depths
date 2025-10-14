package net.ugi.sculk_depths.block.custom.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.ugi.sculk_depths.block.ModBlockEntities;


import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.screen.ZygrinFurnaceScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ZygrinFurnaceBlockEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider{

    protected static final int INPUT_SLOT_INDEX = 0;
    protected static final int FUEL_SLOT_INDEX = 1;
    protected static final int OUTPUT_SLOT_INDEX = 2;
    public static final int BURN_TIME_PROPERTY_INDEX = 0;
    private static final int[] TOP_SLOTS = new int[]{0};
    private static final int[] BOTTOM_SLOTS = new int[]{2, 1};
    private static final int[] SIDE_SLOTS = new int[]{1};
    public static final int FUEL_TIME_PROPERTY_INDEX = 1;
    public static final int COOK_TIME_PROPERTY_INDEX = 2;
    public static final int COOK_TIME_TOTAL_PROPERTY_INDEX = 3;
    public static final int PROPERTY_COUNT = 4;
    public static final int DEFAULT_COOK_TIME = 200;
    public static final int field_31295 = 2;
    protected DefaultedList<ItemStack> inventory;
    int burnTime;
    int fuelTime;
    int cookTime;
    int cookTimeTotal;
    @Nullable
    private static volatile Map<Item, Integer> fuelTimes;
    protected final PropertyDelegate propertyDelegate;
    private final Object2IntOpenHashMap<Identifier> recipesUsed;
    private final RecipeManager.MatchGetter<SingleStackRecipeInput, SmeltingRecipe> matchGetter = RecipeManager.createCachedMatchGetter(RecipeType.SMELTING);

    @Override
    protected Text getContainerName() {
        return Text.translatable("block.sculk_depths.zygrin_furnace");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new ZygrinFurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    //begin custom
    private boolean isBurning() {
        if (this.burnTime > 0) return true;
        return false;
    }

    private int checkBurning(BlockPos pos){
        if (this.burnTime > 1) return 0;
        if (this.burnTime <= 1 && this.burnTime >= -1) {
            if (findKryslumFurnace(pos)) return 1;
            //possibly fastest method is BlockPos.iterateRecursively();
/*            for (BlockPos blockPos : BlockPos.iterate(pos.add(-20, -20, -20), pos.add(20, 20, 20))) {
                if (!world.getFluidState(blockPos).isIn(ModTags.Fluids.KRYSLUM)) continue;
                return 1;
            }*/
            return 2;
        } else {
            return 3;
        }
    }

    public boolean findKryslumFurnace(BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockState offsettedState = world.getBlockState(pos.offset(direction));
            if (offsettedState == ModBlocks.KRYSLUM.getDefaultState()) {
                return true;
            }
        }

        for (Direction direction : Direction.values()) {
            BlockState offsettedState = world.getBlockState(pos.offset(direction));
            BlockState stateToCheckFor = ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING, direction.getOpposite());
            if (offsettedState == stateToCheckFor && findKryslumFlowBlock(pos.offset(direction), 256)) {
                return true;
            }
        }

        return false;
    }

    public boolean findKryslumFlowBlock(BlockPos pos, int count) {
        if (count == 0) return false;

        BlockState stateAtPos = world.getBlockState(pos);
        for (Direction direction : Direction.values()) {
            BlockState offsettedState = world.getBlockState(pos.offset(direction));
            BlockState stateToCheckFor = ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING, direction.getOpposite());
            if (stateAtPos == stateToCheckFor && offsettedState == ModBlocks.KRYSLUM.getDefaultState()) {
                return true;
            }
        }

        for (Direction direction : Direction.values()) {
            BlockState stateToCheckFor = ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING, direction.getOpposite());
            BlockState stateInWorld = world.getBlockState(pos.offset(direction));
            if (stateInWorld == stateToCheckFor && findKryslumFlowBlock(pos.offset(direction), count - 1)) {
                return true;
            }
        }

        return false;
    }

    private static int getCookTime(World world, ZygrinFurnaceBlockEntity furnace) {
        SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(furnace.getStack(0));
        return furnace.matchGetter.getFirstMatch(singleStackRecipeInput, world)
                .map(recipe -> recipe.value().getCookingTime() / 4).orElse(50); //furnace speed
    }

    public static void tick(World world, BlockPos pos, BlockState state, ZygrinFurnaceBlockEntity blockEntity) {
        boolean bl4 = false;
        boolean bl = blockEntity.isBurning();
        boolean bl2 = false;
        int handleBurning = blockEntity.checkBurning(pos);
        switch (handleBurning) {
            //burning and above 0 ticks left
            case 0 -> --blockEntity.burnTime;
            //lighting or checking for fuel source
            case 1 -> blockEntity.burnTime = 50; // set not more ticks than minimum cook time (2.5 sec * 20 or 10 sec * 20)

            // todo make correlate to burnspeed and configurable
            //no fuel source and cache at -1
            case 2 -> blockEntity.burnTime = -20 + MathHelper.nextInt(Random.create(), -2, 2); //todo maybe configurable?
            //no fuel source and valid cache
            case 3 -> ++blockEntity.burnTime;
        }

        ItemStack itemStack = blockEntity.inventory.get(1);
        ItemStack itemStack2 = blockEntity.inventory.get(0);
        boolean bl3 = !blockEntity.inventory.get(0).isEmpty();
        boolean bl5 = bl4 = !itemStack.isEmpty();
        if (blockEntity.isBurning() || bl4 && bl3) {
            RecipeEntry<SmeltingRecipe> recipeEntry = bl3 ?
                    blockEntity.matchGetter.getFirstMatch(new SingleStackRecipeInput(itemStack2), world).orElse(null) : null;

            int i = blockEntity.getMaxCountPerStack();
            if (!blockEntity.isBurning() && canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, blockEntity.inventory, i)) {
                blockEntity.burnTime = blockEntity.getFuelTime(itemStack);
                blockEntity.fuelTime = blockEntity.burnTime;
                if (blockEntity.isBurning()) {
                    bl2 = true;
                    if (bl4) {
                        Item item = itemStack.getItem();
                        itemStack.decrement(1);
                        if (itemStack.isEmpty()) {
                            Item item2 = item.getRecipeRemainder();
                            blockEntity.inventory.set(1, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
                        }
                    }
                }
            }

            if (blockEntity.isBurning() && canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, blockEntity.inventory, i)) {
                ++blockEntity.cookTime;
                if (blockEntity.cookTime == blockEntity.cookTimeTotal) {
                    blockEntity.cookTime = 0;
                    blockEntity.cookTimeTotal = getCookTime(world, blockEntity);
                    if (craftRecipe(world.getRegistryManager(), recipeEntry, blockEntity.inventory, i)) {
                        blockEntity.setLastRecipe(recipeEntry);
                    }

                    bl2 = true;
                }
            } else {
                blockEntity.cookTime = 0;
            }
        } else if (!blockEntity.isBurning() && blockEntity.cookTime > 0) {
            blockEntity.cookTime = MathHelper.clamp(blockEntity.cookTime - 2, 0, blockEntity.cookTimeTotal);
        }

        if (bl != blockEntity.isBurning()) {
            bl2 = true;
            state = state.with(AbstractFurnaceBlock.LIT, blockEntity.isBurning());
            world.setBlockState(pos, state, 3);
        }

        if (bl2) {
            markDirty(world, pos, state);
        }

    }

    //end custom

    public ZygrinFurnaceBlockEntity( BlockPos pos, BlockState state) {
        super(ModBlockEntities.ZYGRIN_FURNACE_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> burnTime;
                    case 1 -> fuelTime;
                    case 2 -> cookTime;
                    case 3 -> cookTimeTotal;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> burnTime = value;
                    case 1 -> fuelTime = value;
                    case 2 -> cookTime = value;
                    case 3 -> cookTimeTotal = value;
                }

            }

            @Override
            public int size() {
                return 4;
            }
        };
        this.recipesUsed = new Object2IntOpenHashMap<>();
    }

    public static Map<Item, Integer> createFuelTimeMap() {
        if (fuelTimes != null) return fuelTimes;
        Map<Item, Integer> map2 = Maps.newLinkedHashMap();
        map2.putAll(AbstractFurnaceBlockEntity.createFuelTimeMap());
        fuelTimes = map2;
        return map2;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory, registryLookup);
        this.burnTime = nbt.getShort("BurnTime");
        this.cookTime = nbt.getShort("CookTime");
        this.cookTimeTotal = nbt.getShort("CookTimeTotal");
        this.fuelTime = this.getFuelTime(this.inventory.get(1));
        NbtCompound nbtCompound = nbt.getCompound("RecipesUsed");

        for (String string : nbtCompound.getKeys()) {
            this.recipesUsed.put(Identifier.of(string), nbtCompound.getInt(string));
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putShort("BurnTime", (short)this.burnTime);
        nbt.putShort("CookTime", (short)this.cookTime);
        nbt.putShort("CookTimeTotal", (short)this.cookTimeTotal);
        Inventories.writeNbt(nbt, this.inventory, registryLookup);
        NbtCompound nbtCompound = new NbtCompound();
        this.recipesUsed.forEach((identifier, count) -> {
            nbtCompound.putInt(identifier.toString(), count);
        });
        nbt.put("RecipesUsed", nbtCompound);
    }

    private static boolean canAcceptRecipeOutput(DynamicRegistryManager registryManager, @Nullable RecipeEntry<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (slots.get(0).isEmpty() || recipe == null) {
            return false;
        }
        ItemStack itemStack = recipe.value().getResult(registryManager);
        if (itemStack.isEmpty()) {
            return false;
        }
        ItemStack itemStack2 = slots.get(2);
        if (itemStack2.isEmpty()) {
            return true;
        } else if (!ItemStack.areItemsAndComponentsEqual(itemStack2, itemStack)) {
            return false;
        } else if (itemStack2.getCount() < count && itemStack2.getCount() < itemStack2.getMaxCount()) {
            return true;
        } else {
            return itemStack2.getCount() < itemStack.getMaxCount();
        }
    }

    private static boolean craftRecipe(DynamicRegistryManager registryManager, @Nullable RecipeEntry<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (recipe != null && canAcceptRecipeOutput(registryManager, recipe, slots, count)) {
            ItemStack itemStack = slots.get(0);
            ItemStack itemStack2 = recipe.value().getResult(registryManager);
            ItemStack itemStack3 = slots.get(2);
            if (itemStack3.isEmpty()) {
                slots.set(2, itemStack2.copy());
            } else if (ItemStack.areItemsAndComponentsEqual(itemStack3, itemStack2)) {
                itemStack3.increment(1);
            }

            if (itemStack.isOf(Blocks.WET_SPONGE.asItem()) && !slots.get(1).isEmpty() && slots.get(1).isOf(Items.BUCKET)) {
                slots.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemStack.decrement(1);
            return true;
        } else {
            return false;
        }
    }

    protected int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            Item item = fuel.getItem();
            return createFuelTimeMap().getOrDefault(item, 0);
        }
    }

    public static boolean canUseAsFuel(ItemStack stack) {
        return createFuelTimeMap().containsKey(stack.getItem());
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return BOTTOM_SLOTS;
        } else {
            return side == Direction.UP ? TOP_SLOTS : SIDE_SLOTS;
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (dir == Direction.DOWN && slot == 1) {
            return stack.isOf(Items.WATER_BUCKET) || stack.isOf(Items.BUCKET);
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = this.inventory.get(slot);
        boolean bl = !stack.isEmpty() && ItemStack.areItemsAndComponentsEqual(itemStack, stack);
        this.inventory.set(slot, stack);
        stack.capCount(this.getMaxCount(stack));
        if (slot == 0 && !bl) {
            this.cookTimeTotal = getCookTime(this.world, this);
            this.cookTime = 0;
            this.markDirty();
        }

    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot == 2) {
            return false;
        } else if (slot != 1) {
            return true;
        } else {
            ItemStack itemStack = this.inventory.get(1);
            return canUseAsFuel(stack) || stack.isOf(Items.BUCKET) && !itemStack.isOf(Items.BUCKET);
        }
    }

    @Override
    public void setLastRecipe(@Nullable RecipeEntry<?> recipe) {
        if (recipe != null) {
            Identifier identifier = recipe.id();
            this.recipesUsed.addTo(identifier, 1);
        }
    }

    @Override
    @Nullable
    public RecipeEntry<?> getLastRecipe() {
        return null;
    }

    @Override
    public void unlockLastRecipe(PlayerEntity player, List<ItemStack> ingredients) {
    }

    public void dropExperienceForRecipesUsed(ServerPlayerEntity player) {
        List<RecipeEntry<?>> list = this.getRecipesUsedAndDropExperience(player.getServerWorld(), player.getPos());
        player.unlockRecipes(list);

        for (RecipeEntry<?> recipeEntry : list) {
            if (recipeEntry != null) {
                player.onRecipeCrafted(recipeEntry, this.inventory);
            }
        }

        this.recipesUsed.clear();
    }

    public List<RecipeEntry<?>> getRecipesUsedAndDropExperience(ServerWorld world, Vec3d pos) {
        List<RecipeEntry<?>> list = Lists.newArrayList();

        for (Object2IntMap.Entry<Identifier> identifierEntry : this.recipesUsed.object2IntEntrySet()) {
            world.getRecipeManager().get(identifierEntry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                dropExperience(world, pos, identifierEntry.getIntValue(), ((AbstractCookingRecipe) recipe.value()).getExperience());
            });
        }

        return list;
    }

    private static void dropExperience(ServerWorld world, Vec3d pos, int multiplier, float experience) {
        int i = MathHelper.floor((float)multiplier * experience);
        float f = MathHelper.fractionalPart((float)multiplier * experience);
        if (f != 0.0F && Math.random() < (double)f) {
            ++i;
        }

        ExperienceOrbEntity.spawn(world, pos, i);
    }

    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {

        for (ItemStack itemStack : this.inventory) {
            finder.addInput(itemStack);
        }

    }
}
