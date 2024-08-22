package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.portal.PortalFrame;
import net.ugi.sculk_depths.state.property.ModProperties;

import java.util.Arrays;
import java.util.List;

public class PedestalBlock extends FacingBlock {
    public static final BooleanProperty HAS_ENERGY_ESSENCE = ModProperties.HAS_ENERGY_ESSENCE;
    public static final MapCodec<PedestalBlock> CODEC = createCodec(PedestalBlock::new);
    private static final VoxelShape RAYCAST_SHAPE = createCuboidShape(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);

    private static BlockPos[] portalFramePos = new BlockPos[0];
    private static String portalFase = "none";

    private static BlockPos[] posArray = new BlockPos[0];
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





/*        GenerateStructureAPI.generateStructure(world, ModDimensions.SCULK_DEPTHS_LEVEL_KEY,
                 world.getRegistryManager().get
                , pos);*/

        if (stack.getItem() == ModItems.ENERGY_ESSENCE && !state.get(ModProperties.HAS_ENERGY_ESSENCE)){
            stack.decrementUnlessCreative(1,player);
            world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            BlockState blockState1 = state.with(HAS_ENERGY_ESSENCE, true);
            world.setBlockState(pos, blockState1);
            
            portalFramePos = PortalFrame.getFramePos(state.get(FACING),pos, world);
            if (portalFramePos != null){
                if ( portalFramePos[0] != null){
                    portalFase = "genFrame";
                    posArray = portalFramePos;
                    world.scheduleBlockTick(pos,state.getBlock(),20);
                }
            }
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
