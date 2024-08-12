package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.CopyNameLootFunction;
import net.minecraft.loot.function.LimitCountLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.LayeredAuricSporeBlock;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {


    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
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
        addDrop(ModBlocks.ZYGRIN_FURNACE, this::nameableContainerDrops);

        addDrop(ModBlocks.VALTROX_LOG, ModBlocks.VALTROX_LOG.asItem());
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
        addDrop(ModBlocks.VALTROX_BUTTON, drops(ModBlocks.VALTROX_BUTTON.asItem()));

        addDrop(ModBlocks.AMALGAMITE, ModBlocks.AMALGAMITE.asItem());

        addDrop(ModBlocks.PENEBRIUM_SHROOM);
        addDropWithSilkTouch(ModBlocks.PENEBRIUM_SHROOM_STEM);
        addDropWithSilkTouch(ModBlocks.PENEBRIUM_SHROOM_BLOCK);
        addDrop(ModBlocks.PENEBRIUM_SPORE_BLOCK, mushroomBlockDrops(ModBlocks.PENEBRIUM_SPORE_BLOCK, ModBlocks.PENEBRIUM_SHROOM));

        addDrop(ModBlocks.AURIC_SPORE_SPROUTS);
        addDropWithSilkTouch(ModBlocks.AURIC_SHROOM_STEM);
        addDropWithSilkTouch(ModBlocks.AURIC_SHROOM_BLOCK);
        addDropWithSilkTouch(ModBlocks.AURIC_SPORE_BLOCK);
        addDrop(ModBlocks.AURIC_SHROOM_BLOCK, mushroomBlockDrops(ModBlocks.AURIC_SHROOM_BLOCK, ModBlocks.AURIC_SPORE_SPROUTS));

/*        addDrop(ModBlocks.AURIC_SPORE_LAYER, (block) -> {//TODO
            return LootTable.builder().pool(LootPool.builder().conditionally(EntityPropertiesLootCondition.create(LootContext.EntityTarget.THIS)).with(AlternativeEntry.builder(LayeredAuricSporeBlock.LAYERS.getValues() , (integer) -> {
                return ItemEntry.builder(ModBlocks.AURIC_SPORE_LAYER).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create((float) integer ))).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(LayeredAuricSporeBlock.LAYERS, integer )));
            }).conditionally(WITH_SILK_TOUCH)));
        });*/
        this.addDrop(
                ModBlocks.AURIC_SPORE_LAYER,
                block -> LootTable.builder()
                        .pool(
                                LootPool.builder()
                                        .conditionally(EntityPropertiesLootCondition.create(LootContext.EntityTarget.THIS))
                                        .with(
                                                AlternativeEntry.builder(
                                                        AlternativeEntry.builder(
                                                                        LayeredAuricSporeBlock.LAYERS.getValues(),
                                                                        integer -> ItemEntry.builder(ModBlocks.AURIC_SPORE_SPROUTS)
                                                                                .conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(LayeredAuricSporeBlock.LAYERS, integer)))
                                                                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, (float)integer.intValue())))
                                                                )
                                                                .conditionally(this.createWithoutSilkTouchCondition()),
                                                        AlternativeEntry.builder(
                                                                SnowBlock.LAYERS.getValues(),
                                                                integer -> ItemEntry.builder(ModBlocks.AURIC_SPORE_LAYER)
                                                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create((float)integer.intValue())))
                                                                        .conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(LayeredAuricSporeBlock.LAYERS, integer)))
                                                        )
                                                )
                                        )
                        )
        );
    }

    public LootTable.Builder mushroomBlockDrops(Block withSilkTouch, ItemConvertible withoutSilkTouch) {
        return this.dropsWithSilkTouch(withSilkTouch, this.applyExplosionDecay(withSilkTouch,
                ItemEntry.builder(withoutSilkTouch)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(-6.0F, 2.0F)))//TODO the chance?
                        .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.createMin(0)))));
    }

    public LootTable.Builder nameableContainerDrops(Block drop) {
        return LootTable.builder().pool(this.addSurvivesExplosionCondition(drop, LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f)).with(ItemEntry.builder(drop).apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY)))));
    }

/*    public LootTable.Builder copperLikeOreDrops(Block drop, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop, (LootPoolEntry.Builder) this.applyExplosionDecay(drop,
                ((LeafEntry.Builder)
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction
                                        .builder(UniformLootNumberProvider
                                                .create(2.0f, 5.0f))))
                        .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }*/
}