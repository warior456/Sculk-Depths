package net.ugi.sculk_depths.portal;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.ExecuteCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.ugi.sculk_depths.world.dimension.ModDimensions;

public class PortalUtils {
    public static boolean attemptTeleport(World world, Entity entity, BlockState blockState){
        if(world.isClient) return false;
        if(entity.hasPortalCooldown())return false;
        RegistryKey<DimensionType> currentDimension = entity.getEntityWorld().getDimensionEntry().getKey().get();
        if(currentDimension == ModDimensions.SCULK_DEPTHS_TYPE){
            entity.teleport(world.getServer().getOverworld(), entity.getX(), entity.getY(), entity.getZ(), null, entity.getYaw(), entity.getPitch());
            setPortalCooldown(world, entity);
            return true;
        } else if(currentDimension == DimensionTypes.OVERWORLD) {
            entity.teleport(world.getServer().getWorld(ModDimensions.SCULK_DEPTHS_LEVEL_KEY), entity.getX(), entity.getY(), entity.getZ(), null, entity.getYaw(), entity.getPitch());
            setPortalCooldown(world, entity);
            return true;
        }
        return false;
    }

    public static void setPortalCooldown(World world, Entity entity){
        LivingEntity livingEntity;
        if(entity instanceof LivingEntity){
            livingEntity = (LivingEntity) entity;
            if(livingEntity.isInCreativeMode()){
                entity.setPortalCooldown(100);
            }else {
                entity.setPortalCooldown(100);
            }
        }else {
            entity.setPortalCooldown(100);
        }


    }
}
