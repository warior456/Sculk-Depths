package net.ugi.sculk_depths.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.Arrays;

import static net.ugi.sculk_depths.tags.ModTags.Blocks.SCULK_VEINS_REPLACABLE;

public class SculkVeins extends Feature<NoneFeatureConfiguration> {
    static double n1 = (2.0/45.0)*(4.0-49.0/2.0);
    static double n2 = (2.0/45.0)*(6.0-49.0/2.0);
    static double n3 = (2.0/45.0)*(9.0-49.0/2.0);
    static double n4 = (2.0/45.0)*(15.0-49.0/2.0);
    static double n5 = (2.0/45.0)*(17.0-49.0/2.0);
    static double n6 = (2.0/45.0)*(23.0-49.0/2.0);
    static double n7 = (2.0/45.0)*(25.0-49.0/2.0);
    static double n8 = (2.0/45.0)*(31.0-49.0/2.0);
    static double n9 = (2.0/45.0)*(34.0-49.0/2.0);
    static double n10 = (2.0/45.0)*(42.0-49.0/2.0);
    static double n11 = (2.0/45.0)*(45.0-49.0/2.0);
    static double n12 = (2.0/45.0)*(49.0-49.0/2.0);

    private boolean check(double width, double offset){
        return width < n1+offset || width > n2-offset && width < n3+offset || width > n4-offset && width < n5+offset || width > n6-offset && width < n7+offset || width > n8-offset && width < n9+offset || width > n10-offset && width < n11+offset || width > n12-offset;
    }

    protected static boolean hasAnyDirection(BlockState state) {
        return Arrays.stream(Direction.values()).anyMatch(direction -> MultifaceBlock.hasFace(state, direction));
    }

    private final Holder<NormalNoise.NoiseParameters> noiseParam = Holder.direct(new NormalNoise.NoiseParameters(-7, 1, 0.5, 1.1, 1.5, 0, 1));

