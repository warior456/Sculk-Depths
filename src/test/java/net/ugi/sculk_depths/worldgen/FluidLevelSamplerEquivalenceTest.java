package net.ugi.sculk_depths.worldgen;

import net.minecraft.Bootstrap;
import net.minecraft.SharedConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Direct, deterministic test of the {@link AquiferSampler.FluidLevelSampler} that
 * {@code NoiseChunkGenerator.createFluidLevelSampler} returns with the Sculk Depths
 * mixin applied. No world is generated; the sampler is invoked across a sweep of y
 * values (including both boundaries themselves) and compared against an oracle that
 * replicates the previous {@code @Overwrite} implementation verbatim, so this proves
 * the {@code @ModifyReturnValue} rewrite is behaviourally identical in a single-mod
 * context. If the mixin were not applied at all, the vanilla sampler (lava at
 * seaLevel - 54) would mismatch the oracle and this test would fail.
 */
class FluidLevelSamplerEquivalenceTest {

    /**
     * Vertical offset of the lava sea below seaLevel. This is the one tunable balance
     * constant the behaviour depends on; it appears ONLY here so a retune is a
     * one-line edit.
     */
    private static final int LAVA_SEA_OFFSET = 117;

    /**
     * Sea level of the sculk_depths dimension (data/sculk_depths/dimension/sculk_depths.json).
     * Deliberately NOT 63: with seaLevel 63 the modded lava level (63 - 117 = -54)
     * coincides with vanilla's hardcoded -54, which would make this test unable to
     * distinguish the mixin from vanilla behaviour.
     */
    private static final int SEA_LEVEL = -248;

    private static final int LAVA_LEVEL_Y = SEA_LEVEL - LAVA_SEA_OFFSET;

    private static Field fluidLevelY;
    private static Field fluidLevelState;

    @BeforeAll
    static void bootstrapMinecraft() throws Exception {
        SharedConstants.createGameVersion();
        Bootstrap.initialize();
        // FluidLevel's y/state are package-private; read them reflectively.
        fluidLevelY = AquiferSampler.FluidLevel.class.getDeclaredField("y");
        fluidLevelY.setAccessible(true);
        fluidLevelState = AquiferSampler.FluidLevel.class.getDeclaredField("state");
        fluidLevelState.setAccessible(true);
    }

    /**
     * Invokes the private static {@code NoiseChunkGenerator.createFluidLevelSampler} on
     * the actual (mixin-transformed) class.
     */
    private static AquiferSampler.FluidLevelSampler createSampler(ChunkGeneratorSettings settings) throws Exception {
        Method m = NoiseChunkGenerator.class.getDeclaredMethod("createFluidLevelSampler", ChunkGeneratorSettings.class);
        m.setAccessible(true);
        return (AquiferSampler.FluidLevelSampler) m.invoke(null, settings);
    }

    /**
     * Minimal generator settings: createFluidLevelSampler only reads seaLevel() and
     * defaultFluid(), so the noise-related components are left null.
     */
    private static ChunkGeneratorSettings fixtureSettings() {
        return new ChunkGeneratorSettings(
                null,                                   // generationShapeConfig (unused)
                Blocks.STONE.getDefaultState(),         // defaultBlock (unused)
                Blocks.WATER.getDefaultState(),         // defaultFluid
                null,                                   // noiseRouter (unused)
                null,                                   // surfaceRule (unused)
                List.of(),                              // spawnTarget (unused)
                SEA_LEVEL,                              // seaLevel
                false, false, false, false);
    }

    /**
     * Verbatim replica of the previous @Overwrite implementation (upstream/portal),
     * used as the independent oracle. The dead AIR FluidLevel at
     * DimensionType.MIN_HEIGHT * 2 from the old code is omitted here: it was never
     * referenced by the returned lambda.
     */
    private static AquiferSampler.FluidLevelSampler preMixinOracle(ChunkGeneratorSettings settings) {
        AquiferSampler.FluidLevel fluidLevel = new AquiferSampler.FluidLevel(settings.seaLevel() - LAVA_SEA_OFFSET, Blocks.LAVA.getDefaultState());
        int i = settings.seaLevel();
        AquiferSampler.FluidLevel fluidLevel2 = new AquiferSampler.FluidLevel(i, settings.defaultFluid());
        return (x, y, z) -> {
            if (y < Math.min(settings.seaLevel() - LAVA_SEA_OFFSET, i)) {
                return fluidLevel;
            }
            return fluidLevel2;
        };
    }

    private static int fluidY(AquiferSampler.FluidLevel level) {
        try {
            return fluidLevelY.getInt(level);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    private static BlockState fluidState(AquiferSampler.FluidLevel level) {
        try {
            return (BlockState) fluidLevelState.get(level);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    @Test
    void lavaBelowThresholdAndDefaultFluidAtAndAbove() throws Exception {
        AquiferSampler.FluidLevelSampler sampler = createSampler(fixtureSettings());

        // Strictly below the lava threshold: lava at seaLevel - LAVA_SEA_OFFSET.
        for (int y : new int[]{LAVA_LEVEL_Y - 2, LAVA_LEVEL_Y - 1}) {
            AquiferSampler.FluidLevel level = sampler.getFluidLevel(0, y, 0);
            assertEquals(LAVA_LEVEL_Y, fluidY(level), "lava level y at y=" + y);
            assertEquals(Blocks.LAVA.getDefaultState(), fluidState(level), "expected lava at y=" + y);
        }

        // At the threshold itself and above: the default fluid at seaLevel.
        // (The boundary condition is strict: y < min(seaLevel - offset, seaLevel).)
        for (int y : new int[]{LAVA_LEVEL_Y, LAVA_LEVEL_Y + 1, SEA_LEVEL - 1, SEA_LEVEL, SEA_LEVEL + 1}) {
            AquiferSampler.FluidLevel level = sampler.getFluidLevel(0, y, 0);
            assertEquals(SEA_LEVEL, fluidY(level), "default fluid level y at y=" + y);
            assertEquals(Blocks.WATER.getDefaultState(), fluidState(level), "expected default fluid at y=" + y);
        }
    }

    @Test
    void matchesPreMixinOracleAcrossBoundarySweep() throws Exception {
        ChunkGeneratorSettings settings = fixtureSettings();
        AquiferSampler.FluidLevelSampler sampler = createSampler(settings);
        AquiferSampler.FluidLevelSampler oracle = preMixinOracle(settings);

        // Sweep well across both boundaries (lava threshold and seaLevel), at several
        // x/z positions: the sampler must be position-independent and equal to the
        // pre-mixin behaviour at every y.
        for (int x : new int[]{-9999, 0, 12345}) {
            for (int z : new int[]{-7777, 0, 54321}) {
                for (int y = LAVA_LEVEL_Y - 32; y <= SEA_LEVEL + 32; y++) {
                    AquiferSampler.FluidLevel actual = sampler.getFluidLevel(x, y, z);
                    AquiferSampler.FluidLevel expected = oracle.getFluidLevel(x, y, z);
                    assertEquals(fluidY(expected), fluidY(actual),
                            "fluid level y differs at (" + x + ", " + y + ", " + z + ")");
                    assertEquals(fluidState(expected), fluidState(actual),
                            "fluid state differs at (" + x + ", " + y + ", " + z + ")");
                }
            }
        }
    }
}
