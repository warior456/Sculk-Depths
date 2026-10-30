package net.ugi.sculk_depths.item;

import com.google.common.base.Suppliers;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.TagKey;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.function.Supplier;

public enum ModToolMaterials implements Tier {
    QUAZARITH(
            3000,
            38.29f,
            4f,
            ModTags.Blocks.INCORRECT_FOR_QUAZARITH_TOOL,
            30,
            Ingredient.of(ModItems.QUAZARITH_INGOT)

    );

    private final int durability;
    private final float miningspeedMultiplier, damage;
    private final TagKey<Block> incorrectBlocksForDrops;
    private final int enchantmentValue;
    private final Ingredient repairIngredient;

    ModToolMaterials(int durability, float miningspeedMultiplier, float damage, TagKey<Block> incorrectBlocksForDrops, int enchantmentValue, Ingredient repairIngredient) {
        this.durability = durability;
        this.miningspeedMultiplier = miningspeedMultiplier;
        this.damage = damage;
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }


    @Override
    public int getUses() {
        return this.durability;
    }

    @Override
    public float getSpeed() {
        return this.miningspeedMultiplier;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return this.incorrectBlocksForDrops;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }



/*
    //GOLD(MiningLevels.WOOD, 32, 12.0f, 0.0f, 22, () -> Ingredient.ofItems(Items.GOLD_INGOT)),
    QUAZARITH(ModTags.Blocks.INCORRECT_FOR_QUAZARITH_TOOL, 3000, 38.29f, 4.0f, 30, () -> Ingredient.ofItems(ModItems.QUAZARITH_INGOT));
    //deepslate instamine miningspeed: 38.29
    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    private ModToolMaterials(TagKey<Block> inverseTag, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.inverseTag = inverseTag;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }*/
}
