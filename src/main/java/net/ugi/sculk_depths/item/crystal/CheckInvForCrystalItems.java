package net.ugi.sculk_depths.item.crystal;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.ugi.sculk_depths.item.ModItems;

import java.util.ArrayList;
import java.util.List;


public class CheckInvForCrystalItems implements ServerTickEvents.StartWorldTick{

    @Override
    public void onStartTick(ServerWorld world) {
        /*List<ServerPlayerEntity> serverPlayers = world.getPlayers(); //Todo: reenable if game loads
        for (ServerPlayerEntity serverPlayer : serverPlayers) {
            PlayerEntity player = world.getClosestPlayer(serverPlayer.getX(),serverPlayer.getY(),serverPlayer.getZ(),0.1,false);

            Iterable<ItemStack> armor = serverPlayer.getArmorItems();
            ArrayList<ItemStack> armorItems = new ArrayList<ItemStack>();
            armor.forEach(ItemStack -> {
                armorItems.add(ItemStack);
            });

            if(armorItems.get(3).getItem() == ModItems.QUAZARITH_HELMET) {
                ItemStack helmet = armorItems.get(3);
                if (helmet.get(DataComponentTypes.CUSTOM_DATA).getNbt().get("sculk_depths.crystal") != null) {
                    if (helmet.get(DataComponentTypes.CUSTOM_DATA).getNbt().get("sculk_depths.crystal").toString().equals("\"white\"")) {
                        player.removeStatusEffect(StatusEffects.BLINDNESS);
                        player.removeStatusEffect(StatusEffects.DARKNESS);
                    }
                }
            }
            if(armorItems.get(2).getItem() == ModItems.QUAZARITH_CHESTPLATE){
                ItemStack chestplate = armorItems.get(2);
            }
            if(armorItems.get(1).getItem() == ModItems.QUAZARITH_LEGGINGS){

            }
            if(armorItems.get(1).getItem() == ModItems.QUAZARITH_BOOTS){

            }

            //mainHand
            ItemStack mainHandStack = serverPlayer.getMainHandStack();
            if (mainHandStack.getItem() == ModItems.QUAZARITH_AXE){
                if (mainHandStack.get(DataComponentTypes.CUSTOM_DATA).getNbt() != null) {
                    if (mainHandStack.get(DataComponentTypes.CUSTOM_DATA).getNbt().get("sculk_depths.crystal") != null) {
                        //white crystal
                        if (mainHandStack.get(DataComponentTypes.CUSTOM_DATA).getNbt().get("sculk_depths.crystal").toString().equals("\"white\"")) {

                        }
                    }
                }
            }

            //offHand
            ItemStack offHandStack = serverPlayer.getOffHandStack();
            if (offHandStack.getItem() == ModItems.QUAZARITH_AXE){
                if (offHandStack.get(DataComponentTypes.CUSTOM_DATA).getNbt() != null) {
                    if (offHandStack.get(DataComponentTypes.CUSTOM_DATA).getNbt().get("sculk_depths.crystal") != null) {
                        //white crystal
                        if (offHandStack.get(DataComponentTypes.CUSTOM_DATA).getNbt().get("sculk_depths.crystal").toString().equals("\"white\"")) {

                        }
                    }
                }
            }
        }*/
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