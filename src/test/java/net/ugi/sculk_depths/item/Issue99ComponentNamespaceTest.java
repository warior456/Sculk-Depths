package net.ugi.sculk_depths.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.ugi.sculk_depths.TestBootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Regression guard for issue #99: data-component types must be registered under the
 * sculk_depths namespace, not the default minecraft namespace.
 */
class Issue99ComponentNamespaceTest {

    @BeforeAll
    static void setup() {
        TestBootstrap.init();
    }

    @Test
    void oscillatorTrackerComponentUsesModNamespace() {
        ResourceLocation id = BuiltInRegistries.DATA_COMPONENT_TYPE.getId(ModComponentTypes.OSCILLATOR_TRACKER);
        assertValueEqual("sculk_depths", id.extractNamespace(),
                "quazarith_oscillator_tracker must be registered under the sculk_depths namespace");
    }

    @Test
    void oscillatorTrackerListComponentUsesModNamespace() {
        ResourceLocation id = BuiltInRegistries.DATA_COMPONENT_TYPE.getId(ModComponentTypes.OSCILLATOR_TRACKER_LIST);
        assertValueEqual("sculk_depths", id.extractNamespace(),
                "oscillator_tracker_list must be registered under the sculk_depths namespace");
    }
}
