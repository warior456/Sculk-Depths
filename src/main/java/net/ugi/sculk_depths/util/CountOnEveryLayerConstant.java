package net.ugi.sculk_depths.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.stream.Stream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.CountMultilayerPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

public class CountOnEveryLayerConstant
        extends PlacementModifier {
    public static final Codec<CountOnEveryLayerConstant> MODIFIER_CODEC = IntProvider.createValidatingCodec(0, 256).fieldOf("count").xmap(CountOnEveryLayerConstant::new, CountOnEveryLayerConstant -> CountOnEveryLayerConstant.count).codec();
    private final IntProvider count;

    private CountOnEveryLayerConstant(IntProvider count) {
        this.count = count;
    }

    public static CountOnEveryLayerConstant of(IntProvider count) {
        return new CountOnEveryLayerConstant(count);
    }

    public static CountOnEveryLayerConstant of(int count) {
        return CountOnEveryLayerConstant.of(ConstantIntProvider.create(count));
    }

    @Override
    public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {
        Stream.Builder<BlockPos> builder = Stream.builder();
        for (int j = 0; j < 25; ++j) {
            int k = pos.getX();
            int n = j* -10;
            int l = pos.getZ();
            builder.add(new BlockPos(k, n, l));

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

