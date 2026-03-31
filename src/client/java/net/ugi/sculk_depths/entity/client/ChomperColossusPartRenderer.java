package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.custom.ChomperColossusPartEntity;

public class ChomperColossusPartRenderer extends EntityRenderer<ChomperColossusPartEntity> {
    private static final Identifier DUMMY_TEXTURE = SculkDepths.identifier("textures/entity/chomper_colossus.png");

    public ChomperColossusPartRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRender(ChomperColossusPartEntity entity, Frustum frustum, double x, double y, double z) {
        // Keep part entities render-tracked so debug hitboxes remain visible.
        return true;
    }

    @Override
    public void render(ChomperColossusPartEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        // Keep renderer alive for tracking; use vanilla F3+B for hitbox visualization.
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(ChomperColossusPartEntity entity) {
        return DUMMY_TEXTURE;
    }
}


