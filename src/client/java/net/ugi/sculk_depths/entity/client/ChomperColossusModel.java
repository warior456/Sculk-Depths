package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.model.*;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.ugi.sculk_depths.entity.animations.ModAnimations;
import net.ugi.sculk_depths.entity.custom.ChomperColossusEntity;

public class ChomperColossusModel <T extends ChomperColossusEntity> extends HierarchicalModel<T> {

    private final ModelPart chomper_colossus;

    public ChomperColossusModel(ModelPart root) {
        this.chomper_colossus = root.getAnyDescendantWithName("chomper_colossus");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData chomper_colossus = modelPartData.addOrReplaceChild("chomper_colossus", ModelPartBuilder.create(), ModelTransform.offset(0.0F, 24.0F, 0.0F));

        ModelPartData body = chomper_colossus.addOrReplaceChild("body", ModelPartBuilder.create(), ModelTransform.offset(0.0F, 0.0F, 0.0F));

        ModelPartData body = body.addOrReplaceChild("torso", ModelPartBuilder.create().texOffs(0, 0).cuboid(-50.0F, -56.0F, -30.0F, 100.0F, 116.0F, 150.0F, new Dilation(0.0F))
                .texOffs(0, 280).cuboid(-61.0F, -68.0F, -111.0F, 120.0F, 130.0F, 100.0F, new Dilation(0.0F)), ModelTransform.offset(0.0F, -120.0F, 20.0F));

        ModelPartData Mouth = body.addOrReplaceChild("Mouth", ModelPartBuilder.create(), ModelTransform.offset(0.0F, -110.0F, -80.0F));

        ModelPartData nose_r1 = Mouth.addOrReplaceChild("nose_r1", ModelPartBuilder.create().texOffs(450, 750).cuboid(-45.0F, 11.0F, -94.0F, 90.0F, 10.0F, 90.0F, new Dilation(0.0F))
                .texOffs(430, 0).cuboid(-50.0F, -21.0F, -100.0F, 100.0F, 36.0F, 100.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -15.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData mouth1_r1 = Mouth.addOrReplaceChild("mouth1_r1", ModelPartBuilder.create().texOffs(450, 858).cuboid(-45.0F, -23.5F, -95.0F, 90.0F, 10.0F, 90.0F, new Dilation(0.0F))
                .texOffs(430, 180).cuboid(-50.0F, -17.5F, -100.0F, 100.0F, 35.0F, 100.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 22.5F, 0.0F, 0.1745F, 0.0F, 0.0F));

        ModelPartData Horn = body.addOrReplaceChild("Horn", ModelPartBuilder.create(), ModelTransform.offset(0.0F, -220.0F, -130.0F));

        ModelPartData cube_r1 = Horn.addOrReplaceChild("cube_r1", ModelPartBuilder.create().texOffs(2, 850).cuboid(-27.7459F, -69.9478F, -10.0F, 20.0F, 94.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(61.7459F, -0.0522F, 1.0F, -3.1416F, 0.0F, 2.4435F));

        ModelPartData cube_r2 = Horn.addOrReplaceChild("cube_r2", ModelPartBuilder.create().texOffs(100, 850).cuboid(-30.7459F, -17.9478F, -10.0F, 20.0F, 94.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(61.7459F, -0.0522F, 1.0F, -3.1416F, 0.0F, -2.618F));

        ModelPartData cube_r3 = Horn.addOrReplaceChild("cube_r3", ModelPartBuilder.create().texOffs(200, 850).cuboid(20.0F, -76.0F, -10.0F, 20.0F, 94.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(-102.0885F, -25.7199F, 1.0F, 0.0F, 0.0F, 0.6981F));

        ModelPartData cube_r4 = Horn.addOrReplaceChild("cube_r4", ModelPartBuilder.create().texOffs(300, 850).cuboid(-27.0F, -47.0F, 40.0F, 20.0F, 94.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(-50.0F, 28.5F, -49.0F, 0.0F, 0.0F, -0.5236F));

        ModelPartData legs = body.addOrReplaceChild("legs", ModelPartBuilder.create().texOffs(0, 530).cuboid(5.0F, -66.0F, 91.0F, 36.0F, 66.0F, 36.0F, new Dilation(0.0F))
                .texOffs(160, 530).cuboid(-41.0F, -66.0F, 91.0F, 36.0F, 66.0F, 36.0F, new Dilation(0.0F))
                .texOffs(320, 534).cuboid(-41.0F, -66.0F, 33.0F, 36.0F, 66.0F, 36.0F, new Dilation(0.0F))
                .texOffs(640, 530).cuboid(10.0F, -66.0F, -60.0F, 40.0F, 66.0F, 36.0F, new Dilation(0.0F))
                .texOffs(800, 530).cuboid(-50.0F, -66.0F, -60.0F, 40.0F, 66.0F, 36.0F, new Dilation(0.0F))
                .texOffs(480, 534).cuboid(5.0F, -66.0F, 33.0F, 36.0F, 66.0F, 36.0F, new Dilation(0.0F)), ModelTransform.offset(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 1024, 1024);
    }
    @Override
    public void setAngles(ChomperColossusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float lowerHeadTick) {
        this.getPart().traverse().forEach(ModelPart::resetPose);
        //this.setHeadAngles(netHeadYaw, headPitch);

        //this.animateMovement(ModAnimations.LESTER_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(entity.idleAnimationState, ModAnimations.LESTER_IDLE, ageInTicks, 1f);
    }
/*    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        //chomper_colossus.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        chomper_colossus.render(matrices, vertexConsumer, light, overlay);//todo check if this works as intended
    }*/

    @Override
    public void render(PoseStack pose, VertexConsumer vertexConsumer, int light, int overlay, int setColor) {
        chomper_colossus.render(pose, vertexConsumer, light, overlay, setColor);
    }

    @Override
    public ModelPart getPart() {
        return chomper_colossus;
    }
}