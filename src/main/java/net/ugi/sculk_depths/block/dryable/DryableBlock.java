package net.ugi.sculk_depths.block.dryable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.dimension.ModDimensions;

public class DryableBlock extends Block implements Dryable {
    public DryableBlock(Settings settings) {
        super(settings);

    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        /*if(world.getDimension() == ModDimensions.SCULK_DEPTHS_TYPE){*/
            SculkDepths.LOGGER.info("e");
            Dryable.DryBlock(state, world, pos, random, this.asBlock());
        //}
    }
}
