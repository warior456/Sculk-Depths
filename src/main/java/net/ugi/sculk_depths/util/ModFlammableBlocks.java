package net.ugi.sculk_depths.util;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.ugi.sculk_depths.block.ModBlocks;

public class ModFlammableBlocks {
    public static void registerFlammables() {
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VALTROX_LOG, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_VALTROX_LOG, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VALTROX_WOOD, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VALTROX_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VALTROX_STAIRS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VALTROX_SLAB, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VALTROX_FENCE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VALTROX_FENCE_GATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VALTROX_DOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VALTROX_TRAPDOOR, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VALTROX_PRESSURE_PLATE, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VALTROX_SIGN, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VALTROX_HANGING_SIGN, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VALTROX_WALL_HANGING_SIGN, 5, 20);

        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIED_VALTROX_LOG, 5, 40);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_DRIED_VALTROX_LOG, 5, 40);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIED_VALTROX_WOOD, 5, 40);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIED_VALTROX_PLANKS, 5, 40);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIED_VALTROX_STAIRS, 5, 40);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIED_VALTROX_SLAB, 5, 40);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIED_VALTROX_FENCE, 5, 40);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIED_VALTROX_FENCE_GATE, 5, 40);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIED_VALTROX_DOOR, 5, 40);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIED_VALTROX_TRAPDOOR, 5, 40);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE, 5, 40);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIED_VALTROX_SIGN, 5, 40);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIED_VALTROX_HANGING_SIGN, 5, 40);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIED_VALTROX_WALL_HANGING_SIGN, 5, 40);
    }
}