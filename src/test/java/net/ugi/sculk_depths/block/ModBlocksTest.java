package net.ugi.sculk_depths.block;

import net.minecraft.block.BlockState;
import net.ugi.sculk_depths.TestBootstrap;
import net.ugi.sculk_depths.state.property.ModProperties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Normal-behavior tests for block registration and declared block settings/state.
 */
class ModBlocksTest {

    @BeforeAll
    static void setup() {
        TestBootstrap.init();
    }

    @Test
    void blocksAreRegisteredUnderModId() {
        assertEquals("sculk_depths:amalgamite",
                net.minecraft.registry.Registries.BLOCK.getId(ModBlocks.AMALGAMITE).toString());
        assertEquals("sculk_depths:kryslum_flumrock_cauldron",
                net.minecraft.registry.Registries.BLOCK.getId(ModBlocks.KRYSLUM_FLUMROCK_CAULDRON).toString());
        assertEquals("sculk_depths:crux_resonator",
                net.minecraft.registry.Registries.ITEM.getId(net.ugi.sculk_depths.item.ModItems.CRUX_RESONATOR).toString());
    }

    @Test
    void amalgamiteHasDeclaredHardnessAndResistance() {
        assertEquals(3.0f, ModBlocks.AMALGAMITE.getHardness());
        assertEquals(6.0f, ModBlocks.AMALGAMITE.getBlastResistance());
    }

    @Test
    void activatedAmalgamiteIsUnbreakableLikeReinforcedDeepslate() {
        // strength(-1.0f, 3600000.0f) mirrors Blocks.REINFORCED_DEEPSLATE
        assertEquals(-1.0f, ModBlocks.ACTIVATED_AMALGAMITE.getHardness());
        assertEquals(3600000.0f, ModBlocks.ACTIVATED_AMALGAMITE.getBlastResistance());
    }

    @Test
    void kryslumCauldronDefaultStateHasExpectedLevels() {
        BlockState state = ModBlocks.KRYSLUM_FLUMROCK_CAULDRON.getDefaultState();

        assertEquals(1, state.get(ModProperties.KRYSLUM_LEVEL).intValue());
        assertEquals(0, state.get(ModProperties.QUAZARITH_LEVEL).intValue());
        assertEquals(0, state.get(ModProperties.CRUX_LEVEL).intValue());
    }

    @Test
    void modLevelPropertiesHaveExpectedRanges() {
        assertEquals(1, ModProperties.KRYSLUM_LEVEL.getValues().stream().mapToInt(Integer::intValue).min().orElseThrow());
        assertEquals(12, ModProperties.KRYSLUM_LEVEL.getValues().stream().mapToInt(Integer::intValue).max().orElseThrow());
        assertEquals(0, ModProperties.QUAZARITH_LEVEL.getValues().stream().mapToInt(Integer::intValue).min().orElseThrow());
        assertEquals(12, ModProperties.QUAZARITH_LEVEL.getValues().stream().mapToInt(Integer::intValue).max().orElseThrow());
        assertEquals(12, ModProperties.SPORE_LEVEL.getValues().stream().mapToInt(Integer::intValue).max().orElseThrow());
    }
}
