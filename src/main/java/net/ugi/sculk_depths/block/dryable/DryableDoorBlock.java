package net.ugi.sculk_depths.block.dryable;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.ugi.sculk_depths.world.dimension.ModDimensions;

public class DryableDoorBlock extends DoorBlock implements Dryable {
    public DryableDoorBlock(BlockSetType type, BlockBehaviour.Properties properties) {
        super(type, properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER){
            if (Mth.nextInt(random, 0, 100) == 0) {
                if(level.dimensionTypeRegistration().is(ModDimensions.SCULK_DEPTHS_TYPE)) {

                    Dryable.DryBlock(state, level, pos);
                }
            }
        }
    }
}