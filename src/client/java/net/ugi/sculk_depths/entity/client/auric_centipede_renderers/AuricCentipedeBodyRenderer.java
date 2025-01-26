package net.ugi.sculk_depths.entity.client.auric_centipede_renderers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.client.ModModelLayers;
import net.ugi.sculk_depths.entity.client.auric_centipede_models.AuricCentipedeBodyModel;
import net.ugi.sculk_depths.entity.custom.AuricCentipedeEntity;

public class AuricCentipedeBodyRenderer extends MobEntityRenderer<AuricCentipedeEntity, AuricCentipedeBodyModel<AuricCentipedeEntity>> {
    public static final Identifier AURIC_CENTIPEDE_TEXTURE = SculkDepths.identifier( "textures/entity/auric_centipede/auric_centipede_body.png");

    public AuricCentipedeBodyRenderer(EntityRendererFactory.Context context) {
        super(context, new AuricCentipedeBodyModel<>(context.getPart(ModModelLayers.AURIC_CENTIPEDE_BODY)), 0.25f);
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

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i); //works except transparent
    }
}