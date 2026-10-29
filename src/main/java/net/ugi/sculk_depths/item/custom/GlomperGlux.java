package net.ugi.sculk_depths.item.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.tags.ModTags;

import static net.minecraft.block.DoorBlock.HALF;

public class GlomperGlux extends Item implements Coatable{

    public GlomperGlux(Settings settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) { //originally copied from mc
        BlockPos blockPos = context.getBlockPos();
        Player playerEntity = context.getPlayer();
        Level world = context.getWorld();
        BlockState block = world.getBlockState(blockPos);
        Block block = block.getBlock();

        if (block.is(ModTags.Blocks.COATABLE_BLOCKS)) {
            if (block == ModBlocks.VALTROX_DOOR){
                BlockPos blockPos1 = block.get(HALF).equals(DoubleBlockHalf.UPPER) ? blockPos.below() : blockPos;
                Coatable.CoatBlock(block, world, blockPos1);
            }
            else {
                Coatable.CoatBlock(block, world, blockPos);
            }
            context.getStack().shrink(1);
            if(playerEntity != null){
                playerEntity.playSound(SoundEvents.HONEYCOMB_WAX_ON, 1.0F, 2.0F);
            }
            return InteractionResult.SUCCESS;

        }
        return InteractionResult.FAIL;
    }

}

