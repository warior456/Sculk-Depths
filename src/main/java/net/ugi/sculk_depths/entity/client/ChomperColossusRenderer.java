package net.ugi.sculk_depths.entity.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.custom.multipart.ChomperColossusEntity;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@Environment(value= EnvType.CLIENT)
public class ChomperColossusRenderer extends MobEntityRenderer<ChomperColossusEntity, ChomperColossusModel<ChomperColossusEntity>> {
    private static final Identifier TEXTURE = new Identifier(SculkDepths.MOD_ID, "textures/entity/chomper_colossus.png");

    public ChomperColossusRenderer(EntityRendererFactory.Context context) {//ModelPart head, ModelPart neck, ModelPart jaw, ModelPart body
        super(context, new ChomperColossusModel<>(context.getPart(ModModelLayers.CHOMPER_COLOSSUS)), 0.5f);
    }

/*    public ChomperColossusRenderer(EntityRendererFactory.Context context) {
        super(context, 0.5f);
        this.model = model;
        this.shadowRadius = 0.5f;
        //this.model = new ChomperColossusModel(context.getPart(EntityModelLayers.ENDER_DRAGON));
    }*/

    @Override
    public Identifier getTexture(ChomperColossusEntity entity) {
        return TEXTURE;
    }

/*
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
*/



