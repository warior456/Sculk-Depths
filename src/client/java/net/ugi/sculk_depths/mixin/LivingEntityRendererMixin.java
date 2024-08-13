package net.ugi.sculk_depths.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.ugi.sculk_depths.entity.client.GlomperRenderer;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @ModifyReturnValue(
            method = "getRenderLayer",
            at = @At("RETURN")
    )
    private RenderLayer AllowGlomperTranslucency(@Nullable RenderLayer original, @Local(ordinal = 0) LivingEntity livingEntity) {
        if(livingEntity.getClass() == GlomperEntity.class){
            return RenderLayer.getEntityTranslucent(GlomperRenderer.GLOMPER_TEXTURE); //TODO unhardcode this
        }
        return original;
    }
}
