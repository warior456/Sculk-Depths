package net.ugi.sculk_depths.block.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SculkChargeParticleEffect;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.dimension.NetherPortal;

import net.ugi.sculk_depths.particle.ModParticleTypes;
import net.ugi.sculk_depths.world.dimension.ModDimensions;


public class SculkDepthsPortalBlock extends Block implements Portal{
    public static final EnumProperty<Direction.Axis> AXIS = Properties.HORIZONTAL_AXIS;
    protected static final VoxelShape X_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public SculkDepthsPortalBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AXIS, Direction.Axis.X));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(AXIS)) {
            case Z -> Z_SHAPE;
            default -> X_SHAPE;
        };
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

    }

    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        switch (rotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch ((Direction.Axis)state.get(AXIS)) {
                    case Z:
                        return (BlockState)state.with(AXIS, Direction.Axis.X);
                    case X:
                        return (BlockState)state.with(AXIS, Direction.Axis.Z);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }


    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        Direction.Axis axis = direction.getAxis();
        Direction.Axis axis2 = (Direction.Axis)state.get(AXIS);
        boolean bl = axis2 != axis && axis.isHorizontal();
        return !bl && !neighborState.isOf(this) && !(new NetherPortal(world, pos, axis2)).wasAlreadyValid() ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0) {
            world.playSound(
                    (double)pos.getX() + 0.5,
                    (double)pos.getY() + 0.5,
                    (double)pos.getZ() + 0.5,
                    SoundEvents.BLOCK_PORTAL_AMBIENT,
                    SoundCategory.BLOCKS,
                    0.5F,
                    random.nextFloat() * 0.4F + 0.8F,
                    false
            );
        }

        for (int i = 0; i < 4; i++) {
            double d = (double)pos.getX() + random.nextDouble();
            double e = (double)pos.getY() + random.nextDouble();
            double f = (double)pos.getZ() + random.nextDouble();
            double g = ((double)random.nextFloat() - 0.5) * 0.5;
            double h = ((double)random.nextFloat() - 0.5) * 0.5;
            double j = ((double)random.nextFloat() - 0.5) * 0.5;
            int k = random.nextInt(2) * 2 - 1;
            if (!world.getBlockState(pos.west()).isOf(this) && !world.getBlockState(pos.east()).isOf(this)) {
                d = (double)pos.getX() + 0.5 + 0.25 * (double)k;
                g = (double)(random.nextFloat() * 2.0F * (float)k);
            } else {
                f = (double)pos.getZ() + 0.5 + 0.25 * (double)k;
                j = (double)(random.nextFloat() * 2.0F * (float)k);
            }

            world.addParticle((ParticleEffect) ModParticleTypes.PENEBRIUM_SPORES, d, e, f, g, h, j);
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        //if(world.isClient())return;
        if(entity.canUsePortals(true)){
            entity.tryUsePortal(this, pos);
        }
    }


    @Override
    public int getPortalDelay(ServerWorld world, Entity entity) {
        if (entity instanceof PlayerEntity playerEntity) {
            return Math.max(1, world.getGameRules().getInt(playerEntity.getAbilities().invulnerable ? GameRules.PLAYERS_NETHER_PORTAL_CREATIVE_DELAY : GameRules.PLAYERS_NETHER_PORTAL_DEFAULT_DELAY));
        } else {
            return 0;
        }
    }


    @Override
    public TeleportTarget createTeleportTarget(ServerWorld world, Entity entity, BlockPos blockPos) {
        RegistryKey<World> registryKey = world.getRegistryKey() == ModDimensions.SCULK_DEPTHS_LEVEL_KEY ? World.OVERWORLD : ModDimensions.SCULK_DEPTHS_LEVEL_KEY;
        ServerWorld serverWorld = world.getServer().getWorld(registryKey);
        if (serverWorld == null) {
            return null;
        } else {
            Vec3d vec3d = Vec3d.of(blockPos);
            vec3d = vec3d.add(0.5f, 0, 0.5f);
            if(entity.isPlayer()){
                vec3d = vec3d.subtract(0, 1,0);
            }
            return new TeleportTarget(
                    serverWorld,
                    vec3d,
                    entity.getVelocity(),
                    entity.getYaw(),
                    entity.getPitch(),
                    TeleportTarget.SEND_TRAVEL_THROUGH_PORTAL_PACKET.then(TeleportTarget.ADD_PORTAL_CHUNK_TICKET)
            );
        }
    }


    @Override
    public Effect getPortalEffect() {
        return Effect.CONFUSION;
    }

    protected boolean canBucketPlace(BlockState state, Fluid fluid) {
        return false;
    }
}
