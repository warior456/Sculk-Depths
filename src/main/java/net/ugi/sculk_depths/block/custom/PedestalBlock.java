package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.portal.TestFrame;
import net.ugi.sculk_depths.state.property.ModProperties;

public class PedestalBlock extends FacingBlock {
    public static final BooleanProperty HAS_ENERGY_ESSENCE = ModProperties.HAS_ENERGY_ESSENCE;
    public static final MapCodec<PedestalBlock> CODEC = createCodec(PedestalBlock::new);
    private static final VoxelShape RAYCAST_SHAPE = createCuboidShape(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
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
        if (stack.getItem() == ModItems.ENERGY_ESSENCE && !state.get(ModProperties.HAS_ENERGY_ESSENCE)){
            stack.decrementUnlessCreative(1,player);

            BlockState blockState1 = state.with(HAS_ENERGY_ESSENCE, true);
            world.setBlockState(pos, blockState1);
            
            BlockPos portalFramePos = TestFrame.getFramePos(state.get(FACING),pos, world);
            if (portalFramePos != null)
                world.setBlockState(portalFramePos, ModBlocks.ACTIVATED_AMALGAMITE.getDefaultState());
            
            
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
