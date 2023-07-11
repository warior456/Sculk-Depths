package net.ugi.sculk_depths.block.custom.ModCauldron;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import static net.minecraft.block.LeveledCauldronBlock.LEVEL;


public class KryslumFlumrockCauldronBlock extends AbstractCauldronBlock {

    public KryslumFlumrockCauldronBlock(AbstractBlock.Settings settings) {
        super(settings, ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR);
        this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 1));
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.get(LEVEL) == 3;
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return (6.0 + (double)state.get(LEVEL).intValue() * 3.0) / 16.0;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient && entity.isOnFire() && this.isEntityTouchingFluid(state, pos, entity)) {

        }
    }

    protected void onFireCollision(BlockState state, World world, BlockPos pos) {
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
    }

    public static void decrementFluidLevel(BlockState state, World world, BlockPos pos) {
        int i = state.get(LEVEL) - 1;
        BlockState blockState = i == 0 ? Blocks.CAULDRON.getDefaultState() : (BlockState)state.with(LEVEL, i);
        world.setBlockState(pos, blockState);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
    }


    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(LEVEL);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }



}
