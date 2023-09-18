package net.ugi.sculk_depths.util;

import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.ugi.sculk_depths.block.ModBlocks;

public class ModStrippableBlocks {
    public static void registerStrippables() {
        StrippableBlockRegistry.register(ModBlocks.VALTROX_LOG, ModBlocks.STRIPPED_VALTROX_LOG);
        StrippableBlockRegistry.register(ModBlocks.VALTROX_WOOD, ModBlocks.STRIPPED_VALTROX_WOOD);
        StrippableBlockRegistry.register(ModBlocks.DRIED_VALTROX_LOG, ModBlocks.STRIPPED_DRIED_VALTROX_LOG);
        StrippableBlockRegistry.register(ModBlocks.DRIED_VALTROX_WOOD, ModBlocks.STRIPPED_DRIED_VALTROX_WOOD);
        StrippableBlockRegistry.register(ModBlocks.PETRIFIED_VALTROX_LOG, ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG);
        StrippableBlockRegistry.register(ModBlocks.PETRIFIED_VALTROX_WOOD, ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD);
    }
}