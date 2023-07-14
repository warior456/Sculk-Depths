package net.ugi.sculk_depths.block.custom.ModCauldron;

import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ugi.sculk_depths.item.ModItems;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Mod;

import java.util.Map;

import static net.minecraft.block.LeveledCauldronBlock.LEVEL;


public class KryslumFlumrockCauldronBlock extends AbstractCauldronBlock {

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
        if(! (itemStack.getItem() == ModItems.QUAZARITH_PIECES || itemStack.getItem() == Items.ANCIENT_DEBRIS || itemStack.getItem() == Items.DIAMOND)) return cauldronBehavior.interact(state, world, pos, player, hand, itemStack);
        world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, SoundCategory.BLOCKS, 100 ,1);
        return ActionResult.SUCCESS;

    }

}
