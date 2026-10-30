package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.particle.ModParticleTypes;
import net.ugi.sculk_depths.portal.GenerateStructureAPI;
import net.ugi.sculk_depths.portal.Portal;
import net.ugi.sculk_depths.state.property.ModProperties;
import net.ugi.sculk_depths.world.dimension.ModDimensions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class PedestalBlock extends DirectionalBlock {
    public static final BooleanProperty HAS_ENERGY_ESSENCE = ModProperties.HAS_ENERGY_ESSENCE;
    public static final MapCodec<PedestalBlock> CODEC = simpleCodec(PedestalBlock::new);
    private static final VoxelShape RAYCAST_SHAPE = box(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);

    private List<BlockPos> portalFramePos = new ArrayList<>();
    private PortalPhase portalPhase = PortalPhase.NONE;
    private List<BlockPos> posArray = new ArrayList<>();
    private ChunkPos[] chunkArray = new ChunkPos[0];
    private StructureStart structureStart;

    public enum PortalPhase {
        NONE,
        CHECK_FRAME,
        GEN_FRAME,
        CANCEL_FRAME,
        GEN_STRUCTURE,
        WAITING_PARTICLES,
        GEN_PORTAL
    }

    protected static final VoxelShape OUTLINE_SHAPE = Shapes.join(
            Shapes.or(box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
                    box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
                    box(2.0, 2.0, 2.0, 14.0, 14.0, 14.0)
            ), Shapes.empty(),
            BooleanOp.ONLY_FIRST
    );

    @Override
    protected MapCodec<? extends DirectionalBlock> codec() {
        return CODEC;
    }

    public PedestalBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HAS_ENERGY_ESSENCE, false));
    }

    private Executor getAsyncExecutor() {
        return CompletableFuture.delayedExecutor(0, TimeUnit.MILLISECONDS);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return ItemInteractionResult.FAIL;
        }
        if (stack.getItem() == ModItems.ENERGY_ESSENCE && !state.getValue(ModProperties.HAS_ENERGY_ESSENCE)) {
            Portal.addBlockPowerUpParticle((ServerLevel) level, pos, RandomSource.create(), 10);
            stack.consume(1, player);
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 1.0f, 1.0f);

            BlockState blockState1 = state.setValue(HAS_ENERGY_ESSENCE, true);
            level.setBlockAndUpdate(pos, blockState1);
            portalPhase = PortalPhase.CHECK_FRAME;
            level.scheduleTick(pos, state.getBlock(), 1);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.FAIL;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        switch (portalPhase) {
            case CHECK_FRAME:

                portalFramePos = Portal.getFramePos(state.getValue(FACING), pos, level);//todo benchmark
                if (portalFramePos == null) {
                    portalPhase = PortalPhase.NONE;
                    return;
                }
                if (portalFramePos.get(0) == null) {
                    portalPhase = PortalPhase.NONE;
                    return;
                }

                portalPhase = PortalPhase.GEN_FRAME;
                posArray = portalFramePos;

                level.scheduleTick(pos, state.getBlock(), 1);
                break;


            case GEN_FRAME:

                posArray = Portal.genFrameStep(level, posArray, random);
                if (posArray.get(posArray.size() - 1).getY() == -4096 && posArray.get(posArray.size() - 1).getZ() == 1) {
                    posArray = new ArrayList<>();
                    for (BlockPos pos1 : portalFramePos) {
                        posArray.add(pos1.above(3));
                        posArray.add(pos1.above(4));
                    }
                    portalPhase = PortalPhase.GEN_STRUCTURE;
                } else if (posArray.get(posArray.size() - 1).getY() == -4096 && posArray.get(posArray.size() - 1).getZ() == 0) {
                    posArray = portalFramePos;
                    portalPhase = PortalPhase.CANCEL_FRAME;
                }
                level.scheduleTick(pos, state.getBlock(), 2);
                break;


            case CANCEL_FRAME:

                posArray = Portal.cancelFrameStep(level, posArray);
                if (posArray.get(posArray.size() - 1).getY() == -4096 && posArray.get(posArray.size() - 1).getZ() == 0) {
                    posArray = new ArrayList<>();
                    portalPhase = PortalPhase.NONE;
                } else {
                    level.scheduleTick(pos, state.getBlock(), 2);
                }
                break;


            case GEN_STRUCTURE:

                BlockPos anchor = Portal.getFrameAnchorPos(state.getValue(FACING), pos, level);

                // Register a stub start so the structure will generate when chunks are actually generated/loaded.
                // If the target chunk is already loaded, the method will place the center piece only.
                if (!GenerateStructureAPI.registerStructureStartStub(level, ModDimensions.SCULK_DEPTHS_LEVEL_KEY, SculkDepths.id("portal_structure"), anchor)) {
                    structureStart = GenerateStructureAPI.structureStart(level, ModDimensions.SCULK_DEPTHS_LEVEL_KEY, SculkDepths.id("portal_structure"), anchor); //50ms (matteo)

                    if (structureStart == null || !structureStart.isValid()) {
                        portalPhase = PortalPhase.NONE;
                        return;
                    }

                    BoundingBox createBoundingBox = structureStart.getBoundingBox();
                    chunkArray = GenerateStructureAPI.generateChunkArray(//not laggy
                            new ChunkPos(SectionPos.blockToSectionCoord(createBoundingBox.minX()), SectionPos.blockToSectionCoord(createBoundingBox.minZ())),
                            new ChunkPos(SectionPos.blockToSectionCoord(createBoundingBox.maxX()), SectionPos.blockToSectionCoord(createBoundingBox.maxZ())),
                            0, 1);

                    ServerLevel serverWorld = level.getServer().getLevel(ModDimensions.SCULK_DEPTHS_LEVEL_KEY);

                    CompletableFuture.runAsync(() -> {
                        for (ChunkPos chunkPos : chunkArray) {//load chunks
                            assert serverWorld != null;
                            serverWorld.getChunk(chunkPos.x, chunkPos.z);
                        }
                        GenerateStructureAPI.forceLoadChunks(chunkArray, serverWorld);

                    }, getAsyncExecutor()).thenRunAsync(() -> {
                        // This code runs back on the main server thread after the chunks are loaded
                        GenerateStructureAPI.generateStructurePartial(level, ModDimensions.SCULK_DEPTHS_LEVEL_KEY, SculkDepths.id("portal_structure"), structureStart, createBoundingBox.intersectingChunks().toArray(ChunkPos[]::new));
                        portalPhase = PortalPhase.GEN_PORTAL;
                    }, level.getServer());
                    portalPhase = PortalPhase.WAITING_PARTICLES;
                    level.scheduleTick(pos, state.getBlock(), 1);
                    break;
                }

                portalPhase = PortalPhase.GEN_PORTAL;
                level.scheduleTick(pos, state.getBlock(), 1);
                break;


            case WAITING_PARTICLES:

                BlockPos posMin = Portal.getFrameMinPos(state.getValue(FACING), pos, level);
                if (state.getValue(FACING) == Direction.NORTH)
                    posMin = posMin.east(random.nextInt(24)).above(random.nextInt(6));
                if (state.getValue(FACING) == Direction.EAST)
                    posMin = posMin.south(random.nextInt(24)).above(random.nextInt(6));
                if (state.getValue(FACING) == Direction.SOUTH)
                    posMin = posMin.east(random.nextInt(24)).above(random.nextInt(6));
                if (state.getValue(FACING) == Direction.WEST)
                    posMin = posMin.south(random.nextInt(24)).above(random.nextInt(6));

                Portal.addPortalStartAttemptParticle(level, posMin, random, 20 + random.nextInt(50));
                level.scheduleTick(pos, state.getBlock(), 1);
                break;


            case GEN_PORTAL:

                posArray = Portal.genPortalStep(level, posArray, state.getValue(FACING), random);
                if (posArray.get(posArray.size() - 1).getY() == -4096 && posArray.get(posArray.size() - 1).getZ() == 0) {
                    posArray = new ArrayList<>();
                    portalPhase = PortalPhase.NONE;
                } else {
                    level.scheduleTick(pos, state.getBlock(), 2);
                }
                break;


            default:
                break;
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(ModProperties.HAS_ENERGY_ESSENCE)) {
            int randomNumber = random.nextIntBetweenInclusive(0, 5);
            float x = pos.getX() + 0.5f;
            float y = pos.getY() + 0.5f;
            float z = pos.getZ() + 0.5f;
            switch (randomNumber) {
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
            level.addAlwaysVisibleParticle((ParticleOptions) ModParticleTypes.ENERGY_PARTICLE, false, x, y, z, 0, 0.01, 0);
        }
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite()).setValue(HAS_ENERGY_ESSENCE, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(HAS_ENERGY_ESSENCE);
    }

}
