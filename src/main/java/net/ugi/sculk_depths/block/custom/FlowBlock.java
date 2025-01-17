package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.ugi.sculk_depths.state.property.ModProperties;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.ArrayList;

import static net.minecraft.util.BlockRotation.CLOCKWISE_180;

public class FlowBlock extends FacingBlock {
    @Override
    protected MapCodec<? extends FacingBlock> getCodec() {
        return null;
    }

    protected static final float field_31233 = 6.0f;
    protected static final float field_31234 = 10.0f;
    protected static final VoxelShape Y_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape Z_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape X_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    public FlowBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
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
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING).getAxis()) {
            default: {
                return X_SHAPE;
            }
            case Z: {
                return Z_SHAPE;
            }
            case Y:
        }
        return Y_SHAPE;
    }

/*    @Override
    @Deprecated
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        ArrayList<BlockPos> blockPosArrayList = new ArrayList<BlockPos>();
        switch (state.get(Properties.FACING)) {
            case NORTH:
                blockPosArrayList.add(pos.offset(Direction.EAST));
                blockPosArrayList.add(pos.offset(Direction.SOUTH));
                blockPosArrayList.add(pos.offset(Direction.WEST));
                blockPosArrayList.add(pos.offset(Direction.UP));
                blockPosArrayList.add(pos.offset(Direction.DOWN));
                if(kryslumPoweredOrNeighbourPowered(world, blockPosArrayList, pos)){
                    state = state.with(ModProperties.KRYSLUM_POWERED, true);
                    world.setBlockState(pos, state);
                } else {
                    state = state.with(ModProperties.KRYSLUM_POWERED, false);
                    world.setBlockState(pos, state);
                }
                break;
            case EAST:
                blockPosArrayList.add(pos.offset(Direction.NORTH));
                blockPosArrayList.add(pos.offset(Direction.SOUTH));
                blockPosArrayList.add(pos.offset(Direction.WEST));
                blockPosArrayList.add(pos.offset(Direction.UP));
                blockPosArrayList.add(pos.offset(Direction.DOWN));
                if(kryslumPoweredOrNeighbourPowered(world, blockPosArrayList, pos)){
                    state = state.with(ModProperties.KRYSLUM_POWERED, true);
                    world.setBlockState(pos, state);
                } else {
                    state = state.with(ModProperties.KRYSLUM_POWERED, false);
                    world.setBlockState(pos, state);
                }
                break;
            case SOUTH:
                blockPosArrayList.add(pos.offset(Direction.NORTH));
                blockPosArrayList.add(pos.offset(Direction.EAST));
                blockPosArrayList.add(pos.offset(Direction.WEST));
                blockPosArrayList.add(pos.offset(Direction.UP));
                blockPosArrayList.add(pos.offset(Direction.DOWN));
                if(kryslumPoweredOrNeighbourPowered(world, blockPosArrayList, pos)){
                    state = state.with(ModProperties.KRYSLUM_POWERED, true);
                    world.setBlockState(pos, state);
                } else {
                    state = state.with(ModProperties.KRYSLUM_POWERED, false);
                    world.setBlockState(pos, state);
                }
                break;
            case WEST:
                blockPosArrayList.add(pos.offset(Direction.NORTH));
                blockPosArrayList.add(pos.offset(Direction.EAST));
                blockPosArrayList.add(pos.offset(Direction.SOUTH));
                blockPosArrayList.add(pos.offset(Direction.UP));
                blockPosArrayList.add(pos.offset(Direction.DOWN));
                if(kryslumPoweredOrNeighbourPowered(world, blockPosArrayList, pos)){
                    state = state.with(ModProperties.KRYSLUM_POWERED, true);
                    world.setBlockState(pos, state);
                } else {
                    state = state.with(ModProperties.KRYSLUM_POWERED, false);
                    world.setBlockState(pos, state);
                }
                break;
            case UP:
                blockPosArrayList.add(pos.offset(Direction.NORTH));
                blockPosArrayList.add(pos.offset(Direction.EAST));
                blockPosArrayList.add(pos.offset(Direction.SOUTH));
                blockPosArrayList.add(pos.offset(Direction.WEST));
                blockPosArrayList.add(pos.offset(Direction.DOWN));
                if(kryslumPoweredOrNeighbourPowered(world, blockPosArrayList, pos)){
                    state = state.with(ModProperties.KRYSLUM_POWERED, true);
                    world.setBlockState(pos, state);
                } else {
                    state = state.with(ModProperties.KRYSLUM_POWERED, false);
                    world.setBlockState(pos, state);
                }
                break;
            case DOWN:
                blockPosArrayList.add(pos.offset(Direction.NORTH));
                blockPosArrayList.add(pos.offset(Direction.EAST));
                blockPosArrayList.add(pos.offset(Direction.SOUTH));
                blockPosArrayList.add(pos.offset(Direction.WEST));
                blockPosArrayList.add(pos.offset(Direction.UP));
                if(kryslumPoweredOrNeighbourPowered(world, blockPosArrayList, pos)){
                    state = state.with(ModProperties.KRYSLUM_POWERED, true);
                    world.setBlockState(pos, state);
                } else {
                    state = state.with(ModProperties.KRYSLUM_POWERED, false);
                    world.setBlockState(pos, state);
                }
                break;
        }

    }*/

    /*public boolean kryslumPoweredOrNeighbourPowered(ServerWorld world, ArrayList<BlockPos> blockPosArrayList, BlockPos pos){

        for (BlockPos blockPos : blockPosArrayList) {
            BlockState blockState = world.getBlockState(blockPos);
            if(blockState.isIn(ModTags.Blocks.KRYSLUM_FLOWABLE_BLOCKS)){
                Direction direction =  Direction.fromVector(blockPos.getX() - pos.getX(), blockPos.getY() - pos.getY(), blockPos.getZ() - pos.getZ());
                switch (direction){
                    case NORTH:

                        if(blockState.get(FACING) == Direction.SOUTH){
                            blockState.scheduledTick((ServerWorld) world, blockPos, Random.create());
                            if(blockState.get(ModProperties.KRYSLUM_POWERED)){
                                return true;
                            }
                        }
                        break;

                    case EAST:

                        if(blockState.get(FACING) == Direction.WEST){
                            blockState.scheduledTick((ServerWorld) world, blockPos, Random.create());
                            if(blockState.get(ModProperties.KRYSLUM_POWERED)){
                                return true;
                            }
                        }
                        break;

                    case SOUTH:

                        if(blockState.get(FACING) == Direction.NORTH){
                            blockState.scheduledTick((ServerWorld) world, blockPos, Random.create());
                            if(blockState.get(ModProperties.KRYSLUM_POWERED)){
                                return true;
                            }
                        }
                        break;

                    case WEST:

                        if(blockState.get(FACING) == Direction.EAST){
                            blockState.scheduledTick((ServerWorld) world, blockPos, Random.create());
                            if(blockState.get(ModProperties.KRYSLUM_POWERED)){
                                return true;
                            }
                        }
                        break;

                    case UP:

                        if(blockState.get(FACING) == Direction.DOWN){
                            blockState.scheduledTick((ServerWorld) world, blockPos, Random.create());
                            if(blockState.get(ModProperties.KRYSLUM_POWERED)){
                                return true;
                            }
                        }
                        break;

                    case DOWN:

                        if(blockState.get(FACING) == Direction.UP){
                            blockState.scheduledTick((ServerWorld) world, blockPos, Random.create());
                            if(blockState.get(ModProperties.KRYSLUM_POWERED)){
                                return true;
                            }
                        }
                        break;

                    default:
                        break;
                }
            } else if (blockState.getFluidState().isIn(ModTags.Fluids.KRYSLUM)) {
                return true;
            }
        }
        return false;
    }*/


    

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getSide().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
