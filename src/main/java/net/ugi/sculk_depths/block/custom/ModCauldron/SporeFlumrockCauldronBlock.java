package net.ugi.sculk_depths.block.custom.ModCauldron;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.enums.CrystalType;
import net.ugi.sculk_depths.item.ModItems;
//import net.ugi.sculk_depths.item.crystal.CrystalUpgrade;
import net.ugi.sculk_depths.state.property.ModProperties;
import net.ugi.sculk_depths.tags.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static net.ugi.sculk_depths.state.property.ModProperties.CRUX_LEVEL;


public class SporeFlumrockCauldronBlock extends AbstractCauldronBlock{
    @Override
    protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
        return null;
    }

    Item[] crystalItemArray = {ModItems.WHITE_CRYSTAL, ModItems.BLUE_CRYSTAL, ModItems.ORANGE_CRYSTAL, ModItems.LIME_CRYSTAL};
    CrystalType[] crystalStateArray = {CrystalType.WHITE, CrystalType.BLUE, CrystalType.ORANGE, CrystalType.LIME};


    List<String> crystalItemNbt = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

    List<Item> crystalItemList = Arrays.asList(crystalItemArray);
    List<CrystalType> crystalStateList = Arrays.asList(crystalStateArray);


    public static final IntProperty LEVEL = ModProperties.SPORE_LEVEL;

    public static final EnumProperty<CrystalType> CRYSTAL = ModProperties.CRYSTAL_TYPE;

    public static final IntProperty CRUX = ModProperties.CRUX_LEVEL;


    private final Map<Item, CauldronBehavior> behaviorMap;

    public SporeFlumrockCauldronBlock(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
        super(settings, ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR);
        this.behaviorMap = behaviorMap;
        this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 1));
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return new ItemStack(ModBlocks.FLUMROCK_CAULDRON);
    }

    private static final VoxelShape RAYCAST_SHAPE = AbstractCauldronBlock.createCuboidShape(1.5, 2.0, 1.5, 14.5, 16.0, 14.5);
    protected static final VoxelShape OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),
            VoxelShapes.union(
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 2.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 14.0, 16.0, 1.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 0.0, 2.0, 1.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(14.0, 0.0, 0.0, 16.0, 1.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(4.0, 0.0, 2.0, 12.0, 1.0, 14.0),
                    AbstractCauldronBlock.createCuboidShape(2.0, 0.0, 4.0, 14.0, 1.0, 12.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 1.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 15.0, 16.0, 2.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 0.0, 1.0, 2.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(15.0, 0.0, 0.0, 16.0, 2.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(1.0, 2.0, 1.0, 15.0, 13.0, 15.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 13.0, 0.0, 16.0, 15.0, 1.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 13.0, 15.0, 16.0, 15.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 13.0, 0.0, 1.0, 15.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(15.0, 13.0, 0.0, 16.0, 15.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(1.0, 15.0, 1.0, 15.0, 16.0, 15.0),
                    RAYCAST_SHAPE), BooleanBiFunction.ONLY_FIRST);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return RAYCAST_SHAPE;
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.get(LEVEL) == 12;
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return (6.0 + (double) state.get(LEVEL) * 3.0) / 16.0;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(LEVEL);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL).add(CRYSTAL).add(CRUX);

    }


    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getMainHandStack();
        CauldronBehavior cauldronBehavior = this.behaviorMap.get(itemStack.getItem());
        if (itemStack.getItem() == ModItems.PENEBRIUM_SPORE_BUCKET
                || itemStack.getItem() == ModItems.CRUX
                || itemStack.getItem() == Items.BUCKET
        ) {
            return cauldronBehavior.interact(state, world, pos, player, hand, itemStack);
        }
        if (itemStack.isIn(ModTags.Items.CRYSTALS)) {
            if (!world.isClient()) {


                int k = crystalItemList.indexOf(player.getMainHandStack().getItem());
                CrystalType l = crystalStateArray[k];


                player.getMainHandStack().decrement(1);

                if (state.get(CRYSTAL) != CrystalType.NONE) {
                    int i = crystalStateList.indexOf(state.get(CRYSTAL));
                    Item j = crystalItemArray[i];

                    ItemStack outputItem = new ItemStack(j, 1);
                    boolean gaveItem = player.giveItemStack(outputItem);
                    if (!gaveItem) {
                        player.dropItem(outputItem, true);
                    }
                }
                world.playSound(null, pos, SoundEvents.BLOCK_MOSS_STEP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                BlockState blockState1 = state.with(CRYSTAL, l);
                world.setBlockState(pos, blockState1);
            }


            return ItemActionResult.success(world.isClient);
        }

        NbtComponent nbtComponent = itemStack.get(DataComponentTypes.CUSTOM_DATA);

        if (nbtComponent != null) {
            if(nbtComponent.getNbt().get("sculk_depths.crystal") != null){ //TODO replace getNbt since it's deprecated
                    return ItemActionResult.FAIL;
            }
        }

        if ((itemStack.getItem() == ModItems.QUAZARITH_HELMET
                && state.get(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_helmet_spore_cost
                && state.get(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_helmet_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)
                || (itemStack.getItem() == ModItems.QUAZARITH_HELMET//========================================
                && state.get(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_helmet_spore_cost
                && state.get(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_helmet_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)) {
            world.playSound(null, pos, SoundEvents.BLOCK_MOSS_STEP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, world, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_helmet_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_helmet_spore_cost);
            return ItemActionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((itemStack.getItem() == ModItems.QUAZARITH_CHESTPLATE
                && state.get(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_chestplate_spore_cost
                && state.get(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_chestplate_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)
                || (itemStack.getItem() == ModItems.QUAZARITH_CHESTPLATE//========================================
                && state.get(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_chestplate_spore_cost
                && state.get(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_chestplate_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)) {
            world.playSound(null, pos, SoundEvents.BLOCK_MOSS_STEP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, world, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_chestplate_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_chestplate_spore_cost);
            return ItemActionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((itemStack.getItem() == ModItems.QUAZARITH_LEGGINGS
                && state.get(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_leggings_spore_cost
                && state.get(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_leggings_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)
                || (itemStack.getItem() == ModItems.QUAZARITH_LEGGINGS//========================================
                && state.get(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_leggings_spore_cost
                && state.get(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_leggings_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)) {
            world.playSound(null, pos, SoundEvents.BLOCK_MOSS_STEP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, world, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_leggings_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_leggings_spore_cost);
            return ItemActionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((itemStack.getItem() == ModItems.QUAZARITH_BOOTS
                && state.get(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_boots_spore_cost
                && state.get(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_boots_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)
                || (itemStack.getItem() == ModItems.QUAZARITH_BOOTS//========================================
                && state.get(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_boots_spore_cost
                && state.get(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_boots_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)) {
            world.playSound(null, pos, SoundEvents.BLOCK_MOSS_STEP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, world, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_boots_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_boots_spore_cost);
            return ItemActionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((itemStack.getItem() == ModItems.QUAZARITH_SHOVEL
                && state.get(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_shovel_spore_cost
                && state.get(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_shovel_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)
                || (itemStack.getItem() == ModItems.QUAZARITH_SHOVEL//========================================
                && state.get(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_shovel_spore_cost
                && state.get(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_shovel_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)) {
            world.playSound(null, pos, SoundEvents.BLOCK_MOSS_STEP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, world, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_shovel_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_shovel_spore_cost);
            return ItemActionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((itemStack.getItem() == ModItems.QUAZARITH_PICKAXE
                && state.get(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_pickaxe_spore_cost
                && state.get(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_pickaxe_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)
                || (itemStack.getItem() == ModItems.QUAZARITH_PICKAXE//========================================
                && state.get(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_pickaxe_spore_cost
                && state.get(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_pickaxe_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)) {
            world.playSound(null, pos, SoundEvents.BLOCK_MOSS_STEP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, world, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_pickaxe_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_pickaxe_spore_cost);
            return ItemActionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((itemStack.getItem() == ModItems.QUAZARITH_AXE
                && state.get(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_axe_spore_cost
                && state.get(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_axe_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)
                || (itemStack.getItem() == ModItems.QUAZARITH_AXE//========================================
                && state.get(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_axe_spore_cost
                && state.get(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_axe_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)) {
            world.playSound(null, pos, SoundEvents.BLOCK_MOSS_STEP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, world, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_axe_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_axe_spore_cost);
            return ItemActionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((itemStack.getItem() == ModItems.QUAZARITH_HOE
                && state.get(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_hoe_spore_cost
                && state.get(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_hoe_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)
                || (itemStack.getItem() == ModItems.QUAZARITH_HOE//========================================
                && state.get(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_hoe_spore_cost
                && state.get(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_hoe_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)) {
            world.playSound(null, pos, SoundEvents.BLOCK_MOSS_STEP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, world, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_hoe_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_hoe_spore_cost);
            return ItemActionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((itemStack.getItem() == ModItems.QUAZARITH_SWORD
                && state.get(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_sword_spore_cost
                && state.get(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_sword_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)
                || (itemStack.getItem() == ModItems.QUAZARITH_SWORD//========================================
                && state.get(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_sword_spore_cost
                && state.get(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_sword_crux_cost
                && state.get(CRYSTAL) != CrystalType.NONE)) {
            world.playSound(null, pos, SoundEvents.BLOCK_MOSS_STEP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, world, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_sword_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_sword_spore_cost);
            return ItemActionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }

        return ItemActionResult.FAIL;

    }

    public void RemoveUsedResources(BlockState state, World world, BlockPos pos, int crux, int spore) {
        int j = state.get(CRUX_LEVEL) - crux;
        int k = state.get(LEVEL) - spore;

        if (k == 0) {
            BlockState newBlockState = state.with(CRUX_LEVEL, j).with(CRYSTAL, CrystalType.NONE);
            world.setBlockState(pos, newBlockState);
            world.setBlockState(pos, ModBlocks.FLUMROCK_CAULDRON.getDefaultState());
            return;
        }
        BlockState newBlockState = state.with(CRUX_LEVEL, j).with(LEVEL, k).with(CRYSTAL, CrystalType.NONE);
        world.setBlockState(pos, newBlockState);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock() && !moved) {
            DefaultedList<ItemStack> stacks = DefaultedList.ofSize(2, ItemStack.EMPTY);
            stacks.set(0, new ItemStack(ModItems.CRUX, state.get(CRUX)));

            if (state.get(CRYSTAL) != CrystalType.NONE) {
                int i = crystalStateList.indexOf(state.get(CRYSTAL));
                Item j = crystalItemArray[i];

                stacks.set(1, new ItemStack(j, 1));
            }


            ItemScatterer.spawn(world, pos, stacks);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));

        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

}
