package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.custom.LesterEntity;

public class LesterRenderer extends MobEntityRenderer<LesterEntity, LesterModel<LesterEntity>> {
    private static final Identifier TEXTURE = SculkDepths.identifier( "textures/entity/lester.png");

    public LesterRenderer(EntityRendererFactory.Context context) {
        super(context, new LesterModel<>(context.getPart(ModModelLayers.LESTER)), 0.6f);
    }

    @Override
    public Identifier getTexture(LesterEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(LesterEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}