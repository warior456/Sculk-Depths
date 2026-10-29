package net.ugi.sculk_depths.portal;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.ugi.sculk_depths.TestBootstrap;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.state.property.ModProperties;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Equivalence tests for the refactored portal frame geometry helpers.
 *
 * <p>The expected values below are the verbatim pre-refactor expressions from
 * upstream/portal, evaluated against the fixed origin {@code (10, 64, -7)}.
 * They are hard-coded as plain coordinates so a change in either helper or its
 * callers will fail here rather than silently drift.</p>
 */
class PortalFrameGeometryEquivalenceTest {

    /**
     * Deliberately asymmetric origin so that swapped axes or dropped signs show up.
     */
    private static final BlockPos ORIGIN = new BlockPos(10, 64, -7);

    private static final BlockState POWERED_PEDESTAL;

    static {
        TestBootstrap.init();
        POWERED_PEDESTAL = ModBlocks.SCULK_PEDESTAL.getDefaultState()
                .with(ModProperties.HAS_ENERGY_ESSENCE, true);
    }

    private static Level worldWithPoweredPedestalAt(BlockPos pedestalPos) {
        Level world = mock(Level.class);
        condition(world.getBlockState(any(BlockPos.class))).thenReturn(Blocks.AIR.getDefaultState());
        condition(world.getBlockState(pedestalPos)).thenReturn(POWERED_PEDESTAL);
        return world;
    }

    @Test
    void eastNorthFrameMatchesOriginalOffsets() {
        Level world = worldWithPoweredPedestalAt(ORIGIN.north(10));

        List<BlockPos> createTopFrame = Portal.getFramePos(Direction.EAST, ORIGIN, world);
        assertNotNull(createTopFrame);
        assertValueEqual(2, createTopFrame.size());
        assertValueEqual(new BlockPos(5, 70, -13), createTopFrame.get(0), "east/north frame first corner");
        assertValueEqual(new BlockPos(5, 70, -12), createTopFrame.get(1), "east/north frame second corner");

        BlockPos anchor = Portal.getFrameAnchorPos(Direction.EAST, ORIGIN, world);
        assertValueEqual(new BlockPos(5, 77, -12), anchor, "east/north anchor");
    }

    @Test
    void eastSouthFrameMatchesOriginalOffsets() {
        Level world = worldWithPoweredPedestalAt(ORIGIN.south(10));

        List<BlockPos> createTopFrame = Portal.getFramePos(Direction.EAST, ORIGIN, world);
        assertNotNull(createTopFrame);
        assertValueEqual(2, createTopFrame.size());
        assertValueEqual(new BlockPos(5, 70, -3), createTopFrame.get(0), "east/south frame first corner");
        assertValueEqual(new BlockPos(5, 70, -2), createTopFrame.get(1), "east/south frame second corner");

        BlockPos anchor = Portal.getFrameAnchorPos(Direction.EAST, ORIGIN, world);
        assertValueEqual(new BlockPos(5, 77, -2), anchor, "east/south anchor");
    }

    @Test
    void northEastFrameMatchesOriginalOffsets() {
        Level world = worldWithPoweredPedestalAt(ORIGIN.east(10));

        List<BlockPos> createTopFrame = Portal.getFramePos(Direction.NORTH, ORIGIN, world);
        assertNotNull(createTopFrame);
        assertValueEqual(2, createTopFrame.size());
        assertValueEqual(new BlockPos(15, 70, -2), createTopFrame.get(0), "north/east frame first corner");
        assertValueEqual(new BlockPos(14, 70, -2), createTopFrame.get(1), "north/east frame second corner");

        BlockPos anchor = Portal.getFrameAnchorPos(Direction.NORTH, ORIGIN, world);
        assertValueEqual(new BlockPos(15, 77, -2), anchor, "north/east anchor");
    }

