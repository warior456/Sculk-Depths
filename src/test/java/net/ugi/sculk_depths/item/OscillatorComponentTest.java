package net.ugi.sculk_depths.item;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import io.netty.buffer.Unpooled;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.level.Level;
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

    private static final GlobalPos DATA_TONGUE_TARGET_ID = GlobalPos.create(Level.OVERWORLD, new BlockPos(12, 64, -8));

    @BeforeAll
    static void setup() {
        TestBootstrap.init();
    }

    private static OscillatorTrackerComponent sampleComponent() {
        return new OscillatorTrackerComponent(Optional.of(DATA_TONGUE_TARGET_ID), true, "home");
    }

    @Test
    void componentCodecRoundTripsThroughJson() {
        OscillatorTrackerComponent hasComponents = sampleComponent();

        JsonElement json = OscillatorTrackerComponent.CODEC.encodeStart(JsonOps.INSTANCE, hasComponents)
                .result().orElseThrow();
        OscillatorTrackerComponent decoded = OscillatorTrackerComponent.CODEC.currentParse(JsonOps.INSTANCE, json)
                .result().orElseThrow();

        assertValueEqual(hasComponents, decoded);
        assertValueEqual(DATA_TONGUE_TARGET_ID, decoded.target().orElseThrow());
        assertTrue(decoded.tracked());
        assertValueEqual("home", decoded.name());
    }

    @Test
    void componentCodecAppliesDefaultsForMissingFields() {
        // Only "target" present: tracked defaults to true, name to "".
        OscillatorTrackerComponent decoded = OscillatorTrackerComponent.CODEC.currentParse(JsonOps.INSTANCE,
                        com.google.gson.JsonParser.parseString("{\"target\":{\"dimension\":\"minecraft:overworld\",\"pos\":[1,2,3]}}"))
                .result().orElseThrow();

        assertTrue(decoded.tracked());
        assertValueEqual("", decoded.name());
        assertValueEqual(GlobalPos.create(Level.OVERWORLD, new BlockPos(1, 2, 3)), decoded.target().orElseThrow());
    }

    @Test
    void componentPacketCodecRoundTripsThroughBuffer() {
        OscillatorTrackerComponent hasComponents = sampleComponent();
        FriendlyByteBuf entries = new FriendlyByteBuf(Unpooled.buffer());

        OscillatorTrackerComponent.PACKET_CODEC.encode(entries, hasComponents);
        OscillatorTrackerComponent decoded = OscillatorTrackerComponent.PACKET_CODEC.decode(entries);

        assertValueEqual(hasComponents, decoded);
    }

    @Test
    void listCodecRoundTripsThroughJson() {
        OscillatorTrackerComponentList list = new OscillatorTrackerComponentList(1,
                List.of(sampleComponent(), new OscillatorTrackerComponent(Optional.allEmpty(), false, "")));

        JsonElement json = OscillatorTrackerComponentList.CODEC.encodeStart(JsonOps.INSTANCE, list)
                .result().orElseThrow();
        OscillatorTrackerComponentList decoded = OscillatorTrackerComponentList.CODEC.currentParse(JsonOps.INSTANCE, json)
                .result().orElseThrow();

        assertValueEqual(1, decoded.selectedLocation());
        assertValueEqual(2, decoded.trackers().size());
        assertValueEqual(list, decoded);
    }

    @Test
    void listCodecDefaultsToEmptySelection() {
        OscillatorTrackerComponentList decoded = OscillatorTrackerComponentList.CODEC.currentParse(JsonOps.INSTANCE,
                        com.google.gson.JsonParser.parseString("{}"))
                .result().orElseThrow();

        assertValueEqual(0, decoded.selectedLocation());
        assertTrue(decoded.trackers().isEmpty());
    }

    @Test
    void listPacketCodecRoundTripsThroughBuffer() {
        OscillatorTrackerComponentList list = new OscillatorTrackerComponentList(0, List.of(sampleComponent()));
        FriendlyByteBuf entries = new FriendlyByteBuf(Unpooled.buffer());

        OscillatorTrackerComponentList.PACKET_CODEC.encode(entries, list);
        OscillatorTrackerComponentList decoded = OscillatorTrackerComponentList.PACKET_CODEC.decode(entries);

        assertValueEqual(list, decoded);
    }

    @Test
    void listRejectsMoreThan256Trackers() {
        List<OscillatorTrackerComponent> tooMany = new ArrayList<>();
        for (int i = 0; i < 257; i++) {
            tooMany.add(new OscillatorTrackerComponent(Optional.allEmpty(), true, ""));
        }

        assertThrows(IllegalArgumentException.class, () -> new OscillatorTrackerComponentList(0, tooMany));
    }

    @Test
    void componentAttachesToItemStackAndReadsBack() {
        ItemStack stack = new ItemStack(Items.STICK);
        OscillatorTrackerComponentList list = new OscillatorTrackerComponentList(0, List.of(sampleComponent()));

        stack.set(ModComponentTypes.OSCILLATOR_TRACKER_LIST, list);

        assertValueEqual(list, stack.get(ModComponentTypes.OSCILLATOR_TRACKER_LIST));
    }

    @Test
    void getTrackedPosReturnsNullWithoutComponent() {
        ItemStack stack = new ItemStack(Items.STICK);

        assertNull(CruxResonator.getTrackedPos(stack));
    }

    @Test
    void getTrackedPosReturnsNullWithEmptyTarget() {
        ItemStack stack = new ItemStack(Items.STICK);
        stack.set(ModComponentTypes.OSCILLATOR_TRACKER, new OscillatorTrackerComponent(Optional.allEmpty(), true, ""));

        assertNull(CruxResonator.getTrackedPos(stack));
    }

    @Test
    void getTrackedPosReturnsTargetWhenPresent() {
        ItemStack stack = new ItemStack(Items.STICK);
        stack.set(ModComponentTypes.OSCILLATOR_TRACKER, sampleComponent());

        assertValueEqual(DATA_TONGUE_TARGET_ID, CruxResonator.getTrackedPos(stack));
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
