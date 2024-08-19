package net.ugi.sculk_depths.block.dryable;

import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ButtonBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.ugi.sculk_depths.world.dimension.ModDimensions;

public class DryableButtonBlock extends ButtonBlock implements Dryable {
    public DryableButtonBlock(BlockSetType blockSetType, int pressTicks, Settings settings) {
        super(blockSetType, pressTicks, settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (MathHelper.nextInt(random, 0, 100) == 0) {
            if(world.getDimensionEntry().getKey().get() == ModDimensions.SCULK_DEPTHS_TYPE) {

                Dryable.DryBlock(state, world, pos);
            }
        }
    }
}