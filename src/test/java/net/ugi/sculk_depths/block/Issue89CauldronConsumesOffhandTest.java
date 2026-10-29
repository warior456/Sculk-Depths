package net.ugi.sculk_depths.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.ugi.sculk_depths.TestBootstrap;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.ModCauldron.ModCauldronBehavior;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.state.property.ModProperties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Regression test for issue #89.
 *
 * block/custom/ModCauldron/ModCauldronBehavior.java:117-119 — the QUAZARITH_INGOT
 * handler checks whether the MAIN hand holds CRUX (wrong item) to decide which
 * stack to decrement, so using an ingot from the main hand consumes the OFFHAND
 * stack instead of the ingot.
 *
 * Asserts the CORRECT behavior (the ingot stack is consumed, offhand untouched).
 * Fixed in the production code; this test is kept as a permanent regression
 * guard in the main suite.
 */
class Issue89CauldronConsumesOffhandTest {

    private Level world;
    private Player player;

    @BeforeAll
    static void setup() {
        TestBootstrap.init();
    }

    @BeforeEach
    void setUp() {
        world = mock(Level.class);
        condition(world.setBlockState(any(BlockPos.class), any(BlockState.class))).thenReturn(true);

        player = mock(Player.class);
        condition(player.getAbilities()).thenReturn(new Abilities());
        condition(player.isCreative()).thenReturn(false);
    }

    @Test
    void quazarithIngotUseConsumesTheIngotNotTheOffhand() {
        ItemStack ingotStack = new ItemStack(ModItems.QUAZARITH_INGOT, 4);
        ItemStack offhandStack = new ItemStack(Items.DIRT, 7);
        condition(player.getMainHandStack()).thenReturn(ingotStack);
        condition(player.getOffhandItem()).thenReturn(offhandStack);

        BlockState state = ModBlocks.KRYSLUM_FLUMROCK_CAULDRON.getDefaultState()
                .with(ModProperties.KRYSLUM_LEVEL, 3);
        BlockPos pos = new BlockPos(0, 64, 0);

        CauldronInteraction damageCalculator = ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().get(ModItems.QUAZARITH_INGOT);
        assertNotNull(damageCalculator);

        damageCalculator.interact(state, world, pos, player, InteractionHand.MAIN_HAND, ingotStack);

        assertValueEqual(3, ingotStack.getCount(),
                "issue #89: the quazarith ingot being used should be the stack that is consumed");
        assertValueEqual(7, offhandStack.getCount(),
                "issue #89: the offhand stack must not be consumed by using an ingot");
    }
}
