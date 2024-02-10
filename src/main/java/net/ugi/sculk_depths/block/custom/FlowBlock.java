package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.ugi.sculk_depths.state.property.ModProperties;

public class FlowBlock extends FacingBlock {
    @Override
    protected MapCodec<? extends FacingBlock> getCodec() {
        return null;
    }

    protected static final float field_31233 = 6.0f;
    protected static final float field_31234 = 10.0f;
    protected static final VoxelShape Y_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape Z_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape X_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    public FlowBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP).with(ModProperties.KRYSLUM_POWERED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING).getAxis()) {
            default: {
                return X_SHAPE;
            }
            case Z: {
                return Z_SHAPE;
            }
            case Y:
        }
        return Y_SHAPE;
    }


    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {//todo check for kryslum on placing
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(ModProperties.KRYSLUM_POWERED);
    }
}
