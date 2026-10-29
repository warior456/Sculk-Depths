package net.ugi.sculk_depths;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.ugi.sculk_depths.item.ModItems;

import java.util.Arrays;
import java.util.List;

public class CustomItemModels {

    public static float QuazarithShovelModels() {
        ItemProperties.register(ModItems.QUAZARITH_SHOVEL, ResourceLocation.fromNamespaceAndPath("crystal"), (item, clientLevel, livingEntity, seed) -> {
            CustomData nbtComponent = item.getComponents().get(DataComponents.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            Tag nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithAxeModels() {
        ItemProperties.register(ModItems.QUAZARITH_AXE, ResourceLocation.fromNamespaceAndPath("crystal"), (item, clientLevel, livingEntity, seed) -> {
            CustomData nbtComponent = item.getComponents().get(DataComponents.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            Tag nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithPickaxeModels() {
        ItemProperties.register(ModItems.QUAZARITH_PICKAXE, ResourceLocation.fromNamespaceAndPath("crystal"), (item, clientLevel, livingEntity, seed) -> {
            CustomData nbtComponent = item.getComponents().get(DataComponents.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            Tag nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithHoeModels() {
        ItemProperties.register(ModItems.QUAZARITH_HOE, ResourceLocation.fromNamespaceAndPath("crystal"), (item, clientLevel, livingEntity, seed) -> {
            CustomData nbtComponent = item.getComponents().get(DataComponents.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            Tag nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithSwordModels() {
        ItemProperties.register(ModItems.QUAZARITH_SWORD, ResourceLocation.fromNamespaceAndPath("crystal"), (item, clientLevel, livingEntity, seed) -> {
            CustomData nbtComponent = item.getComponents().get(DataComponents.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            Tag nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithHelmetModels() {
        ItemProperties.register(ModItems.QUAZARITH_HELMET, ResourceLocation.fromNamespaceAndPath("crystal"), (item, clientLevel, livingEntity, seed) -> {
            CustomData nbtComponent = item.getComponents().get(DataComponents.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            Tag nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithChestplateModels() {
        ItemProperties.register(ModItems.QUAZARITH_CHESTPLATE, ResourceLocation.fromNamespaceAndPath("crystal"), (item, clientLevel, livingEntity, seed) -> {
            CustomData nbtComponent = item.getComponents().get(DataComponents.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            Tag nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithLeggingsModels() {
        ItemProperties.register(ModItems.QUAZARITH_LEGGINGS, ResourceLocation.fromNamespaceAndPath("crystal"), (item, clientLevel, livingEntity, seed) -> {
            CustomData nbtComponent = item.getComponents().get(DataComponents.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            Tag nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithBootsModels() {
        ItemProperties.register(ModItems.QUAZARITH_BOOTS, ResourceLocation.fromNamespaceAndPath("crystal"), (item, clientLevel, livingEntity, seed) -> {
            CustomData nbtComponent = item.getComponents().get(DataComponents.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            Tag nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;


            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

}
