package net.ugi.sculk_depths.block.dryable;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.ugi.sculk_depths.world.dimension.ModDimensions;

public class DryableStairsBlock extends StairsBlock implements Dryable {
    public DryableStairsBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (MathHelper.nextInt(random, 0, 100) == 0) {
            if(world.getDimensionKey() == ModDimensions.SCULK_DEPTHS_TYPE) {
                Dryable.DryBlock(state, world, pos);
            }
        }
    }
}
