package net.ugi.sculk_depths.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.ModCauldron.FlumrockCauldronBlock;
import net.ugi.sculk_depths.block.custom.ModCauldron.SporeFlumrockCauldronBlock;
import net.ugi.sculk_depths.particle.ModParticleTypes;
import net.ugi.sculk_depths.state.property.ModProperties;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiPredicate;


public class SporeBlock extends PenebriumShroomBlock {

    private static final VoxelShape DRIP_COLLISION_SHAPE = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);


    public SporeBlock(Settings settings) {
        super(settings);
    }


    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();

        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int l = 0; l < 14; ++l) {
            mutable.set(i + MathHelper.nextInt(random, -10, 10), j - random.nextInt(10), k + MathHelper.nextInt(random, -10, 10));
            BlockState blockState = world.getBlockState(mutable);
            if (blockState.isFullCube(world, mutable)) continue;
            //ParticleTypes.ASH
            world.addParticle(ModParticleTypes.PENEBRIUM_SPORES, (double) mutable.getX() + random.nextDouble(), (double) mutable.getY() + random.nextDouble(), (double) mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockPos pos2 = getCauldronPos(world, pos);
        if (pos2 == null) {
            return;
        }
        BlockState CauldronBlockstate = world.getBlockState(pos2);
        if (CauldronBlockstate == null) {
            return;
        }
        if (MathHelper.nextInt(random, 0, Math.abs(pos.getY() - pos2.getY()) + 1) >= 2) {
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
    private static BlockPos getCauldronPos(World world, BlockPos pos2) {
        BiPredicate<BlockPos, BlockState> biPredicate = (pos, state) -> SporeBlock.canDripThrough(world, pos, state);
        return SporeBlock.searchInDirection(world, pos2, Direction.DOWN.getDirection(), biPredicate, 11).orElse(null);
    }

    private static boolean canDripThrough(BlockView world, BlockPos pos, BlockState state) {
        if (state.isAir()) {
            return true;
        }
        if (state.isOpaqueFullCube(world, pos)) {
            return false;
        }
        if (!state.getFluidState().isEmpty()) {
            return false;
        }
        VoxelShape voxelShape = state.getCollisionShape(world, pos);
        return !VoxelShapes.matchesAnywhere(DRIP_COLLISION_SHAPE, voxelShape, BooleanBiFunction.AND);
    }

    private static Optional<BlockPos> searchInDirection(WorldAccess world, BlockPos pos, Direction.AxisDirection direction, BiPredicate<BlockPos, BlockState> continuePredicate, int range) {
        Direction direction2 = Direction.get(direction, Direction.Axis.Y);
        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int i = 1; i < range; ++i) {
            mutable.move(direction2);
            BlockState blockState = world.getBlockState(mutable);
            if (blockState.getBlock() instanceof FlumrockCauldronBlock || blockState.getBlock() instanceof SporeFlumrockCauldronBlock) {
                return Optional.of(mutable.toImmutable());
            }
            if (!world.isOutOfHeightLimit(mutable.getY()) && continuePredicate.test(mutable, blockState)) continue;
            return Optional.empty();
        }
        return Optional.empty();


    }
}
