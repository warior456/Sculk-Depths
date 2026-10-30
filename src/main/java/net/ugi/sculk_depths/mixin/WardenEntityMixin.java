package net.ugi.sculk_depths.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.ugi.sculk_depths.tags.ModEntityTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Warden.class)
public class WardenEntityMixin {

    @Inject(method = "canTargetEntity", at = @At("RETURN"), cancellable = true)
    public void doNotAttack(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity != null && entity.getType().is(ModEntityTags.NO_WARDEN_ANGER)) cir.setReturnValue(false);
    }
}
