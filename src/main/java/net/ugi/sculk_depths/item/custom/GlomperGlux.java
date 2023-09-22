package net.ugi.sculk_depths.item.custom;

import net.kyrptonaught.customportalapi.portal.PortalPlacer;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.tags.ModTags;
import net.minecraft.block.DoorBlock;

import static net.minecraft.block.DoorBlock.HALF;
import static net.ugi.sculk_depths.portal.Portals.SOUL_FIRE;

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

