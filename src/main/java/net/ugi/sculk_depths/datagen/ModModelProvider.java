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
        BlockStateModelGenerator.BlockTexturePool driedValtroxPlankPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.DRIED_VALTROX_PLANKS);
        BlockStateModelGenerator.BlockTexturePool petrifiedValtroxPlankPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.PETRIFIED_VALTROX_SLATES);
        BlockStateModelGenerator.BlockTexturePool amalgamiteBrickPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.AMALGAMITE_BRICKS);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.QUAZARITH_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CRUX_BLOCK);
        //blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.AURIC_SPORE_BLOCK);

        amalgamiteBrickPool.stairs(ModBlocks.AMALGAMITE_BRICK_STAIRS);
        amalgamiteBrickPool.slab(ModBlocks.AMALGAMITE_BRICK_SLAB);
        amalgamiteBrickPool.wall(ModBlocks.AMALGAMITE_BRICK_WALL);

        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.VALTROX_SAPLING, ModBlocks.POTTED_VALTROX_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.PENEBRIUM_SHROOM, ModBlocks.POTTED_PENEBRIUM_SHROOM, BlockStateModelGenerator.TintType.NOT_TINTED);
        //iblockStateModelGenerator.registerFlowerPotPlant(ModBlocks.AURIC_SPORE_SPROUTS, ModBlocks.POTTED_AURIC_SPORE_SPROUTS, BlockStateModelGenerator.TintType.NOT_TINTED);

        valtroxPlankPool.fenceGate(ModBlocks.VALTROX_FENCE_GATE);
        valtroxPlankPool.family(BlockFamilies.register(ModBlocks.VALTROX_PLANKS).sign(ModBlocks.VALTROX_SIGN, ModBlocks.VALTROX_WALL_SIGN).build());
        blockStateModelGenerator.registerHangingSign(ModBlocks.STRIPPED_VALTROX_LOG, ModBlocks.VALTROX_HANGING_SIGN, ModBlocks.VALTROX_WALL_HANGING_SIGN);
        blockStateModelGenerator.registerDoor(ModBlocks.VALTROX_DOOR);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.VALTROX_TRAPDOOR);

        driedValtroxPlankPool.fenceGate(ModBlocks.DRIED_VALTROX_FENCE_GATE);
        driedValtroxPlankPool.family(BlockFamilies.register(ModBlocks.DRIED_VALTROX_PLANKS).sign(ModBlocks.DRIED_VALTROX_SIGN, ModBlocks.DRIED_VALTROX_WALL_SIGN).build());
        blockStateModelGenerator.registerHangingSign(ModBlocks.STRIPPED_DRIED_VALTROX_LOG, ModBlocks.DRIED_VALTROX_HANGING_SIGN, ModBlocks.DRIED_VALTROX_WALL_HANGING_SIGN);
        blockStateModelGenerator.registerDoor(ModBlocks.DRIED_VALTROX_DOOR);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.DRIED_VALTROX_TRAPDOOR);

        petrifiedValtroxPlankPool.fenceGate(ModBlocks.PETRIFIED_VALTROX_WALL_GATE);
        petrifiedValtroxPlankPool.family(BlockFamilies.register(ModBlocks.PETRIFIED_VALTROX_SLATES).sign(ModBlocks.PETRIFIED_VALTROX_SIGN, ModBlocks.PETRIFIED_VALTROX_WALL_SIGN).build());
        blockStateModelGenerator.registerHangingSign(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG, ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN, ModBlocks.PETRIFIED_VALTROX_WALL_HANGING_SIGN);
        blockStateModelGenerator.registerDoor(ModBlocks.PETRIFIED_VALTROX_DOOR);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.PETRIFIED_VALTROX_TRAPDOOR);


    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        /*
        itemModelGenerator.register(ModItems.RUBY, Models.GENERATED);
         */
        itemModelGenerator.register(ModItems.CRUX, Models.GENERATED);
    }
}
