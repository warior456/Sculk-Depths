package net.ugi.sculk_depths.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.fluid.ModFluids;
import net.ugi.sculk_depths.item.custom.crux_resonator.CruxResonator;
import net.ugi.sculk_depths.item.custom.GlomperGlux;
import net.ugi.sculk_depths.sound.ModSounds;


public class ModItems {

    // ------------------- resources -------------------
    public static final Item CRUX = registerItem("crux", new Item(new Item.Settings()));
    public static final Item QUAZARITH_PIECES = registerItem("quazarith_pieces", new Item(new Item.Settings()));
    public static final Item QUAZARITH_INGOT = registerItem("quazarith_ingot", new Item(new Item.Settings().fireproof()));

    // ------------------- quazarith -------------------

    //quazarith tools
    public static final Item QUAZARITH_SHOVEL = registerItem("quazarith_shovel",
            new ShovelItem(ModToolMaterials.QUAZARITH,
                    new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.QUAZARITH, 2.5f, -3f)).fireproof()));
    public static final Item QUAZARITH_PICKAXE = registerItem("quazarith_pickaxe",
            new PickaxeItem(ModToolMaterials.QUAZARITH,
                    new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.QUAZARITH, 2f, -2f)).fireproof()));

    public static final Item QUAZARITH_AXE = registerItem("quazarith_axe",
            new AxeItem(ModToolMaterials.QUAZARITH,
                    new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.QUAZARITH, 9f, -3f)).fireproof()));

    public static final Item QUAZARITH_HOE = registerItem("quazarith_hoe",
            new HoeItem(ModToolMaterials.QUAZARITH,
                    new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.QUAZARITH, -4f, 1f)).fireproof()));

    public static final Item QUAZARITH_SWORD = registerItem("quazarith_sword",
            new SwordItem(ModToolMaterials.QUAZARITH,
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

    // ------------------- spawn eggs -------------------
    public static final Item GLOMPER_SPAWN_EGG = registerItem("glomper_spawn_egg",
            new SpawnEggItem(ModEntities.GLOMPER, 0x1B0087, 0x00A6FF, new Item.Settings()));

    public static final Item LESTER_SPAWN_EGG = registerItem("lester_spawn_egg",
            new SpawnEggItem(ModEntities.LESTER, 0x0D1217, 0xBBC39B, new Item.Settings()));

    public static final Item CHOMPER_COLOSSUS_SPAWN_EGG = registerItem("chomper_colossus_spawn_egg",
            new SpawnEggItem(ModEntities.CHOMPER_COLOSSUS, 0x596B6E, 0x958C75, new Item.Settings()));


    // ------------------- crystals -------------------
    public static final Item WHITE_CRYSTAL = registerItem("white_crystal", new Item(new Item.Settings()));
    public static final Item BLUE_CRYSTAL = registerItem("blue_crystal", new Item(new Item.Settings()));
    public static final Item ORANGE_CRYSTAL = registerItem("orange_crystal", new Item(new Item.Settings()));
    public static final Item LIME_CRYSTAL = registerItem("lime_crystal", new Item(new Item.Settings()));

    // ------------------- misc -------------------
    public static final Item KRYSLUM_BUCKET = registerItem("kryslum_bucket", new BucketItem(ModFluids.KRYSLUM_STILL,
            new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));

    public static final Item PENEBRIUM_SPORE_BUCKET = registerItem("penebrium_spore_bucket",
            new Item(new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));

    public static final Item CRUX_RESONATOR = registerItem("crux_resonator",
            new CruxResonator(new Item.Settings()));

    public static final Item QELBERRIES = registerItem("qelberries",
            new AliasedBlockItem(ModBlocks.QELBERRY_BUSH, new Item.Settings().food(FoodComponents.SWEET_BERRIES)));

    public static final Item ENERGY_ESSENCE = registerItem("energy_essence",
            new Item(new Item.Settings()));

    public static final Item GLOMPER_GLUX = registerItem("glomper_glux",
            new GlomperGlux(new Item.Settings()));

    public static final Item MUSIC_DISC_ZINNIA = registerItem("music_disc_zinnia",
            new Item(new Item.Settings().maxCount(1).rarity(Rarity.RARE).jukeboxPlayable(ModSounds.ZINNIA_KEY)));

    // ------------------- signs -------------------
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

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, SculkDepths.identifier( name), item);
    }

    public static void addItemsToItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.SCULK_DEPTHS_ITEMS).register(entries -> {
            // ------------------- resources -------------------
            entries.add(CRUX);
            entries.add(QUAZARITH_PIECES);
            entries.add(QUAZARITH_INGOT);

            // ------------------- quazarith -------------------
            //quazarith tools
            entries.add(QUAZARITH_SHOVEL);
            entries.add(QUAZARITH_PICKAXE);
            entries.add(QUAZARITH_AXE);
            entries.add(QUAZARITH_HOE);
            entries.add(QUAZARITH_SWORD);
            //quazarith armor
            entries.add(QUAZARITH_HELMET);
            entries.add(QUAZARITH_CHESTPLATE);
            entries.add(QUAZARITH_LEGGINGS);
            entries.add(QUAZARITH_BOOTS);

            // ------------------- spawn eggs -------------------
            entries.add(GLOMPER_SPAWN_EGG);
            entries.add(LESTER_SPAWN_EGG);
            entries.add(CHOMPER_COLOSSUS_SPAWN_EGG);

            // ------------------- crystals -------------------
            entries.add(WHITE_CRYSTAL);
            entries.add(BLUE_CRYSTAL);
            entries.add(ORANGE_CRYSTAL);
            entries.add(LIME_CRYSTAL);

            // ------------------- misc -------------------
            entries.add(KRYSLUM_BUCKET);
            entries.add(PENEBRIUM_SPORE_BUCKET);
            entries.add(CRUX_RESONATOR);
            entries.add(QELBERRIES);
            entries.add(ENERGY_ESSENCE);
            entries.add(GLOMPER_GLUX);
            entries.add(MUSIC_DISC_ZINNIA);
            entries.add(CHOMPER_COLOSSUS_SPAWN_EGG);
        });

        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.SCULK_DEPTHS_BLOCKS).register(entries -> {
            // ------------------- signs -------------------
            entries.add(VALTROX_SIGN);
            entries.add(VALTROX_HANGING_SIGN);
            entries.add(COATED_VALTROX_SIGN);
            entries.add(COATED_VALTROX_HANGING_SIGN);
            entries.add(DRIED_VALTROX_SIGN);
            entries.add(DRIED_VALTROX_HANGING_SIGN);
            entries.add(PETRIFIED_VALTROX_SIGN);
            entries.add(PETRIFIED_VALTROX_HANGING_SIGN);
        });
    }

    public static void registerModItems() {
        addItemsToItemGroups();
    }
}
