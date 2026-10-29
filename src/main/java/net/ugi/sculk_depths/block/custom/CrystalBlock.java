package net.ugi.sculk_depths.block.custom;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.item.DyeColor;

public class CrystalBlock
        extends TransparentBlock
        implements BeaconBeamBlock {
    private final DyeColor setColor;

    public CrystalBlock(DyeColor setColor, BlockBehaviour.Properties settings) {
        super(settings);
        this.setColor = setColor;
    }

    @Override
    public DyeColor getColor() {
        return this.setColor;
    }
}


