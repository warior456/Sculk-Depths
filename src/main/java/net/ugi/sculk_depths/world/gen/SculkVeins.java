package net.ugi.sculk_depths.world.gen;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryOwner;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static net.minecraft.block.MultifaceGrowthBlock.hasDirection;
import static net.minecraft.world.RedstoneView.DIRECTIONS;
import static net.ugi.sculk_depths.tags.ModTags.Blocks.SCULK_VEINS_REPLACABLE;

public class SculkVeins extends Feature<DefaultFeatureConfig> {
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

    private boolean check(double n, double offset){
        return n < n1+offset || n > n2-offset && n < n3+offset || n > n4-offset && n < n5+offset || n > n6-offset && n < n7+offset || n > n8-offset && n < n9+offset || n > n10-offset && n < n11+offset || n > n12-offset;
    }

    protected static boolean hasAnyDirection(BlockState state) {
        return Arrays.stream(DIRECTIONS).anyMatch((direction) -> hasDirection(state, direction));
    }


    RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> noiseParam = new RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters>() {
        @Override
        public DoublePerlinNoiseSampler.NoiseParameters value() {
            return null;
        }

        @Override
        public boolean hasKeyAndValue() {
            return false;
        }

        @Override
        public boolean matchesId(Identifier id) {
            return false;
        }

        @Override
        public boolean matchesKey(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> key) {
            return false;
        }

        @Override
        public boolean matches(Predicate<RegistryKey<DoublePerlinNoiseSampler.NoiseParameters>> predicate) {
            return false;
        }

        @Override
        public boolean isIn(TagKey<DoublePerlinNoiseSampler.NoiseParameters> tag) {
            return false;
        }

        @Override
        public boolean matches(RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> entry) {
            return false;
        }

        @Override
        public Stream<TagKey<DoublePerlinNoiseSampler.NoiseParameters>> streamTags() {
            return Stream.empty();
        }

        @Override
        public Either<RegistryKey<DoublePerlinNoiseSampler.NoiseParameters>, DoublePerlinNoiseSampler.NoiseParameters> getKeyOrValue() {
            return null;
        }

        @Override
        public Optional<RegistryKey<DoublePerlinNoiseSampler.NoiseParameters>> getKey() {
            return Optional.empty();
        }

        @Override
        public Type getType() {
            return null;
        }

        @Override
        public boolean ownerEquals(RegistryEntryOwner<DoublePerlinNoiseSampler.NoiseParameters> owner) {
            return false;
        }
    };


    public SculkVeins(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }


    //boolean[][][] IsAirArray = new boolean[20][304][20]; todo : this better?
    //boolean[][][] CaveBorders = new boolean[18][302][18];
    //double[][][] sculkVeinNoiseSamples = new double[18][302][18];
    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Chunk chunk = structureWorldAccess.getChunk(blockPos);

        random.setSeed(structureWorldAccess.getSeed());
        DensityFunction.Noise sculkVeinNoise = new DensityFunction.Noise(noiseParam, DoublePerlinNoiseSampler.create(random, new DoublePerlinNoiseSampler.NoiseParameters(-7, 1,0.5,1.1,1.5,0,1)));
        

        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();

        BlockState block = Blocks.SCULK_VEIN.getDefaultState();

        boolean[][][] IsAirArray = new boolean[20][304][20];
        for (int i = 0;i< 20;i++){
            for (int j = 0;j< 304;j++){
                for (int k = 0;k< 20;k++){
                    IsAirArray[i][j][k] = structureWorldAccess.getBlockState(new BlockPos(x + i-2,y+j-2,z + k-2)).isOf(Blocks.AIR);
                }
            }
        }

