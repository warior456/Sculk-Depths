package net.ugi.sculk_depths.entity.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModStatusEffects {

    public static final RegistryEntry<StatusEffect> INFECTED = registerStatusEffect("infected",
            new InfectedStatusEffect(StatusEffectCategory.HARMFUL, 0xCFD88B));//13621387

    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, SculkDepths.identifier(name), statusEffect);
    }

    public static void init(){

    }
}
