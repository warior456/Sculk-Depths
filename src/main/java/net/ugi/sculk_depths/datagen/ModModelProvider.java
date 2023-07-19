package net.ugi.sculk_depths.datagen;


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        //blockStateModelGenerator.registerPressurePlate(ModBlocks.VALTROX_PRESSURE_PLATE, ModBlocks.VALTROX_PLANKS);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        //itemModelGenerator.register(ModBlocks.VALTROX_PRESSURE_PLATE.asItem(), Models.);
    }
}
