package net.ugi.sculk_depths.block.custom.ModCauldron;

import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;

import java.util.Map;

import static net.minecraft.block.LeveledCauldronBlock.LEVEL;
import static net.ugi.sculk_depths.state.property.ModProperties.DIAMOND_LEVEL;
import static net.ugi.sculk_depths.state.property.ModProperties.QUAZARITH_LEVEL;


public interface ModCauldronBehavior {

    public static final Map<Item, CauldronBehavior> EMPTY_FLUMROCK_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
    public static final Map<Item, CauldronBehavior> KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
    public static final CauldronBehavior FILL_WITH_KRYSLUM = (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(world, pos, player, hand, stack, ModBlocks.KRYSLUM_FLUMROCK_CAULDRON.getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY);

    public static void registerBehavior() {

        registerBucketBehavior(EMPTY_FLUMROCK_CAULDRON_BEHAVIOR);

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient) {
                Item item = stack.getItem();
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(ModItems.KRYSLUM_BUCKET)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                decrementFluidLevel(state, world, pos);
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
            }
            return ActionResult.success(world.isClient);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.put(ModItems.KRYSLUM_BUCKET, (state, world, pos, player, hand, stack) -> {
            if (state.get(LEVEL) == 3) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BUCKET)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                world.setBlockState(pos, state.cycle(LEVEL));
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return ActionResult.success(world.isClient);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.put(ModItems.QUAZARITH_PIECES, (state, world, pos, player, hand, stack) -> {
            if (state.get(QUAZARITH_LEVEL) == 64) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                if (!player.isCreative()){
                    ItemStack heldItem = player.getMainHandStack().getItem() == ModItems.QUAZARITH_PIECES ?
                            player.getMainHandStack() : player.getOffHandStack();
                    heldItem.decrement(1);}
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                world.setBlockState(pos, state.cycle(QUAZARITH_LEVEL));
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return ActionResult.success(world.isClient);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.put(Items.DIAMOND, (state, world, pos, player, hand, stack) -> {
            if (state.get(DIAMOND_LEVEL) == 64) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                if (!player.isCreative()){
                    ItemStack heldItem = player.getMainHandStack().getItem() == Items.DIAMOND ?
                            player.getMainHandStack() : player.getOffHandStack();
                    heldItem.decrement(1);}
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            world.setBlockState(pos, state.cycle(DIAMOND_LEVEL));
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return ActionResult.success(world.isClient);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.put(ModItems.QUAZARITH_INGOT, (state, world, pos, player, hand, stack) -> {
            if (state.get(QUAZARITH_LEVEL) > 60 || state.get(DIAMOND_LEVEL) > 60) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                if (!player.isCreative()){
                    ItemStack heldItem = player.getMainHandStack().getItem() == Items.DIAMOND ?
                            player.getMainHandStack() : player.getOffHandStack();
                    heldItem.decrement(1);}
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));

                int i = state.get(QUAZARITH_LEVEL) + 4;
                int j = state.get(DIAMOND_LEVEL) + 4;
                BlockState blockState = state.with(QUAZARITH_LEVEL, i).with(DIAMOND_LEVEL, j);
                world.setBlockState(pos, blockState);

                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return ActionResult.success(world.isClient);
        });

    }

    public static void decrementFluidLevel(BlockState state, World world, BlockPos pos) {
        int i = state.get(LEVEL) - 1;
        BlockState blockState = i == 0 ? ModBlocks.FLUMROCK_CAULDRON.getDefaultState() : state.with(LEVEL, i);
        world.setBlockState(pos, blockState);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
    }

    public static void registerBucketBehavior(Map<Item, CauldronBehavior> behavior) {
        behavior.put(ModItems.KRYSLUM_BUCKET, FILL_WITH_KRYSLUM);

    }
}