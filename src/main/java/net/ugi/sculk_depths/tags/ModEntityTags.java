package net.ugi.sculk_depths.tags;

import net.minecraft.world.entity.EntityType;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.ugi.sculk_depths.SculkDepths;

public class ModEntityTags {
    public static final TagKey<EntityType<?>> DROPS_ENERGY_ESSENCE = TagKey.of(Registries.ENTITY_TYPE, SculkDepths.id( "drops_energy_essence"));
    public static final TagKey<EntityType<?>> NO_WARDEN_ANGER = TagKey.of(Registries.ENTITY_TYPE, SculkDepths.id( "no_warden_anger"));
}
