package net.ugi.sculk_depths.item;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.item.custom.crux_resonator.OscillatorTrackerComponentList;
import net.ugi.sculk_depths.item.custom.crux_resonator.OscillatorTrackerComponent;
import net.ugi.sculk_depths.util.enums.CrystalType;

public class ModComponentTypes {

    public static final ComponentType<OscillatorTrackerComponent> OSCILLATOR_TRACKER = register("quazarith_oscillator_tracker",
            ComponentType.<OscillatorTrackerComponent>builder()
                    .codec(OscillatorTrackerComponent.CODEC)
                    .packetCodec(OscillatorTrackerComponent.PACKET_CODEC)
                    .build());

    public static final ComponentType<OscillatorTrackerComponentList> OSCILLATOR_TRACKER_LIST = register("oscillator_tracker_list",
            ComponentType.<OscillatorTrackerComponentList>builder()
                    .codec(OscillatorTrackerComponentList.CODEC)
                    .packetCodec(OscillatorTrackerComponentList.PACKET_CODEC)
                    .build());

    public static final ComponentType<CrystalType> CRYSTAL = register("crystal",
            ComponentType.<CrystalType>builder()
                    .codec(CrystalType.CODEC)
                    .packetCodec(CrystalType.PACKET_CODEC)
                    .build());

    public static <T> ComponentType<T> register(String name, ComponentType<T> componentType) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, SculkDepths.identifier(name), componentType);
    }

    public static void register() {
    }
}
