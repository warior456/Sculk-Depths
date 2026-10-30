package net.ugi.sculk_depths;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.entity.client.ChomperColossusModel;
import net.ugi.sculk_depths.entity.client.ChomperColossusRenderer;
import net.ugi.sculk_depths.entity.client.GlomperModel;
import net.ugi.sculk_depths.entity.client.GlomperRenderer;
import net.ugi.sculk_depths.entity.client.LesterModel;
import net.ugi.sculk_depths.entity.client.LesterRenderer;
import net.ugi.sculk_depths.entity.client.ModModelLayers;
import net.ugi.sculk_depths.fluid.ModFluids;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.item.custom.crux_resonator.CruxResonator;
import net.ugi.sculk_depths.particle.AuricSporeParticle;
import net.ugi.sculk_depths.particle.CaveFallingParticle;
import net.ugi.sculk_depths.particle.EnergyParticle;
import net.ugi.sculk_depths.particle.ModParticleTypes;
import net.ugi.sculk_depths.particle.ModPortalAnimationParticle;
import net.ugi.sculk_depths.particle.ModPortalParticle;
import net.ugi.sculk_depths.particle.PenebriumSporeParticle;
import net.ugi.sculk_depths.particle.SurfaceWindParticle;
import net.ugi.sculk_depths.render.SculkDepthsDimensionSpecialEffects;
import net.ugi.sculk_depths.screen.ModScreenHandlers;
import net.ugi.sculk_depths.screen.ZygrinFurnaceScreen;
import net.ugi.sculk_depths.sound.ConditionalSoundPlayerClient;
import net.ugi.sculk_depths.sound.SoundPlayerGetterClient;
import net.ugi.sculk_depths.world.dimension.ModDimensions;

