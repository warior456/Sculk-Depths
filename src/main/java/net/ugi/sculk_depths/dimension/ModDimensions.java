package net.ugi.sculk_depths.dimension;

import net.minecraft.registry.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.ugi.sculk_depths.SculkDepths;

import java.awt.*;
import java.util.OptionalLong;

public class ModDimensions {
    public static final RegistryKey<DimensionOptions> SCULK_DEPTHS = RegistryKey.of(RegistryKeys.DIMENSION,
            new Identifier(SculkDepths.MOD_ID, "kaupendim"));
    public static final RegistryKey<World> KAUPENDIM_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(SculkDepths.MOD_ID, "kaupendim"));
    public static final RegistryKey<DimensionType> SCULK_DEPTHS_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            new Identifier(SculkDepths.MOD_ID, "kaupendim_type"));
}
