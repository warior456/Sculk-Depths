package net.ugi.sculk_depths.portal;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.state.property.ModProperties;

import java.util.Properties;

public class TestFrame {

    public static BlockPos getFramePos(Direction pedestalFacing, BlockPos pos, World world ) {
        switch (pedestalFacing) {
            case EAST -> {
                if (world.getBlockState(pos.north(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.north(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.north(5).up(6).west(5);
                if (world.getBlockState(pos.south(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.south(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.south(5).up(6).west(5);
            }
            case NORTH -> {
                if (world.getBlockState(pos.east(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.east(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.east(5).up(6).south(5);
                if (world.getBlockState(pos.west(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.west(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.west(5).up(6).south(5);
            }
            case WEST -> {
                if (world.getBlockState(pos.north(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.north(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.north(5).up(6).east(5);
                if (world.getBlockState(pos.south(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.south(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.south(5).up(6).east(5);
            }
            case SOUTH -> {
                if (world.getBlockState(pos.east(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.east(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.east(5).up(6).north(5);
                if (world.getBlockState(pos.west(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.west(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.west(5).up(6).north(5);
            }
        }
        return null;
    }
}
