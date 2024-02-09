package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.ugi.sculk_depths.block.ModBlocks;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.VALTROX_PRESSURE_PLATE, drops(ModBlocks.VALTROX_PRESSURE_PLATE.asItem()));
        addDrop(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE, drops(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE.asItem()));
        addDrop(ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE, drops(ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE.asItem()));
        addDrop(ModBlocks.QUAZARITH_BLOCK, drops(ModBlocks.QUAZARITH_BLOCK.asItem()));
        addDrop(ModBlocks.STRIPPED_VALTROX_WOOD, ModBlocks.STRIPPED_VALTROX_WOOD.asItem());
        addDrop(ModBlocks.STRIPPED_DRIED_VALTROX_WOOD, ModBlocks.STRIPPED_DRIED_VALTROX_WOOD.asItem());
        addDrop(ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD, ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD.asItem());

        addDrop(ModBlocks.AMALGAMITE, ModBlocks.AMALGAMITE.asItem());

        addDrop(ModBlocks.PENEBRIUM_SHROOM);
        addDropWithSilkTouch(ModBlocks.PENEBRIUM_SHROOM_STEM);
        addDropWithSilkTouch(ModBlocks.PENEBRIUM_SHROOM_BLOCK);
    }

    public LootTable.Builder copperLikeOreDrops(Block drop, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop, (LootPoolEntry.Builder) this.applyExplosionDecay(drop,
                ((LeafEntry.Builder)
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction
                                        .builder(UniformLootNumberProvider
                                                .create(2.0f, 5.0f))))
                        .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }
}