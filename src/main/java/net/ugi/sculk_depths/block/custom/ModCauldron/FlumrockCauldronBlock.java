package net.ugi.sculk_depths.block.custom.ModCauldron;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class FlumrockCauldronBlock extends AbstractCauldronBlock {

    private static final VoxelShape RAYCAST_SHAPE = AbstractCauldronBlock.createCuboidShape(1.5, 2.0, 1.5, 14.5, 16.0, 14.5);
    protected static final VoxelShape OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),
            VoxelShapes.union(
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 2.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 14.0, 16.0, 1.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 0.0, 2.0, 1.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(14.0, 0.0, 0.0, 16.0, 1.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(4.0, 0.0, 2.0, 12.0, 1.0, 14.0),
                    AbstractCauldronBlock.createCuboidShape(2.0, 0.0, 4.0, 14.0, 1.0, 12.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 1.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 15.0, 16.0, 2.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 0.0, 1.0, 2.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(15.0, 0.0, 0.0, 16.0, 2.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(1.0, 2.0, 1.0, 15.0, 13.0, 15.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 13.0, 0.0, 16.0, 15.0, 1.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 13.0, 15.0, 16.0, 15.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 13.0, 0.0, 1.0, 15.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(15.0, 13.0, 0.0, 16.0, 15.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(1.0, 15.0, 1.0, 15.0, 16.0, 15.0),
                    RAYCAST_SHAPE), BooleanBiFunction.ONLY_FIRST);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return RAYCAST_SHAPE;
    }


    public FlumrockCauldronBlock(Settings settings) {
        super(settings, ModCauldronBehavior.EMPTY_FLUMROCK_CAULDRON_BEHAVIOR);
    }
    @Override
    public boolean isFull(BlockState state) {
        return false;
    }




}

