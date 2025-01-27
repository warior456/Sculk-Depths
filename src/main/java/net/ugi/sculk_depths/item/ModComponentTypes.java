package net.ugi.sculk_depths.item;

import net.minecraft.component.ComponentType;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.ugi.sculk_depths.item.custom.crux_resonator.OscillatorTrackerComponent;

public class ModComponentTypes {
    public static void register(){
        Registry.register(Registries.DATA_COMPONENT_TYPE, "quazarith_oscillator_tracker", OSCILLATOR_TRACKER);
    }

    public static final ComponentType<OscillatorTrackerComponent> OSCILLATOR_TRACKER = ComponentType.<OscillatorTrackerComponent>builder().codec(OscillatorTrackerComponent.CODEC).packetCodec(OscillatorTrackerComponent.PACKET_CODEC).build();

}
