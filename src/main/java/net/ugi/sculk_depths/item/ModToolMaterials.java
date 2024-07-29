package net.ugi.sculk_depths.item;

import com.google.common.base.Suppliers;

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial {
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
    }
}
