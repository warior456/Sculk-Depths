package net.ugi.sculk_depths.item.custom;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ugi.sculk_depths.block.ModBlocks;

import java.util.function.Supplier;

import static net.minecraft.block.DoorBlock.HALF;

public interface Coatable {
    Supplier<BiMap<Block, Block>> COAT_LEVEL_INCREASES = Suppliers.memoize(() ->
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
                                                                                                                                            ((ImmutableBiMap.Builder)
                                                                                                                                                    ImmutableBiMap.builder()
                                                                                                                                                    .put(ModBlocks.VALTROX_LOG, ModBlocks.COATED_VALTROX_LOG))
                                                                                                                                            .put(ModBlocks.VALTROX_WOOD, ModBlocks.COATED_VALTROX_WOOD))
                                                                                                                                    .put(ModBlocks.STRIPPED_VALTROX_LOG, ModBlocks.COATED_STRIPPED_VALTROX_LOG))
                                                                                                                            .put(ModBlocks.STRIPPED_VALTROX_WOOD, ModBlocks.COATED_STRIPPED_VALTROX_WOOD))
                                                                                                                    .put(ModBlocks.VALTROX_PLANKS, ModBlocks.COATED_VALTROX_PLANKS))
                                                                                                            .put(ModBlocks.VALTROX_STAIRS, ModBlocks.COATED_VALTROX_STAIRS))
                                                                                                    .put(ModBlocks.VALTROX_SLAB, ModBlocks.COATED_VALTROX_SLAB))
                                                                                            .put(ModBlocks.VALTROX_FENCE, ModBlocks.COATED_VALTROX_FENCE))
                                                                                    .put(ModBlocks.VALTROX_FENCE_GATE, ModBlocks.COATED_VALTROX_FENCE_GATE))
                                                                            .put(ModBlocks.VALTROX_DOOR, ModBlocks.COATED_VALTROX_DOOR))
                                                                    .put(ModBlocks.VALTROX_TRAPDOOR, ModBlocks.COATED_VALTROX_TRAPDOOR))
                                                            .put(ModBlocks.VALTROX_PRESSURE_PLATE, ModBlocks.COATED_VALTROX_PRESSURE_PLATE))
                                                    .put(ModBlocks.VALTROX_BUTTON, ModBlocks.COATED_VALTROX_BUTTON))
                                            .put(ModBlocks.VALTROX_SIGN, ModBlocks.COATED_VALTROX_SIGN))
                                    .put(ModBlocks.VALTROX_WALL_SIGN, ModBlocks.COATED_VALTROX_WALL_SIGN))
                            .put(ModBlocks.VALTROX_HANGING_SIGN, ModBlocks.COATED_VALTROX_HANGING_SIGN))
                    .put(ModBlocks.VALTROX_WALL_HANGING_SIGN, ModBlocks.COATED_VALTROX_WALL_HANGING_SIGN))
            .build());
    Supplier<BiMap<Block, Block>> COAT_LEVEL_DECREASES = Suppliers.memoize(() -> COAT_LEVEL_INCREASES.get().inverse());


    static Block getCoatedBlock(Block block) {
        return COAT_LEVEL_INCREASES.get().get(block);
    }

    static Block getNonCoatedBlock(Block block) {
        return COAT_LEVEL_DECREASES.get().get(block);
    }

    static void CoatBlock(BlockState state, World world, BlockPos pos) {
        Block block = state.getBlock();

        Block coatedblock = getCoatedBlock(block);

        if (block == ModBlocks.VALTROX_DOOR){
            world.setBlockState(pos, coatedblock.getStateWithProperties(state.with(HALF, DoubleBlockHalf.LOWER)));
            world.setBlockState(pos.up(), coatedblock.getStateWithProperties(state.with(HALF, DoubleBlockHalf.UPPER)));
        }
        else world.setBlockState(pos, coatedblock.getStateWithProperties(state));
    }

    static void unCoatBlock(BlockState state, World world, BlockPos pos) {
        Block block = state.getBlock();

        Block nonCoatedblock = getNonCoatedBlock(block);
        world.setBlockState(pos, nonCoatedblock.getStateWithProperties(state));
    }
}
