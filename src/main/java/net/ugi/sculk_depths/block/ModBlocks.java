package net.ugi.sculk_depths.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.kyrptonaught.customportalapi.CustomPortalBlock;
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
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.custom.*;
import net.ugi.sculk_depths.block.custom.ModCauldron.FlumrockCauldronBlock;
import net.ugi.sculk_depths.block.custom.ModCauldron.KryslumFlumrockCauldronBlock;
import net.ugi.sculk_depths.block.custom.ModCauldron.ModCauldronBehavior;
import net.ugi.sculk_depths.block.custom.SoulFireBlock;
import net.ugi.sculk_depths.block.custom.ModCauldron.SporeFlumrockCauldronBlock;
import net.ugi.sculk_depths.block.dryable.*;
import net.ugi.sculk_depths.block.sapling.ValtroxSaplingGenerator;
import net.ugi.sculk_depths.fluid.ModFluids;
import net.ugi.sculk_depths.item.ModItemGroup;
import net.ugi.sculk_depths.util.ModStrippableBlocks;
import net.ugi.sculk_depths.world.gen.feature.ModConfiguredFeatures;


public class ModBlocks {

    public static final Block FLUMROCK = registerBlock("flumrock",
            new Block(FabricBlockSettings.create().strength(4.0f,0.5f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block CRUMBLING_DIRT = registerBlock("crumbling_dirt",
            new Block(FabricBlockSettings.create().strength(0.3f).requiresTool().sounds(BlockSoundGroup.ROOTED_DIRT)), ModItemGroup.SCULK_DEPTHS);

    public static final Block KRYSLUM_ENRICHED_SOIL = registerBlock("kryslum_enriched_soil",
            new KryslumEnrichedSoilBLock(FabricBlockSettings.copyOf(Blocks.FARMLAND).ticksRandomly().strength(0.8f).sounds(BlockSoundGroup.MUDDY_MANGROVE_ROOTS)), ModItemGroup.SCULK_DEPTHS);

    public static final Block SOUL_FIRE = registerBlockWithoutBlockItem("soul_fire", new SoulFireBlock(FabricBlockSettings.copyOf(Blocks.SOUL_FIRE)), ModItemGroup.SCULK_DEPTHS);

    public static final Block AMALGAMITE = registerBlock("amalgamite",
            new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE).strength(3.0f, 6.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);
    public static final Block LARGUTH = registerBlock("larguth",
            new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN).strength(40.0f,1000f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_FURNACE = registerBlock("zygrin_furnace",
            new ZygrinFurnaceBlock(FabricBlockSettings.copyOf(Blocks.FURNACE).strength(10.0f,10f).requiresTool()), ModItemGroup.SCULK_DEPTHS);


    //umbrusk blockset
    public static final Block UMBRUSK = registerBlock("umbrusk",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(5.0f,8f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_STAIRS = registerBlock("umbrusk_stairs",
            new StairsBlock(UMBRUSK.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.UMBRUSK)), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_SLAB = registerBlock("umbrusk_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.UMBRUSK)), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_WALL = registerBlock("umbrusk_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.UMBRUSK)), ModItemGroup.SCULK_DEPTHS);


    //umbrusk brick blockset
    public static final Block UMBRUSK_BRICKS = registerBlock("umbrusk_bricks",
            new Block(FabricBlockSettings.copyOf(ModBlocks.UMBRUSK)), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_BRICK_STAIRS = registerBlock("umbrusk_brick_stairs",
            new StairsBlock(UMBRUSK.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.UMBRUSK_BRICKS)), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_BRICK_SLAB = registerBlock("umbrusk_brick_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.UMBRUSK_BRICKS)), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_BRICK_WALL = registerBlock("umbrusk_brick_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.UMBRUSK_BRICKS)), ModItemGroup.SCULK_DEPTHS);


    //valtrox blockset
    public static final Block VALTROX_LOG = registerBlock("valtrox_log",
            new DryablePillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_WOOD = registerBlock("valtrox_wood",
            new DryablePillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block STRIPPED_VALTROX_LOG = registerBlock("stripped_valtrox_log",
            new DryablePillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block STRIPPED_VALTROX_WOOD = registerBlock("stripped_valtrox_wood",
            new DryablePillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_PLANKS = registerBlock("valtrox_planks",
            new DryableBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_STAIRS = registerBlock("valtrox_stairs",
            new DryableStairsBlock(VALTROX_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OAK_STAIRS).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_SLAB = registerBlock("valtrox_slab",
            new DryableSlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_FENCE = registerBlock("valtrox_fence",
            new DryableFenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_FENCE_GATE = registerBlock("valtrox_fence_gate",
            new DryableFenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE).strength(2.0f), ModWoodType.VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_DOOR = registerBlock("valtrox_door",
            new DryableDoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_DOOR).strength(2.0f), ModBlockSetType.VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_TRAPDOOR = registerBlock("valtrox_trapdoor",
            new DryableTrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).strength(2.0f), ModBlockSetType.VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_PRESSURE_PLATE = registerBlock("valtrox_pressure_plate",
            new DryablePressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.copyOf(Blocks.OAK_PRESSURE_PLATE), ModBlockSetType.VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_BUTTON = registerBlock("valtrox_button",
            new DryableButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON), ModBlockSetType.VALTROX, 30, true), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_SIGN = registerBlockWithoutBlockItem("valtrox_sign",
            new DryableSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_SIGN).strength(1.0f).solid(), ModWoodType.VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_WALL_SIGN = registerBlockWithoutBlockItem("valtrox_wall_sign",
            new DryableWallSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN).strength(1.0f).dropsLike(ModBlocks.VALTROX_SIGN).solid(), ModWoodType.VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_HANGING_SIGN = registerBlockWithoutBlockItem("valtrox_hanging_sign",
            new DryableHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN).strength(1.0f).solid(), ModWoodType.VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("valtrox_wall_hanging_sign",
            new DryableWallHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN).strength(1.0f).dropsLike(VALTROX_HANGING_SIGN).solid(), ModWoodType.VALTROX), ModItemGroup.SCULK_DEPTHS);

    //coated valtrox blockset
    public static final Block COATED_VALTROX_LOG = registerBlock("coated_valtrox_log",
            new PillarBlock(FabricBlockSettings.copyOf(ModBlocks.VALTROX_LOG)), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_VALTROX_WOOD = registerBlock("coated_valtrox_wood",
            new PillarBlock(FabricBlockSettings.copyOf(ModBlocks.VALTROX_WOOD)), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_STRIPPED_VALTROX_LOG = registerBlock("coated_stripped_valtrox_log",
            new PillarBlock(FabricBlockSettings.copyOf(ModBlocks.STRIPPED_VALTROX_LOG)), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_STRIPPED_VALTROX_WOOD = registerBlock("coated_stripped_valtrox_wood",
            new PillarBlock(FabricBlockSettings.copyOf(ModBlocks.STRIPPED_VALTROX_WOOD)), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_VALTROX_PLANKS = registerBlock("coated_valtrox_planks",
            new Block(FabricBlockSettings.copyOf(ModBlocks.VALTROX_PLANKS)), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_VALTROX_STAIRS = registerBlock("coated_valtrox_stairs",
            new StairsBlock(COATED_VALTROX_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.VALTROX_STAIRS)), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_VALTROX_SLAB = registerBlock("coated_valtrox_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.VALTROX_SLAB)), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_VALTROX_FENCE = registerBlock("coated_valtrox_fence",
            new FenceBlock(FabricBlockSettings.copyOf(ModBlocks.VALTROX_FENCE)), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_VALTROX_FENCE_GATE = registerBlock("coated_valtrox_fence_gate",
            new FenceGateBlock(FabricBlockSettings.copyOf(ModBlocks.VALTROX_FENCE_GATE), ModWoodType.COATED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_VALTROX_DOOR = registerBlock("coated_valtrox_door",
            new DoorBlock(FabricBlockSettings.copyOf(ModBlocks.VALTROX_DOOR), ModBlockSetType.COATED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_VALTROX_TRAPDOOR = registerBlock("coated_valtrox_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(ModBlocks.VALTROX_TRAPDOOR), ModBlockSetType.COATED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_VALTROX_PRESSURE_PLATE = registerBlock("coated_valtrox_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.copyOf(ModBlocks.VALTROX_PRESSURE_PLATE), ModBlockSetType.COATED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_VALTROX_BUTTON = registerBlock("coated_valtrox_button",
            new ButtonBlock(FabricBlockSettings.copyOf(ModBlocks.VALTROX_BUTTON), ModBlockSetType.COATED_VALTROX, 30, true), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_VALTROX_SIGN = registerBlockWithoutBlockItem("coated_valtrox_sign",
            new SignBlock(FabricBlockSettings.copyOf(ModBlocks.VALTROX_SIGN).solid(), ModWoodType.COATED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_VALTROX_WALL_SIGN = registerBlockWithoutBlockItem("coated_valtrox_wall_sign",
            new WallSignBlock(FabricBlockSettings.copyOf(ModBlocks.VALTROX_WALL_SIGN).dropsLike(ModBlocks.COATED_VALTROX_SIGN).solid(), ModWoodType.COATED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_VALTROX_HANGING_SIGN = registerBlockWithoutBlockItem("coated_valtrox_hanging_sign",
            new HangingSignBlock(FabricBlockSettings.copyOf(ModBlocks.VALTROX_HANGING_SIGN).solid(), ModWoodType.COATED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block COATED_VALTROX_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("coated_valtrox_wall_hanging_sign",
            new WallHangingSignBlock(FabricBlockSettings.copyOf(ModBlocks.VALTROX_WALL_HANGING_SIGN).dropsLike(COATED_VALTROX_HANGING_SIGN).solid(), ModWoodType.COATED_VALTROX), ModItemGroup.SCULK_DEPTHS);



    //dried valtrox blockset
    public static final Block DRIED_VALTROX_LOG = registerBlock("dried_valtrox_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_WOOD = registerBlock("dried_valtrox_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block STRIPPED_DRIED_VALTROX_LOG = registerBlock("stripped_dried_valtrox_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block STRIPPED_DRIED_VALTROX_WOOD = registerBlock("stripped_dried_valtrox_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_PLANKS = registerBlock("dried_valtrox_planks",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_STAIRS = registerBlock("dried_valtrox_stairs",
            new StairsBlock(DRIED_VALTROX_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OAK_STAIRS).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_SLAB = registerBlock("dried_valtrox_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_FENCE = registerBlock("dried_valtrox_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_FENCE_GATE = registerBlock("dried_valtrox_fence_gate",
            new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE).strength(2.0f), ModWoodType.DRIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_DOOR = registerBlock("dried_valtrox_door",
            new DoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_DOOR).strength(2.0f), ModBlockSetType.DRIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_TRAPDOOR = registerBlock("dried_valtrox_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).strength(2.0f), ModBlockSetType.DRIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_PRESSURE_PLATE = registerBlock("dried_valtrox_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.copyOf(Blocks.OAK_PRESSURE_PLATE), ModBlockSetType.DRIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_BUTTON = registerBlock("dried_valtrox_button",
            new ButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON), ModBlockSetType.DRIED_VALTROX, 30, true), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_SIGN = registerBlockWithoutBlockItem("dried_valtrox_sign",
            new SignBlock(FabricBlockSettings.copyOf(Blocks.OAK_SIGN).strength(1.0f).solid(), ModWoodType.DRIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_WALL_SIGN = registerBlockWithoutBlockItem("dried_valtrox_wall_sign",
            new WallSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN).strength(1.0f).dropsLike(ModBlocks.DRIED_VALTROX_SIGN).solid(), ModWoodType.DRIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_HANGING_SIGN = registerBlockWithoutBlockItem("dried_valtrox_hanging_sign",
            new HangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN).strength(1.0f).solid(), ModWoodType.DRIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("dried_valtrox_wall_hanging_sign",
            new WallHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN).strength(1.0f).dropsLike(ModBlocks.DRIED_VALTROX_HANGING_SIGN).solid(), ModWoodType.DRIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    //petrified valtrox blockset
    public static final Block PETRIFIED_VALTROX_LOG = registerBlock("petrified_valtrox_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_WOOD = registerBlock("petrified_valtrox_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block STRIPPED_PETRIFIED_VALTROX_LOG = registerBlock("stripped_petrified_valtrox_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block STRIPPED_PETRIFIED_VALTROX_WOOD = registerBlock("stripped_petrified_valtrox_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_SLATES = registerBlock("petrified_valtrox_slates",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_STAIRS = registerBlock("petrified_valtrox_stairs",
            new StairsBlock(PETRIFIED_VALTROX_SLATES.getDefaultState(), FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_SLAB = registerBlock("petrified_valtrox_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_WALL = registerBlock("petrified_valtrox_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f,5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_WALL_GATE = registerBlock("petrified_valtrox_wall_gate",
            new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f,5.0f).requiresTool(), ModWoodType.PETRIFIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_DOOR = registerBlock("petrified_valtrox_door",
            new DoorBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f,5.0f).requiresTool(), ModBlockSetType.PETRIFIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_TRAPDOOR = registerBlock("petrified_valtrox_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f,5.0f).requiresTool(), ModBlockSetType.PETRIFIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_PRESSURE_PLATE = registerBlock("petrified_valtrox_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.copyOf(Blocks.STONE_PRESSURE_PLATE).strength(1.0f).requiresTool(), ModBlockSetType.PETRIFIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_BUTTON = registerBlock("petrified_valtrox_button",
            new ButtonBlock(FabricBlockSettings.copyOf(Blocks.STONE_BUTTON).strength(1.0f).requiresTool(), ModBlockSetType.PETRIFIED_VALTROX, 20, false), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_SIGN = registerBlockWithoutBlockItem("petrified_valtrox_sign",
            new SignBlock(FabricBlockSettings.copyOf(Blocks.OAK_SIGN).strength(1.5f).requiresTool().solid(), ModWoodType.PETRIFIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_WALL_SIGN = registerBlockWithoutBlockItem("petrified_valtrox_wall_sign",
            new WallSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN).strength(1.5f).requiresTool().dropsLike(ModBlocks.PETRIFIED_VALTROX_SIGN).solid(), ModWoodType.PETRIFIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_HANGING_SIGN = registerBlockWithoutBlockItem("petrified_valtrox_hanging_sign",
            new HangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN).strength(1.5f).requiresTool().solid(), ModWoodType.PETRIFIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("petrified_valtrox_wall_hanging_sign",
            new WallHangingSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN).strength(1.5f).requiresTool().dropsLike(ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN).solid(), ModWoodType.PETRIFIED_VALTROX), ModItemGroup.SCULK_DEPTHS);

    //zygrin set
    public static final Block ZYGRIN = registerBlock("zygrin",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(6.0f,9.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_STAIRS = registerBlock("zygrin_stairs",
            new StairsBlock(ZYGRIN.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_SLAB = registerBlock("zygrin_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_WALL = registerBlock("zygrin_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_PILLAR = registerBlock("zygrin_pillar",
            new PillarBlock(FabricBlockSettings.copyOf(ModBlocks.ZYGRIN)), ModItemGroup.SCULK_DEPTHS);

    public static final Block CHISELED_ZYGRIN = registerBlock("chiseled_zygrin",
            new PillarBlock(FabricBlockSettings.copyOf(ModBlocks.ZYGRIN)), ModItemGroup.SCULK_DEPTHS);

    public static final Block POLISHED_ZYGRIN = registerBlock("polished_zygrin",
            new Block(FabricBlockSettings.copyOf(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block POLISHED_ZYGRIN_STAIRS = registerBlock("polished_zygrin_stairs",
            new StairsBlock(POLISHED_ZYGRIN.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block POLISHED_ZYGRIN_SLAB = registerBlock("polished_zygrin_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block POLISHED_ZYGRIN_WALL = registerBlock("polished_zygrin_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_BRICKS = registerBlock("zygrin_bricks",
            new Block(FabricBlockSettings.copyOf(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_BRICK_STAIRS = registerBlock("zygrin_brick_stairs",
            new StairsBlock(ZYGRIN_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_BRICK_SLAB = registerBlock("zygrin_brick_slab",
            new SlabBlock(FabricBlockSettings.copyOf(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_BRICK_WALL = registerBlock("zygrin_brick_wall",
            new WallBlock(FabricBlockSettings.copyOf(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_LIGHT = registerBlock("zygrin_light",
            new Block(FabricBlockSettings.copyOf(ModBlocks.ZYGRIN).requiresTool().luminance(15)), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_FLOWBLOCK = registerBlock("zygrin_flowblock",
            new FlowBlock(FabricBlockSettings.copyOf(ModBlocks.ZYGRIN).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    //leaves
    public static final Block VALTROX_LEAVES = registerBlock("valtrox_leaves",
            new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)), ModItemGroup.SCULK_DEPTHS);

    //saplings
    public static final Block VALTROX_SAPLING = registerBlock("valtrox_sapling",
            new SaplingBlock(new ValtroxSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)), ModItemGroup.SCULK_DEPTHS);

    //vegetation
    public static final Block CEPHLERA = registerBlockWithoutBlockItem("cephlera",
            new CephleraBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.WEEPING_VINES)), ModItemGroup.SCULK_DEPTHS);
    public static final Block CEPHLERA_LIGHT = registerBlock("cephlera_light",
            new CephleraLightBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).noCollision().breakInstantly().emissiveLighting(AbstractBlock.AbstractBlockState::isFullCube).luminance(blockState -> 15).sounds(BlockSoundGroup.WEEPING_VINES)), ModItemGroup.SCULK_DEPTHS);

    public static final Block QUAZARITH_BLOCK = registerBlock("quazarith_block",
            new Block(FabricBlockSettings.copyOf(Blocks.NETHERITE_BLOCK).strength(60.0f,1400.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);
    public static final Block CRUX_BLOCK = registerBlock("crux_block",
            new Block(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK).strength(5.0F, 6.0F).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    //ores
    public static final Block QUAZARITH_ORE = registerBlock("quazarith_ore",
            new LesterSpawningBlock(FabricBlockSettings.copyOf(ModBlocks.UMBRUSK).strength(5.5f,9.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);
    public static final Block CRUX_ORE = registerBlock("crux_ore",
            new Block(FabricBlockSettings.copyOf(ModBlocks.ZYGRIN).strength(4.5f,8.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    //crystals

    public static final Block WHITE_CRYSTAL_BLOCK = registerBlock("white_crystal_block", ModBlocks.createCrystalBlock(DyeColor.WHITE), ModItemGroup.SCULK_DEPTHS);
    public static final Block BLUE_CRYSTAL_BLOCK = registerBlock("blue_crystal_block", ModBlocks.createCrystalBlock(DyeColor.BLUE), ModItemGroup.SCULK_DEPTHS);
    public static final Block ORANGE_CRYSTAL_BLOCK = registerBlock("orange_crystal_block", ModBlocks.createCrystalBlock(DyeColor.ORANGE), ModItemGroup.SCULK_DEPTHS);
    public static final Block LIME_CRYSTAL_BLOCK = registerBlock("lime_crystal_block", ModBlocks.createCrystalBlock(DyeColor.LIME), ModItemGroup.SCULK_DEPTHS);

    //penebrium
    public static final Block PENEBRIUM_SHROOM = registerBlock("penebrium_shroom",
            new MushroomPlantBlock(FabricBlockSettings.copyOf(Blocks.WARPED_FUNGUS).luminance(5).emissiveLighting(AbstractBlock.AbstractBlockState::isFullCube).noCollision(), ModConfiguredFeatures.PENEBRIUM_SHROOM), ModItemGroup.SCULK_DEPTHS);

    public static final Block PENEBRIUM_SHROOM_BLOCK = registerBlock("penebrium_shroom_block",
            new PenebriumShroomBlock( FabricBlockSettings.copyOf(Blocks.MUSHROOM_STEM).luminance(5).emissiveLighting(AbstractBlock.AbstractBlockState::isFullCube).nonOpaque()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PENEBRIUM_SHROOM_STEM = registerBlock("penebrium_shroom_stem",
            new PenebriumShroomBlock(FabricBlockSettings.copyOf(Blocks.MUSHROOM_STEM).nonOpaque()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PENEBRIUM_SPORE_BLOCK = registerBlock("penebrium_spore_block",
            new SporeBlock(FabricBlockSettings.copyOf(Blocks.MUSHROOM_STEM).luminance(5).emissiveLighting(AbstractBlock.AbstractBlockState::isFullCube).nonOpaque()), ModItemGroup.SCULK_DEPTHS);

    //misc
    public static final CustomPortalBlock SCULK_DEPTHS_PORTAL = (CustomPortalBlock) registerBlockWithoutBlockItem("sculk_depths_portal",
            new CustomPortalBlock(FabricBlockSettings.copyOf(Blocks.NETHER_PORTAL).luminance(6).dropsNothing().noCollision().strength(-1.0f,3600000.0f)),ModItemGroup.SCULK_DEPTHS );

    //auric
    public static final Block AURIC_SPORE_BLOCK = registerBlock("auric_spore_block",
            new Block(FabricBlockSettings.create().strength(0.3f).sounds(BlockSoundGroup.MOSS_BLOCK)), ModItemGroup.SCULK_DEPTHS);

    public static final Block AURIC_SPORE_SPOUTS = registerBlock("auric_spore_sprouts",
            new SproutsBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).noCollision().breakInstantly().sounds(BlockSoundGroup.WEEPING_VINES)), ModItemGroup.SCULK_DEPTHS);

    //fluids
    public static final Block KRYSLUM = registerBlockWithoutBlockItem("kryslum", new FluidBlock(ModFluids.KRYSLUM_STILL, FabricBlockSettings.copyOf(Blocks.WATER).replaceable().noCollision().strength(100.0f).pistonBehavior(PistonBehavior.DESTROY).dropsNothing().liquid().solid().sounds(BlockSoundGroup.SCULK)), ModItemGroup.SCULK_DEPTHS);

    public static final Block FLUMROCK_CAULDRON = registerBlock("flumrock_cauldron", new FlumrockCauldronBlock(FabricBlockSettings.copyOf(Blocks.CAULDRON).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);
    public static final Block KRYSLUM_FLUMROCK_CAULDRON = registerBlockWithoutBlockItem("kryslum_flumrock_cauldron", new KryslumFlumrockCauldronBlock(FabricBlockSettings.copyOf(ModBlocks.FLUMROCK_CAULDRON), ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR), ModItemGroup.SCULK_DEPTHS);
    public static final Block SPORE_FLUMROCK_CAULDRON = registerBlockWithoutBlockItem("spore_flumrock_cauldron", new SporeFlumrockCauldronBlock(FabricBlockSettings.copyOf(ModBlocks.FLUMROCK_CAULDRON), ModCauldronBehavior.SPORE_FLUMROCK_CAULDRON_BEHAVIOR), ModItemGroup.SCULK_DEPTHS);

    private static CrystalBlock createCrystalBlock(DyeColor color) {
        return new CrystalBlock(color, FabricBlockSettings.copyOf(Blocks.GLASS).luminance(10));
    }

    private static Block registerBlock(String name, Block block, RegistryKey<ItemGroup> group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(SculkDepths.MOD_ID, name), block);
    }


    private static Block registerBlockWithoutBlockItem(String name, Block block, RegistryKey<ItemGroup> group) {
        return Registry.register(Registries.BLOCK, new Identifier(SculkDepths.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, RegistryKey<ItemGroup> group) {

        Item item = Registry.register(Registries.ITEM, new Identifier(SculkDepths.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return item;
    }

    public static void registerModBlocks() {
        ModStrippableBlocks.registerStrippables();
    }
}