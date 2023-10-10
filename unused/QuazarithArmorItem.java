package net.ugi.sculk_depths.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.ugi.sculk_depths.item.ModItems;

import java.util.UUID;

public class QuazarithArmorItem extends ArmorItem {

    private final Multimap<EntityAttribute, EntityAttributeModifier> defaultAttributeModifiers;
    public final Multimap<EntityAttribute, EntityAttributeModifier> leggingsAttributeModifiers;

    public QuazarithArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);


        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("Armor modifier", material.getProtection(type), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier("Armor toughness", material.getToughness(), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier("Armor knockback resistance", material.getKnockbackResistance(), EntityAttributeModifier.Operation.ADDITION));
        this.defaultAttributeModifiers = builder.build();
        builder.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("Movement Speed", 2,EntityAttributeModifier.Operation.ADDITION));
        this.leggingsAttributeModifiers = builder.build();
    }

//    @Override
//    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
//        if (slot == this.type.getEquipmentSlot()) {
//            if(this == ModItems.QUAZARITH_LEGGINGS){
//                System.out.println(this);
//                System.out.println(this.getDefaultStack());
//                System.out.println(this.getDefaultStack().getNbt());
//
//
//                return leggingsAttributeModifiers;
//            }
//            return this.defaultAttributeModifiers;
//        }
//        return super.getAttributeModifiers(slot);
//    }
}
