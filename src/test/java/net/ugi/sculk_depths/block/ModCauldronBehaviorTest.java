package net.ugi.sculk_depths.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ugi.sculk_depths.TestBootstrap;
import net.ugi.sculk_depths.block.custom.ModCauldron.ModCauldronBehavior;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.state.property.ModProperties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Normal-behavior tests for {@link ModCauldronBehavior} happy paths: the correct
 * item on the correct cauldron advances the level and consumes the correct stack.
 * World/player interactions are mocked; block states and item stacks are real.
 */
class ModCauldronBehaviorTest {

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

    private BlockState kryslumCauldron(int kryslumLevel) {
        return ModBlocks.KRYSLUM_FLUMROCK_CAULDRON.getDefaultState()
                .with(ModProperties.KRYSLUM_LEVEL, kryslumLevel);
    }

    @Test
    void quazarithPiecesOnKryslumCauldronAdvancesLevelAndConsumesMainHand() {
        ItemStack mainHand = new ItemStack(ModItems.QUAZARITH_PIECES, 5);
        when(player.getMainHandStack()).thenReturn(mainHand);
        when(player.getOffHandStack()).thenReturn(ItemStack.EMPTY);
        BlockState state = kryslumCauldron(3);
        BlockPos pos = new BlockPos(0, 64, 0);

        CauldronBehavior behavior = ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().get(ModItems.QUAZARITH_PIECES);
        assertNotNull(behavior, "quazarith pieces should have a registered kryslum cauldron behavior");

        ItemActionResult result = behavior.interact(state, world, pos, player, Hand.MAIN_HAND, mainHand);

        assertEquals(ItemActionResult.CONSUME, result);
        assertEquals(4, mainHand.getCount(), "one quazarith piece should be consumed");
        ArgumentCaptor<BlockState> newState = ArgumentCaptor.forClass(BlockState.class);
        verify(world).setBlockState(eq(pos), newState.capture());
        assertEquals(1, newState.getValue().get(ModProperties.QUAZARITH_LEVEL).intValue());
    }

    @Test
    void cruxOnKryslumCauldronAdvancesCruxLevelAndConsumesMainHand() {
        ItemStack mainHand = new ItemStack(ModItems.CRUX, 2);
        when(player.getMainHandStack()).thenReturn(mainHand);
        when(player.getOffHandStack()).thenReturn(ItemStack.EMPTY);
        BlockState state = kryslumCauldron(3);
        BlockPos pos = new BlockPos(0, 64, 0);

        CauldronBehavior behavior = ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().get(ModItems.CRUX);
        assertNotNull(behavior);

        ItemActionResult result = behavior.interact(state, world, pos, player, Hand.MAIN_HAND, mainHand);

        assertEquals(ItemActionResult.CONSUME, result);
        assertEquals(1, mainHand.getCount(), "one crux should be consumed");
        ArgumentCaptor<BlockState> newState = ArgumentCaptor.forClass(BlockState.class);
        verify(world).setBlockState(eq(pos), newState.capture());
        assertEquals(1, newState.getValue().get(ModProperties.CRUX_LEVEL).intValue());
    }

    @Test
    void fullCauldronRejectsFurtherMaterial() {
        ItemStack mainHand = new ItemStack(ModItems.QUAZARITH_PIECES, 5);
        BlockState state = kryslumCauldron(3).with(ModProperties.QUAZARITH_LEVEL, 12);

        CauldronBehavior behavior = ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().get(ModItems.QUAZARITH_PIECES);

        ItemActionResult result = behavior.interact(state, world, new BlockPos(0, 64, 0), player, Hand.MAIN_HAND, mainHand);

        assertEquals(ItemActionResult.SKIP_DEFAULT_BLOCK_INTERACTION, result);
        assertEquals(5, mainHand.getCount(), "nothing should be consumed when the cauldron is full");
        verify(world, never()).setBlockState(any(BlockPos.class), any(BlockState.class));
    }

