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
        addDrop(ModBlocks.STRIPPED_VALTROX_LOG, ModBlocks.STRIPPED_VALTROX_LOG.asItem());
        addDrop(ModBlocks.STRIPPED_VALTROX_WOOD, ModBlocks.STRIPPED_VALTROX_WOOD.asItem());
        addDrop(ModBlocks.VALTROX_PRESSURE_PLATE, drops(ModBlocks.VALTROX_PRESSURE_PLATE.asItem()));

        addDrop(ModBlocks.COATED_STRIPPED_VALTROX_LOG, ModBlocks.COATED_STRIPPED_VALTROX_LOG.asItem());
        addDrop(ModBlocks.COATED_STRIPPED_VALTROX_WOOD, ModBlocks.COATED_STRIPPED_VALTROX_WOOD.asItem());
        addDrop(ModBlocks.COATED_VALTROX_PRESSURE_PLATE, drops(ModBlocks.COATED_VALTROX_PRESSURE_PLATE.asItem()));

        addDrop(ModBlocks.STRIPPED_DRIED_VALTROX_LOG, ModBlocks.STRIPPED_DRIED_VALTROX_LOG.asItem());
        addDrop(ModBlocks.STRIPPED_DRIED_VALTROX_WOOD, ModBlocks.STRIPPED_DRIED_VALTROX_WOOD.asItem());
        addDrop(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE, drops(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE.asItem()));

        addDrop(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG, ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG.asItem());
        addDrop(ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD, ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD.asItem());
        addDrop(ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE, drops(ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE.asItem()));

        addDrop(ModBlocks.QUAZARITH_BLOCK, drops(ModBlocks.QUAZARITH_BLOCK.asItem()));

        addDrop(ModBlocks.LARGUTH, drops(ModBlocks.LARGUTH.asItem()));

        addDropWithSilkTouch(ModBlocks.ZYGRIN_LIGHT);


/*        addDrop(ModBlocks.VALTROX_LOG, ModBlocks.VALTROX_LOG.asItem());
        addDrop(ModBlocks.VALTROX_WOOD, ModBlocks.VALTROX_WOOD.asItem());
        addDrop(ModBlocks.STRIPPED_VALTROX_LOG, ModBlocks.STRIPPED_VALTROX_LOG.asItem());
        addDrop(ModBlocks.STRIPPED_VALTROX_WOOD, ModBlocks.STRIPPED_VALTROX_WOOD.asItem());
        addDrop(ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_PLANKS.asItem());
        addDrop(ModBlocks.VALTROX_STAIRS, ModBlocks.VALTROX_STAIRS.asItem());
        addDrop(ModBlocks.VALTROX_SLAB, ModBlocks.VALTROX_SLAB.asItem());
        addDrop(ModBlocks.VALTROX_FENCE, ModBlocks.VALTROX_FENCE.asItem());
        addDrop(ModBlocks.VALTROX_FENCE_GATE, ModBlocks.VALTROX_FENCE_GATE.asItem());
        addDrop(ModBlocks.VALTROX_DOOR, ModBlocks.VALTROX_DOOR.asItem());
        addDrop(ModBlocks.VALTROX_TRAPDOOR, ModBlocks.VALTROX_TRAPDOOR.asItem());
        addDrop(ModBlocks.VALTROX_PRESSURE_PLATE, drops(ModBlocks.VALTROX_PRESSURE_PLATE.asItem()));
        addDrop(ModBlocks.VALTROX_BUTTON, drops(ModBlocks.VALTROX_BUTTON.asItem()));*/


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