    public SculkVeins(Codec<NoneFeatureConfiguration> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos blockPos = context.origin();
        RandomSource random = context.random();
        WorldGenLevel structureWorldAccess = context.level();
        ChunkAccess chunk = structureWorldAccess.getChunk(blockPos);

        random.setSeed(structureWorldAccess.getSeed());
        DensityFunction.NoiseHolder sculkVeinNoise = new DensityFunction.NoiseHolder(noiseParam, NormalNoise.create(random, new NormalNoise.NoiseParameters(-7, 1,0.5,1.1,1.5,0,1)));

        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();

        int h = -y+50;

        BlockState block = Blocks.SCULK_VEIN.defaultBlockState();

        int chunkMaxHeight = y;
        for (int i = 0; i < 20; i++){
            for (int k = 0; k < 20; k++){
                int height = structureWorldAccess.getChunk(new BlockPos(x + i-2, y, z + k-2)).getHeight(Heightmap.Types.WORLD_SURFACE_WG, (i+14)%16, (k+14)%16);
                if (height > chunkMaxHeight) chunkMaxHeight = height;
            }
        }
        chunkMaxHeight -= y;
        if (chunkMaxHeight > h) chunkMaxHeight = h;

        boolean[][][] IsAirArray = new boolean[20][chunkMaxHeight+4][20];
        for (int i = 0; i < 20; i++){
            for (int k = 0; k < 20; k++){
                int height = structureWorldAccess.getChunk(new BlockPos(x + i-2, y, z + k-2)).getHeight(Heightmap.Types.WORLD_SURFACE_WG, (i+14)%16, (k+14)%16);
                for (int j = 0; j < chunkMaxHeight+4; j++){
                    if (y+j > height+2) {
                        IsAirArray[i][j][k] = false;
                        continue;
                    }
                    IsAirArray[i][j][k] = structureWorldAccess.getBlockState(new BlockPos(x + i-2, y+j-2, z + k-2)).is(Blocks.AIR);
                }
            }
        }

        boolean[][][] CaveBorders = new boolean[18][chunkMaxHeight+2][18];
        for (int i = 0; i < 18; i++){
            for (int k = 0; k < 18; k++){
                int height = structureWorldAccess.getChunk(new BlockPos(x + i-1, y, z + k-1)).getHeight(Heightmap.Types.WORLD_SURFACE_WG, (i+15)%16, (k+15)%16);
                for (int j = 0; j < chunkMaxHeight+2; j++){
                    if (y+j > height+1) {
                        CaveBorders[i][j][k] = false;
                        continue;
                    }
                    if (!(IsAirArray[i+2][j+1][k+1] && IsAirArray[i+1][j+2][k+1] && IsAirArray[i+1][j+1][k+2] && IsAirArray[i][j+1][k+1] && IsAirArray[i+1][j][k+1] && IsAirArray[i+1][j+1][k])){
                        if (IsAirArray[i+2][j+1][k+1] || IsAirArray[i+1][j+2][k+1] || IsAirArray[i+1][j+1][k+2] || IsAirArray[i][j+1][k+1] || IsAirArray[i+1][j][k+1] || IsAirArray[i+1][j+1][k]){
                            CaveBorders[i][j][k] = true;
                            continue;
                        }
                    }
                    CaveBorders[i][j][k] = false;
                }
            }
        }

        double[][][] sculkVeinNoiseSamples = new double[18][chunkMaxHeight+2][18];
        for (int i = 0; i < 18; i++){
            for (int k = 0; k < 18; k++){
                int height = structureWorldAccess.getChunk(new BlockPos(x + i-1, y, z + k-1)).getHeight(Heightmap.Types.WORLD_SURFACE_WG, (i+15)%16, (k+15)%16);
                for (int j = 0; j < chunkMaxHeight+2; j++){
                    if (y+j > height+1) break;
                    if (!CaveBorders[i][j][k]) continue;
                    sculkVeinNoiseSamples[i][j][k] = sculkVeinNoise.getValue(x + i, y + j, z + k);
                }
            }
        }

        for (int i = 0; i < 16; i++){
            for (int k = 0; k < 16; k++){
                int height = structureWorldAccess.getChunk(blockPos).getHeight(Heightmap.Types.WORLD_SURFACE_WG, i, k);

                for (int j = 0; j < chunkMaxHeight; j++){
                    if (y+j > height) break;
                    if (!CaveBorders[i+1][j+1][k+1]) continue;
                    if (!check(sculkVeinNoiseSamples[i+1][j+1][k+1], 0.02)) continue;
                    BlockPos blockPos1 = new BlockPos(x + i, y + j, z + k);

                    BlockPos blockPosUp = new BlockPos(x + i, y + j + 1, z + k);
                    BlockPos blockPosDown = new BlockPos(x + i, y + j - 1, z + k);
                    BlockPos blockPosNorth = new BlockPos(x + i, y + j, z + k - 1);
                    BlockPos blockPosWest = new BlockPos(x + i - 1, y + j, z + k);
                    BlockPos blockPosEast = new BlockPos(x + i + 1, y + j, z + k);
                    BlockPos blockPosSouth = new BlockPos(x + i, y + j, z + k + 1);

                    if (chunk.getBlockState(blockPos1).is(Blocks.AIR)){
                        block = block
                                .setValue(BlockStateProperties.UP, !check(sculkVeinNoiseSamples[i+1][j+2][k+1], 0) && structureWorldAccess.getBlockState(blockPosUp).is(SCULK_VEINS_REPLACABLE))
                                .setValue(BlockStateProperties.NORTH, !check(sculkVeinNoiseSamples[i+1][j+1][k], 0) && structureWorldAccess.getBlockState(blockPosNorth).is(SCULK_VEINS_REPLACABLE))
                                .setValue(BlockStateProperties.EAST, !check(sculkVeinNoiseSamples[i+2][j+1][k+1], 0) && structureWorldAccess.getBlockState(blockPosEast).is(SCULK_VEINS_REPLACABLE))
                                .setValue(BlockStateProperties.SOUTH, !check(sculkVeinNoiseSamples[i+1][j+1][k+2], 0) && structureWorldAccess.getBlockState(blockPosSouth).is(SCULK_VEINS_REPLACABLE))
                                .setValue(BlockStateProperties.WEST, !check(sculkVeinNoiseSamples[i][j+1][k+1], 0) && structureWorldAccess.getBlockState(blockPosWest).is(SCULK_VEINS_REPLACABLE))
                                .setValue(BlockStateProperties.DOWN, !check(sculkVeinNoiseSamples[i+1][j][k+1], 0) && structureWorldAccess.getBlockState(blockPosDown).is(SCULK_VEINS_REPLACABLE));

                        if (!hasAnyDirection(block)) continue;

                        structureWorldAccess.setBlock(blockPos1, block, 2);
                        continue;
                    }

                    if (!chunk.getBlockState(blockPos1).is(SCULK_VEINS_REPLACABLE)) continue;
                    if (!check(sculkVeinNoiseSamples[i+1][j+1][k+1], 0)) continue;

                    structureWorldAccess.setBlock(blockPos1, Blocks.SCULK.defaultBlockState(), 2);
                }
            }
        }
        return true;
    }
}
