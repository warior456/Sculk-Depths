package net.ugi.sculk_depths.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.tags.ModEntityTags;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onKilledBy", at = @At("RETURN"))
    public void sculkDepths$onKilledBy(@Nullable LivingEntity adversary, CallbackInfo ci) {
        if (this.getWorld().isClient) {
            return;
        }
        if (adversary instanceof WardenEntity && this.getType().isIn(ModEntityTags.DROPS_ENERGY_ESSENCE)) {
            ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.ENERGY_ESSENCE));
            this.getWorld().spawnEntity(itemEntity);
        }
    }
}