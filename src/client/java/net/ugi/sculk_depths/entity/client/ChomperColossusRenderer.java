package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.custom.ChomperColossusEntity;

public class ChomperColossusRenderer extends MobRenderer<ChomperColossusEntity, ChomperColossusModel<ChomperColossusEntity>> {
    private static final ResourceLocation TEXTURE = SculkDepths.id("textures/entity/chomper_colossus.png");

    public ChomperColossusRenderer(EntityRendererProvider.Context context) {
        super(context, new ChomperColossusModel<>(context.bakeLayer(ModModelLayers.CHOMPER_COLOSSUS)), 0.6f);
    }

    @Override
    public ResourceLocation getTextureLocation(ChomperColossusEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(ChomperColossusEntity mobEntity, float f, float g, PoseStack matrixStack,
                       MultiBufferSource vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }
        matrixStack.translate(0,0,0);
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
