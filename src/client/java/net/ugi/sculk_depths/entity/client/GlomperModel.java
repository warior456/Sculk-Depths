package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.model.*;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;
import net.ugi.sculk_depths.entity.animations.ModAnimations;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;

// Made with Blockbench 4.6.5
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class GlomperModel<T extends GlomperEntity> extends HierarchicalModel<T> {


	private final ModelPart glomper;

	public GlomperModel(ModelPart root) {
		this.glomper = root.getAnyDescendantWithName("glomper");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData glomper = modelPartData.addOrReplaceChild("glomper", ModelPartBuilder.create(), ModelTransform.offset(0.0F, 16.0F, 0.0F));

		ModelPartData legs = glomper.addOrReplaceChild("legs", ModelPartBuilder.create(), ModelTransform.offset(0.0F, 8.0F, 0.0F));

		ModelPartData leg1 = legs.addOrReplaceChild("leg1", ModelPartBuilder.create().texOffs(4, 36).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.offset(-4.5F, -7.0F, 2.5F));

		ModelPartData leg2 = legs.addOrReplaceChild("leg2", ModelPartBuilder.create().texOffs(36, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.offset(0.5F, -7.0F, -4.5F));

		ModelPartData leg3 = legs.addOrReplaceChild("leg3", ModelPartBuilder.create().texOffs(0, 36).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.offset(1.5F, -7.0F, -1.5F));

		ModelPartData leg4 = legs.addOrReplaceChild("leg4", ModelPartBuilder.create().texOffs(0, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.offset(-0.5F, -7.0F, 1.5F));

		ModelPartData leg5 = legs.addOrReplaceChild("leg5", ModelPartBuilder.create().texOffs(34, 20).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.offset(-3.5F, -7.0F, -3.5F));

		ModelPartData leg6 = legs.addOrReplaceChild("leg6", ModelPartBuilder.create().texOffs(30, 20).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.offset(-2.5F, -7.0F, -0.5F));

		ModelPartData leg7 = legs.addOrReplaceChild("leg7", ModelPartBuilder.create().texOffs(4, 20).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.offset(4.5F, -7.0F, -0.5F));

		ModelPartData leg8 = legs.addOrReplaceChild("leg8", ModelPartBuilder.create().texOffs(0, 20).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.offset(3.5F, -7.0F, -3.5F));

		ModelPartData leg9 = legs.addOrReplaceChild("leg9", ModelPartBuilder.create().texOffs(8, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.offset(3.5F, -7.0F, 3.5F));

		ModelPartData leg10 = legs.addOrReplaceChild("leg10", ModelPartBuilder.create().texOffs(4, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.offset(0.5F, -7.0F, 4.5F));

		ModelPartData headParts = glomper.addOrReplaceChild("head", ModelPartBuilder.create().texOffs(0, 0).cuboid(-6.0F, -14.99F, -6.0F, 12.0F, 8.0F, 12.0F, new Dilation(0.0F))
				.texOffs(0, 20).cuboid(-5.0F, -13.99F, -5.0F, 10.0F, 6.0F, 10.0F, new Dilation(0.0F)), ModelTransform.offset(0.0F, 8.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(GlomperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float lowerHeadTick) {
		this.getPart().traverse().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, lowerHeadTick);

		//this.animateMovement(ModAnimations.LESTER_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(entity.idleAnimationState, ModAnimations.GLOMPER_IDLE, ageInTicks, 1f);
	}

	private void applyHeadRotation(float yHeadRot, float lowerHeadTick) {
		yHeadRot = Mth.clamp(yHeadRot, -30.0F, 30.0F);
		lowerHeadTick = Mth.clamp(lowerHeadTick, -45.0F, 45.0F);

		this.glomper.yRot = yHeadRot * 0.017453292F;
		this.glomper.xRot = lowerHeadTick * 0.027453292F;
	}


	@Override
	public void render(PoseStack pose, VertexConsumer vertexConsumer, int light, int overlay, int setColor) {
		glomper.render(pose, vertexConsumer, light, overlay, setColor);
	}

	@Override
	public ModelPart getPart() {
		return glomper;
	}
}