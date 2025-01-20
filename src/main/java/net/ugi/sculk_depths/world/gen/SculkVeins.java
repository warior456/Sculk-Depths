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


    RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> t = new RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters>() {
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



    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        
        random.setSeed(structureWorldAccess.getSeed());
        BlockPos blockPos = context.getOrigin();
        DensityFunction.Noise test = new DensityFunction.Noise(t, DoublePerlinNoiseSampler.create(random, new DoublePerlinNoiseSampler.NoiseParameters(-7, 1,0.5,1.1,1.5,0,1)));
        

        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();

        BlockState block = Blocks.SCULK_VEIN.getDefaultState();

        /*ServerChunkManager serverChunkManager = structureWorldAccess.getServer().getWorld(ModDimensions.SCULK_DEPTHS_LEVEL_KEY).getChunkManager();
        NoiseConfig noiseConfig = serverChunkManager.getNoiseConfig();
        DensityFunction noiseRouter = noiseConfig.getNoiseRouter().finalDensity();

        DensityFunction.Noise test2 = new DensityFunction.Noise(t, DoublePerlinNoiseSampler.create(random, noiseConfig.getOrCreateSampler(NoiseParametersKeys.CAVE_CHEESE).copy()));*/
        boolean[][][] IsAirArray = new boolean[18][302][18];
        for (int xx = 0;xx< 18;xx++){
            for (int yy = 0;yy< 302;yy++){
                for (int zz = 0;zz< 18;zz++){
                    IsAirArray[xx][yy][zz] = structureWorldAccess.getBlockState(new BlockPos(x + xx-1,y+yy-1,z + zz-1)).isOf(Blocks.AIR);
                }
            }
        }

        boolean[][][] onlyValues = new boolean[16][300][16];
        for (int xx = 0;xx< 16;xx++){
            for (int yy = 0;yy< 300;yy++){
                for (int zz = 0;zz< 16;zz++){
                    if (!(IsAirArray[xx+2][yy+1][zz+1] && IsAirArray[xx+1][yy+2][zz+1]  && IsAirArray[xx+1][yy+1][zz+2]  && IsAirArray[xx][yy+1][zz+1]  && IsAirArray[xx+1][yy][zz+1]  && IsAirArray[xx+1][yy+1][zz])){


                        if (IsAirArray[xx+2][yy+1][zz+1] || IsAirArray[xx+1][yy+2][zz+1]  || IsAirArray[xx+1][yy+1][zz+2]  || IsAirArray[xx][yy+1][zz+1]  || IsAirArray[xx+1][yy][zz+1]  || IsAirArray[xx+1][yy+1][zz]){
                            onlyValues[xx][yy][zz] = true;
                            continue;
                        }
                    }
                    onlyValues[xx][yy][zz] = false;
                }
            }
        }



        for (int xx = 0;xx< 16;xx++){
            for (int yy = 0;yy< 300;yy++){
                for (int zz = 0;zz< 16;zz++){

                    if (!onlyValues[xx][yy][zz]) continue;
                    BlockPos blockPos1 = new BlockPos(x + xx,y+yy,z + zz);

                    BlockPos blockPosUp = new BlockPos(x+xx,y+yy+1,z+zz); //todo faster than .up()????
                    BlockPos blockPosDown = new BlockPos(x+xx,y+yy-1,z+zz);
                    BlockPos blockPosNorth = new BlockPos(x+xx,y+yy,z+zz-1);
                    BlockPos blockPosWest = new BlockPos(x+xx-1,y+yy,z+zz);
                    BlockPos blockPosEast = new BlockPos(x+xx+1,y+yy,z+zz);
                    BlockPos blockPosSouth = new BlockPos(x+xx,y+yy,z+zz+1);

//                    if (((structureWorldAccess.getBlockState(blockPosUp).isOf(Blocks.AIR) /*|| structureWorldAccess.getBlockState(blockPosUp).isOf(Blocks.SCULK_VEIN)*/) &&
//                            (structureWorldAccess.getBlockState(blockPosNorth).isOf(Blocks.AIR) /*|| structureWorldAccess.getBlockState(blockPosNorth).isOf(Blocks.SCULK_VEIN)*/) &&
//                            (structureWorldAccess.getBlockState(blockPosEast).isOf(Blocks.AIR) /*|| structureWorldAccess.getBlockState(blockPosEast).isOf(Blocks.SCULK_VEIN)*/) &&
//                            (structureWorldAccess.getBlockState(blockPosSouth).isOf(Blocks.AIR) /*|| structureWorldAccess.getBlockState(blockPosSouth).isOf(Blocks.SCULK_VEIN)*/) &&
//                            (structureWorldAccess.getBlockState(blockPosWest).isOf(Blocks.AIR) /*|| structureWorldAccess.getBlockState(blockPosWest).isOf(Blocks.SCULK_VEIN)*/) &&
//                            (structureWorldAccess.getBlockState(blockPosDown).isOf(Blocks.AIR) /*|| structureWorldAccess.getBlockState(blockPosDown).isOf(Blocks.SCULK_VEIN)*/))) continue;
//
//                    if (!(structureWorldAccess.getBlockState(blockPosUp).isOf(Blocks.AIR) ||
//                            structureWorldAccess.getBlockState(blockPosNorth).isOf(Blocks.AIR) ||
//                            structureWorldAccess.getBlockState(blockPosEast).isOf(Blocks.AIR) ||
//                            structureWorldAccess.getBlockState(blockPosSouth).isOf(Blocks.AIR) ||
//                            structureWorldAccess.getBlockState(blockPosWest).isOf(Blocks.AIR) ||
//                            structureWorldAccess.getBlockState(blockPosDown).isOf(Blocks.AIR))) continue;

                    if (structureWorldAccess.getBlockState(blockPos1).isOf(Blocks.AIR)){


                        double n = test.sample(blockPos1.getX(),blockPos1.getY(),blockPos1.getZ());
                        if(!check(n,0.02))continue;


                        block = block
                                .with(Properties.UP,       /*!(structureWorldAccess.getBlockState(blockPosUp).isOf(Blocks.AIR) || structureWorldAccess.getBlockState(blockPosUp).isOf(Blocks.SCULK_VEIN)) &&*/ (!check(test.sample(blockPosUp.getX(),blockPosUp.getY(),blockPosUp.getZ()),0) /*&& !structureWorldAccess.getBlockState(blockPosUp).isOf(Blocks.AIR)*/) && structureWorldAccess.getBlockState(blockPosUp).isIn(SCULK_VEINS_REPLACABLE))
                                .with(Properties.NORTH,    /*!(structureWorldAccess.getBlockState(blockPosNorth).isOf(Blocks.AIR) || structureWorldAccess.getBlockState(blockPosNorth).isOf(Blocks.SCULK_VEIN)) &&*/ (!check(test.sample(blockPosNorth.getX(),blockPosNorth.getY(),blockPosNorth.getZ()),0) /*&& !structureWorldAccess.getBlockState(blockPosNorth).isOf(Blocks.AIR)*/) && structureWorldAccess.getBlockState(blockPosNorth).isIn(SCULK_VEINS_REPLACABLE))
                                .with(Properties.EAST,     /*!(structureWorldAccess.getBlockState(blockPosEast).isOf(Blocks.AIR) || structureWorldAccess.getBlockState(blockPosEast).isOf(Blocks.SCULK_VEIN)) &&*/ (!check(test.sample(blockPosEast.getX(),blockPosEast.getY(),blockPosEast.getZ()),0) /*&& !structureWorldAccess.getBlockState(blockPosEast).isOf(Blocks.AIR)*/) && structureWorldAccess.getBlockState(blockPosEast).isIn(SCULK_VEINS_REPLACABLE))
                                .with(Properties.SOUTH,    /*!(structureWorldAccess.getBlockState(blockPosSouth).isOf(Blocks.AIR) || structureWorldAccess.getBlockState(blockPosSouth).isOf(Blocks.SCULK_VEIN)) &&*/ (!check(test.sample(blockPosSouth.getX(),blockPosSouth.getY(),blockPosSouth.getZ()),0) /*&& !structureWorldAccess.getBlockState(blockPosSouth).isOf(Blocks.AIR)*/) && structureWorldAccess.getBlockState(blockPosSouth).isIn(SCULK_VEINS_REPLACABLE))
                                .with(Properties.WEST,     /*!(structureWorldAccess.getBlockState(blockPosWest).isOf(Blocks.AIR) || structureWorldAccess.getBlockState(blockPosWest).isOf(Blocks.SCULK_VEIN)) &&*/ (!check(test.sample(blockPosWest.getX(),blockPosWest.getY(),blockPosWest.getZ()),0) /*&& !structureWorldAccess.getBlockState(blockPosWest).isOf(Blocks.AIR)*/) && structureWorldAccess.getBlockState(blockPosWest).isIn(SCULK_VEINS_REPLACABLE))
                                .with(Properties.DOWN,     /*!(structureWorldAccess.getBlockState(blockPosDown).isOf(Blocks.AIR) || structureWorldAccess.getBlockState(blockPosDown).isOf(Blocks.SCULK_VEIN)) &&*/ (!check(test.sample(blockPosDown.getX(),blockPosDown.getY(),blockPosDown.getZ()),0) /*&& !structureWorldAccess.getBlockState(blockPosDown).isOf(Blocks.AIR)*/) && structureWorldAccess.getBlockState(blockPosDown).isIn(SCULK_VEINS_REPLACABLE));


                        if (!hasAnyDirection(block)) continue;


                        this.setBlockState(structureWorldAccess, blockPos1, block);
                        continue;

                    }


                    if (!structureWorldAccess.getBlockState(blockPos1).isIn(SCULK_VEINS_REPLACABLE)) continue;

                    double n = test.sample(blockPos1.getX(),blockPos1.getY(),blockPos1.getZ());
                    if(!check(n,0))continue;



                    this.setBlockState(structureWorldAccess, blockPos1, Blocks.SCULK.getDefaultState());

                }
            }
        }

//        Iterable<BlockPos> blockPosIterable = BlockPos.iterateOutwards(blockPos,8, 208,8);
//
//        blockPosIterable.forEach(blockPos1 -> {
//            if (!structureWorldAccess.getBlockState(blockPos1).isOf(Blocks.AIR)){
//                if (structureWorldAccess.getBlockState(blockPos1.up()).isOf(Blocks.AIR) ||
//                    structureWorldAccess.getBlockState(blockPos1.north()).isOf(Blocks.AIR) ||
//                    structureWorldAccess.getBlockState(blockPos1.east()).isOf(Blocks.AIR) ||
//                    structureWorldAccess.getBlockState(blockPos1.south()).isOf(Blocks.AIR) ||
//                    structureWorldAccess.getBlockState(blockPos1.west()).isOf(Blocks.AIR) ||
//                    structureWorldAccess.getBlockState(blockPos1.down()).isOf(Blocks.AIR)){
//                    double n = test.sample(blockPos1.getX(),blockPos1.getY(),blockPos1.getZ());
//                    if(check(n)){
//                    this.setBlockState(structureWorldAccess, blockPos1, Blocks.SCULK.getDefaultState());
//
//                    }
//                }
//            }
//
//        });
        return true;
    }
}
