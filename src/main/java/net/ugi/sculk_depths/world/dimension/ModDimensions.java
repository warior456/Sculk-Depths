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
            new Identifier(SculkDepths.MOD_ID, "sculk_depths"));
    public static final RegistryKey<World> SCULK_DEPTHS_LEVEL_KEY_IDK = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(SculkDepths.MOD_ID, "sculk_depths"));

    public static final RegistryKey<DimensionType> SCULK_DEPTHS_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            new Identifier(SculkDepths.MOD_ID, "sculk_depths_type"));
}
