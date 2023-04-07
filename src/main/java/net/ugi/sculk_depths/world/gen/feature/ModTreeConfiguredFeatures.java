package net.ugi.sculk_depths.world.gen.feature;


import net.minecraft.block.*;
import net.minecraft.registry.Registerable;

import net.minecraft.registry.RegistryKey;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;

import net.minecraft.world.gen.feature.*;

import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.*;

import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import net.minecraft.world.gen.trunk.*;
import net.ugi.sculk_depths.block.ModBlocks;


public class ModTreeConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> VALTROX = ConfiguredFeatures.of("sculk_depths:valtrox");

    public ModTreeConfiguredFeatures() {
    }

}

