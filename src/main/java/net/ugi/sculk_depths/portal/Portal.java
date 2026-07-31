package net.ugi.sculk_depths.portal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.particle.ModParticleTypes;
import net.ugi.sculk_depths.state.property.ModProperties;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.ugi.sculk_depths.block.custom.SculkDepthsPortalBlock.AXIS;

public class Portal {


    public static BlockPos[] addElement(BlockPos[] arr, BlockPos e) {
        List<BlockPos> list = new ArrayList<>(Arrays.asList(arr));
        list.add(e);
        return list.toArray(new BlockPos[0]);
    }

    public static BlockPos[] addElement(BlockPos[] arr1, BlockPos[] arr2) {
        List<BlockPos> list = new ArrayList<>(arr1.length + arr2.length);
        list.addAll(Arrays.asList(arr1));
        list.addAll(Arrays.asList(arr2));
        return list.toArray(new BlockPos[0]);
    }

    public static ChunkPos[] addElement(ChunkPos[] arr, ChunkPos e) {
        List<ChunkPos> list = new ArrayList<>(Arrays.asList(arr));
        list.add(e);
        return list.toArray(new ChunkPos[0]);
    }

    private static boolean hasPoweredPedestal(World world, BlockPos pos, Direction direction) {
        BlockState state = world.getBlockState(pos.offset(direction, 10));
        return state.getBlock() == ModBlocks.SCULK_PEDESTAL && state.get(ModProperties.HAS_ENERGY_ESSENCE);
    }

    public static List<BlockPos> getFramePos(Direction pedestalFacing, BlockPos pos, World world ) {
        Direction right = pedestalFacing.rotateYClockwise();
        Direction left = pedestalFacing.rotateYCounterclockwise();
        if (hasPoweredPedestal(world, pos, right)) {
            return getFramePositionsForSide(pos, pedestalFacing, right);
        }
        if (hasPoweredPedestal(world, pos, left)) {
            return getFramePositionsForSide(pos, pedestalFacing, left);
        }
        return null;
    }

    private static List<BlockPos> getFramePositionsForSide(BlockPos pos, Direction facing, Direction side) {
        Direction depth = facing.getOpposite();
        boolean isClockwise = side == facing.rotateYClockwise();
        boolean facingPositiveAxis = facing == Direction.EAST || facing == Direction.SOUTH;
        int a;
        int b;
        if (isClockwise) {
            a = facingPositiveAxis ? 4 : 5;
            b = facingPositiveAxis ? 5 : 4;
        } else {
            a = facingPositiveAxis ? 6 : 5;
            b = facingPositiveAxis ? 5 : 6;
        }
        return List.of(
                pos.offset(depth, 5).up(6).offset(side, a),
                pos.offset(depth, 5).up(6).offset(side, b)
        );
    }

    public static BlockPos getFrameAnchorPos(Direction pedestalFacing, BlockPos pos, World world ) {
        Direction right = pedestalFacing.rotateYClockwise();
        Direction left = pedestalFacing.rotateYCounterclockwise();
        if (hasPoweredPedestal(world, pos, right)) {
            return getAnchorPosForSide(pos, pedestalFacing, right);
        }
        if (hasPoweredPedestal(world, pos, left)) {
            return getAnchorPosForSide(pos, pedestalFacing, left);
        }
        return null;
    }

    private static BlockPos getAnchorPosForSide(BlockPos pos, Direction facing, Direction side) {
        return pos.offset(side, 5).up(13).offset(facing.getOpposite(), 5);
    }

    public static List<BlockPos> getNextpos(BlockPos pos, World world, Block block){
        List<BlockPos> list = new ArrayList<>();
        if (world.getBlockState(pos.north()).getBlock() == block){
            list.add(pos.north());
        }
        if (world.getBlockState(pos.south()).getBlock() == block){
            list.add(pos.south());
        }
        if (world.getBlockState(pos.east()).getBlock() == block){
            list.add(pos.east());
        }
        if (world.getBlockState(pos.west()).getBlock() == block){
            list.add(pos.west());
        }
        if (world.getBlockState(pos.up()).getBlock() == block){
            list.add(pos.up());
        }
        if (world.getBlockState(pos.down()).getBlock() == block){
            list.add(pos.down());
        }
        return list;
    }

