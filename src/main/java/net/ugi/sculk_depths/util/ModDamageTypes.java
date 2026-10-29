package net.ugi.sculk_depths.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.Level;
import net.ugi.sculk_depths.SculkDepths;

public class ModDamageTypes {

    /*
     * Store the RegistryKey of our DamageType into a new constant called CUSTOM_DAMAGE_TYPE
     * The Identifier in use here points to our JSON file we created earlier.
     */
    public static final ResourceKey<DamageType> INFECTED_DAMAGE_TYPE = ResourceKey.of(Registries.DAMAGE_TYPE, SculkDepths.id( "infected_damage_type"));

    public static DamageSource of(Level world, ResourceKey<DamageType> name) {
        return new DamageSource(world.getRegistryManager().get(Registries.DAMAGE_TYPE).entryOf(name));
    }
}