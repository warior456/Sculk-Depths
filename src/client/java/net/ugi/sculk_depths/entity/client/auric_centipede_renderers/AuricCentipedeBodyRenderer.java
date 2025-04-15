package net.ugi.sculk_depths.entity.client.auric_centipede_renderers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.ugi.sculk_depths.SculkDepths;
//import net.ugi.sculk_depths.entity.client.AuricCentipedeSegmentRenderer;
import net.ugi.sculk_depths.entity.client.ModModelLayers;
import net.ugi.sculk_depths.entity.client.auric_centipede_models.AuricCentipedeBodyModel;
import net.ugi.sculk_depths.entity.custom.AuricCentipedeEntity;

public class AuricCentipedeBodyRenderer extends MobEntityRenderer<AuricCentipedeEntity, AuricCentipedeBodyModel<AuricCentipedeEntity>> {
    public static final Identifier AURIC_CENTIPEDE_TEXTURE = SculkDepths.identifier( "textures/entity/auric_centipede/auric_centipede_body.png");

    int index;

    public AuricCentipedeBodyRenderer(EntityRendererFactory.Context context) {
        super(context, new AuricCentipedeBodyModel<>(context.getPart(ModModelLayers.AURIC_CENTIPEDE_BODY)), 0.25f);
    }

    @Override
    public Identifier getTexture(AuricCentipedeEntity entity) {
        return AURIC_CENTIPEDE_TEXTURE;
    }
    public void setIndex(int pIndex){
        this.index = pIndex;
    }


    @Override
    public void render(AuricCentipedeEntity entity, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        boolean bl;

        if(entity.isBaby()) {
            matrices.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrices.scale(1f, 1f, 1f);
        }

        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        boolean bl2 = bl = minecraftClient.hasOutline(entity) && entity.isInvisible();
        if (entity.isInvisible() && !bl) {
            return;
        }
        VertexConsumer vertexConsumer = bl ? vertexConsumers.getBuffer(RenderLayer.getOutline(this.getTexture(entity))) : vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(entity)));

//        float bodyYawDiff = entity.getParts()[this.index].getbodyYaw() - entity.getParts()[this.index].prevbodyYaw;
//        if (bodyYawDiff > 180) {
//            bodyYawDiff -= 360;
//        } else if (bodyYawDiff < -180) {
//            bodyYawDiff += 360;
//        }
//        float interpolatedbodyYaw = entity.getParts()[this.index].prevbodyYaw + bodyYawDiff * g;

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getParts()[this.index].getYaw()));

        super.render(entity, f, g, matrices, vertexConsumers, light); //works except transparent
        matrices.pop();
    }
}