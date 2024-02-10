package net.ugi.sculk_depths.block.dryable;

import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.ugi.sculk_depths.world.dimension.ModDimensions;

public class DryableDoorBlock extends DoorBlock implements Dryable {
    public DryableDoorBlock(Settings settings, BlockSetType blockSetType) {
        super(settings, blockSetType);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(HALF) == DoubleBlockHalf.LOWER){
            if (MathHelper.nextInt(random, 0, 100) == 0) {
                if(world.getDimensionKey() == ModDimensions.SCULK_DEPTHS_TYPE) {

                    Dryable.DryBlock(state, world, pos);
                }
            }
        }
    }
}