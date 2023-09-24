package net.ugi.sculk_depths.world.biome;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.ugi.sculk_depths.SculkDepths;

public class ModBiomes {
    public static final RegistryKey<Biome> PETRIFIED_FOREST = register("petrified_forest");
    public static final RegistryKey<Biome> DRIED_FOREST = register("dried_forest");

    public static final RegistryKey<Biome> CEPHLERA_CAVES = register("cephlera_caves");
    public static final RegistryKey<Biome> SCULK_CAVES = register("sculk_caves");

    private static RegistryKey<Biome> register(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier(SculkDepths.MOD_ID, name));
    }
}
