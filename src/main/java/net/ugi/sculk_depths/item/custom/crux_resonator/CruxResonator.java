package net.ugi.sculk_depths.item.custom.crux_resonator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FireworksComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.ugi.sculk_depths.item.ModComponentTypes;
import net.ugi.sculk_depths.item.ModItems;
import org.jetbrains.annotations.Nullable;

import static net.ugi.sculk_depths.block.ModBlocks.QUAZARITH_OSCILLATOR;

public class CruxResonator extends Item {
    public CruxResonator(Item.Settings settings) {
        super(settings);
    }

    @Nullable
    public static GlobalPos createSpawnPos(World world) {
        return world.getDimension().natural() ? GlobalPos.create(world.getRegistryKey(), world.getSpawnPos()) : null;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(world.isClient) {return TypedActionResult.pass(itemStack);}//maybe fix some bugs?
        OscillatorTrackerComponentList oscillatorTrackerComponentList = itemStack.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST);
        System.out.println(oscillatorTrackerComponentList.selectedLocation());
        itemStack.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, new OscillatorTrackerComponentList(oscillatorTrackerComponentList.selectedLocation() +1, oscillatorTrackerComponentList.trackers()));
        return TypedActionResult.success(itemStack,false);
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
            OscillatorTrackerComponent oscillatorTrackerComponent = stack.get(ModComponentTypes.OSCILLATOR_TRACKER); //get trackers
            OscillatorTrackerComponentList oscillatorTrackerComponentList = stack.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST);
            if(oscillatorTrackerComponent == null) return;
            if(oscillatorTrackerComponentList == null) return;

            List<OscillatorTrackerComponent> trackers = oscillatorTrackerComponentList.trackers();
            int selectedLocation = oscillatorTrackerComponentList.selectedLocation();

            if(selectedLocation >= trackers.size()) {//outofbounds check
                selectedLocation = 0;
            }
/*            System.out.println("trackers size: "+trackers.size());
            System.out.println("selected: "+ selectedLocation);*/
            if (trackers.get(selectedLocation) != null) {
                OscillatorTrackerComponent oscillatorTrackerComponent1 = trackers.get(selectedLocation);
                OscillatorTrackerComponent oscillatorTrackerComponent2 = oscillatorTrackerComponent1.forWorld(serverWorld);
                if(oscillatorTrackerComponent != oscillatorTrackerComponent1) {
                    stack.set(ModComponentTypes.OSCILLATOR_TRACKER, oscillatorTrackerComponent1);
                    stack.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, new OscillatorTrackerComponentList(selectedLocation, trackers));
                }
                if(oscillatorTrackerComponent1 != oscillatorTrackerComponent2) {
                    //System.out.println();
                    stack.set(ModComponentTypes.OSCILLATOR_TRACKER, oscillatorTrackerComponent2);
                    trackers.remove(selectedLocation);
                    stack.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, new OscillatorTrackerComponentList(selectedLocation-1, trackers));
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
            if(context.getWorld().isClient) return ActionResult.PASS;//maybe error spam fix?
            world.playSound(null, blockPos, SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            PlayerEntity playerEntity = context.getPlayer();
            ItemStack itemStack = context.getStack();
            boolean bl = !playerEntity.isInCreativeMode() && itemStack.getCount() == 1;

            List<OscillatorTrackerComponent> trackers = new ArrayList<OscillatorTrackerComponent>();
            int selectedLocation = 0;
            if(itemStack.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST) != null) {
                trackers = itemStack.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST).trackers();
                selectedLocation = itemStack.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST).selectedLocation();
            };


            OscillatorTrackerComponent oscillatorTrackerComponent = new OscillatorTrackerComponent(Optional.of(GlobalPos.create(world.getRegistryKey(), blockPos)), true);
            if (bl) {

                trackers.add(oscillatorTrackerComponent);
                System.out.println(trackers);
                OscillatorTrackerComponentList oscillatorTrackerComponentList = new OscillatorTrackerComponentList(selectedLocation, trackers);
                System.out.println(oscillatorTrackerComponentList);
                itemStack.set(ModComponentTypes.OSCILLATOR_TRACKER , oscillatorTrackerComponentList.trackers().get(oscillatorTrackerComponentList.trackers().size() - 1));
                itemStack.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST , oscillatorTrackerComponentList);


                //itemStack.apply(ModComponentTypes.OSCILLATOR_TRACKER_LIST, new OscillatorTrackerComponentList(0, trackers), new OscillatorTrackerComponentList(0,));



            } else {
                ItemStack itemStack2 = itemStack.copyComponentsToNewStack(ModItems.CRUX_RESONATOR, 1);
                itemStack.decrementUnlessCreative(1, playerEntity);

                trackers.add(oscillatorTrackerComponent);
                System.out.println("2");
                System.out.println(trackers);
                OscillatorTrackerComponentList oscillatorTrackerComponentList = new OscillatorTrackerComponentList(selectedLocation, trackers);
                System.out.println(oscillatorTrackerComponentList);
                itemStack.set(ModComponentTypes.OSCILLATOR_TRACKER , oscillatorTrackerComponentList.trackers().get(oscillatorTrackerComponentList.trackers().size() - 1));
                itemStack2.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, oscillatorTrackerComponentList);


                if (!playerEntity.getInventory().insertStack(itemStack2)) {
                    playerEntity.dropItem(itemStack2, false);
                }
            }

            return ActionResult.success(world.isClient);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
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


