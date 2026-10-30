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
import net.ugi.sculk_depths.entity.custom.LesterEntity;

// Made with Blockbench 4.6.5
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class LesterModel <T extends LesterEntity> extends HierarchicalModel<T> {
	private final ModelPart lester;

	public LesterModel(ModelPart root) {
		this.lester = root.getChild("lester");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition lester = modelPartData.addOrReplaceChild("lester", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = lester.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, 0.0F));

		PartDefinition R_leg = body.addOrReplaceChild("R_leg", CubeListBuilder.create().texOffs(22, 33).addBox(-2.5F, 0.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 5.0F, 0.0F));

		PartDefinition headParts = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -6.1F, 4.0F));

		PartDefinition cube_r1 = headParts.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 20).addBox(-6.0F, -4.125F, -9.5F, 12.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.875F, -0.5F, -0.7418F, 0.0F, 0.0F));

		PartDefinition cube_r2 = headParts.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(4, 5).addBox(-5.0F, -1.7F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(2, 5).addBox(-1.0F, -1.7F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 1).addBox(2.0F, -0.7F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 5).addBox(3.0F, -1.7F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, -0.7F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-5.0F, -0.7F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.25F, -6.0F, 0.3491F, 0.0F, -3.1416F));

		PartDefinition chest = body.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 6).addBox(3.0F, -8.25F, -3.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 2).addBox(2.0F, -7.25F, -3.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(6, 0).addBox(-1.0F, -8.25F, -3.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 3).addBox(-2.0F, -7.25F, -3.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(4, 4).addBox(-5.0F, -7.25F, -3.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(6, 1).addBox(-5.0F, -8.25F, -3.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-6.0F, -6.25F, -4.0F, 12.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition L_leg = body.addOrReplaceChild("L_leg", CubeListBuilder.create().texOffs(0, 33).addBox(-2.5F, 0.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 5.0F, 0.0F));
		return LayerDefinition.create(modelData, 64, 64);
	}
	@Override
	public void setupAnim(LesterEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		//this.setHeadAngles(netHeadYaw, headPitch);

		this.animateWalk(ModAnimations.LESTER_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(entity.idleAnimationState, ModAnimations.LESTER_IDLE, ageInTicks, 1f);
	}

/*	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}*/


	@Override
	public void renderToBuffer(PoseStack pose, VertexConsumer vertexConsumer, int light, int overlay, int setColor) {
		lester.render(pose, vertexConsumer, light, overlay, setColor);
	}

	@Override
	public ModelPart root() {
		return lester;
	}

	public ModelPart getPart() {
		return root();
	}
}
