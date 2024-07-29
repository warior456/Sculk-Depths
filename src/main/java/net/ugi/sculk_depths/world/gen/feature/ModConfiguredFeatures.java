package net.ugi.sculk_depths.world.gen.feature;


import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.ugi.sculk_depths.SculkDepths;


public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> VALTROX = configuredFeatureRegistryKey("valtrox");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PENEBRIUM_SHROOM = configuredFeatureRegistryKey("penebrium_shroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> AURIC_SHROOM = configuredFeatureRegistryKey("auric_shroom");


    public static RegistryKey<ConfiguredFeature<?, ?>> configuredFeatureRegistryKey(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, SculkDepths.identifier(id));
    }
    public ModConfiguredFeatures() {
    }
}

