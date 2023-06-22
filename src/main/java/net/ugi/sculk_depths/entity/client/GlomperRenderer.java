package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GlomperRenderer extends GeoEntityRenderer<GlomperEntity> {
        public GlomperRenderer(EntityRendererFactory.Context renderManager) {
            super(renderManager, new GlomperModel());
        }

        @Override
        public Identifier getTextureLocation(GlomperEntity animatable) {
            return new Identifier(SculkDepths.MOD_ID, "textures/entity/glomper.png");
        }

    @Override
    protected int getBlockLight(GlomperEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
        public void render(GlomperEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                           VertexConsumerProvider bufferSource, int packedLight) {
            if(entity.isBaby()) {
                poseStack.scale(0.4f, 0.4f, 0.4f);
            }

            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        }
}
