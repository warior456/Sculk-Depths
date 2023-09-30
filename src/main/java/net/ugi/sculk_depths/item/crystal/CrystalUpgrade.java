package net.ugi.sculk_depths.item.crystal;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.enums.CrystalType;
import net.ugi.sculk_depths.item.ModArmorMaterials;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.item.ModToolMaterials;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.Arrays;
import java.util.List;


public class CrystalUpgrade extends Item {


    static int[] crystalUpgradeColorArray = {16777215, 65526, 16742144, 1703680};
    static List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");


    public CrystalUpgrade(Settings settings) {
        super(settings);
    }

    public static ActionResult createCrystalUpgrade(ItemStack stack, PlayerEntity player, CrystalType crystal) {
        addNbtToCrystalUpgrade(stack, player, crystal);

        return ActionResult.SUCCESS;
    }

    public static void addNbtToCrystalUpgrade(ItemStack stack, PlayerEntity player, CrystalType crystal) {

        if(stack.getItem() == ModItems.QUAZARITH_SHOVEL){

            stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Attack Damage", 7.5,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier("Attack Speed", 1,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            if (crystal == CrystalType.WHITE){
                stack.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("Movement Speed", 2,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            }
        }

        if(stack.getItem() == ModItems.QUAZARITH_AXE){

            stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Attack Damage", 12,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier("Attack Speed", 1,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            if (crystal == CrystalType.WHITE){
                stack.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("Movement Speed", 2,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            }
        }

        if(stack.getItem() == ModItems.QUAZARITH_PICKAXE){

            stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Attack Damage", 7,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier("Attack Speed", 2,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            if (crystal == CrystalType.WHITE){
                stack.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("Movement Speed", 2,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            }
        }


        if(stack.getItem() == ModItems.QUAZARITH_HOE){

            stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Attack Damage", 1,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier("Attack Speed", 5,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            if (crystal == CrystalType.WHITE){
                stack.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("Movement Speed", 2,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            }
        }

        if(stack.getItem() == ModItems.QUAZARITH_SWORD){

            stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Attack Damage", 11,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier("Attack Speed", 2,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            if (crystal == CrystalType.WHITE){
                stack.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("Movement Speed", 2,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            }
        }

        if(stack.getItem() == ModItems.QUAZARITH_HELMET){

            stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("Armor", ModArmorMaterials.QUAZARITH.getProtection(ArmorItem.Type.HELMET),EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.HEAD);
            stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier("Armor Toughness", ModArmorMaterials.QUAZARITH.getToughness(),EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.HEAD);
            stack.addAttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier("Knockback Resistance", ModArmorMaterials.QUAZARITH.getKnockbackResistance(),EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.HEAD);

            if (crystal == CrystalType.WHITE){
                stack.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("Movement Speed", 2,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.HEAD);
            }
        }

        if(stack.getItem() == ModItems.QUAZARITH_CHESTPLATE){

            stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("Armor", ModArmorMaterials.QUAZARITH.getProtection(ArmorItem.Type.CHESTPLATE),EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.CHEST);
            stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier("Movement Speed", ModArmorMaterials.QUAZARITH.getToughness(),EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.CHEST);
            stack.addAttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier("Movement Speed", ModArmorMaterials.QUAZARITH.getKnockbackResistance(),EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.CHEST);

            if (crystal == CrystalType.WHITE){
                stack.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("Movement Speed", 2,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.CHEST);
            }
        }

        if(stack.getItem() == ModItems.QUAZARITH_LEGGINGS){

            stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("Movement Speed", ModArmorMaterials.QUAZARITH.getProtection(ArmorItem.Type.LEGGINGS),EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.LEGS);
            stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier("Movement Speed", ModArmorMaterials.QUAZARITH.getToughness(),EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.LEGS);
            stack.addAttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier("Movement Speed", ModArmorMaterials.QUAZARITH.getKnockbackResistance(),EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.LEGS);

            if (crystal == CrystalType.WHITE){
                stack.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("Movement Speed", 2,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.LEGS);
            }
        }

        if(stack.getItem() == ModItems.QUAZARITH_BOOTS){

            stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("Movement Speed", ModArmorMaterials.QUAZARITH.getProtection(ArmorItem.Type.BOOTS),EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.FEET);
            stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier("Movement Speed", ModArmorMaterials.QUAZARITH.getToughness(),EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.FEET);
            stack.addAttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier("Movement Speed", ModArmorMaterials.QUAZARITH.getKnockbackResistance(),EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.FEET);

            if (crystal == CrystalType.WHITE){
                stack.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("Movement Speed", 2,EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.FEET);
            }
        }



        NbtCompound nbtData = stack.getNbt();

        nbtData.putString("sculk_depths.crystal", crystal.toString());

        stack.setNbt(nbtData);

    }

    public static void tooltipAdd() {

        ItemTooltipCallback.EVENT.register((stack, context, tooltip) -> {
            if (stack.isIn(ModTags.Items.CRYSTAL_UPGRADE_ITEMS)) {
                NbtElement nbtData = stack.getNbt().get("sculk_depths.crystal");

                if (nbtData != null) {
                    int i = crystalItemNbtList.indexOf(nbtData.toString());
                    int crystalUpgradeColor = crystalUpgradeColorArray[i];


                    tooltip.add(1, Text.translatable("tooltip.sculk_depths.crystal_upgrade.tooltip").formatted(Formatting.GRAY));

                    MutableText crystalTooltipText = Text.translatable("tooltip.sculk_depths.crystal_upgrade.crystal." + nbtData);
                    crystalTooltipText.setStyle(crystalTooltipText.getStyle().withColor(crystalUpgradeColor));
                    tooltip.add(2, crystalTooltipText);

                }
            }

        });
    }

}