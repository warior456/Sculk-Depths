package net.ugi.sculk_depths.world.gen.feature;


import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.ugi.sculk_depths.SculkDepths;


public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> VALTROX = configuredFeatureRegistryKey("valtrox");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PENEBRIUM_SHROOM = configuredFeatureRegistryKey("penebrium_shroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AURIC_SHROOM = configuredFeatureRegistryKey("auric_shroom");


    public static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureRegistryKey(String id) {
        return ResourceKey.of(Registries.CONFIGURED_FEATURE, SculkDepths.id(id));
    }
    public ModConfiguredFeatures() {
    }
}

