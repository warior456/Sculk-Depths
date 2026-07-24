package net.ugi.sculk_depths.regression;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.ugi.sculk_depths.TestBootstrap;
import net.ugi.sculk_depths.screen.ZygrinFurnaceScreenHandler;
import net.minecraft.world.World;
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
 * Asserts the CORRECT behavior (fuel lands in furnace inventory slot 1), so it
 * FAILS on the current buggy code. That failure is the expected proof.
 */
class Issue93FurnaceQuickMoveTest {

    @BeforeAll
    static void setup() {
        TestBootstrap.init();
    }

    @Test
    void shiftClickingFuelRoutesItToTheFuelSlot() {
        World world = mock(World.class);
        RecipeManager recipeManager = mock(RecipeManager.class);
        doAnswer(invocation -> Optional.empty())
                .when(recipeManager).getFirstMatch(any(), any(SingleStackRecipeInput.class), any());
        when(world.getRecipeManager()).thenReturn(recipeManager);

        PlayerEntity player = mock(PlayerEntity.class);
        when(player.getWorld()).thenReturn(world);

        PlayerInventory playerInventory = new PlayerInventory(player);
        SimpleInventory furnaceInventory = new SimpleInventory(3);
        ZygrinFurnaceScreenHandler handler =
                new ZygrinFurnaceScreenHandler(0, playerInventory, furnaceInventory, new ArrayPropertyDelegate(4));

        // PlayerInventory index 10 -> handler slot 3 (player inventory area in both
        // the actual and the intended vanilla layout).
        playerInventory.setStack(10, new ItemStack(Items.COAL, 4));

        handler.quickMove(player, 3);

        assertTrue(furnaceInventory.getStack(1).isOf(Items.COAL),
                "issue #93: shift-clicked coal should be routed into the furnace fuel slot "
                        + "(furnace inventory index 1)");
        assertTrue(playerInventory.getStack(10).isEmpty(),
                "issue #93: the coal should have left the player inventory slot");
    }
}
