package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;

public class QeldryBerryBush extends PlantBlock implements Fertilizable {
    public static final MapCodec<QeldryBerryBush> CODEC = createCodec(QeldryBerryBush::new);
    public static final int MAX_AGE = 2;
    public static final IntProperty AGE;
    private static final VoxelShape SMALL_SHAPE;
    private static final VoxelShape LARGE_SHAPE;

    public MapCodec<QeldryBerryBush> getCodec() {
        return CODEC;
    }

    public QeldryBerryBush(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(AGE, 0));
    }

    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return new ItemStack(ModItems.QELBERRIES);
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if ((Integer)state.get(AGE) == 0) {
            return SMALL_SHAPE;
        } else {
            return LARGE_SHAPE;
        }
    }

    protected boolean hasRandomTicks(BlockState state) {
        return (Integer)state.get(AGE) < 2;
    }

    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = (Integer)state.get(AGE);
        if (i < 2 && random.nextInt(5) == 0 /*&& world.getBaseLightLevel(pos.up(), 0) >= 9*/) {
            BlockState blockState = (BlockState)state.with(AGE, i + 1);
            world.setBlockState(pos, blockState, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
        }

    }

    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
    }

    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = (Integer)state.get(AGE);
        boolean bl = i == 2;
        return !bl && stack.isOf(Items.BONE_MEAL) ? ItemActionResult.SKIP_DEFAULT_BLOCK_INTERACTION : super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        int i = (Integer)state.get(AGE);
        boolean bl = i == 2;
        if (i > 1) {
            dropStack(world, pos, new ItemStack(ModItems.QELBERRIES, 1));
            world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            BlockState blockState = (BlockState)state.with(AGE, 1);
            world.setBlockState(pos, blockState, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
            return ActionResult.success(world.isClient);
        } else {
            return super.onUse(state, world, pos, player, hit);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AGE});
    }

    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return (Integer)state.get(AGE) < 2;
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = Math.min(2, (Integer)state.get(AGE) + 1);
        world.setBlockState(pos, (BlockState)state.with(AGE, i), 2);
    }

    static {
        AGE = Properties.AGE_2;
        SMALL_SHAPE = Block.createCuboidShape((double)5.0F, (double)0.0F, (double)5.0F, (double)11.0F, (double)6.0F, (double)11.0F);
        LARGE_SHAPE = Block.createCuboidShape((double)3.0F, (double)0.0F, (double)3.0F, (double)13.0F, (double)8.0F, (double)13.0F);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT)  || floor.isOf(Blocks.FARMLAND) || floor.isOf(ModBlocks.CRUMBLING_DIRT);
    }
}
