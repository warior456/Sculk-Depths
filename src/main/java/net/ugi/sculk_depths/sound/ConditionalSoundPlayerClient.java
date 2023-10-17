package net.ugi.sculk_depths.sound;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.ugi.sculk_depths.world.biome.ModBiomes;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Mod;

import java.util.List;
import java.util.Random;

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
            player.playSound(ModSounds.SCULK_DEPTHS_PETRIFIED_FOREST_ADDITIONS_EVENT, 1,1);
        }else {
            player.playSound(ModSounds.SCULK_DEPTHS_PETRIFIED_FOREST_ADDITIONS_EVENT, 0.4f,0.6f);
        }
    }
}
