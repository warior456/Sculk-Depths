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
import net.minecraft.util.Mth;
import net.ugi.sculk_depths.entity.animations.ModAnimations;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;

// Made with Blockbench 4.6.5
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class GlomperModel<T extends GlomperEntity> extends HierarchicalModel<T> {


	private final ModelPart glomper;

	public GlomperModel(ModelPart root) {
		this.glomper = root.getChild("glomper");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition glomper = modelPartData.addOrReplaceChild("glomper", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition legs = glomper.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition leg1 = legs.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(4, 36).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, -7.0F, 2.5F));

		PartDefinition leg2 = legs.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(36, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -7.0F, -4.5F));

		PartDefinition leg3 = legs.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 36).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -7.0F, -1.5F));

		PartDefinition leg4 = legs.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -7.0F, 1.5F));

		PartDefinition leg5 = legs.addOrReplaceChild("leg5", CubeListBuilder.create().texOffs(34, 20).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -7.0F, -3.5F));

		PartDefinition leg6 = legs.addOrReplaceChild("leg6", CubeListBuilder.create().texOffs(30, 20).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -7.0F, -0.5F));

		PartDefinition leg7 = legs.addOrReplaceChild("leg7", CubeListBuilder.create().texOffs(4, 20).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -7.0F, -0.5F));

		PartDefinition leg8 = legs.addOrReplaceChild("leg8", CubeListBuilder.create().texOffs(0, 20).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, -7.0F, -3.5F));

		PartDefinition leg9 = legs.addOrReplaceChild("leg9", CubeListBuilder.create().texOffs(8, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, -7.0F, 3.5F));

		PartDefinition leg10 = legs.addOrReplaceChild("leg10", CubeListBuilder.create().texOffs(4, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -7.0F, 4.5F));

		PartDefinition headParts = glomper.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -14.99F, -6.0F, 12.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 20).addBox(-5.0F, -13.99F, -5.0F, 10.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));
		return LayerDefinition.create(modelData, 64, 64);
	}
	@Override
	public void setupAnim(GlomperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch);

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
	public void renderToBuffer(PoseStack pose, VertexConsumer vertexConsumer, int light, int overlay, int setColor) {
		glomper.render(pose, vertexConsumer, light, overlay, setColor);
	}

	@Override
	public ModelPart root() {
		return glomper;
	}

	public ModelPart getPart() {
		return root();
	}
}
