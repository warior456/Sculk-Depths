package net.ugi.sculk_depths.block.dryable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.dimension.ModDimensions;

public class DryablePillarBlock extends PillarBlock implements Dryable {
    public DryablePillarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (MathHelper.nextInt(random, 0, 100) == 0) {
            if (world.getDimensionKey() == ModDimensions.SCULK_DEPTHS_TYPE) {

                if (state.getBlock() == ModBlocks.VALTROX_LOG || state.getBlock() == ModBlocks.VALTROX_WOOD) {
                    int livingBlocks = -1;
                    int driedBlocks = 0;
                    for (BlockPos blockPos : BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
                        if (world.getBlockState(blockPos).getBlock() == ModBlocks.VALTROX_LOG || world.getBlockState(blockPos).getBlock() == ModBlocks.VALTROX_WOOD ||world.getBlockState(blockPos).getBlock() == ModBlocks.VALTROX_LEAVES) {
                            livingBlocks += 1;
                        }
                        if (world.getBlockState(blockPos).getBlock() == ModBlocks.DRIED_VALTROX_LOG || world.getBlockState(blockPos).getBlock() == ModBlocks.DRIED_VALTROX_WOOD) {
                            driedBlocks += 1;
                        }

                    }
                    if (livingBlocks <= 1 ) {
                        Dryable.DryBlock(state, world, pos);
                    }
                    if(driedBlocks >= 1 && livingBlocks > 1) {
                        for (int l = driedBlocks; l >= 0; --l) {
                            if (MathHelper.nextInt(random, 0, 10) == 0) {
                                Dryable.DryBlock(state, world, pos);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
