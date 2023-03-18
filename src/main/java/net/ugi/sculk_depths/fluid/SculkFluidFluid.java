package net.ugi.sculk_depths.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class SculkFluidFluid extends FlowableFluid {
    public SculkFluidFluid() {
    }

    public Fluid getFlowing() {
        return ModFluids.FLOWING_SCULK_FLUID;
    }

    public Fluid getStill() {
        return ModFluids.SCULK_FLUID;
    }

    public Item getBucketItem() {
        return ModItems.SCULK_FLUID_BUCKET;
    }

    public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
        if (!state.isStill() && !(Boolean)state.get(FALLING)) {
            if (random.nextInt(64) == 0) {
                world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
            }
        } else if (random.nextInt(10) == 0) {
            world.addParticle(ParticleTypes.UNDERWATER, (double)pos.getX() + random.nextDouble(), (double)pos.getY() + random.nextDouble(), (double)pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
        }

    }

    @Nullable
    public ParticleEffect getParticle() {
        return ParticleTypes.SCULK_SOUL;
    }

    protected boolean isInfinite(World world) {
        return world.getGameRules().getBoolean(GameRules.LAVA_SOURCE_CONVERSION);
    }

    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    public int getFlowSpeed(WorldView world) {
        return 4;
    }

    public BlockState toBlockState(FluidState state) {
        return ModBlocks.SCULK_FLUID.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
    }

    public boolean matchesType(Fluid fluid) {
        return fluid == ModFluids.SCULK_FLUID || fluid == ModFluids.FLOWING_SCULK_FLUID;
    }

    public int getLevelDecreasePerBlock(WorldView world) {
        return 1;
    }

    public int getTickRate(WorldView world) {
        return 4;
    }

    public boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN;
    }

    protected float getBlastResistance() {
        return 100.0F;
    }

    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.ITEM_BUCKET_FILL);
    }

    public static class Flowing extends net.ugi.sculk_depths.fluid.SculkFluidFluid {
        public Flowing() {
        }

        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(new Property[]{LEVEL});
        }

        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        public boolean isStill(FluidState state) {
            return false;
        }
    }

    public static class Still extends net.ugi.sculk_depths.fluid.SculkFluidFluid {
        public Still() {
        }

        public int getLevel(FluidState state) {
            return 8;
        }

        public boolean isStill(FluidState state) {
            return true;
        }
    }
}
