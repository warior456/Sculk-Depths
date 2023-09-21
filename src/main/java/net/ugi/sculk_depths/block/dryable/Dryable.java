package net.ugi.sculk_depths.block.dryable;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.ugi.sculk_depths.block.ModBlocks;

import java.util.function.Supplier;

import static net.minecraft.block.DoorBlock.HALF;

public interface Dryable {
    Supplier<BiMap<Block, Block>> DRY_LEVEL_INCREASES = Suppliers.memoize(() -> ((ImmutableBiMap.Builder)
            ((ImmutableBiMap.Builder)
                    ((ImmutableBiMap.Builder)
                            ((ImmutableBiMap.Builder)
                                    ((ImmutableBiMap.Builder)
                                            ((ImmutableBiMap.Builder)
                                                    ((ImmutableBiMap.Builder)
                                                            ((ImmutableBiMap.Builder)
                                                                    ((ImmutableBiMap.Builder)
                                                                            ((ImmutableBiMap.Builder)
                                                                                    ((ImmutableBiMap.Builder)
                                                                                            ((ImmutableBiMap.Builder)
                                                                                                    ((ImmutableBiMap.Builder)
                                                                                                            ((ImmutableBiMap.Builder)
                                                                                                                    ((ImmutableBiMap.Builder)
                                                                                                                            ((ImmutableBiMap.Builder)
                                                                                                                                    ((ImmutableBiMap.Builder)
                                                                                                                                            ImmutableBiMap.builder()
                                                                                                                                                    .put(ModBlocks.VALTROX_LOG, ModBlocks.DRIED_VALTROX_LOG))
                                                                                                                                            .put(ModBlocks.VALTROX_WOOD, ModBlocks.DRIED_VALTROX_WOOD))
                                                                                                                                    .put(ModBlocks.STRIPPED_VALTROX_LOG, ModBlocks.STRIPPED_DRIED_VALTROX_LOG))
                                                                                                                            .put(ModBlocks.STRIPPED_VALTROX_WOOD, ModBlocks.STRIPPED_DRIED_VALTROX_WOOD))
                                                                                                                    .put(ModBlocks.VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_PLANKS))
                                                                                                            .put(ModBlocks.VALTROX_STAIRS, ModBlocks.DRIED_VALTROX_STAIRS))
                                                                                                    .put(ModBlocks.VALTROX_SLAB, ModBlocks.DRIED_VALTROX_SLAB))
                                                                                            .put(ModBlocks.VALTROX_FENCE, ModBlocks.DRIED_VALTROX_FENCE))
                                                                                    .put(ModBlocks.VALTROX_FENCE_GATE, ModBlocks.DRIED_VALTROX_FENCE_GATE))
                                                                            .put(ModBlocks.VALTROX_DOOR, ModBlocks.DRIED_VALTROX_DOOR))
                                                                    .put(ModBlocks.VALTROX_TRAPDOOR, ModBlocks.DRIED_VALTROX_TRAPDOOR))
                                                            .put(ModBlocks.VALTROX_PRESSURE_PLATE, ModBlocks.DRIED_VALTROX_PRESSURE_PLATE))
                                                    .put(ModBlocks.VALTROX_BUTTON, ModBlocks.DRIED_VALTROX_BUTTON))
                                            .put(ModBlocks.VALTROX_SIGN, ModBlocks.DRIED_VALTROX_SIGN))
                                    .put(ModBlocks.VALTROX_WALL_SIGN, ModBlocks.DRIED_VALTROX_WALL_SIGN))
                            .put(ModBlocks.VALTROX_HANGING_SIGN, ModBlocks.DRIED_VALTROX_HANGING_SIGN))
                    .put(ModBlocks.VALTROX_WALL_HANGING_SIGN, ModBlocks.DRIED_VALTROX_WALL_HANGING_SIGN))
            .build());
    Supplier<BiMap<Block, Block>> DRY_LEVEL_DECREASES = Suppliers.memoize(() -> DRY_LEVEL_INCREASES.get().inverse());


    static Block getDriedBlock(Block block) {
        return DRY_LEVEL_INCREASES.get().get(block);
    }

    static Block getNonDriedBlock(Block block) {
        return DRY_LEVEL_DECREASES.get().get(block);
    }

    static void DryBlock(BlockState state, ServerWorld world, BlockPos pos) {
        Block block = state.getBlock();

        Block driedblock = getDriedBlock(block);
        world.setBlockState(pos, driedblock.getStateWithProperties(state));

        if (block == ModBlocks.VALTROX_DOOR){
            world.setBlockState(pos.up(), driedblock.getStateWithProperties(state.with(HALF, DoubleBlockHalf.UPPER)));
            world.setBlockState(pos, driedblock.getStateWithProperties(state.with(HALF, DoubleBlockHalf.LOWER)));
            world.setBlockState(pos.up(), driedblock.getStateWithProperties(state.with(HALF, DoubleBlockHalf.UPPER)));
        }
    }

    static void ReviveBlock(BlockState state, World world, BlockPos pos) {
        Block block = state.getBlock();

        Block driedblock = getNonDriedBlock(block);
        world.setBlockState(pos, driedblock.getStateWithProperties(state));
    }
}
