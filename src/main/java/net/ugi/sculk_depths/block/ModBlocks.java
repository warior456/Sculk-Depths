package net.ugi.sculk_depths.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.event.GameEvent;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.fluid.ModFluids;
import net.ugi.sculk_depths.item.ModArmorMaterials;
import net.ugi.sculk_depths.item.ModItemGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {

    public static final Block FLUMROCK = registerBlock("flumrock",
            new Block(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block CRUMBLING_DIRT = registerBlock("crumbling_dirt",
            new Block(FabricBlockSettings.of(Material.SOLID_ORGANIC).strength(1.0f).requiresTool().sounds(BlockSoundGroup.ROOTED_DIRT)), ModItemGroup.SCULK_DEPTHS);


    //umbrusk blockset
    public static final Block UMBRUSK = registerBlock("umbrusk",
            new Block(FabricBlockSettings.of(Material.STONE).hardness(5.0f).resistance(8f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_STAIRS = registerBlock("umbrusk_stairs",
            new StairsBlock(UMBRUSK.getDefaultState(), FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_SLAB = registerBlock("umbrusk_slab",
            new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_WALL = registerBlock("umbrusk_wall",
            new WallBlock(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);


    //umbrusk brick blockset
    public static final Block UMBRUSK_BRICKS = registerBlock("umbrusk_bricks",
            new Block(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_BRICK_STAIRS = registerBlock("umbrusk_brick_stairs",
            new StairsBlock(UMBRUSK.getDefaultState(), FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_BRICK_SLAB = registerBlock("umbrusk_brick_slab",
            new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block UMBRUSK_BRICK_WALL = registerBlock("umbrusk_brick_wall",
            new WallBlock(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);


    //valtrox blockset
    public static final Block VALTROX_LOG = registerBlock("valtrox_log",
            new PillarBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_PLANKS = registerBlock("valtrox_planks",
            new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_WOOD = registerBlock("valtrox_wood",
            new PillarBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_SLAB = registerBlock("valtrox_slab",
            new SlabBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_STAIRS = registerBlock("valtrox_stairs",
            new StairsBlock(VALTROX_PLANKS.getDefaultState(), FabricBlockSettings.of(Material.WOOD).strength(5.0f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block VALTROX_FENCE = registerBlock("valtrox_fence",
            new FenceBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0f)), ModItemGroup.SCULK_DEPTHS);

    //dried valtrox blockset
    public static final Block DRIED_VALTROX_LOG = registerBlock("dried_valtrox_log",
            new PillarBlock(FabricBlockSettings.of(Material.WOOD).strength(2.5f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_PLANKS = registerBlock("dried_valtrox_planks",
            new Block(FabricBlockSettings.of(Material.WOOD).strength(2.5f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_WOOD = registerBlock("dried_valtrox_wood",
            new PillarBlock(FabricBlockSettings.of(Material.WOOD).strength(2.5f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_SLAB = registerBlock("dried_valtrox_slab",
            new SlabBlock(FabricBlockSettings.of(Material.WOOD).strength(2.5f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_STAIRS = registerBlock("dried_valtrox_stairs",
            new StairsBlock(DRIED_VALTROX_PLANKS.getDefaultState(), FabricBlockSettings.of(Material.WOOD).strength(2.5f)), ModItemGroup.SCULK_DEPTHS);

    public static final Block DRIED_VALTROX_FENCE = registerBlock("dried_valtrox_fence",
            new FenceBlock(FabricBlockSettings.of(Material.WOOD).strength(2.5f)), ModItemGroup.SCULK_DEPTHS);


    //petrified valtrox blockset
    public static final Block PETRIFIED_VALTROX_LOG = registerBlock("petrified_valtrox_log",
            new PillarBlock(FabricBlockSettings.of(Material.STONE).strength(3.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_SLATES = registerBlock("petrified_valtrox_slates",
            new Block(FabricBlockSettings.of(Material.STONE).strength(3.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_WOOD = registerBlock("petrified_valtrox_wood",
            new PillarBlock(FabricBlockSettings.of(Material.STONE).strength(3.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_SLAB = registerBlock("petrified_valtrox_slab",
            new SlabBlock(FabricBlockSettings.of(Material.STONE).strength(3.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_STAIRS = registerBlock("petrified_valtrox_stairs",
            new StairsBlock(PETRIFIED_VALTROX_SLATES.getDefaultState(), FabricBlockSettings.of(Material.STONE).strength(3.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block PETRIFIED_VALTROX_FENCE = registerBlock("petrified_valtrox_fence",
            new FenceBlock(FabricBlockSettings.of(Material.STONE).strength(3.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    //ores
    public static final Block QUAZARITH_ORE = registerBlock("quazarith_ore",
            new Block(FabricBlockSettings.of(Material.STONE).hardness(6.0f).resistance(10f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    //fluids
    public static final Block KRYSLUM = registerBlockWithoutBlockItem("kryslum", new ModFluidBlock(ModFluids.KRYSLUM_STILL, FabricBlockSettings.of(Material.WATER).noCollision().nonOpaque().dropsNothing()), ModItemGroup.SCULK_DEPTHS);

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