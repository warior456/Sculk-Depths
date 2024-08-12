package net.ugi.sculk_depths.block.custom.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.SharedConstants;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.ugi.sculk_depths.block.ModBlockEntities;


import net.ugi.sculk_depths.block.ModBlocks;
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
    private final RecipeManager.MatchGetter<SingleStackRecipeInput, ? extends AbstractCookingRecipe> matchGetter;



    protected Text getContainerName() {
        return Text.translatable("block.sculk_depths.zygrin_furnace");
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
//begin custom
private boolean isBurning() {
    if(this.burnTime > 0) return true;
    return false;
}
    private int checkBurning(BlockPos pos){
        if(this.burnTime > 1) return 0;
        if(this.burnTime <= 1 && this.burnTime >= -1){
            if(findKryslumFurnace(pos)) return 1;
            //possibly fastest method is BlockPos.iterateRecursively();
/*            for (BlockPos blockPos : BlockPos.iterate(pos.add(-20, -20, -20), pos.add(20, 20, 20))) {
                if (!world.getFluidState(blockPos).isIn(ModTags.Fluids.KRYSLUM)) continue;
                return 1;
            }*/
            return 2;
        }else {
            return 3;
        }
    }

    public boolean findKryslumFurnace(BlockPos pos){
        int foundFlowBlock = 0;

        if (world.getBlockState(pos.offset(Direction.NORTH)) == ModBlocks.KRYSLUM.getDefaultState()) return true;
        if (world.getBlockState(pos.offset(Direction.EAST)) == ModBlocks.KRYSLUM.getDefaultState()) return true;
        if (world.getBlockState(pos.offset(Direction.SOUTH)) == ModBlocks.KRYSLUM.getDefaultState()) return true;
        if (world.getBlockState(pos.offset(Direction.WEST)) == ModBlocks.KRYSLUM.getDefaultState()) return true;
        if (world.getBlockState(pos.offset(Direction.UP)) == ModBlocks.KRYSLUM.getDefaultState()) return true;
        if (world.getBlockState(pos.offset(Direction.DOWN)) == ModBlocks.KRYSLUM.getDefaultState()) return true;

        if (world.getBlockState(pos.offset(Direction.NORTH)) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.SOUTH)) foundFlowBlock += findKryslumFlowBlock(pos.offset(Direction.NORTH),256);
        if (world.getBlockState(pos.offset(Direction.EAST)) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.WEST)) foundFlowBlock += findKryslumFlowBlock(pos.offset(Direction.EAST),256);
        if (world.getBlockState(pos.offset(Direction.SOUTH)) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.NORTH)) foundFlowBlock += findKryslumFlowBlock(pos.offset(Direction.SOUTH),256);
        if (world.getBlockState(pos.offset(Direction.WEST)) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.EAST)) foundFlowBlock += findKryslumFlowBlock(pos.offset(Direction.WEST),256);
        if (world.getBlockState(pos.offset(Direction.UP)) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.DOWN)) foundFlowBlock += findKryslumFlowBlock(pos.offset(Direction.UP),256);
        if (world.getBlockState(pos.offset(Direction.DOWN)) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.UP)) foundFlowBlock += findKryslumFlowBlock(pos.offset(Direction.DOWN),256);

        return foundFlowBlock > 0;
    }

    public int findKryslumFlowBlock(BlockPos pos, int count){
        if(count == 0) return 0;
        if (world.getBlockState(pos.offset(Direction.NORTH)) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.SOUTH)) return findKryslumFlowBlock(pos.offset(Direction.NORTH),count - 1);
        else if (world.getBlockState(pos.offset(Direction.EAST)) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.WEST)) return findKryslumFlowBlock(pos.offset(Direction.EAST),count - 1);
        else if (world.getBlockState(pos.offset(Direction.SOUTH)) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.NORTH)) return findKryslumFlowBlock(pos.offset(Direction.SOUTH),count - 1);
        else if (world.getBlockState(pos.offset(Direction.WEST)) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.EAST)) return findKryslumFlowBlock(pos.offset(Direction.WEST),count - 1);
        else if (world.getBlockState(pos.offset(Direction.UP)) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.DOWN)) return findKryslumFlowBlock(pos.offset(Direction.UP),count - 1);
        else if (world.getBlockState(pos.offset(Direction.DOWN)) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.UP)) return findKryslumFlowBlock(pos.offset(Direction.DOWN),count - 1);

        else if (world.getBlockState(pos) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.SOUTH) && world.getBlockState(pos.offset(Direction.NORTH)) == ModBlocks.KRYSLUM.getDefaultState()) return 1;
        else if (world.getBlockState(pos) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.WEST) && world.getBlockState(pos.offset(Direction.EAST)) == ModBlocks.KRYSLUM.getDefaultState()) return 1;
        else if (world.getBlockState(pos) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.NORTH) && world.getBlockState(pos.offset(Direction.SOUTH)) == ModBlocks.KRYSLUM.getDefaultState()) return 1;
        else if (world.getBlockState(pos) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.EAST) && world.getBlockState(pos.offset(Direction.WEST)) == ModBlocks.KRYSLUM.getDefaultState()) return 1;
        else if (world.getBlockState(pos) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.DOWN) && world.getBlockState(pos.offset(Direction.UP)) == ModBlocks.KRYSLUM.getDefaultState()) return 1;
        else if (world.getBlockState(pos) == ModBlocks.ZYGRIN_FLOWBLOCK.getDefaultState().with(Properties.FACING,Direction.SOUTH) && world.getBlockState(pos.offset(Direction.DOWN)) == ModBlocks.KRYSLUM.getDefaultState()) return 1;

        else return 0;
    }


    private static int getCookTime(World world, ZygrinFurnaceBlockEntity furnace) {
        SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(furnace.getStack(0));
        return (Integer)furnace.matchGetter.getFirstMatch(singleStackRecipeInput, world).map((recipe) -> {
            return (((AbstractCookingRecipe)recipe.value()).getCookingTime())/4;//furnace speed //todo test
        }).orElse(50);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ZygrinFurnaceBlockEntity blockEntity) {//todo
        boolean bl4 = false;
        boolean bl = blockEntity.isBurning();
        boolean bl2 = false;
        int handleBurning = blockEntity.checkBurning(pos);
        switch (handleBurning){
            case 0: //burning and above 0 ticks left
                --blockEntity.burnTime;
                break;
            case 1: //lighting or checking for fuel source
                blockEntity.burnTime = 50;// set not more ticks than minimum cook time (2.5 sec * 20 or 10 sec * 20)
                // todo make corrolate to burnspeed and configurable
                break;
            case 2: //no fuel source and cache at -1
                blockEntity.burnTime = -20 + MathHelper.nextInt(Random.create(), -2, 2); //todo maybe configurable?
                break;
            case 3: //no fuel source and valid cache
                ++blockEntity.burnTime;
                break;
        }

        ItemStack itemStack = blockEntity.inventory.get(1);
        ItemStack itemStack2 = blockEntity.inventory.get(0);
        boolean bl3 = !blockEntity.inventory.get(0).isEmpty();
        boolean bl5 = bl4 = !itemStack.isEmpty();
        if (blockEntity.isBurning() || bl4 && bl3) {
            RecipeEntry recipeEntry;
            if (bl3) {
                recipeEntry = (RecipeEntry)blockEntity.matchGetter.getFirstMatch(new SingleStackRecipeInput(itemStack2), world).orElse(null);
            } else {
                recipeEntry = null;
            }

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
            state = (BlockState)state.with(AbstractFurnaceBlock.LIT, blockEntity.isBurning());
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
            public int get(int index) {
                switch (index) {
                    case 0:
                        return burnTime;
                    case 1:
                        return fuelTime;
                    case 2:
                        return cookTime;
                    case 3:
                        return cookTimeTotal;
                    default:
                        return 0;
                }
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        burnTime = value;
                        break;
                    case 1:
                        fuelTime = value;
                        break;
                    case 2:
                        cookTime = value;
                        break;
                    case 3:
                        cookTimeTotal = value;
                }

            }

            public int size() {
                return 4;
            }
        };
        this.recipesUsed = new Object2IntOpenHashMap();
        this.matchGetter = RecipeManager.createCachedMatchGetter(RecipeType.SMELTING);
    }

    public static void clearFuelTimes() {
        fuelTimes = null;
    }

    public static Map<Item, Integer> createFuelTimeMap() {
        Map<Item, Integer> map = fuelTimes;
        if (map != null) {
            return map;
        } else {
            Map<Item, Integer> map2 = Maps.newLinkedHashMap();
            addFuel(map2, (ItemConvertible)Items.LAVA_BUCKET, 20000);
            addFuel(map2, (ItemConvertible)Blocks.COAL_BLOCK, 16000);
            addFuel(map2, (ItemConvertible)Items.BLAZE_ROD, 2400);
            addFuel(map2, (ItemConvertible)Items.COAL, 1600);
            addFuel(map2, (ItemConvertible)Items.CHARCOAL, 1600);
            addFuel(map2, (TagKey)ItemTags.LOGS, 300);
            addFuel(map2, (TagKey)ItemTags.BAMBOO_BLOCKS, 300);
            addFuel(map2, (TagKey)ItemTags.PLANKS, 300);
            addFuel(map2, (ItemConvertible)Blocks.BAMBOO_MOSAIC, 300);
            addFuel(map2, (TagKey)ItemTags.WOODEN_STAIRS, 300);
            addFuel(map2, (ItemConvertible)Blocks.BAMBOO_MOSAIC_STAIRS, 300);
            addFuel(map2, (TagKey)ItemTags.WOODEN_SLABS, 150);
            addFuel(map2, (ItemConvertible)Blocks.BAMBOO_MOSAIC_SLAB, 150);
            addFuel(map2, (TagKey)ItemTags.WOODEN_TRAPDOORS, 300);
            addFuel(map2, (TagKey)ItemTags.WOODEN_PRESSURE_PLATES, 300);
            addFuel(map2, (TagKey)ItemTags.WOODEN_FENCES, 300);
            addFuel(map2, (TagKey)ItemTags.FENCE_GATES, 300);
            addFuel(map2, (ItemConvertible)Blocks.NOTE_BLOCK, 300);
            addFuel(map2, (ItemConvertible)Blocks.BOOKSHELF, 300);
            addFuel(map2, (ItemConvertible)Blocks.CHISELED_BOOKSHELF, 300);
            addFuel(map2, (ItemConvertible)Blocks.LECTERN, 300);
            addFuel(map2, (ItemConvertible)Blocks.JUKEBOX, 300);
            addFuel(map2, (ItemConvertible)Blocks.CHEST, 300);
            addFuel(map2, (ItemConvertible)Blocks.TRAPPED_CHEST, 300);
            addFuel(map2, (ItemConvertible)Blocks.CRAFTING_TABLE, 300);
            addFuel(map2, (ItemConvertible)Blocks.DAYLIGHT_DETECTOR, 300);
            addFuel(map2, (TagKey)ItemTags.BANNERS, 300);
            addFuel(map2, (ItemConvertible)Items.BOW, 300);
            addFuel(map2, (ItemConvertible)Items.FISHING_ROD, 300);
            addFuel(map2, (ItemConvertible)Blocks.LADDER, 300);
            addFuel(map2, (TagKey)ItemTags.SIGNS, 200);
            addFuel(map2, (TagKey)ItemTags.HANGING_SIGNS, 800);
            addFuel(map2, (ItemConvertible)Items.WOODEN_SHOVEL, 200);
            addFuel(map2, (ItemConvertible)Items.WOODEN_SWORD, 200);
            addFuel(map2, (ItemConvertible)Items.WOODEN_HOE, 200);
            addFuel(map2, (ItemConvertible)Items.WOODEN_AXE, 200);
            addFuel(map2, (ItemConvertible)Items.WOODEN_PICKAXE, 200);
            addFuel(map2, (TagKey)ItemTags.WOODEN_DOORS, 200);
            addFuel(map2, (TagKey)ItemTags.BOATS, 1200);
            addFuel(map2, (TagKey)ItemTags.WOOL, 100);
            addFuel(map2, (TagKey)ItemTags.WOODEN_BUTTONS, 100);
            addFuel(map2, (ItemConvertible)Items.STICK, 100);
            addFuel(map2, (TagKey)ItemTags.SAPLINGS, 100);
            addFuel(map2, (ItemConvertible)Items.BOWL, 100);
            addFuel(map2, (TagKey)ItemTags.WOOL_CARPETS, 67);
            addFuel(map2, (ItemConvertible)Blocks.DRIED_KELP_BLOCK, 4001);
            addFuel(map2, (ItemConvertible)Items.CROSSBOW, 300);
            addFuel(map2, (ItemConvertible)Blocks.BAMBOO, 50);
            addFuel(map2, (ItemConvertible)Blocks.DEAD_BUSH, 100);
            addFuel(map2, (ItemConvertible)Blocks.SCAFFOLDING, 50);
            addFuel(map2, (ItemConvertible)Blocks.LOOM, 300);
            addFuel(map2, (ItemConvertible)Blocks.BARREL, 300);
            addFuel(map2, (ItemConvertible)Blocks.CARTOGRAPHY_TABLE, 300);
            addFuel(map2, (ItemConvertible)Blocks.FLETCHING_TABLE, 300);
            addFuel(map2, (ItemConvertible)Blocks.SMITHING_TABLE, 300);
            addFuel(map2, (ItemConvertible)Blocks.COMPOSTER, 300);
            addFuel(map2, (ItemConvertible)Blocks.AZALEA, 100);
            addFuel(map2, (ItemConvertible)Blocks.FLOWERING_AZALEA, 100);
            addFuel(map2, (ItemConvertible)Blocks.MANGROVE_ROOTS, 300);
            fuelTimes = map2;
            return map2;
        }
    }

    private static boolean isNonFlammableWood(Item item) {
        return item.getRegistryEntry().isIn(ItemTags.NON_FLAMMABLE_WOOD);
    }

    private static void addFuel(Map<Item, Integer> fuelTimes, TagKey<Item> tag, int fuelTime) {
        Iterator var3 = Registries.ITEM.iterateEntries(tag).iterator();

        while(var3.hasNext()) {
            RegistryEntry<Item> registryEntry = (RegistryEntry)var3.next();
            if (!isNonFlammableWood((Item)registryEntry.value())) {
                fuelTimes.put((Item)registryEntry.value(), fuelTime);
            }
        }

    }

    private static void addFuel(Map<Item, Integer> fuelTimes, ItemConvertible item, int fuelTime) {
        Item item2 = item.asItem();
        if (isNonFlammableWood(item2)) {
            if (SharedConstants.isDevelopment) {
                throw (IllegalStateException)Util.throwOrPause(new IllegalStateException("A developer tried to explicitly make fire resistant item " + item2.getName((ItemStack)null).getString() + " a furnace fuel. That will not work!"));
            }
        } else {
            fuelTimes.put(item2, fuelTime);
        }
    }


    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory, registryLookup);
        this.burnTime = nbt.getShort("BurnTime");
        this.cookTime = nbt.getShort("CookTime");
        this.cookTimeTotal = nbt.getShort("CookTimeTotal");
        this.fuelTime = this.getFuelTime((ItemStack)this.inventory.get(1));
        NbtCompound nbtCompound = nbt.getCompound("RecipesUsed");
        Iterator var4 = nbtCompound.getKeys().iterator();

        while(var4.hasNext()) {
            String string = (String)var4.next();
            this.recipesUsed.put(Identifier.of(string), nbtCompound.getInt(string));
        }

    }

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
        if (!((ItemStack)slots.get(0)).isEmpty() && recipe != null) {
            ItemStack itemStack = recipe.value().getResult(registryManager);
            if (itemStack.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack2 = (ItemStack)slots.get(2);
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
        } else {
            return false;
        }
    }

    private static boolean craftRecipe(DynamicRegistryManager registryManager, @Nullable RecipeEntry<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (recipe != null && canAcceptRecipeOutput(registryManager, recipe, slots, count)) {
            ItemStack itemStack = (ItemStack)slots.get(0);
            ItemStack itemStack2 = recipe.value().getResult(registryManager);
            ItemStack itemStack3 = (ItemStack)slots.get(2);
            if (itemStack3.isEmpty()) {
                slots.set(2, itemStack2.copy());
            } else if (ItemStack.areItemsAndComponentsEqual(itemStack3, itemStack2)) {
                itemStack3.increment(1);
            }

            if (itemStack.isOf(Blocks.WET_SPONGE.asItem()) && !((ItemStack)slots.get(1)).isEmpty() && ((ItemStack)slots.get(1)).isOf(Items.BUCKET)) {
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
            return (Integer)createFuelTimeMap().getOrDefault(item, 0);
        }
    }



    public static boolean canUseAsFuel(ItemStack stack) {
        return createFuelTimeMap().containsKey(stack.getItem());
    }

    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return BOTTOM_SLOTS;
        } else {
            return side == Direction.UP ? TOP_SLOTS : SIDE_SLOTS;
        }
    }

    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (dir == Direction.DOWN && slot == 1) {
            return stack.isOf(Items.WATER_BUCKET) || stack.isOf(Items.BUCKET);
        } else {
            return true;
        }
    }

    public int size() {
        return this.inventory.size();
    }

    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = (ItemStack)this.inventory.get(slot);
        boolean bl = !stack.isEmpty() && ItemStack.areItemsAndComponentsEqual(itemStack, stack);
        this.inventory.set(slot, stack);
        stack.capCount(this.getMaxCount(stack));
        if (slot == 0 && !bl) {
            this.cookTimeTotal = getCookTime(this.world, this);
            this.cookTime = 0;
            this.markDirty();
        }

    }

    public boolean isValid(int slot, ItemStack stack) {
        if (slot == 2) {
            return false;
        } else if (slot != 1) {
            return true;
        } else {
            ItemStack itemStack = (ItemStack)this.inventory.get(1);
            return canUseAsFuel(stack) || stack.isOf(Items.BUCKET) && !itemStack.isOf(Items.BUCKET);
        }
    }

    public void setLastRecipe(@Nullable RecipeEntry<?> recipe) {
        if (recipe != null) {
            Identifier identifier = recipe.id();
            this.recipesUsed.addTo(identifier, 1);
        }

    }

    @Nullable
    public RecipeEntry<?> getLastRecipe() {
        return null;
    }

    public void unlockLastRecipe(PlayerEntity player, List<ItemStack> ingredients) {
    }

    public void dropExperienceForRecipesUsed(ServerPlayerEntity player) {
        List<RecipeEntry<?>> list = this.getRecipesUsedAndDropExperience(player.getServerWorld(), player.getPos());
        player.unlockRecipes(list);
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            RecipeEntry<?> recipeEntry = (RecipeEntry)var3.next();
            if (recipeEntry != null) {
                player.onRecipeCrafted(recipeEntry, this.inventory);
            }
        }

        this.recipesUsed.clear();
    }

    public List<RecipeEntry<?>> getRecipesUsedAndDropExperience(ServerWorld world, Vec3d pos) {
        List<RecipeEntry<?>> list = Lists.newArrayList();
        ObjectIterator var4 = this.recipesUsed.object2IntEntrySet().iterator();

        while(var4.hasNext()) {
            Object2IntMap.Entry<Identifier> entry = (Object2IntMap.Entry)var4.next();
            world.getRecipeManager().get((Identifier)entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                dropExperience(world, pos, entry.getIntValue(), ((AbstractCookingRecipe)recipe.value()).getExperience());
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

    public void provideRecipeInputs(RecipeMatcher finder) {
        Iterator var2 = this.inventory.iterator();

        while(var2.hasNext()) {
            ItemStack itemStack = (ItemStack)var2.next();
            finder.addInput(itemStack);
        }

    }
}
