package net.ugi.sculk_depths.sound;

import com.mojang.datafixers.util.Either;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.ugi.sculk_depths.world.biome.ModBiomes;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ConditionalSoundPlayerClient implements ClientTickEvents.StartWorldTick {
    public static double windAngleRad = Math.PI/4;
    public static double windX = 0.57f*Math.sin(windAngleRad);
    public static double windZ = 0.57f*Math.cos(windAngleRad);

    RandomSource r = new RandomSource() {
        @Override
        public RandomSource split() {
            return null;
        }

        @Override
        public PositionalRandomFactory forkPositional() {
            return null;
        }

        @Override
        public void initSeed(long seed) {

        }

        @Override
        public int getInt() {
            return 0;
        }

        @Override
        public int getInt(int bound) {
            return 0;
        }

        @Override
        public long getLong() {
            return 0;
        }

        @Override
        public boolean nextBoolean() {
            return false;
        }

        @Override
        public float getFloat() {
            return 0;
        }

        @Override
        public double nextDouble() {
            return 0;
        }

        @Override
        public double normal() {
            return 0;
        }
    };

    long start = 0;

    @Override
    public void onStartTick(ClientLevel world) {
        Player player = SoundPlayerGetterClient.player;
        BlockPos pos = player.getBlockPos();

        if (start == 0) start = world.getTime();


        if(world.getTime() < start + 20) {
            return;
        };
        if (start < world.getTime() - 40) start = world.getTime();
        start += 20;

        if (!(ModBiomes.DRIED_FOREST == world.getNoiseBiome(pos).getKey().get()) && !(ModBiomes.PETRIFIED_FOREST == world.getNoiseBiome(pos).getKey().get())) return;


        CalculateWindAngle(world, player, pos);
        if(Math.random() > 0.2) return;

        if (ModBiomes.DRIED_FOREST == world.getNoiseBiome(pos).getKey().get()) {
            if(Math.random() > 0.2 ) return;
            playSound(world, player, pos);
        }

        if (ModBiomes.PETRIFIED_FOREST == world.getNoiseBiome(pos).getKey().get()) {
            if(Math.random() > 0.6 ) return;
            playSound(world, player, pos);
        }

    }

    private void playSound(ClientLevel world, Player player, BlockPos pos) {
        if(pos.getY() >= world.getTopY(Heightmap.Types.MOTION_BLOCKING, pos.getX(), pos.getZ())){
            player.playSound(ModSounds.AMBIENT_WIND_ADDITIONS_EVENT, 1,1);
        }else {
            player.playSound(ModSounds.AMBIENT_WIND_ADDITIONS_EVENT, 0.4f,0.6f);
        }
    }
    private void CalculateWindAngle(ClientLevel world, Player player, BlockPos pos) {
        //todo maybe check for dimension (only if it's faster!!)
        Holder<NormalNoise.NoiseParameters> noiseParam = new Holder<NormalNoise.NoiseParameters>() {
            @Override
            public NormalNoise.NoiseParameters value() {
                return null;
            }

            @Override
            public boolean isBound() {
                return false;
            }

            @Override
            public boolean is(ResourceLocation id) {
                return false;
            }

            @Override
            public boolean matchesKey(ResourceKey<NormalNoise.NoiseParameters> name) {
                return false;
            }

            @Override
            public boolean matches(Predicate<ResourceKey<NormalNoise.NoiseParameters>> predicate) {
                return false;
            }

            @Override
            public boolean is(TagKey<NormalNoise.NoiseParameters> of) {
                return false;
            }

            @Override
            public boolean matches(Holder<NormalNoise.NoiseParameters> holder) {
                return false;
            }

            @Override
            public Stream<TagKey<NormalNoise.NoiseParameters>> streamTags() {
                return Stream.allEmpty();
            }

            @Override
            public Either<ResourceKey<NormalNoise.NoiseParameters>, NormalNoise.NoiseParameters> unwrap() {
                return null;
            }

            @Override
            public Optional<ResourceKey<NormalNoise.NoiseParameters>> getKey() {
                return Optional.allEmpty();
            }

            @Override
            public Type getType() {
                return null;
            }

            @Override
            public boolean canSerializeIn(HolderOwner<NormalNoise.NoiseParameters> owner) {
                return false;
            }
        };

        RandomSource radRandom = world.getRandom();
        RandomSource speedRandom = world.getRandom();

        radRandom.initSeed(1);
        speedRandom.initSeed(2);


        DensityFunction.NoiseHolder radNoise = new DensityFunction.NoiseHolder(noiseParam, NormalNoise.create(radRandom, new NormalNoise.NoiseParameters(-7, 1,2,3,2,4,2.4,9)));
        DensityFunction.NoiseHolder speedNoise = new DensityFunction.NoiseHolder(noiseParam, NormalNoise.create(speedRandom, new NormalNoise.NoiseParameters(-7, 1,2,3,2,4,2.4,9)));
        double rad = radNoise.data(player.getX()/75d, world.getTime()/600d, player.getZ()/75d)*Math.PI;
        double speed = speedNoise.data(player.getX()/75d, world.getTime()/300d, player.getZ()/75d);

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
