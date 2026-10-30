package net.ugi.sculk_depths.item;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.item.custom.crux_resonator.OscillatorTrackerComponentList;
import net.ugi.sculk_depths.item.custom.crux_resonator.OscillatorTrackerComponent;

public class ModComponentTypes {
    public static void register(){
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, SculkDepths.id("quazarith_oscillator_tracker"), OSCILLATOR_TRACKER);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, SculkDepths.id("oscillator_tracker_list"), OSCILLATOR_TRACKER_LIST);
    }

    public static final DataComponentType<OscillatorTrackerComponent> OSCILLATOR_TRACKER = DataComponentType.<OscillatorTrackerComponent>builder().persistent(OscillatorTrackerComponent.CODEC).networkSynchronized(OscillatorTrackerComponent.PACKET_CODEC).build();
    public static final DataComponentType<OscillatorTrackerComponentList> OSCILLATOR_TRACKER_LIST = DataComponentType.<OscillatorTrackerComponentList>builder().persistent(OscillatorTrackerComponentList.CODEC).networkSynchronized(OscillatorTrackerComponentList.PACKET_CODEC).build();

}
