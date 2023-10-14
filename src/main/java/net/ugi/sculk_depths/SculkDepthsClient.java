package net.ugi.sculk_depths;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.particle.WaterSuspendParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.entity.client.GlomperRenderer;
import net.ugi.sculk_depths.fluid.ModFluids;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.item.crystal.CrystalUpgrade;
import net.ugi.sculk_depths.particle.ModParticleTypes;
import net.ugi.sculk_depths.particle.SurfaceWindParticle;
import net.ugi.sculk_depths.sound.ConditionalSoundPlayer;
import net.ugi.sculk_depths.sound.ConditionalSoundPlayerClient;
import net.ugi.sculk_depths.sound.SoundPlayerGetterClient;

import java.util.Arrays;
import java.util.List;

public class SculkDepthsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        SculkDepths.LOGGER.info("Registering tooltips for " + SculkDepths.MOD_ID);
        CrystalUpgrade.tooltipAdd();

        SculkDepths.LOGGER.info("Registering clientSounds for " + SculkDepths.MOD_ID);
        ClientTickEvents.START_WORLD_TICK.register(new ConditionalSoundPlayerClient());
        ClientTickEvents.START_CLIENT_TICK.register(new SoundPlayerGetterClient());

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.KRYSLUM_STILL,
                ModFluids.KRYSLUM_FLOWING,
                new SimpleFluidRenderHandler(
                        new Identifier("sculk_depths:block/kryslum_still"),
                        new Identifier("sculk_depths:block/kryslum_flow")
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
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VALTROX_SAPLING, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CEPHLERA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CEPHLERA_LIGHT, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOUL_FIRE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WHITE_CRYSTAL_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_CRYSTAL_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ORANGE_CRYSTAL_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIME_CRYSTAL_BLOCK, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SCULK_DEPTHS_PORTAL, RenderLayer.getTranslucent());


        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VALTROX_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VALTROX_TRAPDOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COATED_VALTROX_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COATED_VALTROX_TRAPDOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRIED_VALTROX_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRIED_VALTROX_TRAPDOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PETRIFIED_VALTROX_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PETRIFIED_VALTROX_TRAPDOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.KRYSLUM_FLUMROCK_CAULDRON, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPORE_FLUMROCK_CAULDRON, RenderLayer.getCutout().getTranslucent());

        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.PENEBRIUM_SPORES, WaterSuspendParticle.SporeBlossomAirFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.SURFACE_WIND, SurfaceWindParticle.Factory::new);
    }
}