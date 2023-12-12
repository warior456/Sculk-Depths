package net.ugi.sculk_depths.block.custom;


import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;


public class SoulFireBlock extends AbstractFireBlock {
    @Override
    protected MapCodec<? extends AbstractFireBlock> getCodec() {
        return null;
    }


    public SoulFireBlock(AbstractBlock.Settings settings) {
        super(settings, 2);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (this.canPlaceAt(state, world, pos)) {
            return this.getDefaultState();
        }
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return isSoulBase(world.getBlockState(pos.down()));
    }

    public static boolean isSoulBase(BlockState state) {
        //return true || state.isOf(Blocks.REINFORCED_DEEPSLATE) || state.isIn(BlockTags.LOGS) || state.isIn(BlockTags.INFINIBURN_END);
        return !state.isOf(Blocks.AIR);
    }

    @Override
    protected boolean isFlammable(BlockState state) {
        return true;
    }

}
