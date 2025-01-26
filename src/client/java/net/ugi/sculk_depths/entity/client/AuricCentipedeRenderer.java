package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.entity.client.auric_centipede_renderers.AuricCentipedeBodyRenderer;
import net.ugi.sculk_depths.entity.client.auric_centipede_renderers.AuricCentipedeEndRenderer;
import net.ugi.sculk_depths.entity.client.auric_centipede_renderers.AuricCentipedeHeadRenderer;
import net.ugi.sculk_depths.entity.custom.AuricCentipedeEntity;
import net.ugi.sculk_depths.entity.util.EntityPart;

public class AuricCentipedeRenderer extends EntityRenderer<AuricCentipedeEntity> {

    private final AuricCentipedeHeadRenderer headRenderer;
    private final AuricCentipedeBodyRenderer bodyRenderer;
    private final AuricCentipedeEndRenderer endRenderer;

    public AuricCentipedeRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.headRenderer = new AuricCentipedeHeadRenderer(context);
        this.bodyRenderer = new AuricCentipedeBodyRenderer(context);
        this.endRenderer = new AuricCentipedeEndRenderer(context);
    }

    @Override
    public void render(AuricCentipedeEntity entity, float entityYaw, float partialTicks, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

        // Render each part of the centipede
        for (EntityPart<AuricCentipedeEntity> part : entity.getParts()) {
            if (part == entity.getHead()) {
                matrices.push();
                this.headRenderer.render(entity, entityYaw, partialTicks, matrices, vertexConsumers, light);
                matrices.pop();
            } else if (part == entity.getBody()) {
                matrices.push();
                this.bodyRenderer.render(entity, entityYaw, partialTicks, matrices, vertexConsumers, light);
                matrices.pop();
            } else if (part == entity.getEnd()) {
                matrices.push();
                this.endRenderer.render(entity, entityYaw, partialTicks, matrices, vertexConsumers, light);
                matrices.pop();
            }
        }
    }

    @Override
    public Identifier getTexture(AuricCentipedeEntity entity) {
        return null;
    }
}