    public static List<BlockPos> getNextpos(BlockPos pos, World world, TagKey<Block> tag, Direction facing){
        List<BlockPos> list = new ArrayList<>();
        if (facing == Direction.EAST || facing == Direction.WEST){
            if (world.getBlockState(pos.north()).isIn(tag)){
                list.add(pos.north());
            }
            if (world.getBlockState(pos.south()).isIn(tag)){
                list.add(pos.south());
            }
        }
        if (facing == Direction.NORTH || facing == Direction.SOUTH){
            if (world.getBlockState(pos.east()).isIn(tag)){
                list.add(pos.east());
            }
            if (world.getBlockState(pos.west()).isIn(tag)){
                list.add(pos.west());
            }
        }
        if (world.getBlockState(pos.up()).isIn(tag)){
            list.add(pos.up());
        }
        if (world.getBlockState(pos.down()).isIn(tag)){
            list.add(pos.down());
        }
        return list;
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

    public static List<BlockPos> genFrameStep(World world, List<BlockPos> blockposses, Random random){
        List<BlockPos> newPosArr = new ArrayList<>();
        newPosArr.add(new BlockPos(0,-4096,0));
        for (BlockPos pos: blockposses) {
            List<BlockPos> newPos = new ArrayList<>();
            if (world.getBlockState(pos).getBlock() != ModBlocks.ACTIVATED_AMALGAMITE) {
                world.setBlockState(pos,ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());
                Portal.addBlockPowerUpParticle((ServerWorld) world, pos, random, 10);
                    newPos = getNextpos(pos, world, Blocks.REINFORCED_DEEPSLATE);
                if (newPos.isEmpty()){
                    if (checkFullFrame(pos,world))
                        newPos.add(new BlockPos(0,-4096,1));
                    else
                        newPos.add(new BlockPos(0,-4096,0));
                }
                newPosArr.addAll(newPos);
            }
        }
        return newPosArr;
    }

    public static List<BlockPos> cancelFrameStep(World world, List<BlockPos> blockposses){
        List<BlockPos> newPosArr = new ArrayList<>();
        newPosArr.add(new BlockPos(0,-4096,0));
        for (BlockPos pos: blockposses) {
            List<BlockPos> newPos = new ArrayList<>();
            if (world.getBlockState(pos).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                world.setBlockState(pos,Blocks.REINFORCED_DEEPSLATE.getDefaultState());
                newPos = getNextpos(pos, world, ModBlocks.ACTIVATED_AMALGAMITE);
                if (newPos.isEmpty()){
                    newPos.add(new BlockPos(0,-4096,0));
                }
                newPosArr.addAll(newPos);
            }
        }
        return newPosArr;
    }

    public static List<BlockPos> genPortalStep(World world, List<BlockPos> blockposses, Direction facing, Random random){
        BlockState state = ModBlocks.SCULK_DEPTHS_PORTAL.getDefaultState();
        List<BlockPos> newPosArr = new ArrayList<>();
        newPosArr.add(new BlockPos(0,-4096,0));
        for (BlockPos pos: blockposses) {
            List<BlockPos> newPos = new ArrayList<>();
            if (world.getBlockState(pos).getBlock() != ModBlocks.SCULK_DEPTHS_PORTAL) {
                if (facing == Direction.NORTH || facing == Direction.SOUTH){
                    world.setBlockState(pos,ModBlocks.SCULK_DEPTHS_PORTAL.getStateWithProperties(state.with(AXIS, Direction.Axis.X)));
                    newPos = getNextpos(pos, world, ModTags.Blocks.PORTAL_AIR,facing);
                }
                if (facing == Direction.EAST || facing == Direction.WEST){
                    world.setBlockState(pos,ModBlocks.SCULK_DEPTHS_PORTAL.getStateWithProperties(state.with(AXIS, Direction.Axis.Z)));
                    newPos = getNextpos(pos, world, ModTags.Blocks.PORTAL_AIR, facing);
                }
                if (newPos.isEmpty()){
                    newPos.add(new BlockPos(0,-4096,0));
                }
                newPosArr.addAll(newPos);
            }
        }
        return newPosArr;
    }

    public static void addBlockPowerUpParticle(ServerWorld world, BlockPos pos, Random random, int amount){
        //System.out.println("particle");
        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < 6; j++) {
                float x = pos.getX() + 0.5f;
                float y = pos.getY() + 0.5f;
                float z = pos.getZ() + 0.5f;
                switch (j){
                    case 0://positivex
                        x = x + 0.6f + MathHelper.nextFloat(random, 0f, 0.2f);
                        y = y + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        z = z + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        break;
                    case 1://negativex
                        x = x - 0.6f - MathHelper.nextFloat(random, 0f, 0.2f);
                        y = y + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        z = z + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        break;
                    case 2://positivivez
                        x = x + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        y = y + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        z = z + 0.6f + MathHelper.nextFloat(random, 0f, 0.2f);
                        break;
                    case 3://negativez
                        x = x + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        y = y + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        z = z - 0.6f - MathHelper.nextFloat(random, 0f, 0.2f);
                        break;
                    case 4://positivey
                        x = x + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        y = y + 0.6f + MathHelper.nextFloat(random, 0f, 0.2f);
                        z = z + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        break;
                    case 5://negativey
                        x = x + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        y = y - 0.6f - MathHelper.nextFloat(random, 0f, 0.2f);
                        z = z + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        break;
                }
                //System.out.println(" " + x + " " + y + " "+z);
                List<ServerPlayerEntity> playerEntityList = world.getPlayers(serverPlayerEntity ->
                        Vec3d.ofCenter(pos).isInRange(serverPlayerEntity.getPos(), 128.0));
                for (ServerPlayerEntity serverPlayerEntity : playerEntityList) {
                    world.spawnParticles(serverPlayerEntity, (ParticleEffect) ModParticleTypes.ENERGY_PARTICLE, true, x, y, z, 4, 0.1, 0, 0.1, 0);
                }

                //world.addImportantParticle((ParticleEffect) ModParticleTypes.ENERGY_PARTICLE, false, x, y, z, 0, 0, 0);
            }

        }

    }

    public static BlockPos getFrameMinPos(Direction pedestalFacing, BlockPos pos, World world ) {
        switch (pedestalFacing) {
            case EAST -> {
                if (world.getBlockState(pos.north(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.north(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.north(15).up(7).west(5);
                if (world.getBlockState(pos.south(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.south(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.north(5).up(7).west(5);
            }
            case NORTH -> {
                if (world.getBlockState(pos.east(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.east(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.west(5).up(7).south(5);
                if (world.getBlockState(pos.west(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.west(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.west(15).up(7).south(5);
            }
            case WEST -> {
                if (world.getBlockState(pos.north(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.north(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.north(14).up(7).east(5);
                if (world.getBlockState(pos.south(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.south(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.north(4).up(7).east(5);
            }
            case SOUTH -> {
                if (world.getBlockState(pos.east(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.east(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.west(4).up(7).north(5);
                if (world.getBlockState(pos.west(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.west(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.west(14).up(7).north(5);
            }
        }
        return null;
    }

    public static void addPortalStartAttemptParticle(ServerWorld world, BlockPos pos, Random random, int amount){
        //System.out.println("particle");
        int dx = random.nextInt(10)-5;
        int dy = random.nextInt(10)-5;
        int dz = random.nextInt(10)-5;

        double x = pos.getX() + 0.5f;
        double y = pos.getY() + 0.5f;
        double z = pos.getZ() + 0.5f;

        Vec3d relativeVector = new Vec3d(dx, dy, dz);
        Vec3d normalized3dVector = relativeVector.normalize();

        for (int i = 0; i < amount; i++) {
            x = x + normalized3dVector.getX()/10 + random.nextFloat()/10;
            y = y + normalized3dVector.getY()/10 + random.nextFloat()/10;
            z = z + normalized3dVector.getZ()/10 + random.nextFloat()/10;

            //System.out.println(" " + x + " " + y + " "+z);
            List<ServerPlayerEntity> playerEntityList = world.getPlayers(serverPlayerEntity ->
                    Vec3d.ofCenter(pos).isInRange(serverPlayerEntity.getPos(), 128.0));
            for (ServerPlayerEntity serverPlayerEntity : playerEntityList) {
                world.spawnParticles(serverPlayerEntity, (ParticleEffect) ModParticleTypes.SCULK_DEPTHS_PORTAL_ANIMATION_PARTICLE, true, x, y, z, 4, 0.0, 0.0, 0.0, 0);


            }

        }

    }

}
