package net.ugi.sculk_depths.world.gen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IceSpikeFeature;
import net.ugi.sculk_depths.SculkDepths;

public class ModFeatures {
    public static final Feature<DefaultFeatureConfig> CONFIG_SPIKES = new ConfigurableSpikes(DefaultFeatureConfig.CODEC);

    public static void init(){
        Registry.register(Registries.FEATURE, new Identifier(SculkDepths.MOD_ID, "configurable_spikes"), CONFIG_SPIKES);
    }
}
