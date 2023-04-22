package net.ugi.sculk_depths;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;
import net.ugi.sculk_depths.item.ModItemGroup;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.portal.Portals;
import net.ugi.sculk_depths.sound.ModSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SculkDepths implements ModInitializer {
    public static final String MOD_ID = "sculk_depths";
    public static final Logger LOGGER = LoggerFactory.getLogger("sculk_depths");





    @Override
    public void onInitialize() {
        ModItemGroup.registerItemgroups();
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        Portals.registerModPortals();
        ModSounds.registerModSounds();
        FabricDefaultAttributeRegistry.register(ModEntities.GLOMPER, GlomperEntity.setAttributes());

        LOGGER.info("sculk_depths has loaded");
    }
}
