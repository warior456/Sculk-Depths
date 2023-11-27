package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.entity.animations.ModAnimations;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;
import net.ugi.sculk_depths.entity.custom.LesterEntity;

// Made with Blockbench 4.6.5
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class GlomperModel<T extends GlomperEntity> extends SinglePartEntityModel<T> {


	private final ModelPart glomper;

	public GlomperModel(ModelPart root) {
		this.glomper = root.getChild("glomper");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData glomper = modelPartData.addChild("glomper", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData legs = glomper.addChild("legs", ModelPartBuilder.create().uv(4, 36).cuboid(-5.0F, -7.0F, 2.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F))
				.uv(36, 0).cuboid(0.0F, -7.0F, -5.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 36).cuboid(1.0F, -7.0F, -2.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-1.0F, -7.0F, 1.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F))
				.uv(34, 20).cuboid(-4.0F, -7.0F, -4.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F))
				.uv(30, 20).cuboid(-3.0F, -7.0F, -1.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F))
				.uv(4, 20).cuboid(4.0F, -7.0F, -1.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 20).cuboid(3.0F, -7.0F, -4.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F))
				.uv(8, 0).cuboid(3.0F, -7.0F, 3.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F))
				.uv(4, 0).cuboid(0.0F, -7.0F, 4.0F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head = glomper.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -15.0F, -6.0F, 12.0F, 8.0F, 12.0F, new Dilation(0.0F))
				.uv(0, 20).cuboid(-5.0F, -14.0F, -5.0F, 10.0F, 6.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(GlomperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		//this.setHeadAngles(netHeadYaw, headPitch);

		//this.animateMovement(ModAnimations.LESTER_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAnimationState, ModAnimations.GLOMPER_IDLE, ageInTicks, 1f);
	}

/*	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}*/


	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		glomper.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return glomper;
	}



}