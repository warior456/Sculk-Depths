package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;
import net.minecraft.world.gen.structure.Structures;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.portal.GenerateStructureAPI;
import net.ugi.sculk_depths.portal.PortalFrame;
import net.ugi.sculk_depths.state.property.ModProperties;
import net.ugi.sculk_depths.world.dimension.ModDimensions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PedestalBlock extends FacingBlock {
    public static final BooleanProperty HAS_ENERGY_ESSENCE = ModProperties.HAS_ENERGY_ESSENCE;
    public static final MapCodec<PedestalBlock> CODEC = createCodec(PedestalBlock::new);
    private static final VoxelShape RAYCAST_SHAPE = createCuboidShape(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);

    private BlockPos[] portalFramePos = new BlockPos[0];
    private String portalFase = "none";
    private BlockPos[] posArray = new BlockPos[0];
    private ChunkPos[] chunkArray = new ChunkPos[0];

    protected static final VoxelShape OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(
            VoxelShapes.union(createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
                    createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
                    createCuboidShape(2.0, 2.0, 2.0, 14.0, 14.0, 14.0)
                    ),VoxelShapes.empty(),
            BooleanBiFunction.ONLY_FIRST
    );

    @Override
    protected MapCodec<? extends FacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(HAS_ENERGY_ESSENCE);

    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (portalFase == "checkFrame") {
            portalFramePos = PortalFrame.getFramePos(state.get(FACING),pos, world);
            if (portalFramePos != null){
                if ( portalFramePos[0] != null){
                    BlockPos anchor = PortalFrame.getFrameAnchorPos(state.get(FACING),pos, world);
                    //maybe we can try a world.emitgameevent with a custom event to generate this???
                    Date date = new Date();
                    long timeMilli = date.getTime();
                    System.out.println("start structure: " + timeMilli);
                    StructureStart structureStart = GenerateStructureAPI.structureStart(world, ModDimensions.SCULK_DEPTHS_LEVEL_KEY, SculkDepths.identifier("portal_structure"), anchor);


                    BlockBox boundingBox = structureStart.getBoundingBox();

                    chunkArray = GenerateStructureAPI.generateChunkArray(
                            new ChunkPos(ChunkSectionPos.getSectionCoord(boundingBox.getMinX()),ChunkSectionPos.getSectionCoord(boundingBox.getMinZ())),
                                    new ChunkPos(ChunkSectionPos.getSectionCoord(boundingBox.getMaxX()),ChunkSectionPos.getSectionCoord(boundingBox.getMaxZ())),
                            2,5);
                            /*ChunkPos.stream(
                            new ChunkPos(ChunkSectionPos.getSectionCoord(boundingBox.getMinX())+1,ChunkSectionPos.getSectionCoord(boundingBox.getMinZ())+1),
                            new ChunkPos(ChunkSectionPos.getSectionCoord(boundingBox.getMaxX())-1,ChunkSectionPos.getSectionCoord(boundingBox.getMaxZ())-1)).toArray(ChunkPos[]::new);*/
                            //structureStart.getBoundingBox().streamChunkPos().toArray(ChunkPos[]::new);
                    date = new Date();
                    long timeMilli2 = date.getTime();
                    System.out.println("delta: " + (float)(timeMilli2-timeMilli)/1000F);
                    System.out.println("load: " + timeMilli2);
                    ServerWorld serverWorld = world.getServer().getWorld(ModDimensions.SCULK_DEPTHS_LEVEL_KEY);
                    GenerateStructureAPI.forceLoadNearbyChunks(chunkArray, serverWorld);
                    date = new Date();
                    long timeMilli3 = date.getTime();
                    System.out.println("delta: " + (float)(timeMilli3-timeMilli2)/1000F);
                    System.out.println("gen: " + timeMilli3);
                    GenerateStructureAPI.generateStructurePartial(world, ModDimensions.SCULK_DEPTHS_LEVEL_KEY, SculkDepths.identifier("portal_structure"), structureStart, boundingBox.streamChunkPos().toArray(ChunkPos[]::new));
                    date = new Date();
                    long timeMilli4 = date.getTime();
                    System.out.println("delta: " + (float)(timeMilli4-timeMilli3)/1000F);
                    System.out.println("unload: " + timeMilli4);
                    GenerateStructureAPI.unloadNearbyChunks(chunkArray, world);
                    date = new Date();
                    long timeMilli5 = date.getTime();
                    System.out.println("delta: " + (float)(timeMilli5-timeMilli4)/1000F);
                    System.out.println("stop structure: " + timeMilli5);

                    System.out.println((structureStart.getBoundingBox().getMaxX()-structureStart.getBoundingBox().getMinX()) + " x " + (structureStart.getBoundingBox().getMaxZ()-structureStart.getBoundingBox().getMinZ()) + " blocks");
                    System.out.println((structureStart.getBoundingBox().getMinX()) + " ~ " + (structureStart.getBoundingBox().getMinZ()) + " min");
                    System.out.println((structureStart.getBoundingBox().getMaxX()) + " ~ " + (structureStart.getBoundingBox().getMaxZ()) + " max");
                    System.out.println(((int)(structureStart.getBoundingBox().getMaxX()-structureStart.getBoundingBox().getMinX())/16 + 1) + " x " + ((int)(structureStart.getBoundingBox().getMaxZ()-structureStart.getBoundingBox().getMinZ())/16 + 1) + " chunks");
                    int chunks = structureStart.getBoundingBox().streamChunkPos().toArray(ChunkPos[]::new).length;
                    System.out.println( chunks + " chunks");
                    System.out.println( chunkArray.length + " chunksArray chunks");
                    float m = 1000;
                    float delay = (timeMilli5-timeMilli)/m;
                    System.out.println(delay+ " sec");
                    float chunksL = chunks;
                    System.out.println((delay/chunksL) + " s/chunk");
                    portalFase = "genFrame";
                    posArray = portalFramePos;

                    world.scheduleBlockTick(pos,state.getBlock(),1);
                }
                else portalFase = "none";
            }
            else portalFase = "none";
        }
        if (portalFase == "genFrame") {
            posArray = PortalFrame.genFrameStep(world,posArray);
            if (posArray[posArray.length -1].getY() == -4096 && posArray[posArray.length -1].getZ() == 1){
                posArray = new BlockPos[0];
                for (BlockPos pos1: portalFramePos) {
                    posArray = PortalFrame.addElement(posArray,pos1.up(3));
                    posArray = PortalFrame.addElement(posArray,pos1.up(4));
                }
                portalFase = "genPortal";
            }
            else if (posArray[posArray.length -1].getY() == -4096 && posArray[posArray.length -1].getZ() == 0){
                posArray = portalFramePos;
                portalFase = "cancelFrame";
            }
            world.scheduleBlockTick(pos,state.getBlock(),2);
        }

        if (portalFase == "cancelFrame") {
            posArray = PortalFrame.cancelFrameStep(world,posArray);
            if (posArray[posArray.length -1].getY() == -4096 && posArray[posArray.length -1].getZ() == 0){
                posArray = new BlockPos[0];
                portalFase = "none";
            }
            else {
                world.scheduleBlockTick(pos,state.getBlock(),2);
            }
        }

        if (portalFase == "genPortal") {
            posArray = PortalFrame.genPortalStep(world,posArray,state.get(FACING));
            if (posArray[posArray.length -1].getY() == -4096 && posArray[posArray.length -1].getZ() == 0){
                posArray = new BlockPos[0];
                portalFase = "none";
            }
            else {
                world.scheduleBlockTick(pos,state.getBlock(),2);
            }
        }
    }

    public PedestalBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(HAS_ENERGY_ESSENCE, false));
    }

    @Override
    public BlockState rotate(BlockState blockState, BlockRotation rotation) {
        return blockState.with(FACING, rotation.rotate(blockState.get(FACING)));

    }

    @Override
    public BlockState mirror(BlockState blockState, BlockMirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.get(FACING)));
    }



    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()){
            return ItemActionResult.FAIL;
        }

        //todo check if this runs twice
        System.out.println("checkthis^");


        if (stack.getItem() == ModItems.ENERGY_ESSENCE && !state.get(ModProperties.HAS_ENERGY_ESSENCE)){
            stack.decrementUnlessCreative(1,player);
            world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.BLOCKS, 1.0f, 1.0f);

            BlockState blockState1 = state.with(HAS_ENERGY_ESSENCE, true);
            world.setBlockState(pos, blockState1);
            portalFase = "checkFrame";
            world.scheduleBlockTick(pos,state.getBlock(),1);
            return ItemActionResult.SUCCESS;
        }
        return ItemActionResult.FAIL;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(HAS_ENERGY_ESSENCE, false);
    }

    public static VoxelShape createCuboidShape(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return VoxelShapes.cuboid(minX / 16.0, minY / 16.0, minZ / 16.0, maxX / 16.0, maxY / 16.0, maxZ / 16.0);
    }

}
