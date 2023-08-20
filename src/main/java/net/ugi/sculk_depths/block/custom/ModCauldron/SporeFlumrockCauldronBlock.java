package net.ugi.sculk_depths.block.custom.ModCauldron;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.state.property.ModProperties;

import java.util.Map;

import static net.ugi.sculk_depths.state.property.ModProperties.*;


public class SporeFlumrockCauldronBlock extends AbstractCauldronBlock{

    public static final IntProperty LEVEL = ModProperties.SPORE_LEVEL;

    public static final EnumProperty CRYSTAL = ModProperties.CRYSTAL_TYPE;

    public static final IntProperty CRUX = ModProperties.CRUX_LEVEL;


    private final Map<Item, CauldronBehavior> behaviorMap;

    public SporeFlumrockCauldronBlock(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
        super(settings, ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR);
        this.behaviorMap = behaviorMap;
        this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 1));
    }

    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(ModBlocks.FLUMROCK_CAULDRON);
    }

    private static final VoxelShape RAYCAST_SHAPE = AbstractCauldronBlock.createCuboidShape(1.5, 2.0, 1.5, 14.5, 16.0, 14.5);
    protected static final VoxelShape OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),
            VoxelShapes.union(
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 2.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 14.0, 16.0, 1.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 0.0, 2.0, 1.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(14.0, 0.0, 0.0, 16.0, 1.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(4.0, 0.0, 2.0, 12.0, 1.0, 14.0),
                    AbstractCauldronBlock.createCuboidShape(2.0, 0.0, 4.0, 14.0, 1.0, 12.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 1.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 15.0, 16.0, 2.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 0.0, 1.0, 2.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(15.0, 0.0, 0.0, 16.0, 2.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(1.0, 2.0, 1.0, 15.0, 13.0, 15.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 13.0, 0.0, 16.0, 15.0, 1.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 13.0, 15.0, 16.0, 15.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(0.0, 13.0, 0.0, 1.0, 15.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(15.0, 13.0, 0.0, 16.0, 15.0, 16.0),
                    AbstractCauldronBlock.createCuboidShape(1.0, 15.0, 1.0, 15.0, 16.0, 15.0),
                    RAYCAST_SHAPE), BooleanBiFunction.ONLY_FIRST);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return RAYCAST_SHAPE;
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.get(LEVEL) == 12;
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return (6.0 + (double)state.get(LEVEL).intValue() * 3.0) / 16.0;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient && entity.isOnFire() && this.isEntityTouchingFluid(state, pos, entity)) {

        }
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(LEVEL);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL).add(CRYSTAL).add(CRUX);

    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getMainHandStack();
        CauldronBehavior cauldronBehavior = this.behaviorMap.get(itemStack.getItem());
        if(itemStack.getItem() == ModItems.PENEBRIUM_SHINE_SHROOM_SPORE_BUCKET
                || itemStack.getItem() == ModItems.CRUX
                || itemStack.getItem() == ModItems.QUAZARITH_INGOT
                || itemStack.getItem() == Items.BUCKET
        ) {
            return cauldronBehavior.interact(state, world, pos, player, hand, itemStack);
        }

        return ActionResult.FAIL;

    }

    public ActionResult UpgradeItem(ItemStack outputItem, PlayerEntity player){
        NbtCompound nbtCompound = player.getMainHandStack().getNbt();
        if (nbtCompound != null) {
            outputItem.setNbt(nbtCompound.copy());
        }
        player.setStackInHand(Hand.MAIN_HAND, outputItem);
        return ActionResult.SUCCESS;
    }

    public void RemoveUsedResources(BlockState state, World world, BlockPos pos, int quazarith_pieces, int cruxs, int kryslum){
        int i = state.get(QUAZARITH_LEVEL) - quazarith_pieces;
        int j = state.get(CRUX_LEVEL) - cruxs;
        int k = state.get(LEVEL) - kryslum;

        BlockState blockState1 = state.with(QUAZARITH_LEVEL, i).with(CRUX_LEVEL, j).with(LEVEL, k);
        world.setBlockState(pos, blockState1);

        if(k == 0) {
            BlockState blockState2 = ModBlocks.FLUMROCK_CAULDRON.getDefaultState();
            world.setBlockState(pos, blockState2);
        }

    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock() && moved == false) {
            DefaultedList<ItemStack> stacks = DefaultedList.ofSize(2, ItemStack.EMPTY);
            stacks.set(0 , new ItemStack(ModItems.CRUX, state.get(CRUX)));
            ItemScatterer.spawn(world, pos, stacks);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));

        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

}
