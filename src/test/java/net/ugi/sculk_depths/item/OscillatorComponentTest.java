package net.ugi.sculk_depths.item;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import io.netty.buffer.Unpooled;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.ugi.sculk_depths.TestBootstrap;
import net.ugi.sculk_depths.item.custom.crux_resonator.CruxResonator;
import net.ugi.sculk_depths.item.custom.crux_resonator.OscillatorTrackerComponent;
import net.ugi.sculk_depths.item.custom.crux_resonator.OscillatorTrackerComponentList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Normal-behavior tests for the Crux Resonator data components:
 * codec/packet round-trips, stack attachment, and list invariants.
 */
class OscillatorComponentTest {

    private static final GlobalPos TARGET = GlobalPos.create(World.OVERWORLD, new BlockPos(12, 64, -8));

    @BeforeAll
    static void setup() {
        TestBootstrap.init();
    }

    private static OscillatorTrackerComponent sampleComponent() {
        return new OscillatorTrackerComponent(Optional.of(TARGET), true, "home");
    }

    @Test
    void componentCodecRoundTripsThroughJson() {
        OscillatorTrackerComponent component = sampleComponent();

        JsonElement json = OscillatorTrackerComponent.CODEC.encodeStart(JsonOps.INSTANCE, component)
                .result().orElseThrow();
        OscillatorTrackerComponent decoded = OscillatorTrackerComponent.CODEC.parse(JsonOps.INSTANCE, json)
                .result().orElseThrow();

        assertEquals(component, decoded);
        assertEquals(TARGET, decoded.target().orElseThrow());
        assertTrue(decoded.tracked());
        assertEquals("home", decoded.name());
    }

    @Test
    void componentCodecAppliesDefaultsForMissingFields() {
        // Only "target" present: tracked defaults to true, name to "".
        OscillatorTrackerComponent decoded = OscillatorTrackerComponent.CODEC.parse(JsonOps.INSTANCE,
                        com.google.gson.JsonParser.parseString("{\"target\":{\"dimension\":\"minecraft:overworld\",\"pos\":[1,2,3]}}"))
                .result().orElseThrow();

        assertTrue(decoded.tracked());
        assertEquals("", decoded.name());
        assertEquals(GlobalPos.create(World.OVERWORLD, new BlockPos(1, 2, 3)), decoded.target().orElseThrow());
    }

    @Test
    void componentPacketCodecRoundTripsThroughBuffer() {
        OscillatorTrackerComponent component = sampleComponent();
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());

        OscillatorTrackerComponent.PACKET_CODEC.encode(buf, component);
        OscillatorTrackerComponent decoded = OscillatorTrackerComponent.PACKET_CODEC.decode(buf);

        assertEquals(component, decoded);
    }

    @Test
    void listCodecRoundTripsThroughJson() {
        OscillatorTrackerComponentList list = new OscillatorTrackerComponentList(1,
                List.of(sampleComponent(), new OscillatorTrackerComponent(Optional.empty(), false, "")));

        JsonElement json = OscillatorTrackerComponentList.CODEC.encodeStart(JsonOps.INSTANCE, list)
                .result().orElseThrow();
        OscillatorTrackerComponentList decoded = OscillatorTrackerComponentList.CODEC.parse(JsonOps.INSTANCE, json)
                .result().orElseThrow();

        assertEquals(1, decoded.selectedLocation());
        assertEquals(2, decoded.trackers().size());
        assertEquals(list, decoded);
    }

    @Test
    void listCodecDefaultsToEmptySelection() {
        OscillatorTrackerComponentList decoded = OscillatorTrackerComponentList.CODEC.parse(JsonOps.INSTANCE,
                        com.google.gson.JsonParser.parseString("{}"))
                .result().orElseThrow();

        assertEquals(0, decoded.selectedLocation());
        assertTrue(decoded.trackers().isEmpty());
    }

    @Test
    void listPacketCodecRoundTripsThroughBuffer() {
        OscillatorTrackerComponentList list = new OscillatorTrackerComponentList(0, List.of(sampleComponent()));
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());

        OscillatorTrackerComponentList.PACKET_CODEC.encode(buf, list);
        OscillatorTrackerComponentList decoded = OscillatorTrackerComponentList.PACKET_CODEC.decode(buf);

        assertEquals(list, decoded);
    }

    @Test
    void listRejectsMoreThan256Trackers() {
        List<OscillatorTrackerComponent> tooMany = new ArrayList<>();
        for (int i = 0; i < 257; i++) {
            tooMany.add(new OscillatorTrackerComponent(Optional.empty(), true, ""));
        }

        assertThrows(IllegalArgumentException.class, () -> new OscillatorTrackerComponentList(0, tooMany));
    }

    @Test
    void componentAttachesToItemStackAndReadsBack() {
        ItemStack stack = new ItemStack(Items.STICK);
        OscillatorTrackerComponentList list = new OscillatorTrackerComponentList(0, List.of(sampleComponent()));

        stack.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, list);

        assertEquals(list, stack.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST));
    }

    @Test
    void getTrackedPosReturnsNullWithoutComponent() {
        ItemStack stack = new ItemStack(Items.STICK);

        assertNull(CruxResonator.getTrackedPos(stack));
    }

    @Test
    void getTrackedPosReturnsNullWithEmptyTarget() {
        ItemStack stack = new ItemStack(Items.STICK);
        stack.set(ModComponentTypes.OSCILLATOR_TRACKER, new OscillatorTrackerComponent(Optional.empty(), true, ""));

        assertNull(CruxResonator.getTrackedPos(stack));
    }

    @Test
    void getTrackedPosReturnsTargetWhenPresent() {
        ItemStack stack = new ItemStack(Items.STICK);
        stack.set(ModComponentTypes.OSCILLATOR_TRACKER, sampleComponent());

        assertEquals(TARGET, CruxResonator.getTrackedPos(stack));
    }

    @Test
    void hasGlintOnlyWhenTrackerComponentPresent() {
        ItemStack without = new ItemStack(ModItems.CRUX_RESONATOR);
        assertFalse(ModItems.CRUX_RESONATOR.hasGlint(without));

        ItemStack with = new ItemStack(ModItems.CRUX_RESONATOR);
        with.set(ModComponentTypes.OSCILLATOR_TRACKER, sampleComponent());
        assertTrue(ModItems.CRUX_RESONATOR.hasGlint(with));
    }
}
