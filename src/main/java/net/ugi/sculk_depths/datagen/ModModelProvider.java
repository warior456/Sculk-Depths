package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        /*
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RUBY_BLOCK);
         */
        BlockStateModelGenerator.BlockTexturePool valtroxPlankPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.VALTROX_PLANKS);
        BlockStateModelGenerator.BlockTexturePool coatedValtroxPlankPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.COATED_VALTROX_PLANKS);
        BlockStateModelGenerator.BlockTexturePool driedValtroxPlankPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.DRIED_VALTROX_PLANKS);
        BlockStateModelGenerator.BlockTexturePool petrifiedValtroxPlankPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.PETRIFIED_VALTROX_SLATES);
        BlockStateModelGenerator.BlockTexturePool polishedAmalgamitekPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.POLISHED_AMALGAMITE);
        BlockStateModelGenerator.BlockTexturePool amalgamiteBrickPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.AMALGAMITE_BRICKS);
        BlockStateModelGenerator.BlockTexturePool flumrockTilesPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.FLUMROCK_TILES);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.QUAZARITH_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CRUX_BLOCK);
        //blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.AURIC_SPORE_BLOCK);

        polishedAmalgamitekPool.stairs(ModBlocks.POLISHED_AMALGAMITE_STAIRS);
        polishedAmalgamitekPool.slab(ModBlocks.POLISHED_AMALGAMITE_SLAB);
        polishedAmalgamitekPool.wall(ModBlocks.POLISHED_AMALGAMITE_WALL);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CRACKED_AMALGAMITE_BRICKS);
        amalgamiteBrickPool.stairs(ModBlocks.AMALGAMITE_BRICK_STAIRS);
        amalgamiteBrickPool.slab(ModBlocks.AMALGAMITE_BRICK_SLAB);
        amalgamiteBrickPool.wall(ModBlocks.AMALGAMITE_BRICK_WALL);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CRUMBLING_FLUMROCK_TILES);
        flumrockTilesPool.stairs(ModBlocks.FLUMROCK_TILE_STAIRS);
        flumrockTilesPool.slab(ModBlocks.FLUMROCK_TILE_SLAB);
        flumrockTilesPool.wall(ModBlocks.FLUMROCK_TILE_WALL);


        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.VALTROX_SAPLING, ModBlocks.POTTED_VALTROX_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.PENEBRIUM_SHROOM, ModBlocks.POTTED_PENEBRIUM_SHROOM, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.AURIC_SPORE_SPROUTS, ModBlocks.POTTED_AURIC_SPORE_SPROUTS, BlockStateModelGenerator.TintType.NOT_TINTED);

        valtroxPlankPool.fenceGate(ModBlocks.VALTROX_FENCE_GATE);
        valtroxPlankPool.fence(ModBlocks.VALTROX_FENCE);
        valtroxPlankPool.button(ModBlocks.VALTROX_BUTTON);
        valtroxPlankPool.slab(ModBlocks.VALTROX_SLAB);
        valtroxPlankPool.stairs(ModBlocks.VALTROX_STAIRS);
        blockStateModelGenerator.registerLog(ModBlocks.VALTROX_LOG).log(ModBlocks.VALTROX_LOG).wood(ModBlocks.VALTROX_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_VALTROX_LOG).log(ModBlocks.STRIPPED_VALTROX_LOG).wood(ModBlocks.STRIPPED_VALTROX_WOOD);
        valtroxPlankPool.pressurePlate(ModBlocks.VALTROX_PRESSURE_PLATE);
        valtroxPlankPool.family(BlockFamilies.register(ModBlocks.VALTROX_PLANKS).sign(ModBlocks.VALTROX_SIGN, ModBlocks.VALTROX_WALL_SIGN).build());
        blockStateModelGenerator.registerHangingSign(ModBlocks.STRIPPED_VALTROX_LOG, ModBlocks.VALTROX_HANGING_SIGN, ModBlocks.VALTROX_WALL_HANGING_SIGN);
        blockStateModelGenerator.registerDoor(ModBlocks.VALTROX_DOOR);
        blockStateModelGenerator.registerOrientableTrapdoor(ModBlocks.VALTROX_TRAPDOOR);

        coatedValtroxPlankPool.fenceGate(ModBlocks.COATED_VALTROX_FENCE_GATE);
        coatedValtroxPlankPool.fence(ModBlocks.COATED_VALTROX_FENCE);
        coatedValtroxPlankPool.button(ModBlocks.COATED_VALTROX_BUTTON);
        coatedValtroxPlankPool.slab(ModBlocks.COATED_VALTROX_SLAB);
        coatedValtroxPlankPool.stairs(ModBlocks.COATED_VALTROX_STAIRS);
        blockStateModelGenerator.registerLog(ModBlocks.COATED_VALTROX_LOG).log(ModBlocks.COATED_VALTROX_LOG).wood(ModBlocks.COATED_VALTROX_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.COATED_STRIPPED_VALTROX_LOG).log(ModBlocks.COATED_STRIPPED_VALTROX_LOG).wood(ModBlocks.COATED_STRIPPED_VALTROX_WOOD);
        coatedValtroxPlankPool.pressurePlate(ModBlocks.COATED_VALTROX_PRESSURE_PLATE);
        coatedValtroxPlankPool.family(BlockFamilies.register(ModBlocks.COATED_VALTROX_PLANKS).sign(ModBlocks.COATED_VALTROX_SIGN, ModBlocks.COATED_VALTROX_WALL_SIGN).build());
        blockStateModelGenerator.registerHangingSign(ModBlocks.COATED_STRIPPED_VALTROX_LOG, ModBlocks.COATED_VALTROX_HANGING_SIGN, ModBlocks.COATED_VALTROX_WALL_HANGING_SIGN);
        blockStateModelGenerator.registerDoor(ModBlocks.COATED_VALTROX_DOOR);
        blockStateModelGenerator.registerOrientableTrapdoor(ModBlocks.COATED_VALTROX_TRAPDOOR);


        driedValtroxPlankPool.fenceGate(ModBlocks.DRIED_VALTROX_FENCE_GATE);
        driedValtroxPlankPool.fence(ModBlocks.DRIED_VALTROX_FENCE);
        driedValtroxPlankPool.button(ModBlocks.DRIED_VALTROX_BUTTON);
        driedValtroxPlankPool.slab(ModBlocks.DRIED_VALTROX_SLAB);
        driedValtroxPlankPool.stairs(ModBlocks.DRIED_VALTROX_STAIRS);
        blockStateModelGenerator.registerLog(ModBlocks.DRIED_VALTROX_LOG).log(ModBlocks.DRIED_VALTROX_LOG).wood(ModBlocks.DRIED_VALTROX_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_DRIED_VALTROX_LOG).log(ModBlocks.STRIPPED_DRIED_VALTROX_LOG).wood(ModBlocks.STRIPPED_DRIED_VALTROX_WOOD);
        driedValtroxPlankPool.pressurePlate(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE);
        driedValtroxPlankPool.family(BlockFamilies.register(ModBlocks.DRIED_VALTROX_PLANKS).sign(ModBlocks.DRIED_VALTROX_SIGN, ModBlocks.DRIED_VALTROX_WALL_SIGN).build());
        blockStateModelGenerator.registerHangingSign(ModBlocks.STRIPPED_DRIED_VALTROX_LOG, ModBlocks.DRIED_VALTROX_HANGING_SIGN, ModBlocks.DRIED_VALTROX_WALL_HANGING_SIGN);
        blockStateModelGenerator.registerDoor(ModBlocks.DRIED_VALTROX_DOOR);
        blockStateModelGenerator.registerOrientableTrapdoor(ModBlocks.DRIED_VALTROX_TRAPDOOR);

        petrifiedValtroxPlankPool.fenceGate(ModBlocks.PETRIFIED_VALTROX_WALL_GATE);
        petrifiedValtroxPlankPool.wall(ModBlocks.PETRIFIED_VALTROX_WALL);
        petrifiedValtroxPlankPool.button(ModBlocks.PETRIFIED_VALTROX_BUTTON);
        petrifiedValtroxPlankPool.slab(ModBlocks.PETRIFIED_VALTROX_SLAB);
        petrifiedValtroxPlankPool.stairs(ModBlocks.PETRIFIED_VALTROX_STAIRS);
        blockStateModelGenerator.registerLog(ModBlocks.PETRIFIED_VALTROX_LOG).log(ModBlocks.PETRIFIED_VALTROX_LOG).wood(ModBlocks.PETRIFIED_VALTROX_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG).log(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG).wood(ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD);
        petrifiedValtroxPlankPool.pressurePlate(ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE);
        petrifiedValtroxPlankPool.family(BlockFamilies.register(ModBlocks.PETRIFIED_VALTROX_SLATES).sign(ModBlocks.PETRIFIED_VALTROX_SIGN, ModBlocks.PETRIFIED_VALTROX_WALL_SIGN).build());
        blockStateModelGenerator.registerHangingSign(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG, ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN, ModBlocks.PETRIFIED_VALTROX_WALL_HANGING_SIGN);
        blockStateModelGenerator.registerDoor(ModBlocks.PETRIFIED_VALTROX_DOOR);
        blockStateModelGenerator.registerOrientableTrapdoor(ModBlocks.PETRIFIED_VALTROX_TRAPDOOR);


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
