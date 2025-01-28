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
            if(trackers.isEmpty()) return;
            List<OscillatorTrackerComponent> trackers1 = new ArrayList<OscillatorTrackerComponent>(trackers);//DO NOT DELETE AT ANY COST

            int selectedLocation = oscillatorTrackerComponentList.selectedLocation();

            if(selectedLocation >= trackers.size() || selectedLocation<0) {//outofbounds check
                selectedLocation = 0;
            }

            if(trackers.get(selectedLocation) == null) return;

            OscillatorTrackerComponent oscillatorTrackerComponent1 = trackers.get(selectedLocation);
            OscillatorTrackerComponent oscillatorTrackerComponent2 = oscillatorTrackerComponent1.forWorld(serverWorld);
            if(oscillatorTrackerComponent != oscillatorTrackerComponent1) {
                stack.set(ModComponentTypes.OSCILLATOR_TRACKER, oscillatorTrackerComponent1);
                stack.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, new OscillatorTrackerComponentList(selectedLocation, trackers));
            }
            if(oscillatorTrackerComponent1 != oscillatorTrackerComponent2) {
                //System.out.println();
                stack.set(ModComponentTypes.OSCILLATOR_TRACKER, oscillatorTrackerComponent2);
                System.out.println(trackers);
                System.out.println(selectedLocation);
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
    public ActionResult useOnBlock(ItemUsageContext context) {
        List<OscillatorTrackerComponent> trackers = new ArrayList<OscillatorTrackerComponent>();
        System.out.println("START USE ON BLOCK");

        BlockPos blockPos = context.getBlockPos();
        World world = context.getWorld();

        if (!world.getBlockState(blockPos).isOf(QUAZARITH_OSCILLATOR)) return super.useOnBlock(context);
        //if (context.getWorld().isClient) return ActionResult.PASS;//maybe error spam fix?
        world.playSound(null, blockPos, SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.PLAYERS, 1.0F, 1.0F);

        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();

        if(itemStack.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST) != null) {
            trackers = itemStack.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST).trackers();
            System.out.println("trackers:" + trackers);
        };

        OscillatorTrackerComponent oscillatorTrackerComponent = new OscillatorTrackerComponent(Optional.of(GlobalPos.create(world.getRegistryKey(), blockPos)), true, "");


        List<OscillatorTrackerComponent> trackers1 = new ArrayList<OscillatorTrackerComponent>(trackers);//DO NOT TOUCH AT ANY COST
        trackers1.addFirst(oscillatorTrackerComponent);

        OscillatorTrackerComponentList oscillatorTrackerComponentList = new OscillatorTrackerComponentList(0, trackers1);

        itemStack.set(ModComponentTypes.OSCILLATOR_TRACKER , oscillatorTrackerComponent);
        itemStack.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST , oscillatorTrackerComponentList);


        System.out.println("END USE ON BLOCK");

        return ActionResult.success(world.isClient);

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


