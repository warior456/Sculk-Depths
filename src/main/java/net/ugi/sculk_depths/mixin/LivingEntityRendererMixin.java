package net.ugi.sculk_depths.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.KryslumEnrichedSoilBLock;
import net.ugi.sculk_depths.entity.client.GlomperRenderer;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Objects;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @ModifyReturnValue(
            method = "getRenderLayer",
            at = @At("RETURN")
    )
    private RenderLayer allowOnTopOfKryslumEnrichedSoil(@Nullable RenderLayer original, @Local(ordinal = 0) LivingEntity livingEntity) {
        if(livingEntity.getClass() == GlomperEntity.class){
            return RenderLayer.getEntityTranslucent(GlomperRenderer.GLOMPER_TEXTURE); //TODO unhardcode this
        }
        return original;
    }
}
