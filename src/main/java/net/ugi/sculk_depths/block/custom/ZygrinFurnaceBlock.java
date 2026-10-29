package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.block.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.ugi.sculk_depths.block.ModBlockEntities;
import net.ugi.sculk_depths.block.custom.entity.ZygrinFurnaceBlockEntity;
import org.jetbrains.annotations.Nullable;

public class ZygrinFurnaceBlock extends AbstractFurnaceBlock {
    public ZygrinFurnaceBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends AbstractFurnaceBlock> getCodec() {
        return null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ZygrinFurnaceBlockEntity(pos, state);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return BaseEntityBlock.validateTicker(type, ModBlockEntities.ZYGRIN_FURNACE_BLOCK_ENTITY, ZygrinFurnaceBlockEntity::tick);
    }

    @Override
    protected void openScreen(Level world, BlockPos pos, Player player) {
        BlockEntity blockEntity = world.getEntity(pos);
        if (blockEntity instanceof ZygrinFurnaceBlockEntity) {
            player.openMenu((MenuProvider) blockEntity);
            player.awardStat(Stats.INTERACT_WITH_FURNACE);
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (!state.get(DATA_ID_FUEL).booleanValue()) {
            return;
        }
        double d = (double)pos.getX() + 0.5;
        double e = pos.getY();
        double f = (double)pos.getZ() + 0.5;
        if (random.nextDouble() < 0.1) {
            world.playSound(d, e, f, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0f, 1.0f, false);
        }
        Direction direction = state.get(FACING);
        Direction.Axis orientation = direction.getAxis();
        double g = 0.52;
        double h = random.nextDouble() * 0.6 - 0.3;
        double i = orientation == Direction.Axis.X_ROT ? (double)direction.getOffsetX() * 0.52 : h;
        double j = random.nextDouble() * 6.0 / 16.0;
        double k = orientation == Direction.Axis.Z ? (double)direction.getOffsetZ() * 0.52 : h;
        world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
        world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0, 0.0, 0.0);
    }

    @Override
    protected void onStateReplaced(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {//fixes items not being dropped
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getEntity(pos);
            if (blockEntity instanceof ZygrinFurnaceBlockEntity) {
                if (world instanceof ServerLevel) {
                    Containers.spawn(world, pos, (ZygrinFurnaceBlockEntity)blockEntity);
                    ((ZygrinFurnaceBlockEntity)blockEntity).getRecipesToAwardAndPopExperience((ServerLevel)world, Vec3.ofCenter(pos));
                }

                super.onStateReplaced(state, world, pos, newState, moved);
                world.updateNeighbourForOutputSignal(pos, this);
            } else {
                super.onStateReplaced(state, world, pos, newState, moved);
            }
        }
    }


}
