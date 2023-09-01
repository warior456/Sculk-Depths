package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    //examples:
    ////https://github.com/Tutorials-By-Kaupenjoe/Fabric-Tutorial-1.20.X/blob/a1ce57390adb5ef0f1cd6ba7a0bf6b1b659074f5/src/main/java/net/kaupenjoe/tutorialmod/datagen/ModRecipeProvider.java
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

    }


}