    @Test
    void bucketScoopLowersKryslumLevel() {
        ItemStack mainHand = new ItemStack(Items.BUCKET, 1);
        BlockState state = kryslumCauldron(3);
        BlockPos pos = new BlockPos(0, 64, 0);

        CauldronBehavior behavior = ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().get(Items.BUCKET);
        assertNotNull(behavior);

        ItemActionResult result = behavior.interact(state, world, pos, player, Hand.MAIN_HAND, mainHand);

        assertEquals(ItemActionResult.CONSUME, result);
        ArgumentCaptor<BlockState> newState = ArgumentCaptor.forClass(BlockState.class);
        verify(world, atLeastOnce()).setBlockState(eq(pos), newState.capture());
        BlockState captured = newState.getValue();
        assertEquals(ModBlocks.KRYSLUM_FLUMROCK_CAULDRON, captured.getBlock());
        assertEquals(2, captured.get(ModProperties.KRYSLUM_LEVEL).intValue());
    }

    @Test
    void bucketScoopAtLevelOneEmptiesCauldron() {
        ItemStack mainHand = new ItemStack(Items.BUCKET, 1);
        BlockState state = kryslumCauldron(1);
        BlockPos pos = new BlockPos(0, 64, 0);

        CauldronBehavior behavior = ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().get(Items.BUCKET);

        behavior.interact(state, world, pos, player, Hand.MAIN_HAND, mainHand);

        ArgumentCaptor<BlockState> newState = ArgumentCaptor.forClass(BlockState.class);
        verify(world, atLeastOnce()).setBlockState(eq(pos), newState.capture());
        assertEquals(ModBlocks.FLUMROCK_CAULDRON, newState.getValue().getBlock(),
                "scooping the last level should return the empty flumrock cauldron");
    }

    @Test
    void kryslumBucketFillsEmptyCauldron() {
        ItemStack mainHand = new ItemStack(ModItems.KRYSLUM_BUCKET, 1);
        BlockState state = ModBlocks.FLUMROCK_CAULDRON.getDefaultState();
        BlockPos pos = new BlockPos(0, 64, 0);

        CauldronBehavior behavior = ModCauldronBehavior.EMPTY_FLUMROCK_CAULDRON_BEHAVIOR.map().get(ModItems.KRYSLUM_BUCKET);
        assertNotNull(behavior);

        ItemActionResult result = behavior.interact(state, world, pos, player, Hand.MAIN_HAND, mainHand);

        assertEquals(ItemActionResult.CONSUME, result);
        ArgumentCaptor<BlockState> newState = ArgumentCaptor.forClass(BlockState.class);
        verify(world, atLeastOnce()).setBlockState(eq(pos), newState.capture());
        assertEquals(ModBlocks.KRYSLUM_FLUMROCK_CAULDRON, newState.getValue().getBlock());
    }

    @Test
    void sporeBucketFillsEmptyCauldronWithSporeCauldron() {
        ItemStack mainHand = new ItemStack(ModItems.PENEBRIUM_SPORE_BUCKET, 1);
        BlockState state = ModBlocks.FLUMROCK_CAULDRON.getDefaultState();
        BlockPos pos = new BlockPos(0, 64, 0);

        CauldronBehavior behavior = ModCauldronBehavior.EMPTY_FLUMROCK_CAULDRON_BEHAVIOR.map().get(ModItems.PENEBRIUM_SPORE_BUCKET);
        assertNotNull(behavior);

        behavior.interact(state, world, pos, player, Hand.MAIN_HAND, mainHand);

        ArgumentCaptor<BlockState> newState = ArgumentCaptor.forClass(BlockState.class);
        verify(world, atLeastOnce()).setBlockState(eq(pos), newState.capture());
        assertEquals(ModBlocks.SPORE_FLUMROCK_CAULDRON, newState.getValue().getBlock());
    }
}
