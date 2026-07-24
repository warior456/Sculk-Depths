package net.ugi.sculk_depths.portal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.SculkDepthsPortalBlock;
import net.ugi.sculk_depths.particle.ModParticleTypes;
import net.ugi.sculk_depths.state.property.ModProperties;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.ArrayList;
import java.util.List;

public class Portal {

    public static BlockPos[] addElement(BlockPos[] arr, BlockPos e) {
        int n = arr.length;
        BlockPos[] newarr = new BlockPos[n + 1];

        System.arraycopy(arr, 0, newarr, 0, n);

        newarr[n] = e;
        return newarr;
    }


    public static BlockPos[] getFramePos(Direction pedestalFacing, BlockPos pos, World world) {
        // TODO refactor
        switch (pedestalFacing) {
            case EAST -> {
                if (world.getBlockState(pos.north(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.north(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[] {pos.north(6).up(6).west(5), pos.north(5).up(6).west(5)};
                if (world.getBlockState(pos.south(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.south(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[] {pos.south(4).up(6).west(5), pos.south(5).up(6).west(5)};
            }
            case NORTH -> {
                if (world.getBlockState(pos.east(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.east(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[] {pos.east(5).up(6).south(5), pos.east(4).up(6).south(5)};
                if (world.getBlockState(pos.west(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.west(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[] {pos.west(5).up(6).south(5), pos.west(6).up(6).south(5)};
            }
            case WEST -> {
                if (world.getBlockState(pos.north(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.north(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[] {pos.north(5).up(6).east(5), pos.north(4).up(6).east(5)};
                if (world.getBlockState(pos.south(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.south(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[] {pos.south(5).up(6).east(5), pos.south(6).up(6).east(5)};
            }
            case SOUTH -> {
                if (world.getBlockState(pos.east(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.east(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[] {pos.east(6).up(6).north(5), pos.east(5).up(6).north(5)};
                if (world.getBlockState(pos.west(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.west(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return new BlockPos[] {pos.west(4).up(6).north(5), pos.west(5).up(6).north(5)};
            }
        }
        return null;
    }

    public static BlockPos getFrameAnchorPos(Direction pedestalFacing, BlockPos pos, World world) {
        // TODO refactor
        switch (pedestalFacing) {
            case EAST -> {
                // z negative
                if (world.getBlockState(pos.north(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.north(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.north(5).up(13).offset(pedestalFacing.getOpposite(), 5);

                // z positive
                if (world.getBlockState(pos.south(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.south(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.south(5).up(13).offset(pedestalFacing.getOpposite(), 5);
            }
            case NORTH -> {
                // x positive
                if (world.getBlockState(pos.east(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.east(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.east(5).up(13).offset(pedestalFacing.getOpposite(), 5);

                // x negative
                if (world.getBlockState(pos.west(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.west(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.west(5).up(13).offset(pedestalFacing.getOpposite(), 5);
            }
            case WEST -> {
                // z negative
                if (world.getBlockState(pos.north(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.north(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.north(5).up(13).offset(pedestalFacing.getOpposite(), 5);

                // z positive
                if (world.getBlockState(pos.south(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.south(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.south(5).up(13).offset(pedestalFacing.getOpposite(), 5);
            }
            case SOUTH -> {
                // x positive
                if (world.getBlockState(pos.east(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.east(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.east(5).up(13).offset(pedestalFacing.getOpposite(), 5);

                // x negative
                if (world.getBlockState(pos.west(10)).getBlock() == ModBlocks.SCULK_PEDESTAL)
                    if (world.getBlockState(pos.west(10)).get(ModProperties.HAS_ENERGY_ESSENCE))
                        return pos.west(5).up(13).offset(pedestalFacing.getOpposite(), 5);
            }
        }
        return null;
    }

    public static List<BlockPos> getNextPos(BlockPos pos, World world, Block block) {
        List<BlockPos> positions = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            BlockPos offsettedPos = pos.offset(direction);
            if (world.getBlockState(offsettedPos).isOf(block)) {
                positions.add(offsettedPos);
            }
        }
        return positions;
    }

    public static List<BlockPos> getNextPos(BlockPos pos, World world, TagKey<Block> tag, Direction facing) {
        List<BlockPos> blockPositions = new ArrayList<>();

        for (Direction.AxisDirection axisDirection : Direction.AxisDirection.values()) {
            Direction direction = Direction.from(facing.getAxis(), axisDirection);
            if (world.getBlockState(pos.offset(direction)).isIn(tag)) {
                blockPositions.add(pos.offset(direction));
            }
        }
        return blockPositions;
    }

    private static Boolean checkFullFrame(BlockPos pos, World world) {
        if (pos == null) return false;

        int countActivatedAmalgamite = 0;

        for (Direction direction : Direction.values()) {
            if (world.getBlockState(pos.offset(direction)).isOf(ModBlocks.ACTIVATED_AMALGAMITE)) {
                countActivatedAmalgamite++;
            }
        }

        return countActivatedAmalgamite >= 2;
    }

    public static BlockPos[] genFrameStep(World world, BlockPos[] blockPositions, Random random) {
        List<BlockPos> newPositions = new ArrayList<>();
        newPositions.add(new BlockPos(0, -4096, 0));
        for (BlockPos pos : blockPositions) {
            if (world.getBlockState(pos).isOf(ModBlocks.ACTIVATED_AMALGAMITE)) continue;

            world.setBlockState(pos, ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());
            Portal.addBlockPowerUpParticle((ServerWorld) world, pos, random, 10);
            List<BlockPos> newPos = getNextPos(pos, world, Blocks.REINFORCED_DEEPSLATE);
            if (newPos.isEmpty()) {
                if (checkFullFrame(pos, world)) {
                    newPos.add(new BlockPos(0, -4096, 1));
                } else {
                    newPos.add(new BlockPos(0, -4096, 0));
                }
            }
            newPositions.addAll(newPos);
        }

        return newPositions.toArray(BlockPos[]::new);
    }

    public static BlockPos[] cancelFrameStep(World world, BlockPos[] blockPositions) {
        List<BlockPos> newPositions = new ArrayList<>();
        newPositions.add(new BlockPos(0, -4096, 0));
        for (BlockPos pos : blockPositions) {
            if (world.getBlockState(pos).getBlock() == ModBlocks.ACTIVATED_AMALGAMITE) {
                world.setBlockState(pos, Blocks.REINFORCED_DEEPSLATE.getDefaultState());
                List<BlockPos> newPos = getNextPos(pos, world, ModBlocks.ACTIVATED_AMALGAMITE);
                if (newPos.isEmpty()) {
                    newPos.add(new BlockPos(0, -4096, 0));
                }
                newPositions.addAll(newPos);
            }
        }
        return newPositions.toArray(BlockPos[]::new);
    }

    public static BlockPos[] genPortalStep(World world, BlockPos[] blockPositions, Direction facing, Random random) {
        BlockState state = ModBlocks.SCULK_DEPTHS_PORTAL.getDefaultState();
        List<BlockPos> newPositions = new ArrayList<>();
        newPositions.add(new BlockPos(0, -4096, 0));
        for (BlockPos pos : blockPositions) {
            if (world.getBlockState(pos).getBlock() != ModBlocks.SCULK_DEPTHS_PORTAL) {
                world.setBlockState(pos, ModBlocks.SCULK_DEPTHS_PORTAL.getStateWithProperties(state.with(SculkDepthsPortalBlock.AXIS, facing.getAxis())));
                List<BlockPos> newPos = getNextPos(pos, world, ModTags.Blocks.PORTAL_AIR, facing);

                if (newPos.isEmpty()) {
                    newPos.add(new BlockPos(0, -4096, 0));
                }
                newPositions.addAll(newPos);
            }
        }
        return newPositions.toArray(BlockPos[]::new);
    }

    public static void addBlockPowerUpParticle(ServerWorld world, BlockPos pos, Random random, int amount) {
        //System.out.println("particle");
        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < 6; j++) {
                float x = pos.getX() + 0.5f;
                float y = pos.getY() + 0.5f;
                float z = pos.getZ() + 0.5f;
                switch (j) {
                    case 0 -> { // positive x
                        x = x + 0.6f + MathHelper.nextFloat(random, 0f, 0.2f);
                        y = y + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        z = z + MathHelper.nextFloat(random, -0.6f, 0.6f);
                    }
                    case 1 -> { // negative x
                        x = x - 0.6f - MathHelper.nextFloat(random, 0f, 0.2f);
                        y = y + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        z = z + MathHelper.nextFloat(random, -0.6f, 0.6f);
                    }
                    case 2 -> { // positive z
                        x = x + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        y = y + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        z = z + 0.6f + MathHelper.nextFloat(random, 0f, 0.2f);
                    }
                    case 3 -> { // negative z
                        x = x + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        y = y + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        z = z - 0.6f - MathHelper.nextFloat(random, 0f, 0.2f);
                    }
                    case 4 -> { // positive y
                        x = x + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        y = y + 0.6f + MathHelper.nextFloat(random, 0f, 0.2f);
                        z = z + MathHelper.nextFloat(random, -0.6f, 0.6f);
                    }
                    case 5 -> { // negative y
                        x = x + MathHelper.nextFloat(random, -0.6f, 0.6f);
                        y = y - 0.6f - MathHelper.nextFloat(random, 0f, 0.2f);
                        z = z + MathHelper.nextFloat(random, -0.6f, 0.6f);
                    }
                }
                //System.out.println(" " + x + " " + y + " "+z);
                List<ServerPlayerEntity> playerEntityList = world.getPlayers(serverPlayerEntity -> serverPlayerEntity.isInRange(serverPlayerEntity, 100, 50));//todo test range
                for (ServerPlayerEntity serverPlayerEntity : playerEntityList) {
                    world.spawnParticles(serverPlayerEntity, ModParticleTypes.ENERGY_PARTICLE, true, x, y, z, 4, 0.1, 0, 0.1, 0);
                }

                //world.addImportantParticle((ParticleEffect) ModParticleTypes.ENERGY_PARTICLE, false, x, y, z, 0, 0, 0);
            }

        }

    }

    public static BlockPos getFrameMinPos(Direction pedestalFacing, BlockPos pos, World world) {
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

    public static void addPortalStartAttemptParticle(ServerWorld world, BlockPos pos, Random random, int amount) {
        //System.out.println("particle");
        int dx = random.nextInt(10) - 5;
        int dy = random.nextInt(10) - 5;
        int dz = random.nextInt(10) - 5;

        double x = pos.getX() + 0.5f;
        double y = pos.getY() + 0.5f;
        double z = pos.getZ() + 0.5f;

        Vec3d relativeVector = new Vec3d(dx, dy, dz);
        Vec3d normalized3dVector = relativeVector.normalize();

        for (int i = 0; i < amount; i++) {
            x = x + normalized3dVector.getX() / 10 + random.nextFloat() / 10;
            y = y + normalized3dVector.getY() / 10 + random.nextFloat() / 10;
            z = z + normalized3dVector.getZ() / 10 + random.nextFloat() / 10;

            //System.out.println(" " + x + " " + y + " "+z);
            List<ServerPlayerEntity> playerEntityList = world.getPlayers(serverPlayerEntity -> serverPlayerEntity.isInRange(serverPlayerEntity, 100, 50));//todo test range
            for (ServerPlayerEntity serverPlayerEntity : playerEntityList) {
                world.spawnParticles(serverPlayerEntity, (ParticleEffect) ModParticleTypes.SCULK_DEPTHS_PORTAL_ANIMATION_PARTICLE, true, x, y, z, 4, 0.0, 0.0, 0.0, 0);


            }

        }

    }

}
