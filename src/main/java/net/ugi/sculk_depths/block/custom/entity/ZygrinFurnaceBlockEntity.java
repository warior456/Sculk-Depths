package net.ugi.sculk_depths.block.custom.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.SharedConstants;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.FurnaceBlockEntity;
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
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.FluidTags;
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
import net.minecraft.util.function.LazyIterationConsumer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlockEntities;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.FlowBlock;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.screen.ZygrinFurnaceScreenHandler;
import net.ugi.sculk_depths.state.property.ModProperties;
import net.ugi.sculk_depths.tags.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ZygrinFurnaceBlockEntity
        extends LockableContainerBlockEntity
        implements SidedInventory,
        RecipeUnlocker,
        RecipeInputProvider {
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
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    int burnTime;
    int fuelTime;
    int cookTime;
    int cookTimeTotal;
    private final RecipeManager.MatchGetter<Inventory, ? extends AbstractCookingRecipe> matchGetter;

    //my added stuff
    public ZygrinFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ZYGRIN_FURNACE_BLOCK_ENTITY, pos, state);
        this.matchGetter = RecipeManager.createCachedMatchGetter(RecipeType.SMELTING);
    }


    
    @Override
    protected Text getContainerName() {
        return Text.translatable("container.furnace");
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new ZygrinFurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

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

    public boolean findValidKryslum(BlockPos pos){
        ArrayList<BlockPos> blockPosArrayList = new ArrayList<BlockPos>();
        blockPosArrayList.add(pos.offset(Direction.NORTH));
        blockPosArrayList.add(pos.offset(Direction.EAST));
        blockPosArrayList.add(pos.offset(Direction.SOUTH));
        blockPosArrayList.add(pos.offset(Direction.WEST));
        blockPosArrayList.add(pos.offset(Direction.UP));
        blockPosArrayList.add(pos.offset(Direction.DOWN));
        for (BlockPos blockPos : blockPosArrayList) {
            BlockState blockState = world.getBlockState(blockPos);
            if(blockState.isIn(ModTags.Blocks.KRYSLUM_FLOWABLE_BLOCKS)){
                blockState.scheduledTick((ServerWorld) world, blockPos, Random.create());
                if(blockState.get(ModProperties.KRYSLUM_POWERED)){
                    return true;
                }
            } else if (blockState.getFluidState().isIn(ModTags.Fluids.KRYSLUM)) {
                return true;
            }
        }
        
        return false;
    }

    public static void tick(World world, BlockPos pos, BlockState state, ZygrinFurnaceBlockEntity blockEntity) {
        boolean bl4;
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
        boolean bl3 = !blockEntity.inventory.get(0).isEmpty();
        boolean bl5 = bl4 = !itemStack.isEmpty();
        if (blockEntity.isBurning() || bl4 && bl3) {
            Recipe recipe = bl3 ? (Recipe)blockEntity.matchGetter.getFirstMatch(blockEntity, world).orElse(null) : null;
            int i = blockEntity.getMaxCountPerStack();
            if (!blockEntity.isBurning() && canAcceptRecipeOutput(world.getRegistryManager(), recipe, blockEntity.inventory, i)) {
                blockEntity.fuelTime = blockEntity.burnTime = blockEntity.getFuelTime(itemStack);
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
            if (blockEntity.isBurning() && canAcceptRecipeOutput(world.getRegistryManager(), recipe, blockEntity.inventory, i)) {
                ++blockEntity.cookTime; //speed
                ++blockEntity.cookTime;
                ++blockEntity.cookTime;
                ++blockEntity.cookTime;
                if (blockEntity.cookTime >= blockEntity.cookTimeTotal) {
                    blockEntity.cookTime = 0;
                    blockEntity.cookTimeTotal = getCookTime(world, blockEntity);
                    if (craftRecipe(world.getRegistryManager(), recipe, blockEntity.inventory, i)) {
                        blockEntity.setLastRecipe(recipe);
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
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }
        if (bl2) {
            markDirty(world, pos, state);
        }
    }
    
    //end my added / edited stuff
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate(){

        @Override
        public int get(int index) {
            switch (index) {
                case 0: {
                    return burnTime;
                }
                case 1: {
                    return fuelTime;
                }
                case 2: {
                    return cookTime;
                }
                case 3: {
                    return cookTimeTotal;
                }
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: {
                    burnTime = value;
                    break;
                }
                case 1: {
                    fuelTime = value;
                    break;
                }
                case 2: {
                    cookTime = value;
                    break;
                }
                case 3: {
                    cookTimeTotal = value;
                    break;
                }
            }
        }

        @Override
        public int size() {
            return 4;
        }
    };
    private final Object2IntOpenHashMap<Identifier> recipesUsed = new Object2IntOpenHashMap();


    public static Map<Item, Integer> createFuelTimeMap() {
        LinkedHashMap<Item, Integer> map = Maps.newLinkedHashMap();
        addFuel(map, Items.LAVA_BUCKET, 20000);
        addFuel(map, Blocks.COAL_BLOCK, 16000);
        addFuel(map, Items.BLAZE_ROD, 2400);
        addFuel(map, Items.COAL, 1600);
        addFuel(map, Items.CHARCOAL, 1600);
        addFuel(map, ItemTags.LOGS, 300);
        addFuel(map, ItemTags.BAMBOO_BLOCKS, 300);
        addFuel(map, ItemTags.PLANKS, 300);
        addFuel(map, Blocks.BAMBOO_MOSAIC, 300);
        addFuel(map, ItemTags.WOODEN_STAIRS, 300);
        addFuel(map, Blocks.BAMBOO_MOSAIC_STAIRS, 300);
        addFuel(map, ItemTags.WOODEN_SLABS, 150);
        addFuel(map, Blocks.BAMBOO_MOSAIC_SLAB, 150);
        addFuel(map, ItemTags.WOODEN_TRAPDOORS, 300);
        addFuel(map, ItemTags.WOODEN_PRESSURE_PLATES, 300);
        addFuel(map, ItemTags.WOODEN_FENCES, 300);
        addFuel(map, ItemTags.FENCE_GATES, 300);
        addFuel(map, Blocks.NOTE_BLOCK, 300);
        addFuel(map, Blocks.BOOKSHELF, 300);
        addFuel(map, Blocks.CHISELED_BOOKSHELF, 300);
        addFuel(map, Blocks.LECTERN, 300);
        addFuel(map, Blocks.JUKEBOX, 300);
        addFuel(map, Blocks.CHEST, 300);
        addFuel(map, Blocks.TRAPPED_CHEST, 300);
        addFuel(map, Blocks.CRAFTING_TABLE, 300);
        addFuel(map, Blocks.DAYLIGHT_DETECTOR, 300);
        addFuel(map, ItemTags.BANNERS, 300);
        addFuel(map, Items.BOW, 300);
        addFuel(map, Items.FISHING_ROD, 300);
        addFuel(map, Blocks.LADDER, 300);
        addFuel(map, ItemTags.SIGNS, 200);
        addFuel(map, ItemTags.HANGING_SIGNS, 800);
        addFuel(map, Items.WOODEN_SHOVEL, 200);
        addFuel(map, Items.WOODEN_SWORD, 200);
        addFuel(map, Items.WOODEN_HOE, 200);
        addFuel(map, Items.WOODEN_AXE, 200);
        addFuel(map, Items.WOODEN_PICKAXE, 200);
        addFuel(map, ItemTags.WOODEN_DOORS, 200);
        addFuel(map, ItemTags.BOATS, 1200);
        addFuel(map, ItemTags.WOOL, 100);
        addFuel(map, ItemTags.WOODEN_BUTTONS, 100);
        addFuel(map, Items.STICK, 100);
        addFuel(map, ItemTags.SAPLINGS, 100);
        addFuel(map, Items.BOWL, 100);
        addFuel(map, ItemTags.WOOL_CARPETS, 67);
        addFuel(map, Blocks.DRIED_KELP_BLOCK, 4001);
        addFuel(map, Items.CROSSBOW, 300);
        addFuel(map, Blocks.BAMBOO, 50);
        addFuel(map, Blocks.DEAD_BUSH, 100);
        addFuel(map, Blocks.SCAFFOLDING, 50);
        addFuel(map, Blocks.LOOM, 300);
        addFuel(map, Blocks.BARREL, 300);
        addFuel(map, Blocks.CARTOGRAPHY_TABLE, 300);
        addFuel(map, Blocks.FLETCHING_TABLE, 300);
        addFuel(map, Blocks.SMITHING_TABLE, 300);
        addFuel(map, Blocks.COMPOSTER, 300);
        addFuel(map, Blocks.AZALEA, 100);
        addFuel(map, Blocks.FLOWERING_AZALEA, 100);
        addFuel(map, Blocks.MANGROVE_ROOTS, 300);
        return map;
    }

    /**
     * {@return whether the provided {@code item} is in the {@link
     * ItemTags#NON_FLAMMABLE_WOOD non_flammable_wood} tag}
     */
    private static boolean isNonFlammableWood(Item item) {
        return item.getRegistryEntry().isIn(ItemTags.NON_FLAMMABLE_WOOD);
    }

    private static void addFuel(Map<Item, Integer> fuelTimes, TagKey<Item> tag, int fuelTime) {
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(tag)) {
            if (isNonFlammableWood(registryEntry.value())) continue;
            fuelTimes.put(registryEntry.value(), fuelTime);
        }
    }

    private static void addFuel(Map<Item, Integer> fuelTimes, ItemConvertible item, int fuelTime) {
        Item item2 = item.asItem();
        if (isNonFlammableWood(item2)) {
            if (SharedConstants.isDevelopment) {
                throw Util.throwOrPause(new IllegalStateException("A developer tried to explicitly make fire resistant item " + item2.getName(null).getString() + " a furnace fuel. That will not work!"));
            }
            return;
        }
        fuelTimes.put(item2, fuelTime);
    }



    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
        this.burnTime = nbt.getShort("BurnTime");
        this.cookTime = nbt.getShort("CookTime");
        this.cookTimeTotal = nbt.getShort("CookTimeTotal");
        this.fuelTime = this.getFuelTime(this.inventory.get(1));
        NbtCompound nbtCompound = nbt.getCompound("RecipesUsed");
        for (String string : nbtCompound.getKeys()) {
            this.recipesUsed.put(new Identifier(string), nbtCompound.getInt(string));
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putShort("BurnTime", (short)this.burnTime);
        nbt.putShort("CookTime", (short)this.cookTime);
        nbt.putShort("CookTimeTotal", (short)this.cookTimeTotal);
        Inventories.writeNbt(nbt, this.inventory);
        NbtCompound nbtCompound = new NbtCompound();
        this.recipesUsed.forEach((identifier, count) -> nbtCompound.putInt(identifier.toString(), (int)count));
        nbt.put("RecipesUsed", nbtCompound);
    }


    



    private static boolean canAcceptRecipeOutput(DynamicRegistryManager registryManager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (slots.get(0).isEmpty() || recipe == null) {
            return false;
        }
        ItemStack itemStack = recipe.getOutput(registryManager);
        if (itemStack.isEmpty()) {
            return false;
        }
        ItemStack itemStack2 = slots.get(2);
        if (itemStack2.isEmpty()) {
            return true;
        }
        if (!ItemStack.areItemsEqual(itemStack2, itemStack)) {
            return false;
        }
        if (itemStack2.getCount() < count && itemStack2.getCount() < itemStack2.getMaxCount()) {
            return true;
        }
        return itemStack2.getCount() < itemStack.getMaxCount();
    }

    private static boolean craftRecipe(DynamicRegistryManager registryManager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (recipe == null || !canAcceptRecipeOutput(registryManager, recipe, slots, count)) {
            return false;
        }
        ItemStack itemStack = slots.get(0);
        ItemStack itemStack2 = recipe.getOutput(registryManager);
        ItemStack itemStack3 = slots.get(2);
        if (itemStack3.isEmpty()) {
            slots.set(2, itemStack2.copy());
        } else if (itemStack3.isOf(itemStack2.getItem())) {
            itemStack3.increment(1);
        }
        if (itemStack.isOf(Blocks.WET_SPONGE.asItem()) && !slots.get(1).isEmpty() && slots.get(1).isOf(Items.BUCKET)) {
            slots.set(1, new ItemStack(Items.WATER_BUCKET));
        }
        itemStack.decrement(1);
        return true;
    }

    protected int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        }
        Item item = fuel.getItem();
        return createFuelTimeMap().getOrDefault(item, 0);
    }

    private static int getCookTime(World world, ZygrinFurnaceBlockEntity furnace) {
        return furnace.matchGetter.getFirstMatch(furnace, world).map(AbstractCookingRecipe::getCookTime).orElse(200);
    }

    public static boolean canUseAsFuel(ItemStack stack) {
        return createFuelTimeMap().containsKey(stack.getItem());
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return BOTTOM_SLOTS;
        }
        if (side == Direction.UP) {
            return TOP_SLOTS;
        }
        return SIDE_SLOTS;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (dir == Direction.DOWN && slot == 1) {
            return stack.isOf(Items.WATER_BUCKET) || stack.isOf(Items.BUCKET);
        }
        return true;
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.inventory) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = this.inventory.get(slot);
        boolean bl = !stack.isEmpty() && ItemStack.canCombine(itemStack, stack);
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
        if (slot == 0 && !bl) {
            this.cookTimeTotal = getCookTime(this.world, this);
            this.cookTime = 0;
            this.markDirty();
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot == 2) {
            return false;
        }
        if (slot == 1) {
            ItemStack itemStack = this.inventory.get(1);
            return canUseAsFuel(stack) || stack.isOf(Items.BUCKET) && !itemStack.isOf(Items.BUCKET);
        }
        return true;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public void setLastRecipe(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            Identifier identifier = recipe.getId();
            this.recipesUsed.addTo(identifier, 1);
        }
    }

    @Override
    @Nullable
    public Recipe<?> getLastRecipe() {
        return null;
    }

    @Override
    public void unlockLastRecipe(PlayerEntity player, List<ItemStack> ingredients) {
    }

    public void dropExperienceForRecipesUsed(ServerPlayerEntity player) {
        List<Recipe<?>> list = this.getRecipesUsedAndDropExperience(player.getServerWorld(), player.getPos());
        player.unlockRecipes(list);
        for (Recipe<?> recipe : list) {
            if (recipe == null) continue;
            player.onRecipeCrafted(recipe, this.inventory);
        }
        this.recipesUsed.clear();
    }

    public List<Recipe<?>> getRecipesUsedAndDropExperience(ServerWorld world, Vec3d pos) {
        ArrayList<Recipe<?>> list = Lists.newArrayList();
        for (Object2IntMap.Entry entry : this.recipesUsed.object2IntEntrySet()) {
            world.getRecipeManager().get((Identifier)entry.getKey()).ifPresent(recipe -> {
                list.add((Recipe<?>)recipe);
                dropExperience(world, pos, entry.getIntValue(), ((AbstractCookingRecipe)recipe).getExperience());
            });
        }
        return list;
    }

    private static void dropExperience(ServerWorld world, Vec3d pos, int multiplier, float experience) {
        int i = MathHelper.floor((float)multiplier * experience);
        float f = MathHelper.fractionalPart((float)multiplier * experience);
        if (f != 0.0f && Math.random() < (double)f) {
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
