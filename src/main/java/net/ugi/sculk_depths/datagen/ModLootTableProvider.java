package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderLookup;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.LayeredAuricSporeBlock;
import net.ugi.sculk_depths.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {


    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(dataOutput, registries);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.QUAZARITH_BLOCK, drops(ModBlocks.QUAZARITH_BLOCK.asItem()));
        addDrop(ModBlocks.CRUX_BLOCK, drops(ModBlocks.CRUX_BLOCK.asItem()));

        addDrop(ModBlocks.QUAZARITH_ORE, oreDrops(ModBlocks.QUAZARITH_ORE,ModItems.QUAZARITH_PIECES));
        addDrop(ModBlocks.CRUX_ORE, oreDrops(ModBlocks.CRUX_ORE,ModItems.CRUX));


        addDrop(ModBlocks.LARGUTH);
        addDrop(ModBlocks.ZYGRIN_FURNACE, this::createNameableBlockEntityTable);
        addDrop(ModBlocks.CEPHLERA,ModBlocks.CEPHLERA_LIGHT.asItem());
        addDrop(ModBlocks.CRUMBLING_DIRT);
        addDrop(ModBlocks.KRYSLUM_ENRICHED_SOIL);
        addDrop(ModBlocks.VALTROX_SAPLING);
        addDrop(ModBlocks.AURIC_VINES,ModBlocks.AURIC_VINES_END.asItem());
        addDrop(ModBlocks.VALTROX_LEAVES, createLeavesDrops(ModBlocks.VALTROX_LEAVES,ModBlocks.VALTROX_SAPLING, 0.1F));
        addDrop(ModBlocks.DEAD_GRASS, // drops with silktouch or shears ( and no loottable if not)
                LootTable.builder().withPool(LootPool.builder().setRolls(ConstantValue.create(1.0F)).with(((LootPoolSingletonContainer.Builder)LootItem.builder(ModBlocks.DEAD_GRASS).when(this.hasShearsOrSilkTouch())))));


        addDrop(ModBlocks.QUAZARITH_OSCILLATOR);

        dropPottedContents(ModBlocks.POTTED_AURIC_SPORE_SPROUTS);
        dropPottedContents(ModBlocks.POTTED_PENEBRIUM_SHROOM);
        dropPottedContents(ModBlocks.POTTED_VALTROX_SAPLING);


        addDrop(ModBlocks.VALTROX_LOG);
        addDrop(ModBlocks.VALTROX_WOOD);
        addDrop(ModBlocks.STRIPPED_VALTROX_LOG);
        addDrop(ModBlocks.STRIPPED_VALTROX_WOOD);
        addDrop(ModBlocks.VALTROX_PLANKS);
        addDrop(ModBlocks.VALTROX_STAIRS);
        addDrop(ModBlocks.VALTROX_SLAB, createSlabItemTable(ModBlocks.VALTROX_SLAB));
        addDrop(ModBlocks.VALTROX_FENCE);
        addDrop(ModBlocks.VALTROX_FENCE_GATE);
        addDrop(ModBlocks.VALTROX_DOOR, createDoorTable(ModBlocks.VALTROX_DOOR));
        addDrop(ModBlocks.VALTROX_TRAPDOOR);
        addDrop(ModBlocks.VALTROX_PRESSURE_PLATE);
        addDrop(ModBlocks.VALTROX_BUTTON);
        addDrop(ModBlocks.VALTROX_SIGN);
        addDrop(ModBlocks.VALTROX_HANGING_SIGN);

        addDrop(ModBlocks.COATED_VALTROX_LOG);
        addDrop(ModBlocks.COATED_VALTROX_WOOD);
        addDrop(ModBlocks.COATED_STRIPPED_VALTROX_LOG);
        addDrop(ModBlocks.COATED_STRIPPED_VALTROX_WOOD);
        addDrop(ModBlocks.COATED_VALTROX_PLANKS);
        addDrop(ModBlocks.COATED_VALTROX_STAIRS);
        addDrop(ModBlocks.COATED_VALTROX_SLAB, createSlabItemTable(ModBlocks.COATED_VALTROX_SLAB));
        addDrop(ModBlocks.COATED_VALTROX_FENCE);
        addDrop(ModBlocks.COATED_VALTROX_FENCE_GATE);
        addDrop(ModBlocks.COATED_VALTROX_DOOR, createDoorTable(ModBlocks.COATED_VALTROX_DOOR));
        addDrop(ModBlocks.COATED_VALTROX_TRAPDOOR);
        addDrop(ModBlocks.COATED_VALTROX_PRESSURE_PLATE);
        addDrop(ModBlocks.COATED_VALTROX_BUTTON);
        addDrop(ModBlocks.COATED_VALTROX_SIGN);
        addDrop(ModBlocks.COATED_VALTROX_HANGING_SIGN);

        addDrop(ModBlocks.DRIED_VALTROX_LOG);
        addDrop(ModBlocks.DRIED_VALTROX_WOOD);
        addDrop(ModBlocks.STRIPPED_DRIED_VALTROX_LOG);
        addDrop(ModBlocks.STRIPPED_DRIED_VALTROX_WOOD);
        addDrop(ModBlocks.DRIED_VALTROX_PLANKS);
        addDrop(ModBlocks.DRIED_VALTROX_STAIRS);
        addDrop(ModBlocks.DRIED_VALTROX_SLAB, createSlabItemTable(ModBlocks.DRIED_VALTROX_SLAB));
        addDrop(ModBlocks.DRIED_VALTROX_FENCE);
        addDrop(ModBlocks.DRIED_VALTROX_FENCE_GATE);
        addDrop(ModBlocks.DRIED_VALTROX_DOOR, createDoorTable(ModBlocks.DRIED_VALTROX_DOOR));
        addDrop(ModBlocks.DRIED_VALTROX_TRAPDOOR);
        addDrop(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE);
        addDrop(ModBlocks.DRIED_VALTROX_BUTTON);
        addDrop(ModBlocks.DRIED_VALTROX_SIGN);
        addDrop(ModBlocks.DRIED_VALTROX_HANGING_SIGN);


        addDrop(ModBlocks.PETRIFIED_VALTROX_LOG);
        addDrop(ModBlocks.PETRIFIED_VALTROX_WOOD);
        addDrop(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG);
        addDrop(ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD);
        addDrop(ModBlocks.PETRIFIED_VALTROX_SLATES);
        addDrop(ModBlocks.PETRIFIED_VALTROX_STAIRS);
        addDrop(ModBlocks.PETRIFIED_VALTROX_SLAB, createSlabItemTable(ModBlocks.PETRIFIED_VALTROX_SLAB));
        addDrop(ModBlocks.PETRIFIED_VALTROX_WALL);
        addDrop(ModBlocks.PETRIFIED_VALTROX_WALL_GATE);
        addDrop(ModBlocks.PETRIFIED_VALTROX_DOOR, createDoorTable(ModBlocks.PETRIFIED_VALTROX_DOOR));
        addDrop(ModBlocks.PETRIFIED_VALTROX_TRAPDOOR);
        addDrop(ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE);
        addDrop(ModBlocks.PETRIFIED_VALTROX_BUTTON);
        addDrop(ModBlocks.PETRIFIED_VALTROX_SIGN);
        addDrop(ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN);

        addDrop(ModBlocks.AMALGAMITE);
        addDrop(ModBlocks.POLISHED_AMALGAMITE);
        addDrop(ModBlocks.POLISHED_AMALGAMITE_STAIRS);
        addDrop(ModBlocks.POLISHED_AMALGAMITE_SLAB, createSlabItemTable(ModBlocks.POLISHED_AMALGAMITE_SLAB));
        addDrop(ModBlocks.POLISHED_AMALGAMITE_WALL);
        addDrop(ModBlocks.AMALGAMITE_BRICKS);
        addDrop(ModBlocks.CRACKED_AMALGAMITE_BRICKS);
        addDrop(ModBlocks.AMALGAMITE_BRICK_STAIRS);
        addDrop(ModBlocks.AMALGAMITE_BRICK_SLAB, createSlabItemTable(ModBlocks.AMALGAMITE_BRICK_SLAB));
        addDrop(ModBlocks.AMALGAMITE_BRICK_WALL);
        addDrop(ModBlocks.CHISELED_AMALGAMITE);

        addDrop(ModBlocks.ZYGRIN);
        addDrop(ModBlocks.ZYGRIN_STAIRS);
        addDrop(ModBlocks.ZYGRIN_SLAB, createSlabItemTable(ModBlocks.ZYGRIN_SLAB));
        addDrop(ModBlocks.ZYGRIN_WALL);
        addDrop(ModBlocks.POLISHED_ZYGRIN);
        addDrop(ModBlocks.POLISHED_ZYGRIN_STAIRS);
        addDrop(ModBlocks.POLISHED_ZYGRIN_SLAB, createSlabItemTable(ModBlocks.POLISHED_ZYGRIN_SLAB));
        addDrop(ModBlocks.POLISHED_ZYGRIN_WALL);
        addDrop(ModBlocks.ZYGRIN_BRICKS);
        addDrop(ModBlocks.ZYGRIN_BRICK_STAIRS);
        addDrop(ModBlocks.ZYGRIN_BRICK_SLAB, createSlabItemTable(ModBlocks.ZYGRIN_BRICK_SLAB));
        addDrop(ModBlocks.ZYGRIN_BRICK_WALL);
        addDrop(ModBlocks.ZYGRIN_PILLAR);
        addDrop(ModBlocks.CHISELED_ZYGRIN);
        addDrop(ModBlocks.ZYGRIN_BRICKS);
        addDrop(ModBlocks.ZYGRIN_LIGHT);
        addDrop(ModBlocks.ZYGRIN_FLOWBLOCK);
        addDrop(ModBlocks.ZYGRIN_FURNACE);

        addDrop(ModBlocks.UMBRUSK);
        addDrop(ModBlocks.UMBRUSK_STAIRS);
        addDrop(ModBlocks.UMBRUSK_SLAB, createSlabItemTable(ModBlocks.UMBRUSK_SLAB));
        addDrop(ModBlocks.UMBRUSK_WALL);
        addDrop(ModBlocks.UMBRUSK_BRICKS);
        addDrop(ModBlocks.UMBRUSK_BRICK_STAIRS);
        addDrop(ModBlocks.UMBRUSK_BRICK_SLAB, createSlabItemTable(ModBlocks.UMBRUSK_BRICK_SLAB));
        addDrop(ModBlocks.UMBRUSK_BRICK_WALL);

        addDrop(ModBlocks.FLUMROCK);
        addDrop(ModBlocks.FLUMROCK_TILES);
        addDrop(ModBlocks.CRUMBLING_FLUMROCK_TILES);
        addDrop(ModBlocks.FLUMROCK_TILE_STAIRS);
        addDrop(ModBlocks.FLUMROCK_TILE_SLAB, createSlabItemTable(ModBlocks.FLUMROCK_TILE_SLAB));
        addDrop(ModBlocks.FLUMROCK_TILE_WALL);
        addDrop(ModBlocks.FLUMROCK_CAULDRON);


        addDrop(ModBlocks.PENEBRIUM_SHROOM);
        addDropWithSilkTouch(ModBlocks.PENEBRIUM_SHROOM_STEM);
        addDropWithSilkTouch(ModBlocks.PENEBRIUM_SHROOM_BLOCK);
        addDrop(ModBlocks.PENEBRIUM_SPORE_BLOCK, createMushroomBlockDrop(ModBlocks.PENEBRIUM_SPORE_BLOCK, ModBlocks.PENEBRIUM_SHROOM));

        addDrop(ModBlocks.AURIC_SPORE_SPROUTS);
        addDropWithSilkTouch(ModBlocks.AURIC_SHROOM_STEM);
        addDrop(ModBlocks.AURIC_SPORE_BLOCK, silkTouchOrItemDrops(ModBlocks.AURIC_SPORE_BLOCK,ModBlocks.AURIC_SPORE_SPROUTS.asItem()));
        addDrop(ModBlocks.AURIC_SHROOM_BLOCK, createMushroomBlockDrop(ModBlocks.AURIC_SHROOM_BLOCK, ModBlocks.AURIC_SPORE_SPROUTS));

