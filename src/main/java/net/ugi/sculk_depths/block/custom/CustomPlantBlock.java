package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.ugi.sculk_depths.block.ModBlocks;

public class CustomPlantBlock extends PlantBlock implements Fertilizable {
    public static final MapCodec<ShortPlantBlock> CODEC = createCodec(ShortPlantBlock::new);
    protected static final float field_31261 = 6.0F;
    protected static final VoxelShape SHAPE = Block.createCuboidShape((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)13.0F, (double)14.0F);

    public MapCodec<ShortPlantBlock> getCodec() {
        return CODEC;
    }

    public CustomPlantBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        TallPlantBlock tallPlantBlock = (TallPlantBlock)(state.isOf(Blocks.FERN) ? Blocks.LARGE_FERN : Blocks.TALL_GRASS);
        if (tallPlantBlock.getDefaultState().canPlaceAt(world, pos) && world.isAir(pos.up())) {
            TallPlantBlock.placeAt(world, tallPlantBlock.getDefaultState(), pos, 2);
        }

    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND) || floor.isOf(ModBlocks.CRUMBLING_DIRT);
    }
}