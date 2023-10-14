package net.ugi.sculk_depths.sound;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class SoundPlayerGetterClient implements ClientTickEvents.StartTick {
    public static PlayerEntity player;

    @Override
    public void onStartTick(MinecraftClient client) {
        player = client.player;
    }

    public PlayerEntity getPlayer(){
        return player;
    }

}
