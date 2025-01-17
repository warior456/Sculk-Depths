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

import java.util.List;

import static net.ugi.sculk_depths.block.custom.SculkDepthsPortalBlock.AXIS;

public class Portal {


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

    public static ChunkPos[] addElement(ChunkPos[] arr, ChunkPos e) {
        int n = arr.length;
        ChunkPos[] newarr = new ChunkPos[n + 1];

        for (int i = 0; i < n; i++){
            newarr[i] = arr[i];
        }

        newarr[n] = e;
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

    public static BlockPos getFrameAnchorPos(Direction pedestalFacing, BlockPos pos, World world ) {
        switch (pedestalFacing) {
            case EAST -> {
                if (world.getBlockState(pos.north(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.north(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.north(5).up(13).west(5);
                if (world.getBlockState(pos.south(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.south(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.south(5).up(13).west(5);
            }
            case NORTH -> {
                if (world.getBlockState(pos.east(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.east(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.east(5).up(13).south(5);
                if (world.getBlockState(pos.west(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.west(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.west(5).up(13).south(5);
            }
            case WEST -> {
                if (world.getBlockState(pos.north(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.north(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.north(5).up(13).east(5);
                if (world.getBlockState(pos.south(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.south(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.south(5).up(13).east(5);
            }
            case SOUTH -> {
                if (world.getBlockState(pos.east(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.east(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.east(5).up(13).north(5);
                if (world.getBlockState(pos.west(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.west(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.west(5).up(13).north(5);
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

    public static BlockPos[] genFrameStep(World world, BlockPos[] blockposses, Random random){
        BlockPos[] newPosArr = {new BlockPos(0,-4096,0)};
        for (BlockPos pos: blockposses) {
            BlockPos[] newPos = new BlockPos[0];
            if (world.getBlockState(pos).getBlock() != ModBlocks.ACTIVATED_AMALGAMITE) {
                world.setBlockState(pos,ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());
                Portal.addBlockPowerUpParticle((ServerWorld) world, pos, random, 10);
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
        BlockPos[] newPosArr = {new BlockPos(0,-4096,0)};
        for (BlockPos pos: blockposses) {
            BlockPos[] newPos = new BlockPos[0];
            if (world.getBlockState(pos).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
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

    public static BlockPos[] genPortalStep(World world, BlockPos[] blockposses, Direction facing, Random random){
        BlockState state = ModBlocks.SCULK_DEPTHS_PORTAL.getDefaultState();
        BlockPos[] newPosArr = {new BlockPos(0,-4096,0)};
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
                List<ServerPlayerEntity> playerEntityList = world.getPlayers(serverPlayerEntity -> serverPlayerEntity.isInRange(serverPlayerEntity, 100, 50));//todo test range
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
            List<ServerPlayerEntity> playerEntityList = world.getPlayers(serverPlayerEntity -> serverPlayerEntity.isInRange(serverPlayerEntity, 100, 50));//todo test range
            for (ServerPlayerEntity serverPlayerEntity : playerEntityList) {
                world.spawnParticles(serverPlayerEntity, (ParticleEffect) ModParticleTypes.SCULK_DEPTHS_PORTAL_ANIMATION_PARTICLE, true, x, y, z, 4, 0.0, 0.0, 0.0, 0);


            }

        }

    }

}
