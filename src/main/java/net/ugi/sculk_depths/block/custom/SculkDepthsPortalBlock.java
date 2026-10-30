package net.ugi.sculk_depths.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.particle.ModParticleTypes;
import net.ugi.sculk_depths.world.dimension.ModDimensions;


public class SculkDepthsPortalBlock extends Block implements Portal {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public SculkDepthsPortalBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AXIS)) {
            case Z -> Z_AXIS_AABB;
            default -> X_AXIS_AABB;
        };
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        switch (rotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch (state.getValue(AXIS)) {
                    case Z:
                        return state.setValue(AXIS, Direction.Axis.X);
                    case X:
                        return state.setValue(AXIS, Direction.Axis.Z);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    private boolean regeneratePortal(LevelAccessor level, BlockPos pos, Direction.Axis facing) {
        int connectedPortalBlocks = 0;
        if (level.getBlockState(pos.north()).is(ModBlocks.SCULK_DEPTHS_PORTAL)) {
            connectedPortalBlocks++;
        }
        if (level.getBlockState(pos.south()).is(ModBlocks.SCULK_DEPTHS_PORTAL)) {
            connectedPortalBlocks++;
        }
        if (level.getBlockState(pos.east()).is(ModBlocks.SCULK_DEPTHS_PORTAL)) {
            connectedPortalBlocks++;
        }
        if (level.getBlockState(pos.west()).is(ModBlocks.SCULK_DEPTHS_PORTAL)) {
            connectedPortalBlocks++;
        }
        if (level.getBlockState(pos.above()).is(ModBlocks.SCULK_DEPTHS_PORTAL)) {
            connectedPortalBlocks++;
        }
        if (level.getBlockState(pos.below()).is(ModBlocks.SCULK_DEPTHS_PORTAL)) {
            connectedPortalBlocks++;
        }

        if (connectedPortalBlocks > 1) {
            BlockState state = ModBlocks.SCULK_DEPTHS_PORTAL.defaultBlockState();
            level.setBlock(pos, ModBlocks.SCULK_DEPTHS_PORTAL.withPropertiesOf(state.setValue(AXIS, facing)), 0);
            return true;
        }
        return false;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        Direction.Axis orientation = direction.getAxis();
        Direction.Axis axis2 = state.getValue(AXIS);
        boolean bl = axis2 != orientation && orientation.isHorizontal();
        if (!bl && !neighborState.is(this) && !(new PortalShape(level, pos, axis2)).isComplete()) {
            if (level.getBlockState(pos.north()).is(Blocks.AIR)) {
                if (regeneratePortal(level, pos.north(), state.getValue(AXIS)))
                    return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
            }
            if (level.getBlockState(pos.south()).is(Blocks.AIR)) {
                if (regeneratePortal(level, pos.south(), state.getValue(AXIS)))
                    return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
            }
            if (level.getBlockState(pos.east()).is(Blocks.AIR)) {
                if (regeneratePortal(level, pos.east(), state.getValue(AXIS)))
                    return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
            }
            if (level.getBlockState(pos.west()).is(Blocks.AIR)) {
                if (regeneratePortal(level, pos.west(), state.getValue(AXIS)))
                    return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
            }
            if (level.getBlockState(pos.above()).is(Blocks.AIR)) {
                if (regeneratePortal(level, pos.above(), state.getValue(AXIS)))
                    return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
            }
            if (level.getBlockState(pos.below()).is(Blocks.AIR)) {
                if (regeneratePortal(level, pos.below(), state.getValue(AXIS)))
                    return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
            }
            return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(100) == 0) {
            level.playLocalSound(
                    (double)pos.getX() + 0.5,
                    (double)pos.getY() + 0.5,
                    (double)pos.getZ() + 0.5,
                    SoundEvents.PORTAL_AMBIENT,
                    SoundSource.BLOCKS,
                    0.5F,
                    random.nextFloat() * 0.4F + 0.8F,
                    false
            );
        }

        for (int i = 0; i < 4; i++) {
            double d = (double)pos.getX() + random.nextDouble();
            double e = (double)pos.getY() + random.nextDouble();
            double f = (double)pos.getZ() + random.nextDouble();

            int k = random.nextInt(2) * 2 - 1;
            if (!level.getBlockState(pos.west()).is(this) && !level.getBlockState(pos.east()).is(this)) {
                d = (double)pos.getX() + 0.5 + 0.25 * (double)k;
            } else {
                f = (double)pos.getZ() + 0.5 + 0.25 * (double)k;
            }

            level.addAlwaysVisibleParticle(ModParticleTypes.SCULK_DEPTHS_PORTAL_PARTICLE, false, d, e, f, 0, -0.01f, 0);
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        //if(world.isClient())return;
        if (entity.canUsePortal(true)) {
            entity.setAsInsidePortal(this, pos);
        }
    }


    @Override
    public int getPortalTransitionTime(ServerLevel level, Entity entity) {
        if (entity instanceof Player playerEntity) {
            return Math.max(1, level.getGameRules().getInt(playerEntity.getAbilities().invulnerable ? GameRules.RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY : GameRules.RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY));
        } else {
            return 0;
        }
    }


    @Override
    public DimensionTransition getPortalDestination(ServerLevel level, Entity entity, BlockPos blockPos) {
        ResourceKey<Level> key = level.dimension() == ModDimensions.SCULK_DEPTHS_LEVEL_KEY ? Level.OVERWORLD : ModDimensions.SCULK_DEPTHS_LEVEL_KEY;
        ServerLevel serverWorld = level.getServer().getLevel(key);
        if (serverWorld == null) {
            return null;
        } else {
            Vec3 vec3d = Vec3.atBottomCenterOf(blockPos);
            if (entity instanceof Player) {
                vec3d = vec3d.subtract(0, 1, 0);
            }
            if (!serverWorld.getBlockState(blockPos).is(ModBlocks.SCULK_DEPTHS_PORTAL)) { // backup tp

                serverWorld.setBlockAndUpdate(blockPos, ModBlocks.SCULK_DEPTHS_PORTAL.defaultBlockState());
                serverWorld.setBlockAndUpdate(blockPos.below(), ModBlocks.SCULK_DEPTHS_PORTAL.defaultBlockState());
                serverWorld.setBlockAndUpdate(blockPos.north(), Blocks.AIR.defaultBlockState());
                serverWorld.setBlockAndUpdate(blockPos.east(), Blocks.AIR.defaultBlockState());
                serverWorld.setBlockAndUpdate(blockPos.south(), Blocks.AIR.defaultBlockState());
                serverWorld.setBlockAndUpdate(blockPos.west(), Blocks.AIR.defaultBlockState());
                serverWorld.setBlockAndUpdate(blockPos.below().north(), Blocks.AIR.defaultBlockState());
                serverWorld.setBlockAndUpdate(blockPos.below().east(), Blocks.AIR.defaultBlockState());
                serverWorld.setBlockAndUpdate(blockPos.below().south(), Blocks.AIR.defaultBlockState());
                serverWorld.setBlockAndUpdate(blockPos.below().west(), Blocks.AIR.defaultBlockState());
                serverWorld.setBlockAndUpdate(blockPos.below(2), ModBlocks.ACTIVATED_AMALGAMITE.defaultBlockState());
                serverWorld.setBlockAndUpdate(blockPos.below(2).north(), ModBlocks.ACTIVATED_AMALGAMITE.defaultBlockState());
                serverWorld.setBlockAndUpdate(blockPos.below(2).east(), ModBlocks.ACTIVATED_AMALGAMITE.defaultBlockState());
                serverWorld.setBlockAndUpdate(blockPos.below(2).south(), ModBlocks.ACTIVATED_AMALGAMITE.defaultBlockState());
                serverWorld.setBlockAndUpdate(blockPos.below(2).west(), ModBlocks.ACTIVATED_AMALGAMITE.defaultBlockState());

                SculkDepths.LOGGER.warn("A player went through an invalid portal at {} if you're a server admin or have access to permissions it is recommended to build a proper 2 way portal", blockPos);
            }
            return new DimensionTransition(
                    serverWorld,
                    vec3d,
                    entity.getDeltaMovement(),
                    entity.getYRot(),
                    entity.getXRot(),
                    DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET)
            );
        }
    }


    @Override
    public Transition getLocalTransition() {
        return Transition.CONFUSION;
    }

    @Override
    protected boolean canBeReplaced(BlockState state, Fluid fluid) {
        return false;
    }
}
