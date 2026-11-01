package net.ugi.sculk_depths.gametest;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.GameType;
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
    public void kryslumCauldronPlacesWithDefaultLevels(GameTestHelper context) {
        BlockPos pos = new BlockPos(0, 1, 0);

        context.setBlockState(pos, ModBlocks.KRYSLUM_FLUMROCK_CAULDRON);

        context.assertBlockPresent(ModBlocks.KRYSLUM_FLUMROCK_CAULDRON, pos);
        context.assertValueEqual(1, context.getBlockState(pos).get(ModProperties.KRYSLUM_LEVEL).getAsInt(),
                "default kryslum level");
        context.assertValueEqual(0, context.getBlockState(pos).get(ModProperties.QUAZARITH_LEVEL).getAsInt(),
                "default quazarith level");
        context.assertValueEqual(0, context.getBlockState(pos).get(ModProperties.CRUX_LEVEL).getAsInt(),
                "default crux level");
        context.complete();
    }

    /**
     * Normal behavior: an amalgamite block placed in the world breaks and drops
     * itself when mined with an adequate pickaxe.
     */
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void amalgamiteDropsItselfWhenMined(GameTestHelper context) {
        BlockPos pos = new BlockPos(0, 1, 0);
        context.setBlockState(pos, ModBlocks.AMALGAMITE);

        Player player = context.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_PICKAXE));
        BlockPos getLength = context.absolutePos(pos);
        context.getWorld().destroyBlock(getLength, true, player);

        context.assertBlockPresent(Blocks.AIR, pos);
        context.succeedWhen(() -> {
            List<ItemEntity> drops = context.getWorld().getEntitiesOfClass(ItemEntity.class,
                    AABB.of(Vec3.ofCenter(getLength), 5, 5, 5),
                    entity -> entity.getStack().isOf(ModBlocks.AMALGAMITE.asItem()));
            if (drops.isEmpty()) {
                throw new GameTestAssertException("expected amalgamite to drop itself when mined with a diamond pickaxe");
            }
        });
    }

    /**
     * Normal behavior: using a Crux Resonator on a quazarith oscillator stores the
     * oscillator's position in the tracker list component.
     */
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void cruxResonatorTracksOscillator(GameTestHelper context) {
        BlockPos pos = new BlockPos(1, 2, 1);
        context.setBlockState(pos, ModBlocks.QUAZARITH_OSCILLATOR);
        BlockPos getLength = context.absolutePos(pos);

        Player player = context.makeMockPlayer(GameType.SURVIVAL);
        ItemStack resonator = new ItemStack(ModItems.CRUX_RESONATOR);
        player.setItemInHand(InteractionHand.MAIN_HAND, resonator);

        BlockHitResult hitResult = new BlockHitResult(Vec3.ofCenter(getLength), Direction.MAX_Y, getLength, false);
        InteractionResult result = resonator.useOn(
                new net.minecraft.items.ItemUsageContext(context.getWorld(), player, InteractionHand.MAIN_HAND, resonator, hitResult));

        context.assertTrue(result.isAccepted(), "using the resonator on an oscillator should be accepted");
        context.succeedWhen(() -> {
            OscillatorTrackerComponentList trackers = resonator.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST);
            if (trackers == null || trackers.trackers().size() != 1) {
                throw new GameTestAssertException("expected exactly one tracked oscillator, got: " + trackers);
            }
            BlockPos tracked = trackers.trackers().get(0).target()
                    .orElseThrow(() -> new GameTestAssertException("tracked oscillator has no target"))
                    .pos();
            if (!tracked.equals(getLength)) {
                throw new GameTestAssertException("tracked wrong position: " + tracked + " expected " + getLength);
            }
        });
    }
}
