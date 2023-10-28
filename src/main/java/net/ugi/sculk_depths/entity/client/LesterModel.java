package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.ugi.sculk_depths.entity.animations.ModAnimations;
import net.ugi.sculk_depths.entity.custom.LesterEntity;

// Made with Blockbench 4.6.5
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class LesterModel <T extends LesterEntity> extends SinglePartEntityModel<T> {
	private final ModelPart lester;

	public LesterModel(ModelPart root) {
		this.lester = root.getChild("lester");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData lester = modelPartData.addChild("lester", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData chest = lester.addChild("chest", ModelPartBuilder.create().uv(0, 6).cuboid(3.0F, -8.125F, -7.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(0, 2).cuboid(2.0F, -7.125F, -7.0F, 3.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(6, 0).cuboid(-1.0F, -8.125F, -7.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(0, 3).cuboid(-2.0F, -7.125F, -7.0F, 3.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(4, 4).cuboid(-5.0F, -7.125F, -7.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(6, 1).cuboid(-5.0F, -8.125F, -7.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-6.0F, -6.125F, -8.0F, 12.0F, 12.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.125F, 4.0F));

		ModelPartData h_head = lester.addChild("h_head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -14.0F, 7.5F));

		ModelPartData cube_r1 = h_head.addChild("cube_r1", ModelPartBuilder.create().uv(0, 20).cuboid(-6.0F, -4.125F, -9.5F, 12.0F, 3.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.125F, -4.0F, -0.7418F, 0.0F, 0.0F));

		ModelPartData cube_r2 = h_head.addChild("cube_r2", ModelPartBuilder.create().uv(4, 5).cuboid(-5.5F, 4.875F, -2.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(2, 5).cuboid(-1.5F, 4.875F, -2.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(0, 1).cuboid(1.5F, 5.875F, -2.0F, 3.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(0, 5).cuboid(2.5F, 4.875F, -2.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-2.5F, 5.875F, -2.0F, 3.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(0, 4).cuboid(-5.5F, 5.875F, -2.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -1.125F, -7.5F, 0.0F, 0.0F, -3.1416F));

		ModelPartData hitbox = lester.addChild("hitbox", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData L_leg = lester.addChild("L_leg", ModelPartBuilder.create().uv(0, 33).cuboid(-2.5F, 0.0F, -7.0F, 5.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, -6.0F, 4.0F));

		ModelPartData R_leg = lester.addChild("R_leg", ModelPartBuilder.create().uv(22, 33).cuboid(-2.5F, 0.0F, -7.0F, 5.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, -6.0F, 4.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(LesterEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		//this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(ModAnimations.PORCUPINE_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAnimationState, ModAnimations.PORCUPINE_IDLE, ageInTicks, 1f);
	}

/*	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}*/


	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		lester.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return lester;
	}


}