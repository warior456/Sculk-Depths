package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.custom.AuricCentipedeEntity;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;

public class AuricCentipedeRenderer extends MobEntityRenderer<AuricCentipedeEntity, AuricCentipedeModel<AuricCentipedeEntity>> {
    public static final Identifier AURIC_CENTIPEDE_TEXTURE = SculkDepths.identifier( "textures/entity/auric_centipede/auric_centipede_head.png");

    public AuricCentipedeRenderer(EntityRendererFactory.Context context) {
        super(context, new AuricCentipedeModel<>(context.getPart(ModModelLayers.AURIC_CENTIPEDE)), 0.25f);
        //this.addFeature(new GlomperOverlayFeatureRenderer<GlomperEntity>(this, context.getModelLoader()));
    }

    @Override
    public Identifier getTexture(AuricCentipedeEntity entity) {
        return AURIC_CENTIPEDE_TEXTURE;
    }


    @Override
    public void render(AuricCentipedeEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        boolean bl;

        if(mobEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        boolean bl2 = bl = minecraftClient.hasOutline(mobEntity) && mobEntity.isInvisible();
        if (mobEntity.isInvisible() && !bl) {
            return;
        }
        VertexConsumer vertexConsumer = bl ? vertexConsumerProvider.getBuffer(RenderLayer.getOutline(this.getTexture(mobEntity))) : vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(mobEntity)));

        //this.model.render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(mobEntity, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f); //transparent but broken


        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i); //works except transparent
    }
}