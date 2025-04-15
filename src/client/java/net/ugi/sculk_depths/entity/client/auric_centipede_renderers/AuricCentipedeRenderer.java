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
import net.ugi.sculk_depths.entity.client.auric_centipede_models.AuricCentipedeHeadModel;
import net.ugi.sculk_depths.entity.custom.AuricCentipedeEntity;

public class AuricCentipedeRenderer extends MobEntityRenderer<AuricCentipedeEntity, AuricCentipedeHeadModel<AuricCentipedeEntity>> {
    public static final Identifier AURIC_CENTIPEDE_TEXTURE = SculkDepths.identifier( "textures/entity/auric_centipede/auric_centipede_head.png");

    private final AuricCentipedeBodyRenderer bodyRenderer;
    private final AuricCentipedeEndRenderer endRenderer;

    public AuricCentipedeRenderer(EntityRendererFactory.Context context) {
        super(context, new AuricCentipedeHeadModel<>(context.getPart(ModModelLayers.AURIC_CENTIPEDE_HEAD)), 0.25f);
        this.bodyRenderer = new AuricCentipedeBodyRenderer(context);
        this.endRenderer = new AuricCentipedeEndRenderer(context);
    }

    @Override
    public Identifier getTexture(AuricCentipedeEntity entity) {
        return AURIC_CENTIPEDE_TEXTURE;
    }


    @Override
    public void render(AuricCentipedeEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        matrixStack.push();
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

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, light); //works except transparent
        matrixStack.pop();

        for (int i = 0; i < mobEntity.getParts().length; i++) {
            matrixStack.push();
            matrixStack.translate(mobEntity.getParts()[i].getX() - mobEntity.getX(), mobEntity.getParts()[i].getY() - mobEntity.getY(), mobEntity.getParts()[i].getZ() - mobEntity.getZ());
            if (i < mobEntity.getParts().length - 1) {
                this.bodyRenderer.setIndex(i);
                this.bodyRenderer.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, light); // Custom renderer for the end
            } else {
                this.endRenderer.setIndex(i);
                this.endRenderer.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, light); // Normal body renderer
            }
            matrixStack.pop();
        }
    }
}