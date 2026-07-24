package net.ugi.sculk_depths;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.ugi.sculk_depths.item.ModComponentTypes;
import net.ugi.sculk_depths.util.enums.CrystalType;

public class CustomItemModels {

    public static void quazarithModels(Item item) {
        ModelPredicateProviderRegistry.register(item, SculkDepths.identifier("crystal"), (itemStack, clientWorld, livingEntity, seed) ->
                itemStack.getOrDefault(ModComponentTypes.CRYSTAL, CrystalType.NONE).ordinal() * 0.1f);
    }
}
