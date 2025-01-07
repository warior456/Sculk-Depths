package net.ugi.sculk_depths.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.fluid.ModFluids;
import net.ugi.sculk_depths.item.custom.CruxResonator;
import net.ugi.sculk_depths.item.custom.GlomperGlux;



public class ModItems {

    public static final Item KRYSLUM_BUCKET = registerItem("kryslum_bucket", new BucketItem(ModFluids.KRYSLUM_STILL,
            new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
    public static final Item QUAZARITH_INGOT = registerItem("quazarith_ingot",
            new Item(new Item.Settings().fireproof()));

    public static final Item QUAZARITH_PIECES = registerItem("quazarith_pieces",
            new Item(new Item.Settings()));

    public static final Item CRUX = registerItem("crux",
            new Item(new Item.Settings()));

    public static final Item WHITE_CRYSTAL = registerItem("white_crystal",
            new Item(new Item.Settings()));

    public static final Item BLUE_CRYSTAL = registerItem("blue_crystal",
            new Item(new Item.Settings()));

    public static final Item ORANGE_CRYSTAL = registerItem("orange_crystal",
            new Item(new Item.Settings()));

    public static final Item LIME_CRYSTAL = registerItem("lime_crystal",
            new Item(new Item.Settings()));


    public static final Item PENEBRIUM_SPORE_BUCKET = registerItem("penebrium_spore_bucket",
            new Item(new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));

    public static final Item ENERGY_ESSENCE = registerItem("energy_essence",
            new Item(new Item.Settings()));

    public static final Item GLOMPER_GLUX = registerItem("glomper_glux",
            new GlomperGlux(new Item.Settings()));


    public static final Item GLOMPER_SPAWN_EGG = registerItem("glomper_spawn_egg",
            new SpawnEggItem(ModEntities.GLOMPER, 1769607, 42751, new Item.Settings()));

    public static final Item LESTER_SPAWN_EGG = registerItem("lester_spawn_egg",
            new SpawnEggItem(ModEntities.LESTER, 856599, 12305307, new Item.Settings()));

    public static final Item CHOMPER_COLOSSUS_SPAWN_EGG = registerItem("chomper_colossus_spawn_egg",
            new SpawnEggItem(ModEntities.CHOMPER_COLOSSUS, 5860206, 9800821, new Item.Settings()));

    //quazarith tools

    public static final Item QUAZARITH_SHOVEL = registerItem("quazarith_shovel",
            new ShovelItem(ModToolMaterials.QUAZARITH, //2.5F, -3.0F,
                    new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.QUAZARITH, 2.5f, -3f)).fireproof()));
    public static final Item QUAZARITH_PICKAXE = registerItem("quazarith_pickaxe",
            new PickaxeItem(ModToolMaterials.QUAZARITH, //2, -2.0F,
                    new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.QUAZARITH, 2f,-2f)).fireproof()));

    public static final Item QUAZARITH_AXE = registerItem("quazarith_axe",
            new AxeItem(ModToolMaterials.QUAZARITH, //7, -3.0F,
                    new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.QUAZARITH, 9f,-3f)).fireproof()));

    public static final Item QUAZARITH_HOE = registerItem("quazarith_hoe",
            new HoeItem(ModToolMaterials.QUAZARITH, //-4, 1.0F,
                    new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.QUAZARITH, -4f,1f)).fireproof()));

    public static final Item QUAZARITH_SWORD = registerItem("quazarith_sword",
            new SwordItem(ModToolMaterials.QUAZARITH, //6, -2.0F,
                    new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.QUAZARITH, 6, -2f)).fireproof()));


    //quazarith armor
    public static final Item QUAZARITH_HELMET = registerItem("quazarith_helmet",
            new ArmorItem(ModArmorMaterials.QUAZARITH_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Settings().fireproof().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(200))));
    public static final Item QUAZARITH_CHESTPLATE = registerItem("quazarith_chestplate",
            new ArmorItem(ModArmorMaterials.QUAZARITH_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings().fireproof().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(200))));
    public static final Item QUAZARITH_LEGGINGS = registerItem("quazarith_leggings",
            new ArmorItem(ModArmorMaterials.QUAZARITH_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Settings().fireproof().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(200))));
    public static final Item QUAZARITH_BOOTS = registerItem("quazarith_boots",
            new ArmorItem(ModArmorMaterials.QUAZARITH_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Settings().fireproof().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(200))));

    public static final Item VALTROX_SIGN = registerItem("valtrox_sign",
            new SignItem(new Item.Settings().maxCount(16), ModBlocks.VALTROX_SIGN, ModBlocks.VALTROX_WALL_SIGN));

    public static final Item VALTROX_HANGING_SIGN = registerItem("valtrox_hanging_sign",
            new HangingSignItem(ModBlocks.VALTROX_HANGING_SIGN, ModBlocks.VALTROX_WALL_HANGING_SIGN, new Item.Settings().maxCount(16)));

    public static final Item COATED_VALTROX_SIGN = registerItem("coated_valtrox_sign",
            new SignItem(new Item.Settings().maxCount(16), ModBlocks.COATED_VALTROX_SIGN, ModBlocks.COATED_VALTROX_WALL_SIGN));

    public static final Item COATED_VALTROX_HANGING_SIGN = registerItem("coated_valtrox_hanging_sign",
            new HangingSignItem(ModBlocks.COATED_VALTROX_HANGING_SIGN, ModBlocks.COATED_VALTROX_WALL_HANGING_SIGN, new Item.Settings().maxCount(16)));

    public static final Item DRIED_VALTROX_SIGN = registerItem("dried_valtrox_sign",
            new SignItem(new Item.Settings().maxCount(16), ModBlocks.DRIED_VALTROX_SIGN, ModBlocks.DRIED_VALTROX_WALL_SIGN));

    public static final Item DRIED_VALTROX_HANGING_SIGN = registerItem("dried_valtrox_hanging_sign",
            new HangingSignItem(ModBlocks.DRIED_VALTROX_HANGING_SIGN, ModBlocks.DRIED_VALTROX_WALL_HANGING_SIGN, new Item.Settings().maxCount(16)));

    public static final Item PETRIFIED_VALTROX_SIGN = registerItem("petrified_valtrox_sign",
            new SignItem(new Item.Settings().maxCount(16), ModBlocks.PETRIFIED_VALTROX_SIGN, ModBlocks.PETRIFIED_VALTROX_WALL_SIGN));

    public static final Item PETRIFIED_VALTROX_HANGING_SIGN = registerItem("petrified_valtrox_hanging_sign",
            new HangingSignItem(ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN, ModBlocks.PETRIFIED_VALTROX_WALL_HANGING_SIGN, new Item.Settings().maxCount(16)));

    public static final Item CRUX_RESONATOR = registerItem("crux_resonator",
            (new CruxResonator(new Item.Settings())));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, SculkDepths.identifier( name), item);
    }


    public static void addItemsToItemGroup() {

        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, KRYSLUM_BUCKET);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, ENERGY_ESSENCE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, CRUX_RESONATOR);

        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, GLOMPER_GLUX);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, GLOMPER_SPAWN_EGG);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, LESTER_SPAWN_EGG);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, CHOMPER_COLOSSUS_SPAWN_EGG);

        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, CRUX);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, WHITE_CRYSTAL);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, BLUE_CRYSTAL);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, ORANGE_CRYSTAL);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, LIME_CRYSTAL);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, PENEBRIUM_SPORE_BUCKET);

        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_INGOT);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_PIECES);

        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_SHOVEL);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_PICKAXE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_AXE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_HOE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_SWORD);

        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_HELMET);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_CHESTPLATE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_LEGGINGS);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_BOOTS);

        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, VALTROX_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, VALTROX_HANGING_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, COATED_VALTROX_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, COATED_VALTROX_HANGING_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, DRIED_VALTROX_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, DRIED_VALTROX_HANGING_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, PETRIFIED_VALTROX_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, PETRIFIED_VALTROX_HANGING_SIGN);
    }

    private static void addToItemGroup(RegistryKey<ItemGroup> group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add((item)));
    }

    public static void registerModItems() {
        addItemsToItemGroup();
    }
}
