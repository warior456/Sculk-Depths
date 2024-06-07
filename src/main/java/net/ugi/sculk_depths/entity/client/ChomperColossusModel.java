package net.ugi.sculk_depths.entity.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.ugi.sculk_depths.entity.animations.ModAnimations;
import net.ugi.sculk_depths.entity.custom.multipart.ChomperColossusEntity;
import org.jetbrains.annotations.Nullable;

@Environment(value= EnvType.CLIENT)
public class ChomperColossusModel <T extends ChomperColossusEntity> extends SinglePartEntityModel<T> {

    private final ModelPart chomper_colossus;
    private final ModelPart body;


    public ChomperColossusModel(ModelPart root) {
        this.chomper_colossus = root.getChild("chomper_colossus");
        this.body = chomper_colossus.getChild("body");
    }



    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData chomper_colossus = modelPartData.addChild("chomper_colossus", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = chomper_colossus.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData torso = body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-50.0F, -56.0F, -30.0F, 100.0F, 116.0F, 150.0F, new Dilation(0.0F))
                .uv(0, 280).cuboid(-61.0F, -68.0F, -111.0F, 120.0F, 130.0F, 100.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -120.0F, 20.0F));

        ModelPartData mouth = body.addChild("mouth", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -110.0F, -80.0F));

        ModelPartData nose_r1 = mouth.addChild("nose_r1", ModelPartBuilder.create().uv(450, 750).cuboid(-45.0F, 11.0F, -94.0F, 90.0F, 10.0F, 90.0F, new Dilation(0.0F))
                .uv(430, 0).cuboid(-50.0F, -21.0F, -100.0F, 100.0F, 36.0F, 100.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -15.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData mouth1_r1 = mouth.addChild("mouth1_r1", ModelPartBuilder.create().uv(450, 858).cuboid(-45.0F, -23.5F, -95.0F, 90.0F, 10.0F, 90.0F, new Dilation(0.0F))
                .uv(430, 180).cuboid(-50.0F, -17.5F, -100.0F, 100.0F, 35.0F, 100.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 22.5F, 0.0F, 0.1745F, 0.0F, 0.0F));

        ModelPartData horn = body.addChild("horn", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -220.0F, -130.0F));

        ModelPartData cube_r1 = horn.addChild("cube_r1", ModelPartBuilder.create().uv(2, 850).cuboid(-27.7459F, -69.9478F, -10.0F, 20.0F, 94.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(61.7459F, -0.0522F, 1.0F, -3.1416F, 0.0F, 2.4435F));

        ModelPartData cube_r2 = horn.addChild("cube_r2", ModelPartBuilder.create().uv(100, 850).cuboid(-30.7459F, -17.9478F, -10.0F, 20.0F, 94.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(61.7459F, -0.0522F, 1.0F, -3.1416F, 0.0F, -2.618F));

        ModelPartData cube_r3 = horn.addChild("cube_r3", ModelPartBuilder.create().uv(200, 850).cuboid(20.0F, -76.0F, -10.0F, 20.0F, 94.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(-102.0885F, -25.7199F, 1.0F, 0.0F, 0.0F, 0.6981F));

        ModelPartData cube_r4 = horn.addChild("cube_r4", ModelPartBuilder.create().uv(300, 850).cuboid(-27.0F, -47.0F, 40.0F, 20.0F, 94.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(-50.0F, 28.5F, -49.0F, 0.0F, 0.0F, -0.5236F));

        ModelPartData legs = body.addChild("legs", ModelPartBuilder.create().uv(0, 530).cuboid(5.0F, -66.0F, 91.0F, 36.0F, 66.0F, 36.0F, new Dilation(0.0F))
                .uv(160, 530).cuboid(-41.0F, -66.0F, 91.0F, 36.0F, 66.0F, 36.0F, new Dilation(0.0F))
                .uv(320, 534).cuboid(-41.0F, -66.0F, 33.0F, 36.0F, 66.0F, 36.0F, new Dilation(0.0F))
                .uv(640, 530).cuboid(10.0F, -66.0F, -60.0F, 40.0F, 66.0F, 36.0F, new Dilation(0.0F))
                .uv(800, 530).cuboid(-50.0F, -66.0F, -60.0F, 40.0F, 66.0F, 36.0F, new Dilation(0.0F))
                .uv(480, 534).cuboid(5.0F, -66.0F, 33.0F, 36.0F, 66.0F, 36.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 1024, 1024);
    }
/*    @Override
    public void setAngles(ChomperColossusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        //this.setHeadAngles(netHeadYaw, headPitch);

        //this.animateMovement(ModAnimations.LESTER_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.updateAnimation(entity.idleAnimationState, ModAnimations.LESTER_IDLE, ageInTicks, 1f);
    }*/
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        chomper_colossus.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return chomper_colossus;
    }




    @Nullable
    private ChomperColossusEntity dragon;
    private float tickDelta;



    @Override
    public void animateModel(ChomperColossusEntity chomperColossusEntity, float f, float g, float h) {
        this.dragon = chomperColossusEntity;
        this.tickDelta = h;
    }

    @Override
    public void setAngles(ChomperColossusEntity enderDragonEntity, float f, float g, float h, float i, float j) {
    }

 /*   @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        float p;
        matrices.push();
        float f = MathHelper.lerp(this.tickDelta, this.dragon.prevWingPosition, this.dragon.wingPosition);
        this.torso.pitch = (float)(Math.sin(f * ((float)Math.PI * 2)) + 1.0) * 0.2f;
        float g = (float)(Math.sin(f * ((float)Math.PI * 2) - 1.0f) + 1.0);
        g = (g * g + g * 2.0f) * 0.05f;
        matrices.translate(0.0f, g - 2.0f, -3.0f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(g * 2.0f));
        float h = 0.0f;
        float i = 20.0f;
        float j = -12.0f;
        float k = 1.5f;
        double[] ds = this.dragon.getSegmentProperties(6, this.tickDelta);
        float l = MathHelper.wrapDegrees((float)(this.dragon.getSegmentProperties(5, this.tickDelta)[0] - this.dragon.getSegmentProperties(10, this.tickDelta)[0]));
        float m = MathHelper.wrapDegrees((float)(this.dragon.getSegmentProperties(5, this.tickDelta)[0] + (double)(l / 2.0f)));
        float n = f * ((float)Math.PI * 2);
        for (int o = 0; o < 5; ++o) {
            double[] es = this.dragon.getSegmentProperties(5 - o, this.tickDelta);
            p = (float)Math.cos((float)o * 0.45f + n) * 0.15f;
*//*            this.neck.yaw = MathHelper.wrapDegrees((float)(es[0] - ds[0])) * ((float)Math.PI / 180) * 1.5f;
            this.neck.pitch = p + this.dragon.getChangeInNeckPitch(o, ds, es) * ((float)Math.PI / 180) * 1.5f * 5.0f;
            this.neck.roll = -MathHelper.wrapDegrees((float)(es[0] - (double)m)) * ((float)Math.PI / 180) * 1.5f;
            this.neck.pivotY = i;
            this.neck.pivotZ = j;
            this.neck.pivotX = h;
            i += MathHelper.sin(this.neck.pitch) * 10.0f;
            j -= MathHelper.cos(this.neck.yaw) * MathHelper.cos(this.neck.pitch) * 10.0f;
            h -= MathHelper.sin(this.neck.yaw) * MathHelper.cos(this.neck.pitch) * 10.0f;
            this.neck.render(matrices, vertices, light, overlay, 1.0f, 1.0f, 1.0f, alpha);*//*
        }*//*
        this.head.pivotY = i;
        this.head.pivotZ = j;
        this.head.pivotX = h;*//*
        double[] fs = this.dragon.getSegmentProperties(0, this.tickDelta);
*//*        this.head.yaw = MathHelper.wrapDegrees((float)(fs[0] - ds[0])) * ((float)Math.PI / 180);
        this.head.pitch = MathHelper.wrapDegrees(this.dragon.getChangeInNeckPitch(6, ds, fs)) * ((float)Math.PI / 180) * 1.5f * 5.0f;
        this.head.roll = -MathHelper.wrapDegrees((float)(fs[0] - (double)m)) * ((float)Math.PI / 180);
        this.head.render(matrices, vertices, light, overlay, 1.0f, 1.0f, 1.0f, alpha);*//*
        matrices.push();
        matrices.translate(0.0f, 1.0f, 0.0f);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-l * 1.5f));
        matrices.translate(0.0f, -1.0f, 0.0f);
        this.body.roll = 0.0f;
        this.body.render(matrices, vertices, light, overlay, 1.0f, 1.0f, 1.0f, alpha);
        float q = f * ((float)Math.PI * 2);

matrices.pop();
        p = -MathHelper.sin(f * ((float)Math.PI * 2)) * 0.0f;
        n = f * ((float)Math.PI * 2);
        i = 10.0f;
        j = 60.0f;
        h = 0.0f;
        ds = this.dragon.getSegmentProperties(11, this.tickDelta);
        for (int r = 0; r < 12; ++r) {
            fs = this.dragon.getSegmentProperties(12 + r, this.tickDelta);
*//*            this.neck.yaw = (MathHelper.wrapDegrees((float)(fs[0] - ds[0])) * 1.5f + 180.0f) * ((float)Math.PI / 180);
            this.neck.pitch = (p += MathHelper.sin((float)r * 0.45f + n) * 0.05f) + (float)(fs[1] - ds[1]) * ((float)Math.PI / 180) * 1.5f * 5.0f;
            this.neck.roll = MathHelper.wrapDegrees((float)(fs[0] - (double)m)) * ((float)Math.PI / 180) * 1.5f;
            this.neck.pivotY = i;
            this.neck.pivotZ = j;
            this.neck.pivotX = h;
            i += MathHelper.sin(this.neck.pitch) * 10.0f;
            j -= MathHelper.cos(this.neck.yaw) * MathHelper.cos(this.neck.pitch) * 10.0f;
            h -= MathHelper.sin(this.neck.yaw) * MathHelper.cos(this.neck.pitch) * 10.0f;
            this.neck.render(matrices, vertices, light, overlay, 1.0f, 1.0f, 1.0f, alpha);*//*
        }
        matrices.pop();
    }*/

    private void setLimbRotation(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float offset, ModelPart wing, ModelPart frontLeg, ModelPart frontLegTip, ModelPart frontFoot, ModelPart hindLeg, ModelPart hindLegTip, ModelPart hindFoot, float alpha) {
        hindLeg.pitch = 1.0f + offset * 0.1f;
        hindLegTip.pitch = 0.5f + offset * 0.1f;
        hindFoot.pitch = 0.75f + offset * 0.1f;
        frontLeg.pitch = 1.3f + offset * 0.1f;
        frontLegTip.pitch = -0.5f - offset * 0.1f;
        frontFoot.pitch = 0.75f + offset * 0.1f;
        wing.render(matrices, vertices, light, overlay, 1.0f, 1.0f, 1.0f, alpha);
        frontLeg.render(matrices, vertices, light, overlay, 1.0f, 1.0f, 1.0f, alpha);
        hindLeg.render(matrices, vertices, light, overlay, 1.0f, 1.0f, 1.0f, alpha);
    }
}