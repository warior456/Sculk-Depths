package net.ugi.sculk_depths.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.fluid.ModFluids;
import net.ugi.sculk_depths.item.ModItemGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {
    public static final Block CITRINE_BLOCK = registerBlock("citrine_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);

    public static final Block CITRINE_ORE = registerBlock("citrine_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool(),
                    UniformIntProvider.create(2, 6)), ModItemGroup.SCULK_DEPTHS);
    public static final Block DEEPSLATE_CITRINE_ORE = registerBlock("deepslate_citrine_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool(),
                    UniformIntProvider.create(2, 6)), ModItemGroup.SCULK_DEPTHS);
    public static final Block UMBRUSK = registerBlock("umbrusk",
            new Block(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool()), ModItemGroup.SCULK_DEPTHS);
    public static final Block SCULK_FLUID = registerFluids("sculk_fluid",
            new FluidBlock(ModFluids.SCULK_FLUID, AbstractBlock.Settings.of(Material.LAVA).strength(100.0F).dropsNothing()), ModItemGroup.SCULK_DEPTHS);


    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(SculkDepths.MOD_ID, name), block);
    }
    private static Block registerFluids(String name, Block block, ItemGroup group) {
        return (Block)Registry.register(Registries.BLOCK, new Identifier(SculkDepths.MOD_ID, name), block);
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