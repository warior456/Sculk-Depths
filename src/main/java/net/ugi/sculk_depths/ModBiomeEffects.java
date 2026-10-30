package net.ugi.sculk_depths;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.ugi.sculk_depths.entity.effect.ModStatusEffects;
import net.ugi.sculk_depths.world.biome.ModBiomes;

import java.util.List;

public class ModBiomeEffects {

    public void onStartTick(ServerLevel level) {
        if (level.getGameTime() % 10 != 0) {
            return;
        }
        List<ServerPlayer> detectedPlayers = level.getPlayers(LivingEntity::isAlive);
        for (ServerPlayer player : detectedPlayers) {
            if (level.getBiome(player.blockPosition()).is(ModBiomes.INFECTED_COLUMNS)) {
                player.addEffect(new MobEffectInstance(ModStatusEffects.INFECTED, 115, 0, true, true, true));
            }
        }
    }
}
