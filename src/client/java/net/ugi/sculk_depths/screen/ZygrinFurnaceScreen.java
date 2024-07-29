package net.ugi.sculk_depths.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.recipebook.FurnaceRecipeBookScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

@Environment(EnvType.CLIENT)
public class ZygrinFurnaceScreen extends AbstractZygrinFurnaceScreen<ZygrinFurnaceScreenHandler> {
    private static final Identifier TEXTURE = SculkDepths.identifier("textures/gui/container/zygrin_furnace.png");

/*    public ZygrinFurnaceScreen(ZygrinFurnaceScreenHandler handler, AbstractFurnaceRecipeBookScreen recipeBook, PlayerInventory inventory, Text title, Identifier background) {
        super(handler, recipeBook, inventory, title, TEXTURE);
    }

    public ZygrinFurnaceScreen(ZygrinFurnaceScreenHandler handler, AbstractFurnaceRecipeBookScreen recipeBook, PlayerInventory inventory, Text title) {
        super(handler, recipeBook, inventory, title, TEXTURE);
    }*/

    public ZygrinFurnaceScreen(ZygrinFurnaceScreenHandler zygrinFurnaceScreenHandler, PlayerInventory playerInventory, Text text) {
        super(zygrinFurnaceScreenHandler, new FurnaceRecipeBookScreen() , playerInventory, text, TEXTURE);
    }
}
