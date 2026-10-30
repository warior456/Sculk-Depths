package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FlowBlock extends DirectionalBlock {
    public static final MapCodec<FlowBlock> CODEC = simpleCodec(FlowBlock::new);

    @Override
    public MapCodec<FlowBlock> codec() {
        return CODEC;
    }

    protected static final float field_31233 = 6.0f;
    protected static final float field_31234 = 10.0f;
    protected static final VoxelShape Y_AXIS_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    public FlowBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    @Override
    public BlockState rotate(BlockState block, Rotation rotation) {
        return block.setValue(FACING, rotation.rotate(block.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState block, Mirror mirror) {
        return block.rotate(mirror.getRotation(block.getValue(FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING).getAxis()) {
            default: {
                return X_AXIS_AABB;
            }
            case Z: {
                return Z_AXIS_AABB;
            }
            case Y: {
                return Y_AXIS_AABB;
            }
        }
    }

/*    @Override
    @Deprecated
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        ArrayList<BlockPos> blockPosArrayList = new ArrayList<BlockPos>();
        switch (state.get(Properties.FACING)) {
            case NORTH:
                blockPosArrayList.add(pos.relative(Direction.EAST));
                blockPosArrayList.add(pos.relative(Direction.SOUTH));
                blockPosArrayList.add(pos.relative(Direction.WEST));
                blockPosArrayList.add(pos.relative(Direction.UP));
                blockPosArrayList.add(pos.relative(Direction.DOWN));
                if(kryslumPoweredOrNeighbourPowered(world, blockPosArrayList, pos)){
                    state = state.with(ModProperties.KRYSLUM_POWERED, true);
                    level.setBlock(pos, state);
                } else {
                    state = state.with(ModProperties.KRYSLUM_POWERED, false);
                    level.setBlock(pos, state);
                }
                break;
            case EAST:
                blockPosArrayList.add(pos.relative(Direction.NORTH));
                blockPosArrayList.add(pos.relative(Direction.SOUTH));
                blockPosArrayList.add(pos.relative(Direction.WEST));
                blockPosArrayList.add(pos.relative(Direction.UP));
                blockPosArrayList.add(pos.relative(Direction.DOWN));
                if(kryslumPoweredOrNeighbourPowered(world, blockPosArrayList, pos)){
                    state = state.with(ModProperties.KRYSLUM_POWERED, true);
                    level.setBlock(pos, state);
                } else {
                    state = state.with(ModProperties.KRYSLUM_POWERED, false);
                    level.setBlock(pos, state);
                }
                break;
            case SOUTH:
                blockPosArrayList.add(pos.relative(Direction.NORTH));
                blockPosArrayList.add(pos.relative(Direction.EAST));
                blockPosArrayList.add(pos.relative(Direction.WEST));
                blockPosArrayList.add(pos.relative(Direction.UP));
                blockPosArrayList.add(pos.relative(Direction.DOWN));
                if(kryslumPoweredOrNeighbourPowered(world, blockPosArrayList, pos)){
                    state = state.with(ModProperties.KRYSLUM_POWERED, true);
                    level.setBlock(pos, state);
                } else {
                    state = state.with(ModProperties.KRYSLUM_POWERED, false);
                    level.setBlock(pos, state);
                }
                break;
            case WEST:
                blockPosArrayList.add(pos.relative(Direction.NORTH));
                blockPosArrayList.add(pos.relative(Direction.EAST));
                blockPosArrayList.add(pos.relative(Direction.SOUTH));
                blockPosArrayList.add(pos.relative(Direction.UP));
                blockPosArrayList.add(pos.relative(Direction.DOWN));
                if(kryslumPoweredOrNeighbourPowered(world, blockPosArrayList, pos)){
                    state = state.with(ModProperties.KRYSLUM_POWERED, true);
                    level.setBlock(pos, state);
                } else {
                    state = state.with(ModProperties.KRYSLUM_POWERED, false);
                    level.setBlock(pos, state);
                }
                break;
            case UP:
                blockPosArrayList.add(pos.relative(Direction.NORTH));
                blockPosArrayList.add(pos.relative(Direction.EAST));
                blockPosArrayList.add(pos.relative(Direction.SOUTH));
                blockPosArrayList.add(pos.relative(Direction.WEST));
                blockPosArrayList.add(pos.relative(Direction.DOWN));
                if(kryslumPoweredOrNeighbourPowered(world, blockPosArrayList, pos)){
                    state = state.with(ModProperties.KRYSLUM_POWERED, true);
                    level.setBlock(pos, state);
                } else {
                    state = state.with(ModProperties.KRYSLUM_POWERED, false);
                    level.setBlock(pos, state);
                }
                break;
            case DOWN:
                blockPosArrayList.add(pos.relative(Direction.NORTH));
                blockPosArrayList.add(pos.relative(Direction.EAST));
                blockPosArrayList.add(pos.relative(Direction.SOUTH));
                blockPosArrayList.add(pos.relative(Direction.WEST));
                blockPosArrayList.add(pos.relative(Direction.UP));
                if(kryslumPoweredOrNeighbourPowered(world, blockPosArrayList, pos)){
                    state = state.with(ModProperties.KRYSLUM_POWERED, true);
                    level.setBlock(pos, state);
                } else {
                    state = state.with(ModProperties.KRYSLUM_POWERED, false);
                    level.setBlock(pos, state);
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
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getClickedFace().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
