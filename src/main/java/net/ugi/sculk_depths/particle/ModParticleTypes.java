package net.ugi.sculk_depths.particle;


import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.AshParticle;
import net.minecraft.client.particle.DamageParticle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.item.ItemGroup;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModParticleTypes {

    public static final DefaultParticleType PENEBRIUM_SPORES = FabricParticleTypes.simple();
    public static final DefaultParticleType SURFACE_WIND = FabricParticleTypes.simple();

    public static void registerModParticles() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(SculkDepths.MOD_ID, "penebrium_spores"), PENEBRIUM_SPORES);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(SculkDepths.MOD_ID, "surface_wind"), SURFACE_WIND);
    }
}