package net.ugi.sculk_depths.world.gen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class CountOnEveryLayerConstant extends PlacementModifier {
    private final IntProvider count;
    private final IntProvider layers;

    private final IntProvider startY;
    private final IntProvider separation;

    public static final MapCodec<CountOnEveryLayerConstant> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            IntProvider.CODEC.fieldOf("count").forGetter(c -> c.count),
            IntProvider.POSITIVE_CODEC.fieldOf("layers").forGetter(c -> c.layers),
            IntProvider.CODEC.fieldOf("start_y").forGetter(c -> c.startY),
            IntProvider.CODEC.fieldOf("separation").forGetter(c -> c.separation)
    ).apply(instance, CountOnEveryLayerConstant::new));

    public CountOnEveryLayerConstant(IntProvider count, IntProvider layers, IntProvider startY, IntProvider separation) {
        this.count = count;
        this.layers = layers;
        this.startY = startY;
        this.separation = separation;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
        int startY = this.startY.sample(random);

        Stream.Builder<BlockPos> builder = Stream.builder();

        for (int i = 0; i < this.layers.sample(random); i++) {
            int layerY = startY - (i * this.separation.sample(random));

            for (int j = 0; j < this.count.sample(random); j++) {
                int x = pos.getX();
                int z = pos.getZ();

                builder.add(new BlockPos(x, layerY, z));
            }
        }
        return builder.build();
    }

    @Override
    public PlacementModifierType<?> type() {
        return ModPlacementModifierType.COUNT_ON_EVERY_LAYER_CONSTANT;
    }

    private static int findOnGroundYPosition(PlacementContext context, int x, int y, int z, int targetY) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(x, y, z);
        int i = 0;
        BlockState block = context.getBlockState(mutable);
        for (int j = y; j >= context.getMinBuildHeight() + 1; --j) {
            mutable.setY(j - 1);
            BlockState blockState2 = context.getBlockState(mutable);
            if (!isEmpty(blockState2) && isEmpty(block) && !blockState2.is(Blocks.BEDROCK)) {
                if (i == targetY) {
                    return mutable.getY() + 1;
                }
                ++i;
            }
            block = blockState2;
        }
        return Integer.MIN_VALUE;
    }

    private static boolean isEmpty(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) || state.is(Blocks.LAVA);
    }
}
