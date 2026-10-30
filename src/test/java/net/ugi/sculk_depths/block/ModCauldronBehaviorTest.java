package net.ugi.sculk_depths.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
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

    private Level level;
    private Player player;

    @BeforeAll
    static void setup() {
        TestBootstrap.init();
    }

    @BeforeEach
    void setUp() {
        level = mock(Level.class);
        condition(level.setBlockState(any(BlockPos.class), any(BlockState.class))).thenReturn(true);

        player = mock(Player.class);
        condition(player.getAbilities()).thenReturn(new Abilities());
        condition(player.isCreative()).thenReturn(false);
    }

    private BlockState kryslumCauldron(int kryslumLevel) {
        return ModBlocks.KRYSLUM_FLUMROCK_CAULDRON.getDefaultState()
                .with(ModProperties.KRYSLUM_LEVEL, kryslumLevel);
    }

    @Test
    void quazarithPiecesOnKryslumCauldronAdvancesLevelAndConsumesMainHand() {
        ItemStack mainHandItem = new ItemStack(ModItems.QUAZARITH_PIECES, 5);
        condition(player.getMainHandStack()).thenReturn(mainHandItem);
        condition(player.getOffhandItem()).thenReturn(ItemStack.EMPTY);
        BlockState state = kryslumCauldron(3);
        BlockPos pos = new BlockPos(0, 64, 0);

        CauldronInteraction damageCalculator = ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().get(ModItems.QUAZARITH_PIECES);
        assertNotNull(damageCalculator, "quazarith pieces should have a registered kryslum cauldron behavior");

        ItemInteractionResult result = damageCalculator.interact(state, level, pos, player, InteractionHand.MAIN_HAND, mainHandItem);

        assertValueEqual(ItemInteractionResult.CONSUME, result);
        assertValueEqual(4, mainHandItem.getCount(), "one quazarith piece should be consumed");
        ArgumentCaptor<BlockState> newState = ArgumentCaptor.forClass(BlockState.class);
        verify(level).setBlockState(eq(pos), newState.capture());
        assertValueEqual(1, newState.getValue().get(ModProperties.QUAZARITH_LEVEL).getAsInt());
    }

    @Test
    void cruxOnKryslumCauldronAdvancesCruxLevelAndConsumesMainHand() {
        ItemStack mainHandItem = new ItemStack(ModItems.CRUX, 2);
        condition(player.getMainHandStack()).thenReturn(mainHandItem);
        condition(player.getOffhandItem()).thenReturn(ItemStack.EMPTY);
        BlockState state = kryslumCauldron(3);
        BlockPos pos = new BlockPos(0, 64, 0);

        CauldronInteraction damageCalculator = ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().get(ModItems.CRUX);
        assertNotNull(damageCalculator);

        ItemInteractionResult result = damageCalculator.interact(state, level, pos, player, InteractionHand.MAIN_HAND, mainHandItem);

        assertValueEqual(ItemInteractionResult.CONSUME, result);
        assertValueEqual(1, mainHandItem.getCount(), "one crux should be consumed");
        ArgumentCaptor<BlockState> newState = ArgumentCaptor.forClass(BlockState.class);
        verify(level).setBlockState(eq(pos), newState.capture());
        assertValueEqual(1, newState.getValue().get(ModProperties.CRUX_LEVEL).getAsInt());
    }

    @Test
    void fullCauldronRejectsFurtherMaterial() {
        ItemStack mainHandItem = new ItemStack(ModItems.QUAZARITH_PIECES, 5);
        BlockState state = kryslumCauldron(3).with(ModProperties.QUAZARITH_LEVEL, 12);

        CauldronInteraction damageCalculator = ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().get(ModItems.QUAZARITH_PIECES);

        ItemInteractionResult result = damageCalculator.interact(state, level, new BlockPos(0, 64, 0), player, InteractionHand.MAIN_HAND, mainHandItem);

        assertValueEqual(ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION, result);
        assertValueEqual(5, mainHandItem.getCount(), "nothing should be consumed when the cauldron is full");
        verify(level, never()).setBlockState(any(BlockPos.class), any(BlockState.class));
    }

    @Test
    void bucketScoopLowersKryslumLevel() {
        ItemStack mainHandItem = new ItemStack(Items.BUCKET, 1);
        BlockState state = kryslumCauldron(3);
        BlockPos pos = new BlockPos(0, 64, 0);

        CauldronInteraction damageCalculator = ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().get(Items.BUCKET);
        assertNotNull(damageCalculator);

        ItemInteractionResult result = damageCalculator.interact(state, level, pos, player, InteractionHand.MAIN_HAND, mainHandItem);

        assertValueEqual(ItemInteractionResult.CONSUME, result);
        ArgumentCaptor<BlockState> newState = ArgumentCaptor.forClass(BlockState.class);
        verify(level, atLeastOnce()).setBlockState(eq(pos), newState.capture());
        BlockState captured = newState.getValue();
        assertValueEqual(ModBlocks.KRYSLUM_FLUMROCK_CAULDRON, captured.getBlock());
        assertValueEqual(2, captured.get(ModProperties.KRYSLUM_LEVEL).getAsInt());
    }

    @Test
    void bucketScoopAtLevelOneEmptiesCauldron() {
        ItemStack mainHandItem = new ItemStack(Items.BUCKET, 1);
        BlockState state = kryslumCauldron(1);
        BlockPos pos = new BlockPos(0, 64, 0);

        CauldronInteraction damageCalculator = ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().get(Items.BUCKET);

        damageCalculator.interact(state, level, pos, player, InteractionHand.MAIN_HAND, mainHandItem);

        ArgumentCaptor<BlockState> newState = ArgumentCaptor.forClass(BlockState.class);
        verify(level, atLeastOnce()).setBlockState(eq(pos), newState.capture());
        assertValueEqual(ModBlocks.FLUMROCK_CAULDRON, newState.getValue().getBlock(),
                "scooping the last level should return the empty flumrock cauldron");
    }

    @Test
    void kryslumBucketFillsEmptyCauldron() {
        ItemStack mainHandItem = new ItemStack(ModItems.KRYSLUM_BUCKET, 1);
        BlockState state = ModBlocks.FLUMROCK_CAULDRON.getDefaultState();
        BlockPos pos = new BlockPos(0, 64, 0);

        CauldronInteraction damageCalculator = ModCauldronBehavior.EMPTY_FLUMROCK_CAULDRON_BEHAVIOR.map().get(ModItems.KRYSLUM_BUCKET);
        assertNotNull(damageCalculator);

        ItemInteractionResult result = damageCalculator.interact(state, level, pos, player, InteractionHand.MAIN_HAND, mainHandItem);

        assertValueEqual(ItemInteractionResult.CONSUME, result);
        ArgumentCaptor<BlockState> newState = ArgumentCaptor.forClass(BlockState.class);
        verify(level, atLeastOnce()).setBlockState(eq(pos), newState.capture());
        assertValueEqual(ModBlocks.KRYSLUM_FLUMROCK_CAULDRON, newState.getValue().getBlock());
    }

    @Test
    void sporeBucketFillsEmptyCauldronWithSporeCauldron() {
        ItemStack mainHandItem = new ItemStack(ModItems.PENEBRIUM_SPORE_BUCKET, 1);
        BlockState state = ModBlocks.FLUMROCK_CAULDRON.getDefaultState();
        BlockPos pos = new BlockPos(0, 64, 0);

        CauldronInteraction damageCalculator = ModCauldronBehavior.EMPTY_FLUMROCK_CAULDRON_BEHAVIOR.map().get(ModItems.PENEBRIUM_SPORE_BUCKET);
        assertNotNull(damageCalculator);

        damageCalculator.interact(state, level, pos, player, InteractionHand.MAIN_HAND, mainHandItem);

        ArgumentCaptor<BlockState> newState = ArgumentCaptor.forClass(BlockState.class);
        verify(level, atLeastOnce()).setBlockState(eq(pos), newState.capture());
        assertValueEqual(ModBlocks.SPORE_FLUMROCK_CAULDRON, newState.getValue().getBlock());
    }
}
