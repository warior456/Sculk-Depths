package net.ugi.sculk_depths.datagen;


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class ModModelGenerator extends FabricModelProvider {
    public ModModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        //blockStateModelGenerator.registerPressurePlate(ModBlocks.VALTROX_PRESSURE_PLATE, ModBlocks.VALTROX_PLANKS);
        //blockStateModelGenerator.registerPressurePlate(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE, ModBlocks.DRIED_VALTROX_PLANKS);
        //blockStateModelGenerator.registerPressurePlate(ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE, ModBlocks.PETRIFIED_VALTROX_SLATES);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        //itemModelGenerator.register(ModBlocks.VALTROX_PRESSURE_PLATE.asItem(), Models.);
    }
}
