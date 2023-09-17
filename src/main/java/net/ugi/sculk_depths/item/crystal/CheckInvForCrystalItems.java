package net.ugi.sculk_depths.item.crystal;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.ugi.sculk_depths.item.ModItems;

import java.util.List;

public class CheckInvForCrystalItems implements ServerTickEvents.StartWorldTick{
    @Override
    public void onStartTick(ServerWorld world) {
        List<ServerPlayerEntity> serverPlayers = world.getPlayers();
        for (ServerPlayerEntity serverPlayer : serverPlayers) {
            PlayerEntity player = world.getClosestPlayer(serverPlayer.getX(),serverPlayer.getY(),serverPlayer.getZ(),0.1,false);
            //mainHand
            ItemStack mainHandStack = serverPlayer.getMainHandStack();

            if (mainHandStack.getItem() == ModItems.QUAZARITH_AXE){
                if (mainHandStack.getNbt() != null) {
                    if (mainHandStack.getNbt().get("sculk_depths.crystal") != null) {
                        //white crystal
                        if (mainHandStack.getNbt().get("sculk_depths.crystal").toString().equals("\"white\"")) {

                            serverPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 2, 0, false, false));
                        }
                    }
                }
            }

            //offHand
            ItemStack offHandStack = serverPlayer.getOffHandStack();

            if (offHandStack.getItem() == ModItems.QUAZARITH_AXE){
                if (offHandStack.getNbt() != null) {
                    if (offHandStack.getNbt().get("sculk_depths.crystal") != null) {
                        //white crystal
                        if (offHandStack.getNbt().get("sculk_depths.crystal").toString().equals("\"white\"")) {

                            serverPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 2, 0, false, false));
                        }
                    }
                }
            }
        }
    }
}

//                            int i = serverPlayer.getStatusEffect(StatusEffects.STRENGTH) != null ?
//                                    serverPlayer.getStatusEffect(StatusEffects.STRENGTH).getAmplifier() + 1 : 0;
//                            int j = serverPlayer.getStatusEffect(StatusEffects.STRENGTH) != null ?
//                                    serverPlayer.getStatusEffect(StatusEffects.STRENGTH).getDuration() : 2;