    @Test
    void northWestFrameMatchesOriginalOffsets() {
        Level world = worldWithPoweredPedestalAt(ORIGIN.west(10));

        List<BlockPos> createTopFrame = Portal.getFramePos(Direction.NORTH, ORIGIN, world);
        assertNotNull(createTopFrame);
        assertValueEqual(2, createTopFrame.size());
        assertValueEqual(new BlockPos(5, 70, -2), createTopFrame.get(0), "north/west frame first corner");
        assertValueEqual(new BlockPos(4, 70, -2), createTopFrame.get(1), "north/west frame second corner");

        BlockPos anchor = Portal.getFrameAnchorPos(Direction.NORTH, ORIGIN, world);
        assertValueEqual(new BlockPos(5, 77, -2), anchor, "north/west anchor");
    }

    @Test
    void westNorthFrameMatchesOriginalOffsets() {
        Level world = worldWithPoweredPedestalAt(ORIGIN.north(10));

        List<BlockPos> createTopFrame = Portal.getFramePos(Direction.WEST, ORIGIN, world);
        assertNotNull(createTopFrame);
        assertValueEqual(2, createTopFrame.size());
        assertValueEqual(new BlockPos(15, 70, -12), createTopFrame.get(0), "west/north frame first corner");
        assertValueEqual(new BlockPos(15, 70, -11), createTopFrame.get(1), "west/north frame second corner");

        BlockPos anchor = Portal.getFrameAnchorPos(Direction.WEST, ORIGIN, world);
        assertValueEqual(new BlockPos(15, 77, -12), anchor, "west/north anchor");
    }

    @Test
    void westSouthFrameMatchesOriginalOffsets() {
        Level world = worldWithPoweredPedestalAt(ORIGIN.south(10));

        List<BlockPos> createTopFrame = Portal.getFramePos(Direction.WEST, ORIGIN, world);
        assertNotNull(createTopFrame);
        assertValueEqual(2, createTopFrame.size());
        assertValueEqual(new BlockPos(15, 70, -2), createTopFrame.get(0), "west/south frame first corner");
        assertValueEqual(new BlockPos(15, 70, -1), createTopFrame.get(1), "west/south frame second corner");

        BlockPos anchor = Portal.getFrameAnchorPos(Direction.WEST, ORIGIN, world);
        assertValueEqual(new BlockPos(15, 77, -2), anchor, "west/south anchor");
    }

    @Test
    void southEastFrameMatchesOriginalOffsets() {
        Level world = worldWithPoweredPedestalAt(ORIGIN.east(10));

        List<BlockPos> createTopFrame = Portal.getFramePos(Direction.SOUTH, ORIGIN, world);
        assertNotNull(createTopFrame);
        assertValueEqual(2, createTopFrame.size());
        assertValueEqual(new BlockPos(16, 70, -12), createTopFrame.get(0), "south/east frame first corner");
        assertValueEqual(new BlockPos(15, 70, -12), createTopFrame.get(1), "south/east frame second corner");

        BlockPos anchor = Portal.getFrameAnchorPos(Direction.SOUTH, ORIGIN, world);
        assertValueEqual(new BlockPos(15, 77, -12), anchor, "south/east anchor");
    }

    @Test
    void southWestFrameMatchesOriginalOffsets() {
        Level world = worldWithPoweredPedestalAt(ORIGIN.west(10));

        List<BlockPos> createTopFrame = Portal.getFramePos(Direction.SOUTH, ORIGIN, world);
        assertNotNull(createTopFrame);
        assertValueEqual(2, createTopFrame.size());
        assertValueEqual(new BlockPos(6, 70, -12), createTopFrame.get(0), "south/west frame first corner");
        assertValueEqual(new BlockPos(5, 70, -12), createTopFrame.get(1), "south/west frame second corner");

        BlockPos anchor = Portal.getFrameAnchorPos(Direction.SOUTH, ORIGIN, world);
        assertValueEqual(new BlockPos(5, 77, -12), anchor, "south/west anchor");
    }
}
