package net.ugi.sculk_depths.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
//import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.impl.content.registry.FlammableBlockRegistryImpl;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.custom.*;
import net.ugi.sculk_depths.block.custom.ModCauldron.FlumrockCauldronBlock;
import net.ugi.sculk_depths.block.custom.ModCauldron.KryslumFlumrockCauldronBlock;
import net.ugi.sculk_depths.block.custom.ModCauldron.ModCauldronBehavior;
import net.ugi.sculk_depths.fluid.ModFluids;
import net.ugi.sculk_depths.item.ModItemGroup;
import net.ugi.sculk_depths.sound.ModBlockSoundGroups;
import net.ugi.sculk_depths.util.ModFlammableBlocks;
import net.ugi.sculk_depths.world.WorldGenerator;
import net.ugi.sculk_depths.block.custom.ModCauldron.SporeFlumrockCauldronBlock;
import net.ugi.sculk_depths.block.dryable.*;
import net.ugi.sculk_depths.util.ModStrippableBlocks;
import net.ugi.sculk_depths.world.gen.feature.ModConfiguredFeatures;


public class ModBlocks {



    // ------------------- amalgamite  -------------------


    public static final Block AMALGAMITE = registerBlock("amalgamite",
            new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE).strength(3.0f, 6.0f).requiresTool().sounds(ModBlockSoundGroups.AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //polished amalgamite blockset
    public static final Block POLISHED_AMALGAMITE = registerBlock("polished_amalgamite",
            new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_BRICKS).strength(2.9f, 6.0f).requiresTool().sounds(ModBlockSoundGroups.AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block POLISHED_AMALGAMITE_STAIRS = registerBlock("polished_amalgamite_stairs",
            new StairsBlock(ModBlocks.POLISHED_AMALGAMITE.getDefaultState(), AbstractBlock.Settings.copy(ModBlocks.POLISHED_AMALGAMITE).sounds(ModBlockSoundGroups.AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block POLISHED_AMALGAMITE_SLAB = registerBlock("polished_amalgamite_slab",
            new SlabBlock(AbstractBlock.Settings.copy(ModBlocks.POLISHED_AMALGAMITE).sounds(ModBlockSoundGroups.AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block POLISHED_AMALGAMITE_WALL = registerBlock("polished_amalgamite_wall",
            new WallBlock(AbstractBlock.Settings.copy(ModBlocks.POLISHED_AMALGAMITE).sounds(ModBlockSoundGroups.AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //amalgamite bricks blockset
    public static final Block AMALGAMITE_BRICKS = registerBlock("amalgamite_bricks",
            new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_BRICKS).strength(2.9f, 6.0f).requiresTool().sounds(ModBlockSoundGroups.AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block CRACKED_AMALGAMITE_BRICKS = registerBlock("cracked_amalgamite_bricks",
            new Block(AbstractBlock.Settings.copy(ModBlocks.AMALGAMITE_BRICKS).sounds(ModBlockSoundGroups.AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AMALGAMITE_BRICK_STAIRS = registerBlock("amalgamite_brick_stairs",
            new StairsBlock(ModBlocks.AMALGAMITE_BRICKS.getDefaultState(), AbstractBlock.Settings.copy(ModBlocks.AMALGAMITE_BRICKS).sounds(ModBlockSoundGroups.AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AMALGAMITE_BRICK_SLAB = registerBlock("amalgamite_brick_slab",
            new SlabBlock(AbstractBlock.Settings.copy(ModBlocks.AMALGAMITE_BRICKS).sounds(ModBlockSoundGroups.AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AMALGAMITE_BRICK_WALL = registerBlock("amalgamite_brick_wall",
            new WallBlock(AbstractBlock.Settings.copy(ModBlocks.AMALGAMITE_BRICKS).sounds(ModBlockSoundGroups.AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //rest
    public static final Block CHISELED_AMALGAMITE = registerBlock("chiseled_amalgamite",
            new Block(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_BRICKS).strength(2.9f, 6.0f).requiresTool().sounds(ModBlockSoundGroups.AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ACTIVATED_AMALGAMITE = registerBlock("activated_amalgamite",
            new Block(AbstractBlock.Settings.copy(Blocks.REINFORCED_DEEPSLATE).strength(-1.0f, 3600000.0f).sounds(ModBlockSoundGroups.AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);


    // ------------------- umbrusk -------------------

    //umbrusk blockset
    public static final Block UMBRUSK = registerBlock("umbrusk",
            new Block(AbstractBlock.Settings.copy(Blocks.STONE).strength(5.0f,8f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block UMBRUSK_STAIRS = registerBlock("umbrusk_stairs",
            new StairsBlock(UMBRUSK.getDefaultState(), AbstractBlock.Settings.copy(ModBlocks.UMBRUSK)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block UMBRUSK_SLAB = registerBlock("umbrusk_slab",
            new SlabBlock(AbstractBlock.Settings.copy(ModBlocks.UMBRUSK)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block UMBRUSK_WALL = registerBlock("umbrusk_wall",
            new WallBlock(AbstractBlock.Settings.copy(ModBlocks.UMBRUSK)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //umbrusk bricks blockset
    public static final Block UMBRUSK_BRICKS = registerBlock("umbrusk_bricks",
            new Block(AbstractBlock.Settings.copy(ModBlocks.UMBRUSK)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block UMBRUSK_BRICK_STAIRS = registerBlock("umbrusk_brick_stairs",
            new StairsBlock(UMBRUSK_BRICKS.getDefaultState(), AbstractBlock.Settings.copy(ModBlocks.UMBRUSK_BRICKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block UMBRUSK_BRICK_SLAB = registerBlock("umbrusk_brick_slab",
            new SlabBlock(AbstractBlock.Settings.copy(ModBlocks.UMBRUSK_BRICKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block UMBRUSK_BRICK_WALL = registerBlock("umbrusk_brick_wall",
            new WallBlock(AbstractBlock.Settings.copy(ModBlocks.UMBRUSK_BRICKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);


    // ------------------- zygrin  -------------------

    //zygrin blockset
    public static final Block ZYGRIN = registerBlock("zygrin",
            new Block(AbstractBlock.Settings.copy(Blocks.STONE).strength(6.0f,9.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_STAIRS = registerBlock("zygrin_stairs",
            new StairsBlock(ZYGRIN.getDefaultState(), AbstractBlock.Settings.copy(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_SLAB = registerBlock("zygrin_slab",
            new SlabBlock(AbstractBlock.Settings.copy(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_WALL = registerBlock("zygrin_wall",
            new WallBlock(AbstractBlock.Settings.copy(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //polished zygrin blockset
    public static final Block POLISHED_ZYGRIN = registerBlock("polished_zygrin",
            new Block(AbstractBlock.Settings.copy(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block POLISHED_ZYGRIN_STAIRS = registerBlock("polished_zygrin_stairs",
            new StairsBlock(POLISHED_ZYGRIN.getDefaultState(), AbstractBlock.Settings.copy(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block POLISHED_ZYGRIN_SLAB = registerBlock("polished_zygrin_slab",
            new SlabBlock(AbstractBlock.Settings.copy(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block POLISHED_ZYGRIN_WALL = registerBlock("polished_zygrin_wall",
            new WallBlock(AbstractBlock.Settings.copy(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //zygrin bricks blockset
    public static final Block ZYGRIN_BRICKS = registerBlock("zygrin_bricks",
            new Block(AbstractBlock.Settings.copy(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_BRICK_STAIRS = registerBlock("zygrin_brick_stairs",
            new StairsBlock(ZYGRIN_BRICKS.getDefaultState(), AbstractBlock.Settings.copy(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_BRICK_SLAB = registerBlock("zygrin_brick_slab",
            new SlabBlock(AbstractBlock.Settings.copy(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_BRICK_WALL = registerBlock("zygrin_brick_wall",
            new WallBlock(AbstractBlock.Settings.copy(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //rest
    public static final Block ZYGRIN_PILLAR = registerBlock("zygrin_pillar",
            new PillarBlock(AbstractBlock.Settings.copy(ModBlocks.ZYGRIN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block CHISELED_ZYGRIN = registerBlock("chiseled_zygrin",
            new PillarBlock(AbstractBlock.Settings.copy(ModBlocks.ZYGRIN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_LIGHT = registerBlock("zygrin_light",
            new Block(AbstractBlock.Settings.copy(ModBlocks.ZYGRIN).requiresTool().luminance((state) -> 15)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_FLOWBLOCK = registerBlock("zygrin_flowblock",
            new FlowBlock(AbstractBlock.Settings.copy(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_FURNACE = registerBlock("zygrin_furnace",
            new ZygrinFurnaceBlock(AbstractBlock.Settings.copy(Blocks.FURNACE).strength(10.0f,10f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);


    // ------------------- flumrock -------------------
    public static final Block FLUMROCK = registerBlock("flumrock",
            new Block(AbstractBlock.Settings.create().strength(4.0f,0.5f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //flumrock tiles
    public static final Block FLUMROCK_TILES = registerBlock("flumrock_tiles",
            new Block(AbstractBlock.Settings.copy(ModBlocks.FLUMROCK).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block CRUMBLING_FLUMROCK_TILES = registerBlock("crumbling_flumrock_tiles",
            new Block(AbstractBlock.Settings.copy(ModBlocks.FLUMROCK).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);


    public static final Block FLUMROCK_TILE_STAIRS = registerBlock("flumrock_tile_stairs",
            new StairsBlock(FLUMROCK_TILES.getDefaultState(), AbstractBlock.Settings.copy(ModBlocks.FLUMROCK_TILES).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block FLUMROCK_TILE_SLAB = registerBlock("flumrock_tile_slab",
            new SlabBlock(AbstractBlock.Settings.copy(ModBlocks.FLUMROCK_TILES).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block FLUMROCK_TILE_WALL = registerBlock("flumrock_tile_wall",
            new WallBlock(AbstractBlock.Settings.copy(ModBlocks.FLUMROCK_TILES).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //cauldron
    public static final Block FLUMROCK_CAULDRON = registerBlock("flumrock_cauldron", new FlumrockCauldronBlock(AbstractBlock.Settings.copy(Blocks.CAULDRON).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);
    public static final Block KRYSLUM_FLUMROCK_CAULDRON = registerBlockWithoutBlockItem("kryslum_flumrock_cauldron", new KryslumFlumrockCauldronBlock(AbstractBlock.Settings.copy(ModBlocks.FLUMROCK_CAULDRON), ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map()), ModItemGroup.SCULK_DEPTHS_BLOCKS);
    public static final Block SPORE_FLUMROCK_CAULDRON = registerBlockWithoutBlockItem("spore_flumrock_cauldron", new SporeFlumrockCauldronBlock(AbstractBlock.Settings.copy(ModBlocks.FLUMROCK_CAULDRON), ModCauldronBehavior.SPORE_FLUMROCK_CAULDRON_BEHAVIOR.map()), ModItemGroup.SCULK_DEPTHS_BLOCKS);



    // ------------------- valtrox  -------------------

    //valtrox blockset
    public static final Block VALTROX_LOG = registerBlock("valtrox_log",
            new DryablePillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_WOOD = registerBlock("valtrox_wood",
            new DryablePillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block STRIPPED_VALTROX_LOG = registerBlock("stripped_valtrox_log",
            new DryablePillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block STRIPPED_VALTROX_WOOD = registerBlock("stripped_valtrox_wood",
            new DryablePillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_PLANKS = registerBlock("valtrox_planks",
            new DryableBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_STAIRS = registerBlock("valtrox_stairs",
            new DryableStairsBlock(VALTROX_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(Blocks.OAK_STAIRS).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_SLAB = registerBlock("valtrox_slab",
            new DryableSlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_FENCE = registerBlock("valtrox_fence",
            new DryableFenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_FENCE_GATE = registerBlock("valtrox_fence_gate",
            new DryableFenceGateBlock(ModWoodType.VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_DOOR = registerBlock("valtrox_door",
            new DryableDoorBlock(ModBlockSetType.VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_DOOR).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_TRAPDOOR = registerBlock("valtrox_trapdoor",
            new DryableTrapdoorBlock(ModBlockSetType.VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_PRESSURE_PLATE = registerBlock("valtrox_pressure_plate",
            new DryablePressurePlateBlock(ModBlockSetType.VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);
    public static final Block VALTROX_BUTTON = registerBlock("valtrox_button",
            new DryableButtonBlock(ModBlockSetType.VALTROX, 30, AbstractBlock.Settings.copy(Blocks.OAK_BUTTON)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_SIGN = registerBlockWithoutBlockItem("valtrox_sign",
            new DryableSignBlock(ModWoodType.VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_SIGN).strength(1.0f).solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_WALL_SIGN = registerBlockWithoutBlockItem("valtrox_wall_sign",
            new DryableWallSignBlock(ModWoodType.VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN).strength(1.0f).dropsLike(ModBlocks.VALTROX_SIGN).solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_HANGING_SIGN = registerBlockWithoutBlockItem("valtrox_hanging_sign",
            new DryableHangingSignBlock(ModWoodType.VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN).strength(1.0f).solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("valtrox_wall_hanging_sign",
            new DryableWallHangingSignBlock(ModWoodType.VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_WALL_HANGING_SIGN).strength(1.0f).dropsLike(VALTROX_HANGING_SIGN).solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //coated valtrox blockset
    public static final Block COATED_VALTROX_LOG = registerBlock("coated_valtrox_log",
            new PillarBlock(AbstractBlock.Settings.copy(ModBlocks.VALTROX_LOG)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_WOOD = registerBlock("coated_valtrox_wood",
            new PillarBlock(AbstractBlock.Settings.copy(ModBlocks.VALTROX_WOOD)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_STRIPPED_VALTROX_LOG = registerBlock("coated_stripped_valtrox_log",
            new PillarBlock(AbstractBlock.Settings.copy(ModBlocks.STRIPPED_VALTROX_LOG)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_STRIPPED_VALTROX_WOOD = registerBlock("coated_stripped_valtrox_wood",
            new PillarBlock(AbstractBlock.Settings.copy(ModBlocks.STRIPPED_VALTROX_WOOD)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_PLANKS = registerBlock("coated_valtrox_planks",
            new Block(AbstractBlock.Settings.copy(ModBlocks.VALTROX_PLANKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_STAIRS = registerBlock("coated_valtrox_stairs",
            new StairsBlock(COATED_VALTROX_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(ModBlocks.VALTROX_STAIRS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_SLAB = registerBlock("coated_valtrox_slab",
            new SlabBlock(AbstractBlock.Settings.copy(ModBlocks.VALTROX_SLAB)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_FENCE = registerBlock("coated_valtrox_fence",
            new FenceBlock(AbstractBlock.Settings.copy(ModBlocks.VALTROX_FENCE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_FENCE_GATE = registerBlock("coated_valtrox_fence_gate",
            new FenceGateBlock(ModWoodType.COATED_VALTROX, AbstractBlock.Settings.copy(ModBlocks.VALTROX_FENCE_GATE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_DOOR = registerBlock("coated_valtrox_door",
            new DoorBlock(ModBlockSetType.COATED_VALTROX, AbstractBlock.Settings.copy(ModBlocks.VALTROX_DOOR)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_TRAPDOOR = registerBlock("coated_valtrox_trapdoor",
            new TrapdoorBlock(ModBlockSetType.COATED_VALTROX, AbstractBlock.Settings.copy(ModBlocks.VALTROX_TRAPDOOR)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_PRESSURE_PLATE = registerBlock("coated_valtrox_pressure_plate",
            new PressurePlateBlock(ModBlockSetType.COATED_VALTROX, AbstractBlock.Settings.copy(ModBlocks.VALTROX_PRESSURE_PLATE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_BUTTON = registerBlock("coated_valtrox_button",
            new ButtonBlock(ModBlockSetType.COATED_VALTROX, 30, AbstractBlock.Settings.copy(ModBlocks.VALTROX_BUTTON)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_SIGN = registerBlockWithoutBlockItem("coated_valtrox_sign",
            new SignBlock(ModWoodType.COATED_VALTROX, AbstractBlock.Settings.copy(ModBlocks.VALTROX_SIGN).solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_WALL_SIGN = registerBlockWithoutBlockItem("coated_valtrox_wall_sign",
            new WallSignBlock(ModWoodType.COATED_VALTROX, AbstractBlock.Settings.copy(ModBlocks.VALTROX_WALL_SIGN).dropsLike(ModBlocks.COATED_VALTROX_SIGN).solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_HANGING_SIGN = registerBlockWithoutBlockItem("coated_valtrox_hanging_sign",
            new HangingSignBlock(ModWoodType.COATED_VALTROX, AbstractBlock.Settings.copy(ModBlocks.VALTROX_HANGING_SIGN).solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("coated_valtrox_wall_hanging_sign",
            new WallHangingSignBlock(ModWoodType.COATED_VALTROX, AbstractBlock.Settings.copy(ModBlocks.VALTROX_WALL_HANGING_SIGN).dropsLike(COATED_VALTROX_HANGING_SIGN).solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);



    //dried valtrox blockset
    public static final Block DRIED_VALTROX_LOG = registerBlock("dried_valtrox_log",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_WOOD = registerBlock("dried_valtrox_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block STRIPPED_DRIED_VALTROX_LOG = registerBlock("stripped_dried_valtrox_log",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block STRIPPED_DRIED_VALTROX_WOOD = registerBlock("stripped_dried_valtrox_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_PLANKS = registerBlock("dried_valtrox_planks",
            new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_STAIRS = registerBlock("dried_valtrox_stairs",
            new StairsBlock(DRIED_VALTROX_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(Blocks.OAK_STAIRS).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_SLAB = registerBlock("dried_valtrox_slab",
            new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_FENCE = registerBlock("dried_valtrox_fence",
            new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_FENCE_GATE = registerBlock("dried_valtrox_fence_gate",
            new FenceGateBlock(ModWoodType.DRIED_VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_DOOR = registerBlock("dried_valtrox_door",
            new DoorBlock(ModBlockSetType.DRIED_VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_DOOR).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_TRAPDOOR = registerBlock("dried_valtrox_trapdoor",
            new TrapdoorBlock(ModBlockSetType.DRIED_VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR).strength(2.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_PRESSURE_PLATE = registerBlock("dried_valtrox_pressure_plate",
            new PressurePlateBlock(ModBlockSetType.DRIED_VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_BUTTON = registerBlock("dried_valtrox_button",
            new ButtonBlock(ModBlockSetType.DRIED_VALTROX, 30, AbstractBlock.Settings.copy(Blocks.OAK_BUTTON)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_SIGN = registerBlockWithoutBlockItem("dried_valtrox_sign",
            new SignBlock(ModWoodType.DRIED_VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_SIGN).strength(1.0f).solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_WALL_SIGN = registerBlockWithoutBlockItem("dried_valtrox_wall_sign",
            new WallSignBlock(ModWoodType.DRIED_VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN).strength(1.0f).dropsLike(ModBlocks.DRIED_VALTROX_SIGN).solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_HANGING_SIGN = registerBlockWithoutBlockItem("dried_valtrox_hanging_sign",
            new HangingSignBlock(ModWoodType.DRIED_VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN).strength(1.0f).solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("dried_valtrox_wall_hanging_sign",
            new WallHangingSignBlock(ModWoodType.DRIED_VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_WALL_HANGING_SIGN).strength(1.0f).dropsLike(ModBlocks.DRIED_VALTROX_HANGING_SIGN).solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //petrified valtrox blockset
    public static final Block PETRIFIED_VALTROX_LOG = registerBlock("petrified_valtrox_log",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_WOOD = registerBlock("petrified_valtrox_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block STRIPPED_PETRIFIED_VALTROX_LOG = registerBlock("stripped_petrified_valtrox_log",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block STRIPPED_PETRIFIED_VALTROX_WOOD = registerBlock("stripped_petrified_valtrox_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_SLATES = registerBlock("petrified_valtrox_slates",
            new Block(AbstractBlock.Settings.copy(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_STAIRS = registerBlock("petrified_valtrox_stairs",
            new StairsBlock(PETRIFIED_VALTROX_SLATES.getDefaultState(), AbstractBlock.Settings.copy(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_SLAB = registerBlock("petrified_valtrox_slab",
            new SlabBlock(AbstractBlock.Settings.copy(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_WALL = registerBlock("petrified_valtrox_wall",
            new WallBlock(AbstractBlock.Settings.copy(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_WALL_GATE = registerBlock("petrified_valtrox_wall_gate",
            new FenceGateBlock(ModWoodType.PETRIFIED_VALTROX, AbstractBlock.Settings.copy(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_DOOR = registerBlock("petrified_valtrox_door",
            new DoorBlock(ModBlockSetType.PETRIFIED_VALTROX, AbstractBlock.Settings.copy(Blocks.STONE).strength(3.0f,5.0f).requiresTool().nonOpaque()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_TRAPDOOR = registerBlock("petrified_valtrox_trapdoor",
            new TrapdoorBlock(ModBlockSetType.PETRIFIED_VALTROX, AbstractBlock.Settings.copy(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_PRESSURE_PLATE = registerBlock("petrified_valtrox_pressure_plate",
            new PressurePlateBlock(ModBlockSetType.PETRIFIED_VALTROX, AbstractBlock.Settings.copy(Blocks.STONE_PRESSURE_PLATE).strength(1.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_BUTTON = registerBlock("petrified_valtrox_button",
            new ButtonBlock(ModBlockSetType.PETRIFIED_VALTROX, 20, AbstractBlock.Settings.copy(Blocks.STONE_BUTTON).strength(1.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_SIGN = registerBlockWithoutBlockItem("petrified_valtrox_sign",
            new SignBlock(ModWoodType.PETRIFIED_VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_SIGN).strength(1.5f).requiresTool().solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_WALL_SIGN = registerBlockWithoutBlockItem("petrified_valtrox_wall_sign",
            new WallSignBlock(ModWoodType.PETRIFIED_VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN).strength(1.5f).requiresTool().dropsLike(ModBlocks.PETRIFIED_VALTROX_SIGN).solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_HANGING_SIGN = registerBlockWithoutBlockItem("petrified_valtrox_hanging_sign",
            new HangingSignBlock(ModWoodType.PETRIFIED_VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN).strength(1.5f).requiresTool().solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("petrified_valtrox_wall_hanging_sign",
            new WallHangingSignBlock(ModWoodType.PETRIFIED_VALTROX, AbstractBlock.Settings.copy(Blocks.OAK_WALL_HANGING_SIGN).strength(1.5f).requiresTool().dropsLike(ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN).solid()), ModItemGroup.SCULK_DEPTHS_BLOCKS);



    // ------------------- leaves -------------------
    public static final Block VALTROX_LEAVES = registerBlock("valtrox_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    // ------------------- saplings -------------------
    public static final Block VALTROX_SAPLING = registerBlock("valtrox_sapling",
            new SaplingBlock(WorldGenerator.VALTROX_SAPLING_GENERATOR, AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    // ------------------- cephlera -------------------
    public static final Block CEPHLERA = registerBlockWithoutBlockItem("cephlera",
            new CephleraBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.WEEPING_VINES)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block CEPHLERA_LIGHT = registerBlock("cephlera_light",
            new CephleraLightBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).noCollision().breakInstantly().emissiveLighting(AbstractBlock.AbstractBlockState::isFullCube).luminance(blockState -> 15).sounds(BlockSoundGroup.WEEPING_VINES)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    // ------------------- resource blocks  -------------------
    public static final Block QUAZARITH_BLOCK = registerBlock("quazarith_block",
            new Block(AbstractBlock.Settings.copy(Blocks.NETHERITE_BLOCK).strength(60.0f,1400.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block CRUX_BLOCK = registerBlock("crux_block",
            new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_BLOCK).strength(5.0F, 6.0F).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    // ------------------- ores -------------------
    public static final Block QUAZARITH_ORE = registerBlock("quazarith_ore",
            new LesterSpawningBlock(AbstractBlock.Settings.copy(ModBlocks.UMBRUSK).strength(5.5f,9.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block CRUX_ORE = registerBlock("crux_ore",
            new Block(AbstractBlock.Settings.copy(ModBlocks.ZYGRIN).strength(4.5f,8.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    // ------------------- crystals -------------------
    public static final Block WHITE_CRYSTAL_BLOCK = registerBlock("white_crystal_block", ModBlocks.createCrystalBlock(DyeColor.WHITE), ModItemGroup.SCULK_DEPTHS_BLOCKS);
    public static final Block BLUE_CRYSTAL_BLOCK = registerBlock("blue_crystal_block", ModBlocks.createCrystalBlock(DyeColor.BLUE), ModItemGroup.SCULK_DEPTHS_BLOCKS);
    public static final Block ORANGE_CRYSTAL_BLOCK = registerBlock("orange_crystal_block", ModBlocks.createCrystalBlock(DyeColor.ORANGE), ModItemGroup.SCULK_DEPTHS_BLOCKS);
    public static final Block LIME_CRYSTAL_BLOCK = registerBlock("lime_crystal_block", ModBlocks.createCrystalBlock(DyeColor.LIME), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    // ------------------- penebrium -------------------
    public static final Block PENEBRIUM_SHROOM = registerBlock("penebrium_shroom",
            new MushroomPlantBlock(ModConfiguredFeatures.PENEBRIUM_SHROOM, AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM)), ModItemGroup.SCULK_DEPTHS_BLOCKS);//.luminance((state) -> 5).emissiveLighting(AbstractBlock.AbstractBlockState::isFullCube))

    public static final Block PENEBRIUM_SHROOM_BLOCK = registerBlock("penebrium_shroom_block",
            new ShroomBlock(AbstractBlock.Settings.copy(Blocks.MUSHROOM_STEM).luminance((state) -> 5).emissiveLighting(AbstractBlock.AbstractBlockState::isFullCube).nonOpaque()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PENEBRIUM_SHROOM_STEM = registerBlock("penebrium_shroom_stem",
            new ShroomBlock(AbstractBlock.Settings.copy(Blocks.MUSHROOM_STEM).nonOpaque()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PENEBRIUM_SPORE_BLOCK = registerBlock("penebrium_spore_block",
            new SporeBlock(AbstractBlock.Settings.copy(Blocks.MUSHROOM_STEM).luminance((state) -> 5).emissiveLighting(AbstractBlock.AbstractBlockState::isFullCube).nonOpaque()), ModItemGroup.SCULK_DEPTHS_BLOCKS);



    // ------------------- misc -------------------
    public static final Block CRUMBLING_DIRT = registerBlock("crumbling_dirt",
            new Block(AbstractBlock.Settings.create().strength(0.3f).sounds(BlockSoundGroup.ROOTED_DIRT)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block LARGUTH = registerBlock("larguth",
            new Block(AbstractBlock.Settings.copy(Blocks.OBSIDIAN).strength(40.0f,1000f).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_GRASS = registerBlock("dried_grass",
            new CustomPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block KRYSLUM_ENRICHED_SOIL = registerBlock("kryslum_enriched_soil",
            new KryslumEnrichedSoilBLock(AbstractBlock.Settings.copy(Blocks.FARMLAND).ticksRandomly().strength(0.8f).sounds(BlockSoundGroup.MUDDY_MANGROVE_ROOTS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);
    
    public static final Block SCULK_DEPTHS_PORTAL = registerBlockWithoutBlockItem("sculk_depths_portal",
            new SculkDepthsPortalBlock(AbstractBlock.Settings.copy(Blocks.NETHER_PORTAL).luminance((state) -> 6).dropsNothing().noCollision().strength(-1.0f,3600000.0f)),ModItemGroup.SCULK_DEPTHS_BLOCKS );

    public static final Block SCULK_PEDESTAL = registerBlock("sculk_pedestal",
            new PedestalBlock(AbstractBlock.Settings.create().strength(-1f, 3600000.0f).pistonBehavior(PistonBehavior.BLOCK)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block QELBERRY_BUSH = registerBlockWithoutBlockItem("qelberry_bush",
        new QeldryBerryBush(AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block QUAZARITH_OSCILLATOR = registerBlock("quazarith_oscillator",
            new Block(AbstractBlock.Settings.copy(ModBlocks.AMALGAMITE_BRICKS).requiresTool()), ModItemGroup.SCULK_DEPTHS_BLOCKS);



    // ------------------- auric -------------------
    public static final Block AURIC_SPORE_BLOCK = registerBlock("auric_spore_block",
            new ShroomBlock(AbstractBlock.Settings.create().strength(0.3f).sounds(BlockSoundGroup.MOSS_BLOCK)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AURIC_SHROOM_BLOCK = registerBlock("auric_shroom_block",
            new ShroomBlock(AbstractBlock.Settings.create().strength(0.3f).sounds(BlockSoundGroup.MOSS_BLOCK)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AURIC_SHROOM_STEM = registerBlock("auric_shroom_stem",
            new ShroomBlock(AbstractBlock.Settings.copy(Blocks.MUSHROOM_STEM).nonOpaque()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AURIC_VINES = registerBlockWithoutBlockItem("auric_vines",
            new AuricVinesBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.WEEPING_VINES_LOW_PITCH)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AURIC_VINES_END = registerBlock("auric_vines_end",
            new AuricVinesEndBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.WEEPING_VINES_LOW_PITCH)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AURIC_SPORE_LAYER = registerBlock("auric_spore_layer",
            new LayeredAuricSporeBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(0.5F).sounds(BlockSoundGroup.SAND).notSolid().ticksRandomly()
                    .blockVision((state, world, pos) -> {return (Integer)state.get(SnowBlock.LAYERS) >= 8;})
                    .pistonBehavior(PistonBehavior.DESTROY)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AURIC_SPORE_SPROUTS = registerBlock("auric_spore_sprouts",
            new ShroomPlantBlock(AbstractBlock.Settings.copy(Blocks.BROWN_MUSHROOM).luminance((state)-> 0).noCollision().breakInstantly().sounds(BlockSoundGroup.WEEPING_VINES), ModConfiguredFeatures.AURIC_SHROOM) , ModItemGroup.SCULK_DEPTHS_BLOCKS);

    // ------------------- fluids -------------------
    public static final Block KRYSLUM = registerBlockWithoutBlockItem("kryslum", new FluidBlock(ModFluids.KRYSLUM_STILL, AbstractBlock.Settings.copy(Blocks.WATER).replaceable().noCollision().strength(100.0f).pistonBehavior(PistonBehavior.DESTROY).dropsNothing().liquid().solid().sounds(BlockSoundGroup.SCULK)), ModItemGroup.SCULK_DEPTHS_BLOCKS);


    // ------------------- potted stuff -------------------
    public static final Block POTTED_VALTROX_SAPLING = registerBlockWithoutBlockItem("potted_valtrox_sapling", new FlowerPotBlock(ModBlocks.VALTROX_SAPLING, AbstractBlock.Settings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)), ModItemGroup.SCULK_DEPTHS_BLOCKS);
    public static final Block POTTED_PENEBRIUM_SHROOM = registerBlockWithoutBlockItem("potted_penebrium_shroom", new FlowerPotBlock(ModBlocks.PENEBRIUM_SHROOM, AbstractBlock.Settings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)), ModItemGroup.SCULK_DEPTHS_BLOCKS);
    public static final Block POTTED_AURIC_SPORE_SPROUTS = registerBlockWithoutBlockItem("potted_auric_spore_sprouts", new FlowerPotBlock(ModBlocks.AURIC_SPORE_SPROUTS, AbstractBlock.Settings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)), ModItemGroup.SCULK_DEPTHS_BLOCKS);


    private static CrystalBlock createCrystalBlock(DyeColor color) {
        return new CrystalBlock(color, AbstractBlock.Settings.copy(Blocks.GLASS).luminance((state) -> 10));
    }

    private static Block registerBlock(String name, Block block, RegistryKey<ItemGroup> group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, SculkDepths.identifier( name), block);
    }


    private static Block registerBlockWithoutBlockItem(String name, Block block, RegistryKey<ItemGroup> group) {
        return Registry.register(Registries.BLOCK, SculkDepths.identifier(name), block);
    }

    private static Item registerBlockItem(String name, Block block, RegistryKey<ItemGroup> group) {

        Item item = Registry.register(Registries.ITEM, SculkDepths.identifier( name),
                new BlockItem(block, new Item.Settings()));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return item;
    }

    public static void registerModBlocks() {
        ModFlammableBlocks.registerFlammables();
        ModStrippableBlocks.registerStrippables();
    }
}
