package net.ugi.sculk_depths;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Bootstrap;
import net.minecraft.SharedConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Baseline harness test: proves that fabric-loader-junit boots the loader
 * and that the Minecraft bootstrap (registries) is available in tests.
 * This test MUST pass; if it fails the harness itself is broken.
 */
class HarnessBaselineTest {

    @BeforeAll
    static void bootstrapMinecraft() {
        SharedConstants.createGameVersion();
        Bootstrap.initialize();
    }

    @Test
    void fabricLoaderIsAvailableInTestEnvironment() {
        assertTrue(FabricLoader.getInstance().isDevelopmentEnvironment());
    }

    @Test
    void minecraftRegistriesAreBootstrapped() {
        // Bootstrap has run under fabric-loader-junit; vanilla content is present.
        assertNotNull(net.minecraft.registry.Registries.BLOCK.get(net.minecraft.util.Identifier.of("minecraft", "stone")));
    }

    @Test
    void junitAssertionsWork() {
        assertEquals(2, 1 + 1);
    }
}
