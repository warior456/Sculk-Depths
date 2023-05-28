package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;
import software.bernie.example.block.entity.FertilizerBlockEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GlomperModel extends GeoModel<GlomperEntity> {
    @Override
    public Identifier getModelResource(GlomperEntity animatable) {
        return new Identifier(SculkDepths.MOD_ID, "geo/glomper.geo.json");
    }

    @Override
    public RenderLayer getRenderType(GlomperEntity animatable, Identifier texture) {
        return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
    }
    @Override
    public Identifier getTextureResource(GlomperEntity animatable) {
        return new Identifier(SculkDepths.MOD_ID, "textures/entity/glomper.png");
    }


    @Override
    public Identifier getAnimationResource(GlomperEntity animatable) {
        return new Identifier(SculkDepths.MOD_ID, "animations/glomper.animation.json");

    }


    @Override
    public void setCustomAnimations(GlomperEntity animatable, long instanceId, AnimationState<GlomperEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}