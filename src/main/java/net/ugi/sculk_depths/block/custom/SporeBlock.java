package net.ugi.sculk_depths.block.custom;

import net.minecraft.world.level.block.MultifaceBlock;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.ModCauldron.FlumrockCauldronBlock;
import net.ugi.sculk_depths.block.custom.ModCauldron.SporeFlumrockCauldronBlock;
import net.ugi.sculk_depths.particle.ModParticleTypes;
import net.ugi.sculk_depths.state.property.ModProperties;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiPredicate;

public class SporeBlock extends ShroomBlock {
    public static final MapCodec<SporeBlock> CODEC = simpleCodec(SporeBlock::new);

    private static final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);

    @Override
    public MapCodec<? extends net.minecraft.world.level.block.TransparentBlock> codec() {
        return CODEC;
    }

    public SporeBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int l = 0; l < 14; ++l) {
            mutable.set(i + Mth.nextInt(random, -10, 10), j - random.nextInt(10), k + Mth.nextInt(random, -10, 10));
            BlockState block = level.getBlockState(mutable);
            if (block.isCollisionShapeFullBlock(level, mutable)) continue;
            //ParticleTypes.ASH
            level.addParticle((ParticleOptions) ModParticleTypes.PENEBRIUM_SPORES, (double) mutable.getX() + random.nextDouble(), (double) mutable.getY() + random.nextDouble(), (double) mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos pos2 = findFillableCauldronBelowStalactiteTip(level, pos);
        if (pos2 == null) {
            return;
        }
        BlockState cauldronBlockstate = level.getBlockState(pos2);
        if (cauldronBlockstate == null) {
            return;
        }
        if (Mth.nextInt(random, 0, Math.abs(pos.getY() - pos2.getY()) + 1) >= 2) {
            return;
        }
        if (cauldronBlockstate.getBlock() instanceof FlumrockCauldronBlock) {
            BlockState newCauldronBlockState = ModBlocks.SPORE_FLUMROCK_CAULDRON.defaultBlockState();
            level.setBlock(pos2, newCauldronBlockState, Block.UPDATE_ALL);
            return;
        }
        if (cauldronBlockstate.getBlock() instanceof SporeFlumrockCauldronBlock) {
            if (cauldronBlockstate.getValue(SporeFlumrockCauldronBlock.LEVEL) >= (ModProperties.SPORE_LEVEL.getPossibleValues().size())) {
                return;
            }
            BlockState newCauldronBlockState = cauldronBlockstate.setValue(SporeFlumrockCauldronBlock.LEVEL, cauldronBlockstate.getValue(SporeFlumrockCauldronBlock.LEVEL) + 1);
            level.setBlock(pos2, newCauldronBlockState, Block.UPDATE_ALL);
        }
    }

    @Nullable
    private static BlockPos findFillableCauldronBelowStalactiteTip(Level level, BlockPos pos2) {
        BiPredicate<BlockPos, BlockState> biPredicate = (pos, state) -> SporeBlock.canDripThrough(level, pos, state);
        return SporeBlock.findBlockVertical(level, pos2, Direction.DOWN.getAxisDirection(), biPredicate, 11).orElse(null);
    }

    private static boolean canDripThrough(BlockGetter level, BlockPos pos, BlockState state) {
        if (state.isAir()) {
            return true;
        }
        if (state.isSolidRender(level, pos)) {
            return false;
        }
        if (!state.getFluidState().isEmpty()) {
            return false;
        }
        VoxelShape voxelShape = state.getCollisionShape(level, pos);
        return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, voxelShape, BooleanOp.AND);
    }

    private static Optional<BlockPos> findBlockVertical(LevelAccessor level, BlockPos pos, Direction.AxisDirection direction, BiPredicate<BlockPos, BlockState> continuePredicate, int composite) {
        Direction direction2 = Direction.fromAxisAndDirection(Direction.Axis.Y, direction);
        BlockPos.MutableBlockPos mutable = pos.mutable();
        for (int i = 1; i < composite; ++i) {
            mutable.move(direction2);
            BlockState block = level.getBlockState(mutable);
            if (block.getBlock() instanceof FlumrockCauldronBlock || block.getBlock() instanceof SporeFlumrockCauldronBlock) {
                return Optional.of(mutable.immutable());
            }
            if (!level.isOutsideBuildHeight(mutable.getY()) && continuePredicate.test(mutable, block)) continue;
            return Optional.empty();
        }
        return Optional.empty();
    }
}
