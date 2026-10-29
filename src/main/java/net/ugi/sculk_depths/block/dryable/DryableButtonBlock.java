package net.ugi.sculk_depths.block.dryable;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.ugi.sculk_depths.world.dimension.ModDimensions;

public class DryableButtonBlock extends ButtonBlock implements Dryable {
    public DryableButtonBlock(BlockSetType type, int ticksToStayPressed, Settings settings) {
        super(type, ticksToStayPressed, settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }
    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (Mth.getInt(random, 0, 100) == 0) {
            if(world.dimensionTypeRegistration().getKey().get() == ModDimensions.SCULK_DEPTHS_TYPE) {

                Dryable.DryBlock(state, world, pos);
            }
        }
    }
}