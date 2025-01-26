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
    public void render(AuricCentipedeEntity entity, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        //the head is the parent entity
        this.headRenderer.render(entity, f, g, matrices, vertexConsumers, light);

        for (int i = 0; i < entity.getParts().size(); i++) {
            EntityPart<AuricCentipedeEntity> part = entity.getParts().get(i);

            matrices.push();

            matrices.translate(part.getX() - entity.getX(), part.getY() - entity.getY(), part.getZ() - entity.getZ());

            // Apply any rotation or other transformations here if needed
            // For example, you could rotate parts around the entity's yaw angle
//            matrixStack.multiply(Ve.POSITIVE_Y.getRotationQuaternion(entity.getYaw()));

            // Differentiate the last segment (end segment)
            if (i < entity.getParts().size() - 1) {
                this.bodyRenderer.render(entity, f, g, matrices, vertexConsumers, light); // Custom renderer for the end
            } else {
                this.endRenderer.render(entity, f, g, matrices, vertexConsumers, light); // Normal body renderer
            }

            matrices.pop();
        }
    }


    @Override
    public Identifier getTexture(AuricCentipedeEntity entity) {
        return null;
    }
}
