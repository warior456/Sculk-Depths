package net.ugi.sculk_depths.config;

import net.minecraft.item.Items;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.item.QuazarithCrystalUpgrade;
import net.ugi.sculk_depths.item.QuazarithRecipe;

public class ConfigHandler {
    public int config_version = 3;
    public double glomper_health = 20;
    public float glomper_damage = 20;

    public int activate_portal_durability_usage = 15;

    public QuazarithRecipe[] quazarithRecipes = {
            new QuazarithRecipe(ModItems.QUAZARITH_HELMET, Items.NETHERITE_HELMET, 5, 5, 5),
            new QuazarithRecipe(ModItems.QUAZARITH_CHESTPLATE, Items.NETHERITE_CHESTPLATE, 8, 8, 8),
            new QuazarithRecipe(ModItems.QUAZARITH_LEGGINGS, Items.NETHERITE_LEGGINGS, 7, 7, 7),
            new QuazarithRecipe(ModItems.QUAZARITH_BOOTS, Items.NETHERITE_BOOTS, 4, 4, 4),
            new QuazarithRecipe(ModItems.QUAZARITH_SHOVEL, Items.NETHERITE_SHOVEL, 1, 1, 1),
            new QuazarithRecipe(ModItems.QUAZARITH_PICKAXE, Items.NETHERITE_PICKAXE, 3, 3, 3),
            new QuazarithRecipe(ModItems.QUAZARITH_AXE, Items.NETHERITE_AXE, 3, 3, 3),
            new QuazarithRecipe(ModItems.QUAZARITH_HOE, Items.NETHERITE_HOE, 2, 2, 2),
            new QuazarithRecipe(ModItems.QUAZARITH_SWORD, Items.NETHERITE_SWORD, 2, 2, 2),
            new QuazarithRecipe(ModItems.QUAZARITH_INGOT, Items.AIR, 4, 4, 4),
    };

    public QuazarithCrystalUpgrade[] quazarithCrystalUpgrades = {
            new QuazarithCrystalUpgrade(ModItems.QUAZARITH_HELMET, 5, 5),
            new QuazarithCrystalUpgrade(ModItems.QUAZARITH_CHESTPLATE, 8, 8),
            new QuazarithCrystalUpgrade(ModItems.QUAZARITH_LEGGINGS, 7, 7),
            new QuazarithCrystalUpgrade(ModItems.QUAZARITH_BOOTS, 4, 4),
            new QuazarithCrystalUpgrade(ModItems.QUAZARITH_SHOVEL, 1, 1),
            new QuazarithCrystalUpgrade(ModItems.QUAZARITH_PICKAXE, 3, 3),
            new QuazarithCrystalUpgrade(ModItems.QUAZARITH_AXE, 3, 3),
            new QuazarithCrystalUpgrade(ModItems.QUAZARITH_HOE, 2, 2),
            new QuazarithCrystalUpgrade(ModItems.QUAZARITH_SWORD, 2, 2),
    };

}
