package net.ugi.sculk_depths.block.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Stainable;
import net.minecraft.util.DyeColor;

public class CrystalBlock
        extends AbstractGlassBlock
        implements Stainable {
    private final DyeColor color;

    public CrystalBlock(DyeColor color, AbstractBlock.Settings settings) {
        super(settings);
        this.color = color;
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }
}


