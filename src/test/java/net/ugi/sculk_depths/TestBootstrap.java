package net.ugi.sculk_depths;

import net.minecraft.Bootstrap;
import net.minecraft.SharedConstants;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.ModCauldron.ModCauldronBehavior;
import net.ugi.sculk_depths.config.ConfigHandler;
import net.ugi.sculk_depths.item.ModComponentTypes;
import net.ugi.sculk_depths.item.ModItemGroup;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.screen.ModScreenHandlers;

/**
 * fabric-loader-junit boots the loader and the Minecraft registries, but does NOT
 * run mod entrypoints (onInitialize). Tests that touch registered mod content call
 * this once to perform the same registrations the initializer would have done.
 */
public final class TestBootstrap {

    private static boolean initialized = false;

    private TestBootstrap() {
    }

    public static synchronized void init() {
        if (initialized) {
            return;
        }
        // fabric-loader-junit boots the loader but not the game bootstrap.
        SharedConstants.createGameVersion();
        Bootstrap.initialize();

        // Config is normally read from disk in onInitialize; give tests sane defaults.
        SculkDepths.CONFIG = new ConfigHandler();

        ModItemGroup.registerItemgroups();
        // Class-loading ModItems / ModBlocks runs their static registration.
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModScreenHandlers.registerModScreenHandlers();
        ModComponentTypes.register();
        // Populates the cauldron behavior maps (normally done in onInitialize).
        ModCauldronBehavior.registerBehavior();

        initialized = true;
    }
}
