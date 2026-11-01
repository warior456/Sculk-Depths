package net.ugi.sculk_depths.config;

import net.fabricmc.loader.api.FabricLoader;
import net.ugi.sculk_depths.SculkDepths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Normal-behavior tests for {@link Config}: valid files parse, defaults are used
 * when no file exists, and save/load round-trips are stable.
 */
class ConfigLoadTest {

    private Path configFile;

    @BeforeEach
    void setUp() throws IOException {
        configFile = FabricLoader.getInstance().getConfigDir().resolve(SculkDepths.MOD_ID + "_config.json");
        Files.createDirectoriesSafe(configFile.getParent());
        Files.deleteIfExists(configFile);
        SculkDepths.CONFIG = null;
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(configFile);
        // Leave a sane global state for other tests running in the same JVM.
        SculkDepths.CONFIG = new ConfigHandler();
    }

    @Test
    void missingFileCreatesDefaultConfigAndWritesIt() {
        Config.loadConfig();

        assertNotNull(SculkDepths.CONFIG);
        assertValueEqual(2, SculkDepths.CONFIG.config_version);
        assertValueEqual(20.0, SculkDepths.CONFIG.glomper_health);
        assertValueEqual(20.0f, SculkDepths.CONFIG.glomper_damage);
        assertValueEqual(15, SculkDepths.CONFIG.activate_portal_durability_usage);
        assertValueEqual(4, SculkDepths.CONFIG.quazarith_ingot_kryslum_cost);
        assertValueEqual(5, SculkDepths.CONFIG.quazarith_helmet_kryslum_cost);
        assertTrue(Files.exists(configFile), "loadConfig should persist the default config");
    }

    @Test
    void validFileParsesIntoExpectedValues() throws IOException {
        Files.writeUtf(configFile, """
                {
                  "config_version": 2,
                  "glomper_health": 42.0,
                  "quazarith_boots_crux_cost": 9
                }
                """);

        Config.loadConfig();

        assertNotNull(SculkDepths.CONFIG);
        assertValueEqual(42.0, SculkDepths.CONFIG.glomper_health);
        assertValueEqual(9, SculkDepths.CONFIG.quazarith_boots_crux_cost);
        // Fields absent from the file keep their declared defaults.
        assertValueEqual(5, SculkDepths.CONFIG.quazarith_helmet_kryslum_cost);
        assertValueEqual(15, SculkDepths.CONFIG.activate_portal_durability_usage);
    }

    @Test
    void emptyJsonObjectYieldsDefaults() throws IOException {
        Files.writeUtf(configFile, "{}");

        Config.loadConfig();

        assertNotNull(SculkDepths.CONFIG);
        assertValueEqual(2, SculkDepths.CONFIG.config_version);
        assertValueEqual(20.0, SculkDepths.CONFIG.glomper_health);
    }

    @Test
    void saveLoadRoundTripIsStable() throws IOException {
        Config.loadConfig();
        SculkDepths.CONFIG.glomper_health = 33.0;
        Config.saveConfig();
        String savedOnce = Files.readString(configFile);

        Config.loadConfig();

        assertValueEqual(33.0, SculkDepths.CONFIG.glomper_health);
        assertValueEqual(2, SculkDepths.CONFIG.config_version);
        // loadConfig re-saves; the serialized form must be identical.
        assertValueEqual(savedOnce, Files.readString(configFile), "save/load round-trip should be stable");
    }
}
