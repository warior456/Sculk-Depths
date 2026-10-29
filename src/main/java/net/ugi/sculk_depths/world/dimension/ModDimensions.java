package net.ugi.sculk_depths.world.dimension;

import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.dimension.DimensionType;
import net.ugi.sculk_depths.SculkDepths;

public class ModDimensions {
    public static final ResourceKey<LevelStem> SCULK_DEPTHS = ResourceKey.of(Registries.LEVEL_STEM,
            SculkDepths.id( "sculk_depths"));
    public static final ResourceKey<Level> SCULK_DEPTHS_LEVEL_KEY = ResourceKey.of(Registries.DIMENSION,
            SculkDepths.id( "sculk_depths"));

    public static final ResourceKey<DimensionType> SCULK_DEPTHS_TYPE = ResourceKey.of(Registries.DIMENSION_TYPE,
            SculkDepths.id( "sculk_depths_type"));
}
