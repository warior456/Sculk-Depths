package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.ugi.sculk_depths.state.property.ModProperties;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.ArrayList;


public class FlowBlock extends FacingBlock {
    @Override
    protected MapCodec<? extends FacingBlock> getCodec() {
        return null;
    }

    protected static final float field_31233 = 6.0f;
    protected static final float field_31234 = 10.0f;
    protected static final VoxelShape Y_AXIS_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    public FlowBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.registerDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.MAX_Y));
    }

    @Override
    public BlockState hasRot(BlockState block, Rotation rotation) {
        return block.with(FACING, rotation.hasRot(block.get(FACING)));

    }

    @Override
    public BlockState initialMirror(BlockState block, Mirror initialMirror) {
        return block.hasRot(initialMirror.getRotation(block.get(FACING)));
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING).getAxis()) {
            default: {
                return X_AXIS_AABB;
            }
            case Z: {
                return Z_AXIS_AABB;
            }
            case Y_ROT:
        }
        return Y_AXIS_AABB;
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
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getSide().opposite());
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
