package net.ugi.sculk_depths.item.custom.crux_resonator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.ugi.sculk_depths.item.ModComponentTypes;
import org.jetbrains.annotations.Nullable;

import static net.ugi.sculk_depths.block.ModBlocks.QUAZARITH_OSCILLATOR;

public class CruxResonator extends Item {
    public CruxResonator(Item.Properties settings) {
        super(settings);
    }

    @Nullable
    public static GlobalPos getSpawnPosition(Level level) {
        return level.dimensionType().natural() ? GlobalPos.of(level.dimension(), level.getSharedSpawnPos()) : null;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player user, InteractionHand hand) {
        ItemStack item = user.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.pass(item);
        }
        OscillatorTrackerComponentList oscillatorTrackerComponentList = item.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST);
        if (oscillatorTrackerComponentList == null) {
            item.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, new OscillatorTrackerComponentList(0, List.of()));
            return InteractionResultHolder.success(item);
        }
        item.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, new OscillatorTrackerComponentList(oscillatorTrackerComponentList.selectedLocation() + 1, oscillatorTrackerComponentList.trackers()));
        return InteractionResultHolder.success(item);
    }

    public static GlobalPos getTrackedPos(ItemStack stack) {
        OscillatorTrackerComponent tracker = stack.get(ModComponentTypes.OSCILLATOR_TRACKER);
        if (tracker == null) return null;
        if (tracker.target().isEmpty()) return null;
        return tracker.target().get();
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.has(ModComponentTypes.OSCILLATOR_TRACKER) || super.isFoil(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (level instanceof ServerLevel serverWorld) {
            OscillatorTrackerComponent oscillatorTrackerComponent = stack.get(ModComponentTypes.OSCILLATOR_TRACKER);
            OscillatorTrackerComponentList oscillatorTrackerComponentList = stack.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST);
            if (oscillatorTrackerComponent == null) return;
            if (oscillatorTrackerComponentList == null) return;

            List<OscillatorTrackerComponent> trackers = oscillatorTrackerComponentList.trackers();
            if (trackers.isEmpty()) return;
            List<OscillatorTrackerComponent> trackers1 = new ArrayList<>(trackers);

            int selectedLocation = oscillatorTrackerComponentList.selectedLocation();

            if (selectedLocation >= trackers.size() || selectedLocation < 0) {
                selectedLocation = 0;
            }

            if (trackers.get(selectedLocation) == null) return;

            OscillatorTrackerComponent oscillatorTrackerComponent1 = trackers.get(selectedLocation);
            OscillatorTrackerComponent oscillatorTrackerComponent2 = oscillatorTrackerComponent1.tick(serverWorld);
            if (oscillatorTrackerComponent != oscillatorTrackerComponent1) {
                stack.set(ModComponentTypes.OSCILLATOR_TRACKER, oscillatorTrackerComponent1);
                stack.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, new OscillatorTrackerComponentList(selectedLocation, trackers));
            }
            if (oscillatorTrackerComponent1 != oscillatorTrackerComponent2) {
                stack.set(ModComponentTypes.OSCILLATOR_TRACKER, oscillatorTrackerComponent2);
                trackers1.remove(selectedLocation);
                if (selectedLocation == 0) {
                    stack.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, new OscillatorTrackerComponentList(selectedLocation, trackers1));
                    return;
                }
                stack.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, new OscillatorTrackerComponentList(selectedLocation - 1, trackers1));
            }
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        List<OscillatorTrackerComponent> trackers = new ArrayList<>();

        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();

        if (!level.getBlockState(blockPos).is(QUAZARITH_OSCILLATOR)) return super.useOn(context);
        level.playSound(null, blockPos, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);

        ItemStack item = context.getItemInHand();

        if (item.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST) != null) {
            trackers = item.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST).trackers();
        }

        OscillatorTrackerComponent oscillatorTrackerComponent = new OscillatorTrackerComponent(Optional.of(GlobalPos.of(level.dimension(), blockPos)), true, "");

        List<OscillatorTrackerComponent> trackers1 = new ArrayList<>(trackers);
        trackers1.add(0, oscillatorTrackerComponent);

        OscillatorTrackerComponentList oscillatorTrackerComponentList = new OscillatorTrackerComponentList(0, trackers1);

        item.set(ModComponentTypes.OSCILLATOR_TRACKER, oscillatorTrackerComponent);
        item.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, oscillatorTrackerComponentList);

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        OscillatorTrackerComponent oscillatorTrackerComponent = stack.get(ModComponentTypes.OSCILLATOR_TRACKER);
        if (oscillatorTrackerComponent != null) {
            oscillatorTrackerComponent.addToTooltip(context, tooltip::add, type);
        }
        OscillatorTrackerComponentList oscillatorTrackerComponentList = stack.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST);
        if (oscillatorTrackerComponentList != null) {
            oscillatorTrackerComponentList.addToTooltip(context, tooltip::add, type);
        }
    }
}
