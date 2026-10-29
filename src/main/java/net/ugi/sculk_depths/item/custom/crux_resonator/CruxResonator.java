package net.ugi.sculk_depths.item.custom.crux_resonator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.level.Level;
import net.ugi.sculk_depths.item.ModComponentTypes;
import org.jetbrains.annotations.Nullable;

import static net.ugi.sculk_depths.block.ModBlocks.QUAZARITH_OSCILLATOR;

public class CruxResonator extends Item {
    public CruxResonator(Item.Properties settings) {
        super(settings);
    }

    @Nullable
    public static GlobalPos getSpawnPosition(Level world) {
        return world.getDimension().natural() ? GlobalPos.create(world.getRegistryKey(), world.getSharedSpawnPos()) : null;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack item = user.getItemInHand(hand);
        if(world.isClientSide) {return InteractionResultHolder.pass(item);}//maybe fix some bugs?
        OscillatorTrackerComponentList oscillatorTrackerComponentList = item.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST);
        if (oscillatorTrackerComponentList == null) {
            //fresh resonator: no tracked locations yet, initialize an empty tracker list
            item.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, new OscillatorTrackerComponentList(0, List.of()));
            return InteractionResultHolder.success(item,false);
        }
        item.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, new OscillatorTrackerComponentList(oscillatorTrackerComponentList.selectedLocation() +1, oscillatorTrackerComponentList.trackers()));
        return InteractionResultHolder.success(item,false);
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
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (world instanceof ServerLevel serverWorld) {
            OscillatorTrackerComponent oscillatorTrackerComponent = stack.get(ModComponentTypes.OSCILLATOR_TRACKER); //get trackers
            OscillatorTrackerComponentList oscillatorTrackerComponentList = stack.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST);
            if(oscillatorTrackerComponent == null) return;
            if(oscillatorTrackerComponentList == null) return;

            List<OscillatorTrackerComponent> trackers = oscillatorTrackerComponentList.trackers();
            if(trackers.isEmpty()) return;
            List<OscillatorTrackerComponent> trackers1 = new ArrayList<OscillatorTrackerComponent>(trackers);//DO NOT DELETE AT ANY COST

            int selectedLocation = oscillatorTrackerComponentList.selectedLocation();

            if(selectedLocation >= trackers.size() || selectedLocation<0) {//outofbounds check
                selectedLocation = 0;
            }

            if(trackers.get(selectedLocation) == null) return;

            OscillatorTrackerComponent oscillatorTrackerComponent1 = trackers.get(selectedLocation);
            OscillatorTrackerComponent oscillatorTrackerComponent2 = oscillatorTrackerComponent1.tick(serverWorld);
            if(oscillatorTrackerComponent != oscillatorTrackerComponent1) {
                stack.set(ModComponentTypes.OSCILLATOR_TRACKER, oscillatorTrackerComponent1);
                stack.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, new OscillatorTrackerComponentList(selectedLocation, trackers));
            }
            if(oscillatorTrackerComponent1 != oscillatorTrackerComponent2) {
                //System.out.println();
                stack.set(ModComponentTypes.OSCILLATOR_TRACKER, oscillatorTrackerComponent2);
                trackers1.remove(selectedLocation);
                if(selectedLocation == 0) {
                    stack.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, new OscillatorTrackerComponentList(selectedLocation, trackers1));
                    return;
                }
                stack.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, new OscillatorTrackerComponentList(selectedLocation-1, trackers1));
            }

        }
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        List<OscillatorTrackerComponent> trackers = new ArrayList<OscillatorTrackerComponent>();

        BlockPos blockPos = context.getBlockPos();
        Level world = context.getWorld();

        if (!world.getBlockState(blockPos).isOf(QUAZARITH_OSCILLATOR)) return super.useOn(context);
        //if (context.getWorld().isClient) return ActionResult.PASS;//maybe error spam fix?
        world.playSound(null, blockPos, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYER_OLD_DATA_DIR, 1.0F, 1.0F);

        Player playerEntity = context.getPlayer();
        ItemStack item = context.getStack();

        if(item.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST) != null) {
            trackers = item.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST).trackers();
        };

        OscillatorTrackerComponent oscillatorTrackerComponent = new OscillatorTrackerComponent(Optional.of(GlobalPos.create(world.getRegistryKey(), blockPos)), true, "");


        List<OscillatorTrackerComponent> trackers1 = new ArrayList<OscillatorTrackerComponent>(trackers);//DO NOT TOUCH AT ANY COST
        trackers1.addFirst(oscillatorTrackerComponent);

        OscillatorTrackerComponentList oscillatorTrackerComponentList = new OscillatorTrackerComponentList(0, trackers1);

        item.set(ModComponentTypes.OSCILLATOR_TRACKER , oscillatorTrackerComponent);
        item.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST , oscillatorTrackerComponentList);


        return InteractionResult.success(world.isClientSide);

    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        OscillatorTrackerComponent oscillatorTrackerComponent = stack.get(ModComponentTypes.OSCILLATOR_TRACKER);
        if (oscillatorTrackerComponent != null) {
            oscillatorTrackerComponent.appendTooltip(context, tooltip::add, type);
        }
        OscillatorTrackerComponentList oscillatorTrackerComponentList = stack.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST);
        if (oscillatorTrackerComponentList != null) {
            oscillatorTrackerComponentList.appendTooltip(context, tooltip::add, type);
        }
    }
    
    
    
    
}


