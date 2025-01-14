package net.ugi.sculk_depths.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.ugi.sculk_depths.entity.effect.ModStatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets= "net.minecraft.client.gui.hud.InGameHud$HeartType")
public class InGameHudMixin {
    @ModifyExpressionValue(
            method = "fromPlayerState",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;hasStatusEffect(Lnet/minecraft/registry/entry/RegistryEntry;)Z")
    )
    private static boolean HasInfectedStatusEffect(boolean original,@Local(ordinal = 0) PlayerEntity player){
        return player.hasStatusEffect(ModStatusEffects.INFECTED) || original;
    }
}
