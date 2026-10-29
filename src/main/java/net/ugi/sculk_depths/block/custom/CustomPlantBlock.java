package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.ugi.sculk_depths.block.ModBlocks;

public class CustomPlantBlock extends PlantBlock implements Fertilizable {
    public static final MapCodec<ShortPlantBlock> CODEC = createCodec(ShortPlantBlock::new);
    protected static final float field_31261 = 6.0F;
    protected static final VoxelShape SHAPE = Block.box((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)13.0F, (double)14.0F);

    public MapCodec<ShortPlantBlock> getCodec() {
        return CODEC;
    }

    public CustomPlantBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return true;
    }

    public boolean canGrow(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        TallPlantBlock tallPlantBlock = (TallPlantBlock)(state.isOf(Blocks.FERN) ? Blocks.LARGE_FERN : Blocks.TALL_GRASS);
        if (tallPlantBlock.getDefaultState().canPlaceAt(world, pos) && world.isAir(pos.above())) {
            TallPlantBlock.generateIcebergBlock(world, tallPlantBlock.getDefaultState(), pos, 2);
        }

    }

    @Override
    protected boolean mayPlaceOn(BlockState pointingUp, BlockGetter world, BlockPos pos) {
        return pointingUp.is(BlockTags.DIRT) || pointingUp.isOf(Blocks.FARMLAND) || pointingUp.isOf(ModBlocks.CRUMBLING_DIRT);
    }
}