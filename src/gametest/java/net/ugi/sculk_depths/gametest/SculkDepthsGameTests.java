package net.ugi.sculk_depths.gametest;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.test.GameTest;
import net.minecraft.test.GameTestException;
import net.minecraft.test.TestContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModComponentTypes;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.item.custom.crux_resonator.OscillatorTrackerComponentList;
import net.ugi.sculk_depths.state.property.ModProperties;

import java.util.List;

/**
 * In-world happy-path GameTests. Run with: ./gradlew runGameTestServer
 */
public class SculkDepthsGameTests implements FabricGameTest {

    /**
     * Normal behavior: a kryslum flumrock cauldron can be placed in the world and
     * carries the expected default fill levels.
     */
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void kryslumCauldronPlacesWithDefaultLevels(TestContext context) {
        BlockPos pos = new BlockPos(0, 1, 0);

        context.setBlockState(pos, ModBlocks.KRYSLUM_FLUMROCK_CAULDRON);

        context.expectBlock(ModBlocks.KRYSLUM_FLUMROCK_CAULDRON, pos);
        context.assertEquals(1, context.getBlockState(pos).get(ModProperties.KRYSLUM_LEVEL).intValue(),
                "default kryslum level");
        context.assertEquals(0, context.getBlockState(pos).get(ModProperties.QUAZARITH_LEVEL).intValue(),
                "default quazarith level");
        context.assertEquals(0, context.getBlockState(pos).get(ModProperties.CRUX_LEVEL).intValue(),
                "default crux level");
        context.complete();
    }

    /**
     * Normal behavior: an amalgamite block placed in the world breaks and drops
     * itself when mined with an adequate pickaxe.
     */
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void amalgamiteDropsItselfWhenMined(TestContext context) {
        BlockPos pos = new BlockPos(0, 1, 0);
        context.setBlockState(pos, ModBlocks.AMALGAMITE);

        PlayerEntity player = context.createMockPlayer(GameMode.SURVIVAL);
        player.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.DIAMOND_PICKAXE));
        BlockPos absolute = context.getAbsolutePos(pos);
        context.getWorld().breakBlock(absolute, true, player);

        context.expectBlock(Blocks.AIR, pos);
        context.addInstantFinalTask(() -> {
            List<ItemEntity> drops = context.getWorld().getEntitiesByClass(ItemEntity.class,
                    Box.of(Vec3d.ofCenter(absolute), 5, 5, 5),
                    entity -> entity.getStack().isOf(ModBlocks.AMALGAMITE.asItem()));
            if (drops.isEmpty()) {
                throw new GameTestException("expected amalgamite to drop itself when mined with a diamond pickaxe");
            }
        });
    }

    /**
     * Normal behavior: using a Crux Resonator on a quazarith oscillator stores the
     * oscillator's position in the tracker list component.
     */
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void cruxResonatorTracksOscillator(TestContext context) {
        BlockPos pos = new BlockPos(1, 2, 1);
        context.setBlockState(pos, ModBlocks.QUAZARITH_OSCILLATOR);
        BlockPos absolute = context.getAbsolutePos(pos);

        PlayerEntity player = context.createMockPlayer(GameMode.SURVIVAL);
        ItemStack resonator = new ItemStack(ModItems.CRUX_RESONATOR);
        player.setStackInHand(Hand.MAIN_HAND, resonator);

        BlockHitResult hit = new BlockHitResult(Vec3d.ofCenter(absolute), Direction.UP, absolute, false);
        ActionResult result = resonator.useOnBlock(
                new net.minecraft.item.ItemUsageContext(context.getWorld(), player, Hand.MAIN_HAND, resonator, hit));

        context.assertTrue(result.isAccepted(), "using the resonator on an oscillator should be accepted");
        context.addInstantFinalTask(() -> {
            OscillatorTrackerComponentList trackers = resonator.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST);
            if (trackers == null || trackers.trackers().size() != 1) {
                throw new GameTestException("expected exactly one tracked oscillator, got: " + trackers);
            }
            BlockPos tracked = trackers.trackers().get(0).target()
                    .orElseThrow(() -> new GameTestException("tracked oscillator has no target"))
                    .pos();
            if (!tracked.equals(absolute)) {
                throw new GameTestException("tracked wrong position: " + tracked + " expected " + absolute);
            }
        });
    }
}
