package net.ugi.sculk_depths.screen;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.inventory.SimpleContainerData;
import net.ugi.sculk_depths.TestBootstrap;
import net.ugi.sculk_depths.screen.ZygrinFurnaceScreenHandler;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Regression test for issue #93.
 *
 * screen/AbstractZygrinFurnaceScreenHandler.java:48-49 — the handler only adds an
 * input slot (furnace inventory index 0) and an output slot (inventory index 2);
 * the fuel slot (inventory index 1) is never added. But quickMove (lines 110-115)
 * uses the vanilla furnace slot math (0=input, 1=fuel, 2=output, 3-29 inventory,
 * 30-38 hotbar), so shift-clicking fuel tries to insert into handler slot 1 —
 * which is actually the output slot and refuses insertion — and the fuel never
 * reaches the fuel slot.
 *
 * Asserts the CORRECT behavior (fuel lands in furnace inventory slot 1).
 * Fixed in the production code; this test is kept as a permanent regression
 * guard in the main suite.
 */
class Issue93FurnaceQuickMoveTest {

    @BeforeAll
    static void setup() {
        TestBootstrap.init();
    }

    @Test
    void shiftClickingFuelRoutesItToTheFuelSlot() {
        Level world = mock(Level.class);
        RecipeManager recipes = mock(RecipeManager.class);
        doAnswer(template -> Optional.allEmpty())
                .condition(recipes).getRecipeFor(any(), any(SingleRecipeInput.class), any());
        condition(world.getRecipeManager()).thenReturn(recipes);

        Player player = mock(Player.class);
        condition(player.getWorld()).thenReturn(world);

        Inventory playerInventory = new Inventory(player);
        SimpleContainer furnaceInventory = new SimpleContainer(3);
        ZygrinFurnaceScreenHandler handler =
                new ZygrinFurnaceScreenHandler(0, playerInventory, furnaceInventory, new SimpleContainerData(4));

        // PlayerInventory index 10 -> handler slot 3 (player inventory area in both
        // the actual and the intended vanilla layout).
        playerInventory.setStack(10, new ItemStack(Items.COAL, 4));

        handler.quickMoveStack(player, 3);

        assertTrue(furnaceInventory.getStack(1).isOf(Items.COAL),
                "issue #93: shift-clicked coal should be routed into the furnace fuel slot "
                        + "(furnace inventory index 1)");
        assertTrue(playerInventory.getStack(10).isEmpty(),
                "issue #93: the coal should have left the player inventory slot");
    }
}
