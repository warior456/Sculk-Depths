package net.ugi.sculk_depths.sound;

import com.mojang.datafixers.util.Either;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryOwner;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.RandomSplitter;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.ugi.sculk_depths.world.biome.ModBiomes;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ConditionalSoundPlayerClient implements ClientTickEvents.StartWorldTick {
    public static double windAngleRad = Math.PI/4;
    public static double windX = 0.57f*Math.sin(windAngleRad);
    public static double windZ = 0.57f*Math.cos(windAngleRad);

    Random r = new Random() {
        @Override
        public Random split() {
            return null;
        }

        @Override
        public RandomSplitter nextSplitter() {
            return null;
        }

        @Override
        public void setSeed(long seed) {

        }

        @Override
        public int nextInt() {
            return 0;
        }

        @Override
        public int nextInt(int bound) {
            return 0;
        }

        @Override
        public long nextLong() {
            return 0;
        }

        @Override
        public boolean nextBoolean() {
            return false;
        }

        @Override
        public float nextFloat() {
            return 0;
        }

        @Override
        public double nextDouble() {
            return 0;
        }

        @Override
        public double nextGaussian() {
            return 0;
        }
    };

    long start = 0;

    @Override
    public void onStartTick(ClientWorld world) {
        PlayerEntity player = SoundPlayerGetterClient.player;
        BlockPos pos = player.getBlockPos();

        if (start == 0) start = world.getTime();


        if(world.getTime() < start + 20) {
            return;
        };
        if (start < world.getTime() - 40) start = world.getTime();
        start += 20;

        if (!(ModBiomes.DRIED_FOREST == world.getBiome(pos).getKey().get()) && !(ModBiomes.PETRIFIED_FOREST == world.getBiome(pos).getKey().get())) return;


        CalculateWindAngle(world, player, pos);
        if(Math.random() > 0.2) return;

        if (ModBiomes.DRIED_FOREST == world.getBiome(pos).getKey().get()) {
            if(Math.random() > 0.2 ) return;
            playSound(world, player, pos);
        }

        if (ModBiomes.PETRIFIED_FOREST == world.getBiome(pos).getKey().get()) {
            if(Math.random() > 0.6 ) return;
            playSound(world, player, pos);
        }

    }

    private void playSound(ClientWorld world, PlayerEntity player, BlockPos pos) {
        if(pos.getY() >= world.getTopY(Heightmap.Type.MOTION_BLOCKING, pos.getX(), pos.getZ())){
            player.playSound(ModSounds.AMBIENT_WIND_ADDITIONS_EVENT, 1,1);
        }else {
            player.playSound(ModSounds.AMBIENT_WIND_ADDITIONS_EVENT, 0.4f,0.6f);
        }
    }
    private void CalculateWindAngle(ClientWorld world, PlayerEntity player, BlockPos pos) {
        //todo maybe check for dimension (only if it's faster!!)
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

        Random radRandom = world.getRandom();
        Random speedRandom = world.getRandom();

        radRandom.setSeed(1);
        speedRandom.setSeed(2);


        DensityFunction.Noise radNoise = new DensityFunction.Noise(noiseParam, DoublePerlinNoiseSampler.create(radRandom, new DoublePerlinNoiseSampler.NoiseParameters(-7, 1,2,3,2,4,2.4,9)));
        DensityFunction.Noise speedNoise = new DensityFunction.Noise(noiseParam, DoublePerlinNoiseSampler.create(speedRandom, new DoublePerlinNoiseSampler.NoiseParameters(-7, 1,2,3,2,4,2.4,9)));
        System.out.println(world.getTime());
        double rad = radNoise.sample(player.getX()/75d, world.getTime()/600d, player.getZ()/75d)*Math.PI;
        double speed = speedNoise.sample(player.getX()/75d, world.getTime()/300d, player.getZ()/75d);

        windX = speed*Math.sin(rad);
        windZ = speed*Math.cos(rad);
    }
    public static float getWindX(){
        return (float) windX;
    }
    public static float getWindZ(){
        return (float) windZ;
    }
}
