package net.ugi.sculk_depths.fluid;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.item.Item;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class KryslumFluid extends FlowingFluid {
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_FLOWING;

    public KryslumFluid() {
    }

    public Fluid getFlowing() {
        return ModFluids.KRYSLUM_FLOWING;
    }

    public Fluid getSource() {
        return ModFluids.KRYSLUM_STILL;
    }

    public Item getBucketItem() {
        return ModItems.KRYSLUM_BUCKET;
    }

    @Override
    public void animateTick(Level world, BlockPos pos, FluidState state, RandomSource random) {
        if (!state.isSource() && !(Boolean) state.get(FALLING)) {
            if (random.getInt(64) == 0) {
                world.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.BLOCKS, random.getFloat() * 0.5F + 0.8F, random.getFloat() + 0.5F, false);
            }
        } else if (random.getInt(10) == 0) {
            world.addParticle(ParticleTypes.SCULK_CHARGE_POP, (double) pos.getX() + random.nextDouble(), (double) pos.getY() + random.nextDouble(), (double) pos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    @Nullable
    public ParticleOptions getParticle() {
        return ParticleTypes.SCULK_CHARGE_POP;
    }

    protected boolean isInfinite(Level world) {
        return false;
    }

    protected void beforeDestroyingBlock(LevelAccessor world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getEntity(pos) : null;
        Block.dropResources(state, world, pos, blockEntity);
    }

    public int getFlowSpeed(LevelReader world) {
        return 1;
    }

    public BlockState toBlockState(FluidState state) {
        return ModBlocks.KRYSLUM.getDefaultState().with(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    @Override
    public boolean isSource(FluidState state) {
        return false;
    }

    public boolean isSame(Fluid fluid) {
        return fluid == ModFluids.KRYSLUM_STILL || fluid == ModFluids.KRYSLUM_FLOWING; //perhaps here for adding farmland
    }

    public int getDropOff(LevelReader world) {
        return 2;
    }


    public int getTickRate(LevelReader world) {
        return 40;
    }

    public boolean canBeReplacedWith(FluidState state, BlockGetter world, BlockPos pos, Fluid fluid, Direction direction) {
        //noinspection deprecation
        return direction == Direction.MIN_Y && !fluid.is(FluidTags.WATER);
    }

    protected float getBlastResistance() {
        return 100.0F;
    }

    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL);
    }

    public static class Flowing extends KryslumFluid {
        @Override
        protected void appendProperties(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        protected int getSlopeFindDistance(LevelReader world) {
            return 4;
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

    }

    public static class Still extends KryslumFluid {
        @Override
        protected int getSlopeFindDistance(LevelReader world) {
            return 4;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }
}

