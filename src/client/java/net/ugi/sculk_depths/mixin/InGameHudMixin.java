package net.ugi.sculk_depths.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Player;
import net.ugi.sculk_depths.entity.effect.ModStatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.client.gui.Gui$HeartType")
public class InGameHudMixin {
    @ModifyExpressionValue(
            method = "forPlayer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;hasEffect(Lnet/minecraft/core/Holder;)Z")
    )
    private static boolean HasInfectedStatusEffect(boolean source, @Local(ordinal = 0) Player player) {
        return player.hasEffect(ModStatusEffects.INFECTED) || source;
    }
}
