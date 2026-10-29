package net.ugi.sculk_depths;


import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stat.*;
import net.ugi.sculk_depths.entity.effect.ModStatusEffects;
import net.ugi.sculk_depths.world.biome.ModBiomes;

import java.util.List;

public class ModBiomeEffects implements ServerTickEvents.StartWorldTick{

    @Override
    public void onStartTick(ServerLevel world) {
            if(world.getTime()%10 != 0){
                return;
            }
            List<ServerPlayer> detectedPlayers = world.getPlayers(LivingEntity::isAlive);
            for (ServerPlayer player : detectedPlayers) {
                if (world.getNoiseBiome(player.getBlockPos()).getKey().get() == ModBiomes.INFECTED_COLUMNS) {
                    player.addEffect(new MobEffectInstance(ModStatusEffects.INFECTED, 115, 0, true, true, true));
                }
            }
        }
}
