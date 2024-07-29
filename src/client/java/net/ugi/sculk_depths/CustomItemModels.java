package net.ugi.sculk_depths;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.item.ModItems;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CustomItemModels {

    public static float QuazarithShovelModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_SHOVEL, Identifier.of("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtComponent nbtComponent = itemStack.getComponents().get(DataComponentTypes.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            NbtElement nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithAxeModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_AXE, Identifier.of("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtComponent nbtComponent = itemStack.getComponents().get(DataComponentTypes.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            NbtElement nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithPickaxeModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_PICKAXE, Identifier.of("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtComponent nbtComponent = itemStack.getComponents().get(DataComponentTypes.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            NbtElement nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithHoeModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_HOE, Identifier.of("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtComponent nbtComponent = itemStack.getComponents().get(DataComponentTypes.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            NbtElement nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithSwordModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_SWORD, Identifier.of("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtComponent nbtComponent = itemStack.getComponents().get(DataComponentTypes.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            NbtElement nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithHelmetModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_HELMET, Identifier.of("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtComponent nbtComponent = itemStack.getComponents().get(DataComponentTypes.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            NbtElement nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithChestplateModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_CHESTPLATE, Identifier.of("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtComponent nbtComponent = itemStack.getComponents().get(DataComponentTypes.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            NbtElement nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithLeggingsModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_LEGGINGS, Identifier.of("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtComponent nbtComponent = itemStack.getComponents().get(DataComponentTypes.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            NbtElement nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithBootsModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_BOOTS, Identifier.of("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtComponent nbtComponent = itemStack.getComponents().get(DataComponentTypes.CUSTOM_DATA);
            if (nbtComponent == null) return 0.0F;
            NbtElement nbtData = nbtComponent.getNbt().get("sculk_depths.crystal"); //TODO change to new system
            if (nbtData == null) return 0.0F;


            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

}
