package net.ugi.sculk_depths.item;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.ugi.sculk_depths.item.custom.crux_resonator.OscillatorTrackerComponentList;
import net.ugi.sculk_depths.item.custom.crux_resonator.OscillatorTrackerComponent;

public class ModComponentTypes {
    public static void register(){
        Registry.register(Registries.DATA_COMPONENT_TYPE, "quazarith_oscillator_tracker", OSCILLATOR_TRACKER);
        Registry.register(Registries.DATA_COMPONENT_TYPE, "oscillator_tracker_list", OSCILLATOR_TRACKER_LIST);
    }

    public static final ComponentType<OscillatorTrackerComponent> OSCILLATOR_TRACKER = ComponentType.<OscillatorTrackerComponent>builder().codec(OscillatorTrackerComponent.CODEC).packetCodec(OscillatorTrackerComponent.PACKET_CODEC).build();
    public static final ComponentType<OscillatorTrackerComponentList> OSCILLATOR_TRACKER_LIST = ComponentType.<OscillatorTrackerComponentList>builder().codec(OscillatorTrackerComponentList.CODEC).packetCodec(OscillatorTrackerComponentList.PACKET_CODEC).build();

}
