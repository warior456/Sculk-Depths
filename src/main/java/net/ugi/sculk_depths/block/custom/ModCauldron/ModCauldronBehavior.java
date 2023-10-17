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
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.state.property.ModProperties;

import java.util.Map;

import static net.ugi.sculk_depths.state.property.ModProperties.CRUX_LEVEL;
import static net.ugi.sculk_depths.state.property.ModProperties.QUAZARITH_LEVEL;


public interface ModCauldronBehavior {

    public static final IntProperty KRYSLUM_LEVEL = ModProperties.KRYSLUM_LEVEL;
    public static final IntProperty SPORE_LEVEL = ModProperties.SPORE_LEVEL;

    public static final Map<Item, CauldronBehavior> EMPTY_FLUMROCK_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
    public static final Map<Item, CauldronBehavior> KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();

    public static final Map<Item, CauldronBehavior> SPORE_FLUMROCK_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
    public static final CauldronBehavior FILL_WITH_KRYSLUM = (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(world, pos, player, hand, stack, ModBlocks.KRYSLUM_FLUMROCK_CAULDRON.getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY);
    public static final CauldronBehavior FILL_WITH_SPORE = (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(world, pos, player, hand, stack, ModBlocks.SPORE_FLUMROCK_CAULDRON.getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY);

    public static void registerBehavior() {

        registerBucketBehavior(EMPTY_FLUMROCK_CAULDRON_BEHAVIOR);

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient) {
                Item item = stack.getItem();
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(ModItems.KRYSLUM_BUCKET)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                decrementFluidLevel(KRYSLUM_LEVEL, state, world, pos);
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ActionResult.success(world.isClient);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.put(ModItems.KRYSLUM_BUCKET, (state, world, pos, player, hand, stack) -> {
            if (state.get(KRYSLUM_LEVEL) == 12) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BUCKET)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                world.setBlockState(pos, state.cycle(KRYSLUM_LEVEL));
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ActionResult.success(world.isClient);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.put(ModItems.QUAZARITH_PIECES, (state, world, pos, player, hand, stack) -> {
            if (state.get(QUAZARITH_LEVEL) == 12) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                if (!player.isCreative()) {
                    ItemStack heldItem = player.getMainHandStack().getItem() == ModItems.QUAZARITH_PIECES ?
                            player.getMainHandStack() : player.getOffHandStack();
                    heldItem.decrement(1);
                }
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                world.setBlockState(pos, state.cycle(QUAZARITH_LEVEL));
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ActionResult.success(world.isClient);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.put(ModItems.CRUX, (state, world, pos, player, hand, stack) -> {
            if (state.get(CRUX_LEVEL) == 12) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                if (!player.isCreative()) {
                    ItemStack heldItem = player.getMainHandStack().getItem() == ModItems.CRUX ?
                            player.getMainHandStack() : player.getOffHandStack();
                    heldItem.decrement(1);
                }
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                world.setBlockState(pos, state.cycle(CRUX_LEVEL));
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ActionResult.success(world.isClient);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.put(ModItems.QUAZARITH_INGOT, (state, world, pos, player, hand, stack) -> {
            if (state.get(QUAZARITH_LEVEL) > 8 || state.get(CRUX_LEVEL) > 8) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                if (!player.isCreative()) {
                    ItemStack heldItem = player.getMainHandStack().getItem() == ModItems.CRUX ?
                            player.getMainHandStack() : player.getOffHandStack();
                    heldItem.decrement(1);
                }
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));

                int i = state.get(QUAZARITH_LEVEL) + SculkDepths.CONFIG.quazarith_ingot_quazarith_pieces_cost;
                int j = state.get(CRUX_LEVEL) + SculkDepths.CONFIG.quazarith_ingot_crux_cost;
                BlockState blockState = state.with(QUAZARITH_LEVEL, i).with(CRUX_LEVEL, j);
                world.setBlockState(pos, blockState);

                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }


            return ActionResult.success(world.isClient);
        });


        SPORE_FLUMROCK_CAULDRON_BEHAVIOR.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient) {
                Item item = stack.getItem();
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(ModItems.PENEBRIUM_SHINE_SHROOM_SPORE_BUCKET)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                decrementFluidLevel(SPORE_LEVEL, state, world, pos);
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ActionResult.success(world.isClient);
        });

        SPORE_FLUMROCK_CAULDRON_BEHAVIOR.put(ModItems.PENEBRIUM_SHINE_SHROOM_SPORE_BUCKET, (state, world, pos, player, hand, stack) -> {
            if (state.get(SPORE_LEVEL) == 12) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BUCKET)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                world.setBlockState(pos, state.cycle(SPORE_LEVEL));
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ActionResult.success(world.isClient);
        });


        SPORE_FLUMROCK_CAULDRON_BEHAVIOR.put(ModItems.CRUX, (state, world, pos, player, hand, stack) -> {
            if (state.get(CRUX_LEVEL) == 12) {
                return ActionResult.PASS;
            }
            if (!world.isClient) {
                if (!player.isCreative()) {
                    ItemStack heldItem = player.getMainHandStack().getItem() == ModItems.CRUX ?
                            player.getMainHandStack() : player.getOffHandStack();
                    heldItem.decrement(1);
                }
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                world.setBlockState(pos, state.cycle(CRUX_LEVEL));
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ActionResult.success(world.isClient);
        });

    }

    public static void decrementFluidLevel(IntProperty levelName, BlockState state, World world, BlockPos pos) {
        int i = state.get(levelName) - 1;
        BlockState blockState = i == 0 ? ModBlocks.FLUMROCK_CAULDRON.getDefaultState() : state.with(levelName, i);
        world.setBlockState(pos, blockState);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
    }

    public static void registerBucketBehavior(Map<Item, CauldronBehavior> behavior) {
        behavior.put(ModItems.KRYSLUM_BUCKET, FILL_WITH_KRYSLUM);
        behavior.put(ModItems.PENEBRIUM_SHINE_SHROOM_SPORE_BUCKET, FILL_WITH_SPORE);

    }
}