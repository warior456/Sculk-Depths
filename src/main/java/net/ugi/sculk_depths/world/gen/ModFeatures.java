package net.ugi.sculk_depths.world.gen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.ugi.sculk_depths.SculkDepths;

public class ModFeatures {
    public static final Feature<DefaultFeatureConfig> CONFIG_SPIKES = new ConfigurableSpikes(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> CONFIG_PILLARS = new ConfigurablePillars(DefaultFeatureConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> AURIC_SHROOM = new AuricMushroom(DefaultFeatureConfig.CODEC);

    public static void init(){
        Registry.register(Registries.FEATURE, SculkDepths.identifier( "configurable_spikes"), CONFIG_SPIKES);
        Registry.register(Registries.FEATURE, SculkDepths.identifier("pillars"), CONFIG_PILLARS);
        Registry.register(Registries.FEATURE, SculkDepths.identifier("auric_shroom"), AURIC_MUSHROOM);
    }
}
