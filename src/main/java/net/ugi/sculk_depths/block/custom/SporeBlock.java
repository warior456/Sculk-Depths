package net.ugi.sculk_depths.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.ModCauldron.FlumrockCauldronBlock;
import net.ugi.sculk_depths.block.custom.ModCauldron.SporeFlumrockCauldronBlock;


import net.ugi.sculk_depths.particle.ModParticleTypes;
import net.ugi.sculk_depths.state.property.ModProperties;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiPredicate;


public class SporeBlock extends ShroomBlock {

    private static final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);


    public SporeBlock(Settings settings) {
        super(settings);
    }


    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int l = 0; l < 14; ++l) {
            mutable.set(i + Mth.getInt(random, -10, 10), j - random.getInt(10), k + Mth.getInt(random, -10, 10));
            BlockState block = world.getBlockState(mutable);
            if (block.isCollisionShapeFullBlock(world, mutable)) continue;
            //ParticleTypes.ASH
            world.addParticle((ParticleOptions) ModParticleTypes.PENEBRIUM_SPORES, (double) mutable.getX() + random.nextDouble(), (double) mutable.getY() + random.nextDouble(), (double) mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        BlockPos pos2 = findFillableCauldronBelowStalactiteTip(world, pos);
        if (pos2 == null) {
            return;
        }
        BlockState CauldronBlockstate = world.getBlockState(pos2);
        if (CauldronBlockstate == null) {
            return;
        }
        if (Mth.getInt(random, 0, Math.abs(pos.getY() - pos2.getY()) + 1) >= 2) {
            return;
        }
        if (CauldronBlockstate.getBlock() instanceof FlumrockCauldronBlock) {
            BlockState newCauldronBlockState = ModBlocks.SPORE_FLUMROCK_CAULDRON.getDefaultState();
            world.setBlockState(pos2, newCauldronBlockState);
            return;
        }
        if (CauldronBlockstate.getBlock() instanceof SporeFlumrockCauldronBlock) {
            if (CauldronBlockstate.get(SporeFlumrockCauldronBlock.LEVEL) >= (ModProperties.SPORE_LEVEL.getValues().size())) {
                return;
            }
            BlockState newCauldronBlockState = CauldronBlockstate.with(SporeFlumrockCauldronBlock.LEVEL, CauldronBlockstate.get(SporeFlumrockCauldronBlock.LEVEL) + 1);
            world.setBlockState(pos2, newCauldronBlockState);
        }
    }

    @Nullable
    private static BlockPos findFillableCauldronBelowStalactiteTip(Level world, BlockPos pos2) {
        BiPredicate<BlockPos, BlockState> biPredicate = (pos, state) -> SporeBlock.canDripThrough(world, pos, state);
        return SporeBlock.findBlockVertical(world, pos2, Direction.MIN_Y.getDirection(), biPredicate, 11).orElse(null);
    }

    private static boolean canDripThrough(BlockGetter world, BlockPos pos, BlockState state) {
        if (state.isAir()) {
            return true;
        }
        if (state.isSolidRender(world, pos)) {
            return false;
        }
        if (!state.getFluidState().isEmpty()) {
            return false;
        }
        VoxelShape voxelShape = state.nonClimbableShape(world, pos);
        return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, voxelShape, BooleanOp.AND);
    }

    private static Optional<BlockPos> findBlockVertical(LevelAccessor world, BlockPos pos, Direction.AxisDirection direction, BiPredicate<BlockPos, BlockState> continuePredicate, int composite) {
        Direction direction2 = Direction.get(direction, Direction.Axis.Y_ROT);
        BlockPos.MutableBlockPos mutable = pos.mutable();
        for (int i = 1; i < composite; ++i) {
            mutable.move(direction2);
            BlockState block = world.getBlockState(mutable);
            if (block.getBlock() instanceof FlumrockCauldronBlock || block.getBlock() instanceof SporeFlumrockCauldronBlock) {
                return Optional.of(mutable.toImmutable());
            }
            if (!world.isOutsideBuildHeight(mutable.getY()) && continuePredicate.test(mutable, block)) continue;
            return Optional.allEmpty();
        }
        return Optional.allEmpty();


    }
}
