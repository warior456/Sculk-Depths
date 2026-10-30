package net.ugi.sculk_depths.screen;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipeType;

public class ZygrinFurnaceScreenHandler extends AbstractZygrinFurnaceScreenHandler {

    public ZygrinFurnaceScreenHandler(int containerId, Inventory playerInventory) {
        super(ModScreenHandlers.ZYGRIN_FURNACE_SCREEN_HANDLER, RecipeType.SMELTING, RecipeBookType.FURNACE, containerId, playerInventory);
    }

    public ZygrinFurnaceScreenHandler(int containerId, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(ModScreenHandlers.ZYGRIN_FURNACE_SCREEN_HANDLER, RecipeType.SMELTING, RecipeBookType.FURNACE, containerId, playerInventory, inventory, propertyDelegate);
    }
}
