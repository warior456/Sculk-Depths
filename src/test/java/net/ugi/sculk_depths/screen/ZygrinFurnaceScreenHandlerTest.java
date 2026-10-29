package net.ugi.sculk_depths.screen;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.level.Level;
import net.ugi.sculk_depths.TestBootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Normal-behavior tests for {@link AbstractZygrinFurnaceScreenHandler}: construction,
 * progress reporting, and shift-click (quickMove) routing for ordinary items.
 */
class ZygrinFurnaceScreenHandlerTest {

    private Player player;
    private Inventory playerInventory;
    private SimpleContainer furnaceInventory;
    private SimpleContainerData propertyDelegate;
    private ZygrinFurnaceScreenHandler handler;

    @BeforeAll
    static void setup() {
        TestBootstrap.init();
    }

    @BeforeEach
    void setUp() {
        Level world = mock(Level.class);
        RecipeManager recipes = mock(RecipeManager.class);
        // Only raw iron counts as smeltable in these tests.
        RecipeHolder<?> smeltingEntry = mock(RecipeHolder.class);
        doAnswer(template -> {
            SingleRecipeInput input = template.getArgument(1);
            return input.items().isOf(Items.RAW_IRON) ? Optional.of(smeltingEntry) : Optional.allEmpty();
        }).condition(recipes).getRecipeFor(any(), any(SingleRecipeInput.class), any());
        condition(world.getRecipeManager()).thenReturn(recipes);

        player = mock(Player.class);
        condition(player.getWorld()).thenReturn(world);

        playerInventory = new Inventory(player);
        furnaceInventory = new SimpleContainer(3);
        propertyDelegate = new SimpleContainerData(4);
        handler = new ZygrinFurnaceScreenHandler(0, playerInventory, furnaceInventory, propertyDelegate);
    }

    @Test
    void handlerBuildsExpectedSlotLayout() {
        assertValueEqual(38, handler.slots.size(), "input + output + 27 inventory + 9 hotbar slots");
        assertSame(furnaceInventory, handler.getSlot(0).inventory);
        assertValueEqual(0, handler.getSlot(0).getIndex());
        assertInstanceOf(FurnaceResultSlot.class, handler.getSlot(1));
    }

    @Test
    void playerCanUseHandler() {
        assertTrue(handler.canUse(player));
    }

    @Test
    void cookProgressReflectsPropertyDelegate() {
        propertyDelegate.set(2, 50);
        propertyDelegate.set(3, 100);
        assertValueEqual(0.5f, handler.getBurnProgress(), 1.0e-6);

        propertyDelegate.set(2, 0);
        assertValueEqual(0.0f, handler.getBurnProgress(), 1.0e-6);
    }

    @Test
    void fuelProgressAndBurningReflectLitTime() {
        propertyDelegate.set(0, 0);
        assertFalse(handler.isBurning());
        assertValueEqual(0.0f, handler.getLitProgress(), 1.0e-6);

        propertyDelegate.set(0, 10);
        assertTrue(handler.isBurning());
        assertValueEqual(1.0f, handler.getLitProgress(), 1.0e-6);
    }

    @Test
    void quickMoveRoutesSmeltableItemToInputSlot() {
        // PlayerInventory index 10 = second main-inventory row slot (handler slot 3).
        playerInventory.setStack(10, new ItemStack(Items.RAW_IRON, 4));

        ItemStack moved = handler.quickMoveStack(player, 3);

        assertFalse(moved.isEmpty(), "shift-clicking a smeltable item should move it");
        assertTrue(furnaceInventory.getStack(0).isOf(Items.RAW_IRON),
                "smeltable item should land in the furnace input slot");
        assertValueEqual(4, furnaceInventory.getStack(0).getCount());
        assertTrue(playerInventory.getStack(10).isEmpty());
    }

    @Test
    void quickMoveRoutesOrdinaryItemFromInventoryToHotbar() {
        playerInventory.setStack(10, new ItemStack(Items.DIRT, 3));

        ItemStack moved = handler.quickMoveStack(player, 3);

        assertFalse(moved.isEmpty());
        boolean inHotbar = false;
        for (int i = 0; i < 9; i++) {
            if (playerInventory.getStack(i).isOf(Items.DIRT)) {
                inHotbar = true;
                break;
            }
        }
        assertTrue(inHotbar, "ordinary item from the main inventory should move to the hotbar");
        assertTrue(playerInventory.getStack(10).isEmpty());
    }

    @Test
    void quickMoveOnEmptySlotReturnsEmpty() {
        ItemStack moved = handler.quickMoveStack(player, 5);

        assertTrue(moved.isEmpty());
    }
}
