package net.ugi.sculk_depths;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class test implements ServerLivingEntityEvents.AfterDeath{

    @Override
    public void afterDeath(LivingEntity entity, DamageSource damageSource) {

    }
}
