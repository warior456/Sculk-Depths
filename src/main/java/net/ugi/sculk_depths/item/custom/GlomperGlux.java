package net.ugi.sculk_depths.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.tags.ModTags;

import static net.minecraft.block.DoorBlock.HALF;

public class GlomperGlux extends Item implements Coatable{

    public GlomperGlux(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) { //originally copied from mc
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();

        if (blockState.isIn(ModTags.Blocks.COATABLE_BLOCKS)) {
            if (block == ModBlocks.VALTROX_DOOR){
                BlockPos blockPos1 = blockState.get(HALF).equals(DoubleBlockHalf.UPPER) ? blockPos.down() : blockPos;
                Coatable.CoatBlock(blockState, world, blockPos1);
            }
            else {
                Coatable.CoatBlock(blockState, world, blockPos);
            }



            return ActionResult.SUCCESS;

        }
        return ActionResult.FAIL;
    }

}

