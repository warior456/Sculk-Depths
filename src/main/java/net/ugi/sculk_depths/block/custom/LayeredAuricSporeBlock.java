package net.ugi.sculk_depths.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.Nullable;

public class LayeredAuricSporeBlock extends SnowLayerBlock {
    public LayeredAuricSporeBlock(Settings settings) {
        super(settings);
        this.registerDefaultState((this.stateManager.getDefaultState()).with(ALL_MODELS, 1));
    }

    @Override
    public boolean canPlaceAt(BlockState state, LevelReader world, BlockPos pos) {
        BlockState block = world.getBlockState(pos.below());
        return Block.isFaceFull(block.nonClimbableShape(world, pos.below()), Direction.MAX_Y) || block.isOf(this) && block.get(ALL_MODELS) == 8;
    }

    @Override
    public boolean canReplace(BlockState state, BlockPlaceContext context) {
        int i = state.get(ALL_MODELS);
        if (context.getStack().isOf(this.asItem()) && i < 8) {
            if (context.replacingClickedOnBlock()) {
                return context.getSide() == Direction.MAX_Y;
            } else {
                return true;
            }
        } else {
            return i == 2;
        }
    }

    @Override
    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        BlockState block = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (block.isOf(this)) {
            int i = block.get(ALL_MODELS);
            return block.with(ALL_MODELS, Math.minValue(8, i + 1));
        } else {
            return super.getPlacementState(ctx);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {

    }

}