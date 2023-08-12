package net.ugi.sculk_depths.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.ugi.sculk_depths.SculkDepths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static net.ugi.sculk_depths.SculkDepths.CONFIG;

public class Config {
    public static void loadConfig() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), SculkDepths.MOD_ID + "_config.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (configFile.exists()) {
            try {
                FileReader fileReader = new FileReader(configFile);
                CONFIG = gson.fromJson(fileReader, ConfigHandler.class);
                fileReader.close();
                saveConfig(); //update config
            } catch (IOException e) {
                SculkDepths.LOGGER.warn("the config was not loaded: " + e.getLocalizedMessage());
            }
        } else {
            CONFIG = new ConfigHandler();
            saveConfig();
        }
    }

    public static void saveConfig() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), SculkDepths.MOD_ID + "_config.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (!configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdir();
        }
        try {
            FileWriter fileWriter = new FileWriter(configFile);
            fileWriter.write(gson.toJson(CONFIG));
            fileWriter.close();
        } catch (IOException e) {
            SculkDepths.LOGGER.warn("the config was not saved: " + e.getLocalizedMessage());
        }
    }
}
