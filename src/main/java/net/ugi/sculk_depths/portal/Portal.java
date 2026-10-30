package net.ugi.sculk_depths.portal;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
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

    private static boolean hasPoweredPedestal(Level level, BlockPos pos, Direction direction) {
        BlockState state = level.getBlockState(pos.relative(direction, 10));
        return state.is(ModBlocks.SCULK_PEDESTAL) && state.getValue(ModProperties.HAS_ENERGY_ESSENCE);
    }

    public static List<BlockPos> getFramePos(Direction pedestalFacing, BlockPos pos, Level level ) {
        Direction right = pedestalFacing.getClockWise();
        Direction left = pedestalFacing.getCounterClockWise();
        if (hasPoweredPedestal(level, pos, right)) {
            return getFramePositionsForSide(pos, pedestalFacing, right);
        }
        if (hasPoweredPedestal(level, pos, left)) {
            return getFramePositionsForSide(pos, pedestalFacing, left);
        }
        return null;
    }

    private static List<BlockPos> getFramePositionsForSide(BlockPos pos, Direction facing, Direction side) {
        Direction depth = facing.getOpposite();
        boolean isClockwise = side == facing.getClockWise();
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
                pos.relative(depth, 5).above(6).relative(side, a),
                pos.relative(depth, 5).above(6).relative(side, b)
        );
    }

    public static BlockPos getFrameAnchorPos(Direction pedestalFacing, BlockPos pos, Level level ) {
        Direction right = pedestalFacing.getClockWise();
        Direction left = pedestalFacing.getCounterClockWise();
        if (hasPoweredPedestal(level, pos, right)) {
            return getAnchorPosForSide(pos, pedestalFacing, right);
        }
        if (hasPoweredPedestal(level, pos, left)) {
            return getAnchorPosForSide(pos, pedestalFacing, left);
        }
        return null;
    }

    private static BlockPos getAnchorPosForSide(BlockPos pos, Direction facing, Direction side) {
        return pos.relative(side, 5).above(13).relative(facing.getOpposite(), 5);
    }

    public static List<BlockPos> getNextpos(BlockPos pos, Level level, Block block){
        List<BlockPos> list = new ArrayList<>();
        if (level.getBlockState(pos.north()).is(block)){
            list.add(pos.north());
        }
        if (level.getBlockState(pos.south()).is(block)){
            list.add(pos.south());
        }
        if (level.getBlockState(pos.east()).is(block)){
            list.add(pos.east());
        }
        if (level.getBlockState(pos.west()).is(block)){
            list.add(pos.west());
        }
        if (level.getBlockState(pos.above()).is(block)){
            list.add(pos.above());
        }
        if (level.getBlockState(pos.below()).is(block)){
            list.add(pos.below());
        }
        return list;
    }

    public static List<BlockPos> getNextpos(BlockPos pos, Level level, TagKey<Block> of, Direction facing){
        List<BlockPos> list = new ArrayList<>();
        if (facing == Direction.EAST || facing == Direction.WEST){
            if (level.getBlockState(pos.north()).is(of)){
                list.add(pos.north());
            }
            if (level.getBlockState(pos.south()).is(of)){
                list.add(pos.south());
            }
        }
        if (facing == Direction.NORTH || facing == Direction.SOUTH){
            if (level.getBlockState(pos.east()).is(of)){
                list.add(pos.east());
            }
            if (level.getBlockState(pos.west()).is(of)){
                list.add(pos.west());
            }
        }
        if (level.getBlockState(pos.above()).is(of)){
            list.add(pos.above());
        }
        if (level.getBlockState(pos.below()).is(of)){
            list.add(pos.below());
        }
        return list;
    }

    private static Boolean checkFullFrame(BlockPos pos, Level level){
        int countActivatedAmalgamite = 0;

        if (pos != null) {

            if (level.getBlockState(pos.north()).is(ModBlocks.ACTIVATED_AMALGAMITE)) {
                countActivatedAmalgamite++;
            }
            if (level.getBlockState(pos.south()).is(ModBlocks.ACTIVATED_AMALGAMITE)) {
                countActivatedAmalgamite++;
            }
            if (level.getBlockState(pos.east()).is(ModBlocks.ACTIVATED_AMALGAMITE)) {
                countActivatedAmalgamite++;
            }
            if (level.getBlockState(pos.west()).is(ModBlocks.ACTIVATED_AMALGAMITE)) {
                countActivatedAmalgamite++;
            }
            if (level.getBlockState(pos.above()).is(ModBlocks.ACTIVATED_AMALGAMITE)) {
                countActivatedAmalgamite++;
            }
            if (level.getBlockState(pos.below()).is(ModBlocks.ACTIVATED_AMALGAMITE)) {
                countActivatedAmalgamite++;
            }

            if (countActivatedAmalgamite >= 2)
                return true;
            return false;
        }
        return false;
    }

    public static List<BlockPos> genFrameStep(Level level, List<BlockPos> blockposses, RandomSource random){
        List<BlockPos> newPosArr = new ArrayList<>();
        newPosArr.add(new BlockPos(0,-4096,0));
        for (BlockPos pos: blockposses) {
            List<BlockPos> newPos = new ArrayList<>();
            if (!level.getBlockState(pos).is(ModBlocks.ACTIVATED_AMALGAMITE)) {
                level.setBlockAndUpdate(pos,ModBlocks.ACTIVATED_AMALGAMITE.defaultBlockState());
                Portal.addBlockPowerUpParticle((ServerLevel) level, pos, random, 10);
                    newPos = getNextpos(pos, level, Blocks.REINFORCED_DEEPSLATE);
                if (newPos.isEmpty()){
                    if (checkFullFrame(pos,level))
                        newPos.add(new BlockPos(0,-4096,1));
                    else
                        newPos.add(new BlockPos(0,-4096,0));
                }
                newPosArr.addAll(newPos);
            }
        }
        return newPosArr;
    }

    public static List<BlockPos> cancelFrameStep(Level level, List<BlockPos> blockposses){
        List<BlockPos> newPosArr = new ArrayList<>();
        newPosArr.add(new BlockPos(0,-4096,0));
        for (BlockPos pos: blockposses) {
            List<BlockPos> newPos = new ArrayList<>();
            if (level.getBlockState(pos).is(ModBlocks.ACTIVATED_AMALGAMITE)) {
                level.setBlockAndUpdate(pos,Blocks.REINFORCED_DEEPSLATE.defaultBlockState());
                newPos = getNextpos(pos, level, ModBlocks.ACTIVATED_AMALGAMITE);
                if (newPos.isEmpty()){
                    newPos.add(new BlockPos(0,-4096,0));
                }
                newPosArr.addAll(newPos);
            }
        }
        return newPosArr;
    }

    public static List<BlockPos> genPortalStep(Level level, List<BlockPos> blockposses, Direction facing, RandomSource random){
        BlockState state = ModBlocks.SCULK_DEPTHS_PORTAL.defaultBlockState();
        List<BlockPos> newPosArr = new ArrayList<>();
        newPosArr.add(new BlockPos(0,-4096,0));
        for (BlockPos pos: blockposses) {
            List<BlockPos> newPos = new ArrayList<>();
            if (!level.getBlockState(pos).is(ModBlocks.SCULK_DEPTHS_PORTAL)) {
                if (facing == Direction.NORTH || facing == Direction.SOUTH){
                    level.setBlockAndUpdate(pos,ModBlocks.SCULK_DEPTHS_PORTAL.withPropertiesOf(state.setValue(AXIS, Direction.Axis.X)));
                    newPos = getNextpos(pos, level, ModTags.Blocks.PORTAL_AIR,facing);
                }
                if (facing == Direction.EAST || facing == Direction.WEST){
                    level.setBlockAndUpdate(pos,ModBlocks.SCULK_DEPTHS_PORTAL.withPropertiesOf(state.setValue(AXIS, Direction.Axis.Z)));
                    newPos = getNextpos(pos, level, ModTags.Blocks.PORTAL_AIR, facing);
                }
                if (newPos.isEmpty()){
                    newPos.add(new BlockPos(0,-4096,0));
                }
                newPosArr.addAll(newPos);
            }
        }
        return newPosArr;
    }

    public static void addBlockPowerUpParticle(ServerLevel level, BlockPos pos, RandomSource random, int amount){
        //System.out.println("particle");
        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < 6; j++) {
                float x = pos.getX() + 0.5f;
                float y = pos.getY() + 0.5f;
                float z = pos.getZ() + 0.5f;
                switch (j){
                    case 0://positivex
                        x = x + 0.6f + Mth.nextFloat(random, 0f, 0.2f);
                        y = y + Mth.nextFloat(random, -0.6f, 0.6f);
                        z = z + Mth.nextFloat(random, -0.6f, 0.6f);
                        break;
                    case 1://negativex
                        x = x - 0.6f - Mth.nextFloat(random, 0f, 0.2f);
                        y = y + Mth.nextFloat(random, -0.6f, 0.6f);
                        z = z + Mth.nextFloat(random, -0.6f, 0.6f);
                        break;
                    case 2://positivivez
                        x = x + Mth.nextFloat(random, -0.6f, 0.6f);
                        y = y + Mth.nextFloat(random, -0.6f, 0.6f);
                        z = z + 0.6f + Mth.nextFloat(random, 0f, 0.2f);
                        break;
                    case 3://negativez
                        x = x + Mth.nextFloat(random, -0.6f, 0.6f);
                        y = y + Mth.nextFloat(random, -0.6f, 0.6f);
                        z = z - 0.6f - Mth.nextFloat(random, 0f, 0.2f);
                        break;
                    case 4://positivey
                        x = x + Mth.nextFloat(random, -0.6f, 0.6f);
                        y = y + 0.6f + Mth.nextFloat(random, 0f, 0.2f);
                        z = z + Mth.nextFloat(random, -0.6f, 0.6f);
                        break;
                    case 5://negativey
                        x = x + Mth.nextFloat(random, -0.6f, 0.6f);
                        y = y - 0.6f - Mth.nextFloat(random, 0f, 0.2f);
                        z = z + Mth.nextFloat(random, -0.6f, 0.6f);
                        break;
                }
                //System.out.println(" " + x + " " + y + " "+z);
                List<ServerPlayer> playerEntityList = level.players().stream().filter(serverPlayerEntity ->
                        Vec3.atCenterOf(pos).closerThan(serverPlayerEntity.position(), 128.0)).toList();
                for (ServerPlayer serverPlayerEntity : playerEntityList) {
                    level.sendParticles(serverPlayerEntity, ModParticleTypes.ENERGY_PARTICLE, true, x, y, z, 4, 0.1, 0, 0.1, 0);
                }

                //world.addImportantParticle((ParticleEffect) ModParticleTypes.ENERGY_PARTICLE, false, x, y, z, 0, 0, 0);
            }

        }

    }

    public static BlockPos getFrameMinPos(Direction pedestalFacing, BlockPos pos, Level level ) {
        switch (pedestalFacing) {
            case EAST -> {
                if (level.getBlockState(pos.north(10)).is(ModBlocks.SCULK_PEDESTAL))
                    if (level.getBlockState(pos.north(10)).getValue(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.north(15).above(7).west(5);
                if (level.getBlockState(pos.south(10)).is(ModBlocks.SCULK_PEDESTAL))
                    if (level.getBlockState(pos.south(10)).getValue(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.north(5).above(7).west(5);
            }
            case NORTH -> {
                if (level.getBlockState(pos.east(10)).is(ModBlocks.SCULK_PEDESTAL))
                    if (level.getBlockState(pos.east(10)).getValue(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.west(5).above(7).south(5);
                if (level.getBlockState(pos.west(10)).is(ModBlocks.SCULK_PEDESTAL))
                    if (level.getBlockState(pos.west(10)).getValue(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.west(15).above(7).south(5);
            }
            case WEST -> {
                if (level.getBlockState(pos.north(10)).is(ModBlocks.SCULK_PEDESTAL))
                    if (level.getBlockState(pos.north(10)).getValue(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.north(14).above(7).east(5);
                if (level.getBlockState(pos.south(10)).is(ModBlocks.SCULK_PEDESTAL))
                    if (level.getBlockState(pos.south(10)).getValue(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.north(4).above(7).east(5);
            }
            case SOUTH -> {
                if (level.getBlockState(pos.east(10)).is(ModBlocks.SCULK_PEDESTAL))
                    if (level.getBlockState(pos.east(10)).getValue(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.west(4).above(7).north(5);
                if (level.getBlockState(pos.west(10)).is(ModBlocks.SCULK_PEDESTAL))
                    if (level.getBlockState(pos.west(10)).getValue(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.west(14).above(7).north(5);
            }
        }
        return null;
    }

    public static void addPortalStartAttemptParticle(ServerLevel level, BlockPos pos, RandomSource random, int amount){
        //System.out.println("particle");
        int dx = random.nextInt(10)-5;
        int dy = random.nextInt(10)-5;
        int deltaZ = random.nextInt(10)-5;

        double x = pos.getX() + 0.5f;
        double y = pos.getY() + 0.5f;
        double z = pos.getZ() + 0.5f;

        Vec3 relativeVector = new Vec3(dx, dy, deltaZ);
        Vec3 normalized3dVector = relativeVector.normalize();

        for (int i = 0; i < amount; i++) {
            x = x + normalized3dVector.x/10 + (double) random.nextFloat()/10;
            y = y + normalized3dVector.y/10 + (double) random.nextFloat()/10;
            z = z + normalized3dVector.z/10 + (double) random.nextFloat()/10;

            //System.out.println(" " + x + " " + y + " "+z);
            List<ServerPlayer> playerEntityList = level.players().stream().filter(serverPlayerEntity ->
                    Vec3.atCenterOf(pos).closerThan(serverPlayerEntity.position(), 128.0)).toList();
            for (ServerPlayer serverPlayerEntity : playerEntityList) {
                level.sendParticles(serverPlayerEntity, ModParticleTypes.SCULK_DEPTHS_PORTAL_ANIMATION_PARTICLE, true, x, y, z, 4, 0.0, 0.0, 0.0, 0);


            }

        }

    }

}
