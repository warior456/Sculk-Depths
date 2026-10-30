package net.ugi.sculk_depths.block.dryable;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.ugi.sculk_depths.world.dimension.ModDimensions;

public class DryableWallHangingSignBlock extends WallHangingSignBlock implements Dryable {
    public DryableWallHangingSignBlock(WoodType woodType, BlockBehaviour.Properties properties) {
        super(woodType, properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (Mth.nextInt(random, 0, 100) == 0) {
            if(level.dimensionTypeRegistration().is(ModDimensions.SCULK_DEPTHS_TYPE)) {

                Dryable.DryBlock(state, level, pos);
            }
        }
    }
}
