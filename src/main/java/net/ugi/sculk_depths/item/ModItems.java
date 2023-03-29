package net.ugi.sculk_depths.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.fluid.ModFluids;


public class ModItems {

    public static final Item SOUL_HEART = registerItem("soul_heart",
            new Item(new FabricItemSettings().maxCount(16)));
    public static final Item KRYSLUM_BUCKET = registerItem("kryslum_bucket", new BucketItem(ModFluids.KRYSLUM_STILL,
            new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));
    public static final Item QUAZARITH_INGOT = registerItem("quazarith_ingot",
            new Item(new FabricItemSettings()));

    public static final Item QUAZARITH_SCRAP = registerItem("quazarith_scrap",
            new Item(new FabricItemSettings()));

    //quazarith tools
    public static final Item QUAZARITH_PICKAXE = registerItem("quazarith_pickaxe",
            new PickaxeItem(ModToolMaterials.QUAZARITH, 2, -2.0F,
                    new FabricItemSettings()));

    //quazarith armor
    public static final Item QUAZARITH_HELMET = registerItem("quazarith_helmet",
            new ArmorItem(ModArmorMaterials.QUAZARITH, ArmorItem.Type.HELMET,
                    new FabricItemSettings()));
    public static final Item QUAZARITH_CHESTPLATE = registerItem("quazarith_chestplate",
            new ArmorItem(ModArmorMaterials.QUAZARITH, ArmorItem.Type.CHESTPLATE,
                    new FabricItemSettings()));
    public static final Item QUAZARITH_LEGGINGS = registerItem("quazarith_leggings",
            new ArmorItem(ModArmorMaterials.QUAZARITH, ArmorItem.Type.LEGGINGS,
                    new FabricItemSettings()));
    public static final Item QUAZARITH_BOOTS = registerItem("quazarith_boots",
            new ArmorItem(ModArmorMaterials.QUAZARITH, ArmorItem.Type.BOOTS,
                    new FabricItemSettings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(SculkDepths.MOD_ID, name), item);
    }

    public static void addItemsToItemGroup(){

        addToItemGroup(ModItemGroup.SCULK_DEPTHS, KRYSLUM_BUCKET);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, SOUL_HEART);

        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_INGOT);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_SCRAP);

        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_PICKAXE);

        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_HELMET);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_CHESTPLATE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_LEGGINGS);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, QUAZARITH_BOOTS);
    }

    private static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries ->  entries.add((item)));
    }

    public static void registerModItems() {
        SculkDepths.LOGGER.info("Registering Mod items for" + SculkDepths.MOD_ID);
        addItemsToItemGroup();
    }
}
