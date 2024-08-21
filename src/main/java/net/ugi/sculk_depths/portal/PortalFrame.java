package net.ugi.sculk_depths.portal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.state.property.ModProperties;

import static net.ugi.sculk_depths.block.custom.SculkDepthsPortalBlock.AXIS;

public class PortalFrame {

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

    private static BlockPos getNextFrameBlockPos(BlockPos pos, World world, Block block){
        if (world.getBlockState(pos.north()).getBlock() == block){
            return pos.north();
        }
        if (world.getBlockState(pos.south()).getBlock() == block){
            return pos.south();
        }
        if (world.getBlockState(pos.east()).getBlock() == block){
            return pos.east();
        }
        if (world.getBlockState(pos.west()).getBlock() == block){
            return pos.west();
        }
        if (world.getBlockState(pos.up()).getBlock() == block){
            return pos.up();
        }
        if (world.getBlockState(pos.down()).getBlock() == block){
            return pos.down();
        }
        return null;
    }

    private static Boolean checkFullFrame(BlockPos pos1,BlockPos pos2, World world){
        int countActivatedAmalgamite = 0;

        if (pos1 != null) {

            if (world.getBlockState(pos1.north()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }
            if (world.getBlockState(pos1.south()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }
            if (world.getBlockState(pos1.east()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }
            if (world.getBlockState(pos1.west()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }
            if (world.getBlockState(pos1.up()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }
            if (world.getBlockState(pos1.down()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }

            if (countActivatedAmalgamite >= 2)
                return true;
            return false;
        }

        if (pos2 != null) {

            if (world.getBlockState(pos2.north()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }
            if (world.getBlockState(pos2.south()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }
            if (world.getBlockState(pos2.east()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }
            if (world.getBlockState(pos2.west()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }
            if (world.getBlockState(pos2.up()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }
            if (world.getBlockState(pos2.down()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }

            if (countActivatedAmalgamite >= 2)
                return true;
            return false;
        }
        return false;
    }

    public static boolean genFrame(BlockPos pos, World world){
        BlockPos nextPos1 = pos;
        BlockPos nextPos2 = pos;
        System.out.println("pos: " + pos);

        if (world.getBlockState(pos).getBlock() == Blocks.REINFORCED_DEEPSLATE)
            world.setBlockState(pos,ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());

        while (nextPos1 != null && nextPos2 != null){

            nextPos1 = getNextFrameBlockPos(nextPos1,world, Blocks.REINFORCED_DEEPSLATE);
            System.out.println("nextPos1: " + nextPos1);
            if (nextPos1 == null){
                break;
            }
            world.setBlockState(nextPos1,ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());

            nextPos2 = getNextFrameBlockPos(nextPos2,world, Blocks.REINFORCED_DEEPSLATE);
            System.out.println("nextPos2: " + nextPos2);
            if (nextPos2 == null){
                break;
            }
            world.setBlockState(nextPos2,ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());
        }
        return checkFullFrame(nextPos1, nextPos2, world);
    }

    public static void genPortalX(BlockPos pos, World world, int depth){
        if(world.isClient)return;
        depth++;
        if(depth > 500) {
            System.out.println("ERROR: depth exceeded 500");
            return;
        }
        BlockState state = ModBlocks.SCULK_DEPTHS_PORTAL.getDefaultState();
        if (world.getBlockState(pos.east()).getBlock() != ModBlocks.ACTIVATED_AMALGAMITE && world.getBlockState(pos.east()).getBlock() != ModBlocks.SCULK_DEPTHS_PORTAL){
            world.setBlockState(pos.east(),ModBlocks.SCULK_DEPTHS_PORTAL.getStateWithProperties(state.with(AXIS, Direction.Axis.X)));
            genPortalX(pos.east(), world, depth);
        }
        if (world.getBlockState(pos.west()).getBlock() != ModBlocks.ACTIVATED_AMALGAMITE && world.getBlockState(pos.west()).getBlock() != ModBlocks.SCULK_DEPTHS_PORTAL){
            world.setBlockState(pos.west(),ModBlocks.SCULK_DEPTHS_PORTAL.getStateWithProperties(state.with(AXIS, Direction.Axis.X)));
            genPortalX(pos.west(), world, depth);
        }
        if (world.getBlockState(pos.up()).getBlock() != ModBlocks.ACTIVATED_AMALGAMITE && world.getBlockState(pos.up()).getBlock() != ModBlocks.SCULK_DEPTHS_PORTAL){
            world.setBlockState(pos.up(),ModBlocks.SCULK_DEPTHS_PORTAL.getStateWithProperties(state.with(AXIS, Direction.Axis.X)));
            genPortalX(pos.up(), world, depth);
        }
        if (world.getBlockState(pos.down()).getBlock() != ModBlocks.ACTIVATED_AMALGAMITE && world.getBlockState(pos.down()).getBlock() != ModBlocks.SCULK_DEPTHS_PORTAL){
            world.setBlockState(pos.down(),ModBlocks.SCULK_DEPTHS_PORTAL.getStateWithProperties(state.with(AXIS, Direction.Axis.X)));
            genPortalX(pos.down(), world,depth);
        }
    }
    public static void genPortalZ(BlockPos pos, World world, int depth){
        if(world.isClient)return;
        depth++;
        if(depth > 500) {
            System.out.println("ERROR: depth exceeded 500");
            return;
        }
        BlockState state = ModBlocks.SCULK_DEPTHS_PORTAL.getDefaultState();
        if (world.getBlockState(pos.north()).getBlock() != ModBlocks.ACTIVATED_AMALGAMITE && world.getBlockState(pos.north()).getBlock() != ModBlocks.SCULK_DEPTHS_PORTAL){
            world.setBlockState(pos.north(),ModBlocks.SCULK_DEPTHS_PORTAL.getStateWithProperties(state.with(AXIS, Direction.Axis.Z)));
            genPortalZ(pos.north(), world, depth);
        }
        if (world.getBlockState(pos.south()).getBlock() != ModBlocks.ACTIVATED_AMALGAMITE && world.getBlockState(pos.south()).getBlock() != ModBlocks.SCULK_DEPTHS_PORTAL){
            world.setBlockState(pos.south(),ModBlocks.SCULK_DEPTHS_PORTAL.getStateWithProperties(state.with(AXIS, Direction.Axis.Z)));
            genPortalZ(pos.south(), world, depth);
        }
        if (world.getBlockState(pos.up()).getBlock() != ModBlocks.ACTIVATED_AMALGAMITE && world.getBlockState(pos.up()).getBlock() != ModBlocks.SCULK_DEPTHS_PORTAL){
            world.setBlockState(pos.up(),ModBlocks.SCULK_DEPTHS_PORTAL.getStateWithProperties(state.with(AXIS, Direction.Axis.Z)));
            genPortalZ(pos.up(), world, depth);
        }
        if (world.getBlockState(pos.down()).getBlock() != ModBlocks.ACTIVATED_AMALGAMITE && world.getBlockState(pos.down()).getBlock() != ModBlocks.SCULK_DEPTHS_PORTAL){
            world.setBlockState(pos.down(),ModBlocks.SCULK_DEPTHS_PORTAL.getStateWithProperties(state.with(AXIS, Direction.Axis.Z)));
            genPortalZ(pos.down(), world, depth);
        }
    }



    public static void undoFrame(BlockPos pos, World world){
        BlockPos nextPos1 = pos;
        BlockPos nextPos2 = pos;

        if (world.getBlockState(pos).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE)
            world.setBlockState(pos,Blocks.REINFORCED_DEEPSLATE.getDefaultState());

        while (nextPos1 != null && nextPos2 != null){

            nextPos1 = getNextFrameBlockPos(nextPos1,world, ModBlocks.ACTIVATED_AMALGAMITE);
            if (nextPos1 == null){
                break;
            }
            world.setBlockState(nextPos1,Blocks.REINFORCED_DEEPSLATE.getDefaultState());

            nextPos2 = getNextFrameBlockPos(nextPos2,world , ModBlocks.ACTIVATED_AMALGAMITE);
            if (nextPos2 == null){
                break;
            }
            world.setBlockState(nextPos2,Blocks.REINFORCED_DEEPSLATE.getDefaultState());
        }
    }
}
