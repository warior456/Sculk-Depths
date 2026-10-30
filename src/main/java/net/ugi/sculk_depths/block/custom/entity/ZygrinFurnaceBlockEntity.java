package net.ugi.sculk_depths.block.custom.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.ugi.sculk_depths.block.ModBlockEntities;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.screen.ZygrinFurnaceScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ZygrinFurnaceBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible {

    protected static final int INPUT_SLOT_INDEX = 0;
    protected static final int FUEL_SLOT_INDEX = 1;
    protected static final int SLOT_RESULT = 2;
    public static final int DATA_LIT_TIME = 0;
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    public static final int DATA_LIT_DURATION = 1;
    public static final int DATA_COOKING_PROGRESS = 2;
    public static final int DATA_COOKING_TOTAL_TIME = 3;
    public static final int PROPERTY_COUNT = 4;
    public static final int BURN_TIME_STANDARD = 200;
    public static final int field_31295 = 2;
    protected NonNullList<ItemStack> inventory;
    int litTime;
    int litDuration;
    int cookingProgress;
    int cookingTotalTime;
    @Nullable
    private static volatile Map<Item, Integer> fuelCache;
    protected final ContainerData propertyDelegate;
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed;
    private final RecipeManager.CachedCheck<SingleRecipeInput, ? extends AbstractCookingRecipe> quickCheck;

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.sculk_depths.zygrin_furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
        return new ZygrinFurnaceScreenHandler(containerId, playerInventory, this, this.propertyDelegate);
    }

    //begin custom
    private boolean isBurning() {
        return this.litTime > 0;
    }

    private int checkBurning(BlockPos pos) {
        if (this.litTime > 1) return 0;
        if (this.litTime <= 1 && this.litTime >= -1) {
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
        int foundFlowBlock = 0;

        if (level.getBlockState(pos.relative(Direction.NORTH)).is(ModBlocks.KRYSLUM)) return true;
        if (level.getBlockState(pos.relative(Direction.EAST)).is(ModBlocks.KRYSLUM)) return true;
        if (level.getBlockState(pos.relative(Direction.SOUTH)).is(ModBlocks.KRYSLUM)) return true;
        if (level.getBlockState(pos.relative(Direction.WEST)).is(ModBlocks.KRYSLUM)) return true;
        if (level.getBlockState(pos.relative(Direction.UP)).is(ModBlocks.KRYSLUM)) return true;
        if (level.getBlockState(pos.relative(Direction.DOWN)).is(ModBlocks.KRYSLUM)) return true;

        if (level.getBlockState(pos.relative(Direction.NORTH)).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH))) foundFlowBlock += findKryslumFlowBlock(pos.relative(Direction.NORTH), 256);
        if (level.getBlockState(pos.relative(Direction.EAST)).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST))) foundFlowBlock += findKryslumFlowBlock(pos.relative(Direction.EAST), 256);
        if (level.getBlockState(pos.relative(Direction.SOUTH)).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH))) foundFlowBlock += findKryslumFlowBlock(pos.relative(Direction.SOUTH), 256);
        if (level.getBlockState(pos.relative(Direction.WEST)).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST))) foundFlowBlock += findKryslumFlowBlock(pos.relative(Direction.WEST), 256);
        if (level.getBlockState(pos.relative(Direction.UP)).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN))) foundFlowBlock += findKryslumFlowBlock(pos.relative(Direction.UP), 256);
        if (level.getBlockState(pos.relative(Direction.DOWN)).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP))) foundFlowBlock += findKryslumFlowBlock(pos.relative(Direction.DOWN), 256);

        return foundFlowBlock > 0;
    }

    public int findKryslumFlowBlock(BlockPos pos, int count) {
        int foundFlowBlock = 0;
        if (count == 0) return 0;

        if (level.getBlockState(pos).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH)) && level.getBlockState(pos.relative(Direction.NORTH)).is(ModBlocks.KRYSLUM)) return 1;
        if (level.getBlockState(pos).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST)) && level.getBlockState(pos.relative(Direction.EAST)).is(ModBlocks.KRYSLUM)) return 1;
        if (level.getBlockState(pos).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH)) && level.getBlockState(pos.relative(Direction.SOUTH)).is(ModBlocks.KRYSLUM)) return 1;
        if (level.getBlockState(pos).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST)) && level.getBlockState(pos.relative(Direction.WEST)).is(ModBlocks.KRYSLUM)) return 1;
        if (level.getBlockState(pos).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN)) && level.getBlockState(pos.relative(Direction.UP)).is(ModBlocks.KRYSLUM)) return 1;
        if (level.getBlockState(pos).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH)) && level.getBlockState(pos.relative(Direction.DOWN)).is(ModBlocks.KRYSLUM)) return 1;

        if (level.getBlockState(pos.relative(Direction.NORTH)).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH))) foundFlowBlock += findKryslumFlowBlock(pos.relative(Direction.NORTH), count - 1);
        if (level.getBlockState(pos.relative(Direction.EAST)).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST))) foundFlowBlock += findKryslumFlowBlock(pos.relative(Direction.EAST), count - 1);
        if (level.getBlockState(pos.relative(Direction.SOUTH)).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH))) foundFlowBlock += findKryslumFlowBlock(pos.relative(Direction.SOUTH), count - 1);
        if (level.getBlockState(pos.relative(Direction.WEST)).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST))) foundFlowBlock += findKryslumFlowBlock(pos.relative(Direction.WEST), count - 1);
        if (level.getBlockState(pos.relative(Direction.UP)).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN))) foundFlowBlock += findKryslumFlowBlock(pos.relative(Direction.UP), count - 1);
        if (level.getBlockState(pos.relative(Direction.DOWN)).equals(ModBlocks.ZYGRIN_FLOWBLOCK.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP))) foundFlowBlock += findKryslumFlowBlock(pos.relative(Direction.DOWN), count - 1);
        if (foundFlowBlock > 0) return 1;
        return 0;
    }

    private static int getTotalCookTime(Level level, ZygrinFurnaceBlockEntity isFurnaceMenu) {
        SingleRecipeInput singleStackRecipeInput = new SingleRecipeInput(isFurnaceMenu.getItem(0));
        return isFurnaceMenu.quickCheck.getRecipeFor(singleStackRecipeInput, level).map((addRecipe) -> {
            return (((AbstractCookingRecipe)addRecipe.value()).getCookingTime()) / 4; //furnace speed
        }).orElse(50);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ZygrinFurnaceBlockEntity blockEntity) {
        boolean bl4 = false;
        boolean bl = blockEntity.isBurning();
        boolean bl2 = false;
        int handleBurning = blockEntity.checkBurning(pos);
        switch (handleBurning) {
            case 0: //burning and above 0 ticks left
                --blockEntity.litTime;
                break;
            case 1: //lighting or checking for fuel source
                blockEntity.litTime = 50; // set not more ticks than minimum cook time (2.5 sec * 20 or 10 sec * 20)
                // todo make corrolate to burnspeed and configurable
                break;
            case 2: //no fuel source and cache at -1
                blockEntity.litTime = -20 + Mth.nextInt(RandomSource.create(), -2, 2); //todo maybe configurable?
                break;
            case 3: //no fuel source and valid cache
                ++blockEntity.litTime;
                break;
        }

        ItemStack item = blockEntity.inventory.get(1);
        ItemStack itemStack2 = blockEntity.inventory.get(0);
        boolean bl3 = !blockEntity.inventory.get(0).isEmpty();
        boolean bl5 = bl4 = !item.isEmpty();
        if (blockEntity.isBurning() || bl4 && bl3) {
            RecipeHolder<?> recipeEntry;
            if (bl3) {
                recipeEntry = blockEntity.quickCheck.getRecipeFor(new SingleRecipeInput(itemStack2), level).orElse(null);
            } else {
                recipeEntry = null;
            }

            int i = blockEntity.getMaxStackSize();
            if (!blockEntity.isBurning() && canBurn(level.registryAccess(), recipeEntry, blockEntity.inventory, i)) {
                blockEntity.litTime = blockEntity.getBurnDuration(item);
                blockEntity.litDuration = blockEntity.litTime;
                if (blockEntity.isBurning()) {
                    bl2 = true;
                    if (bl4) {
                        Item items = item.getItem();
                        item.shrink(1);
                        if (item.isEmpty()) {
                            Item item2 = items.getCraftingRemainingItem();
                            blockEntity.inventory.set(1, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
                        }
                    }
                }
            }

            if (blockEntity.isBurning() && canBurn(level.registryAccess(), recipeEntry, blockEntity.inventory, i)) {
                ++blockEntity.cookingProgress;
                if (blockEntity.cookingProgress == blockEntity.cookingTotalTime) {
                    blockEntity.cookingProgress = 0;
                    blockEntity.cookingTotalTime = getTotalCookTime(level, blockEntity);
                    if (burn(level.registryAccess(), recipeEntry, blockEntity.inventory, i)) {
                        blockEntity.setRecipeUsed(recipeEntry);
                    }

                    bl2 = true;
                }
            } else {
                blockEntity.cookingProgress = 0;
            }
        } else if (!blockEntity.isBurning() && blockEntity.cookingProgress > 0) {
            blockEntity.cookingProgress = Mth.clamp(blockEntity.cookingProgress - 2, 0, blockEntity.cookingTotalTime);
        }

        if (bl != blockEntity.isBurning()) {
            bl2 = true;
            state = state.setValue(AbstractFurnaceBlock.LIT, blockEntity.isBurning());
            level.setBlock(pos, state, 3);
        }

        if (bl2) {
            setChanged(level, pos, state);
        }

    }

    //end custom

    public ZygrinFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ZYGRIN_FURNACE_BLOCK_ENTITY, pos, state);
        this.inventory = NonNullList.withSize(3, ItemStack.EMPTY);
        this.propertyDelegate = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0:
                        return litTime;
                    case 1:
                        return litDuration;
                    case 2:
                        return cookingProgress;
                    case 3:
                        return cookingTotalTime;
                    default:
                        return 0;
                }
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        litTime = value;
                        break;
                    case 1:
                        litDuration = value;
                        break;
                    case 2:
                        cookingProgress = value;
                        break;
                    case 3:
                        cookingTotalTime = value;
                }

            }

            public int getCount() {
                return 4;
            }
        };
        this.recipesUsed = new Object2IntOpenHashMap<>();
        this.quickCheck = RecipeManager.createCheck(RecipeType.SMELTING);
    }

    public static void invalidateCache() {
        fuelCache = null;
    }

    public static Map<Item, Integer> getFuel() {
        Map<Item, Integer> map = fuelCache;
        if (map != null) {
            return map;
        } else {
            Map<Item, Integer> map2 = Maps.newLinkedHashMap();
            add(map2, (ItemLike)Items.LAVA_BUCKET, 20000);
            add(map2, (ItemLike)Blocks.COAL_BLOCK, 16000);
            add(map2, (ItemLike)Items.BLAZE_ROD, 2400);
            add(map2, (ItemLike)Items.COAL, 1600);
            add(map2, (ItemLike)Items.CHARCOAL, 1600);
            add(map2, (TagKey)ItemTags.LOGS, 300);
            add(map2, (TagKey)ItemTags.BAMBOO_BLOCKS, 300);
            add(map2, (TagKey)ItemTags.PLANKS, 300);
            add(map2, (ItemLike)Blocks.BAMBOO_MOSAIC, 300);
            add(map2, (TagKey)ItemTags.WOODEN_STAIRS, 300);
            add(map2, (ItemLike)Blocks.BAMBOO_MOSAIC_STAIRS, 300);
            add(map2, (TagKey)ItemTags.WOODEN_SLABS, 150);
            add(map2, (ItemLike)Blocks.BAMBOO_MOSAIC_SLAB, 150);
            add(map2, (TagKey)ItemTags.WOODEN_TRAPDOORS, 300);
            add(map2, (TagKey)ItemTags.WOODEN_PRESSURE_PLATES, 300);
            add(map2, (TagKey)ItemTags.WOODEN_FENCES, 300);
            add(map2, (TagKey)ItemTags.FENCE_GATES, 300);
            add(map2, (ItemLike)Blocks.NOTE_BLOCK, 300);
            add(map2, (ItemLike)Blocks.BOOKSHELF, 300);
            add(map2, (ItemLike)Blocks.CHISELED_BOOKSHELF, 300);
            add(map2, (ItemLike)Blocks.LECTERN, 300);
            add(map2, (ItemLike)Blocks.JUKEBOX, 300);
            add(map2, (ItemLike)Blocks.CHEST, 300);
            add(map2, (ItemLike)Blocks.TRAPPED_CHEST, 300);
            add(map2, (ItemLike)Blocks.CRAFTING_TABLE, 300);
            add(map2, (ItemLike)Blocks.DAYLIGHT_DETECTOR, 300);
            add(map2, (TagKey)ItemTags.BANNERS, 300);
            add(map2, (ItemLike)Items.BOW, 300);
            add(map2, (ItemLike)Items.FISHING_ROD, 300);
            add(map2, (ItemLike)Blocks.LADDER, 300);
            add(map2, (TagKey)ItemTags.SIGNS, 200);
            add(map2, (TagKey)ItemTags.HANGING_SIGNS, 800);
            add(map2, (ItemLike)Items.WOODEN_SHOVEL, 200);
            add(map2, (ItemLike)Items.WOODEN_SWORD, 200);
            add(map2, (ItemLike)Items.WOODEN_HOE, 200);
            add(map2, (ItemLike)Items.WOODEN_AXE, 200);
            add(map2, (ItemLike)Items.WOODEN_PICKAXE, 200);
            add(map2, (TagKey)ItemTags.WOODEN_DOORS, 200);
            add(map2, (TagKey)ItemTags.BOATS, 1200);
            add(map2, (TagKey)ItemTags.WOOL, 100);
            add(map2, (TagKey)ItemTags.WOODEN_BUTTONS, 100);
            add(map2, (ItemLike)Items.STICK, 100);
            add(map2, (TagKey)ItemTags.SAPLINGS, 100);
            add(map2, (ItemLike)Items.BOWL, 100);
            add(map2, (TagKey)ItemTags.WOOL_CARPETS, 67);
            add(map2, (ItemLike)Blocks.DRIED_KELP_BLOCK, 4001);
            add(map2, (ItemLike)Items.CROSSBOW, 300);
            add(map2, (ItemLike)Blocks.BAMBOO, 50);
            add(map2, (ItemLike)Blocks.DEAD_BUSH, 100);
            add(map2, (ItemLike)Blocks.SCAFFOLDING, 50);
            add(map2, (ItemLike)Blocks.LOOM, 300);
            add(map2, (ItemLike)Blocks.BARREL, 300);
            add(map2, (ItemLike)Blocks.CARTOGRAPHY_TABLE, 300);
            add(map2, (ItemLike)Blocks.FLETCHING_TABLE, 300);
            add(map2, (ItemLike)Blocks.SMITHING_TABLE, 300);
            add(map2, (ItemLike)Blocks.COMPOSTER, 300);
            add(map2, (ItemLike)Blocks.AZALEA, 100);
            add(map2, (ItemLike)Blocks.FLOWERING_AZALEA, 100);
            add(map2, (ItemLike)Blocks.MANGROVE_ROOTS, 300);
            fuelCache = map2;
            return map2;
        }
    }

    private static boolean isNeverAFurnaceFuel(Item items) {
        return items.builtInRegistryHolder().is(ItemTags.NON_FLAMMABLE_WOOD);
    }

    private static void add(Map<Item, Integer> fuelCache, TagKey<Item> of, int litDuration) {
        for (Holder<Item> builtInRegistryHolder : BuiltInRegistries.ITEM.getTagOrEmpty(of)) {
            if (!isNeverAFurnaceFuel(builtInRegistryHolder.value())) {
                fuelCache.put(builtInRegistryHolder.value(), litDuration);
            }
        }
    }

    private static void add(Map<Item, Integer> fuelCache, ItemLike items, int litDuration) {
        Item item2 = items.asItem();
        if (isNeverAFurnaceFuel(item2)) {
            if (SharedConstants.IS_RUNNING_IN_IDE) {
                throw (IllegalStateException)Util.pauseInIde(new IllegalStateException("A developer tried to explicitly make fire resistant item " + item2.getName(ItemStack.EMPTY).getString() + " a furnace fuel. That will not work!"));
            }
        } else {
            fuelCache.put(item2, litDuration);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.inventory, registries);
        this.litTime = nbt.getShort("BurnTime");
        this.cookingProgress = nbt.getShort("CookTime");
        this.cookingTotalTime = nbt.getShort("CookTimeTotal");
        this.litDuration = this.getBurnDuration(this.inventory.get(1));
        CompoundTag nbtCompound = nbt.getCompound("RecipesUsed");

        for (String string : nbtCompound.getAllKeys()) {
            this.recipesUsed.put(ResourceLocation.parse(string), nbtCompound.getInt(string));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        nbt.putShort("BurnTime", (short)this.litTime);
        nbt.putShort("CookTime", (short)this.cookingProgress);
        nbt.putShort("CookTimeTotal", (short)this.cookingTotalTime);
        ContainerHelper.saveAllItems(nbt, this.inventory, registries);
        CompoundTag nbtCompound = new CompoundTag();
        this.recipesUsed.forEach((id, count) -> {
            nbtCompound.putInt(id.toString(), count);
        });
        nbt.put("RecipesUsed", nbtCompound);
    }

    private static boolean canBurn(RegistryAccess registryAccess, @Nullable RecipeHolder<?> addRecipe, NonNullList<ItemStack> slots, int count) {
        if (!slots.get(0).isEmpty() && addRecipe != null) {
            ItemStack item = ((Recipe<?>)addRecipe.value()).getResultItem(registryAccess);
            if (item.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack2 = slots.get(2);
                if (itemStack2.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItemSameComponents(itemStack2, item)) {
                    return false;
                } else if (itemStack2.getCount() < count && itemStack2.getCount() < itemStack2.getMaxStackSize()) {
                    return true;
                } else {
                    return itemStack2.getCount() < item.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private static boolean burn(RegistryAccess registryAccess, @Nullable RecipeHolder<?> addRecipe, NonNullList<ItemStack> slots, int count) {
        if (addRecipe != null && canBurn(registryAccess, addRecipe, slots, count)) {
            ItemStack item = slots.get(0);
            ItemStack itemStack2 = ((Recipe<?>)addRecipe.value()).getResultItem(registryAccess);
            ItemStack itemStack3 = slots.get(2);
            if (itemStack3.isEmpty()) {
                slots.set(2, itemStack2.copy());
            } else if (ItemStack.isSameItemSameComponents(itemStack3, itemStack2)) {
                itemStack3.grow(1);
            }

            if (item.is(Blocks.WET_SPONGE.asItem()) && !slots.get(1).isEmpty() && slots.get(1).is(Items.BUCKET)) {
                slots.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            item.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    protected int getBurnDuration(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            Item items = fuel.getItem();
            return getFuel().getOrDefault(items, 0);
        }
    }

    public static boolean isFuel(ItemStack stack) {
        return getFuel().containsKey(stack.getItem());
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        if (dir == Direction.DOWN && slot == 1) {
            return stack.is(Items.WATER_BUCKET) || stack.is(Items.BUCKET);
        } else {
            return true;
        }
    }

    @Deprecated
    public int[] getAvailableSlots(Direction side) {
        return this.getSlotsForFace(side);
    }

    @Deprecated
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.canPlaceItemThroughFace(slot, stack, dir);
    }

    @Deprecated
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return this.canTakeItemThroughFace(slot, stack, dir);
    }

    @Override
    public int getContainerSize() {
        return this.inventory.size();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        ItemStack item = this.inventory.get(slot);
        boolean bl = !stack.isEmpty() && ItemStack.isSameItemSameComponents(item, stack);
        this.inventory.set(slot, stack);
        stack.limitSize(this.getMaxStackSize(stack));
        if (slot == 0 && !bl) {
            this.cookingTotalTime = getTotalCookTime(this.level, this);
            this.cookingProgress = 0;
            this.setChanged();
        }
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot == 2) {
            return false;
        } else if (slot != 1) {
            return true;
        } else {
            ItemStack item = this.inventory.get(1);
            return isFuel(stack) || stack.is(Items.BUCKET) && !item.is(Items.BUCKET);
        }
    }

    @Deprecated
    public int size() {
        return this.getContainerSize();
    }

    @Deprecated
    public ItemStack getStack(int slot) {
        return this.getItem(slot);
    }

    @Deprecated
    public void setStack(int slot, ItemStack stack) {
        this.setItem(slot, stack);
    }

    @Deprecated
    public boolean isValid(int slot, ItemStack stack) {
        return this.canPlaceItem(slot, stack);
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> addRecipe) {
        if (addRecipe != null) {
            ResourceLocation id = addRecipe.id();
            this.recipesUsed.addTo(id, 1);
        }
    }

    @Override
    @Nullable
    public RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(Player player, List<ItemStack> ingredients) {
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        List<RecipeHolder<?>> list = this.getRecipesToAwardAndPopExperience(player.serverLevel(), player.position());
        player.awardRecipes(list);

        for (RecipeHolder<?> recipeEntry : list) {
            if (recipeEntry != null) {
                player.triggerRecipeCrafted(recipeEntry, this.inventory);
            }
        }

        this.recipesUsed.clear();
    }

    public List<RecipeHolder<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 pos) {
        List<RecipeHolder<?>> list = Lists.newArrayList();

        for (Object2IntMap.Entry<ResourceLocation> holder : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(holder.getKey()).ifPresent((addRecipe) -> {
                list.add(addRecipe);
                dropExperience(level, pos, holder.getIntValue(), ((AbstractCookingRecipe)addRecipe.value()).getExperience());
            });
        }

        return list;
    }

    private static void dropExperience(ServerLevel level, Vec3 pos, int multiplier, float experience) {
        int i = Mth.floor((float)multiplier * experience);
        float f = Mth.frac((float)multiplier * experience);
        if (f != 0.0F && Math.random() < (double)f) {
            ++i;
        }

        ExperienceOrb.award(level, pos, i);
    }

    @Override
    public void fillStackedContents(StackedContents finder) {
        for (ItemStack item : this.inventory) {
            finder.accountStack(item);
        }
    }
}
