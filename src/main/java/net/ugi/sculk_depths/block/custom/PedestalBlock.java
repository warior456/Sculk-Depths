package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.util.*;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.particle.ModParticleTypes;
import net.ugi.sculk_depths.portal.GenerateStructureAPI;
import net.ugi.sculk_depths.portal.Portal;
import net.ugi.sculk_depths.state.property.ModProperties;
import net.ugi.sculk_depths.world.dimension.ModDimensions;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class PedestalBlock extends DirectionalBlock {
    public static final BooleanProperty HAS_ENERGY_ESSENCE = ModProperties.HAS_ENERGY_ESSENCE;
    public static final MapCodec<PedestalBlock> CODEC = createCodec(PedestalBlock::new);
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
            Shapes.union(box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
                    box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
                    box(2.0, 2.0, 2.0, 14.0, 14.0, 14.0)
                    ),Shapes.allEmpty(),
            BooleanOp.ONLY_FIRST
    );

    @Override
    protected MapCodec<? extends DirectionalBlock> getCodec() {
        return CODEC;
    }

    public PedestalBlock(Settings settings) {
        super(settings);
        this.registerDefaultState(this.getDefaultState().with(HAS_ENERGY_ESSENCE, false));
    }

    private Executor getAsyncExecutor() {
        return CompletableFuture.delayedExecutor(0, TimeUnit.MILLISECONDS);
    }

    @Override
    protected ItemActionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, Hand hand, BlockHitResult hitResult) {
        if (world.isClientSide()){
            return ItemActionResult.FAIL;
        }
        if (stack.getItem() == ModItems.ENERGY_ESSENCE && !state.get(ModProperties.HAS_ENERGY_ESSENCE)){
            Portal.addBlockPowerUpParticle((ServerLevel) world, pos, RandomSource.create(), 10);
            stack.consume(1,player);
            world.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 1.0f, 1.0f);

            BlockState blockState1 = state.with(HAS_ENERGY_ESSENCE, true);
            world.setBlockState(pos, blockState1);
            portalPhase = PortalPhase.CHECK_FRAME;
            world.scheduleBlockTick(pos,state.getBlock(),1);
            return ItemActionResult.SUCCESS;
        }
        return ItemActionResult.FAIL;
    }

    @Override
    public void scheduledTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        switch (portalPhase){
            case CHECK_FRAME:

                portalFramePos = Portal.getFramePos(state.get(FACING),pos, world);//todo benchmark
                if (portalFramePos == null) {portalPhase = PortalPhase.NONE;return;}
                if (portalFramePos.get(0) == null) {portalPhase = PortalPhase.NONE;return;}

                portalPhase = PortalPhase.GEN_FRAME;
                posArray = portalFramePos;

                world.scheduleBlockTick(pos,state.getBlock(),1);
                break;


            case GEN_FRAME:

                posArray = Portal.genFrameStep(world, posArray, random);
                if (posArray.get(posArray.size() - 1).getY() == -4096 && posArray.get(posArray.size() - 1).getZ() == 1){
                    posArray = new ArrayList<>();
                    for (BlockPos pos1: portalFramePos) {
                        posArray.add(pos1.above(3));
                        posArray.add(pos1.above(4));
                    }
                    portalPhase = PortalPhase.GEN_STRUCTURE;
                }
                else if (posArray.get(posArray.size() - 1).getY() == -4096 && posArray.get(posArray.size() - 1).getZ() == 0){
                    posArray = portalFramePos;
                    portalPhase = PortalPhase.CANCEL_FRAME;
                }
                world.scheduleBlockTick(pos,state.getBlock(),2);
                break;


            case CANCEL_FRAME:

                posArray = Portal.cancelFrameStep(world,posArray);
                if (posArray.get(posArray.size() - 1).getY() == -4096 && posArray.get(posArray.size() - 1).getZ() == 0){
                    posArray = new ArrayList<>();
                    portalPhase = PortalPhase.NONE;
                }
                else {
                    world.scheduleBlockTick(pos,state.getBlock(),2);
                }
                break;


            case GEN_STRUCTURE:

                BlockPos anchor = Portal.getFrameAnchorPos(state.get(FACING),pos, world);

                // Register a stub start so the structure will generate when chunks are actually generated/loaded.
                // If the target chunk is already loaded, the method will place the center piece only.
                if (!GenerateStructureAPI.registerStructureStartStub(world, ModDimensions.SCULK_DEPTHS_LEVEL_KEY, SculkDepths.id("portal_structure"), anchor)) {
                    structureStart = GenerateStructureAPI.structureStart(world, ModDimensions.SCULK_DEPTHS_LEVEL_KEY, SculkDepths.id("portal_structure"), anchor); //50ms (matteo)

                    if (structureStart == null || !structureStart.isValid()) {portalPhase = PortalPhase.NONE;return;}

                    BlockBox createBoundingBox = structureStart.getBoundingBox();
                    chunkArray = GenerateStructureAPI.generateChunkArray(//not laggy
                            new ChunkPos(ChunkSectionPos.getSectionCoord(createBoundingBox.getMinX()),ChunkSectionPos.getSectionCoord(createBoundingBox.getMinZ())),
                            new ChunkPos(ChunkSectionPos.getSectionCoord(createBoundingBox.getMaxX()),ChunkSectionPos.getSectionCoord(createBoundingBox.getMaxZ())),
                            0,1);

                    ServerLevel serverWorld = world.getServer().getWorld(ModDimensions.SCULK_DEPTHS_LEVEL_KEY);

                    CompletableFuture.runAsync(() -> {
                        for (ChunkPos chunkPos : chunkArray) {//load chunks
                            assert serverWorld != null;
                            serverWorld.getChunk(chunkPos.x, chunkPos.z);
                        }
                        GenerateStructureAPI.forceLoadChunks(chunkArray, serverWorld);

                    }, getAsyncExecutor()).thenRunAsync(() -> {
                        // This code runs back on the main server thread after the chunks are loaded
                        GenerateStructureAPI.generateStructurePartial(world, ModDimensions.SCULK_DEPTHS_LEVEL_KEY, SculkDepths.id("portal_structure"), structureStart, createBoundingBox.intersectingChunks().adjustArgs(ChunkPos[]::new));
                        portalPhase = PortalPhase.GEN_PORTAL;
                    }, world.getServer());
                    portalPhase = PortalPhase.WAITING_PARTICLES;
                    world.scheduleBlockTick(pos,state.getBlock(),1);
                    break;
                }

                portalPhase = PortalPhase.GEN_PORTAL;
                world.scheduleBlockTick(pos,state.getBlock(),1);
                break;


            case WAITING_PARTICLES:

                BlockPos posMin = Portal.getFrameMinPos(state.get(FACING),pos, world);
                if (state.get(FACING) == Direction.NORTH)
                    posMin = posMin.east(random.getInt(24)).above(random.getInt(6));
                if (state.get(FACING) == Direction.EAST)
                    posMin = posMin.south(random.getInt(24)).above(random.getInt(6));
                if (state.get(FACING) == Direction.SOUTH)
                    posMin = posMin.east(random.getInt(24)).above(random.getInt(6));
                if (state.get(FACING) == Direction.WEST)
                    posMin = posMin.south(random.getInt(24)).above(random.getInt(6));

                Portal.addPortalStartAttemptParticle(world, posMin, random, 20 + random.getInt(50));
                world.scheduleBlockTick(pos,state.getBlock(),1);
                break;


            case GEN_PORTAL:

                posArray = Portal.genPortalStep(world,posArray,state.get(FACING), random);
                if (posArray.get(posArray.size() - 1).getY() == -4096 && posArray.get(posArray.size() - 1).getZ() == 0){
                    posArray = new ArrayList<>();
                    portalPhase = PortalPhase.NONE;
                }
                else {
                    world.scheduleBlockTick(pos,state.getBlock(),2);
                }
                break;


            default:
                break;
        }
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if(state.get(ModProperties.HAS_ENERGY_ESSENCE)){
            int randomNumber = random.nextBetween(0, 5);
            float x = pos.getX() + 0.5f;
            float y = pos.getY() + 0.5f;
            float z = pos.getZ() + 0.5f;
            switch (randomNumber) {
                case 0://positivex
                    x = x + 0.6f + MathHelper.getFloat(random, 0f, 0.2f);
                    y = y + MathHelper.getFloat(random, -0.6f, 0.6f);
                    z = z + MathHelper.getFloat(random, -0.6f, 0.6f);
                    break;
                case 1://negativex
                    x = x - 0.6f - MathHelper.getFloat(random, 0f, 0.2f);
                    y = y + MathHelper.getFloat(random, -0.6f, 0.6f);
                    z = z + MathHelper.getFloat(random, -0.6f, 0.6f);
                    break;
                case 2://positivivez
                    x = x + MathHelper.getFloat(random, -0.6f, 0.6f);
                    y = y + MathHelper.getFloat(random, -0.6f, 0.6f);
                    z = z + 0.6f + MathHelper.getFloat(random, 0f, 0.2f);
                    break;
                case 3://negativez
                    x = x + MathHelper.getFloat(random, -0.6f, 0.6f);
                    y = y + MathHelper.getFloat(random, -0.6f, 0.6f);
                    z = z - 0.6f - MathHelper.getFloat(random, 0f, 0.2f);
                    break;
                case 4://positivey
                    x = x + MathHelper.getFloat(random, -0.6f, 0.6f);
                    y = y + 0.6f + MathHelper.getFloat(random, 0f, 0.2f);
                    z = z + MathHelper.getFloat(random, -0.6f, 0.6f);
                    break;
                case 5://negativey
                    x = x + MathHelper.getFloat(random, -0.6f, 0.6f);
                    y = y - 0.6f - MathHelper.getFloat(random, 0f, 0.2f);
                    z = z + MathHelper.getFloat(random, -0.6f, 0.6f);
                    break;
            }
            world.addAlwaysVisibleParticle((ParticleOptions) ModParticleTypes.ENERGY_PARTICLE, false, x, y, z, 0, 0.01, 0);
        }
    }

    @Override
    public BlockState hasRot(BlockState block, BlockRotation rotation) {
        return block.with(FACING, rotation.hasRot(block.get(FACING)));

    }

    @Override
    public BlockState initialMirror(BlockState block, BlockMirror initialMirror) {
        return block.hasRot(initialMirror.getRotation(block.get(FACING)));
    }


    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalDirection().opposite()).with(HAS_ENERGY_ESSENCE, false);
    }

    public static VoxelShape box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return Shapes.cuboid(minX / 16.0, minY / 16.0, minZ / 16.0, maxX / 16.0, maxY / 16.0, maxZ / 16.0);
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(HAS_ENERGY_ESSENCE);
    }

}
