package net.ugi.sculk_depths;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.ugi.sculk_depths.entity.effect.ModStatusEffects;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.world.biome.ModBiomes;

import java.util.ArrayList;
import java.util.List;

public class ModBiomeEffects implements ServerTickEvents.StartWorldTick{

    @Override
    public void onStartTick(ServerWorld world) {//todo should be replaced with an actual effect
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
