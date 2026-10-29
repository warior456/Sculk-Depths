package net.ugi.sculk_depths.block.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.level.portal.PortalShape;

import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.particle.ModParticleTypes;
import net.ugi.sculk_depths.world.dimension.ModDimensions;


public class SculkDepthsPortalBlock extends Block implements Portal{
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public SculkDepthsPortalBlock(Settings settings) {
        super(settings);
        this.registerDefaultState(this.stateManager.getDefaultState().with(AXIS, Direction.Axis.X_ROT));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(AXIS)) {
            case Z -> Z_AXIS_AABB;
            default -> X_AXIS_AABB;
        };
    }

    @Override
    public void scheduledTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {

    }

    protected BlockState hasRot(BlockState state, Rotation rotation) {
        switch (rotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch ((Direction.Axis)state.get(AXIS)) {
                    case Z:
                        return (BlockState)state.with(AXIS, Direction.Axis.X_ROT);
                    case X_ROT:
                        return (BlockState)state.with(AXIS, Direction.Axis.Z);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }
    private boolean regeneratePortal(WorldAccess world, BlockPos pos, Direction.Axis facing){
        int connectedPortalBlocks = 0;
        if(world.getBlockState(pos.north()).getBlock() == ModBlocks.SCULK_DEPTHS_PORTAL){
            connectedPortalBlocks++;
        }
        if(world.getBlockState(pos.south()).getBlock() == ModBlocks.SCULK_DEPTHS_PORTAL){
            connectedPortalBlocks++;
        }
        if(world.getBlockState(pos.east()).getBlock() == ModBlocks.SCULK_DEPTHS_PORTAL){
            connectedPortalBlocks++;
        }
        if(world.getBlockState(pos.west()).getBlock() == ModBlocks.SCULK_DEPTHS_PORTAL){
            connectedPortalBlocks++;
        }
        if(world.getBlockState(pos.above()).getBlock() == ModBlocks.SCULK_DEPTHS_PORTAL){
            connectedPortalBlocks++;
        }
        if(world.getBlockState(pos.below()).getBlock() == ModBlocks.SCULK_DEPTHS_PORTAL){
            connectedPortalBlocks++;
        }

        if (connectedPortalBlocks > 1){
            BlockState state = ModBlocks.SCULK_DEPTHS_PORTAL.getDefaultState();
            world.setBlockState(pos,ModBlocks.SCULK_DEPTHS_PORTAL.withPropertiesOf(state.with(AXIS, facing)),0);
            return true;
        }
        return false;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        Direction.Axis orientation = direction.getAxis();
        Direction.Axis axis2 = (Direction.Axis)state.get(AXIS);
        boolean bl = axis2 != orientation && orientation.isHorizontal();
        if (!bl && !state.isOf(this) && !(new PortalShape(world, pos, axis2)).isComplete()){
            if(world.getBlockState(pos.north()).getBlock() == Blocks.AIR){
                if (regeneratePortal(world,pos.north(),state.get(AXIS)))
                    return super.updateShape(state, direction, state, world, pos, neighborPos);
            }
            if(world.getBlockState(pos.south()).getBlock() == Blocks.AIR){
                if (regeneratePortal(world,pos.south(),state.get(AXIS)))
                    return super.updateShape(state, direction, state, world, pos, neighborPos);
            }
            if(world.getBlockState(pos.east()).getBlock() == Blocks.AIR){
                if (regeneratePortal(world,pos.east(),state.get(AXIS)))
                    return super.updateShape(state, direction, state, world, pos, neighborPos);
            }
            if(world.getBlockState(pos.west()).getBlock() == Blocks.AIR){
                if (regeneratePortal(world,pos.west(),state.get(AXIS)))
                    return super.updateShape(state, direction, state, world, pos, neighborPos);
            }
            if(world.getBlockState(pos.above()).getBlock() == Blocks.AIR){
                if (regeneratePortal(world,pos.above(),state.get(AXIS)))
                    return super.updateShape(state, direction, state, world, pos, neighborPos);
            }
            if(world.getBlockState(pos.below()).getBlock() == Blocks.AIR){
                if (regeneratePortal(world,pos.below(),state.get(AXIS)))
                    return super.updateShape(state, direction, state, world, pos, neighborPos);
            }
            return super.updateShape(state, direction, state, world, pos, neighborPos);
        }
        return super.updateShape(state, direction, state, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, RandomSource random) {
        if (random.getInt(100) == 0) {
            world.playSound(
                    (double)pos.getX() + 0.5,
                    (double)pos.getY() + 0.5,
                    (double)pos.getZ() + 0.5,
                    SoundEvents.PORTAL_AMBIENT,
                    SoundSource.BLOCKS,
                    0.5F,
                    random.getFloat() * 0.4F + 0.8F,
                    false
            );
        }

        for (int i = 0; i < 4; i++) {
            double d = (double)pos.getX() + random.nextDouble();
            double e = (double)pos.getY() + random.nextDouble();
            double f = (double)pos.getZ() + random.nextDouble();

            int k = random.getInt(2) * 2 - 1;
            if (!world.getBlockState(pos.west()).isOf(this) && !world.getBlockState(pos.east()).isOf(this)) {
                d = (double)pos.getX() + 0.5 + 0.25 * (double)k;
            } else {
                f = (double)pos.getZ() + 0.5 + 0.25 * (double)k;
            }

            world.addAlwaysVisibleParticle((ParticleOptions) ModParticleTypes.SCULK_DEPTHS_PORTAL_PARTICLE, false, d, e, f, 0, -0.01f, 0);
        }
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        //if(world.isClient())return;
        if(entity.canUsePortal(true)){
            entity.setAsInsidePortal(this, pos);
        }
    }


    @Override
    public int getPortalTransitionTime(ServerLevel world, Entity entity) {
        if (entity instanceof Player playerEntity) {
            return Math.max(1, world.gameRules().getInt(playerEntity.getAbilities().invulnerable ? GameRules.RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY : GameRules.RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY));
        } else {
            return 0;
        }
    }


    @Override
    public TeleportTarget getPortalDestination(ServerLevel world, Entity entity, BlockPos blockPos) {
        ResourceKey<World> key = world.getRegistryKey() == ModDimensions.SCULK_DEPTHS_LEVEL_KEY ? World.OVERWORLD : ModDimensions.SCULK_DEPTHS_LEVEL_KEY;
        ServerLevel serverWorld = world.getServer().getWorld(key);
        if (serverWorld == null) {
            return null;
        } else {
            Vec3 vec3d = Vec3.of(blockPos);
            vec3d = vec3d.add(0.5f, 0, 0.5f);
            if(entity.isAlwaysTicking()){
                vec3d = vec3d.subtract(0, 1,0);
            }
            if(!serverWorld.getBlockState(blockPos).isOf(ModBlocks.SCULK_DEPTHS_PORTAL)){//backup tp

                serverWorld.setBlockState(blockPos, ModBlocks.SCULK_DEPTHS_PORTAL.getDefaultState());
                serverWorld.setBlockState(blockPos.below(), ModBlocks.SCULK_DEPTHS_PORTAL.getDefaultState());
                serverWorld.setBlockState(blockPos.north(), Blocks.AIR.getDefaultState());
                serverWorld.setBlockState(blockPos.east(), Blocks.AIR.getDefaultState());
                serverWorld.setBlockState(blockPos.south(), Blocks.AIR.getDefaultState());
                serverWorld.setBlockState(blockPos.west(), Blocks.AIR.getDefaultState());
                serverWorld.setBlockState(blockPos.below().north(), Blocks.AIR.getDefaultState());
                serverWorld.setBlockState(blockPos.below().east(), Blocks.AIR.getDefaultState());
                serverWorld.setBlockState(blockPos.below().south(), Blocks.AIR.getDefaultState());
                serverWorld.setBlockState(blockPos.below().west(), Blocks.AIR.getDefaultState());
                serverWorld.setBlockState(blockPos.below(2), ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());
                serverWorld.setBlockState(blockPos.below(2).north(), ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());
                serverWorld.setBlockState(blockPos.below(2).east(), ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());
                serverWorld.setBlockState(blockPos.below(2).south(), ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());
                serverWorld.setBlockState(blockPos.below(2).west(), ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());

                SculkDepths.LOGGER.warn("A player went through an invalid portal at {} if you're a server admin or have access to permissions it is recommended to build a proper 2 way portal", blockPos);
            }
            return new TeleportTarget(
                    serverWorld,
                    vec3d,
                    entity.getVelocity(),
                    entity.getYaw(),
                    entity.getPitch(),
                    TeleportTarget.PLAY_PORTAL_SOUND.step(TeleportTarget.PLACE_PORTAL_TICKET)
            );
        }
    }


    @Override
    public Effect getLocalTransition() {
        return Effect.CONFUSION;
    }

    protected boolean canBeReplaced(BlockState state, Fluid fluid) {
        return false;
    }
}
