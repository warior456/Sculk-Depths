package net.ugi.sculk_depths.sound;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.ugi.sculk_depths.world.biome.ModBiomes;

public class ConditionalSoundPlayerClient implements ClientTickEvents.StartWorldTick {
    public static double windAngleRad = Math.PI/4;
    public static double windX = 0.57f*Math.sin(windAngleRad);
    public static double windZ = 0.57f*Math.cos(windAngleRad);

    @Override
    public void onStartTick(ClientWorld world) {
        PlayerEntity player = SoundPlayerGetterClient.player;
        BlockPos pos = player.getBlockPos();

        if(Math.random() > 0.01) return;

        //todo maybe check for dimension (only if it's faster!!)
        windAngleRad=CalculateWindAngle(world);
        windX = 0.57f*Math.sin(windAngleRad);
        windZ = 0.57f*Math.cos(windAngleRad);

        if (ModBiomes.DRIED_FOREST == world.getBiome(pos).getKey().get()) {
            if(Math.random() > 0.2 ) return;
            playSound(world, player, pos);
        }

        if (ModBiomes.PETRIFIED_FOREST == world.getBiome(pos).getKey().get()) {
            if(Math.random() > 0.6 ) return;
            playSound(world, player, pos);
        }
        System.out.println(windAngleRad);
    }

    private void playSound(ClientWorld world, PlayerEntity player, BlockPos pos) {
        if(pos.getY() >= world.getTopY(Heightmap.Type.MOTION_BLOCKING, pos.getX(), pos.getZ())){
            player.playSound(ModSounds.AMBIENT_WIND_ADDITIONS_EVENT, 1,1);
        }else {
            player.playSound(ModSounds.AMBIENT_WIND_ADDITIONS_EVENT, 0.4f,0.6f);
        }
    }
    private double CalculateWindAngle(ClientWorld world){
        world.getLunarTime();
        //or
        world.getMoonPhase();

        world.getTime();
        float timeOfDay = world.getTimeOfDay();
        return timeOfDay/2000;
        //return Math.PI/4;
    }
    public static float getWindX(){
        return (float) windX;
    }
    public static float getWindZ(){
        return (float) windZ;
    }
}
