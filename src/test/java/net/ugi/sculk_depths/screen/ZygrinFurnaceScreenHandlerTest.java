package net.ugi.sculk_depths.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.world.World;
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

    private PlayerEntity player;
    private PlayerInventory playerInventory;
    private SimpleInventory furnaceInventory;
    private ArrayPropertyDelegate propertyDelegate;
    private ZygrinFurnaceScreenHandler handler;

    @BeforeAll
    static void setup() {
        TestBootstrap.init();
    }

    @BeforeEach
    void setUp() {
        World world = mock(World.class);
        RecipeManager recipeManager = mock(RecipeManager.class);
        // Only raw iron counts as smeltable in these tests.
        RecipeEntry<?> smeltingEntry = mock(RecipeEntry.class);
        doAnswer(invocation -> {
            SingleStackRecipeInput input = invocation.getArgument(1);
            return input.item().isOf(Items.RAW_IRON) ? Optional.of(smeltingEntry) : Optional.empty();
        }).when(recipeManager).getFirstMatch(any(), any(SingleStackRecipeInput.class), any());
        when(world.getRecipeManager()).thenReturn(recipeManager);

        player = mock(PlayerEntity.class);
        when(player.getWorld()).thenReturn(world);

        playerInventory = new PlayerInventory(player);
        furnaceInventory = new SimpleInventory(3);
        propertyDelegate = new ArrayPropertyDelegate(4);
        handler = new ZygrinFurnaceScreenHandler(0, playerInventory, furnaceInventory, propertyDelegate);
    }

    @Test
    void handlerBuildsExpectedSlotLayout() {
        assertEquals(38, handler.slots.size(), "input + output + 27 inventory + 9 hotbar slots");
        assertSame(furnaceInventory, handler.getSlot(0).inventory);
        assertEquals(0, handler.getSlot(0).getIndex());
        assertInstanceOf(FurnaceOutputSlot.class, handler.getSlot(1));
    }

    @Test
    void playerCanUseHandler() {
        assertTrue(handler.canUse(player));
    }

    @Test
    void cookProgressReflectsPropertyDelegate() {
        propertyDelegate.set(2, 50);
        propertyDelegate.set(3, 100);
        assertEquals(0.5f, handler.getCookProgress(), 1.0e-6);

        propertyDelegate.set(2, 0);
        assertEquals(0.0f, handler.getCookProgress(), 1.0e-6);
    }

    @Test
    void fuelProgressAndBurningReflectLitTime() {
        propertyDelegate.set(0, 0);
        assertFalse(handler.isBurning());
        assertEquals(0.0f, handler.getFuelProgress(), 1.0e-6);

        propertyDelegate.set(0, 10);
        assertTrue(handler.isBurning());
        assertEquals(1.0f, handler.getFuelProgress(), 1.0e-6);
    }

    @Test
    void quickMoveRoutesSmeltableItemToInputSlot() {
        // PlayerInventory index 10 = second main-inventory row slot (handler slot 3).
        playerInventory.setStack(10, new ItemStack(Items.RAW_IRON, 4));

        ItemStack moved = handler.quickMove(player, 3);

        assertFalse(moved.isEmpty(), "shift-clicking a smeltable item should move it");
        assertTrue(furnaceInventory.getStack(0).isOf(Items.RAW_IRON),
                "smeltable item should land in the furnace input slot");
        assertEquals(4, furnaceInventory.getStack(0).getCount());
        assertTrue(playerInventory.getStack(10).isEmpty());
    }

    @Test
    void quickMoveRoutesOrdinaryItemFromInventoryToHotbar() {
        playerInventory.setStack(10, new ItemStack(Items.DIRT, 3));

        ItemStack moved = handler.quickMove(player, 3);

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
        ItemStack moved = handler.quickMove(player, 5);

        assertTrue(moved.isEmpty());
    }
}
