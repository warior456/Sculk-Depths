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
import net.ugi.sculk_depths.entity.custom.GlomperEntity;

public class GlomperRenderer extends MobEntityRenderer<GlomperEntity, GlomperModel<GlomperEntity>> {
    public static final Identifier GLOMPER_TEXTURE = SculkDepths.identifier( "textures/entity/glomper.png");

    public GlomperRenderer(EntityRendererFactory.Context context) {
        super(context, new GlomperModel<>(context.getPart(ModModelLayers.GLOMPER)), 0.25f);
        //this.addFeature(new GlomperOverlayFeatureRenderer<GlomperEntity>(this, context.getModelLoader()));
    }
    @Override
    protected int getBlockLight(GlomperEntity entity, BlockPos pos) {
            return 15;
    }

    @Override
    public Identifier getTexture(GlomperEntity entity) {
        return GLOMPER_TEXTURE;
    }

    @Override
    protected boolean isVisible(GlomperEntity entity) {
        return false;
    }

    @Override
    public void render(GlomperEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        if (mobEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        boolean bl = minecraftClient.hasOutline(mobEntity) && mobEntity.isInvisible();
        if (mobEntity.isInvisible() && !bl) {
            return;
        }
//        VertexConsumer vertexConsumer = bl ? vertexConsumerProvider.getBuffer(RenderLayer.getOutline(this.getTexture(mobEntity))) :
//                vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(mobEntity)));

        //this.model.render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(mobEntity, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f); //transparent but broken


        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i); //works except transparent
    }
}