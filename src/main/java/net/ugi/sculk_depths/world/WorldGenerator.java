package net.ugi.sculk_depths.world;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.world.gen.feature.ModConfiguredFeatures;

import java.util.Optional;

public class WorldGenerator {
    public static void initWorldGen() {}
    public static final TreeGrower VALTROX_SAPLING_GENERATOR = new TreeGrower(
            SculkDepths.id( "valtrox").toString(),
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.VALTROX),
            Optional.empty()
    );
 }