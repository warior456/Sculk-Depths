package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.PathAwareEntity;
import net.ugi.sculk_depths.entity.animations.ModAnimations;
import net.ugi.sculk_depths.entity.custom.ChomperColossusEntity;
import net.ugi.sculk_depths.entity.custom.LesterEntity;

public class ChomperColossusModel <T extends ChomperColossusEntity> extends SinglePartEntityModel<T> {

    private final ModelPart chomper_colossus;

    public ChomperColossusModel(ModelPart root) {
        this.chomper_colossus = root.getChild("chomper_colossus");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData chomper_colossus = modelPartData.addChild("chomper_colossus", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = chomper_colossus.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData torso = body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-50.0F, -56.0F, -30.0F, 100.0F, 116.0F, 150.0F, new Dilation(0.0F))
                .uv(0, 280).cuboid(-61.0F, -68.0F, -111.0F, 120.0F, 130.0F, 100.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -120.0F, 20.0F));

        ModelPartData Mouth = body.addChild("Mouth", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -110.0F, -80.0F));

        ModelPartData nose_r1 = Mouth.addChild("nose_r1", ModelPartBuilder.create().uv(450, 750).cuboid(-45.0F, 11.0F, -94.0F, 90.0F, 10.0F, 90.0F, new Dilation(0.0F))
                .uv(430, 0).cuboid(-50.0F, -21.0F, -100.0F, 100.0F, 36.0F, 100.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -15.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData mouth1_r1 = Mouth.addChild("mouth1_r1", ModelPartBuilder.create().uv(450, 858).cuboid(-45.0F, -23.5F, -95.0F, 90.0F, 10.0F, 90.0F, new Dilation(0.0F))
                .uv(430, 180).cuboid(-50.0F, -17.5F, -100.0F, 100.0F, 35.0F, 100.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 22.5F, 0.0F, 0.1745F, 0.0F, 0.0F));

        ModelPartData Horn = body.addChild("Horn", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -220.0F, -130.0F));

        ModelPartData cube_r1 = Horn.addChild("cube_r1", ModelPartBuilder.create().uv(2, 850).cuboid(-27.7459F, -69.9478F, -10.0F, 20.0F, 94.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(61.7459F, -0.0522F, 1.0F, -3.1416F, 0.0F, 2.4435F));

        ModelPartData cube_r2 = Horn.addChild("cube_r2", ModelPartBuilder.create().uv(100, 850).cuboid(-30.7459F, -17.9478F, -10.0F, 20.0F, 94.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(61.7459F, -0.0522F, 1.0F, -3.1416F, 0.0F, -2.618F));

        ModelPartData cube_r3 = Horn.addChild("cube_r3", ModelPartBuilder.create().uv(200, 850).cuboid(20.0F, -76.0F, -10.0F, 20.0F, 94.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(-102.0885F, -25.7199F, 1.0F, 0.0F, 0.0F, 0.6981F));

        ModelPartData cube_r4 = Horn.addChild("cube_r4", ModelPartBuilder.create().uv(300, 850).cuboid(-27.0F, -47.0F, 40.0F, 20.0F, 94.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(-50.0F, 28.5F, -49.0F, 0.0F, 0.0F, -0.5236F));

        ModelPartData legs = body.addChild("legs", ModelPartBuilder.create().uv(0, 530).cuboid(5.0F, -66.0F, 91.0F, 36.0F, 66.0F, 36.0F, new Dilation(0.0F))
                .uv(160, 530).cuboid(-41.0F, -66.0F, 91.0F, 36.0F, 66.0F, 36.0F, new Dilation(0.0F))
                .uv(320, 534).cuboid(-41.0F, -66.0F, 33.0F, 36.0F, 66.0F, 36.0F, new Dilation(0.0F))
                .uv(640, 530).cuboid(10.0F, -66.0F, -60.0F, 40.0F, 66.0F, 36.0F, new Dilation(0.0F))
                .uv(800, 530).cuboid(-50.0F, -66.0F, -60.0F, 40.0F, 66.0F, 36.0F, new Dilation(0.0F))
                .uv(480, 534).cuboid(5.0F, -66.0F, 33.0F, 36.0F, 66.0F, 36.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 1024, 1024);
    }
    @Override
    public void setAngles(ChomperColossusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        //this.setHeadAngles(netHeadYaw, headPitch);

        //this.animateMovement(ModAnimations.LESTER_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.updateAnimation(entity.idleAnimationState, ModAnimations.LESTER_IDLE, ageInTicks, 1f);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        chomper_colossus.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return chomper_colossus;
    }
}