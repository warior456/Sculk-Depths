package net.ugi.sculk_depths.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.ugi.sculk_depths.tags.ModEntityTags;
import net.ugi.sculk_depths.tags.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WardenEntity.class)

public class WardenEntityMixin {

    @Inject(method="isValidTarget", at=@At("RETURN"), cancellable=true)
    public void doNotAttack(Entity entity, CallbackInfoReturnable<Boolean> cir){
        if (entity != null && entity.getType().isIn(ModEntityTags.NO_WARDEN_ANGER)) cir.setReturnValue(false);
    }
    //Lnet/minecraft/entity/mob/WardenEntity;isValidTarget(Lnet/minecraft/entity/Entity;)Z
}