        boolean[][][] CaveBorders = new boolean[18][302][18];
        for (int i = 0;i< 18;i++){
            for (int j = 0;j< 302;j++){
                for (int k = 0;k< 18;k++){

                    if (!(IsAirArray[i+2][j+1][k+1] && IsAirArray[i+1][j+2][k+1]  && IsAirArray[i+1][j+1][k+2]  && IsAirArray[i][j+1][k+1]  && IsAirArray[i+1][j][k+1]  && IsAirArray[i+1][j+1][k])){
                        if (IsAirArray[i+2][j+1][k+1] || IsAirArray[i+1][j+2][k+1]  || IsAirArray[i+1][j+1][k+2]  || IsAirArray[i][j+1][k+1]  || IsAirArray[i+1][j][k+1]  || IsAirArray[i+1][j+1][k]){

                            CaveBorders[i][j][k] = true;
                            continue;
                        }
                    }
                    CaveBorders[i][j][k] = false;
                }
            }
        }

        double[][][] sculkVeinNoiseSamples = new double[18][302][18];
        for (int i = 0;i< 18;i++){
            for (int j = 0;j< 302;j++){
                for (int k = 0;k< 18;k++){

                    if (!CaveBorders[i][j][k]) continue;
                    sculkVeinNoiseSamples[i][j][k] = sculkVeinNoise.sample(x + i,y+j,z + k);
                }
            }
        }



        for (int i = 0;i< 16;i++){
            for (int j = 0;j< 300;j++){
                for (int k = 0;k< 16;k++){

                    if (!CaveBorders[i+1][j+1][k+1]) continue;
                    if(!check(sculkVeinNoiseSamples[i+1][j+1][k+1],0.02))continue;
                    BlockPos blockPos1 = new BlockPos(x + i,y+j,z + k);

                    BlockPos blockPosUp = new BlockPos(x+i,y+j+1,z+k); //todo faster than .up()????
                    BlockPos blockPosDown = new BlockPos(x+i,y+j-1,z+k);
                    BlockPos blockPosNorth = new BlockPos(x+i,y+j,z+k-1);
                    BlockPos blockPosWest = new BlockPos(x+i-1,y+j,z+k);
                    BlockPos blockPosEast = new BlockPos(x+i+1,y+j,z+k);
                    BlockPos blockPosSouth = new BlockPos(x+i,y+j,z+k+1);



                    if (chunk.getBlockState(blockPos1).isOf(Blocks.AIR)){


                        block = block
                                .with(Properties.UP,        !check(sculkVeinNoiseSamples[i+1][j+2][k+1],0) && structureWorldAccess.getBlockState(blockPosUp).isIn(SCULK_VEINS_REPLACABLE))
                                .with(Properties.NORTH,     !check(sculkVeinNoiseSamples[i+1][j+1][k],0) && structureWorldAccess.getBlockState(blockPosNorth).isIn(SCULK_VEINS_REPLACABLE))
                                .with(Properties.EAST,      !check(sculkVeinNoiseSamples[i+2][j+1][k+1],0)  && structureWorldAccess.getBlockState(blockPosEast).isIn(SCULK_VEINS_REPLACABLE))
                                .with(Properties.SOUTH,     !check(sculkVeinNoiseSamples[i+1][j+1][k+2],0)  && structureWorldAccess.getBlockState(blockPosSouth).isIn(SCULK_VEINS_REPLACABLE))
                                .with(Properties.WEST,      !check(sculkVeinNoiseSamples[i][j+1][k+1],0)  && structureWorldAccess.getBlockState(blockPosWest).isIn(SCULK_VEINS_REPLACABLE))
                                .with(Properties.DOWN,      !check(sculkVeinNoiseSamples[i+1][j][k+1],0)  && structureWorldAccess.getBlockState(blockPosDown).isIn(SCULK_VEINS_REPLACABLE));


                        if (!hasAnyDirection(block)) continue;

                        this.setBlockState(structureWorldAccess, blockPos1, block);
                        continue;

                    }


                    if (!chunk.getBlockState(blockPos1).isIn(SCULK_VEINS_REPLACABLE)) continue;


                    if(!check(sculkVeinNoiseSamples[i+1][j+1][k+1],0))continue;



                    this.setBlockState(structureWorldAccess, blockPos1, Blocks.SCULK.getDefaultState());

                }
            }
        }
        return true;
    }
}
