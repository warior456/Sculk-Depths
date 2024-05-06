package net.ugi.sculk_depths.world;

import net.minecraft.block.SaplingGenerator;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.world.gen.feature.ModConfiguredFeatures;

import java.util.Optional;

public class WorldGenerator {
    public static void initWorldGen() {}
    public static final SaplingGenerator VALTROX_SAPLING_GENERATOR = new SaplingGenerator(
            new Identifier(SculkDepths.MOD_ID, "valtrox").toString(),
            Optional.of(ModConfiguredFeatures.VALTROX),
            Optional.empty(),
            Optional.empty()
    );
 }