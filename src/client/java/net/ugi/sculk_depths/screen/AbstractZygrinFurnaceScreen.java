package net.ugi.sculk_depths.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(value= EnvType.CLIENT)
public class AbstractZygrinFurnaceScreen<T extends AbstractZygrinFurnaceScreenHandler> extends AbstractContainerScreen<T> implements RecipeUpdateListener {
    public final AbstractFurnaceRecipeBookComponent recipeBook;
    private boolean widthTooNarrow;
    private final ResourceLocation texture;
    private final ResourceLocation litProgressSprite;
    private final ResourceLocation burnProgressSprite;

    public AbstractZygrinFurnaceScreen(
            T handler,
            AbstractFurnaceRecipeBookComponent recipeBook,
            Inventory inventory,
            Component header,
            ResourceLocation texture,
            ResourceLocation litProgressSprite,
            ResourceLocation burnProgressSprite
    ) {
        super(handler, inventory, header);
        this.recipeBook = recipeBook;
        this.texture = texture;
        this.litProgressSprite = litProgressSprite;
        this.burnProgressSprite = burnProgressSprite;
    }

    @Override
    public void init() {
        super.init();
        this.widthTooNarrow = this.setWidth < 379;
        this.recipeBook.initialize(this.setWidth, this.height, this.client, this.widthTooNarrow, (RecipeBookMenu<?, ?>)this.handler);
        this.x = this.recipeBook.updateScreenPosition(this.setWidth, this.imageWidth);
        this.addRenderableWidget(new ImageButton(this.x + 20, this.height / 2 - 49, 20, 18, RecipeBookComponent.RECIPE_BUTTON_SPRITES, addButton -> {
            this.recipeBook.toggleVisibility();
            this.x = this.recipeBook.updateScreenPosition(this.setWidth, this.imageWidth);
            addButton.setPosition(this.x + 20, this.height / 2 - 49);
        }));
        this.titleLabelX = (this.imageWidth - this.font.getWidth(this.header)) / 2;
    }

    @Override
    public void containerTick() {
        super.containerTick();
        this.recipeBook.updateDelay();
    }

    @Override
    public void render(GuiGraphics context, int xMouse, int yMouse, float fadeDirection) {
        if (this.recipeBook.isOpen() && this.widthTooNarrow) {
            this.renderBackground(context, xMouse, yMouse, fadeDirection);
            this.recipeBook.render(context, xMouse, yMouse, fadeDirection);
        } else {
            super.render(context, xMouse, yMouse, fadeDirection);
            this.recipeBook.render(context, xMouse, yMouse, fadeDirection);
            this.recipeBook.renderGhostRecipe(context, this.x, this.y, true, fadeDirection);
        }

        this.renderTooltip(context, xMouse, yMouse);
        this.recipeBook.drawTooltip(context, this.x, this.y, xMouse, yMouse);
    }

    @Override
    protected void drawBackground(GuiGraphics context, float fadeDirection, int xMouse, int yMouse) {
        int i = this.x;
        int j = this.y;
        context.blit(this.texture, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if (this.handler.isBurning()) {
            int k = 14;
            int l = Mth.ceil(this.handler.getLitProgress() * 13.0F) + 1;
            context.blitSprite(this.litProgressSprite, 14, 14, 0, 14 - l, i + 56, j + 36 + 14 - l, 14, l);
        }

        int k = 24;
        int l = Mth.ceil(this.handler.getBurnProgress() * 24.0F);
        context.blitSprite(this.burnProgressSprite, 24, 16, 0, 0, i + 79, j + 34, l, 16);
    }

    @Override
    public boolean handleChatQueueClicked(double xMouse, double yMouse, int addButton) {
        if (this.recipeBook.handleChatQueueClicked(xMouse, yMouse, addButton)) {
            return true;
        } else {
            return this.widthTooNarrow && this.recipeBook.isOpen() ? true : super.handleChatQueueClicked(xMouse, yMouse, addButton);
        }
    }

    @Override
    protected void onMouseClick(Slot slot, int slotIndex, int addButton, ClickType clickType) {
        super.onMouseClick(slot, slotIndex, addButton, clickType);
        this.recipeBook.slotClicked(slot);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return this.recipeBook.keyPressed(keyCode, scanCode, modifiers) ? true : super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected boolean hasClickedOutside(double xMouse, double yMouse, int left, int top, int addButton) {
        boolean bl = xMouse < (double)left
                || yMouse < (double)top
                || xMouse >= (double)(left + this.imageWidth)
                || yMouse >= (double)(top + this.imageHeight);
        return this.recipeBook.hasClickedOutside(xMouse, yMouse, this.x, this.y, this.imageWidth, this.imageHeight, addButton) && bl;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return this.recipeBook.charTyped(chr, modifiers) ? true : super.charTyped(chr, modifiers);
    }

    @Override
    public void recipesUpdated() {
        this.recipeBook.refresh();
    }

    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return this.recipeBook;
    }
}
