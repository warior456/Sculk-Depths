package net.ugi.sculk_depths.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class KryslumFluid extends FlowableFluid {
    public static final IntProperty LEVEL = Properties.LEVEL_1_8;

    public KryslumFluid() {
    }

    public Fluid getFlowing() {
        return ModFluids.KRYSLUM_FLOWING;
    }

    public Fluid getStill() {
        return ModFluids.KRYSLUM_STILL;
    }

    public Item getBucketItem() {
        return ModItems.KRYSLUM_BUCKET;
    }

    @Override
    public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
        if (!state.isStill() && !(Boolean) state.get(FALLING)) {
            if (random.nextInt(64) == 0) {
                world.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_SCULK_CATALYST_BLOOM, SoundCategory.BLOCKS, random.nextFloat() * 0.5F + 0.8F, random.nextFloat() + 0.5F, false);
            }
        } else if (random.nextInt(10) == 0) {
            world.addParticle(ParticleTypes.SCULK_CHARGE_POP, (double) pos.getX() + random.nextDouble(), (double) pos.getY() + random.nextDouble(), (double) pos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    @Nullable
    public ParticleEffect getParticle() {
        return ParticleTypes.SCULK_CHARGE_POP;
    }

    protected boolean isInfinite(World world) {
        return false;
    }

    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    public int getFlowSpeed(WorldView world) {
        return 1;
    }

    public BlockState toBlockState(FluidState state) {
        return ModBlocks.KRYSLUM.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    public boolean matchesType(Fluid fluid) {
        return fluid == ModFluids.KRYSLUM_STILL || fluid == ModFluids.KRYSLUM_FLOWING; //perhaps here for adding farmland
    }

    public int getLevelDecreasePerBlock(WorldView world) {
        return 2;
    }


    public int getTickRate(WorldView world) {
        return 40;
    }

    public boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        //noinspection deprecation
        return direction == Direction.DOWN && !fluid.isIn(FluidTags.WATER);
    }

    protected float getBlastResistance() {
        return 100.0F;
    }

    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.ITEM_BUCKET_FILL);
    }

    public static class Flowing extends KryslumFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

    }

    public static class Still extends KryslumFluid {
        @Override
        public int getLevel(FluidState state) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }
    }
}

