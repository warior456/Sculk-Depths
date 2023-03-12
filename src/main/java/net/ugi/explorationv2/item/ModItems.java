package net.ugi.explorationv2.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ugi.explorationv2.ExplorationV2;

public class ModItems {
    public static final Item CITRINE = registerItem("citrine",
            new Item(new FabricItemSettings()));

    public static final Item RAW_CITRINE = registerItem("raw_citrine",
            new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ExplorationV2.MOD_ID, name), item);
    }

    public static void addItemsToItemGroup(){
        addToItemGroup(ItemGroups.INGREDIENTS, CITRINE);
        addToItemGroup(ItemGroups.INGREDIENTS, RAW_CITRINE);

        addToItemGroup(ModItemGroup.EXPLORATIONV2, CITRINE);
        addToItemGroup(ModItemGroup.EXPLORATIONV2, RAW_CITRINE);
    }

    private static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries ->  entries.add((item)));
    }

    public static void registerModItems() {
        ExplorationV2.LOGGER.info("Registering Mod items for" + ExplorationV2.MOD_ID);
        addItemsToItemGroup();
    }
}
