package net.ugi.sculk_depths.world.gen;

import com.mojang.serialization.Codec;

import java.util.stream.Stream;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

public class CountOnEveryLayerConstant
        extends PlacementModifier {

    public static final Codec<CountOnEveryLayerConstant> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IntProvider.VALUE_CODEC.fieldOf("count").forGetter(c -> c.count),
            IntProvider.POSITIVE_CODEC.fieldOf("layers").forGetter(c -> c.layers),
            IntProvider.VALUE_CODEC.fieldOf("start_y").forGetter(c -> c.startY),
            IntProvider.VALUE_CODEC.fieldOf("separation").forGetter(c -> c.separation)
    ).apply(instance, CountOnEveryLayerConstant::new));
    private final IntProvider count;
    private final IntProvider layers;

    private final IntProvider startY;
    private final IntProvider separation;

    public CountOnEveryLayerConstant(IntProvider count, IntProvider layers, IntProvider startY, IntProvider separation) {
        this.count = count;
        this.layers = layers;
        this.startY = startY;
        this.separation = separation;
    }





    @Override
    public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {

        int startY = this.startY.get(random);

        Stream.Builder<BlockPos> builder = Stream.builder();

        for(int i = 0; i < this.layers.get(random); i++) {
            int layerY = startY - (i * this.separation.get(random));

            for(int j = 0; j < this.count.get(random); j++) {
                int x = pos.getX();
                int z = pos.getZ();

                builder.add(new BlockPos(x, layerY, z));
            }
        }
        return builder.build();
    }

    @Override
    public PlacementModifierType<?> getType() {
        return ModPlacementModifierType.COUNT_ON_EVERY_LAYER_CONSTANT;
    }

    private static int findPos(FeaturePlacementContext context, int x, int y, int z, int targetY) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);
        int i = 0;
        BlockState blockState = context.getBlockState(mutable);
        for (int j = y; j >= context.getBottomY() + 1; --j) {
            mutable.setY(j - 1);
            BlockState blockState2 = context.getBlockState(mutable);
            if (!blocksSpawn(blockState2) && blocksSpawn(blockState) && !blockState2.isOf(Blocks.BEDROCK)) {
                if (i == targetY) {
                    return mutable.getY() + 1;
                }
                ++i;
            }
            blockState = blockState2;
        }
        return Integer.MAX_VALUE;
    }

    private static boolean blocksSpawn(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER) || state.isOf(Blocks.LAVA);
    }
}

