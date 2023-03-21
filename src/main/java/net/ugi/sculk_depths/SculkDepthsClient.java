package net.ugi.sculk_depths;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.registry.Registry;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.fluid.ModFluids;

public class SculkDepthsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {


        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.KRYSLUM_STILL, new SimpleFluidRenderHandler(
                SimpleFluidRenderHandler.WATER_STILL, SimpleFluidRenderHandler.WATER_FLOWING, SimpleFluidRenderHandler.WATER_OVERLAY, 0x003A4D));
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.KRYSLUM_FLOWING, new SimpleFluidRenderHandler(
                SimpleFluidRenderHandler.WATER_STILL, SimpleFluidRenderHandler.WATER_FLOWING, SimpleFluidRenderHandler.WATER_OVERLAY, 0x003A4D));


        //if you want to use custom textures they needs to be registered.
        //In this example this is unnecessary because the vanilla water textures are already registered.
        //To register your custom textures use this method.
        //ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
        //    Registry.register(new Identifier("sculk_depths:block/kryslum_fluid_still"));
        //    Registry.register(new Identifier("sculk_depths:block/kryslum_fluid_flowing"));
        //});

        // ...
    }
}