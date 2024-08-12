package net.ugi.sculk_depths.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.util.UUIDTypeAdapter;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.command.argument.UuidArgumentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Uuids;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.UUID;

/*
@Mixin(ItemStack.class)
public class ItemStackMixin{

    @ModifyExpressionValue(
            method = "getTooltip",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttributeModifier;getId()Ljava/util/UUID;")

    )
    private static UUID fixBug(UUID original){
        if (original.equals(ItemAccessorMixin.getATTACK_DAMAGE_MODIFIER_ID())) {
            return ItemAccessorMixin.getATTACK_DAMAGE_MODIFIER_ID();
        }
        if (original.equals(ItemAccessorMixin.getATTACK_SPEED_MODIFIER_ID())) {
            return ItemAccessorMixin.getATTACK_SPEED_MODIFIER_ID();
        }
        return original;
    }
}
*/
