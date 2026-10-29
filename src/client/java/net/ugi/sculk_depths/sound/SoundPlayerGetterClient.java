package net.ugi.sculk_depths.sound;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class SoundPlayerGetterClient implements ClientTickEvents.StartTick {
    public static Player player;

    @Override
    public void onStartTick(Minecraft client) {
        player = client.player;
    }

}
