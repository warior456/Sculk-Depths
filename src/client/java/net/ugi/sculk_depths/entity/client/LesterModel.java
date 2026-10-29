package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.model.*;
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
		this.lester = root.getAnyDescendantWithName("lester");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData lester = modelPartData.addOrReplaceChild("lester", ModelPartBuilder.create(), ModelTransform.offset(0.0F, 24.0F, 0.0F));

		ModelPartData body = lester.addOrReplaceChild("body", ModelPartBuilder.create(), ModelTransform.offset(0.0F, -11.0F, 0.0F));

		ModelPartData R_leg = body.addOrReplaceChild("R_leg", ModelPartBuilder.create().texOffs(22, 33).cuboid(-2.5F, 0.0F, -3.0F, 5.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.offset(2.5F, 5.0F, 0.0F));

		ModelPartData headParts = body.addOrReplaceChild("head", ModelPartBuilder.create(), ModelTransform.offset(0.0F, -6.1F, 4.0F));

		ModelPartData cube_r1 = headParts.addOrReplaceChild("cube_r1", ModelPartBuilder.create().texOffs(0, 20).cuboid(-6.0F, -4.125F, -9.5F, 12.0F, 3.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.875F, -0.5F, -0.7418F, 0.0F, 0.0F));

		ModelPartData cube_r2 = headParts.addOrReplaceChild("cube_r2", ModelPartBuilder.create().texOffs(4, 5).cuboid(-5.0F, -1.7F, 0.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.texOffs(2, 5).cuboid(-1.0F, -1.7F, 0.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.texOffs(0, 1).cuboid(2.0F, -0.7F, 0.0F, 3.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.texOffs(0, 5).cuboid(3.0F, -1.7F, 0.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.texOffs(0, 0).cuboid(-2.0F, -0.7F, 0.0F, 3.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.texOffs(0, 4).cuboid(-5.0F, -0.7F, 0.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.25F, -6.0F, 0.3491F, 0.0F, -3.1416F));

		ModelPartData chest = body.addOrReplaceChild("chest", ModelPartBuilder.create().texOffs(0, 6).cuboid(3.0F, -8.25F, -3.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.texOffs(0, 2).cuboid(2.0F, -7.25F, -3.0F, 3.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.texOffs(6, 0).cuboid(-1.0F, -8.25F, -3.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.texOffs(0, 3).cuboid(-2.0F, -7.25F, -3.0F, 3.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.texOffs(4, 4).cuboid(-5.0F, -7.25F, -3.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.texOffs(6, 1).cuboid(-5.0F, -8.25F, -3.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.texOffs(0, 0).cuboid(-6.0F, -6.25F, -4.0F, 12.0F, 12.0F, 8.0F, new Dilation(0.0F)), ModelTransform.offset(0.0F, 0.0F, 0.0F));

		ModelPartData L_leg = body.addOrReplaceChild("L_leg", ModelPartBuilder.create().texOffs(0, 33).cuboid(-2.5F, 0.0F, -3.0F, 5.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.offset(-2.5F, 5.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(LesterEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float lowerHeadTick) {
		this.getPart().traverse().forEach(ModelPart::resetPose);
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
	public void render(PoseStack pose, VertexConsumer vertexConsumer, int light, int overlay, int setColor) {
		lester.render(pose, vertexConsumer, light, overlay, setColor);
	}

	@Override
	public ModelPart getPart() {
		return lester;
	}


}