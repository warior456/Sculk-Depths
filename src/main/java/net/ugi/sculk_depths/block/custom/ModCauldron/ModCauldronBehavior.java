package net.ugi.sculk_depths.block.custom.ModCauldron;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.state.property.ModProperties;

import java.util.Map;

import static net.ugi.sculk_depths.state.property.ModProperties.CRUX_LEVEL;
import static net.ugi.sculk_depths.state.property.ModProperties.QUAZARITH_LEVEL;


public interface ModCauldronBehavior {

    public static final IntegerProperty KRYSLUM_LEVEL = ModProperties.KRYSLUM_LEVEL;
    public static final IntegerProperty SPORE_LEVEL = ModProperties.SPORE_LEVEL;

    public static final CauldronInteraction.InteractionMap EMPTY_FLUMROCK_CAULDRON_BEHAVIOR = CauldronInteraction.createMap("empty_flumrock_cauldron");
    public static final CauldronInteraction.InteractionMap KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR = CauldronInteraction.createMap("kryslum_flumrock_cauldron");

    public static final CauldronInteraction.InteractionMap SPORE_FLUMROCK_CAULDRON_BEHAVIOR = CauldronInteraction.createMap("spore_flumrock_cauldron");
    public static final CauldronInteraction FILL_WITH_KRYSLUM = (state, world, pos, player, hand, stack) -> CauldronInteraction.emptyBucket(world, pos, player, hand, stack, ModBlocks.KRYSLUM_FLUMROCK_CAULDRON.getDefaultState(), SoundEvents.BUCKET_EMPTY);
    public static final CauldronInteraction FILL_WITH_SPORE = (state, world, pos, player, hand, stack) -> CauldronInteraction.emptyBucket(world, pos, player, hand, stack, ModBlocks.SPORE_FLUMROCK_CAULDRON.getDefaultState(), SoundEvents.MOSS_PLACE);

    public static void bootStrap() {

        addDefaultInteractions(EMPTY_FLUMROCK_CAULDRON_BEHAVIOR.map());

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().put(Items.BUCKET, (state, world, pos, player, hand, stack) -> {
            if (!world.isClientSide) {
                Item items = stack.getItem();
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(ModItems.KRYSLUM_BUCKET)));
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(items));
                lowerFillLevel(KRYSLUM_LEVEL, state, world, pos);
                world.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                world.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.success(world.isClientSide);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().put(ModItems.KRYSLUM_BUCKET, (state, world, pos, player, hand, stack) -> {
            if (state.get(KRYSLUM_LEVEL) == 12) {
                return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            }
            if (!world.isClientSide) {
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET)));
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                world.setBlockState(pos, state.cycle(KRYSLUM_LEVEL));
                world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                world.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.success(world.isClientSide);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().put(ModItems.QUAZARITH_PIECES, (state, world, pos, player, hand, stack) -> {
            if (state.get(QUAZARITH_LEVEL) == 12) {
                return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            }
            if (!world.isClientSide) {
                if (!player.isCreative()) {
                    ItemStack heldItem = player.getMainHandStack().getItem() == ModItems.QUAZARITH_PIECES ?
                            player.getMainHandStack() : player.getOffhandItem();
                    heldItem.shrink(1);
                }
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                world.setBlockState(pos, state.cycle(QUAZARITH_LEVEL));
                world.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1.0f);
                world.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.success(world.isClientSide);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().put(ModItems.CRUX, (state, world, pos, player, hand, stack) -> {
            if (state.get(CRUX_LEVEL) == 12) {
                return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            }
            if (!world.isClientSide) {
                if (!player.isCreative()) {
                    ItemStack heldItem = player.getMainHandStack().getItem() == ModItems.CRUX ?
                            player.getMainHandStack() : player.getOffhandItem();
                    heldItem.shrink(1);
                }
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                world.setBlockState(pos, state.cycle(CRUX_LEVEL));
                world.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1f);
                world.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.success(world.isClientSide);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().put(ModItems.QUAZARITH_INGOT, (state, world, pos, player, hand, stack) -> {
            if (state.get(QUAZARITH_LEVEL) > 8 || state.get(CRUX_LEVEL) > 8) {
                return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            }
            if (!world.isClientSide) {
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));

                int i = state.get(QUAZARITH_LEVEL) + SculkDepths.CONFIG.quazarith_ingot_quazarith_pieces_cost;
                int j = state.get(CRUX_LEVEL) + SculkDepths.CONFIG.quazarith_ingot_crux_cost;
                BlockState block = state.with(QUAZARITH_LEVEL, i).with(CRUX_LEVEL, j);
                world.setBlockState(pos, block);

                world.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1.0f);
                world.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }


            return ItemInteractionResult.success(world.isClientSide);
        });


        SPORE_FLUMROCK_CAULDRON_BEHAVIOR.map().put(Items.BUCKET, (state, world, pos, player, hand, stack) -> {
            if (!world.isClientSide) {
                Item items = stack.getItem();
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(ModItems.PENEBRIUM_SPORE_BUCKET)));
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(items));
                lowerFillLevel(SPORE_LEVEL, state, world, pos);
                world.playSound(null, pos, SoundEvents.MOSS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                world.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.success(world.isClientSide);
        });

        SPORE_FLUMROCK_CAULDRON_BEHAVIOR.map().put(ModItems.PENEBRIUM_SPORE_BUCKET, (state, world, pos, player, hand, stack) -> {
            if (state.get(SPORE_LEVEL) == 12) {
                return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            }
            if (!world.isClientSide) {
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET)));
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                world.setBlockState(pos, state.cycle(SPORE_LEVEL));
                world.playSound(null, pos, SoundEvents.MOSS_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                world.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.success(world.isClientSide);
        });


        SPORE_FLUMROCK_CAULDRON_BEHAVIOR.map().put(ModItems.CRUX, (state, world, pos, player, hand, stack) -> {
            if (state.get(CRUX_LEVEL) == 12) {
                return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            }
            if (!world.isClientSide) {
                if (!player.isCreative()) {
                    ItemStack heldItem = player.getMainHandStack().getItem() == ModItems.CRUX ?
                            player.getMainHandStack() : player.getOffhandItem();
                    heldItem.shrink(1);
                }
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                world.setBlockState(pos, state.cycle(CRUX_LEVEL));
                world.playSound(null, pos, SoundEvents.MOSS_STEP, SoundSource.BLOCKS, 1.0f, 1.0f);
                world.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.success(world.isClientSide);
        });

    }

    public static void lowerFillLevel(IntegerProperty worldName, BlockState state, Level world, BlockPos pos) {
        int i = state.get(worldName) - 1;
        BlockState block = i == 0 ? ModBlocks.FLUMROCK_CAULDRON.getDefaultState() : state.with(worldName, i);
        world.setBlockState(pos, block);
        world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(block));
    }

    public static void addDefaultInteractions(Map<Item, CauldronInteraction> damageCalculator) {
        damageCalculator.put(ModItems.KRYSLUM_BUCKET, FILL_WITH_KRYSLUM);
        damageCalculator.put(ModItems.PENEBRIUM_SPORE_BUCKET, FILL_WITH_SPORE);

    }
}