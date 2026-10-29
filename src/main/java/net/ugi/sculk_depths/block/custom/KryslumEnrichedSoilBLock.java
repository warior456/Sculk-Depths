package net.ugi.sculk_depths.block.custom;

import net.minecraft.block.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;

public class KryslumEnrichedSoilBLock extends FarmlandBlock {

    public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE;
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);
    public static final int MAX_MOISTURE = 7;

    public KryslumEnrichedSoilBLock(AbstractBlock.Settings settings) {
        super(settings);
        this.registerDefaultState((this.stateManager.getDefaultState()).with(MOISTURE, MAX_MOISTURE));
    }


    @Override
    public boolean canPlaceAt(BlockState state, LevelReader world, BlockPos pos) {
        return true;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.MAX_Y && !state.canPlaceAt(world, pos)) {
            world.scheduleBlockTick(pos, this, 1);
        }
        return super.updateShape(state, direction, state, world, pos, neighborPos);
    }


    @Override
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return this.getDefaultState();
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void scheduledTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
    }


    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
    }
}