    private static final Identifier EYE_TEXTURE = new Identifier("textures/entity/enderdragon/dragon_eyes.png");
    private static final RenderLayer DRAGON_CUTOUT = RenderLayer.getEntityCutoutNoCull(TEXTURE);
    private static final RenderLayer DRAGON_DECAL = RenderLayer.getEntityDecal(TEXTURE);
    private static final RenderLayer DRAGON_EYES = RenderLayer.getEyes(EYE_TEXTURE);
    private static final float HALF_SQRT_3 = (float)(Math.sqrt(3.0) / 2.0);


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

/*    @Override
    public void render(ChomperColossusEntity chomperColossusEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        float h = (float)chomperColossusEntity.getSegmentProperties(7, g)[0];
        float j = (float)(chomperColossusEntity.getSegmentProperties(5, g)[1] - chomperColossusEntity.getSegmentProperties(10, g)[1]);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-h));
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(j * 10.0f));
        matrixStack.translate(0.0f, 0.0f, 1.0f);
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.translate(0.0f, -1.501f, 0.0f);
        boolean bl = chomperColossusEntity.hurtTime > 0;
        this.model.animateModel(chomperColossusEntity, 0.0f, 0.0f, g);

            VertexConsumer vertexConsumer3 = vertexConsumerProvider.getBuffer(DRAGON_CUTOUT);
            this.model.render(matrixStack, vertexConsumer3, i, OverlayTexture.getUv(0.0f, bl), 1.0f, 1.0f, 1.0f, 1.0f);

        VertexConsumer vertexConsumer35 = vertexConsumerProvider.getBuffer(DRAGON_EYES);
        this.model.render(matrixStack, vertexConsumer35, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        if (chomperColossusEntity.ticksSinceDeath > 0) {
            float l = ((float)chomperColossusEntity.ticksSinceDeath + g) / 200.0f;
            float m = Math.min(l > 0.8f ? (l - 0.8f) / 0.2f : 0.0f, 1.0f);
            Random random = Random.create(432L);
            VertexConsumer vertexConsumer4 = vertexConsumerProvider.getBuffer(RenderLayer.getLightning());
            matrixStack.push();
            matrixStack.translate(0.0f, -1.0f, -2.0f);
            int n = 0;
            while ((float)n < (l + l * l) / 2.0f * 60.0f) {
                matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(random.nextFloat() * 360.0f));
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(random.nextFloat() * 360.0f));
                matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(random.nextFloat() * 360.0f));
                matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(random.nextFloat() * 360.0f));
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(random.nextFloat() * 360.0f));
                matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(random.nextFloat() * 360.0f + l * 90.0f));
                float o = random.nextFloat() * 20.0f + 5.0f + m * 10.0f;
                float p = random.nextFloat() * 2.0f + 1.0f + m * 2.0f;
                Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
                int q = (int)(255.0f * (1.0f - m));
                ++n;
            }
            matrixStack.pop();
        }
        matrixStack.pop();
        if (chomperColossusEntity.connectedCrystal != null) {
            matrixStack.push();
            float l = (float)(chomperColossusEntity.connectedCrystal.getX() - MathHelper.lerp((double)g, chomperColossusEntity.prevX, chomperColossusEntity.getX()));
            float m = (float)(chomperColossusEntity.connectedCrystal.getY() - MathHelper.lerp((double)g, chomperColossusEntity.prevY, chomperColossusEntity.getY()));
            float r = (float)(chomperColossusEntity.connectedCrystal.getZ() - MathHelper.lerp((double)g, chomperColossusEntity.prevZ, chomperColossusEntity.getZ()));
            net.minecraft.client.render.entity.EnderDragonEntityRenderer.renderCrystalBeam(l, m + EndCrystalEntityRenderer.getYOffset(chomperColossusEntity.connectedCrystal, g), r, g, chomperColossusEntity.age, matrixStack, vertexConsumerProvider, i);
            matrixStack.pop();
        }
        super.render(chomperColossusEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }*/




    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        float f = -16.0f;
        ModelPartData modelPartData2 = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().cuboid("upperlip", -6.0f, -1.0f, -24.0f, 12, 5, 16, 176, 44).cuboid("upperhead", -8.0f, -8.0f, -10.0f, 16, 16, 16, 112, 30).mirrored().cuboid("scale", -5.0f, -12.0f, -4.0f, 2, 4, 6, 0, 0).cuboid("nostril", -5.0f, -3.0f, -22.0f, 2, 2, 4, 112, 0).mirrored().cuboid("scale", 3.0f, -12.0f, -4.0f, 2, 4, 6, 0, 0).cuboid("nostril", 3.0f, -3.0f, -22.0f, 2, 2, 4, 112, 0), ModelTransform.NONE);
        modelPartData2.addChild(EntityModelPartNames.JAW, ModelPartBuilder.create().cuboid(EntityModelPartNames.JAW, -6.0f, 0.0f, -16.0f, 12, 4, 16, 176, 65), ModelTransform.pivot(0.0f, 4.0f, -8.0f));
        modelPartData.addChild(EntityModelPartNames.NECK, ModelPartBuilder.create().cuboid("box", -5.0f, -5.0f, -5.0f, 10, 10, 10, 192, 104).cuboid("scale", -1.0f, -9.0f, -3.0f, 2, 4, 6, 48, 0), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().cuboid(EntityModelPartNames.BODY, -12.0f, 0.0f, -16.0f, 24, 24, 64, 0, 0).cuboid("scale", -1.0f, -6.0f, -10.0f, 2, 6, 12, 220, 53).cuboid("scale", -1.0f, -6.0f, 10.0f, 2, 6, 12, 220, 53).cuboid("scale", -1.0f, -6.0f, 30.0f, 2, 6, 12, 220, 53), ModelTransform.pivot(0.0f, 4.0f, 8.0f));
        ModelPartData modelPartData3 = modelPartData.addChild(EntityModelPartNames.LEFT_WING, ModelPartBuilder.create().mirrored().cuboid(EntityModelPartNames.BONE, 0.0f, -4.0f, -4.0f, 56, 8, 8, 112, 88).cuboid("skin", 0.0f, 0.0f, 2.0f, 56, 0, 56, -56, 88), ModelTransform.pivot(12.0f, 5.0f, 2.0f));
        modelPartData3.addChild(EntityModelPartNames.LEFT_WING_TIP, ModelPartBuilder.create().mirrored().cuboid(EntityModelPartNames.BONE, 0.0f, -2.0f, -2.0f, 56, 4, 4, 112, 136).cuboid("skin", 0.0f, 0.0f, 2.0f, 56, 0, 56, -56, 144), ModelTransform.pivot(56.0f, 0.0f, 0.0f));
        ModelPartData modelPartData4 = modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().cuboid("main", -4.0f, -4.0f, -4.0f, 8, 24, 8, 112, 104), ModelTransform.pivot(12.0f, 20.0f, 2.0f));
        ModelPartData modelPartData5 = modelPartData4.addChild(EntityModelPartNames.LEFT_FRONT_LEG_TIP, ModelPartBuilder.create().cuboid("main", -3.0f, -1.0f, -3.0f, 6, 24, 6, 226, 138), ModelTransform.pivot(0.0f, 20.0f, -1.0f));
        modelPartData5.addChild(EntityModelPartNames.LEFT_FRONT_FOOT, ModelPartBuilder.create().cuboid("main", -4.0f, 0.0f, -12.0f, 8, 4, 16, 144, 104), ModelTransform.pivot(0.0f, 23.0f, 0.0f));
        ModelPartData modelPartData6 = modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().cuboid("main", -8.0f, -4.0f, -8.0f, 16, 32, 16, 0, 0), ModelTransform.pivot(16.0f, 16.0f, 42.0f));
        ModelPartData modelPartData7 = modelPartData6.addChild(EntityModelPartNames.LEFT_HIND_LEG_TIP, ModelPartBuilder.create().cuboid("main", -6.0f, -2.0f, 0.0f, 12, 32, 12, 196, 0), ModelTransform.pivot(0.0f, 32.0f, -4.0f));
        modelPartData7.addChild(EntityModelPartNames.LEFT_HIND_FOOT, ModelPartBuilder.create().cuboid("main", -9.0f, 0.0f, -20.0f, 18, 6, 24, 112, 0), ModelTransform.pivot(0.0f, 31.0f, 4.0f));
        ModelPartData modelPartData8 = modelPartData.addChild(EntityModelPartNames.RIGHT_WING, ModelPartBuilder.create().cuboid(EntityModelPartNames.BONE, -56.0f, -4.0f, -4.0f, 56, 8, 8, 112, 88).cuboid("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56, -56, 88), ModelTransform.pivot(-12.0f, 5.0f, 2.0f));
        modelPartData8.addChild(EntityModelPartNames.RIGHT_WING_TIP, ModelPartBuilder.create().cuboid(EntityModelPartNames.BONE, -56.0f, -2.0f, -2.0f, 56, 4, 4, 112, 136).cuboid("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56, -56, 144), ModelTransform.pivot(-56.0f, 0.0f, 0.0f));
        ModelPartData modelPartData9 = modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().cuboid("main", -4.0f, -4.0f, -4.0f, 8, 24, 8, 112, 104), ModelTransform.pivot(-12.0f, 20.0f, 2.0f));
        ModelPartData modelPartData10 = modelPartData9.addChild(EntityModelPartNames.RIGHT_FRONT_LEG_TIP, ModelPartBuilder.create().cuboid("main", -3.0f, -1.0f, -3.0f, 6, 24, 6, 226, 138), ModelTransform.pivot(0.0f, 20.0f, -1.0f));
        modelPartData10.addChild(EntityModelPartNames.RIGHT_FRONT_FOOT, ModelPartBuilder.create().cuboid("main", -4.0f, 0.0f, -12.0f, 8, 4, 16, 144, 104), ModelTransform.pivot(0.0f, 23.0f, 0.0f));
        ModelPartData modelPartData11 = modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().cuboid("main", -8.0f, -4.0f, -8.0f, 16, 32, 16, 0, 0), ModelTransform.pivot(-16.0f, 16.0f, 42.0f));
        ModelPartData modelPartData12 = modelPartData11.addChild(EntityModelPartNames.RIGHT_HIND_LEG_TIP, ModelPartBuilder.create().cuboid("main", -6.0f, -2.0f, 0.0f, 12, 32, 12, 196, 0), ModelTransform.pivot(0.0f, 32.0f, -4.0f));
        modelPartData12.addChild(EntityModelPartNames.RIGHT_HIND_FOOT, ModelPartBuilder.create().cuboid("main", -9.0f, 0.0f, -20.0f, 18, 6, 24, 112, 0), ModelTransform.pivot(0.0f, 31.0f, 4.0f));
        return TexturedModelData.of(modelData, 256, 256);
    }

}

