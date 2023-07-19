package net.ugi.sculk_depths.block.custom.ModCauldron;

import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ugi.sculk_depths.block.entity.CauldronBlockEntity;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.state.property.ModProperties;


import java.util.Map;
import java.util.Optional;

import static net.minecraft.block.LeveledCauldronBlock.LEVEL;
import static net.ugi.sculk_depths.state.property.ModProperties.DIAMOND_LEVEL;


public class KryslumFlumrockCauldronBlock extends AbstractCauldronBlock implements BlockEntityProvider{

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
        ItemStack itemStack = player.getStackInHand(hand);
        CauldronBehavior cauldronBehavior = this.behaviorMap.get(itemStack.getItem());
        if(itemStack.getItem() == ModItems.KRYSLUM_BUCKET
                || itemStack.getItem() == Items.BUCKET
                || itemStack.getItem() == ModItems.QUAZARITH_PIECES
                || itemStack.getItem() == Items.DIAMOND
                || itemStack.getItem() == ModItems.QUAZARITH_INGOT
        ) {
            return cauldronBehavior.interact(state, world, pos, player, hand, itemStack);
        }

        if(itemStack.getItem() == Items.NETHERITE_HELMET && state.get(LEVEL) == 3 && state.get(DIAMOND) == 5 && state.get(QUAZARITH) == 5){
            player.setStackInHand(hand, ItemUsage.exchangeStack(player.getStackInHand(Hand.MAIN_HAND), player, new ItemStack(ModItems.KRYSLUM_BUCKET)));


        }
        if(itemStack.getItem() == Items.NETHERITE_CHESTPLATE && state.get(LEVEL) == 3 && state.get(DIAMOND) == 8 && state.get(QUAZARITH) == 8){

        }
        if(itemStack.getItem() == Items.NETHERITE_LEGGINGS && state.get(LEVEL) == 3 && state.get(DIAMOND) == 7 && state.get(QUAZARITH) == 7){

        }
        if(itemStack.getItem() == Items.NETHERITE_BOOTS && state.get(LEVEL) == 3 && state.get(DIAMOND) == 4 && state.get(QUAZARITH) == 4){

        }
        if(itemStack.getItem() == Items.NETHERITE_AXE && state.get(LEVEL) == 3 && state.get(DIAMOND) == 3 && state.get(QUAZARITH) == 3){

        }
        if(itemStack.getItem() == Items.NETHERITE_PICKAXE && state.get(LEVEL) == 3 && state.get(DIAMOND) == 3 && state.get(QUAZARITH) == 3){

        }
        if(itemStack.getItem() == Items.NETHERITE_SHOVEL && state.get(LEVEL) == 3 && state.get(DIAMOND) == 1 && state.get(QUAZARITH) == 1){

        }
        if(itemStack.getItem() == Items.NETHERITE_SWORD && state.get(LEVEL) == 3 && state.get(DIAMOND) == 2 && state.get(QUAZARITH) == 2){

        }
        if(itemStack.getItem() == Items.NETHERITE_HOE && state.get(LEVEL) == 3 && state.get(DIAMOND) == 2 && state.get(QUAZARITH) == 2){

        }

        return ActionResult.FAIL;

    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CauldronBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CauldronBlockEntity) {
                ItemScatterer.spawn(world, pos, (CauldronBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
}
