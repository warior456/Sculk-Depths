package net.ugi.sculk_depths.sound;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.ugi.sculk_depths.world.biome.ModBiomes;

public class ConditionalSoundPlayerClient implements ClientTickEvents.StartWorldTick {
    @Override
    public void onStartTick(ClientWorld world) {
        PlayerEntity player = SoundPlayerGetterClient.player;
        BlockPos pos = player.getBlockPos();

        if(Math.random() > 0.01) return;
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
}
