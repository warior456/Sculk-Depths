package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.ugi.sculk_depths.entity.animations.ModAnimations;
import net.ugi.sculk_depths.entity.custom.AuricCentipedeEntity;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;

// Made with Blockbench 4.6.5
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class AuricCentipedeModel<T extends AuricCentipedeEntity> extends SinglePartEntityModel<T> {


	private final ModelPart auric_centipede;

	public AuricCentipedeModel(ModelPart root) {
		this.auric_centipede = root.getChild("auric_centipede_body");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData auric_centipede_body = modelPartData.addChild("auric_centipede_body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = auric_centipede_body.addChild("body", ModelPartBuilder.create().uv(0, 25).cuboid(-7.0F, -5.0F, -8.0F, 14.0F, 7.0F, 16.0F, new Dilation(0.0F))
				.uv(0, 48).cuboid(-6.0F, 2.0F, -8.0F, 12.0F, 4.0F, 16.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-8.0F, -6.0F, -8.0F, 16.0F, 9.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -10.0F, 0.0F));

		ModelPartData fungus = body.addChild("fungus", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 14.0F, 1.0F));

		ModelPartData cube_r1 = fungus.addChild("cube_r1", ModelPartBuilder.create().uv(64, 60).cuboid(0.0F, -5.0F, -2.0F, 0.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -19.0F, 3.0F, 0.0F, 0.7854F, 0.0F));

		ModelPartData cube_r2 = fungus.addChild("cube_r2", ModelPartBuilder.create().uv(64, 60).cuboid(0.0F, -5.0F, -2.0F, 0.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -19.0F, 3.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData fungus2 = body.addChild("fungus2", ModelPartBuilder.create(), ModelTransform.pivot(3.0F, -5.0F, -2.0F));

		ModelPartData cube_r3 = fungus2.addChild("cube_r3", ModelPartBuilder.create().uv(64, 42).cuboid(0.0F, -7.0F, -3.0F, 0.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -1.0F, 0.0F, 0.7854F, 0.0F));

		ModelPartData cube_r4 = fungus2.addChild("cube_r4", ModelPartBuilder.create().uv(64, 42).cuboid(0.0F, -7.0F, -3.0F, 0.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -1.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData legBL = auric_centipede_body.addChild("legBL", ModelPartBuilder.create().uv(64, 0).cuboid(0.0F, -2.0F, -1.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(64, 16).cuboid(4.0F, 0.0F, -1.0F, 1.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, -5.0F, 5.0F, 0.0F, 0.0F, -0.3491F));

		ModelPartData legML = auric_centipede_body.addChild("legML", ModelPartBuilder.create().uv(48, 8).cuboid(0.0F, -2.0F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(70, 16).cuboid(2.0F, 0.0F, -1.0F, 1.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, -6.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		ModelPartData legFL = auric_centipede_body.addChild("legFL", ModelPartBuilder.create().uv(48, 0).cuboid(0.0F, -2.0F, -1.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(76, 16).cuboid(4.0F, 0.0F, -1.0F, 1.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, -5.0F, -5.0F, 0.0F, 0.0F, -0.3491F));

		ModelPartData legBR = auric_centipede_body.addChild("legBR", ModelPartBuilder.create().uv(64, 32).cuboid(2.0F, 0.0F, -1.0F, 1.0F, 7.0F, 2.0F, new Dilation(0.0F))
				.uv(64, 8).cuboid(0.0F, -2.0F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-6.0F, -6.0F, 5.0F, 3.1416F, 0.0F, -2.9671F));

		ModelPartData legMR = auric_centipede_body.addChild("legMR", ModelPartBuilder.create().uv(48, 4).cuboid(0.0F, -2.0F, -1.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(70, 32).cuboid(4.0F, 0.0F, -1.0F, 1.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-6.0F, -5.0F, 0.0F, 3.1416F, 0.0F, -2.7925F));

		ModelPartData legFR = auric_centipede_body.addChild("legFR", ModelPartBuilder.create().uv(76, 32).cuboid(2.0F, 0.0F, -1.0F, 1.0F, 7.0F, 2.0F, new Dilation(0.0F))
				.uv(48, 12).cuboid(0.0F, -2.0F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-6.0F, -6.0F, -5.0F, 3.1416F, 0.0F, -2.9671F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(AuricCentipedeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(ModAnimations.AURIC_CENTIPEDE_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
//		this.updateAnimation(entity.idleAnimationState, ModAnimations.GLOMPER_IDLE, ageInTicks, 1f);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -45.0F, 45.0F);

		this.auric_centipede.yaw = headYaw * 0.017453292F;
		this.	auric_centipede.pitch = headPitch * 0.027453292F;
	}


	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		auric_centipede.render(matrices, vertexConsumer, light, overlay, color);
	}

	@Override
	public ModelPart getPart() {
		return auric_centipede;
	}
}