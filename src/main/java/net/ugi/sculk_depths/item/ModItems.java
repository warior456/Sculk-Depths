package net.ugi.sculk_depths.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EquipmentSlot;
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



    //custom armor seet
    public static final Item CUSTOM_ARMOR_HELMET = registerItem("custom_armor_helmet",
            new ArmorItem(ModArmorMaterials.CUSTOMARMORMATERIAL, ArmorItem.Type.HELMET,
                    new FabricItemSettings()));
    public static final Item CUSTOM_ARMOR_CHESTPLATE = registerItem("custom_armor_chestplate",
            new ArmorItem(ModArmorMaterials.CUSTOMARMORMATERIAL, ArmorItem.Type.CHESTPLATE,
                    new FabricItemSettings()));
    public static final Item CUSTOM_ARMOR_LEGGINGS = registerItem("custom_armor_leggings",
            new ArmorItem(ModArmorMaterials.CUSTOMARMORMATERIAL, ArmorItem.Type.LEGGINGS,
                    new FabricItemSettings()));
    public static final Item CUSTOM_ARMOR_BOOTS = registerItem("custom_armor_boots",
            new ArmorItem(ModArmorMaterials.CUSTOMARMORMATERIAL, ArmorItem.Type.BOOTS,
                    new FabricItemSettings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(SculkDepths.MOD_ID, name), item);
    }

    public static void addItemsToItemGroup(){

        addToItemGroup(ModItemGroup.SCULK_DEPTHS, KRYSLUM_BUCKET);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, SOUL_HEART);

        addToItemGroup(ModItemGroup.SCULK_DEPTHS, CUSTOM_ARMOR_HELMET);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, CUSTOM_ARMOR_CHESTPLATE);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, CUSTOM_ARMOR_LEGGINGS);
        addToItemGroup(ModItemGroup.SCULK_DEPTHS, CUSTOM_ARMOR_BOOTS);
    }

    private static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries ->  entries.add((item)));
    }

    public static void registerModItems() {
        SculkDepths.LOGGER.info("Registering Mod items for" + SculkDepths.MOD_ID);
        addItemsToItemGroup();
    }
}
