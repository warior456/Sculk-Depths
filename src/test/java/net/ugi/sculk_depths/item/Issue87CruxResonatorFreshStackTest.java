package net.ugi.sculk_depths.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;
import net.ugi.sculk_depths.TestBootstrap;
import net.ugi.sculk_depths.item.ModComponentTypes;
import net.ugi.sculk_depths.item.ModItems;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Regression test for issue #87.
 *
 * item/custom/crux_resonator/CruxResonator.java:49-50 — use() reads the
 * OSCILLATOR_TRACKER_LIST component and immediately dereferences it. A fresh
 * Crux Resonator has no such component, so get() returns null and the call
 * throws a NullPointerException.
 *
 * Asserts the CORRECT behavior (using a fresh resonator succeeds and leaves an
 * initialized tracker list on the stack). Fixed in the production code; this test
 * is kept as a permanent regression guard in the main suite.
 */
class Issue87CruxResonatorFreshStackTest {

    @BeforeAll
    static void setup() {
        TestBootstrap.init();
    }

    @Test
    void usingFreshCruxResonatorDoesNotThrow() {
        // Mocked World leaves the public final isClient field false -> server path,
        // which is where the NPE lives.
        Level world = mock(Level.class);
        Player player = mock(Player.class);
        ItemStack freshResonator = new ItemStack(ModItems.CRUX_RESONATOR);
        assertNull(freshResonator.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST),
                "a fresh resonator has no tracker list component (precondition of the bug)");
        condition(player.getItemInHand(InteractionHand.MAIN_HAND)).thenReturn(freshResonator);

        InteractionResultHolder<ItemStack> result = assertDoesNotThrow(
                () -> ModItems.CRUX_RESONATOR.use(world, player, InteractionHand.MAIN_HAND),
                "issue #87: using a fresh Crux Resonator must not throw");

        assertTrue(result.getResult().isAccepted());
        assertNotNull(freshResonator.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST),
                "using the resonator should leave a tracker list component on the stack");
    }
}
