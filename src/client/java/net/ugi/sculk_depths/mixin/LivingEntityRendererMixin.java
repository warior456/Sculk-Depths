package net.ugi.sculk_depths.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.ugi.sculk_depths.entity.client.GlomperRenderer;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @ModifyReturnValue(
            method = "getRenderLayer",
            forIndex = @At("RETURN")
    )
    private RenderType AllowGlomperTranslucency(@Nullable RenderType source, @Local(ordinal = 0) LivingEntity livingEntity) {
        if(livingEntity.getClass() == GlomperEntity.class){
            return RenderType.entityTranslucent(GlomperRenderer.GLOMPER_TEXTURE); //TODO unhardcode this
        }
        return source;
    }
}
