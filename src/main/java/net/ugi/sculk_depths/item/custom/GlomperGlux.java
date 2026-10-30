package net.ugi.sculk_depths.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.tags.ModTags;

import static net.minecraft.world.level.block.DoorBlock.HALF;

public class GlomperGlux extends Item implements Coatable {

    public GlomperGlux(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) { //originally copied from mc
        BlockPos blockPos = context.getClickedPos();
        Player playerEntity = context.getPlayer();
        Level level = context.getLevel();
        BlockState state = level.getBlockState(blockPos);
        Block block = state.getBlock();

        if (state.is(ModTags.Blocks.COATABLE_BLOCKS)) {
            if (block == ModBlocks.VALTROX_DOOR) {
                BlockPos blockPos1 = state.getValue(HALF).equals(DoubleBlockHalf.UPPER) ? blockPos.below() : blockPos;
                Coatable.CoatBlock(state, level, blockPos1);
            } else {
                Coatable.CoatBlock(state, level, blockPos);
            }
            context.getItemInHand().shrink(1);
            if (playerEntity != null) {
                playerEntity.playSound(SoundEvents.HONEYCOMB_WAX_ON, 1.0F, 2.0F);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
