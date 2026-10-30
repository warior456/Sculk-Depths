package net.ugi.sculk_depths.block;

//import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.core.Direction;
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
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).strength(3.0f, 6.0f).requiresCorrectToolForDrops().sound(ModBlockSoundGroups.AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //polished amalgamite blockset
    public static final Block POLISHED_AMALGAMITE = registerBlock("polished_amalgamite",
            new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.AMALGAMITE).requiresCorrectToolForDrops()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block POLISHED_AMALGAMITE_STAIRS = registerBlock("polished_amalgamite_stairs",
            new StairBlock(ModBlocks.POLISHED_AMALGAMITE.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block POLISHED_AMALGAMITE_SLAB = registerBlock("polished_amalgamite_slab",
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block POLISHED_AMALGAMITE_WALL = registerBlock("polished_amalgamite_wall",
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_AMALGAMITE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //amalgamite bricks blockset
    public static final Block AMALGAMITE_BRICKS = registerBlock("amalgamite_bricks",
            new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.AMALGAMITE).requiresCorrectToolForDrops()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block CRACKED_AMALGAMITE_BRICKS = registerBlock("cracked_amalgamite_bricks",
            new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.AMALGAMITE_BRICKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AMALGAMITE_BRICK_STAIRS = registerBlock("amalgamite_brick_stairs",
            new StairBlock(ModBlocks.AMALGAMITE_BRICKS.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(ModBlocks.AMALGAMITE_BRICKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AMALGAMITE_BRICK_SLAB = registerBlock("amalgamite_brick_slab",
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.AMALGAMITE_BRICKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AMALGAMITE_BRICK_WALL = registerBlock("amalgamite_brick_wall",
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.AMALGAMITE_BRICKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //rest
    public static final Block CHISELED_AMALGAMITE = registerBlock("chiseled_amalgamite",
            new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.AMALGAMITE_BRICKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ACTIVATED_AMALGAMITE = registerBlock("activated_amalgamite",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.REINFORCED_DEEPSLATE).strength(-1.0f, 3600000.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);


    // ------------------- umbrusk -------------------

    //umbrusk blockset
    public static final Block UMBRUSK = registerBlock("umbrusk",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(5.0f,8f).requiresCorrectToolForDrops().mapColor(MapColor.COLOR_BLACK)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block UMBRUSK_STAIRS = registerBlock("umbrusk_stairs",
            new StairBlock(UMBRUSK.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(ModBlocks.UMBRUSK)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block UMBRUSK_SLAB = registerBlock("umbrusk_slab",
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.UMBRUSK)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block UMBRUSK_WALL = registerBlock("umbrusk_wall",
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.UMBRUSK)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //umbrusk bricks blockset
    public static final Block UMBRUSK_BRICKS = registerBlock("umbrusk_bricks",
            new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.UMBRUSK)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block UMBRUSK_BRICK_STAIRS = registerBlock("umbrusk_brick_stairs",
            new StairBlock(UMBRUSK_BRICKS.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(ModBlocks.UMBRUSK_BRICKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block UMBRUSK_BRICK_SLAB = registerBlock("umbrusk_brick_slab",
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.UMBRUSK_BRICKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block UMBRUSK_BRICK_WALL = registerBlock("umbrusk_brick_wall",
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.UMBRUSK_BRICKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);


    // ------------------- zygrin  -------------------

    //zygrin blockset
    public static final Block ZYGRIN = registerBlock("zygrin",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(6.0f,9.0f).requiresCorrectToolForDrops().mapColor(MapColor.SNOW)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_STAIRS = registerBlock("zygrin_stairs",
            new StairBlock(ZYGRIN.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(ModBlocks.ZYGRIN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_SLAB = registerBlock("zygrin_slab",
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.ZYGRIN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_WALL = registerBlock("zygrin_wall",
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.ZYGRIN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //polished zygrin blockset
    public static final Block POLISHED_ZYGRIN = registerBlock("polished_zygrin",
            new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.ZYGRIN).requiresCorrectToolForDrops()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block POLISHED_ZYGRIN_STAIRS = registerBlock("polished_zygrin_stairs",
            new StairBlock(POLISHED_ZYGRIN.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_ZYGRIN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block POLISHED_ZYGRIN_SLAB = registerBlock("polished_zygrin_slab",
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_ZYGRIN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block POLISHED_ZYGRIN_WALL = registerBlock("polished_zygrin_wall",
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_ZYGRIN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //zygrin bricks blockset
    public static final Block ZYGRIN_BRICKS = registerBlock("zygrin_bricks",
            new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.ZYGRIN).requiresCorrectToolForDrops()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_BRICK_STAIRS = registerBlock("zygrin_brick_stairs",
            new StairBlock(ZYGRIN_BRICKS.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(ModBlocks.ZYGRIN_BRICKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_BRICK_SLAB = registerBlock("zygrin_brick_slab",
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.ZYGRIN_BRICKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_BRICK_WALL = registerBlock("zygrin_brick_wall",
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.ZYGRIN_BRICKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //rest
    public static final Block ZYGRIN_PILLAR = registerBlock("zygrin_pillar",
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.ZYGRIN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block CHISELED_ZYGRIN = registerBlock("chiseled_zygrin",
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.ZYGRIN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_LIGHT = registerBlock("zygrin_light",
            new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.ZYGRIN).lightLevel((state) -> 15)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_FLOWBLOCK = registerBlock("zygrin_flowblock",
            new FlowBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.ZYGRIN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block ZYGRIN_FURNACE = registerBlock("zygrin_furnace",
            new ZygrinFurnaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE).strength(10.0f,10f).requiresCorrectToolForDrops()), ModItemGroup.SCULK_DEPTHS_BLOCKS);


    // ------------------- flumrock -------------------
    public static final Block FLUMROCK = registerBlock("flumrock",
            new Block(BlockBehaviour.Properties.of().strength(4.0f,5.5f).requiresCorrectToolForDrops().mapColor(MapColor.TERRACOTTA_GREEN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //flumrock tiles
    public static final Block FLUMROCK_TILES = registerBlock("flumrock_tiles",
            new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.FLUMROCK).strength(4.5f,6f).requiresCorrectToolForDrops()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block CRUMBLING_FLUMROCK_TILES = registerBlock("crumbling_flumrock_tiles",
            new Block(BlockBehaviour.Properties.ofFullCopy(FLUMROCK_TILES).strength(3.5f,5.5f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block FLUMROCK_TILE_STAIRS = registerBlock("flumrock_tile_stairs",
            new StairBlock(FLUMROCK_TILES.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(ModBlocks.FLUMROCK_TILES)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block FLUMROCK_TILE_SLAB = registerBlock("flumrock_tile_slab",
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.FLUMROCK_TILES)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block FLUMROCK_TILE_WALL = registerBlock("flumrock_tile_wall",
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.FLUMROCK_TILES)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //cauldron
    public static final Block FLUMROCK_CAULDRON = registerBlock("flumrock_cauldron",
            new FlumrockCauldronBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON).strength(4.5f,6f).requiresCorrectToolForDrops()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block KRYSLUM_FLUMROCK_CAULDRON = registerBlockWithoutBlockItem("kryslum_flumrock_cauldron",
            new KryslumFlumrockCauldronBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.FLUMROCK_CAULDRON).dropsLike(ModBlocks.FLUMROCK_CAULDRON), ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR.map()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block SPORE_FLUMROCK_CAULDRON = registerBlockWithoutBlockItem("spore_flumrock_cauldron",
            new SporeFlumrockCauldronBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.FLUMROCK_CAULDRON).dropsLike(ModBlocks.FLUMROCK_CAULDRON), ModCauldronBehavior.SPORE_FLUMROCK_CAULDRON_BEHAVIOR.map()), ModItemGroup.SCULK_DEPTHS_BLOCKS);



    // ------------------- valtrox  -------------------

    //valtrox blockset
    public static final Block VALTROX_LOG = registerBlock("valtrox_log",
            new DryablePillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.DIAMOND : MapColor.COLOR_LIGHT_BLUE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_WOOD = registerBlock("valtrox_wood",
            new DryablePillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block STRIPPED_VALTROX_LOG = registerBlock("stripped_valtrox_log",
            new DryablePillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block STRIPPED_VALTROX_WOOD = registerBlock("stripped_valtrox_wood",
            new DryablePillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_PLANKS = registerBlock("valtrox_planks",
            new DryableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_STAIRS = registerBlock("valtrox_stairs",
            new DryableStairsBlock(VALTROX_PLANKS.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_SLAB = registerBlock("valtrox_slab",
            new DryableSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_FENCE = registerBlock("valtrox_fence",
            new DryableFenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_FENCE_GATE = registerBlock("valtrox_fence_gate",
            new DryableFenceGateBlock(ModWoodType.VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_DOOR = registerBlock("valtrox_door",
            new DryableDoorBlock(ModBlockSetType.VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_TRAPDOOR = registerBlock("valtrox_trapdoor",
            new DryableTrapdoorBlock(ModBlockSetType.VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_PRESSURE_PLATE = registerBlock("valtrox_pressure_plate",
            new DryablePressurePlateBlock(ModBlockSetType.VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_BUTTON = registerBlock("valtrox_button",
            new DryableButtonBlock(ModBlockSetType.VALTROX, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_SIGN = registerBlockWithoutBlockItem("valtrox_sign",
            new DryableSignBlock(ModWoodType.VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_WALL_SIGN = registerBlockWithoutBlockItem("valtrox_wall_sign",
            new DryableWallSignBlock(ModWoodType.VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).dropsLike(ModBlocks.VALTROX_SIGN).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_HANGING_SIGN = registerBlockWithoutBlockItem("valtrox_hanging_sign",
            new DryableHangingSignBlock(ModWoodType.VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block VALTROX_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("valtrox_wall_hanging_sign",
            new DryableWallHangingSignBlock(ModWoodType.VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).dropsLike(VALTROX_HANGING_SIGN).mapColor(MapColor.DIAMOND)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //coated valtrox blockset
    public static final Block COATED_VALTROX_LOG = registerBlock("coated_valtrox_log",
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.VALTROX_LOG)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_WOOD = registerBlock("coated_valtrox_wood",
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.VALTROX_WOOD)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_STRIPPED_VALTROX_LOG = registerBlock("coated_stripped_valtrox_log",
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.STRIPPED_VALTROX_LOG)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_STRIPPED_VALTROX_WOOD = registerBlock("coated_stripped_valtrox_wood",
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.STRIPPED_VALTROX_WOOD)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_PLANKS = registerBlock("coated_valtrox_planks",
            new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.VALTROX_PLANKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_STAIRS = registerBlock("coated_valtrox_stairs",
            new StairBlock(COATED_VALTROX_PLANKS.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(ModBlocks.VALTROX_STAIRS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_SLAB = registerBlock("coated_valtrox_slab",
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.VALTROX_SLAB)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_FENCE = registerBlock("coated_valtrox_fence",
            new FenceBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.VALTROX_FENCE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_FENCE_GATE = registerBlock("coated_valtrox_fence_gate",
            new FenceGateBlock(ModWoodType.COATED_VALTROX, BlockBehaviour.Properties.ofFullCopy(ModBlocks.VALTROX_FENCE_GATE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_DOOR = registerBlock("coated_valtrox_door",
            new DoorBlock(ModBlockSetType.COATED_VALTROX, BlockBehaviour.Properties.ofFullCopy(ModBlocks.VALTROX_DOOR)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_TRAPDOOR = registerBlock("coated_valtrox_trapdoor",
            new TrapDoorBlock(ModBlockSetType.COATED_VALTROX, BlockBehaviour.Properties.ofFullCopy(ModBlocks.VALTROX_TRAPDOOR)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_PRESSURE_PLATE = registerBlock("coated_valtrox_pressure_plate",
            new PressurePlateBlock(ModBlockSetType.COATED_VALTROX, BlockBehaviour.Properties.ofFullCopy(ModBlocks.VALTROX_PRESSURE_PLATE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_BUTTON = registerBlock("coated_valtrox_button",
            new ButtonBlock(ModBlockSetType.COATED_VALTROX, 30, BlockBehaviour.Properties.ofFullCopy(ModBlocks.VALTROX_BUTTON)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_SIGN = registerBlockWithoutBlockItem("coated_valtrox_sign",
            new StandingSignBlock(ModWoodType.COATED_VALTROX, BlockBehaviour.Properties.ofFullCopy(ModBlocks.VALTROX_SIGN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_WALL_SIGN = registerBlockWithoutBlockItem("coated_valtrox_wall_sign",
            new WallSignBlock(ModWoodType.COATED_VALTROX, BlockBehaviour.Properties.ofFullCopy(ModBlocks.VALTROX_WALL_SIGN).dropsLike(ModBlocks.COATED_VALTROX_SIGN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_HANGING_SIGN = registerBlockWithoutBlockItem("coated_valtrox_hanging_sign",
            new CeilingHangingSignBlock(ModWoodType.COATED_VALTROX, BlockBehaviour.Properties.ofFullCopy(ModBlocks.VALTROX_HANGING_SIGN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block COATED_VALTROX_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("coated_valtrox_wall_hanging_sign",
            new WallHangingSignBlock(ModWoodType.COATED_VALTROX, BlockBehaviour.Properties.ofFullCopy(ModBlocks.VALTROX_WALL_HANGING_SIGN).dropsLike(COATED_VALTROX_HANGING_SIGN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);


    //dried valtrox blockset
    public static final Block DRIED_VALTROX_LOG = registerBlock("dried_valtrox_log",
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_WOOD = registerBlock("dried_valtrox_wood",
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block STRIPPED_DRIED_VALTROX_LOG = registerBlock("stripped_dried_valtrox_log",
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block STRIPPED_DRIED_VALTROX_WOOD = registerBlock("stripped_dried_valtrox_wood",
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_PLANKS = registerBlock("dried_valtrox_planks",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_STAIRS = registerBlock("dried_valtrox_stairs",
            new StairBlock(DRIED_VALTROX_PLANKS.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_SLAB = registerBlock("dried_valtrox_slab",
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_FENCE = registerBlock("dried_valtrox_fence",
            new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_FENCE_GATE = registerBlock("dried_valtrox_fence_gate",
            new FenceGateBlock(ModWoodType.DRIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_DOOR = registerBlock("dried_valtrox_door",
            new DoorBlock(ModBlockSetType.DRIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_TRAPDOOR = registerBlock("dried_valtrox_trapdoor",
            new TrapDoorBlock(ModBlockSetType.DRIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_PRESSURE_PLATE = registerBlock("dried_valtrox_pressure_plate",
            new PressurePlateBlock(ModBlockSetType.DRIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_BUTTON = registerBlock("dried_valtrox_button",
            new ButtonBlock(ModBlockSetType.DRIED_VALTROX, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_SIGN = registerBlockWithoutBlockItem("dried_valtrox_sign",
            new StandingSignBlock(ModWoodType.DRIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_WALL_SIGN = registerBlockWithoutBlockItem("dried_valtrox_wall_sign",
            new WallSignBlock(ModWoodType.DRIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).dropsLike(ModBlocks.DRIED_VALTROX_SIGN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_HANGING_SIGN = registerBlockWithoutBlockItem("dried_valtrox_hanging_sign",
            new CeilingHangingSignBlock(ModWoodType.DRIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DRIED_VALTROX_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("dried_valtrox_wall_hanging_sign",
            new WallHangingSignBlock(ModWoodType.DRIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).dropsLike(ModBlocks.DRIED_VALTROX_HANGING_SIGN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    //petrified valtrox blockset
    public static final Block PETRIFIED_VALTROX_LOG = registerBlock("petrified_valtrox_log",
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3.0f,5.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_WOOD = registerBlock("petrified_valtrox_wood",
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3.0f,5.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block STRIPPED_PETRIFIED_VALTROX_LOG = registerBlock("stripped_petrified_valtrox_log",
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3.0f,5.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block STRIPPED_PETRIFIED_VALTROX_WOOD = registerBlock("stripped_petrified_valtrox_wood",
            new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3.0f,5.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_SLATES = registerBlock("petrified_valtrox_slates",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3.0f,5.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_STAIRS = registerBlock("petrified_valtrox_stairs",
            new StairBlock(PETRIFIED_VALTROX_SLATES.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3.0f,5.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_SLAB = registerBlock("petrified_valtrox_slab",
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3.0f,5.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_WALL = registerBlock("petrified_valtrox_wall",
            new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3.0f,5.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_WALL_GATE = registerBlock("petrified_valtrox_wall_gate",
            new FenceGateBlock(ModWoodType.PETRIFIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3.0f,5.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_DOOR = registerBlock("petrified_valtrox_door",
            new DoorBlock(ModBlockSetType.PETRIFIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3.0f,5.0f).noOcclusion()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_TRAPDOOR = registerBlock("petrified_valtrox_trapdoor",
            new TrapDoorBlock(ModBlockSetType.PETRIFIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3.0f,5.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_PRESSURE_PLATE = registerBlock("petrified_valtrox_pressure_plate",
            new PressurePlateBlock(ModBlockSetType.PETRIFIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE).strength(1.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_BUTTON = registerBlock("petrified_valtrox_button",
            new ButtonBlock(ModBlockSetType.PETRIFIED_VALTROX, 20, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BUTTON).strength(1.0f)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_SIGN = registerBlockWithoutBlockItem("petrified_valtrox_sign",
            new StandingSignBlock(ModWoodType.PETRIFIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).strength(1.5f).requiresCorrectToolForDrops()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_WALL_SIGN = registerBlockWithoutBlockItem("petrified_valtrox_wall_sign",
            new WallSignBlock(ModWoodType.PETRIFIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).strength(1.5f).requiresCorrectToolForDrops().dropsLike(ModBlocks.PETRIFIED_VALTROX_SIGN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_HANGING_SIGN = registerBlockWithoutBlockItem("petrified_valtrox_hanging_sign",
            new CeilingHangingSignBlock(ModWoodType.PETRIFIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).strength(1.5f).requiresCorrectToolForDrops()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PETRIFIED_VALTROX_WALL_HANGING_SIGN = registerBlockWithoutBlockItem("petrified_valtrox_wall_hanging_sign",
            new WallHangingSignBlock(ModWoodType.PETRIFIED_VALTROX, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).strength(1.5f).requiresCorrectToolForDrops().dropsLike(ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN)), ModItemGroup.SCULK_DEPTHS_BLOCKS);
    //

    // ------------------- leaves -------------------
    public static final Block VALTROX_LEAVES = registerBlock("valtrox_leaves",
            new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_PURPLE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    // ------------------- saplings -------------------
    public static final Block VALTROX_SAPLING = registerBlock("valtrox_sapling",
            new SaplingBlock(WorldGenerator.VALTROX_SAPLING_GENERATOR, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_PURPLE)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    // ------------------- cephlera -------------------

    public static final Block CEPHLERA = registerBlockWithoutBlockItem("cephlera",
            new CephleraBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).randomTicks().noCollission().instabreak().sound(SoundType.WEEPING_VINES)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block CEPHLERA_LIGHT = registerBlock("cephlera_light",
            new CephleraLightBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.CEPHLERA).emissiveRendering(BlockBehaviour.BlockStateBase::isCollisionShapeFullBlock).lightLevel(block -> 15).dropsLike(ModBlocks.CEPHLERA)), ModItemGroup.SCULK_DEPTHS_BLOCKS);


    // ------------------- resource blocks  -------------------
    public static final Block QUAZARITH_BLOCK = registerBlock("quazarith_block",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK).strength(60.0f,1400.0f).requiresCorrectToolForDrops()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block CRUX_BLOCK = registerBlock("crux_block",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).strength(5.0F, 6.0F).requiresCorrectToolForDrops()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    // ------------------- ores -------------------
    public static final Block QUAZARITH_ORE = registerBlock("quazarith_ore",
            new LesterSpawningBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.UMBRUSK).strength(5.5f,9.0f).requiresCorrectToolForDrops()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block CRUX_ORE = registerBlock("crux_ore",
            new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.ZYGRIN).strength(4.5f,8.0f).requiresCorrectToolForDrops()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    // ------------------- crystals -------------------
    public static final Block WHITE_CRYSTAL_BLOCK = registerBlock("white_crystal_block", ModBlocks.createCrystalBlock(DyeColor.WHITE), ModItemGroup.SCULK_DEPTHS_BLOCKS);
    public static final Block BLUE_CRYSTAL_BLOCK = registerBlock("blue_crystal_block", ModBlocks.createCrystalBlock(DyeColor.BLUE), ModItemGroup.SCULK_DEPTHS_BLOCKS);
    public static final Block ORANGE_CRYSTAL_BLOCK = registerBlock("orange_crystal_block", ModBlocks.createCrystalBlock(DyeColor.ORANGE), ModItemGroup.SCULK_DEPTHS_BLOCKS);
    public static final Block LIME_CRYSTAL_BLOCK = registerBlock("lime_crystal_block", ModBlocks.createCrystalBlock(DyeColor.LIME), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    // ------------------- penebrium -------------------
    public static final Block PENEBRIUM_SHROOM = registerBlock("penebrium_shroom",
            new MushroomBlock(ModConfiguredFeatures.PENEBRIUM_SHROOM, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM)), ModItemGroup.SCULK_DEPTHS_BLOCKS);//.luminance((state) -> 5).emissiveLighting(AbstractBlock.AbstractBlockState::isFullCube))

    public static final Block PENEBRIUM_SHROOM_BLOCK = registerBlock("penebrium_shroom_block",
            new ShroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM).lightLevel((state) -> 5).emissiveRendering(BlockBehaviour.BlockStateBase::isCollisionShapeFullBlock).noOcclusion()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PENEBRIUM_SHROOM_STEM = registerBlock("penebrium_shroom_stem",
            new ShroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM).noOcclusion()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block PENEBRIUM_SPORE_BLOCK = registerBlock("penebrium_spore_block",
            new SporeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM).lightLevel((state) -> 5).emissiveRendering(BlockBehaviour.BlockStateBase::isCollisionShapeFullBlock).noOcclusion()), ModItemGroup.SCULK_DEPTHS_BLOCKS);



    // ------------------- misc -------------------
    public static final Block CRUMBLING_DIRT = registerBlock("crumbling_dirt",
            new Block(BlockBehaviour.Properties.of().strength(0.3f).sound(SoundType.ROOTED_DIRT).mapColor(MapColor.DIRT)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block LARGUTH = registerBlock("larguth",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN).strength(40.0f,1000f).requiresCorrectToolForDrops().mapColor(MapColor.NETHER)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block DEAD_GRASS = registerBlock("dead_grass",
            new CustomPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).mapColor(MapColor.COLOR_LIGHT_GRAY)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block KRYSLUM_ENRICHED_SOIL = registerBlock("kryslum_enriched_soil",
            new KryslumEnrichedSoilBLock(BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND).randomTicks().strength(0.8f).sound(SoundType.MUDDY_MANGROVE_ROOTS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);
    
    public static final Block SCULK_DEPTHS_PORTAL = registerBlockWithoutBlockItem("sculk_depths_portal",
            new SculkDepthsPortalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL).lightLevel((state) -> 6).noLootTable().noCollission().strength(-1.0f,3600000.0f)),ModItemGroup.SCULK_DEPTHS_BLOCKS );

    public static final Block SCULK_PEDESTAL = registerBlock("sculk_pedestal",
            new PedestalBlock(BlockBehaviour.Properties.of().strength(-1f, 3600000.0f).pushReaction(PushReaction.BLOCK)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block QELBERRY_BUSH = registerBlockWithoutBlockItem("qelberry_bush",
        new QeldryBerryBush(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block QUAZARITH_OSCILLATOR = registerBlock("quazarith_oscillator",
            new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.AMALGAMITE_BRICKS).requiresCorrectToolForDrops()), ModItemGroup.SCULK_DEPTHS_BLOCKS);



    // ------------------- auric -------------------
    public static final Block AURIC_SPORE_BLOCK = registerBlock("auric_spore_block",
            new ShroomBlock(BlockBehaviour.Properties.of().strength(0.2f).mapColor(MapColor.SAND).sound(SoundType.MOSS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AURIC_SHROOM_BLOCK = registerBlock("auric_shroom_block",
            new ShroomBlock(BlockBehaviour.Properties.of().strength(0.2f).mapColor(MapColor.SAND).sound(SoundType.MOSS)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AURIC_SHROOM_STEM = registerBlock("auric_shroom_stem",
            new ShroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM).mapColor(MapColor.SAND).noOcclusion()), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AURIC_VINES = registerBlockWithoutBlockItem("auric_vines",
            new AuricVinesBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).randomTicks().noCollission().instabreak().sound(SoundType.TWISTING_VINES)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AURIC_VINES_END = registerBlock("auric_vines_end",
            new AuricVinesEndBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.AURIC_VINES).dropsLike(ModBlocks.AURIC_VINES)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AURIC_SPORE_LAYER = registerBlock("auric_spore_layer",
            new LayeredAuricSporeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.5F).sound(SoundType.SAND).forceSolidOff().randomTicks()
                    .isViewBlocking((state, level, pos) -> state.getValue(SnowLayerBlock.LAYERS) >= 8)
                    .pushReaction(PushReaction.DESTROY)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block AURIC_SPORE_SPROUTS = registerBlock("auric_spore_sprouts",
            new ShroomPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM).lightLevel((state)-> 0).mapColor(MapColor.SAND).sound(SoundType.WEEPING_VINES), ModConfiguredFeatures.AURIC_SHROOM) , ModItemGroup.SCULK_DEPTHS_BLOCKS);

    // ------------------- fluids -------------------
    public static final Block KRYSLUM = registerBlockWithoutBlockItem("kryslum",
            new LiquidBlock(ModFluids.KRYSLUM_STILL, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).replaceable().noCollission().strength(100.0f).pushReaction(PushReaction.DESTROY).noLootTable().liquid()/*.solid()*/.sound(SoundType.SCULK)), ModItemGroup.SCULK_DEPTHS_BLOCKS);


    // ------------------- potted stuff -------------------
    public static final Block POTTED_VALTROX_SAPLING = registerBlockWithoutBlockItem("potted_valtrox_sapling",
            new FlowerPotBlock(ModBlocks.VALTROX_SAPLING, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block POTTED_PENEBRIUM_SHROOM = registerBlockWithoutBlockItem("potted_penebrium_shroom",
            new FlowerPotBlock(ModBlocks.PENEBRIUM_SHROOM, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)), ModItemGroup.SCULK_DEPTHS_BLOCKS);

    public static final Block POTTED_AURIC_SPORE_SPROUTS = registerBlockWithoutBlockItem("potted_auric_spore_sprouts",
            new FlowerPotBlock(ModBlocks.AURIC_SPORE_SPROUTS, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)), ModItemGroup.SCULK_DEPTHS_BLOCKS);


    private static CrystalBlock createCrystalBlock(DyeColor setColor) {
        return new CrystalBlock(setColor, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).lightLevel((state) -> 10));
    }

    private static Block registerBlock(String name, Block block, ResourceKey<CreativeModeTab> recipeGroupPrefix) {
        registerBlockItem(name, block, recipeGroupPrefix);
        return Registry.register(BuiltInRegistries.BLOCK, SculkDepths.id( name), block);
    }


    private static Block registerBlockWithoutBlockItem(String name, Block block, ResourceKey<CreativeModeTab> recipeGroupPrefix) {
        return Registry.register(BuiltInRegistries.BLOCK, SculkDepths.id(name), block);
    }

    private static Item registerBlockItem(String name, Block block, ResourceKey<CreativeModeTab> recipeGroupPrefix) {

        Item items = Registry.register(BuiltInRegistries.ITEM, SculkDepths.id( name),
                new BlockItem(block, new Item.Properties()));
        net.ugi.sculk_depths.item.ModItems.addToItemGroup(recipeGroupPrefix, items);
        return items;
    }

    public static void registerModBlocks() {
        ModFlammableBlocks.registerFlammables();
        ModStrippableBlocks.registerStrippables();
    }
}
