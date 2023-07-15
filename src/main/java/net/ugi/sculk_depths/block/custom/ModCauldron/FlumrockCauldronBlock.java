package net.ugi.sculk_depths.block.custom.ModCauldron;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.ugi.sculk_depths.block.entity.FlumrockCauldronBlockEntity;

public class FlumrockCauldronBlock extends AbstractCauldronBlock implements BlockEntityProvider {


    public FlumrockCauldronBlock(Settings settings) {
        super(settings, ModCauldronBehavior.EMPTY_FLUMROCK_CAULDRON_BEHAVIOR);
    }
    @Override
    public boolean isFull(BlockState state) {
        return false;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FlumrockCauldronBlockEntity(pos, state);
    }

}

