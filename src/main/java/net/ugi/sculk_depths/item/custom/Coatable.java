package net.ugi.sculk_depths.item.custom;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.ugi.sculk_depths.block.ModBlocks;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.DoorBlock.HALF;

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

    static void CoatBlock(BlockState state, Level level, BlockPos pos) {
        Block block = state.getBlock();

        Block coatedblock = getCoatedBlock(block);

        if (block == ModBlocks.VALTROX_DOOR){
            level.setBlockAndUpdate(pos, coatedblock.withPropertiesOf(state.setValue(HALF, DoubleBlockHalf.LOWER)));
            level.setBlockAndUpdate(pos.above(), coatedblock.withPropertiesOf(state.setValue(HALF, DoubleBlockHalf.UPPER)));
        }
        else level.setBlockAndUpdate(pos, coatedblock.withPropertiesOf(state));
    }

    static void unCoatBlock(BlockState state, Level level, BlockPos pos) {
        Block block = state.getBlock();

        Block nonCoatedblock = getNonCoatedBlock(block);
        level.setBlockAndUpdate(pos, nonCoatedblock.withPropertiesOf(state));
    }
}
