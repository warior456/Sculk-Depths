package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.custom.LesterEntity;

public class LesterRenderer extends MobRenderer<LesterEntity, LesterModel<LesterEntity>> {
    private static final ResourceLocation TEXTURE = SculkDepths.id("textures/entity/lester.png");

    public LesterRenderer(EntityRendererProvider.Context context) {
        super(context, new LesterModel<>(context.bakeLayer(ModModelLayers.LESTER)), 0.6f);
    }

    @Override
    public ResourceLocation getTextureLocation(LesterEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(LesterEntity mobEntity, float f, float g, PoseStack matrixStack,
                       MultiBufferSource vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
