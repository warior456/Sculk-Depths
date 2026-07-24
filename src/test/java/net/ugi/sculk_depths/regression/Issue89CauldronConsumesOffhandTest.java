package net.ugi.sculk_depths.regression;

import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
 * Asserts the CORRECT behavior (the ingot stack is consumed, offhand untouched),
 * so it FAILS on the current buggy code. That failure is the expected proof.
 */
class Issue89CauldronConsumesOffhandTest {

    private World world;
    private PlayerEntity player;

    @BeforeAll
    static void setup() {
        TestBootstrap.init();
    }

    @BeforeEach
    void setUp() {
        world = mock(World.class);
        when(world.setBlockState(any(BlockPos.class), any(BlockState.class))).thenReturn(true);

        player = mock(PlayerEntity.class);
        when(player.getAbilities()).thenReturn(new PlayerAbilities());
        when(player.isCreative()).thenReturn(false);
    }

    @Test
    void quazarithIngotUseConsumesTheIngotNotTheOffhand() {
        ItemStack ingotStack = new ItemStack(ModItems.QUAZARITH_INGOT, 4);
        ItemStack offhandStack = new ItemStack(Items.DIRT, 7);
        when(player.getMainHandStack()).thenReturn(ingotStack);
        when(player.getOffHandStack()).thenReturn(offhandStack);

        BlockState state = ModBlocks.KRYSLUM_FLUMROCK_CAULDRON.getDefaultState()
                .with(ModProperties.KRYSLUM_LEVEL, 3);
        BlockPos pos = new BlockPos(0, 64, 0);

        CauldronBehavior behavior = ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().get(ModItems.QUAZARITH_INGOT);
        assertNotNull(behavior);

        behavior.interact(state, world, pos, player, Hand.MAIN_HAND, ingotStack);

        assertEquals(3, ingotStack.getCount(),
                "issue #89: the quazarith ingot being used should be the stack that is consumed");
        assertEquals(7, offhandStack.getCount(),
                "issue #89: the offhand stack must not be consumed by using an ingot");
    }
}
