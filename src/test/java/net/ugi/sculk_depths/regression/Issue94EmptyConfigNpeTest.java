package net.ugi.sculk_depths.regression;

import net.fabricmc.loader.api.FabricLoader;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.config.Config;
import net.ugi.sculk_depths.config.ConfigHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Regression tests for issue #94.
 *
 * config/Config.java:19-27 — when the config file exists but is empty (or only
 * whitespace), gson.fromJson returns null and SculkDepths.CONFIG stays null,
 * causing NPEs downstream (e.g. GlomperEntity.java:67 reads CONFIG.glomper_health).
 *
 * These assert the CORRECT behavior (a usable default config), so they FAIL on the
 * current buggy code. That failure is the expected proof of the bug.
 */
class Issue94EmptyConfigNpeTest {

    private Path configFile;

    @BeforeEach
    void setUp() throws IOException {
        configFile = FabricLoader.getInstance().getConfigDir().resolve(SculkDepths.MOD_ID + "_config.json");
        Files.createDirectories(configFile.getParent());
        Files.deleteIfExists(configFile);
        SculkDepths.CONFIG = null;
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(configFile);
        SculkDepths.CONFIG = new ConfigHandler();
    }

    @Test
    void emptyConfigFileYieldsNonNullConfigWithDefaults() throws IOException {
        Files.writeString(configFile, "");

        Config.loadConfig();

        assertNotNull(SculkDepths.CONFIG,
                "issue #94: loading an empty config file must yield a non-null config with defaults");
        assertEquals(2, SculkDepths.CONFIG.config_version);
        assertEquals(20.0, SculkDepths.CONFIG.glomper_health);
    }

    @Test
    void whitespaceOnlyConfigFileYieldsNonNullConfigWithDefaults() throws IOException {
        Files.writeString(configFile, "  \n\t  \n");

        Config.loadConfig();

        assertNotNull(SculkDepths.CONFIG,
                "issue #94: loading a whitespace-only config file must yield a non-null config with defaults");
        assertEquals(2, SculkDepths.CONFIG.config_version);
        assertEquals(20.0, SculkDepths.CONFIG.glomper_health);
    }
}
