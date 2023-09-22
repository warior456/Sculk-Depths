package net.ugi.sculk_depths;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.item.ModItems;

import java.util.Arrays;
import java.util.List;

public class CustomItemModels {

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

}
