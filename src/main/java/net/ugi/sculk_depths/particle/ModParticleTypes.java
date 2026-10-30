package net.ugi.sculk_depths.particle;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.ugi.sculk_depths.SculkDepths;

public class ModParticleTypes {

    public static final SimpleParticleType PENEBRIUM_SPORES = new SimpleParticleType(false);
    public static final SimpleParticleType SCULK_DEPTHS_PORTAL_PARTICLE = new SimpleParticleType(false);

    public static final SimpleParticleType SCULK_DEPTHS_PORTAL_ANIMATION_PARTICLE = new SimpleParticleType(false);

    public static final SimpleParticleType AURIC_SPORES = new SimpleParticleType(false);
    public static final SimpleParticleType SURFACE_WIND = new SimpleParticleType(false);
    public static final SimpleParticleType CAVE_FALLING_PARTICLE = new SimpleParticleType(false);
    public static final SimpleParticleType ENERGY_PARTICLE = new SimpleParticleType(false);

    public static void registerModParticles() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, SculkDepths.id("penebrium_spores"), PENEBRIUM_SPORES);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, SculkDepths.id("sculk_depths_portal_particle"), SCULK_DEPTHS_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, SculkDepths.id("sculk_depths_portal_animation_particle"), SCULK_DEPTHS_PORTAL_ANIMATION_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, SculkDepths.id("auric_spores"), AURIC_SPORES);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, SculkDepths.id("surface_wind"), SURFACE_WIND);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, SculkDepths.id("cave_falling_particle"), CAVE_FALLING_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, SculkDepths.id("energy_particle"), ENERGY_PARTICLE);
    }
}