package net.ugi.sculk_depths.block.custom.ModCauldron;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;

public class FlumrockCauldronBlock extends AbstractCauldronBlock {


    public FlumrockCauldronBlock(Settings settings) {
        super(settings, ModCauldronBehavior.EMPTY_FLUMROCK_CAULDRON_BEHAVIOR);
    }
    @Override
    public boolean isFull(BlockState state) {
        return false;
    }


}

