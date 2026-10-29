package net.ugi.sculk_depths.screen;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.screen.*;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class AbstractZygrinFurnaceScreenHandler extends AbstractRecipeScreenHandler<SingleRecipeInput, AbstractCookingRecipe> {
    public static final int field_30738 = 0;
    public static final int field_30739 = 1;
    public static final int field_30740 = 2;
    public static final int field_30741 = 3;
    public static final int field_30742 = 4;
    private static final int field_30743 = 3;
    private static final int field_30744 = 30;
    private static final int field_30745 = 30;
    private static final int field_30746 = 38;
    private final Container inventory;
    private final PropertyDelegate propertyDelegate;
    protected final Level world;
    private final RecipeType<? extends AbstractCookingRecipe> recipeType;
    private final RecipeBookType category;

    protected AbstractZygrinFurnaceScreenHandler(ScreenHandlerType<?> type, RecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookType category, int containerId, Inventory playerInventory) {
        this(type, recipeType, category, containerId, playerInventory, new SimpleContainer(3), new ArrayPropertyDelegate(4));
    }

    protected AbstractZygrinFurnaceScreenHandler(ScreenHandlerType<?> type, RecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookType category, int containerId, Inventory playerInventory, Container inventory, PropertyDelegate propertyDelegate) {
        super(type, containerId);
        int i;
        this.recipeType = recipeType;
        this.category = category;
        AbstractFurnaceScreenHandler.checkContainerSize(inventory, 3);
        AbstractFurnaceScreenHandler.checkContainerDataCount(propertyDelegate, 4);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.world = playerInventory.player.getWorld();
        this.addIngredient(new Slot(inventory, 0, 56, 17));
        this.addIngredient(new FurnaceResultSlot(playerInventory.player, inventory, 2, 116, 35));
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addIngredient(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addIngredient(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.addDataSlots(propertyDelegate);
    }

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        if (this.inventory instanceof RecipeInputProvider) {
            ((RecipeInputProvider)((Object)this.inventory)).fillStackedContents(finder);
        }
    }

    @Override
    public void clearCraftingContent() {
        this.getSlot(0).set(ItemStack.EMPTY);
        this.getSlot(2).set(ItemStack.EMPTY);
    }


    public boolean matches(RecipeEntry<AbstractCookingRecipe> addRecipe) {
        return ((AbstractCookingRecipe)addRecipe.value()).matches(new SingleRecipeInput(this.inventory.getStack(0)), this.world);
    }
    @Override
    public int getResultSlotIndex() {
        return 2;
    }

    @Override
    public int getGridWidth() {
        return 1;
    }

    @Override
    public int getGridHeight() {
        return 1;
    }

    @Override
    public int getSize() {
        return 3;
    }

    @Override
    public boolean canUse(Player player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        ItemStack item = ItemStack.EMPTY;
        Slot slot2 = (Slot)this.slots.get(slot);
        if (slot2 != null && slot2.hasItem()) {
            ItemStack itemStack2 = slot2.getStack();
            item = itemStack2.copy();
            if (slot == 1) {
                //output slot -> player inventory (handler slots 2-37)
                if (!this.moveItemStackTo(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
                slot2.onQuickCraft(itemStack2, item);
            } else if (slot == 0) {
                //input slot -> player inventory
                if (!this.moveItemStackTo(itemStack2, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.canSmelt(itemStack2)) {
                if (!this.moveItemStackTo(itemStack2, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.isFuel(itemStack2)) {
                //the fuel slot (furnace inventory index 1) has no handler slot,
                //so insertItem cannot reach it; insert the fuel directly
                if (!this.insertIntoFuelSlot(itemStack2)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot >= 2 && slot < 29) {
                if (!this.moveItemStackTo(itemStack2, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot >= 29 && slot < 38 && !this.moveItemStackTo(itemStack2, 2, 29, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
            if (itemStack2.getCount() == item.getCount()) {
                return ItemStack.EMPTY;
            }
            slot2.onTake(player, itemStack2);
        }
        return item;
    }

    private boolean insertIntoFuelSlot(ItemStack stack) {
        ItemStack fuelStack = this.inventory.getStack(1);
        if (fuelStack.isEmpty()) {
            this.inventory.setStack(1, stack.copy());
            this.inventory.markDirty();
            stack.setCount(0);
            return true;
        }
        if (ItemStack.isSameItemSameComponents(fuelStack, stack)) {
            int i = Math.minValue(stack.getCount(), Math.minValue(fuelStack.getMaxCount(), this.inventory.getMaxCount(fuelStack)) - fuelStack.getCount());
            if (i > 0) {
                fuelStack.increment(i);
                stack.shrink(i);
                this.inventory.markDirty();
                return true;
            }
        }
        return false;
    }

    protected boolean canSmelt(ItemStack item) {
        return this.world.getRecipeManager().getRecipeFor(this.recipeType, new SingleRecipeInput(item), this.world).isSuccess();
    }

    protected boolean isFuel(ItemStack item) {
        return AbstractFurnaceBlockEntity.isFuel(item);
    }

    public float getBurnProgress() {
        int i = this.propertyDelegate.get(2);
        int j = this.propertyDelegate.get(3);
        return j != 0 && i != 0 ? Mth.clamp((float)i / (float)j, 0.0F, 1.0F) : 0.0F;
    }

    public float getLitProgress() {
        int i = this.propertyDelegate.get(1);
        if (i == 0) {
            i = 200;
        }
//return 12 for full fire
        if(this.propertyDelegate.get(0) > 0) { // checks if burntime is greater than 0
            return 1f;
        } else {
            return 0;
        }
        //return (int) (this.propertyDelegate.get(0)*0.25);//todo discuss to use this alternate or not
    }

    public boolean isBurning() {
        return this.propertyDelegate.get(0) > 0;
    }

    @Override
    public RecipeBookType getCategory() {
        return this.category;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != 1;
    }
}