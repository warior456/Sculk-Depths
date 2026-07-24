package net.ugi.sculk_depths.sound;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.ugi.sculk_depths.world.biome.ModBiomes;

public class ConditionalSoundPlayerClient implements ClientTickEvents.StartWorldTick {
    public static double windAngleRad = Math.PI / 4;
    public static double windX = 0.57f * Math.sin(windAngleRad);
    public static double windZ = 0.57f * Math.cos(windAngleRad);

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

        if (!(world.getBiome(pos).matchesKey(ModBiomes.DRIED_FOREST)) && !(ModBiomes.PETRIFIED_FOREST == world.getBiome(pos).getKey().get())) return;


        if (Math.random() > 0.2) return;
        calculateWindAngle(world, player, pos);

        if (world.getBiome(pos).matchesKey(ModBiomes.DRIED_FOREST)) {
            if (Math.random() > 0.2) return;
            playSound(world, player, pos);
        }

        if (world.getBiome(pos).matchesKey(ModBiomes.PETRIFIED_FOREST)) {
            if (Math.random() > 0.6) return;
            playSound(world, player, pos);
        }

    }

    private void playSound(ClientWorld world, PlayerEntity player, BlockPos pos) {
        if (pos.getY() >= world.getTopY(Heightmap.Type.MOTION_BLOCKING, pos.getX(), pos.getZ())) {
            player.playSound(ModSounds.AMBIENT_WIND_ADDITIONS_EVENT, 1,1);
        } else {
            player.playSound(ModSounds.AMBIENT_WIND_ADDITIONS_EVENT, 0.4f,0.6f);
        }
    }
    private void calculateWindAngle(ClientWorld world, PlayerEntity player, BlockPos pos) {
        //todo maybe check for dimension (only if it's faster!!)
        RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> noiseParam = RegistryEntry.of(null);
//        Registry<DoublePerlinNoiseSampler.NoiseParameters> noiseParametersRegistry = world.getRegistryManager().get(RegistryKeys.NOISE_PARAMETERS);
//        noiseParametersRegistry.get(NoiseParametersKeys.)

        Random radRandom = world.getRandom();
        Random speedRandom = world.getRandom();

        radRandom.setSeed(1);
        speedRandom.setSeed(2);


        DensityFunction.Noise radNoise = new DensityFunction.Noise(noiseParam, DoublePerlinNoiseSampler.create(radRandom, new DoublePerlinNoiseSampler.NoiseParameters(-7, 1,2,3,2,4,2.4,9)));
        DensityFunction.Noise speedNoise = new DensityFunction.Noise(noiseParam, DoublePerlinNoiseSampler.create(speedRandom, new DoublePerlinNoiseSampler.NoiseParameters(-7, 1,2,3,2,4,2.4,9)));
        System.out.println(world.getTime());
        double rad = radNoise.sample(player.getX() / 75d, world.getTime() / 600d, player.getZ() / 75d) * Math.PI;
        double speed = speedNoise.sample(player.getX() / 75d, world.getTime() / 300d, player.getZ() / 75d);

        windX = speed * Math.sin(rad);
        windZ = speed * Math.cos(rad);
    }

    public static float getWindX() {
        return (float) windX;
    }

    public static float getWindZ() {
        return (float) windZ;
    }
}
