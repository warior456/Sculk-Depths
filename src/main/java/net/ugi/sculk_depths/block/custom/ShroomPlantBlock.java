package net.ugi.sculk_depths.block.custom;

import net.minecraft.block.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class ShroomPlantBlock
        extends MushroomPlantBlock
        implements Fertilizable {
    protected static final float field_31195 = 3.0f;
    protected static final VoxelShape SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);
    private final ResourceKey<ConfiguredFeature<?, ?>> feature;


    public ShroomPlantBlock(Settings settings, ResourceKey<ConfiguredFeature<?, ?>> feature) {
        super(feature, settings);
        this.feature = feature;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.getInt(25) == 0) {
            int i = 5;
            int j = 4;
            for (BlockPos blockPos : BlockPos.iterate(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
                if (!world.getBlockState(blockPos).isOf(this) || --i > 0) continue;
                return;
            }
            BlockPos blockPos2 = pos.add(random.getInt(3) - 1, random.getInt(2) - random.getInt(2), random.getInt(3) - 1);
            for (int k = 0; k < 4; ++k) {
                if (world.isAir(blockPos2) && state.canPlaceAt(world, blockPos2)) {
                    pos = blockPos2;
                }
                blockPos2 = pos.add(random.getInt(3) - 1, random.getInt(2) - random.getInt(2), random.getInt(3) - 1);
            }
            if (world.isAir(blockPos2) && state.canPlaceAt(world, blockPos2)) {
                world.setBlockState(blockPos2, state, Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState pointingUp, BlockGetter world, BlockPos pos) {
        return pointingUp.isSolidRender(world, pos);
    }

    @Override
    public boolean canPlaceAt(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.below();
        BlockState block = world.getBlockState(blockPos);
        if (block.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return true;
        }
        return world.getRawBrightness(pos, 0) < 13 && this.mayPlaceOn(block, world, blockPos);
    }

    public boolean growMushroom(ServerLevel world, BlockPos pos, BlockState state, RandomSource random) {
        Optional<Holder.Reference<ConfiguredFeature<?, ?>>> isOptIn = world.getRegistryManager().get(Registries.CONFIGURED_FEATURE).getEntry(this.feature);
        if (isOptIn.isEmpty()) {
            return false;
        }
        world.destroyBlock(pos, false);
        if (((ConfiguredFeature)((Holder)isOptIn.get()).value()).generate(world, world.getChunkSource().getChunkGenerator(), random, pos)) {
            return true;
        }
        world.setBlockState(pos, state, Block.UPDATE_ALL);
        return false;
    }


    @Override
    public boolean canGrow(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return (double)random.getFloat() < 0.4;
    }

    @Override
    public void grow(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        this.growMushroom(world, pos, state, random);
    }
}

