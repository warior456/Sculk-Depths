package net.ugi.sculk_depths;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.CauldronFluidContent;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.poi.PointOfInterestType;
import net.ugi.sculk_depths.block.ModBlockEntities;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.ModCauldron.ModCauldronBehavior;
import net.ugi.sculk_depths.config.Config;
import net.ugi.sculk_depths.config.ConfigHandler;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.entity.custom.ChomperColossusEntity;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;
import net.ugi.sculk_depths.entity.custom.LesterEntity;
import net.ugi.sculk_depths.entity.effect.ModStatusEffects;
import net.ugi.sculk_depths.fluid.ModFluids;
import net.ugi.sculk_depths.item.ModItemGroup;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.item.crystal.CheckInvForCrystalItems;
import net.ugi.sculk_depths.particle.ModParticleTypes;
import net.ugi.sculk_depths.screen.ModScreenHandlers;
import net.ugi.sculk_depths.sound.ConditionalSoundPlayer;
import net.ugi.sculk_depths.sound.ModSounds;
import net.ugi.sculk_depths.state.property.ModProperties;
import net.ugi.sculk_depths.world.WorldGenerator;
import net.ugi.sculk_depths.world.gen.ModFeatures;
import net.ugi.sculk_depths.world.gen.ModPlacementModifierType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class SculkDepths implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "sculk_depths";
	public static ConfigHandler CONFIG;
	public static final Logger LOGGER = LoggerFactory.getLogger("sculk_depths");

	public static Identifier identifier(String path) {
		return Identifier.of(SculkDepths.MOD_ID, path);
	}
	@Override
	public void onInitialize() {
        SpawnRestriction.register(ModEntities.LESTER, SpawnLocationTypes.ON_GROUND, null, (type, world, reason, pos, random) -> true);
        SpawnRestriction.register(ModEntities.CHOMPER_COLOSSUS, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (type, world, reason, pos, random) -> true);

        SculkDepths.LOGGER.info("Loading Config for " + SculkDepths.MOD_ID);
        Config.loadConfig();
        SculkDepths.LOGGER.info("Registering Itemgroups for " + SculkDepths.MOD_ID);
        ModItemGroup.registerItemgroups();
        SculkDepths.LOGGER.info("Registering items for " + SculkDepths.MOD_ID);
        ModItems.registerModItems();
        SculkDepths.LOGGER.info("Registering Blocks for " + SculkDepths.MOD_ID);
        ModBlocks.registerModBlocks();
/*        SculkDepths.LOGGER.info("Registering Portals for " + SculkDepths.MOD_ID);
        Portals.registerModPortals();*/
        SculkDepths.LOGGER.info("Registering Sounds for " + SculkDepths.MOD_ID);
        ModSounds.registerModSounds();
        SculkDepths.LOGGER.info("Registering Effects for " + SculkDepths.MOD_ID);
        ModStatusEffects.init();
        ServerTickEvents.START_WORLD_TICK.register(new ConditionalSoundPlayer());
        //SculkDepths.LOGGER.info("Registering LootTables for " + SculkDepths.MOD_ID);
        //ModLootTableModifiers.modifyLootTables();
        SculkDepths.LOGGER.info("Registering ModCauldronBehavior for " + SculkDepths.MOD_ID);
        ModCauldronBehavior.registerBehavior();
        //SculkDepths.LOGGER.info("Registering Recipes for " + SculkDepths.MOD_ID);
        //ModRecipes.register();
        SculkDepths.LOGGER.info("Registering BlockEntities for " + SculkDepths.MOD_ID);
        ModBlockEntities.registerBlockEntities();
        SculkDepths.LOGGER.info("Registering Entities for " + SculkDepths.MOD_ID);
        FabricDefaultAttributeRegistry.register(ModEntities.GLOMPER, GlomperEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.LESTER, LesterEntity.createLesterAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CHOMPER_COLOSSUS, ChomperColossusEntity.createChomperColossusAttributes());
        SculkDepths.LOGGER.info("Registering Particles for " + SculkDepths.MOD_ID);
        ModParticleTypes.registerModParticles();
        SculkDepths.LOGGER.info("Registering ScreenHandlers for " + SculkDepths.MOD_ID);
        ModScreenHandlers.registerModScreenHandlers();
        ModPlacementModifierType.init();
        ModFeatures.init();

        WorldGenerator.initWorldGen();


        ServerTickEvents.START_WORLD_TICK.register(new CheckInvForCrystalItems());
        ServerTickEvents.START_WORLD_TICK.register(new ModBiomeEffects());

        //registerCustomPOI();


        CauldronFluidContent.registerCauldron(ModBlocks.KRYSLUM_FLUMROCK_CAULDRON, ModFluids.KRYSLUM_STILL, FluidConstants.BUCKET, ModProperties.KRYSLUM_LEVEL); //support for mods to see how much fluid is in it (doesn't work for create pipes)

        LOGGER.info(SculkDepths.MOD_ID + " has loaded");
    }


//    private static void registerStates(PointOfInterestType poiType, Set<BlockState> states) {
//        states.forEach(state -> {
//            if (PointOfInterestTypes.containsState(state)) {
//                throw new IllegalStateException(state + " is already mapped to a POI!");
//            }
//            PointOfInterestTypes.registerState(poiType, state);
//        });
//    }
//
//    public static void registerCustomPOI() {
//        PointOfInterestType customPOIType = new PointOfInterestType(
//                CUSTOM_SMITH_BLOCK_STATES, // Block states associated with this POI
//                1,                        // Ticket count
//                1                         // Search distance
//        );
//
//        Registry.register(
//                LODESTONE_POI, // POI Registry
//                CUSTOM_SMITH.getValue(),         // Identifier
//                customPOIType                    // Your custom POI type
//        );
//
//        registerStates(customPOIType, CUSTOM_SMITH_BLOCK_STATES); // Map custom states to the POI type
//    }
}

