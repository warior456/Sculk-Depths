package net.ugi.sculk_depths.item.custom;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BedPart;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestType;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class CruxCompass extends Item {
    public CruxCompass(Item.Settings settings) {
        super(settings);
    }

    @Nullable
    public static GlobalPos createSpawnPos(World world) {
        return world.getDimension().natural() ? GlobalPos.create(world.getRegistryKey(), world.getSpawnPos()) : null;
    }

    public boolean hasGlint(ItemStack stack) {
        return stack.contains(DataComponentTypes.LODESTONE_TRACKER) || super.hasGlint(stack);
    }


    //-----
    public static final RegistryKey<PointOfInterestType> Q_LODESTONE = of("q_lodestone");

    private static final Map<BlockState, RegistryEntry<PointOfInterestType>> POI_STATES_TO_TYPE = Maps.<BlockState, RegistryEntry<PointOfInterestType>>newHashMap();

    private static Set<BlockState> getStatesOfBlock(Block block) {
        return ImmutableSet.copyOf(block.getStateManager().getStates());
    }

    private static RegistryKey<PointOfInterestType> of(String id) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Identifier.ofVanilla(id));
    }

    private static PointOfInterestType register(
            Registry<PointOfInterestType> registry, RegistryKey<PointOfInterestType> key, Set<BlockState> states, int ticketCount, int searchDistance
    ) {
        PointOfInterestType pointOfInterestType = new PointOfInterestType(states, ticketCount, searchDistance);
        Registry.register(registry, key, pointOfInterestType);
        registerStates(registry.entryOf(key), states);
        return pointOfInterestType;
    }

    private static void registerStates(RegistryEntry<PointOfInterestType> poiTypeEntry, Set<BlockState> states) {
        states.forEach(state -> {
            RegistryEntry<PointOfInterestType> registryEntry2 = (RegistryEntry<PointOfInterestType>)POI_STATES_TO_TYPE.put(state, poiTypeEntry);
            if (registryEntry2 != null) {
                throw (IllegalStateException)Util.throwOrPause(new IllegalStateException(String.format(Locale.ROOT, "%s is defined in more than one PoI type", state)));
            }
        });
    }

    public static Optional<RegistryEntry<PointOfInterestType>> getTypeForState(BlockState state) {
        return Optional.ofNullable((RegistryEntry)POI_STATES_TO_TYPE.get(state));
    }

    public static boolean isPointOfInterest(BlockState state) {
        return POI_STATES_TO_TYPE.containsKey(state);
    }

    public static PointOfInterestType registerAndGetDefault(Registry<PointOfInterestType> registry) {
    return register(registry, Q_LODESTONE, getStatesOfBlock(ModBlocks.QUAZARITH_LODESTONE), 0, 1);
    }
    //-----

    public LodestoneTrackerComponent forWorld(ServerWorld world, LodestoneTrackerComponent component) {
        if (component.tracked() && !component.target().isEmpty()) {
            if (((GlobalPos)component.target().get()).dimension() != world.getRegistryKey()) {
                return component;
            } else {
                BlockPos blockPos = ((GlobalPos)component.target().get()).pos();
                return world.isInBuildLimit(blockPos) && world.getPointOfInterestStorage().hasTypeAt(Q_LODESTONE, blockPos)
                        ? component
                        : new LodestoneTrackerComponent(Optional.empty(), true);
            }
        } else {
            return component;
        }
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world instanceof ServerWorld serverWorld) {
            LodestoneTrackerComponent lodestoneTrackerComponent = (LodestoneTrackerComponent)stack.get(DataComponentTypes.LODESTONE_TRACKER);
            if (lodestoneTrackerComponent != null) {
                LodestoneTrackerComponent lodestoneTrackerComponent2 = forWorld(serverWorld,lodestoneTrackerComponent);
                if (lodestoneTrackerComponent2 != lodestoneTrackerComponent) {
                    stack.set(DataComponentTypes.LODESTONE_TRACKER, lodestoneTrackerComponent2);
                }
            }
        }

    }
    public static GlobalPos getTrackedPos(ItemStack stack){
        if (stack.get(DataComponentTypes.LODESTONE_TRACKER) == null) return null;
        if (stack.get(DataComponentTypes.LODESTONE_TRACKER).target().isEmpty()) return null;
        return stack.get(DataComponentTypes.LODESTONE_TRACKER).target().get();

    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockPos = context.getBlockPos();
        World world = context.getWorld();
        if (!world.getBlockState(blockPos).isOf(ModBlocks.QUAZARITH_LODESTONE)) {
            return super.useOnBlock(context);
        } else {
            world.playSound(null, blockPos, SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            PlayerEntity playerEntity = context.getPlayer();
            ItemStack itemStack = context.getStack();
            boolean bl = !playerEntity.isInCreativeMode() && itemStack.getCount() == 1;
            LodestoneTrackerComponent lodestoneTrackerComponent = new LodestoneTrackerComponent(Optional.of(GlobalPos.create(world.getRegistryKey(), blockPos)), true);
            if (bl) {
                itemStack.set(DataComponentTypes.LODESTONE_TRACKER, lodestoneTrackerComponent);
            } else {
                ItemStack itemStack2 = itemStack.copyComponentsToNewStack(ModItems.CRUX_COMPASS, 1);
                itemStack.decrementUnlessCreative(1, playerEntity);
                itemStack2.set(DataComponentTypes.LODESTONE_TRACKER, lodestoneTrackerComponent);
                if (!playerEntity.getInventory().insertStack(itemStack2)) {
                    playerEntity.dropItem(itemStack2, false);
                }
            }

            return ActionResult.success(world.isClient);
        }
    }
}
