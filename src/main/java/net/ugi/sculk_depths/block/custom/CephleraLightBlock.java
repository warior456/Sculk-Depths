package net.ugi.sculk_depths.block.custom;


import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.ugi.sculk_depths.block.ModBlocks;

public class CephleraLightBlock extends AbstractPlantStemBlock {
    @Override
    protected MapCodec<? extends AbstractPlantStemBlock> getCodec() {
        return null;
    }

    protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public CephleraLightBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.MIN_Y, SHAPE, false, 0.1);
    }

    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return VineLogic.getBlocksToGrowWhenBonemealed(random);
    }

    protected Block getBodyBlock() {
        return ModBlocks.CEPHLERA;
    }

    protected boolean canGrowInto(BlockState state) {
        return VineLogic.isValidGrowthState(state);
    }
}
