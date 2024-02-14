package net.ugi.sculk_depths.block.dryable;

import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.ugi.sculk_depths.world.dimension.ModDimensions;

public class DryableSignBlock extends SignBlock implements Dryable {
    public DryableSignBlock(WoodType woodType, Settings settings) {
        super(woodType, settings);
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
