package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class LayeredAuricSporeBlock extends SnowLayerBlock {

    public static final MapCodec<LayeredAuricSporeBlock> CODEC = simpleCodec(LayeredAuricSporeBlock::new);

    public static final IntegerProperty LAYERS = SnowLayerBlock.LAYERS;
    public static final IntegerProperty ALL_MODELS = LAYERS;

    public LayeredAuricSporeBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LAYERS, 1));
    }

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<SnowLayerBlock> codec() {
        return (MapCodec<SnowLayerBlock>) (MapCodec<?>) CODEC;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState block = level.getBlockState(pos.below());
        return Block.isFaceFull(block.getCollisionShape(level, pos.below()), Direction.UP) || (block.is(this) && block.getValue(LAYERS) == 8);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        int i = state.getValue(LAYERS);
        if (context.getItemInHand().is(this.asItem()) && i < 8) {
            if (context.replacingClickedOnBlock()) {
                return context.getClickedFace() == Direction.UP;
            } else {
                return true;
            }
        } else {
            return i == 1;
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState block = ctx.getLevel().getBlockState(ctx.getClickedPos());
        if (block.is(this)) {
            int i = block.getValue(LAYERS);
            return block.setValue(LAYERS, Math.min(8, i + 1));
        } else {
            return super.getStateForPlacement(ctx);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

    }

}
