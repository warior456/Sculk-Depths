package net.ugi.sculk_depths.world.biome;

import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.ugi.sculk_depths.SculkDepths;

public class ModBiomes {
    public static final ResourceKey<Biome> PETRIFIED_FOREST = register("petrified_valtrox_forest");
    public static final ResourceKey<Biome> DRIED_FOREST = register("dried_valtrox_forest");

    public static final ResourceKey<Biome> INFECTED_COLUMNS = register("infected_columns");

    public static final ResourceKey<Biome> CEPHLERA_CAVES = register("cephlera_caves");
    public static final ResourceKey<Biome> SCULK_CAVES = register("sculk_caves");

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, SculkDepths.id( name));
    }
}
