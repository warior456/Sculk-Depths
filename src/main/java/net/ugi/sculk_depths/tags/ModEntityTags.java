package net.ugi.sculk_depths.tags;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModEntityTags {
    public static final TagKey<EntityType<?>> DROPS_ENERGY_ESSENCE = TagKey.of(RegistryKeys.ENTITY_TYPE, SculkDepths.identifier( "drops_energy_essence"));
    public static final TagKey<EntityType<?>> NO_WARDEN_ANGER = TagKey.of(RegistryKeys.ENTITY_TYPE, SculkDepths.identifier( "no_warden_anger"));
}
