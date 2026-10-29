package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.gameevent.GameEvent;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;

public class QeldryBerryBush extends PlantBlock implements Fertilizable {
    public static final MapCodec<QeldryBerryBush> CODEC = createCodec(QeldryBerryBush::new);
    public static final int DEAD_AGE = 2;
    public static final IntegerProperty AGE;
    private static final VoxelShape SMALL_SHAPE;
    private static final VoxelShape LARGE_SHAPE;

    public MapCodec<QeldryBerryBush> getCodec() {
        return CODEC;
    }

    public QeldryBerryBush(AbstractBlock.Settings settings) {
        super(settings);
        this.registerDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(AGE, 0));
    }

    public ItemStack getPickStack(LevelReader world, BlockPos pos, BlockState state) {
        return new ItemStack(ModItems.QELBERRIES);
    }

    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        if ((Integer)state.get(AGE) == 0) {
            return SMALL_SHAPE;
        } else {
            return LARGE_SHAPE;
        }
    }

    protected boolean isRandomlyTicking(BlockState state) {
        return (Integer)state.get(AGE) < 2;
    }

    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int i = (Integer)state.get(AGE);
        if (i < 2 && random.getInt(5) == 0 /*&& world.getBaseLightLevel(pos.up(), 0) >= 9*/) {
            BlockState block = (BlockState)state.with(AGE, i + 1);
            world.setBlockState(pos, block, 2);
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(block));
        }

    }

    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
    }

    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        int i = (Integer)state.get(AGE);
        boolean bl = i == 2;
        return !bl && stack.isOf(Items.BONE_MEAL) ? ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION : super.useItemOn(stack, state, world, pos, player, hand, hitResult);
    }

    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hitResult) {
        int i = (Integer)state.get(AGE);
        boolean bl = i == 2;
        if (i > 1) {
            dropStack(world, pos, new ItemStack(ModItems.QELBERRIES, 1));
            world.playSound((Player)null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + world.random.getFloat() * 0.4F);
            BlockState block = (BlockState)state.with(AGE, 1);
            world.setBlockState(pos, block, 2);
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, block));
            return InteractionResult.success(world.isClientSide);
        } else {
            return super.useWithoutItem(state, world, pos, player, hitResult);
        }
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AGE});
    }

    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return (Integer)state.get(AGE) < 2;
    }

    public boolean canGrow(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        int i = Math.minValue(2, (Integer)state.get(AGE) + 1);
        world.setBlockState(pos, (BlockState)state.with(AGE, i), 2);
    }

    static {
        AGE = BlockStateProperties.AGE_2;
        SMALL_SHAPE = Block.box((double)5.0F, (double)0.0F, (double)5.0F, (double)11.0F, (double)6.0F, (double)11.0F);
        LARGE_SHAPE = Block.box((double)3.0F, (double)0.0F, (double)3.0F, (double)13.0F, (double)8.0F, (double)13.0F);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pointingUp, BlockGetter world, BlockPos pos) {
        return pointingUp.is(BlockTags.DIRT)  || pointingUp.isOf(Blocks.FARMLAND) || pointingUp.isOf(ModBlocks.CRUMBLING_DIRT);
    }
}
