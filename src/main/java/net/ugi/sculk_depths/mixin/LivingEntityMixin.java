package net.ugi.sculk_depths.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.tags.ModEntityTags;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "onKilledBy", forIndex = @At("RETURN"))
    public void sculkDepths$onKilledBy(@Nullable LivingEntity adversary, CallbackInfo ci) {
        if (this.getWorld().isClientSide) {
            return;
        }
        if (adversary instanceof Warden && this.getType().is(ModEntityTags.DROPS_ENERGY_ESSENCE)) {
            ItemEntity itemEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.ENERGY_ESSENCE));
            this.getWorld().spawnEntity(itemEntity);
            this.getWorld().spawnEntity(itemEntity);
        }
    }
}