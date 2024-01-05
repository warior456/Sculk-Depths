package net.ugi.sculk_depths.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.UUID;

@Mixin(ItemStack.class)
public class ItemStackMixin extends Item{
    public ItemStackMixin(Settings settings) {
        super(settings);
    }

    @ModifyExpressionValue(
            method = "getTooltip",
            @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttributeModifier;getId()Ljava/util/UUID;")

    )
    private static UUID fixBug (UUID original){
        if (original.equals(Item.ATTACK_DAMAGE_MODIFIER_ID)) {
            return Item.ATTACK_DAMAGE_MODIFIER_ID;
        }
        return original;
    }

/*    @ModifyExpressionValue(
            method = "getTooltip",
            at = @At(value = "HEAD", target = "Lnet/minecraft/item/Item;ATTACK_DAMAGE_MODIFIER_ID:Ljava/util/UUID")

    )*/

}
