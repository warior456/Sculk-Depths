package net.ugi.sculk_depths.block.custom;


import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.ugi.sculk_depths.block.ModBlocks;

public class AuricVinesEndBlock extends GrowingPlantHeadBlock {
    @Override
    protected MapCodec<? extends GrowingPlantHeadBlock> getCodec() {
        return null;
    }

    protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public AuricVinesEndBlock(Settings settings) {
        super(settings, Direction.MIN_Y, SHAPE, false, 0.1);
    }

    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return NetherVines.getBlocksToGrowWhenBonemealed(random);
    }

    protected Block getBodyBlock() {
        return ModBlocks.AURIC_VINES;
    }

    protected boolean canGrowInto(BlockState state) {
        return NetherVines.isValidGrowthState(state);
    }
}