@EventBusSubscriber(modid = SculkDepths.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SculkDepthsClient {

	private static final CompassItemPropertyFunction ANGLE_DELEGATE = new CompassItemPropertyFunction((level, stack, entity) -> {
		return CruxResonator.getTrackedPos(stack);
	});

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			CustomItemModels.QuazarithSwordModels();
			CustomItemModels.QuazarithAxeModels();
			CustomItemModels.QuazarithPickaxeModels();
			CustomItemModels.QuazarithHoeModels();
			CustomItemModels.QuazarithShovelModels();
			CustomItemModels.QuazarithHelmetModels();
			CustomItemModels.QuazarithChestplateModels();
			CustomItemModels.QuazarithLeggingsModels();
			CustomItemModels.QuazarithBootsModels();

			SculkDepths.LOGGER.info("Cutting RenderLayers for " + SculkDepths.MOD_ID);
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.VALTROX_SAPLING, RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_VALTROX_SAPLING, RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_PENEBRIUM_SHROOM, RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_AURIC_SPORE_SPROUTS, RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.PENEBRIUM_SHROOM, RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.AURIC_SPORE_SPROUTS, RenderType.cutout());

			ItemBlockRenderTypes.setRenderLayer(ModBlocks.CEPHLERA, RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.CEPHLERA_LIGHT, RenderType.cutout());

			ItemBlockRenderTypes.setRenderLayer(ModBlocks.WHITE_CRYSTAL_BLOCK, RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLUE_CRYSTAL_BLOCK, RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.ORANGE_CRYSTAL_BLOCK, RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.LIME_CRYSTAL_BLOCK, RenderType.translucent());

			ItemBlockRenderTypes.setRenderLayer(ModBlocks.VALTROX_DOOR, RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.VALTROX_TRAPDOOR, RenderType.cutout());

			ItemBlockRenderTypes.setRenderLayer(ModBlocks.COATED_VALTROX_DOOR, RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.COATED_VALTROX_TRAPDOOR, RenderType.cutout());

			ItemBlockRenderTypes.setRenderLayer(ModBlocks.DRIED_VALTROX_DOOR, RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.DRIED_VALTROX_TRAPDOOR, RenderType.cutout());

			ItemBlockRenderTypes.setRenderLayer(ModBlocks.PETRIFIED_VALTROX_DOOR, RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.PETRIFIED_VALTROX_TRAPDOOR, RenderType.cutout());

			ItemBlockRenderTypes.setRenderLayer(ModBlocks.KRYSLUM_FLUMROCK_CAULDRON, RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.SPORE_FLUMROCK_CAULDRON, RenderType.cutout());

			ItemBlockRenderTypes.setRenderLayer(ModBlocks.PENEBRIUM_SHROOM_STEM, RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.PENEBRIUM_SHROOM_BLOCK, RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.PENEBRIUM_SPORE_BLOCK, RenderType.translucent());

			ItemBlockRenderTypes.setRenderLayer(ModBlocks.SCULK_PEDESTAL, RenderType.cutout());

			ItemBlockRenderTypes.setRenderLayer(ModBlocks.AURIC_VINES, RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.AURIC_VINES_END, RenderType.cutout());

			ItemBlockRenderTypes.setRenderLayer(ModBlocks.QELBERRY_BUSH, RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.DEAD_GRASS, RenderType.cutout());

			ItemProperties.register(ModItems.CRUX_RESONATOR, ResourceLocation.withDefaultNamespace("angle"),
					(stack, level, entity, i) -> {
						var pos = CruxResonator.getTrackedPos(stack);
						if (pos == null && level != null) {
							return getSpinningAngle(level);
						}

						return ANGLE_DELEGATE.unclampedCall(stack, level, entity, i);
					});
		});
	}

	@SubscribeEvent
	public static void registerScreens(net.neoforged.neoforge.client.event.RegisterMenuScreensEvent event) {
		event.register(ModScreenHandlers.ZYGRIN_FURNACE_SCREEN_HANDLER, ZygrinFurnaceScreen::new);
	}

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		SculkDepths.LOGGER.info("Registering EntityRenderers for " + SculkDepths.MOD_ID);
		event.registerEntityRenderer(ModEntities.GLOMPER, GlomperRenderer::new);
		event.registerEntityRenderer(ModEntities.LESTER, LesterRenderer::new);
		event.registerEntityRenderer(ModEntities.CHOMPER_COLOSSUS, ChomperColossusRenderer::new);
	}

	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.GLOMPER, GlomperModel::getTexturedModelData);
		event.registerLayerDefinition(ModModelLayers.LESTER, LesterModel::getTexturedModelData);
		event.registerLayerDefinition(ModModelLayers.CHOMPER_COLOSSUS, ChomperColossusModel::getTexturedModelData);
	}

	@SubscribeEvent
	public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(ModParticleTypes.PENEBRIUM_SPORES, PenebriumSporeParticle.Factory::new);
		event.registerSpriteSet(ModParticleTypes.SCULK_DEPTHS_PORTAL_PARTICLE, ModPortalParticle.Factory::new);
		event.registerSpriteSet(ModParticleTypes.SCULK_DEPTHS_PORTAL_ANIMATION_PARTICLE, ModPortalAnimationParticle.Factory::new);
		event.registerSpriteSet(ModParticleTypes.AURIC_SPORES, AuricSporeParticle.Factory::new);
		event.registerSpriteSet(ModParticleTypes.SURFACE_WIND, SurfaceWindParticle.Factory::new);
		event.registerSpriteSet(ModParticleTypes.CAVE_FALLING_PARTICLE, CaveFallingParticle.Factory::new);
		event.registerSpriteSet(ModParticleTypes.ENERGY_PARTICLE, EnergyParticle.Factory::new);
	}

	@SubscribeEvent
	public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
		event.register(ModDimensions.SCULK_DEPTHS_LEVEL_KEY.location(), new SculkDepthsDimensionSpecialEffects());
	}

	@SubscribeEvent
	public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
		event.registerFluidType(new IClientFluidTypeExtensions() {
			private static final ResourceLocation STILL = SculkDepths.id("block/kryslum_still");
			private static final ResourceLocation FLOWING = SculkDepths.id("block/kryslum_flow");

			@Override
			public ResourceLocation getStillTexture() {
				return STILL;
			}

			@Override
			public ResourceLocation getFlowingTexture() {
				return FLOWING;
			}
		}, ModFluids.KRYSLUM_FLUID_TYPE);
	}

	private static float getSpinningAngle(ClientLevel level) {
		return (float) (level.getGameTime() % 32L) / 32.0f;
	}

	@EventBusSubscriber(modid = SculkDepths.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
	public static class ClientGameEvents {
		private static final ConditionalSoundPlayerClient SOUND_PLAYER = new ConditionalSoundPlayerClient();

		@SubscribeEvent
		public static void onClientTickPre(ClientTickEvent.Pre event) {
			Minecraft client = Minecraft.getInstance();
			SoundPlayerGetterClient.player = client.player;
		}

		@SubscribeEvent
		public static void onClientTickPost(ClientTickEvent.Post event) {
			Minecraft client = Minecraft.getInstance();
			if (client.level != null) {
				SOUND_PLAYER.onStartTick(client.level);
			}
		}
	}
}
