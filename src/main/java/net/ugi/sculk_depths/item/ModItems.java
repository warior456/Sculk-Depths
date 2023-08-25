package net.ugi.sculk_depths.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.fluid.ModFluids;
import net.ugi.sculk_depths.item.custom.EnergizedFlintAndSteelItem;


public class ModItems {

    public static final Item SOUL_HEART = registerItem("soul_heart",
            new Item(new FabricItemSettings().maxCount(16)));
    public static final Item KRYSLUM_BUCKET = registerItem("kryslum_bucket", new BucketItem(ModFluids.KRYSLUM_STILL,
            new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));
    public static final Item QUAZARITH_INGOT = registerItem("quazarith_ingot",
            new Item(new FabricItemSettings().fireproof()));

    public static final Item QUAZARITH_PIECES = registerItem("quazarith_pieces",
            new Item(new FabricItemSettings()));

    public static final Item CRUX = registerItem("crux",
            new Item(new FabricItemSettings()));

    public static final Item WHITE_CRYSTAL = registerItem("white_crystal",
            new Item(new FabricItemSettings()));

    public static final Item BLUE_CRYSTAL = registerItem("blue_crystal",
            new Item(new FabricItemSettings()));

    public static final Item ORANGE_CRYSTAL = registerItem("orange_crystal",
            new Item(new FabricItemSettings()));

    public static final Item LIME_CRYSTAL = registerItem("lime_crystal",
            new Item(new FabricItemSettings()));


    public static final Item PENEBRIUM_SHINE_SHROOM_SPORE_BUCKET = registerItem("penebrium_shine_mushroom_spore_bucket",
            new Item(new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));

    public static final Item ENERGY_ESSENCE = registerItem("energy_essence",
            new Item(new FabricItemSettings()));
    public static final Item ENERGISED_FLINT_AND_STEEL = registerItem("energized_flint_and_steel",
            new EnergizedFlintAndSteelItem(new FabricItemSettings().maxCount(1).maxDamage(128)));

    public static final Item GLOMPER_SPAWN_EGG = registerItem("glomper_spawn_egg",
            new SpawnEggItem(ModEntities.GLOMPER, 1769607, 42751, new FabricItemSettings()));

    //quazarith tools

    public static final Item QUAZARITH_SHOVEL = registerItem("quazarith_shovel",
            new ShovelItem(ModToolMaterials.QUAZARITH, 2.5F, -3.0F,
                    new FabricItemSettings().fireproof()));
    public static final Item QUAZARITH_PICKAXE = registerItem("quazarith_pickaxe",
            new PickaxeItem(ModToolMaterials.QUAZARITH, 2, -2.0F,
                    new FabricItemSettings().fireproof()));

    public static final Item QUAZARITH_AXE = registerItem("quazarith_axe",
            new AxeItem(ModToolMaterials.QUAZARITH, 7, -3.0F,
                    new FabricItemSettings().fireproof()));

    public static final Item QUAZARITH_HOE = registerItem("quazarith_hoe",
            new HoeItem(ModToolMaterials.QUAZARITH, -4, 1.0F,
                    new FabricItemSettings().fireproof()));

    public static final Item QUAZARITH_SWORD = registerItem("quazarith_sword",
            new SwordItem(ModToolMaterials.QUAZARITH, 6, -2.0F,
                    new FabricItemSettings().fireproof()));


    //quazarith armor
    public static final Item QUAZARITH_HELMET = registerItem("quazarith_helmet",
            new ArmorItem(ModArmorMaterials.QUAZARITH, ArmorItem.Type.HELMET,
                    new FabricItemSettings().fireproof()));
    public static final Item QUAZARITH_CHESTPLATE = registerItem("quazarith_chestplate",
            new ArmorItem(ModArmorMaterials.QUAZARITH, ArmorItem.Type.CHESTPLATE,
                    new FabricItemSettings().fireproof()));
    public static final Item QUAZARITH_LEGGINGS = registerItem("quazarith_leggings",
            new ArmorItem(ModArmorMaterials.QUAZARITH, ArmorItem.Type.LEGGINGS,
                    new FabricItemSettings().fireproof()));
    public static final Item QUAZARITH_BOOTS = registerItem("quazarith_boots",
            new ArmorItem(ModArmorMaterials.QUAZARITH, ArmorItem.Type.BOOTS,
                    new FabricItemSettings().fireproof()));

    public static final Item VALTROX_SIGN = registerItem("valtrox_sign",
            new SignItem(new FabricItemSettings().maxCount(16), ModBlocks.VALTROX_SIGN, ModBlocks.VALTROX_WALL_SIGN));

    public static final Item VALTROX_HANGING_SIGN = registerItem("valtrox_hanging_sign",
            new HangingSignItem( ModBlocks.VALTROX_HANGING_SIGN, ModBlocks.VALTROX_WALL_HANGING_SIGN, new FabricItemSettings().maxCount(16)));

    public static final Item DRIED_VALTROX_SIGN = registerItem("dried_valtrox_sign",
            new SignItem(new FabricItemSettings().maxCount(16), ModBlocks.DRIED_VALTROX_SIGN, ModBlocks.DRIED_VALTROX_WALL_SIGN));

    public static final Item DRIED_VALTROX_HANGING_SIGN = registerItem("dried_valtrox_hanging_sign",
            new HangingSignItem( ModBlocks.DRIED_VALTROX_HANGING_SIGN, ModBlocks.DRIED_VALTROX_WALL_HANGING_SIGN, new FabricItemSettings().maxCount(16)));

    public static final Item PETRIFIED_VALTROX_SIGN = registerItem("petrified_valtrox_sign",
            new SignItem(new FabricItemSettings().maxCount(16), ModBlocks.PETRIFIED_VALTROX_SIGN, ModBlocks.PETRIFIED_VALTROX_WALL_SIGN));

    public static final Item PETRIFIED_VALTROX_HANGING_SIGN = registerItem("petrified_valtrox_hanging_sign",
            new HangingSignItem( ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN, ModBlocks.PETRIFIED_VALTROX_WALL_HANGING_SIGN, new FabricItemSettings().maxCount(16)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(SculkDepths.MOD_ID, name), item);
    }

    public static void addItemsToItemGroup() {

        addToItemGroup(ModItemGroup.SCULK_DEPTHS, KRYSLUM_BUCKET);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, SOUL_HEART);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, ENERGY_ESSENCE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, ENERGISED_FLINT_AND_STEEL);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, GLOMPER_SPAWN_EGG);

        addToItemGroup(ModItemGroup.SCULK_DEPTHS, CRUX);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, WHITE_CRYSTAL);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, BLUE_CRYSTAL);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, ORANGE_CRYSTAL);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, LIME_CRYSTAL);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, PENEBRIUM_SHINE_SHROOM_SPORE_BUCKET);

        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_INGOT);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_PIECES);

        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_SHOVEL);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_PICKAXE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_AXE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_HOE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_SWORD);

        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_HELMET);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_CHESTPLATE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_LEGGINGS);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_BOOTS);

        addToItemGroup(ModItemGroup.SCULK_DEPTHS, VALTROX_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, VALTROX_HANGING_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, DRIED_VALTROX_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, DRIED_VALTROX_HANGING_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, PETRIFIED_VALTROX_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, PETRIFIED_VALTROX_HANGING_SIGN);


    }

    private static void addToItemGroup(RegistryKey<ItemGroup> group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add((item)));
    }

    public static void registerModItems() {
        addItemsToItemGroup();
    }
}
