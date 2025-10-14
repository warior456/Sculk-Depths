package net.ugi.sculk_depths.block.custom.ModCauldron;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.component.ComponentMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
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
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.item.QuazarithRecipe;
import net.ugi.sculk_depths.state.property.ModProperties;

import java.util.Map;

import static net.ugi.sculk_depths.state.property.ModProperties.CRUX_LEVEL;
import static net.ugi.sculk_depths.state.property.ModProperties.QUAZARITH_LEVEL;

public class KryslumFlumrockCauldronBlock extends AbstractCauldronBlock {
    public static final IntProperty LEVEL = ModProperties.KRYSLUM_LEVEL;
    public static final IntProperty QUAZARITH = ModProperties.QUAZARITH_LEVEL;
    public static final IntProperty CRUX = ModProperties.CRUX_LEVEL;
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
                    RAYCAST_SHAPE
            ), BooleanBiFunction.ONLY_FIRST);

    public KryslumFlumrockCauldronBlock(AbstractBlock.Settings settings) {
        super(settings, ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR);
        this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 1));
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
        return null;
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return new ItemStack(ModBlocks.FLUMROCK_CAULDRON);
    }

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
        return (6.0 + state.get(LEVEL) * 3.0) / 16.0;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient && entity.isOnFire() && this.isEntityTouchingFluid(state, pos, entity)) {

        }
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(LEVEL);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL).add(QUAZARITH).add(CRUX);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getMainHandStack();

        for (QuazarithRecipe quazarithRecipe : SculkDepths.config.quazarithRecipes) {
            if (itemStack.isOf(quazarithRecipe.input())
                    && state.get(LEVEL) >= quazarithRecipe.kryslumCost()
                    && state.get(CRUX) >= quazarithRecipe.cruxCost()
                    && state.get(QUAZARITH) >= quazarithRecipe.quazarithPiecesCost()) {
                world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, SoundCategory.BLOCKS, 0.2f, 1.0f);
                removeUsedResources(state, world, pos,
                        quazarithRecipe.quazarithPiecesCost(),
                        quazarithRecipe.cruxCost(),
                        quazarithRecipe.kryslumCost());
                return upgradeItem(new ItemStack(quazarithRecipe.result()), player);
            }
        }

        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    public ItemActionResult upgradeItem(ItemStack outputItem, PlayerEntity player) {
        ComponentMap componentMap = player.getMainHandStack().getComponents();
        if (componentMap != null) {
            outputItem.applyComponentsFrom(componentMap);
        }
        player.setStackInHand(Hand.MAIN_HAND, outputItem);
        return ItemActionResult.SUCCESS;
    }

    public void removeUsedResources(BlockState state, World world, BlockPos pos, int quazarithPiecesCost, int cruxCost, int kryslumCost) {
        int quazarithLeft = state.get(QUAZARITH_LEVEL) - quazarithPiecesCost;
        int cruxLeft = state.get(CRUX_LEVEL) - cruxCost;
        int kryslumLeft = state.get(LEVEL) - kryslumCost;

        if (kryslumLeft == 0) {
            BlockState newBlockState = state.with(QUAZARITH_LEVEL, quazarithLeft).with(CRUX_LEVEL, cruxLeft);
            world.setBlockState(pos, newBlockState);
            world.setBlockState(pos, ModBlocks.FLUMROCK_CAULDRON.getDefaultState());
            return;
        }

        BlockState newBlockState = state.with(QUAZARITH_LEVEL, quazarithLeft).with(CRUX_LEVEL, cruxLeft).with(LEVEL, kryslumLeft);
        world.setBlockState(pos, newBlockState);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock() && !moved) {
            DefaultedList<ItemStack> stacks = DefaultedList.ofSize(2, ItemStack.EMPTY);
            stacks.set(0, new ItemStack(ModItems.QUAZARITH_PIECES, state.get(QUAZARITH)));
            stacks.set(1, new ItemStack(ModItems.CRUX, state.get(CRUX)));
            ItemScatterer.spawn(world, pos, stacks);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
