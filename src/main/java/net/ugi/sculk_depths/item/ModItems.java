package net.ugi.sculk_depths.item;

import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Rarity;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.fluid.ModFluids;
import net.ugi.sculk_depths.item.custom.crux_resonator.CruxResonator;
import net.ugi.sculk_depths.item.custom.GlomperGlux;
import net.ugi.sculk_depths.sound.ModSounds;


public class ModItems {

    public static final java.util.Map<ResourceKey<CreativeModeTab>, java.util.List<Item>> TAB_ITEMS = new java.util.LinkedHashMap<>();

    // ------------------- resources -------------------

    public static final Item CRUX = registerItem("crux",
            new Item(new Item.Properties()));

    public static final Item QUAZARITH_PIECES = registerItem("quazarith_pieces",
            new Item(new Item.Properties()));

    public static final Item QUAZARITH_INGOT = registerItem("quazarith_ingot",
            new Item(new Item.Properties().fireResistant()));


    // ------------------- quazarith -------------------

    //quazarith tools
    public static final Item QUAZARITH_SHOVEL = registerItem("quazarith_shovel",
            new ShovelItem(ModToolMaterials.QUAZARITH, //2.5F, -3.0F,
                    new Item.Properties().attributes(ShovelItem.createAttributes(ModToolMaterials.QUAZARITH, 2.5f, -3f)).fireResistant()));
    public static final Item QUAZARITH_PICKAXE = registerItem("quazarith_pickaxe",
            new PickaxeItem(ModToolMaterials.QUAZARITH, //2, -2.0F,
                    new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolMaterials.QUAZARITH, 2f,-2f)).fireResistant()));

    public static final Item QUAZARITH_AXE = registerItem("quazarith_axe",
            new AxeItem(ModToolMaterials.QUAZARITH, //7, -3.0F,
                    new Item.Properties().attributes(AxeItem.createAttributes(ModToolMaterials.QUAZARITH, 9f,-3f)).fireResistant()));

    public static final Item QUAZARITH_HOE = registerItem("quazarith_hoe",
            new HoeItem(ModToolMaterials.QUAZARITH, //-4, 1.0F,
                    new Item.Properties().attributes(HoeItem.createAttributes(ModToolMaterials.QUAZARITH, -4f,1f)).fireResistant()));

    public static final Item QUAZARITH_SWORD = registerItem("quazarith_sword",
            new SwordItem(ModToolMaterials.QUAZARITH, //6, -2.0F,
                    new Item.Properties().attributes(SwordItem.createAttributes(ModToolMaterials.QUAZARITH, 6, -2f)).fireResistant()));

    //quazarith armor
    public static final Item QUAZARITH_HELMET = registerItem("quazarith_helmet",
            new ArmorItem(ModArmorMaterials.QUAZARITH_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().fireResistant().durability(ArmorItem.Type.HELMET.getDurability(200))));

    public static final Item QUAZARITH_CHESTPLATE = registerItem("quazarith_chestplate",
            new ArmorItem(ModArmorMaterials.QUAZARITH_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().fireResistant().durability(ArmorItem.Type.CHESTPLATE.getDurability(200))));

    public static final Item QUAZARITH_LEGGINGS = registerItem("quazarith_leggings",
            new ArmorItem(ModArmorMaterials.QUAZARITH_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().fireResistant().durability(ArmorItem.Type.LEGGINGS.getDurability(200))));

    public static final Item QUAZARITH_BOOTS = registerItem("quazarith_boots",
            new ArmorItem(ModArmorMaterials.QUAZARITH_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().fireResistant().durability(ArmorItem.Type.BOOTS.getDurability(200))));


    // ------------------- spawn eggs -------------------

    public static final Item GLOMPER_SPAWN_EGG = registerItem("glomper_spawn_egg",
            new SpawnEggItem(ModEntities.GLOMPER, 1769607, 42751, new Item.Properties()));

    public static final Item LESTER_SPAWN_EGG = registerItem("lester_spawn_egg",
            new SpawnEggItem(ModEntities.LESTER, 856599, 12305307, new Item.Properties()));

    public static final Item CHOMPER_COLOSSUS_SPAWN_EGG = registerItem("chomper_colossus_spawn_egg",
            new SpawnEggItem(ModEntities.CHOMPER_COLOSSUS, 5860206, 9800821, new Item.Properties()));


    // ------------------- crystals -------------------
    public static final Item WHITE_CRYSTAL = registerItem("white_crystal",
            new Item(new Item.Properties()));

    public static final Item BLUE_CRYSTAL = registerItem("blue_crystal",
            new Item(new Item.Properties()));

    public static final Item ORANGE_CRYSTAL = registerItem("orange_crystal",
            new Item(new Item.Properties()));

    public static final Item LIME_CRYSTAL = registerItem("lime_crystal",
            new Item(new Item.Properties()));


    // ------------------- misc -------------------
    public static final Item KRYSLUM_BUCKET = registerItem("kryslum_bucket", new BucketItem(ModFluids.KRYSLUM_STILL,
            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final Item PENEBRIUM_SPORE_BUCKET = registerItem("penebrium_spore_bucket",
            new Item(new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final Item CRUX_RESONATOR = registerItem("crux_resonator",
            (new CruxResonator(new Item.Properties())));

    public static final Item QELBERRIES = registerItem("qelberries",
            (new ItemNameBlockItem(ModBlocks.QELBERRY_BUSH, (new Item.Properties()).food(Foods.SWEET_BERRIES))));

    public static final Item ENERGY_ESSENCE = registerItem("energy_essence",
            new Item(new Item.Properties()));

    public static final Item GLOMPER_GLUX = registerItem("glomper_glux",
            new GlomperGlux(new Item.Properties()));

    public static final Item MUSIC_DISC_ZINNIA = registerItem("music_disc_zinnia",
            new Item((new Item.Properties()).stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ModSounds.ZINNIA_KEY)));





    // ------------------- signs -------------------
    public static final Item VALTROX_SIGN = registerItem("valtrox_sign",
            new SignItem(new Item.Properties().stacksTo(16), ModBlocks.VALTROX_SIGN, ModBlocks.VALTROX_WALL_SIGN));

    public static final Item VALTROX_HANGING_SIGN = registerItem("valtrox_hanging_sign",
            new HangingSignItem(ModBlocks.VALTROX_HANGING_SIGN, ModBlocks.VALTROX_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16)));

    public static final Item COATED_VALTROX_SIGN = registerItem("coated_valtrox_sign",
            new SignItem(new Item.Properties().stacksTo(16), ModBlocks.COATED_VALTROX_SIGN, ModBlocks.COATED_VALTROX_WALL_SIGN));

    public static final Item COATED_VALTROX_HANGING_SIGN = registerItem("coated_valtrox_hanging_sign",
            new HangingSignItem(ModBlocks.COATED_VALTROX_HANGING_SIGN, ModBlocks.COATED_VALTROX_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16)));

    public static final Item DRIED_VALTROX_SIGN = registerItem("dried_valtrox_sign",
            new SignItem(new Item.Properties().stacksTo(16), ModBlocks.DRIED_VALTROX_SIGN, ModBlocks.DRIED_VALTROX_WALL_SIGN));

    public static final Item DRIED_VALTROX_HANGING_SIGN = registerItem("dried_valtrox_hanging_sign",
            new HangingSignItem(ModBlocks.DRIED_VALTROX_HANGING_SIGN, ModBlocks.DRIED_VALTROX_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16)));

    public static final Item PETRIFIED_VALTROX_SIGN = registerItem("petrified_valtrox_sign",
            new SignItem(new Item.Properties().stacksTo(16), ModBlocks.PETRIFIED_VALTROX_SIGN, ModBlocks.PETRIFIED_VALTROX_WALL_SIGN));

    public static final Item PETRIFIED_VALTROX_HANGING_SIGN = registerItem("petrified_valtrox_hanging_sign",
            new HangingSignItem(ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN, ModBlocks.PETRIFIED_VALTROX_WALL_HANGING_SIGN, new Item.Properties().stacksTo(16)));



    private static Item registerItem(String name, Item items) {
        return Registry.register(BuiltInRegistries.ITEM, SculkDepths.id( name), items);
    }


    public static void addItemsToItemGroup() {

        // ------------------- resources -------------------
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, CRUX);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_PIECES);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_INGOT);

        // ------------------- quazarith -------------------
        //quazarith tools
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_SHOVEL);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_PICKAXE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_AXE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_HOE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_SWORD);
        //quazarith armor
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_HELMET);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_CHESTPLATE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_LEGGINGS);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QUAZARITH_BOOTS);

        // ------------------- spawn eggs -------------------
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, GLOMPER_SPAWN_EGG);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, LESTER_SPAWN_EGG);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, CHOMPER_COLOSSUS_SPAWN_EGG);

        // ------------------- crystals -------------------
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, WHITE_CRYSTAL);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, BLUE_CRYSTAL);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, ORANGE_CRYSTAL);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, LIME_CRYSTAL);

        // ------------------- misc -------------------
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, KRYSLUM_BUCKET);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, PENEBRIUM_SPORE_BUCKET);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, CRUX_RESONATOR);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, QELBERRIES);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, ENERGY_ESSENCE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, GLOMPER_GLUX);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_ITEMS, MUSIC_DISC_ZINNIA);




        // ------------------- signs -------------------
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, VALTROX_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, VALTROX_HANGING_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, COATED_VALTROX_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, COATED_VALTROX_HANGING_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, DRIED_VALTROX_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, DRIED_VALTROX_HANGING_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, PETRIFIED_VALTROX_SIGN);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS_BLOCKS, PETRIFIED_VALTROX_HANGING_SIGN);
    }

    public static void addToItemGroup(ResourceKey<CreativeModeTab> recipeGroupPrefix, Item items) {
        TAB_ITEMS.computeIfAbsent(recipeGroupPrefix, k -> new java.util.ArrayList<>()).add(items);
    }

    public static void registerModItems() {
        addItemsToItemGroup();
    }
}
