package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class ShroomPlantBlock
        extends MushroomBlock
        implements BonemealableBlock {

    public static final MapCodec<ShroomPlantBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    ResourceKey.codec(Registries.CONFIGURED_FEATURE).fieldOf("feature").forGetter(b -> b.feature),
                    propertiesCodec()
            ).apply(instance, ShroomPlantBlock::new)
    );

    protected static final float field_31195 = 3.0f;
    protected static final VoxelShape SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);
    private final ResourceKey<ConfiguredFeature<?, ?>> feature;

    public ShroomPlantBlock(BlockBehaviour.Properties properties, ResourceKey<ConfiguredFeature<?, ?>> feature) {
        super(feature, properties);
        this.feature = feature;
    }

    public ShroomPlantBlock(ResourceKey<ConfiguredFeature<?, ?>> feature, BlockBehaviour.Properties properties) {
        super(feature, properties);
        this.feature = feature;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MapCodec<MushroomBlock> codec() {
        return (MapCodec<MushroomBlock>) (MapCodec<?>) CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(25) == 0) {
            int i = 5;
            int j = 4;
            for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-4, -1, -4), pos.offset(4, 1, 4))) {
                if (!level.getBlockState(blockPos).is(this) || --i > 0) continue;
                return;
            }
            BlockPos blockPos2 = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
            for (int k = 0; k < 4; ++k) {
                if (level.isEmptyBlock(blockPos2) && state.canSurvive(level, blockPos2)) {
                    pos = blockPos2;
                }
                blockPos2 = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
            }
            if (level.isEmptyBlock(blockPos2) && state.canSurvive(level, blockPos2)) {
                level.setBlock(blockPos2, state, Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState pointingUp, BlockGetter level, BlockPos pos) {
        return pointingUp.isSolidRender(level, pos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockPos = pos.below();
        BlockState block = level.getBlockState(blockPos);
        if (block.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return true;
        }
        return level.getRawBrightness(pos, 0) < 13 && this.mayPlaceOn(block, level, blockPos);
    }

    @Override
    public boolean growMushroom(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        Optional<? extends Holder<ConfiguredFeature<?, ?>>> isOptIn = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(this.feature);
        if (isOptIn.isEmpty()) {
            return false;
        }
        level.destroyBlock(pos, false);
        if (isOptIn.get().value().place(level, level.getChunkSource().getGenerator(), random, pos)) {
            return true;
        }
        level.setBlock(pos, state, Block.UPDATE_ALL);
        return false;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return (double) random.nextFloat() < 0.4;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        this.growMushroom(level, pos, state, random);
    }
}
