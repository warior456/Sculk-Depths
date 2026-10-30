package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.ugi.sculk_depths.entity.animations.ModAnimations;
import net.ugi.sculk_depths.entity.custom.ChomperColossusEntity;

public class ChomperColossusModel <T extends ChomperColossusEntity> extends HierarchicalModel<T> {

    private final ModelPart chomper_colossus;

    public ChomperColossusModel(ModelPart root) {
        this.chomper_colossus = root.getChild("chomper_colossus");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition chomper_colossus = modelPartData.addOrReplaceChild("chomper_colossus", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = chomper_colossus.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-50.0F, -56.0F, -30.0F, 100.0F, 116.0F, 150.0F, new CubeDeformation(0.0F))
                .texOffs(0, 280).addBox(-61.0F, -68.0F, -111.0F, 120.0F, 130.0F, 100.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -120.0F, 20.0F));

        PartDefinition Mouth = body.addOrReplaceChild("Mouth", CubeListBuilder.create(), PartPose.offset(0.0F, -110.0F, -80.0F));

        PartDefinition nose_r1 = Mouth.addOrReplaceChild("nose_r1", CubeListBuilder.create().texOffs(450, 750).addBox(-45.0F, 11.0F, -94.0F, 90.0F, 10.0F, 90.0F, new CubeDeformation(0.0F))
                .texOffs(430, 0).addBox(-50.0F, -21.0F, -100.0F, 100.0F, 36.0F, 100.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition mouth1_r1 = Mouth.addOrReplaceChild("mouth1_r1", CubeListBuilder.create().texOffs(450, 858).addBox(-45.0F, -23.5F, -95.0F, 90.0F, 10.0F, 90.0F, new CubeDeformation(0.0F))
                .texOffs(430, 180).addBox(-50.0F, -17.5F, -100.0F, 100.0F, 35.0F, 100.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 22.5F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition Horn = body.addOrReplaceChild("Horn", CubeListBuilder.create(), PartPose.offset(0.0F, -220.0F, -130.0F));

        PartDefinition cube_r1 = Horn.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(2, 850).addBox(-27.7459F, -69.9478F, -10.0F, 20.0F, 94.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(61.7459F, -0.0522F, 1.0F, -3.1416F, 0.0F, 2.4435F));

        PartDefinition cube_r2 = Horn.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(100, 850).addBox(-30.7459F, -17.9478F, -10.0F, 20.0F, 94.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(61.7459F, -0.0522F, 1.0F, -3.1416F, 0.0F, -2.618F));

        PartDefinition cube_r3 = Horn.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(200, 850).addBox(20.0F, -76.0F, -10.0F, 20.0F, 94.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-102.0885F, -25.7199F, 1.0F, 0.0F, 0.0F, 0.6981F));

        PartDefinition cube_r4 = Horn.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(300, 850).addBox(-27.0F, -47.0F, 40.0F, 20.0F, 94.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-50.0F, 28.5F, -49.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition legs = body.addOrReplaceChild("legs", CubeListBuilder.create().texOffs(0, 530).addBox(5.0F, -66.0F, 91.0F, 36.0F, 66.0F, 36.0F, new CubeDeformation(0.0F))
                .texOffs(160, 530).addBox(-41.0F, -66.0F, 91.0F, 36.0F, 66.0F, 36.0F, new CubeDeformation(0.0F))
                .texOffs(320, 534).addBox(-41.0F, -66.0F, 33.0F, 36.0F, 66.0F, 36.0F, new CubeDeformation(0.0F))
                .texOffs(640, 530).addBox(10.0F, -66.0F, -60.0F, 40.0F, 66.0F, 36.0F, new CubeDeformation(0.0F))
                .texOffs(800, 530).addBox(-50.0F, -66.0F, -60.0F, 40.0F, 66.0F, 36.0F, new CubeDeformation(0.0F))
                .texOffs(480, 534).addBox(5.0F, -66.0F, 33.0F, 36.0F, 66.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 1024, 1024);
    }
    @Override
    public void setupAnim(ChomperColossusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
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
    public void renderToBuffer(PoseStack pose, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        chomper_colossus.render(pose, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return chomper_colossus;
    }

    public ModelPart getPart() {
        return root();
    }
}
