package net.ugi.sculk_depths.particle;


import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.ugi.sculk_depths.SculkDepths;

public class ModParticleTypes {

    public static final ParticleType<SimpleParticleType> PENEBRIUM_SPORES = FabricParticleTypes.simple();
    public static final ParticleType<SimpleParticleType> SCULK_DEPTHS_PORTAL_PARTICLE = FabricParticleTypes.simple();

    public static final ParticleType<SimpleParticleType> AURIC_SPORES = FabricParticleTypes.simple();
    public static final ParticleType<SimpleParticleType> SURFACE_WIND = FabricParticleTypes.simple();
    public static final ParticleType<SimpleParticleType> CAVE_FALLING_PARTICLE = FabricParticleTypes.simple();
    public static final ParticleType<SimpleParticleType> ENERGY_PARTICLE = FabricParticleTypes.simple();

    public static void registerModParticles() {
        Registry.register(Registries.PARTICLE_TYPE, SculkDepths.identifier( "penebrium_spores"), PENEBRIUM_SPORES);
        Registry.register(Registries.PARTICLE_TYPE, SculkDepths.identifier( "sculk_depths_portal_particle"), SCULK_DEPTHS_PORTAL_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, SculkDepths.identifier( "auric_spores"), AURIC_SPORES);
        Registry.register(Registries.PARTICLE_TYPE, SculkDepths.identifier( "surface_wind"), SURFACE_WIND);
        Registry.register(Registries.PARTICLE_TYPE, SculkDepths.identifier( "cave_falling_particle"), CAVE_FALLING_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, SculkDepths.identifier( "energy_particle"), ENERGY_PARTICLE);
    }
}