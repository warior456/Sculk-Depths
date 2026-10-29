package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.data.BlockFamilies;
import net.minecraft.resources.ResourceLocation;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;

import java.util.ArrayList;
import java.util.List;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput withResultSlot) {
        super(withResultSlot);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        /*
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RUBY_BLOCK);
         */
        BlockStateModelGenerator.BlockTexturePool valtroxPlankPool = blockStateModelGenerator.family(ModBlocks.VALTROX_PLANKS);
        BlockStateModelGenerator.BlockTexturePool coatedValtroxPlankPool = blockStateModelGenerator.family(ModBlocks.COATED_VALTROX_PLANKS);
        BlockStateModelGenerator.BlockTexturePool driedValtroxPlankPool = blockStateModelGenerator.family(ModBlocks.DRIED_VALTROX_PLANKS);
        BlockStateModelGenerator.BlockTexturePool petrifiedValtroxPlankPool = blockStateModelGenerator.family(ModBlocks.PETRIFIED_VALTROX_SLATES);
        BlockStateModelGenerator.BlockTexturePool polishedAmalgamitekPool = blockStateModelGenerator.family(ModBlocks.POLISHED_AMALGAMITE);
        BlockStateModelGenerator.BlockTexturePool amalgamiteBrickPool = blockStateModelGenerator.family(ModBlocks.AMALGAMITE_BRICKS);
        BlockStateModelGenerator.BlockTexturePool flumrockTilesPool = blockStateModelGenerator.family(ModBlocks.FLUMROCK_TILES);

        blockStateModelGenerator.createTrivialCube(ModBlocks.QUAZARITH_BLOCK);
        blockStateModelGenerator.createTrivialCube(ModBlocks.CRUX_BLOCK);
        //blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.AURIC_SPORE_BLOCK);

        polishedAmalgamitekPool.stairs(ModBlocks.POLISHED_AMALGAMITE_STAIRS);
        polishedAmalgamitekPool.sandSlab(ModBlocks.POLISHED_AMALGAMITE_SLAB);
        polishedAmalgamitekPool.sandstone(ModBlocks.POLISHED_AMALGAMITE_WALL);

        blockStateModelGenerator.createTrivialCube(ModBlocks.CRACKED_AMALGAMITE_BRICKS);
        amalgamiteBrickPool.stairs(ModBlocks.AMALGAMITE_BRICK_STAIRS);
        amalgamiteBrickPool.sandSlab(ModBlocks.AMALGAMITE_BRICK_SLAB);
        amalgamiteBrickPool.sandstone(ModBlocks.AMALGAMITE_BRICK_WALL);

        blockStateModelGenerator.createTrivialBlock(ModBlocks.ACTIVATED_AMALGAMITE, TexturedModel.COLUMN);


        blockStateModelGenerator.createTrivialCube(ModBlocks.CRUMBLING_FLUMROCK_TILES);
        flumrockTilesPool.stairs(ModBlocks.FLUMROCK_TILE_STAIRS);
        flumrockTilesPool.sandSlab(ModBlocks.FLUMROCK_TILE_SLAB);
        flumrockTilesPool.sandstone(ModBlocks.FLUMROCK_TILE_WALL);

        blockStateModelGenerator.createCrossBlockWithDefaultItem(ModBlocks.DEAD_GRASS, BlockStateModelGenerator.TintType.NOT_TINTED);


        blockStateModelGenerator.createPlant(ModBlocks.VALTROX_SAPLING, ModBlocks.POTTED_VALTROX_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.createPlant(ModBlocks.PENEBRIUM_SHROOM, ModBlocks.POTTED_PENEBRIUM_SHROOM, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.createPlant(ModBlocks.AURIC_SPORE_SPROUTS, ModBlocks.POTTED_AURIC_SPORE_SPROUTS, BlockStateModelGenerator.TintType.NOT_TINTED);

        valtroxPlankPool.fenceGate(ModBlocks.VALTROX_FENCE_GATE);
        valtroxPlankPool.fenceState(ModBlocks.VALTROX_FENCE);
        valtroxPlankPool.addButton(ModBlocks.VALTROX_BUTTON);
        valtroxPlankPool.sandSlab(ModBlocks.VALTROX_SLAB);
        valtroxPlankPool.stairs(ModBlocks.VALTROX_STAIRS);
        blockStateModelGenerator.woodProvider(ModBlocks.VALTROX_LOG).log(ModBlocks.VALTROX_LOG).wood(ModBlocks.VALTROX_WOOD);
        blockStateModelGenerator.woodProvider(ModBlocks.STRIPPED_VALTROX_LOG).log(ModBlocks.STRIPPED_VALTROX_LOG).wood(ModBlocks.STRIPPED_VALTROX_WOOD);
        valtroxPlankPool.pressurePlate(ModBlocks.VALTROX_PRESSURE_PLATE);
        valtroxPlankPool.generateFor(BlockFamilies.register(ModBlocks.VALTROX_PLANKS).signCommand(ModBlocks.VALTROX_SIGN, ModBlocks.VALTROX_WALL_SIGN).build());
        blockStateModelGenerator.createHangingSign(ModBlocks.STRIPPED_VALTROX_LOG, ModBlocks.VALTROX_HANGING_SIGN, ModBlocks.VALTROX_WALL_HANGING_SIGN);
        blockStateModelGenerator.createDoor(ModBlocks.VALTROX_DOOR);
        blockStateModelGenerator.createOrientableTrapdoor(ModBlocks.VALTROX_TRAPDOOR);

        coatedValtroxPlankPool.fenceGate(ModBlocks.COATED_VALTROX_FENCE_GATE);
        coatedValtroxPlankPool.fenceState(ModBlocks.COATED_VALTROX_FENCE);
        coatedValtroxPlankPool.addButton(ModBlocks.COATED_VALTROX_BUTTON);
        coatedValtroxPlankPool.sandSlab(ModBlocks.COATED_VALTROX_SLAB);
        coatedValtroxPlankPool.stairs(ModBlocks.COATED_VALTROX_STAIRS);
        blockStateModelGenerator.woodProvider(ModBlocks.COATED_VALTROX_LOG).log(ModBlocks.COATED_VALTROX_LOG).wood(ModBlocks.COATED_VALTROX_WOOD);
        blockStateModelGenerator.woodProvider(ModBlocks.COATED_STRIPPED_VALTROX_LOG).log(ModBlocks.COATED_STRIPPED_VALTROX_LOG).wood(ModBlocks.COATED_STRIPPED_VALTROX_WOOD);
        coatedValtroxPlankPool.pressurePlate(ModBlocks.COATED_VALTROX_PRESSURE_PLATE);
        coatedValtroxPlankPool.generateFor(BlockFamilies.register(ModBlocks.COATED_VALTROX_PLANKS).signCommand(ModBlocks.COATED_VALTROX_SIGN, ModBlocks.COATED_VALTROX_WALL_SIGN).build());
        blockStateModelGenerator.createHangingSign(ModBlocks.COATED_STRIPPED_VALTROX_LOG, ModBlocks.COATED_VALTROX_HANGING_SIGN, ModBlocks.COATED_VALTROX_WALL_HANGING_SIGN);
        blockStateModelGenerator.createDoor(ModBlocks.COATED_VALTROX_DOOR);
        blockStateModelGenerator.createOrientableTrapdoor(ModBlocks.COATED_VALTROX_TRAPDOOR);


        driedValtroxPlankPool.fenceGate(ModBlocks.DRIED_VALTROX_FENCE_GATE);
        driedValtroxPlankPool.fenceState(ModBlocks.DRIED_VALTROX_FENCE);
        driedValtroxPlankPool.addButton(ModBlocks.DRIED_VALTROX_BUTTON);
        driedValtroxPlankPool.sandSlab(ModBlocks.DRIED_VALTROX_SLAB);
        driedValtroxPlankPool.stairs(ModBlocks.DRIED_VALTROX_STAIRS);
        blockStateModelGenerator.woodProvider(ModBlocks.DRIED_VALTROX_LOG).log(ModBlocks.DRIED_VALTROX_LOG).wood(ModBlocks.DRIED_VALTROX_WOOD);
        blockStateModelGenerator.woodProvider(ModBlocks.STRIPPED_DRIED_VALTROX_LOG).log(ModBlocks.STRIPPED_DRIED_VALTROX_LOG).wood(ModBlocks.STRIPPED_DRIED_VALTROX_WOOD);
        driedValtroxPlankPool.pressurePlate(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE);
        driedValtroxPlankPool.generateFor(BlockFamilies.register(ModBlocks.DRIED_VALTROX_PLANKS).signCommand(ModBlocks.DRIED_VALTROX_SIGN, ModBlocks.DRIED_VALTROX_WALL_SIGN).build());
        blockStateModelGenerator.createHangingSign(ModBlocks.STRIPPED_DRIED_VALTROX_LOG, ModBlocks.DRIED_VALTROX_HANGING_SIGN, ModBlocks.DRIED_VALTROX_WALL_HANGING_SIGN);
        blockStateModelGenerator.createDoor(ModBlocks.DRIED_VALTROX_DOOR);
        blockStateModelGenerator.createOrientableTrapdoor(ModBlocks.DRIED_VALTROX_TRAPDOOR);

        petrifiedValtroxPlankPool.fenceGate(ModBlocks.PETRIFIED_VALTROX_WALL_GATE);
        petrifiedValtroxPlankPool.sandstone(ModBlocks.PETRIFIED_VALTROX_WALL);
        petrifiedValtroxPlankPool.addButton(ModBlocks.PETRIFIED_VALTROX_BUTTON);
        petrifiedValtroxPlankPool.sandSlab(ModBlocks.PETRIFIED_VALTROX_SLAB);
        petrifiedValtroxPlankPool.stairs(ModBlocks.PETRIFIED_VALTROX_STAIRS);
        blockStateModelGenerator.woodProvider(ModBlocks.PETRIFIED_VALTROX_LOG).log(ModBlocks.PETRIFIED_VALTROX_LOG).wood(ModBlocks.PETRIFIED_VALTROX_WOOD);
        blockStateModelGenerator.woodProvider(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG).log(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG).wood(ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD);
        petrifiedValtroxPlankPool.pressurePlate(ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE);
        petrifiedValtroxPlankPool.generateFor(BlockFamilies.register(ModBlocks.PETRIFIED_VALTROX_SLATES).signCommand(ModBlocks.PETRIFIED_VALTROX_SIGN, ModBlocks.PETRIFIED_VALTROX_WALL_SIGN).build());
        blockStateModelGenerator.createHangingSign(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG, ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN, ModBlocks.PETRIFIED_VALTROX_WALL_HANGING_SIGN);
        blockStateModelGenerator.createDoor(ModBlocks.PETRIFIED_VALTROX_DOOR);
        blockStateModelGenerator.createOrientableTrapdoor(ModBlocks.PETRIFIED_VALTROX_TRAPDOOR);

        List<String> suffixList = new ArrayList<>();
        suffixList.add("");
        suffixList.add("2");
        suffixList.add("3");
        suffixList.add("4");

        registerBlockWithVariants(blockStateModelGenerator,ModBlocks.AMALGAMITE,suffixList, Models.COLUMN);

    }

    private void registerBlockWithVariants(BlockStateModelGenerator blockStateModelGenerator, Block block, List<String> suffixList, Model model) {

        List<ResourceLocation> identifierList = new ArrayList<>();
        suffixList.forEach(playerSuffix -> {

           //VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, TexturedModel.CUBE_COLUMN.upload(block, suffix, blockStateModelGenerator.modelCollector)));

            identifierList.add(ModelIds.getBlockSubModelId(ModBlocks.AMALGAMITE, playerSuffix));
            TextureMap textureMap = (new TextureMap()).put(TextureKey.END_NOISE_SETTINGS,ModelIds.getBlockSubModelId(ModBlocks.AMALGAMITE, "_end" + playerSuffix)).put(TextureKey.SIDE,ModelIds.getBlockSubModelId(ModBlocks.AMALGAMITE, "_side" + playerSuffix));
            model.upload(ModelIds.getBlockSubModelId(ModBlocks.AMALGAMITE, playerSuffix), textureMap, blockStateModelGenerator.modelOutput);
        });

        blockStateModelGenerator.blockStateOutput.accept(createBlockStateWithVariants(block,identifierList));

    }



    public static VariantsBlockStateSupplier createBlockStateWithVariants(Block block, List<ResourceLocation> modelIdList) {


        BlockStateVariant[] blockStateVariants = new BlockStateVariant[modelIdList.size()];
        final int[] i = {0};
        modelIdList.forEach(modelId -> {
            blockStateVariants[i[0]] = BlockStateVariant.create().put(VariantSettings.MODEL, modelId);
            i[0]++;
        });
        return VariantsBlockStateSupplier.create(block, blockStateVariants);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        /*
        itemModelGenerator.register(ModItems.RUBY, Models.GENERATED);
         */
        itemModelGenerator.register(ModItems.CRUX, Models.GENERATED);
        itemModelGenerator.register(ModItems.MUSIC_DISC_ZINNIA, Models.GENERATED);
    }
}
