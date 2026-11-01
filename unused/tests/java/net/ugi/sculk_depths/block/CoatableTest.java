package net.ugi.sculk_depths.block;

import net.ugi.sculk_depths.TestBootstrap;
import net.ugi.sculk_depths.item.custom.Coatable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Normal-behavior tests for the Coatable valtrox wood coating mapping.
 */
class CoatableTest {

    @BeforeAll
    static void setup() {
        TestBootstrap.init();
    }

    @Test
    void valtroxLogMapsToCoatedVariant() {
        assertEquals(ModBlocks.COATED_VALTROX_LOG, Coatable.getCoatedBlock(ModBlocks.VALTROX_LOG));
    }

    @Test
    void coatingMappingIsInvertible() {
        assertEquals(ModBlocks.VALTROX_PLANKS, Coatable.getNonCoatedBlock(ModBlocks.COATED_VALTROX_PLANKS));
        assertEquals(ModBlocks.VALTROX_DOOR, Coatable.getNonCoatedBlock(ModBlocks.COATED_VALTROX_DOOR));
    }

    @Test
    void everyCoatableEntryRoundTrips() {
        Coatable.COAT_LEVEL_INCREASES.get().forEach((base, coated) ->
                assertEquals(base, Coatable.getNonCoatedBlock(coated),
                        "coated variant of " + base + " must map back"));
    }

    @Test
    void coatingMapCoversWholeValtroxSet() {
        // log, wood, stripped log, stripped wood, planks, stairs, slab, fence,
        // fence gate, door, trapdoor, pressure plate, button, sign, wall sign,
        // hanging sign, wall hanging sign
        assertEquals(17, Coatable.COAT_LEVEL_INCREASES.get().size());
    }

    @Test
    void uncoatableBlockMapsToNull() {
        assertNull(Coatable.getCoatedBlock(net.minecraft.block.Blocks.STONE));
        assertNull(Coatable.getNonCoatedBlock(net.minecraft.block.Blocks.STONE));
    }
}
