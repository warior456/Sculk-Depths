package net.ugi.sculk_depths.world.gen.feature;


import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;


public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> VALTROX = ConfiguredFeatures.of("sculk_depths:valtrox");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PENEBRIUM_SHROOM = ConfiguredFeatures.of("sculk_depths:penebrium_shroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> AURIC_SHROOM = ConfiguredFeatures.of("sculk_depths:auric_shroom");

    public ModConfiguredFeatures() {
    }
}

