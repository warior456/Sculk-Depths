package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.ugi.sculk_depths.block.ModBlocks;

public class AuricVinesBlock extends AbstractPlantBlock {
    @Override
    protected MapCodec<? extends AbstractPlantBlock> getCodec() {
        return null;
    }
    public static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public AuricVinesBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.DOWN, SHAPE, false);
    }

    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ModBlocks.AURIC_VINES_END;
    }
}