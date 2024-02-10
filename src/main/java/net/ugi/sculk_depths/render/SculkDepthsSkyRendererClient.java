package net.ugi.sculk_depths.render;

import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;

public class SculkDepthsSkyRendererClient implements DimensionRenderingRegistry.SkyRenderer {
    @Override
    public void render(WorldRenderContext context) { //needed to remove black sky under y +-63

    }
}
