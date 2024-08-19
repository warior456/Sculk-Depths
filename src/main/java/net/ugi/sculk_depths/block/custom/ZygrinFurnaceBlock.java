package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
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
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ZygrinFurnaceBlockEntity(pos, state);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return BlockWithEntity.validateTicker(type, ModBlockEntities.ZYGRIN_FURNACE_BLOCK_ENTITY, ZygrinFurnaceBlockEntity::tick);
    }

    @Override
    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ZygrinFurnaceBlockEntity) {
            player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
            player.incrementStat(Stats.INTERACT_WITH_FURNACE);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(LIT).booleanValue()) {
            return;
        }
        double d = (double)pos.getX() + 0.5;
        double e = pos.getY();
        double f = (double)pos.getZ() + 0.5;
        if (random.nextDouble() < 0.1) {
            world.playSound(d, e, f, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
        }
        Direction direction = state.get(FACING);
        Direction.Axis axis = direction.getAxis();
        double g = 0.52;
        double h = random.nextDouble() * 0.6 - 0.3;
        double i = axis == Direction.Axis.X ? (double)direction.getOffsetX() * 0.52 : h;
        double j = random.nextDouble() * 6.0 / 16.0;
        double k = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * 0.52 : h;
        world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
        world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0, 0.0, 0.0);
    }

}
