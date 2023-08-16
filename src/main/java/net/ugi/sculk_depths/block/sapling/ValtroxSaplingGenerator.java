package net.ugi.sculk_depths.block.sapling;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.ugi.sculk_depths.world.gen.feature.ModConfiguredFeatures;

public class ValtroxSaplingGenerator extends SaplingGenerator {
    public ValtroxSaplingGenerator() {
    }

    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {

        return ModConfiguredFeatures.VALTROX;
    }
}