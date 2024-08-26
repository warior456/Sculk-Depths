package net.ugi.sculk_depths;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.entity.client.*;
import net.ugi.sculk_depths.fluid.ModFluids;
import net.ugi.sculk_depths.particle.*;
import net.ugi.sculk_depths.render.SculkDepthsCloudRendererClient;
import net.ugi.sculk_depths.render.SculkDepthsSkyRendererClient;
import net.ugi.sculk_depths.screen.ModScreenHandlers;
import net.ugi.sculk_depths.screen.ZygrinFurnaceScreen;
import net.ugi.sculk_depths.world.dimension.ModDimensions;

public class SculkDepthsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		SculkDepths.LOGGER.info("Registering tooltips for " + SculkDepths.MOD_ID);
		//CrystalUpgrade.tooltipAdd();

		SculkDepths.LOGGER.info("Registering clientSounds for " + SculkDepths.MOD_ID);
/*		ClientTickEvents.START_WORLD_TICK.register(new ConditionalSoundPlayerClient());
		ClientTickEvents.START_CLIENT_TICK.register(new SoundPlayerGetterClient());*/

		FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.KRYSLUM_STILL,
				ModFluids.KRYSLUM_FLOWING,
				new SimpleFluidRenderHandler(
						SculkDepths.identifier( "block/kryslum_still"),
						SculkDepths.identifier( "block/kryslum_flow")
				)
		);


		CustomItemModels.QuazarithSwordModels();
		CustomItemModels.QuazarithAxeModels();
		CustomItemModels.QuazarithPickaxeModels();
		CustomItemModels.QuazarithHoeModels();
		CustomItemModels.QuazarithSwordModels();
		CustomItemModels.QuazarithHelmetModels();
		CustomItemModels.QuazarithChestplateModels();
		CustomItemModels.QuazarithLeggingsModels();
		CustomItemModels.QuazarithBootsModels();


		EntityRendererRegistry.register(ModEntities.GLOMPER, GlomperRenderer::new);
		EntityRendererRegistry.register(ModEntities.LESTER, LesterRenderer::new);
		EntityRendererRegistry.register(ModEntities.CHOMPER_COLOSSUS, ChomperColossusRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.GLOMPER, GlomperModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.LESTER, LesterModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CHOMPER_COLOSSUS, ChomperColossusModel::getTexturedModelData);
		RenderLayer.getEntityTranslucent(GlomperRenderer.GLOMPER_TEXTURE,false);

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VALTROX_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_VALTROX_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_PENEBRIUM_SHROOM, RenderLayer.getCutout());
		//BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_AURIC_SPORE_SPROUTS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PENEBRIUM_SHROOM, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AURIC_SPORE_SPROUTS, RenderLayer.getCutout());

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CEPHLERA, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CEPHLERA_LIGHT, RenderLayer.getCutout());

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOUL_FIRE, RenderLayer.getCutout());

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WHITE_CRYSTAL_BLOCK, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_CRYSTAL_BLOCK, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ORANGE_CRYSTAL_BLOCK, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIME_CRYSTAL_BLOCK, RenderLayer.getTranslucent());

		//BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SCULK_DEPTHS_PORTAL, RenderLayer.getTranslucent());

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VALTROX_DOOR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VALTROX_TRAPDOOR, RenderLayer.getCutout());

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COATED_VALTROX_DOOR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COATED_VALTROX_TRAPDOOR, RenderLayer.getCutout());

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRIED_VALTROX_DOOR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRIED_VALTROX_TRAPDOOR, RenderLayer.getCutout());

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PETRIFIED_VALTROX_DOOR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PETRIFIED_VALTROX_TRAPDOOR, RenderLayer.getCutout());

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.KRYSLUM_FLUMROCK_CAULDRON, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPORE_FLUMROCK_CAULDRON, RenderLayer.getCutout());

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PENEBRIUM_SHROOM_STEM, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PENEBRIUM_SHROOM_BLOCK, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PENEBRIUM_SPORE_BLOCK, RenderLayer.getTranslucent());

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SCULK_PEDESTAL, RenderLayer.getCutout());


		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.PENEBRIUM_SPORES, PenebriumSporeParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.SCULK_DEPTHS_PORTAL_PARTICLE, ModPortalParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.AURIC_SPORES, AuricSporeParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.SURFACE_WIND, SurfaceWindParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.CAVE_FALLING_PARTICLE, CaveFallingParticle.Factory::new);

		DimensionRenderingRegistry.registerCloudRenderer(ModDimensions.SCULK_DEPTHS_LEVEL_KEY, new SculkDepthsCloudRendererClient());
		DimensionRenderingRegistry.registerSkyRenderer(ModDimensions.SCULK_DEPTHS_LEVEL_KEY, new SculkDepthsSkyRendererClient());



		HandledScreens.register(ModScreenHandlers.ZYGRIN_FURNACE_SCREEN_HANDLER, ZygrinFurnaceScreen::new); //if this doesn't work for some reason use the line below instead
		//HandledScreens.register(ModScreenHandlerTypes.ZYGRIN_FURNACE_SCREEN_HANDLER, (HandledScreens.Provider<ZygrinFurnaceScreenHandler, ZygrinFurnaceScreen>) ZygrinFurnaceScreen::new);
	}
}