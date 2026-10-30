package net.ugi.sculk_depths.entity.effect;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.ugi.sculk_depths.util.ModDamageTypes;

public class InfectedStatusEffect extends MobEffect {
    protected InfectedStatusEffect(MobEffectCategory category, Integer setColor) {
        super(category, setColor);
    }

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    @Override
    // This method is called when it applies the status effect.
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if(entity.level().getGameTime() % 40 != 0){
            return true;
        }
        entity.hurt(ModDamageTypes.of(entity.level(), ModDamageTypes.INFECTED_DAMAGE_TYPE), 0.75f);//TODO custom damagesource
        return true;
    }
}
