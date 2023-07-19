package net.ugi.sculk_depths.block.custom.ModCauldron;

import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.ugi.sculk_depths.block.entity.CauldronBlockEntity;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.state.property.ModProperties;

import java.util.Map;

import static net.minecraft.block.LeveledCauldronBlock.LEVEL;
import static net.ugi.sculk_depths.state.property.ModProperties.DIAMOND_LEVEL;
import static net.ugi.sculk_depths.state.property.ModProperties.QUAZARITH_LEVEL;


public class KryslumFlumrockCauldronBlock extends AbstractCauldronBlock{

    public static final IntProperty QUAZARITH = ModProperties.QUAZARITH_LEVEL;
    public static final IntProperty DIAMOND = ModProperties.DIAMOND_LEVEL;


    private final Map<Item, CauldronBehavior> behaviorMap;

    public KryslumFlumrockCauldronBlock(AbstractBlock.Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
        super(settings, ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR);
        this.behaviorMap = behaviorMap;
        this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 1));
    }


    @Override
    public boolean isFull(BlockState state) {
        return state.get(LEVEL) == 3;
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
        builder.add(LEVEL).add(QUAZARITH).add(DIAMOND);

    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getMainHandStack();
        CauldronBehavior cauldronBehavior = this.behaviorMap.get(itemStack.getItem());
        if(itemStack.getItem() == ModItems.KRYSLUM_BUCKET
                || itemStack.getItem() == Items.BUCKET
                || itemStack.getItem() == ModItems.QUAZARITH_PIECES
                || itemStack.getItem() == Items.DIAMOND
                || itemStack.getItem() == ModItems.QUAZARITH_INGOT
        ) {
            return cauldronBehavior.interact(state, world, pos, player, hand, itemStack);
        }

        if(itemStack.getItem() == Items.NETHERITE_HELMET && state.get(LEVEL) >= 3 && state.get(DIAMOND) >= 5 && state.get(QUAZARITH) >= 5){
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_HELMET);
            RemoveUsedResources(state, world, pos, 5,5, 2);
            return UpgradeItem(outputItem, player);
        }

        if(itemStack.getItem() == Items.NETHERITE_CHESTPLATE && state.get(LEVEL) >= 3 && state.get(DIAMOND) >= 8 && state.get(QUAZARITH) >= 8){
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_CHESTPLATE);
            RemoveUsedResources(state, world, pos, 8,8,2);
            return UpgradeItem(outputItem, player);
        }
        if(itemStack.getItem() == Items.NETHERITE_LEGGINGS && state.get(LEVEL) >= 3 && state.get(DIAMOND) >= 7 && state.get(QUAZARITH) >= 7){
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_LEGGINGS);
            RemoveUsedResources(state, world, pos, 7,7,2);
            return UpgradeItem(outputItem, player);
        }
        if(itemStack.getItem() == Items.NETHERITE_BOOTS && state.get(LEVEL) >= 3 && state.get(DIAMOND) >= 4 && state.get(QUAZARITH) >= 4){
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_BOOTS);
            RemoveUsedResources(state, world, pos, 4,4,2);
            return UpgradeItem(outputItem, player);
        }
        if(itemStack.getItem() == Items.NETHERITE_AXE && state.get(LEVEL) >= 3 && state.get(DIAMOND) >= 3 && state.get(QUAZARITH) >= 3){
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_AXE);
            RemoveUsedResources(state, world, pos, 3,3,2);
            return UpgradeItem(outputItem, player);
        }
        if(itemStack.getItem() == Items.NETHERITE_PICKAXE && state.get(LEVEL) >= 3 && state.get(DIAMOND) >= 3 && state.get(QUAZARITH) >= 3){
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_PICKAXE);
            RemoveUsedResources(state, world, pos, 3,3, 2);
            return UpgradeItem(outputItem, player);
        }
        if(itemStack.getItem() == Items.NETHERITE_SHOVEL && state.get(LEVEL) >= 2 && state.get(DIAMOND) >= 1 && state.get(QUAZARITH) >= 1){
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_SHOVEL);
            RemoveUsedResources(state, world, pos, 1,1, 1);
            return UpgradeItem(outputItem, player);
        }
        if(itemStack.getItem() == Items.NETHERITE_SWORD && state.get(LEVEL) >= 3 && state.get(DIAMOND) >= 2 && state.get(QUAZARITH) >= 2){
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_SWORD);
            RemoveUsedResources(state, world, pos, 2,2, 2);
            return UpgradeItem(outputItem, player);
        }
        if(itemStack.getItem() == Items.NETHERITE_HOE && state.get(LEVEL) >= 3 && state.get(DIAMOND) >= 2 && state.get(QUAZARITH) >= 2){
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_HOE);
            RemoveUsedResources(state, world, pos, 2,2, 2);
            return UpgradeItem(outputItem, player);
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

    public void RemoveUsedResources(BlockState state, World world, BlockPos pos, int quazarith_pieces, int diamonds, int kryslum){
        int i = state.get(QUAZARITH_LEVEL) - quazarith_pieces;
        int j = state.get(DIAMOND_LEVEL) - diamonds;
        int k = state.get(LEVEL) - kryslum;
        BlockState blockState = state.with(QUAZARITH_LEVEL, i).with(DIAMOND_LEVEL, j).with(LEVEL, k);
        world.setBlockState(pos, blockState);

    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock() && moved == false) {
            DefaultedList<ItemStack> stacks = DefaultedList.ofSize(2, ItemStack.EMPTY);
            stacks.set( 0 , new ItemStack(ModItems.QUAZARITH_PIECES, state.get(QUAZARITH)));
            stacks.set(1 , new ItemStack(Items.DIAMOND, state.get(DIAMOND)));
            ItemScatterer.spawn(world, pos, stacks);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));

        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
