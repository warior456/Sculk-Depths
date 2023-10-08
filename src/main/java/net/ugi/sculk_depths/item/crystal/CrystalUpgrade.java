package net.ugi.sculk_depths.item.crystal;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
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
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.enums.CrystalType;
import net.ugi.sculk_depths.item.ModArmorMaterials;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.item.ModToolMaterials;
import net.ugi.sculk_depths.tags.ModTags;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;


public class CrystalUpgrade extends Item {


    static EquipmentSlot getEquipmentSlot(Item item) {
        if(item == ModItems.QUAZARITH_SHOVEL || item == ModItems.QUAZARITH_AXE || item == ModItems.QUAZARITH_PICKAXE || item == ModItems.QUAZARITH_HOE || item == ModItems.QUAZARITH_SWORD){
            return EquipmentSlot.MAINHAND;
        }
        if(item == ModItems.QUAZARITH_HELMET){
            return EquipmentSlot.HEAD;

        }
        if(item == ModItems.QUAZARITH_CHESTPLATE){
            return EquipmentSlot.CHEST;

        }
        if(item == ModItems.QUAZARITH_LEGGINGS){
            return EquipmentSlot.LEGS;

        }
        if(item == ModItems.QUAZARITH_BOOTS){
            return EquipmentSlot.FEET;

        }
        return null;
    }

    static int[] crystalUpgradeColorArray = {16777215, 65526, 16742144, 1703680};
    static List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");


    public CrystalUpgrade(Settings settings) {
        super(settings);
    }

    public static ActionResult createCrystalUpgrade(ItemStack stack, PlayerEntity player, CrystalType crystal) {
        addNbtToCrystalUpgrade(stack, player, crystal);
        addAttributeToCrystalUpgrade(stack, player, crystal);

        return ActionResult.SUCCESS;
    }
    public static void addAttributeToCrystalUpgrade(ItemStack stack, PlayerEntity player, CrystalType crystal) {
        EquipmentSlot slot = EquipmentSlot.MAINHAND;
        Multimap<EntityAttribute, EntityAttributeModifier> modifiers = stack.getAttributeModifiers(slot);

        if(stack.getItem() == ModItems.QUAZARITH_SHOVEL){
            if (crystal == CrystalType.WHITE){
                modifiers.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(UUID.randomUUID(), "Attack Damage", 2,EntityAttributeModifier.Operation.ADDITION));
            }
        }

        if(stack.getItem() == ModItems.QUAZARITH_AXE){

            if (crystal == CrystalType.WHITE){
                modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier( "Attack Damage", 2,EntityAttributeModifier.Operation.ADDITION));
            }
        }

        if(stack.getItem() == ModItems.QUAZARITH_PICKAXE){

            if (crystal == CrystalType.WHITE){
                modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier( "Attack Damage", 2,EntityAttributeModifier.Operation.ADDITION));
            }
        }


        if(stack.getItem() == ModItems.QUAZARITH_HOE){

            if (crystal == CrystalType.WHITE){
                modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier( "Attack Damage", 2,EntityAttributeModifier.Operation.ADDITION));
            }
        }

        if(stack.getItem() == ModItems.QUAZARITH_SWORD){

            if (crystal == CrystalType.WHITE){
                modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier( "Attack Damage", 2,EntityAttributeModifier.Operation.ADDITION));
            }
        }

        if(stack.getItem() == ModItems.QUAZARITH_HELMET){

            if (crystal == CrystalType.WHITE){
                modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier( "Attack Damage", 2,EntityAttributeModifier.Operation.ADDITION));
            }
        }

        if(stack.getItem() == ModItems.QUAZARITH_CHESTPLATE){

            if (crystal == CrystalType.WHITE){
                modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier( "Attack Damage", 2,EntityAttributeModifier.Operation.ADDITION));
            }
        }

        if(stack.getItem() == ModItems.QUAZARITH_LEGGINGS){

            if (crystal == CrystalType.WHITE){
                modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier( "Attack Damage", 2,EntityAttributeModifier.Operation.ADDITION));
            }
        }

        if(stack.getItem() == ModItems.QUAZARITH_BOOTS){

            if (crystal == CrystalType.WHITE){
                modifiers.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier( "Attack Damage", 2,EntityAttributeModifier.Operation.ADDITION));
            }
        }

        EquipmentSlot slot2 = getEquipmentSlot(stack.getItem());

        modifiers.forEach((entityAttribute, entityAttributeModifier) -> {
            stack.addAttributeModifier(entityAttribute, new EntityAttributeModifier(Item.ATTACK_DAMAGE_MODIFIER_ID, entityAttributeModifier.getName(), entityAttributeModifier.getValue(), entityAttributeModifier.getOperation()) , slot2);
        });
    }

    public static void addNbtToCrystalUpgrade(ItemStack stack, PlayerEntity player, CrystalType crystal) {

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