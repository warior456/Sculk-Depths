package net.ugi.sculk_depths.screen;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.ContainerData;

public class ZygrinFurnaceScreenHandler extends AbstractZygrinFurnaceScreenHandler {

/*    public ZygrinFurnaceScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandlerTypes.ZYGRIN_FURNACE_SCREEN_HANDLER, RecipeType.SMELTING, RecipeBookCategory.FURNACE, syncId, playerInventory);
    }*/

/*    public ZygrinFurnaceScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlerTypes.ZYGRIN_FURNACE_SCREEN_HANDLER, RecipeType.SMELTING, RecipeBookCategory.FURNACE, syncId, playerInventory, inventory, propertyDelegate);
    }

    public ZygrinFurnaceScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
        super(ModScreenHandlerTypes.ZYGRIN_FURNACE_SCREEN_HANDLER, RecipeType.SMELTING, RecipeBookCategory.FURNACE, syncId, playerInventory);
    }*/
    public ZygrinFurnaceScreenHandler(int containerId, Inventory playerInventory) {
    super(ModScreenHandlers.ZYGRIN_FURNACE_SCREEN_HANDLER, RecipeType.SMELTING_RECIPE, RecipeBookType.FURNACE_CATEGORIES, containerId, playerInventory);
    }

    public ZygrinFurnaceScreenHandler(int containerId, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(ModScreenHandlers.ZYGRIN_FURNACE_SCREEN_HANDLER, RecipeType.SMELTING_RECIPE, RecipeBookType.FURNACE_CATEGORIES, containerId, playerInventory, inventory, propertyDelegate);
    }
}
