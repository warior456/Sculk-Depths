package net.ugi.sculk_depths.sound;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.ugi.sculk_depths.world.biome.ModBiomes;

public class ConditionalSoundPlayerClient {
    public static double windAngleRad = Math.PI / 4;
    public static double windX = 0.57f * Math.sin(windAngleRad);
    public static double windZ = 0.57f * Math.cos(windAngleRad);

    RandomSource r = RandomSource.create();

    long start = 0;

    public void onStartTick(ClientLevel level) {
        Player player = SoundPlayerGetterClient.player;
        if (player == null) return;
        BlockPos pos = player.blockPosition();

        if (start == 0) start = level.getGameTime();

        if (level.getGameTime() < start + 20) {
            return;
        }
        if (start < level.getGameTime() - 40) start = level.getGameTime();
        start += 20;

        boolean isDriedForest = level.getBiome(pos).is(ModBiomes.DRIED_FOREST);
        boolean isPetrifiedForest = level.getBiome(pos).is(ModBiomes.PETRIFIED_FOREST);

        if (!isDriedForest && !isPetrifiedForest) return;

        CalculateWindAngle(level, player, pos);
        if (Math.random() > 0.2) return;

        if (isDriedForest) {
            if (Math.random() > 0.2) return;
            playSound(level, player, pos);
        }

        if (isPetrifiedForest) {
            if (Math.random() > 0.6) return;
            playSound(level, player, pos);
        }
    }

    private void playSound(ClientLevel level, Player player, BlockPos pos) {
        if (pos.getY() >= level.getHeight(Heightmap.Types.MOTION_BLOCKING, pos.getX(), pos.getZ())) {
            player.playSound(ModSounds.AMBIENT_WIND_ADDITIONS_EVENT, 1, 1);
        } else {
            player.playSound(ModSounds.AMBIENT_WIND_ADDITIONS_EVENT, 0.4f, 0.6f);
        }
    }

    private void CalculateWindAngle(ClientLevel level, Player player, BlockPos pos) {
        NormalNoise.NoiseParameters params = new NormalNoise.NoiseParameters(-7, 1, 2, 3, 2, 4, 2.4, 9);
        Holder<NormalNoise.NoiseParameters> noiseParam = Holder.direct(params);

        RandomSource radRandom = RandomSource.create(1);
        RandomSource speedRandom = RandomSource.create(2);

        DensityFunction.NoiseHolder radNoise = new DensityFunction.NoiseHolder(noiseParam, NormalNoise.create(radRandom, params));
        DensityFunction.NoiseHolder speedNoise = new DensityFunction.NoiseHolder(noiseParam, NormalNoise.create(speedRandom, params));
        double rad = radNoise.getValue(player.getX() / 75d, level.getGameTime() / 600d, player.getZ() / 75d) * Math.PI;
        double speed = speedNoise.getValue(player.getX() / 75d, level.getGameTime() / 300d, player.getZ() / 75d);

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
