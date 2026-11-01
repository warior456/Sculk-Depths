package net.ugi.sculk_depths.portal;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Normal-behavior tests for the world-independent array helpers in {@link Portal},
 * used by the portal frame generation steps.
 */
class PortalArrayLogicTest {

    @Test
    void addElementAppendsSingleBlockPos() {
        BlockPos[] arr = new BlockPos[0];

        BlockPos[] result = Portal.addElement(arr, new BlockPos(1, 2, 3));

        assertEquals(1, result.length);
        assertEquals(new BlockPos(1, 2, 3), result[0]);
    }

    @Test
    void addElementPreservesOrderAndDoesNotMutateInput() {
        BlockPos a = new BlockPos(1, 0, 0);
        BlockPos b = new BlockPos(2, 0, 0);
        BlockPos[] arr = new BlockPos[]{a};

        BlockPos[] result = Portal.addElement(arr, b);

        assertEquals(1, arr.length, "input array must be left unchanged");
        assertEquals(2, result.length);
        assertEquals(a, result[0]);
        assertEquals(b, result[1]);
    }

    @Test
    void addElementMergesTwoBlockPosArraysInOrder() {
        BlockPos[] first = new BlockPos[]{new BlockPos(0, 0, 0), new BlockPos(1, 0, 0)};
        BlockPos[] second = new BlockPos[]{new BlockPos(2, 0, 0)};

        BlockPos[] result = Portal.addElement(first, second);

        assertEquals(3, result.length);
        assertEquals(new BlockPos(0, 0, 0), result[0]);
        assertEquals(new BlockPos(1, 0, 0), result[1]);
        assertEquals(new BlockPos(2, 0, 0), result[2]);
    }

    @Test
    void addElementMergesEmptyArrays() {
        BlockPos[] result = Portal.addElement(new BlockPos[0], new BlockPos[0]);

        assertEquals(0, result.length);
    }

    @Test
    void addElementAppendsChunkPos() {
        ChunkPos[] arr = new ChunkPos[]{new ChunkPos(0, 0)};

        ChunkPos[] result = Portal.addElement(arr, new ChunkPos(5, -3));

        assertEquals(2, result.length);
        assertEquals(new ChunkPos(0, 0), result[0]);
        assertEquals(new ChunkPos(5, -3), result[1]);
    }
}
