package net.ugi.sculk_depths.item.custom;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LodestoneTrackerComponent;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestType;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModComponentTypes;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.item.custom.crux_resonator.OscillatorTrackerComponent;
import org.jetbrains.annotations.Nullable;

import static net.ugi.sculk_depths.block.ModBlocks.QUAZARITH_OSCILLATOR;
import static net.ugi.sculk_depths.util.POIs.QUAZARITH_OSCILLATOR_POI;

public class CruxResonator extends Item {
    public CruxResonator(Item.Settings settings) {
        super(settings);
    }

    @Nullable
    public static GlobalPos createSpawnPos(World world) {
        return world.getDimension().natural() ? GlobalPos.create(world.getRegistryKey(), world.getSpawnPos()) : null;
    }

    public static GlobalPos getTrackedPos(ItemStack stack){
        if (stack.get(ModComponentTypes.OSCILLATOR_TRACKER) == null) return null;
        if (stack.get(ModComponentTypes.OSCILLATOR_TRACKER).target().isEmpty()) return null;
        return stack.get(ModComponentTypes.OSCILLATOR_TRACKER).target().get();
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.contains(ModComponentTypes.OSCILLATOR_TRACKER) || super.hasGlint(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world instanceof ServerWorld serverWorld) {
            OscillatorTrackerComponent oscillatorTrackerComponent = stack.get(ModComponentTypes.OSCILLATOR_TRACKER);
            if (oscillatorTrackerComponent != null) {
                OscillatorTrackerComponent oscillatorTrackerComponent2 = oscillatorTrackerComponent.forWorld(serverWorld);
                if (oscillatorTrackerComponent2 != oscillatorTrackerComponent) {
                    stack.set(ModComponentTypes.OSCILLATOR_TRACKER, oscillatorTrackerComponent2);
                }
            }
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockPos = context.getBlockPos();
        World world = context.getWorld();
        if (!world.getBlockState(blockPos).isOf(QUAZARITH_OSCILLATOR)) {
            return super.useOnBlock(context);
        } else {
            world.playSound(null, blockPos, SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            PlayerEntity playerEntity = context.getPlayer();
            ItemStack itemStack = context.getStack();
            boolean bl = !playerEntity.isInCreativeMode() && itemStack.getCount() == 1;
            OscillatorTrackerComponent oscillatorTrackerComponent = new OscillatorTrackerComponent(Optional.of(GlobalPos.create(world.getRegistryKey(), blockPos)), true);
            if (bl) {
                itemStack.set(ModComponentTypes.OSCILLATOR_TRACKER , oscillatorTrackerComponent);
            } else {
                ItemStack itemStack2 = itemStack.copyComponentsToNewStack(ModItems.CRUX_RESONATOR, 1);
                itemStack.decrementUnlessCreative(1, playerEntity);
                itemStack2.set(ModComponentTypes.OSCILLATOR_TRACKER, oscillatorTrackerComponent);
                if (!playerEntity.getInventory().insertStack(itemStack2)) {
                    playerEntity.dropItem(itemStack2, false);
                }
            }

            return ActionResult.success(world.isClient);
        }
    }


    
    
    
    
}


