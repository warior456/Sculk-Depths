package net.ugi.sculk_depths.portal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.state.property.ModProperties;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.List;

import static net.ugi.sculk_depths.block.custom.SculkDepthsPortalBlock.AXIS;

public class PortalFrame {


    public static BlockPos[] addElement(BlockPos[] arr, BlockPos e) {
        int n = arr.length;
        BlockPos[] newarr = new BlockPos[n + 1];

        for (int i = 0; i < n; i++){
            newarr[i] = arr[i];
        }

        newarr[n] = e;
        return newarr;
    }

    public static BlockPos[] addElement(BlockPos[] arr1, BlockPos[] arr2) {
        int n = arr1.length;
        int m = arr2.length;
        BlockPos[] newarr = new BlockPos[n+m];

        for (int i = 0; i < n; i++)
            newarr[i] = arr1[i];

        for (int i = 0; i < m; i++)
            newarr[i+n] = arr2[i];
        return newarr;
    }

    public static BlockPos[] getFramePos(Direction pedestalFacing, BlockPos pos, World world ) {
        switch (pedestalFacing) {
            case EAST -> {
                if (world.getBlockState(pos.north(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.north(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[]{pos.north(6).up(6).west(5),pos.north(5).up(6).west(5)};
                if (world.getBlockState(pos.south(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.south(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[]{pos.south(4).up(6).west(5),pos.south(5).up(6).west(5)};
            }
            case NORTH -> {
                if (world.getBlockState(pos.east(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.east(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[]{pos.east(5).up(6).south(5),pos.east(4).up(6).south(5)};
                if (world.getBlockState(pos.west(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.west(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[]{pos.west(5).up(6).south(5),pos.west(6).up(6).south(5)};
            }
            case WEST -> {
                if (world.getBlockState(pos.north(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.north(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[]{pos.north(5).up(6).east(5),pos.north(4).up(6).east(5)};
                if (world.getBlockState(pos.south(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.south(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[]{pos.south(5).up(6).east(5),pos.south(6).up(6).east(5)};
            }
            case SOUTH -> {
                if (world.getBlockState(pos.east(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.east(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[]{pos.east(6).up(6).north(5),pos.east(5).up(6).north(5)};
                if (world.getBlockState(pos.west(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.west(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[]{pos.west(4).up(6).north(5),pos.west(5).up(6).north(5)};
            }
        }
        return null;
    }

    public static BlockPos[] getNextpos(BlockPos pos, World world, Block block){
        BlockPos[] arr = new BlockPos[0];
        if (world.getBlockState(pos.north()).getBlock() == block){
            arr = addElement(arr,pos.north());
        }
        if (world.getBlockState(pos.south()).getBlock() == block){
            arr = addElement(arr,pos.south());
        }
        if (world.getBlockState(pos.east()).getBlock() == block){
            arr = addElement(arr,pos.east());
        }
        if (world.getBlockState(pos.west()).getBlock() == block){
            arr = addElement(arr,pos.west());
        }
        if (world.getBlockState(pos.up()).getBlock() == block){
            arr = addElement(arr,pos.up());
        }
        if (world.getBlockState(pos.down()).getBlock() == block){
            arr = addElement(arr,pos.down());
        }
        return arr;
    }

    public static BlockPos[] getNextpos(BlockPos pos, World world, TagKey<Block> tag, Direction facing){
        BlockPos[] arr = new BlockPos[0];
        if (facing == Direction.EAST || facing == Direction.WEST){
            if (world.getBlockState(pos.north()).isIn(tag)){
                arr = addElement(arr,pos.north());
            }
            if (world.getBlockState(pos.south()).isIn(tag)){
                arr = addElement(arr,pos.south());
            }
        }
        if (facing == Direction.NORTH || facing == Direction.SOUTH){
            if (world.getBlockState(pos.east()).isIn(tag)){
                arr = addElement(arr,pos.east());
            }
            if (world.getBlockState(pos.west()).isIn(tag)){
                arr = addElement(arr,pos.west());
            }
        }
        if (world.getBlockState(pos.up()).isIn(tag)){
            arr = addElement(arr,pos.up());
        }
        if (world.getBlockState(pos.down()).isIn(tag)){
            arr = addElement(arr,pos.down());
        }
        return arr;
    }

    private static Boolean checkFullFrame(BlockPos pos, World world){
        int countActivatedAmalgamite = 0;

        if (pos != null) {

            if (world.getBlockState(pos.north()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }
            if (world.getBlockState(pos.south()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }
            if (world.getBlockState(pos.east()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }
            if (world.getBlockState(pos.west()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }
            if (world.getBlockState(pos.up()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }
            if (world.getBlockState(pos.down()).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                countActivatedAmalgamite++;
            }

            if (countActivatedAmalgamite >= 2)
                return true;
            return false;
        }
        return false;
    }

    public static BlockPos[] genFrameStep(World world, BlockPos[] blockposses){
        BlockPos[] newPosArr = new BlockPos[0];
        for (BlockPos pos: blockposses) {
            BlockPos[] newPos = new BlockPos[0];
            if (world.getBlockState(pos).getBlock() != ModBlocks.ACTIVATED_AMALGAMITE) {
                world.setBlockState(pos,ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());
                    newPos = getNextpos(pos, world, Blocks.REINFORCED_DEEPSLATE);
                if (newPos.length == 0){
                    if (checkFullFrame(pos,world))
                        newPos = addElement(newPos,new BlockPos(0,-4096,1));
                    else
                        newPos = addElement(newPos,new BlockPos(0,-4096,0));
                }
                newPosArr = addElement(newPosArr,newPos);
            }
        }
        return newPosArr;
    }

    public static BlockPos[] cancelFrameStep(World world, BlockPos[] blockposses){
        BlockPos[] newPosArr = new BlockPos[0];
        for (BlockPos pos: blockposses) {
            BlockPos[] newPos = new BlockPos[0];
            if (world.getBlockState(pos).getBlock() != Blocks.REINFORCED_DEEPSLATE) {
                world.setBlockState(pos,Blocks.REINFORCED_DEEPSLATE.getDefaultState());
                newPos = getNextpos(pos, world, ModBlocks.ACTIVATED_AMALGAMITE);
                if (newPos.length == 0){
                    newPos = addElement(newPos,new BlockPos(0,-4096,0));
                }
                newPosArr = addElement(newPosArr,newPos);
            }
        }
        return newPosArr;
    }

    public static BlockPos[] genPortalStep(World world, BlockPos blockposses[], Direction facing){
        BlockState state = ModBlocks.SCULK_DEPTHS_PORTAL.getDefaultState();
        BlockPos[] newPosArr = new BlockPos[0];
        for (BlockPos pos: blockposses) {
            BlockPos[] newPos = new BlockPos[0];
            if (world.getBlockState(pos).getBlock() != ModBlocks.SCULK_DEPTHS_PORTAL) {
                if (facing == Direction.NORTH || facing == Direction.SOUTH){
                    world.setBlockState(pos,ModBlocks.SCULK_DEPTHS_PORTAL.getStateWithProperties(state.with(AXIS, Direction.Axis.X)));
                    newPos = getNextpos(pos, world, ModTags.Blocks.PORTAL_AIR,facing);
                }
                if (facing == Direction.EAST || facing == Direction.WEST){
                    world.setBlockState(pos,ModBlocks.SCULK_DEPTHS_PORTAL.getStateWithProperties(state.with(AXIS, Direction.Axis.Z)));
                    newPos = getNextpos(pos, world, ModTags.Blocks.PORTAL_AIR, facing);
                }
                if (newPos.length == 0){
                    newPos = addElement(newPos,new BlockPos(0,-4096,0));
                }
                newPosArr = addElement(newPosArr,newPos);
            }
        }
        return newPosArr;
    }

        /*BlockPos nextPos1 = pos;
        BlockPos nextPos2 = pos;

        if (world.getBlockState(pos).getBlock() == Blocks.REINFORCED_DEEPSLATE)
            world.setBlockState(pos,ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());

        while (nextPos1 != null && nextPos2 != null){

            nextPos1 = getNextFrameBlockPos(nextPos1,world, Blocks.REINFORCED_DEEPSLATE);
            if (nextPos1 == null){
                break;
            }
            world.setBlockState(nextPos1,ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());

            nextPos2 = getNextFrameBlockPos(nextPos2,world, Blocks.REINFORCED_DEEPSLATE);
            if (nextPos2 == null){
                break;
            }
            world.setBlockState(nextPos2,ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());
        }
        return checkFullFrame(nextPos1, nextPos2, world);
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

    public static boolean genFrame(BlockPos pos, World world){
        BlockPos nextPos1 = pos;
        BlockPos nextPos2 = pos;

        if (world.getBlockState(pos).getBlock() == Blocks.REINFORCED_DEEPSLATE)
            world.setBlockState(pos,ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());

        while (nextPos1 != null && nextPos2 != null){

            nextPos1 = getNextFrameBlockPos(nextPos1,world, Blocks.REINFORCED_DEEPSLATE);
            if (nextPos1 == null){
                break;
            }
            world.setBlockState(nextPos1,ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());

            nextPos2 = getNextFrameBlockPos(nextPos2,world, Blocks.REINFORCED_DEEPSLATE);
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
    }*/
}
