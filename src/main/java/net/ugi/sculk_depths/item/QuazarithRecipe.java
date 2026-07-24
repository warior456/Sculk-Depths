package net.ugi.sculk_depths.item;

import net.minecraft.item.Item;

public record QuazarithRecipe(Item result, Item input, int kryslumCost, int cruxCost, int quazarithPiecesCost) {
}
