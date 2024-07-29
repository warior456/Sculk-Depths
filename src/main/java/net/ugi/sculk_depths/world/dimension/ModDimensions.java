package net.ugi.sculk_depths.world.dimension;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.ugi.sculk_depths.SculkDepths;

public class ModDimensions {
    public static final RegistryKey<DimensionOptions> SCULK_DEPTHS = RegistryKey.of(RegistryKeys.DIMENSION,
            SculkDepths.identifier( "sculk_depths"));
    public static final RegistryKey<World> SCULK_DEPTHS_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            SculkDepths.identifier( "sculk_depths"));

    public static final RegistryKey<DimensionType> SCULK_DEPTHS_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            SculkDepths.identifier( "sculk_depths_type"));
}
