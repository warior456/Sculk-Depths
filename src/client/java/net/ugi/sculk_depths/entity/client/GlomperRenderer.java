package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;

public class GlomperRenderer extends MobRenderer<GlomperEntity, GlomperModel<GlomperEntity>> {
    public static final ResourceLocation GLOMPER_TEXTURE = SculkDepths.id("textures/entity/glomper.png");

    public GlomperRenderer(EntityRendererProvider.Context context) {
        super(context, new GlomperModel<>(context.bakeLayer(ModModelLayers.GLOMPER)), 0.25f);
        //this.addFeature(new GlomperOverlayFeatureRenderer<GlomperEntity>(this, context.getModelLoader()));
    }
    @Override
    protected int getBlockLightLevel(GlomperEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(GlomperEntity entity) {
        return GLOMPER_TEXTURE;
    }


    @Override
    public void render(GlomperEntity mobEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        boolean bl;

        if(mobEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        Minecraft minecraftClient = Minecraft.getInstance();
        boolean bl2 = bl = minecraftClient.shouldEntityAppearGlowing(mobEntity) && mobEntity.isInvisible();
        if (mobEntity.isInvisible() && !bl) {
            return;
        }
        VertexConsumer vertexConsumer = bl ? vertexConsumerProvider.getBuffer(RenderType.outline(this.getTextureLocation(mobEntity))) : vertexConsumerProvider.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(mobEntity)));

        //this.model.render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(mobEntity, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f); //transparent but broken


        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i); //works except transparent
    }
}
