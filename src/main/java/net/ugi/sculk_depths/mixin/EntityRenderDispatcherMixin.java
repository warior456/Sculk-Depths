package net.ugi.sculk_depths.mixin;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.ugi.sculk_depths.entity.custom.multipart.ChomperColossusEntity;
import net.ugi.sculk_depths.entity.custom.multipart.ChomperColossusPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {//todo make better
    @Inject(method = "renderHitbox", at = @At("TAIL"))
    private static void renderHitboxOfMultipartEntity(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta, CallbackInfo ci ) {
        if (entity instanceof ChomperColossusEntity) {
            double d = -MathHelper.lerp((double)tickDelta, entity.lastRenderX, entity.getX());
            double e = -MathHelper.lerp((double)tickDelta, entity.lastRenderY, entity.getY());
            double f = -MathHelper.lerp((double)tickDelta, entity.lastRenderZ, entity.getZ());
            for (ChomperColossusPart chomperColossusPart : ((ChomperColossusEntity)entity).getBodyParts()) {
                matrices.push();
                double g = d + MathHelper.lerp((double)tickDelta, chomperColossusPart.lastRenderX, chomperColossusPart.getX());
                double h = e + MathHelper.lerp((double)tickDelta, chomperColossusPart.lastRenderY, chomperColossusPart.getY());
                double i = f + MathHelper.lerp((double)tickDelta, chomperColossusPart.lastRenderZ, chomperColossusPart.getZ());
                matrices.translate(g, h, i);
                WorldRenderer.drawBox(matrices, vertices, chomperColossusPart.getBoundingBox().offset(-chomperColossusPart.getX(), -chomperColossusPart.getY(), -chomperColossusPart.getZ()), 0.25f, 1.0f, 0.0f, 1.0f);
                matrices.pop();
            }
        }
    }
}
