package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.custom.ChomperColossusEntity;

public class ChomperColossusRenderer extends MobEntityRenderer<ChomperColossusEntity, ChomperColossusModel<ChomperColossusEntity>> {
    private static final Identifier TEXTURE = new Identifier(SculkDepths.MOD_ID, "textures/entity/chomper_colossus.png");

    public ChomperColossusRenderer(EntityRendererFactory.Context context) {
        super(context, new ChomperColossusModel<>(context.getPart(ModModelLayers.CHOMPER_COLOSSUS)), 0.6f);
    }

    @Override
    public Identifier getTexture(ChomperColossusEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(ChomperColossusEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }
        matrixStack.translate(0,0,0);
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}