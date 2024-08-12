package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.ugi.sculk_depths.entity.animations.ModAnimations;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;

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
		ModelPartData glomper = modelPartData.addChild("glomper", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

		ModelPartData legs = glomper.addChild("legs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

		ModelPartData leg1 = legs.addChild("leg1", ModelPartBuilder.create().uv(4, 36).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.5F, -7.0F, 2.5F));

		ModelPartData leg2 = legs.addChild("leg2", ModelPartBuilder.create().uv(36, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, -7.0F, -4.5F));

		ModelPartData leg3 = legs.addChild("leg3", ModelPartBuilder.create().uv(0, 36).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, -7.0F, -1.5F));

		ModelPartData leg4 = legs.addChild("leg4", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -7.0F, 1.5F));

		ModelPartData leg5 = legs.addChild("leg5", ModelPartBuilder.create().uv(34, 20).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, -7.0F, -3.5F));

		ModelPartData leg6 = legs.addChild("leg6", ModelPartBuilder.create().uv(30, 20).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, -7.0F, -0.5F));

		ModelPartData leg7 = legs.addChild("leg7", ModelPartBuilder.create().uv(4, 20).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(4.5F, -7.0F, -0.5F));

		ModelPartData leg8 = legs.addChild("leg8", ModelPartBuilder.create().uv(0, 20).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(3.5F, -7.0F, -3.5F));

		ModelPartData leg9 = legs.addChild("leg9", ModelPartBuilder.create().uv(8, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(3.5F, -7.0F, 3.5F));

		ModelPartData leg10 = legs.addChild("leg10", ModelPartBuilder.create().uv(4, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, -7.0F, 4.5F));

		ModelPartData head = glomper.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -14.99F, -6.0F, 12.0F, 8.0F, 12.0F, new Dilation(0.0F))
				.uv(0, 20).cuboid(-5.0F, -13.99F, -5.0F, 10.0F, 6.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 8.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(GlomperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		//this.animateMovement(ModAnimations.LESTER_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAnimationState, ModAnimations.GLOMPER_IDLE, ageInTicks, 1f);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -45.0F, 45.0F);

		this.glomper.yaw = headYaw * 0.017453292F;
		this.glomper.pitch = headPitch * 0.027453292F;
	}


	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		glomper.render(matrices, vertexConsumer, light, overlay, color);
	}

	@Override
	public ModelPart getPart() {
		return glomper;
	}
}