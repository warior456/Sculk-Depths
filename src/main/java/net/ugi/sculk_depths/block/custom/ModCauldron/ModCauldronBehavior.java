package net.ugi.sculk_depths.block.custom.ModCauldron;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
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

    public static final CauldronInteraction.InteractionMap EMPTY_FLUMROCK_CAULDRON_BEHAVIOR = CauldronInteraction.newInteractionMap("empty_flumrock_cauldron");
    public static final CauldronInteraction.InteractionMap KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR = CauldronInteraction.newInteractionMap("kryslum_flumrock_cauldron");

    public static final CauldronInteraction.InteractionMap SPORE_FLUMROCK_CAULDRON_BEHAVIOR = CauldronInteraction.newInteractionMap("spore_flumrock_cauldron");
    public static final CauldronInteraction FILL_WITH_KRYSLUM = (state, level, pos, player, hand, stack) -> CauldronInteraction.emptyBucket(level, pos, player, hand, stack, ModBlocks.KRYSLUM_FLUMROCK_CAULDRON.defaultBlockState(), SoundEvents.BUCKET_EMPTY);
    public static final CauldronInteraction FILL_WITH_SPORE = (state, level, pos, player, hand, stack) -> CauldronInteraction.emptyBucket(level, pos, player, hand, stack, ModBlocks.SPORE_FLUMROCK_CAULDRON.defaultBlockState(), SoundEvents.MOSS_PLACE);

    public static void bootStrap() {

        addDefaultInteractions(EMPTY_FLUMROCK_CAULDRON_BEHAVIOR.map());

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().put(Items.BUCKET, (state, level, pos, player, hand, stack) -> {
            if (!level.isClientSide) {
                Item items = stack.getItem();
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(ModItems.KRYSLUM_BUCKET)));
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(items));
                lowerFillLevel(KRYSLUM_LEVEL, state, level, pos);
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().put(ModItems.KRYSLUM_BUCKET, (state, level, pos, player, hand, stack) -> {
            if (state.getValue(KRYSLUM_LEVEL) == 12) {
                return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            }
            if (!level.isClientSide) {
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET)));
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                level.setBlockAndUpdate(pos, state.cycle(KRYSLUM_LEVEL));
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().put(ModItems.QUAZARITH_PIECES, (state, level, pos, player, hand, stack) -> {
            if (state.getValue(QUAZARITH_LEVEL) == 12) {
                return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            }
            if (!level.isClientSide) {
                if (!player.isCreative()) {
                    ItemStack heldItem = player.getMainHandItem().getItem() == ModItems.QUAZARITH_PIECES ?
                            player.getMainHandItem() : player.getOffhandItem();
                    heldItem.shrink(1);
                }
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                level.setBlockAndUpdate(pos, state.cycle(QUAZARITH_LEVEL));
                level.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1.0f);
                level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().put(ModItems.CRUX, (state, level, pos, player, hand, stack) -> {
            if (state.getValue(CRUX_LEVEL) == 12) {
                return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            }
            if (!level.isClientSide) {
                if (!player.isCreative()) {
                    ItemStack heldItem = player.getMainHandItem().getItem() == ModItems.CRUX ?
                            player.getMainHandItem() : player.getOffhandItem();
                    heldItem.shrink(1);
                }
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                level.setBlockAndUpdate(pos, state.cycle(CRUX_LEVEL));
                level.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1f);
                level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        });

        KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map().put(ModItems.QUAZARITH_INGOT, (state, level, pos, player, hand, stack) -> {
            if (state.getValue(QUAZARITH_LEVEL) > 8 || state.getValue(CRUX_LEVEL) > 8) {
                return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            }
            if (!level.isClientSide) {
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));

                int i = state.getValue(QUAZARITH_LEVEL) + SculkDepths.CONFIG.quazarith_ingot_quazarith_pieces_cost;
                int j = state.getValue(CRUX_LEVEL) + SculkDepths.CONFIG.quazarith_ingot_crux_cost;
                BlockState block = state.setValue(QUAZARITH_LEVEL, i).setValue(CRUX_LEVEL, j);
                level.setBlockAndUpdate(pos, block);

                level.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1.0f);
                level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }


            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        });


        SPORE_FLUMROCK_CAULDRON_BEHAVIOR.map().put(Items.BUCKET, (state, level, pos, player, hand, stack) -> {
            if (!level.isClientSide) {
                Item items = stack.getItem();
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(ModItems.PENEBRIUM_SPORE_BUCKET)));
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(items));
                lowerFillLevel(SPORE_LEVEL, state, level, pos);
                level.playSound(null, pos, SoundEvents.MOSS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        });

        SPORE_FLUMROCK_CAULDRON_BEHAVIOR.map().put(ModItems.PENEBRIUM_SPORE_BUCKET, (state, level, pos, player, hand, stack) -> {
            if (state.getValue(SPORE_LEVEL) == 12) {
                return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            }
            if (!level.isClientSide) {
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET)));
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                level.setBlockAndUpdate(pos, state.cycle(SPORE_LEVEL));
                level.playSound(null, pos, SoundEvents.MOSS_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        });


        SPORE_FLUMROCK_CAULDRON_BEHAVIOR.map().put(ModItems.CRUX, (state, level, pos, player, hand, stack) -> {
            if (state.getValue(CRUX_LEVEL) == 12) {
                return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            }
            if (!level.isClientSide) {
                if (!player.isCreative()) {
                    ItemStack heldItem = player.getMainHandItem().getItem() == ModItems.CRUX ?
                            player.getMainHandItem() : player.getOffhandItem();
                    heldItem.shrink(1);
                }
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                level.setBlockAndUpdate(pos, state.cycle(CRUX_LEVEL));
                level.playSound(null, pos, SoundEvents.MOSS_STEP, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        });

    }

    public static void registerBehavior() {
        bootStrap();
    }

    public static void lowerFillLevel(IntegerProperty worldName, BlockState state, Level level, BlockPos pos) {
        int i = state.getValue(worldName) - 1;
        BlockState block = i == 0 ? ModBlocks.FLUMROCK_CAULDRON.defaultBlockState() : state.setValue(worldName, i);
        level.setBlockAndUpdate(pos, block);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(block));
    }

    public static void addDefaultInteractions(Map<Item, CauldronInteraction> damageCalculator) {
        damageCalculator.put(ModItems.KRYSLUM_BUCKET, FILL_WITH_KRYSLUM);
        damageCalculator.put(ModItems.PENEBRIUM_SPORE_BUCKET, FILL_WITH_SPORE);

    }
}