/*        addDrop(ModBlocks.AURIC_SPORE_LAYER, (block) -> {//TODO
            return LootTable.builder().pool(LootPool.builder().conditionally(EntityPropertiesLootCondition.create(LootContext.EntityTarget.THIS)).with(AlternativeEntry.builder(LayeredAuricSporeBlock.LAYERS.getValues() , (integer) -> {
                return ItemEntry.builder(ModBlocks.AURIC_SPORE_LAYER).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create((float) integer ))).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(LayeredAuricSporeBlock.LAYERS, integer )));
            }).conditionally(WITH_SILK_TOUCH)));
        });*/
        this.addDrop(
                ModBlocks.AURIC_SPORE_LAYER,
                block -> LootTable.builder()
                        .withPool(
                                LootPool.builder()
                                        .when(LootItemEntityPropertyCondition.create(LootContext.EntityTarget.THIS))
                                        .with(
                                                AlternativesEntry.builder(
                                                        AlternativesEntry.builder(
                                                                        LayeredAuricSporeBlock.ALL_MODELS.getValues(),
                                                                        integer -> LootItem.builder(ModBlocks.AURIC_SPORE_SPROUTS)
                                                                                .when(LootItemBlockStatePropertyCondition.builder(block).properties(StatePropertiesPredicate.Builder.create().hasProperty(LayeredAuricSporeBlock.ALL_MODELS, integer)))
                                                                                .apply(SetItemCountFunction.builder(UniformGenerator.create(0, (float)integer.getAsInt())))
                                                                )
                                                                .when(this.doesNotHaveSilkTouch()),
                                                        AlternativesEntry.builder(
                                                                SnowLayerBlock.ALL_MODELS.getValues(),
                                                                integer -> LootItem.builder(ModBlocks.AURIC_SPORE_LAYER)
                                                                        .apply(SetItemCountFunction.builder(ConstantValue.create((float)integer.getAsInt())))
                                                                        .when(LootItemBlockStatePropertyCondition.builder(block).properties(StatePropertiesPredicate.Builder.create().hasProperty(LayeredAuricSporeBlock.ALL_MODELS, integer)))
                                                        )
                                                )
                                        )
                        )
        );
    }

    public LootTable.Builder createMushroomBlockDrop(Block withSilkTouch, ItemLike withoutSilkTouch) {
        return this.dropsWithSilkTouch(withSilkTouch, this.applyExplosionDecay(withSilkTouch,
                LootItem.builder(withoutSilkTouch)
                        .apply(SetItemCountFunction.builder(UniformGenerator.create(-6.0F, 2.0F)))//TODO the chance?
                        .apply(LimitCount.builder(IntRange.lowerBound(0)))));
    }


    public LootTable.Builder createNameableBlockEntityTable(Block drop) {
        return LootTable.builder().withPool(this.applyExplosionCondition(drop, LootPool.builder().setRolls(ConstantValue.create(1.0f)).with(LootItem.builder(drop).apply(CopyNameFunction.builder(CopyNameFunction.NameSource.BLOCK_ENTITY)))));
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

    public LootTable.Builder silkTouchOrItemDrops(Block withSilkTouch, Item withoutSilkTouch) {
        HolderLookup.RegistryLookup<Enchantment> impl = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.dropsWithSilkTouch(withSilkTouch, (LootPoolEntryContainer.Builder)this.applyExplosionDecay(withSilkTouch, LootItem.builder(withoutSilkTouch)));
    }

}