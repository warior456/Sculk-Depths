package net.ugi.sculk_depths.entity.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModStatusEffects {
    public static final StatusEffect INFECTED = new InfectedStatusEffect(StatusEffectCategory.HARMFUL, 13621387);
    public static void init(){
        Registry.register(Registries.STATUS_EFFECT, new Identifier(SculkDepths.MOD_ID, "infected"), INFECTED);
    }
}
