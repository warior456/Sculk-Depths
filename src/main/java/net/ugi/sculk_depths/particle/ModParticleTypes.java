package net.ugi.sculk_depths.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.ugi.sculk_depths.SculkDepths;

public class ModParticleTypes {

    public static final SimpleParticleType PENEBRIUM_SPORES = register("penebrium_spores", FabricParticleTypes.simple());
    public static final SimpleParticleType SCULK_DEPTHS_PORTAL_PARTICLE = register("sculk_depths_portal_particle", FabricParticleTypes.simple());
    public static final SimpleParticleType SCULK_DEPTHS_PORTAL_ANIMATION_PARTICLE = register("sculk_depths_portal_animation_particle", FabricParticleTypes.simple());
    public static final SimpleParticleType AURIC_SPORES = register("auric_spores", FabricParticleTypes.simple());
    public static final SimpleParticleType SURFACE_WIND = register("surface_wind", FabricParticleTypes.simple());
    public static final SimpleParticleType CAVE_FALLING_PARTICLE = register("cave_falling_particle", FabricParticleTypes.simple());
    public static final SimpleParticleType ENERGY_PARTICLE = register("energy_particle", FabricParticleTypes.simple());

    private static <T extends ParticleType<E>, E extends ParticleEffect> T register(String name, T particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, SculkDepths.identifier(name), particleType);
    }

    public static void registerModParticles() {
    }
}