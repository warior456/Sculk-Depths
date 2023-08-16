package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.ugi.sculk_depths.block.ModBlocks;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        /*
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RUBY_BLOCK);
         */
        BlockStateModelGenerator.BlockTexturePool valtroxPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.VALTROX_PLANKS);
        BlockStateModelGenerator.BlockTexturePool driedValtroxPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.DRIED_VALTROX_PLANKS);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PENEBRIUM_SHINE_SHROOM_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PENEBRIUM_SHINE_SHROOM_SPORE_BLOCK);


    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        /*
        itemModelGenerator.register(ModItems.RUBY, Models.GENERATED);
         */
    }
}
