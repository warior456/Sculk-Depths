/*package net.ugi.sculk_depths.item.crystal;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ItemActionResult;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.item.ModComponentTypes;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.util.enums.CrystalType;

import java.util.UUID;

public class CrystalUpgrade extends Item {

    public CrystalUpgrade(Settings settings) {
        super(settings);
    }

    static EquipmentSlot getEquipmentSlot(Item item) {
        if (item instanceof ArmorItem armorItem) {
            return armorItem.getSlotType();
        }
        return EquipmentSlot.MAINHAND;
    }

    public static ItemActionResult createCrystalUpgrade(ItemStack stack, PlayerEntity player, CrystalType crystal) {
        addNbtToCrystalUpgrade(stack, player, crystal);
        addAttributeToCrystalUpgrade(stack, player, crystal);
        return ItemActionResult.SUCCESS;
    }

    public static void addAttributeToCrystalUpgrade(ItemStack stack, PlayerEntity player, CrystalType crystal) {
        EquipmentSlot slot = EquipmentSlot.MAINHAND;
        Multimap<EntityAttribute, EntityAttributeModifier> modifiers = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS).modifiers();
        SculkDepths.LOGGER.info(modifiers.toString());
        Multimap<EntityAttribute, EntityAttributeModifier> modifiers2 = HashMultimap.create();

        if (stack.getItem() == ModItems.QUAZARITH_SHOVEL) {
            if (crystal == CrystalType.WHITE) {
                modifiers.put((EntityAttribute) EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(UUID.randomUUID(), "Attack Damage", 2, EntityAttributeModifier.Operation.ADD_VALUE));
            }
        }

        if (stack.getItem() == ModItems.QUAZARITH_AXE) {

            if (crystal == CrystalType.WHITE) {
                modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE.value(), new EntityAttributeModifier(SculkDepths.identifier("attack_damage"), 2, EntityAttributeModifier.Operation.ADD_VALUE));
            }
        }

        if (stack.getItem() == ModItems.QUAZARITH_PICKAXE) {

            if (crystal == CrystalType.WHITE) {
                modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE.value(), new EntityAttributeModifier(SculkDepths.identifier("attack_damage"), 2, EntityAttributeModifier.Operation.ADD_VALUE));
            }
        }


        if (stack.getItem() == ModItems.QUAZARITH_HOE) {

            if (crystal == CrystalType.WHITE) {
                modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE.value(), new EntityAttributeModifier(SculkDepths.identifier("attack_damage"), 2, EntityAttributeModifier.Operation.ADD_VALUE));
            }
        }

        if (stack.getItem() == ModItems.QUAZARITH_SWORD) {

            if (crystal == CrystalType.WHITE) {
                modifiers2 = addAttribute(modifiers, modifiers2, EntityAttributes.GENERIC_ATTACK_DAMAGE, Item.ATTACK_DAMAGE_MODIFIER_ID, "Attack Damage", 20, EntityAttributeModifier.Operation.ADDITION);
                modifiers2 = addAttribute(modifiers, modifiers2, EntityAttributes.GENERIC_ATTACK_SPEED, Item.ATTACK_SPEED_MODIFIER_ID, "Attack Speed", 2, EntityAttributeModifier.Operation.ADDITION);
                modifiers2 = addAttribute(modifiers, modifiers2, EntityAttributes.GENERIC_MOVEMENT_SPEED, UUID.randomUUID(), "Movement Speed", 20, EntityAttributeModifier.Operation.ADDITION);
            }
            if (crystal == CrystalType.ORANGE) {

                modifiers2 = addAttribute(modifiers, modifiers2, EntityAttributes.GENERIC_MOVEMENT_SPEED, UUID.randomUUID(), "Movement Speed", 20, EntityAttributeModifier.Operation.ADDITION);
            }
        }

        if (stack.getItem() == ModItems.QUAZARITH_HELMET) {

            if (crystal == CrystalType.WHITE) {
                modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE.value(), new EntityAttributeModifier(SculkDepths.identifier("attack_damage"), 2, EntityAttributeModifier.Operation.ADD_VALUE));
            }
        }

        if (stack.getItem() == ModItems.QUAZARITH_CHESTPLATE) {

            if (crystal == CrystalType.WHITE) {
                modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE.value(), new EntityAttributeModifier(SculkDepths.identifier("attack_damage"), 2, EntityAttributeModifier.Operation.ADD_VALUE));
            }
        }

        if (stack.getItem() == ModItems.QUAZARITH_LEGGINGS) {

            if (crystal == CrystalType.WHITE) {
                modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE.value(), new EntityAttributeModifier(SculkDepths.identifier("attack_damage"), 2, EntityAttributeModifier.Operation.ADD_VALUE));
            }
        }

        if (stack.getItem() == ModItems.QUAZARITH_BOOTS) {

            if (crystal == CrystalType.WHITE) {
                modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE.value(), new EntityAttributeModifier(SculkDepths.identifier("attack_damage"), 2, EntityAttributeModifier.Operation.ADD_VALUE));
            }
        }

        // to add attributes that haven't been modified with the addAttribute function
        Multimap<EntityAttribute, EntityAttributeModifier> finalModifiers = modifiers2;
        modifiers.forEach((entityAttribute, entityAttributeModifier) -> {
            int[] check = {0};
            finalModifiers.forEach((entityAttribute2, entityAttributeModifier2) -> {
                SculkDepths.LOGGER.info(entityAttribute.toString() + ", " + entityAttributeModifier.toString());
                if (entityAttributeModifier2.getId().equals(entityAttributeModifier.getId())) {
                    check[0] = 1;
                }

            });
            if (check[0] == 0) {
                finalModifiers.put(entityAttribute, entityAttributeModifier);
            }
        });


        EquipmentSlot slot2 = getEquipmentSlot(stack.getItem());

        finalModifiers.forEach((entityAttribute, entityAttributeModifier) -> {                                        //used to be getname() might be broken
            stack.addAttributeModifier(entityAttribute, new EntityAttributeModifier(
                    entityAttributeModifier.getId(),
                    entityAttributeModifier.getOperation().name(),
                    entityAttributeModifier.getValue(),
                    entityAttributeModifier.getOperation()
            ), slot2);
        });
    }

    public static Multimap<EntityAttribute, EntityAttributeModifier> addAttribute(Multimap<EntityAttribute, EntityAttributeModifier> modifiers,Multimap<EntityAttribute, EntityAttributeModifier> modifiers2, EntityAttribute attribute, UUID uuid, String name, double value, EntityAttributeModifier.Operation operation) {
        final int[] check = {0};
        modifiers.forEach((entityAttribute, entityAttributeModifier) -> {
            if (entityAttributeModifier.getId().equals(uuid)) {
                check[0] = 1;
                if (operation == EntityAttributeModifier.Operation.ADDITION) {
                    double originalValue = entityAttributeModifier.getValue();
                    modifiers2.put(attribute, new EntityAttributeModifier(uuid,name, originalValue + value, operation));
                }
                if (operation == EntityAttributeModifier.Operation.MULTIPLY_TOTAL) {
                    double originalValue = entityAttributeModifier.getValue();
                    modifiers2.put(attribute, new EntityAttributeModifier(uuid, name, originalValue * value, operation));
                }
            }

        });
        if (check[0] == 0) {
            modifiers2.put(attribute, new EntityAttributeModifier(uuid, name, value, operation));
        }
        return modifiers2;
    }

    public static void addNbtToCrystalUpgrade(ItemStack stack, PlayerEntity player, CrystalType crystal) {
        stack.set(ModComponentTypes.CRYSTAL, crystal);
    }
}*/
