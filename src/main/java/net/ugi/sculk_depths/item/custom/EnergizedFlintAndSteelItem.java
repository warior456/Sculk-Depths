package net.ugi.sculk_depths.item.custom;


import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
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

//import static net.ugi.sculk_depths.portal.Portals.SOUL_FIRE;

public class EnergizedFlintAndSteelItem extends Item {

    public EnergizedFlintAndSteelItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) { //originally copied from mc
        BlockPos blockPos;
        PlayerEntity playerEntity = context.getPlayer();
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(blockPos = context.getBlockPos());

        boolean portalLighted = false;//PortalPlacer.attemptPortalLight(world, blockPos.offset(context.getSide()), SOUL_FIRE);//TODO (customportalapi)
        if (portalLighted) {
            ItemStack itemStack = context.getStack();
            if (playerEntity instanceof ServerPlayerEntity) {
                itemStack.damage(SculkDepths.CONFIG.activate_portal_durability_usage, playerEntity, EquipmentSlot.MAINHAND);
            }
            return ActionResult.success(world.isClient());
        }

        if (CampfireBlock.canBeLit(blockState) || CandleBlock.canBeLit(blockState) || CandleCakeBlock.canBeLit(blockState)) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f);
            world.setBlockState(blockPos, (BlockState) blockState.with(Properties.LIT, true), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            world.emitGameEvent((Entity) playerEntity, GameEvent.BLOCK_CHANGE, blockPos);
            if (playerEntity != null) {
                context.getStack().damage(1, playerEntity, EquipmentSlot.MAINHAND);
            }
            return ActionResult.success(world.isClient());
        }
        BlockPos blockPos2 = blockPos.offset(context.getSide());
        if (AbstractFireBlock.canPlaceAt(world, blockPos2, context.getHorizontalPlayerFacing())) {
            world.playSound(playerEntity, blockPos2, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f);
            BlockState blockState2 = ModBlocks.SOUL_FIRE.getDefaultState();
            world.setBlockState(blockPos2, blockState2, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            world.emitGameEvent((Entity) playerEntity, GameEvent.BLOCK_PLACE, blockPos);
            ItemStack itemStack = context.getStack();
            if (playerEntity instanceof ServerPlayerEntity) {
                Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity) playerEntity, blockPos2, itemStack);
                itemStack.damage(1, playerEntity, EquipmentSlot.MAINHAND);
            }
            return ActionResult.success(world.isClient());
        }

        return ActionResult.FAIL;
    }

}
