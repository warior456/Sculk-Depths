package net.ugi.sculk_depths.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagBuilder;
import net.minecraft.sound.BlockSoundGroup;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.custom.*;
import net.ugi.sculk_depths.block.custom.SoulFireBlock;
import net.ugi.sculk_depths.block.sapling.ValtroxSaplingGenerator;
import net.ugi.sculk_depths.fluid.ModFluids;
import net.ugi.sculk_depths.item.ModItemGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModBlocks {

    public static final Block FLUMROCK = registerBlock("flumrock",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block CRUMBLING_DIRT = registerBlock("crumbling_dirt",
            new Block(FabricBlockSettings.copyOf(Blocks.DIRT).strength(1.0f).requiresTool().sounds(BlockSoundGroup.ROOTED_DIRT)), ModItemGroup.SCULK_DEPTHS);

    public static final Block KRYSLUM_ENRICHED_SOIL = registerBlock("kryslum_enriched_soil",
            new KryslumEnrichedSoilBLock(FabricBlockSettings.copyOf(Blocks.FARMLAND).ticksRandomly().strength(0.8f).sounds(BlockSoundGroup.GRAVEL)), ModItemGroup.SCULK_DEPTHS);

    public static final Block SOUL_FIRE = registerBlockWithoutBlockItem("soul_fire", new SoulFireBlock(FabricBlockSettings.copyOf(Blocks.SOUL_FIRE)), ModItemGroup.SCULK_DEPTHS);

    //umbrusk blockset
    public static final Block UMBRUSK = registerBlock("umbrusk",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).hardness(5.0f).resistance(8f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_STAIRS = registerBlock("umbrusk_stairs",
            new StairsBlock(UMBRUSK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.STONE).strength(5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_SLAB = registerBlock("umbrusk_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_WALL = registerBlock("umbrusk_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);


    //umbrusk brick blockset
    public static final Block UMBRUSK_BRICKS = registerBlock("umbrusk_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_BRICK_STAIRS = registerBlock("umbrusk_brick_stairs",
            new StairsBlock(UMBRUSK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.STONE).strength(5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_BRICK_SLAB = registerBlock("umbrusk_brick_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_BRICK_WALL = registerBlock("umbrusk_brick_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);


    //valtrox blockset
    public static final Block VALTROX_LOG = registerBlock("valtrox_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_PLANKS = registerBlock("valtrox_planks",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_WOOD = registerBlock("valtrox_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_SLAB = registerBlock("valtrox_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_STAIRS = registerBlock("valtrox_stairs",
            new StairsBlock(VALTROX_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OAK_STAIRS).strength(5.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_FENCE = registerBlock("valtrox_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    //dried valtrox blockset
    public static final Block DRIED_VALTROX_LOG = registerBlock("dried_valtrox_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(2.5f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_PLANKS = registerBlock("dried_valtrox_planks",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(2.5f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_WOOD = registerBlock("dried_valtrox_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(2.5f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_SLAB = registerBlock("dried_valtrox_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(2.5f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_STAIRS = registerBlock("dried_valtrox_stairs",
            new StairsBlock(DRIED_VALTROX_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.STONE).strength(2.5f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_FENCE = registerBlock("dried_valtrox_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(2.5f)), ModItemGroup.SCULK_DEPTHS);


    //petrified valtrox blockset
    public static final Block PETRIFIED_VALTROX_LOG = registerBlock("petrified_valtrox_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_SLATES = registerBlock("petrified_valtrox_slates",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_WOOD = registerBlock("petrified_valtrox_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_SLAB = registerBlock("petrified_valtrox_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_STAIRS = registerBlock("petrified_valtrox_stairs",
            new StairsBlock(PETRIFIED_VALTROX_SLATES.getDefaultState(), FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_WALL = registerBlock("petrified_valtrox_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(3.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    //zygrin set
    public static final Block ZYGRIN = registerBlock("zygrin",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_STAIRS = registerBlock("zygrin_stairs",
            new StairsBlock(ZYGRIN.getDefaultState(), FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_SLAB = registerBlock("zygrin_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_WALL = registerBlock("zygrin_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_PILLAR = registerBlock("zygrin_pillar",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block CHISELED_ZYGRIN = registerBlock("chiseled_zygrin",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block POLISHED_ZYGRIN = registerBlock("polished_zygrin",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block POLISHED_ZYGRIN_STAIRS = registerBlock("polished_zygrin_stairs",
            new StairsBlock(POLISHED_ZYGRIN.getDefaultState(), FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block POLISHED_ZYGRIN_SLAB = registerBlock("polished_zygrin_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block POLISHED_ZYGRIN_WALL = registerBlock("polished_zygrin_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_BRICKS = registerBlock("zygrin_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_BRICK_STAIRS = registerBlock("zygrin_brick_stairs",
            new StairsBlock(ZYGRIN_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_BRICK_SLAB = registerBlock("zygrin_brick_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_BRICK_WALL = registerBlock("zygrin_brick_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_LIGHT = registerBlock("zygrin_light",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f).requiresTool().luminance(blockState -> 15)), ModItemGroup.SCULK_DEPTHS);

    public static final Block ZYGRIN_FLOWBLOCK = registerBlock("zygrin_flowblock",
            new FlowBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(4.0f)), ModItemGroup.SCULK_DEPTHS);

    //leaves
    public static final Block VALTROX_LEAVES = registerBlock("valtrox_leaves",
            new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)), ModItemGroup.SCULK_DEPTHS);

    //saplings
    public static final Block VALTROX_SAPLING = registerBlock("valtrox_sapling",
            new SaplingBlock(new ValtroxSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)), ModItemGroup.SCULK_DEPTHS);

    //vegetation
    public static final Block CEPHLERA = registerBlockWithoutBlockItem("cephlera",
            new CephleraBlock(FabricBlockSettings.of(Material.PLANT).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.WEEPING_VINES)), ModItemGroup.SCULK_DEPTHS);
    public static final Block CEPHLERA_LIGHT = registerBlock("cephlera_light",
            new CephleraLightBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().emissiveLighting(AbstractBlock.AbstractBlockState::isFullCube).luminance(15).sounds(BlockSoundGroup.WEEPING_VINES)), ModItemGroup.SCULK_DEPTHS);

    //ores
    public static final Block QUAZARITH_ORE = registerBlock("quazarith_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE).hardness(6.0f).resistance(10f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    //fluids

    public static final Block KRYSLUM = registerBlockWithoutBlockItem("kryslum", new FluidBlock(ModFluids.KRYSLUM_STILL, FabricBlockSettings.of(Material.WATER).noCollision().nonOpaque().dropsNothing()), ModItemGroup.SCULK_DEPTHS);


    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(SculkDepths.MOD_ID, name), block);
    }


    private static Block registerBlockWithoutBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registries.BLOCK, new Identifier(SculkDepths.MOD_ID, name), block);
    }


    private static Item registerBlockItem(String name, Block block, ItemGroup group) {

        Item item = Registry.register(Registries.ITEM, new Identifier(SculkDepths.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return item;
    }

    public static void registerModBlocks() {
        SculkDepths.LOGGER.info("Registering ModBlocks for " + SculkDepths.MOD_ID);
    }
}