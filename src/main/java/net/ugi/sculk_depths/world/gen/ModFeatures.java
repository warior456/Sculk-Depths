package net.ugi.sculk_depths.world.gen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.ugi.sculk_depths.SculkDepths;

public class ModFeatures {
    public static final Feature<NoneFeatureConfiguration> CONFIG_SPIKES = new ConfigurableSpikes(NoneFeatureConfiguration.CODEC);
    public static final Feature<NoneFeatureConfiguration> CONFIG_PILLARS = new ConfigurablePillars(NoneFeatureConfiguration.CODEC);
    public static final Feature<NoneFeatureConfiguration> SCULK_VEINS = new SculkVeins(NoneFeatureConfiguration.CODEC);
    public static final Feature<NoneFeatureConfiguration> AURIC_SHROOM = new AuricMushroom(NoneFeatureConfiguration.CODEC);

    public static void init(){
        Registry.register(BuiltInRegistries.FEATURE, SculkDepths.id( "configurable_spikes"), CONFIG_SPIKES);
        Registry.register(BuiltInRegistries.FEATURE, SculkDepths.id("pillars"), CONFIG_PILLARS);
        Registry.register(BuiltInRegistries.FEATURE, SculkDepths.id("sculk_veins"), SCULK_VEINS );
        Registry.register(BuiltInRegistries.FEATURE, SculkDepths.id("auric_shroom"), AURIC_SHROOM);
    }
}
