package net.ugi.sculk_depths;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.item.ModItems;

import java.util.Arrays;
import java.util.List;

public class CustomItemModels {

    public static float QuazarithShovelModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_SHOVEL, new Identifier("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtElement nbtData = itemStack.getNbt().get("sculk_depths.crystal");

            if (nbtData == null) {
                return 0.0F;
            }

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithAxeModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_AXE, new Identifier("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtElement nbtData = itemStack.getNbt().get("sculk_depths.crystal");

            if (nbtData == null) {
                return 0.0F;
            }

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithPickaxeModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_PICKAXE, new Identifier("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtElement nbtData = itemStack.getNbt().get("sculk_depths.crystal");

            if (nbtData == null) {
                return 0.0F;
            }

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithHoeModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_HOE, new Identifier("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtElement nbtData = itemStack.getNbt().get("sculk_depths.crystal");

            if (nbtData == null) {
                return 0.0F;
            }

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithSwordModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_SWORD, new Identifier("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtElement nbtData = itemStack.getNbt().get("sculk_depths.crystal");

            if (nbtData == null) {
                return 0.0F;
            }

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithHelmetModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_HELMET, new Identifier("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtElement nbtData = itemStack.getNbt().get("sculk_depths.crystal");

            if (nbtData == null) {
                return 0.0F;
            }

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithChestplateModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_CHESTPLATE, new Identifier("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtElement nbtData = itemStack.getNbt().get("sculk_depths.crystal");

            if (nbtData == null) {
                return 0.0F;
            }

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithLeggingsModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_LEGGINGS, new Identifier("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtElement nbtData = itemStack.getNbt().get("sculk_depths.crystal");

            if (nbtData == null) {
                return 0.0F;
            }

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

    public static float QuazarithBootsModels() {
        ModelPredicateProviderRegistry.register(ModItems.QUAZARITH_BOOTS, new Identifier("crystal"), (itemStack, clientWorld, livingEntity, seed) -> {
            NbtElement nbtData = itemStack.getNbt().get("sculk_depths.crystal");

            if (nbtData == null) {
                return 0.0F;
            }

            List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

            return (crystalItemNbtList.indexOf(nbtData.toString()) + 1) * 0.1F;
        });

        return 0.0F;
    }

}
