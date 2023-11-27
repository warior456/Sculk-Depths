package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SlimeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;
import net.ugi.sculk_depths.entity.custom.LesterEntity;

public class GlomperRenderer extends MobEntityRenderer<GlomperEntity, GlomperModel<GlomperEntity>> {
    public static final Identifier GLOMPER_TEXTURE = new Identifier(SculkDepths.MOD_ID, "textures/entity/glomper.png");

    public GlomperRenderer(EntityRendererFactory.Context context) {
        super(context, new GlomperModel<>(context.getPart(ModModelLayers.GLOMPER)), 0.25f);
    }

    @Override
    public Identifier getTexture(GlomperEntity entity) {
        return GLOMPER_TEXTURE;
    }


    @Override
    public void render(GlomperEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
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

        this.model.render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(mobEntity, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
    }



}