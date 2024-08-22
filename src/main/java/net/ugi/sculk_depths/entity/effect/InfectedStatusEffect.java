package net.ugi.sculk_depths.entity.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.ugi.sculk_depths.util.ModDamageTypes;

public class InfectedStatusEffect extends StatusEffect {
    protected InfectedStatusEffect(StatusEffectCategory category, Integer color) {
        super(category, color);
    }

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return  true;
    }

    @Override
    // This method is called when it applies the status effect.
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity.getWorld().getTime()%40 != 0){
            return true;
        }
        entity.damage(ModDamageTypes.of(entity.getWorld(), ModDamageTypes.INFECTED_DAMAGE_TYPE), 0.75f);//TODO custom damagesource
        return true;
    }
}
