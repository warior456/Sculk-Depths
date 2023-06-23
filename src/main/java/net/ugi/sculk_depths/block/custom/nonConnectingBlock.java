package net.ugi.sculk_depths.block.custom;

import net.minecraft.block.*;


public class nonConnectingBlock extends Block {


    public nonConnectingBlock(Settings settings) {
        super(settings);
    }

    //@Override
    public static boolean cannotConnect(BlockState state) {
        return true;
    }
}
