package net.ugi.sculk_depths;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.ugi.sculk_depths.datagen.ModLootTableGenerator;
import net.ugi.sculk_depths.datagen.ModModelGenerator;


public class SculkDepthsDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModLootTableGenerator::new);
        //pack.addProvider(ModRecipeGenerator::new);
        pack.addProvider(ModModelGenerator::new);
    }
}
