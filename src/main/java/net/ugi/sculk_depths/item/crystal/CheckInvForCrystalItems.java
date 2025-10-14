package net.ugi.sculk_depths.item.crystal;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.ugi.sculk_depths.item.ModComponentTypes;
import net.ugi.sculk_depths.util.enums.CrystalType;

import java.util.List;

public class CheckInvForCrystalItems implements ServerTickEvents.StartWorldTick {

    @Override
    public void onStartTick(ServerWorld world) {
        List<ServerPlayerEntity> serverPlayers = world.getPlayers();
        for (ServerPlayerEntity serverPlayer : serverPlayers) {
            for (ItemStack equippedStack : serverPlayer.getEquippedItems()) {
                if (equippedStack.get(ModComponentTypes.CRYSTAL) == CrystalType.WHITE) {
                    // TODO: stop it from adding the effects instead of removing them every tick
                    //  or just prevent the effects from doing anything
                    serverPlayer.removeStatusEffect(StatusEffects.BLINDNESS);
                    serverPlayer.removeStatusEffect(StatusEffects.DARKNESS);
                    break;
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

//                            int i = serverPlayer.get("statusEffect(StatusEffects.STRENGTH) != null ?
//                                    serverPlayer.get("statusEffect(StatusEffects.STRENGTH).getAmplifier() + 1 : 0;
//                            int j = serverPlayer.get("statusEffect(StatusEffects.STRENGTH) != null ?
//                                    serverPlayer.get("statusEffect(StatusEffects.STRENGTH).getDuration() : 2;