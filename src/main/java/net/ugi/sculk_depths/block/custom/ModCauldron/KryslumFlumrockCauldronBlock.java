package net.ugi.sculk_depths.block.custom.ModCauldron;

import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.ugi.sculk_depths.block.entity.CauldronBlockEntity;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.recipe.FlumRockCauldronRecipe;

import java.util.Map;
import java.util.Optional;

import static net.minecraft.block.LeveledCauldronBlock.LEVEL;


public class KryslumFlumrockCauldronBlock extends AbstractCauldronBlock implements BlockEntityProvider{

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
        builder.add(LEVEL);
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        CauldronBehavior cauldronBehavior = this.behaviorMap.get(itemStack.getItem());

        ActionResult t = ActionResult.FAIL;
        if((itemStack.getItem() == ModItems.QUAZARITH_PIECES || itemStack.getItem() == Items.ANCIENT_DEBRIS || itemStack.getItem() == Items.DIAMOND)){
            //additemtoinventory
            t = ActionResult.CONSUME;
        }

        Optional<FlumRockCauldronRecipe> match = world.getRecipeManager()
                .getFirstMatch(FlumRockCauldronRecipe.Type.INSTANCE, (Inventory) world.getBlockEntity(pos) , world);
        System.out.println(match);
        if (match.isPresent()) {
            // Give the player the item and remove from what he has. Make sure to copy the ItemStack to not ruin it!
            player.getInventory().offerOrDrop(match.get().getOutput(DynamicRegistryManager.EMPTY).copy());
            return ActionResult.SUCCESS;
        }
        return t;

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