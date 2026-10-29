package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.ugi.sculk_depths.block.ModBlocks;

public class AuricVinesBlock extends AbstractPlantBlock {
    @Override
    protected MapCodec<? extends AbstractPlantBlock> getCodec() {
        return null;
    }
    public static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public AuricVinesBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.MIN_Y, SHAPE, false);
    }

    protected AbstractPlantStemBlock getHeadBlock() {
        return (AbstractPlantStemBlock) ModBlocks.AURIC_VINES_END;
    }
}