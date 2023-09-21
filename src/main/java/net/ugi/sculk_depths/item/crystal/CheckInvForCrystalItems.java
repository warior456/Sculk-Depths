package net.ugi.sculk_depths.item.crystal;

import com.google.gson.GsonBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.AttributeCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.ugi.sculk_depths.item.ModItems;

import java.util.List;
import java.util.UUID;


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
                            //AttributeCommand.register();


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

    /*private static int executeModifierAdd(ServerCommandSource source, Entity
            target, RegistryEntry< EntityAttribute > attribute, UUID uuid, String name, double value, EntityAttributeModifier.Operation operation) throws
            CommandSyntaxException {
        net.minecraft.entity.attribute.EntityAttributeModifier entityAttributeModifier;
        EntityAttributeInstance entityAttributeInstance = AttributeCommand.getAttributeInstance(target, attribute);
        entityAttributeInstance.addPersistentModifier(entityAttributeModifier);
        source.sendFeedback(() -> Text.translatable("commands.attribute.modifier.add.success", uuid, AttributeCommand.getName(attribute), target.getName()), false);
        return 1;
    }

    private static EntityAttributeInstance getAttributeInstance(Entity entity, RegistryEntry<EntityAttribute> attribute) throws CommandSyntaxException {
        EntityAttributeInstance entityAttributeInstance = AttributeCommand.getLivingEntity(entity).getAttributes().getCustomInstance(attribute);
        return entityAttributeInstance;
    }

    private static LivingEntity getLivingEntity(Entity entity) throws CommandSyntaxException {
        if (!(entity instanceof LivingEntity)) {
            throw ENTITY_FAILED_EXCEPTION.create(entity.getName());
        }
        return (LivingEntity)entity;
    }*/
}

//                            int i = serverPlayer.getStatusEffect(StatusEffects.STRENGTH) != null ?
//                                    serverPlayer.getStatusEffect(StatusEffects.STRENGTH).getAmplifier() + 1 : 0;
//                            int j = serverPlayer.getStatusEffect(StatusEffects.STRENGTH) != null ?
//                                    serverPlayer.getStatusEffect(StatusEffects.STRENGTH).getDuration() : 2;