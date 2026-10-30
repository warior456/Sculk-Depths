package net.ugi.sculk_depths;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
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
import net.ugi.sculk_depths.item.ModComponentTypes;
import net.ugi.sculk_depths.item.ModItemGroup;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.particle.ModParticleTypes;
import net.ugi.sculk_depths.screen.ModScreenHandlers;
import net.ugi.sculk_depths.sound.ConditionalSoundPlayer;
import net.ugi.sculk_depths.sound.ModSounds;
import net.ugi.sculk_depths.util.POIs;
import net.ugi.sculk_depths.world.WorldGenerator;
import net.ugi.sculk_depths.world.gen.ModFeatures;
import net.ugi.sculk_depths.world.gen.ModPlacementModifierType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SculkDepths.MOD_ID)
public class SculkDepths {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "sculk_depths";
	public static ConfigHandler CONFIG;
	public static final Logger LOGGER = LoggerFactory.getLogger("sculk_depths");

	private static final ConditionalSoundPlayer CONDITIONAL_SOUND_PLAYER = new ConditionalSoundPlayer();
	private static final ModBiomeEffects BIOME_EFFECTS = new ModBiomeEffects();
	private boolean registriesFilled = false;

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(SculkDepths.MOD_ID, path);
	}

	public SculkDepths(IEventBus modBus, ModContainer container) {
		LOGGER.info("Loading Config for " + SculkDepths.MOD_ID);
		Config.loadConfig();

		// Fabric let each registries be filled eagerly from a static initialiser. NeoForge
		// freezes the built-in registries after RegisterEvent, so the same (unchanged)
		// registration code runs once, inside the first RegisterEvent: at that point no
		// built-in registry has been frozen yet, so the vanilla Registry.register calls
		// used throughout the mod remain valid.
		modBus.addListener(RegisterEvent.class, this::onRegister);
		modBus.addListener(EntityAttributeCreationEvent.class, SculkDepths::onEntityAttributes);
		modBus.addListener(RegisterSpawnPlacementsEvent.class, SculkDepths::onSpawnPlacements);
		modBus.addListener(net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent.class, SculkDepths::onBuildCreativeTab);

		NeoForge.EVENT_BUS.addListener(SculkDepths::onServerTick);
	}

	private void onRegister(RegisterEvent event) {
		if (registriesFilled) {
			return;
		}
		registriesFilled = true;

		// Registration order matters here: the mod keeps its original "static field" registries,
		// but those fields are now filled lazily instead of in static initialisers. Anything that
		// is read while another registry class initialises must therefore be registered first:
		//   * ModItems' spawn eggs read ModEntities.GLOMPER/LESTER/CHOMPER_COLOSSUS at construction,
		//   * ModItems' kryslum bucket reads ModFluids.KRYSLUM_STILL at construction,
		//   * ModBlocks' kryslum block reads ModFluids.KRYSLUM_STILL at construction.
		// (Fabric used to get this for free because each class registered itself on first touch.)
		LOGGER.info("Registering Entities for " + SculkDepths.MOD_ID);
		ModEntities.registerModEntities();

		LOGGER.info("Registering Fluids for " + SculkDepths.MOD_ID);
		net.ugi.sculk_depths.fluid.ModFluids.init();

		LOGGER.info("Registering ItemGroups for " + SculkDepths.MOD_ID);
		ModItemGroup.registerItemgroups();

		LOGGER.info("Registering items for " + SculkDepths.MOD_ID);
		ModItems.registerModItems();

		LOGGER.info("Registering Blocks for " + SculkDepths.MOD_ID);
		ModBlocks.registerModBlocks();

		LOGGER.info("Registering Sounds for " + SculkDepths.MOD_ID);
		ModSounds.registerModSounds();

		LOGGER.info("Registering Effects for " + SculkDepths.MOD_ID);
		ModStatusEffects.init();

		LOGGER.info("Registering ModCauldronBehavior for " + SculkDepths.MOD_ID);
		ModCauldronBehavior.registerBehavior();

		LOGGER.info("Registering BlockEntities for " + SculkDepths.MOD_ID);
		ModBlockEntities.registerBlockEntities();

		LOGGER.info("Registering Particles for " + SculkDepths.MOD_ID);
		ModParticleTypes.registerModParticles();

		LOGGER.info("Registering ScreenHandlers for " + SculkDepths.MOD_ID);
		ModScreenHandlers.registerModScreenHandlers();

		LOGGER.info("Registering WorldGen for " + SculkDepths.MOD_ID);
		ModPlacementModifierType.init();
		ModFeatures.init();
		WorldGenerator.initWorldGen();

		LOGGER.info("Registering Components for " + SculkDepths.MOD_ID);
		ModComponentTypes.register();

		LOGGER.info("Registering POI's for " + SculkDepths.MOD_ID);
		POIs.register();

		LOGGER.info(SculkDepths.MOD_ID + " has loaded");
	}

	private static void onEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntities.GLOMPER, GlomperEntity.setAttributes().build());
		event.put(ModEntities.LESTER, LesterEntity.createLesterAttributes().build());
		event.put(ModEntities.CHOMPER_COLOSSUS, ChomperColossusEntity.createChomperColossusAttributes().build());
	}

	private static void onSpawnPlacements(RegisterSpawnPlacementsEvent event) {
		// Faithful to the Fabric version: only lester and chomper_colossus had a restriction call,
		// the glomper intentionally had none (NeoForge logs an error for that, so declare it explicitly).
		event.register(ModEntities.GLOMPER, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(type, level, reason, pos, random) -> true, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntities.LESTER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(type, level, reason, pos, random) -> true, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntities.CHOMPER_COLOSSUS, SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(type, level, reason, pos, random) -> true, RegisterSpawnPlacementsEvent.Operation.REPLACE);
	}

	private static void onServerTick(ServerTickEvent.Post event) {
		event.getServer().getAllLevels().forEach(level -> {
			CONDITIONAL_SOUND_PLAYER.onStartTick(level);
			BIOME_EFFECTS.onStartTick(level);
		});
	}

	private static void onBuildCreativeTab(net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent event) {
		var items = ModItems.TAB_ITEMS.get(event.getTabKey());
		if (items != null) {
			// Fabric's ItemGroupEntries.add() silently allowed the same item to be listed twice
			// (block items are registered into the tab by ModBlocks and some are also listed
			// explicitly in ModItems#addItemsToItemGroup). NeoForge's accept() throws an
			// IllegalArgumentException on duplicates, so collapse them while keeping the order.
			java.util.Set<net.minecraft.world.item.Item> added = new java.util.LinkedHashSet<>();
			for (var item : items) {
				if (added.add(item)) {
					event.accept(item);
				}
			}
		}
	}
}
