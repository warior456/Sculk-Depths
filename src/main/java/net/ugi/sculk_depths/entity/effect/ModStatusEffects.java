package net.ugi.sculk_depths.entity.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder;
import net.ugi.sculk_depths.SculkDepths;

public class ModStatusEffects {

    public static final Holder<MobEffect> INFECTED = registerStatusEffect("infected",
            new InfectedStatusEffect(MobEffectCategory.HARMFUL, 0xCFD88B));//13621387

    private static Holder<MobEffect> registerStatusEffect(String name, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, SculkDepths.id(name), effect);
    }

    public static void init(){

    }
}
