package net.ugi.sculk_depths.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static net.ugi.sculk_depths.SculkDepths.config;

public class Config {
    public static final TypeAdapter<Item> ITEM_TYPE_ADAPTER = new TypeAdapter<>() {
        @Override
        public void write(JsonWriter out, Item value) throws IOException {
            out.value(Registries.ITEM.getId(value).toString());
        }

        @Override
        public Item read(JsonReader in) throws IOException {
            return Registries.ITEM.get(Identifier.tryParse(in.nextString()));
        }
    };
    public static final Gson GSON = new GsonBuilder().registerTypeAdapter(Item.class, ITEM_TYPE_ADAPTER).setPrettyPrinting().create();

    public static void loadConfig() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), SculkDepths.MOD_ID + "_config.json");
        if (configFile.exists()) {
            try (FileReader fileReader = new FileReader(configFile)) {
                config = GSON.fromJson(fileReader, ConfigHandler.class);
                saveConfig(); //update config
            } catch (IOException e) {
                SculkDepths.LOGGER.warn("the config was not loaded: " + e.getLocalizedMessage());
            }
        } else {
            config = new ConfigHandler();
            saveConfig();
        }
    }

    public static void saveConfig() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), SculkDepths.MOD_ID + "_config.json");
        if (!configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdir();
        }
        try (FileWriter fileWriter = new FileWriter(configFile)) {
            fileWriter.write(GSON.toJson(config));
        } catch (IOException e) {
            SculkDepths.LOGGER.warn("the config was not saved: " + e.getLocalizedMessage());
        }
    }
}
