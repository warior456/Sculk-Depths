package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.ugi.sculk_depths.block.ModBlocks;

public class ModLootTableGenerator extends FabricBlockLootTableProvider {

    public ModLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.VALTROX_PRESSURE_PLATE, drops(ModBlocks.VALTROX_PRESSURE_PLATE.asItem()));
        addDrop(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE, drops(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE.asItem()));
        addDrop(ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE, drops(ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE.asItem()));
    }
}

