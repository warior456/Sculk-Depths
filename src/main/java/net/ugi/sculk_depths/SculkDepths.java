package net.ugi.sculk_depths;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.ugi.sculk_depths.block.ModBlockEntities;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.ModCauldron.ModCauldronBehavior;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;
import net.ugi.sculk_depths.item.ModItemGroup;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.portal.Portals;
import net.ugi.sculk_depths.recipe.ModRecipes;
import net.ugi.sculk_depths.sound.ModSounds;
import net.ugi.sculk_depths.util.ModLootTableModifiers;
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
        ModLootTableModifiers.modifyLootTables();
        ModCauldronBehavior.registerBehavior();
        ModRecipes.register();
        ModBlockEntities.registerBlockEntityTypes();
        ModBlockEntities.registerBlockEntities();
        FabricDefaultAttributeRegistry.register(ModEntities.GLOMPER, GlomperEntity.setAttributes());

        LOGGER.info("sculk_depths has loaded");
    }
}
