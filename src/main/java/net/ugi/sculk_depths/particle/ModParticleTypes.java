package net.ugi.sculk_depths.particle;


import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.ugi.sculk_depths.SculkDepths;

public class ModParticleTypes {

    public static final ParticleType<SimpleParticleType> PENEBRIUM_SPORES = FabricParticleTypes.simple();
    public static final ParticleType<SimpleParticleType> SCULK_DEPTHS_PORTAL_PARTICLE = FabricParticleTypes.simple();

    public static final ParticleType<SimpleParticleType> SCULK_DEPTHS_PORTAL_ANIMATION_PARTICLE = FabricParticleTypes.simple();

    public static final ParticleType<SimpleParticleType> AURIC_SPORES = FabricParticleTypes.simple();
    public static final ParticleType<SimpleParticleType> SURFACE_WIND = FabricParticleTypes.simple();
    public static final ParticleType<SimpleParticleType> CAVE_FALLING_PARTICLE = FabricParticleTypes.simple();
    public static final ParticleType<SimpleParticleType> ENERGY_PARTICLE = FabricParticleTypes.simple();

    public static void registerModParticles() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, SculkDepths.id( "penebrium_spores"), PENEBRIUM_SPORES);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, SculkDepths.id( "sculk_depths_portal_particle"), SCULK_DEPTHS_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, SculkDepths.id( "sculk_depths_portal_animation_particle"), SCULK_DEPTHS_PORTAL_ANIMATION_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, SculkDepths.id( "auric_spores"), AURIC_SPORES);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, SculkDepths.id( "surface_wind"), SURFACE_WIND);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, SculkDepths.id( "cave_falling_particle"), CAVE_FALLING_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, SculkDepths.id( "energy_particle"), ENERGY_PARTICLE);
    }
}