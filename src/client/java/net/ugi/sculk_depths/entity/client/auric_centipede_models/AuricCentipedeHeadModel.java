package net.ugi.sculk_depths.entity.client.auric_centipede_models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.ugi.sculk_depths.entity.custom.AuricCentipedeEntity;

// Made with Blockbench 4.6.5
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class AuricCentipedeHeadModel<T extends AuricCentipedeEntity> extends SinglePartEntityModel<T> {


	private final ModelPart auric_centipede_head;
	private final ModelPart head;

	public AuricCentipedeHeadModel(ModelPart root) {
		this.auric_centipede_head = root.getChild("auric_centipede_head");
		this.head = root.getChild("auric_centipede_head").getChild("head");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData auric_centipede_head = modelPartData.addChild("auric_centipede_head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 14.0F, 8.0F));

		ModelPartData head = auric_centipede_head.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -6.0F, -14.0F, 16.0F, 11.0F, 14.0F, new Dilation(0.0F))
				.uv(0, 26).cuboid(-7.0F, -5.0F, -13.0F, 14.0F, 7.0F, 13.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create().uv(0, 47).cuboid(-7.0F, 0.0F, -13.0F, 14.0F, 4.0F, 13.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

		ModelPartData tendrilLeft = head.addChild("tendrilLeft", ModelPartBuilder.create(), ModelTransform.pivot(7.0F, -2.0F, -9.0F));

		ModelPartData cube_r1 = tendrilLeft.addChild("cube_r1", ModelPartBuilder.create().uv(48, -1).cuboid(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		ModelPartData tendrilRight = head.addChild("tendrilRight", ModelPartBuilder.create(), ModelTransform.pivot(7.0F, -2.0F, -9.0F));

		ModelPartData cube_r2 = tendrilRight.addChild("cube_r2", ModelPartBuilder.create().uv(48, -7).cuboid(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-14.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(AuricCentipedeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -45.0F, 45.0F);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.027453292F;
	}


	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		auric_centipede_head.render(matrices, vertexConsumer, light, overlay, color);
	}

	@Override
	public ModelPart getPart() {
		return auric_centipede_head;
	}
}