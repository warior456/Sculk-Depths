package net.ugi.sculk_depths.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.recipebook.SmeltingRecipeBookComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.ugi.sculk_depths.SculkDepths;

@Environment(EnvType.CLIENT)
public class ZygrinFurnaceScreen extends AbstractZygrinFurnaceScreen<ZygrinFurnaceScreenHandler> {
    //private static final Identifier TEXTURE = SculkDepths.identifier("textures/gui/container/zygrin_furnace.png");

/*    public ZygrinFurnaceScreen(ZygrinFurnaceScreenHandler handler, AbstractFurnaceRecipeBookScreen recipeBook, PlayerInventory inventory, Text title, Identifier background) {
        super(handler, recipeBook, inventory, title, TEXTURE);
    }

    public ZygrinFurnaceScreen(ZygrinFurnaceScreenHandler handler, AbstractFurnaceRecipeBookScreen recipeBook, PlayerInventory inventory, Text title) {
        super(handler, recipeBook, inventory, title, TEXTURE);
    }*/

    private static final ResourceLocation LIT_PROGRESS_SPRITE = SculkDepths.id("container/zygrin_furnace/lit_progress");
    private static final ResourceLocation BURN_PROGRESS_SPRITE = SculkDepths.id("container/zygrin_furnace/burn_progress");
    private static final ResourceLocation TEXTURE = SculkDepths.id("textures/gui/container/zygrin_furnace.png");

    public ZygrinFurnaceScreen(ZygrinFurnaceScreenHandler handler, Inventory inventory, Component header) {
        super(handler, new SmeltingRecipeBookComponent(), inventory, header, TEXTURE, LIT_PROGRESS_SPRITE, BURN_PROGRESS_SPRITE);
    }

/*    public ZygrinFurnaceScreen(ZygrinFurnaceScreenHandler zygrinFurnaceScreenHandler, PlayerInventory playerInventory, Text text) {
        super(zygrinFurnaceScreenHandler, new FurnaceRecipeBookScreen() , playerInventory, text, TEXTURE);
    }*/
}
