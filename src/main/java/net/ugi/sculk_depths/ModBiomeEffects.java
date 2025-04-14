package net.ugi.sculk_depths;


import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.*;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.entity.effect.ModStatusEffects;
import net.ugi.sculk_depths.world.biome.ModBiomes;

import java.util.List;

public class ModBiomeEffects implements ServerTickEvents.StartWorldTick{

    @Override
    public void onStartTick(ServerWorld world) {
            if(world.getTime()%10 != 0){
                return;
            }
            List<ServerPlayerEntity> players = world.getPlayers(LivingEntity::isAlive);
            for (ServerPlayerEntity player : players) {
                if (world.getBiome(player.getBlockPos()).getKey().get() == ModBiomes.INFECTED_COLUMNS) {
                    player.addStatusEffect(new StatusEffectInstance(ModStatusEffects.INFECTED, 115, 0, true, true, true));
                }
            }
        }
}
