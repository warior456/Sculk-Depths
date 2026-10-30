package net.ugi.sculk_depths.datagen;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.LayeredAuricSporeBlock;
import net.ugi.sculk_depths.item.ModItems;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(PackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(dataOutput, Collections.emptySet(), List.of(
                new SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ), registries);
    }

    public static class ModBlockLootTableProvider extends BlockLootSubProvider {

        public ModBlockLootTableProvider(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        public void generate() {
            dropSelf(ModBlocks.QUAZARITH_BLOCK);
            dropSelf(ModBlocks.CRUX_BLOCK);

            add(ModBlocks.QUAZARITH_ORE, block -> createOreDrop(block, ModItems.QUAZARITH_PIECES));
            add(ModBlocks.CRUX_ORE, block -> createOreDrop(block, ModItems.CRUX));

            dropSelf(ModBlocks.LARGUTH);
            add(ModBlocks.ZYGRIN_FURNACE, this::createNameableBlockEntityTable);
            dropOther(ModBlocks.CEPHLERA, ModBlocks.CEPHLERA_LIGHT.asItem());
            dropSelf(ModBlocks.CRUMBLING_DIRT);
            dropSelf(ModBlocks.KRYSLUM_ENRICHED_SOIL);
            dropSelf(ModBlocks.VALTROX_SAPLING);
            dropOther(ModBlocks.AURIC_VINES, ModBlocks.AURIC_VINES_END.asItem());
            add(ModBlocks.VALTROX_LEAVES, block -> createLeavesDrops(block, ModBlocks.VALTROX_SAPLING, 0.1F));
            add(ModBlocks.DEAD_GRASS, // drops with silktouch or shears ( and no loottable if not)
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ModBlocks.DEAD_GRASS).when(HAS_SHEARS.or(this.hasSilkTouch())))));

            dropSelf(ModBlocks.QUAZARITH_OSCILLATOR);

            dropPottedContents(ModBlocks.POTTED_AURIC_SPORE_SPROUTS);
            dropPottedContents(ModBlocks.POTTED_PENEBRIUM_SHROOM);
            dropPottedContents(ModBlocks.POTTED_VALTROX_SAPLING);

            dropSelf(ModBlocks.VALTROX_LOG);
            dropSelf(ModBlocks.VALTROX_WOOD);
            dropSelf(ModBlocks.STRIPPED_VALTROX_LOG);
            dropSelf(ModBlocks.STRIPPED_VALTROX_WOOD);
            dropSelf(ModBlocks.VALTROX_PLANKS);
            dropSelf(ModBlocks.VALTROX_STAIRS);
            add(ModBlocks.VALTROX_SLAB, this::createSlabItemTable);
            dropSelf(ModBlocks.VALTROX_FENCE);
            dropSelf(ModBlocks.VALTROX_FENCE_GATE);
            add(ModBlocks.VALTROX_DOOR, this::createDoorTable);
            dropSelf(ModBlocks.VALTROX_TRAPDOOR);
            dropSelf(ModBlocks.VALTROX_PRESSURE_PLATE);
            dropSelf(ModBlocks.VALTROX_BUTTON);
            dropSelf(ModBlocks.VALTROX_SIGN);
            dropSelf(ModBlocks.VALTROX_HANGING_SIGN);

            dropSelf(ModBlocks.COATED_VALTROX_LOG);
            dropSelf(ModBlocks.COATED_VALTROX_WOOD);
            dropSelf(ModBlocks.COATED_STRIPPED_VALTROX_LOG);
            dropSelf(ModBlocks.COATED_STRIPPED_VALTROX_WOOD);
            dropSelf(ModBlocks.COATED_VALTROX_PLANKS);
            dropSelf(ModBlocks.COATED_VALTROX_STAIRS);
            add(ModBlocks.COATED_VALTROX_SLAB, this::createSlabItemTable);
            dropSelf(ModBlocks.COATED_VALTROX_FENCE);
            dropSelf(ModBlocks.COATED_VALTROX_FENCE_GATE);
            add(ModBlocks.COATED_VALTROX_DOOR, this::createDoorTable);
            dropSelf(ModBlocks.COATED_VALTROX_TRAPDOOR);
            dropSelf(ModBlocks.COATED_VALTROX_PRESSURE_PLATE);
            dropSelf(ModBlocks.COATED_VALTROX_BUTTON);
            dropSelf(ModBlocks.COATED_VALTROX_SIGN);
            dropSelf(ModBlocks.COATED_VALTROX_HANGING_SIGN);

            dropSelf(ModBlocks.DRIED_VALTROX_LOG);
            dropSelf(ModBlocks.DRIED_VALTROX_WOOD);
            dropSelf(ModBlocks.STRIPPED_DRIED_VALTROX_LOG);
            dropSelf(ModBlocks.STRIPPED_DRIED_VALTROX_WOOD);
            dropSelf(ModBlocks.DRIED_VALTROX_PLANKS);
            dropSelf(ModBlocks.DRIED_VALTROX_STAIRS);
            add(ModBlocks.DRIED_VALTROX_SLAB, this::createSlabItemTable);
            dropSelf(ModBlocks.DRIED_VALTROX_FENCE);
            dropSelf(ModBlocks.DRIED_VALTROX_FENCE_GATE);
            add(ModBlocks.DRIED_VALTROX_DOOR, this::createDoorTable);
            dropSelf(ModBlocks.DRIED_VALTROX_TRAPDOOR);
            dropSelf(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE);
            dropSelf(ModBlocks.DRIED_VALTROX_BUTTON);
            dropSelf(ModBlocks.DRIED_VALTROX_SIGN);
            dropSelf(ModBlocks.DRIED_VALTROX_HANGING_SIGN);

            dropSelf(ModBlocks.PETRIFIED_VALTROX_LOG);
            dropSelf(ModBlocks.PETRIFIED_VALTROX_WOOD);
            dropSelf(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG);
            dropSelf(ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD);
            dropSelf(ModBlocks.PETRIFIED_VALTROX_SLATES);
            dropSelf(ModBlocks.PETRIFIED_VALTROX_STAIRS);
            add(ModBlocks.PETRIFIED_VALTROX_SLAB, this::createSlabItemTable);
            dropSelf(ModBlocks.PETRIFIED_VALTROX_WALL);
            dropSelf(ModBlocks.PETRIFIED_VALTROX_WALL_GATE);
            add(ModBlocks.PETRIFIED_VALTROX_DOOR, this::createDoorTable);
            dropSelf(ModBlocks.PETRIFIED_VALTROX_TRAPDOOR);
            dropSelf(ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE);
            dropSelf(ModBlocks.PETRIFIED_VALTROX_BUTTON);
            dropSelf(ModBlocks.PETRIFIED_VALTROX_SIGN);
            dropSelf(ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN);

            dropSelf(ModBlocks.AMALGAMITE);
            dropSelf(ModBlocks.POLISHED_AMALGAMITE);
            dropSelf(ModBlocks.POLISHED_AMALGAMITE_STAIRS);
            add(ModBlocks.POLISHED_AMALGAMITE_SLAB, this::createSlabItemTable);
            dropSelf(ModBlocks.POLISHED_AMALGAMITE_WALL);
            dropSelf(ModBlocks.AMALGAMITE_BRICKS);
            dropSelf(ModBlocks.CRACKED_AMALGAMITE_BRICKS);
            dropSelf(ModBlocks.AMALGAMITE_BRICK_STAIRS);
            add(ModBlocks.AMALGAMITE_BRICK_SLAB, this::createSlabItemTable);
            dropSelf(ModBlocks.AMALGAMITE_BRICK_WALL);
            dropSelf(ModBlocks.CHISELED_AMALGAMITE);

            dropSelf(ModBlocks.ZYGRIN);
            dropSelf(ModBlocks.ZYGRIN_STAIRS);
            add(ModBlocks.ZYGRIN_SLAB, this::createSlabItemTable);
            dropSelf(ModBlocks.ZYGRIN_WALL);
            dropSelf(ModBlocks.POLISHED_ZYGRIN);
            dropSelf(ModBlocks.POLISHED_ZYGRIN_STAIRS);
            add(ModBlocks.POLISHED_ZYGRIN_SLAB, this::createSlabItemTable);
            dropSelf(ModBlocks.POLISHED_ZYGRIN_WALL);
            dropSelf(ModBlocks.ZYGRIN_BRICKS);
            dropSelf(ModBlocks.ZYGRIN_BRICK_STAIRS);
            add(ModBlocks.ZYGRIN_BRICK_SLAB, this::createSlabItemTable);
            dropSelf(ModBlocks.ZYGRIN_BRICK_WALL);
            dropSelf(ModBlocks.ZYGRIN_PILLAR);
            dropSelf(ModBlocks.CHISELED_ZYGRIN);
            dropSelf(ModBlocks.ZYGRIN_LIGHT);
            dropSelf(ModBlocks.ZYGRIN_FLOWBLOCK);

            dropSelf(ModBlocks.UMBRUSK);
            dropSelf(ModBlocks.UMBRUSK_STAIRS);
            add(ModBlocks.UMBRUSK_SLAB, this::createSlabItemTable);
            dropSelf(ModBlocks.UMBRUSK_WALL);
            dropSelf(ModBlocks.UMBRUSK_BRICKS);
            dropSelf(ModBlocks.UMBRUSK_BRICK_STAIRS);
            add(ModBlocks.UMBRUSK_BRICK_SLAB, this::createSlabItemTable);
            dropSelf(ModBlocks.UMBRUSK_BRICK_WALL);

            dropSelf(ModBlocks.FLUMROCK);
            dropSelf(ModBlocks.FLUMROCK_TILES);
            dropSelf(ModBlocks.CRUMBLING_FLUMROCK_TILES);
            dropSelf(ModBlocks.FLUMROCK_TILE_STAIRS);
            add(ModBlocks.FLUMROCK_TILE_SLAB, this::createSlabItemTable);
            dropSelf(ModBlocks.FLUMROCK_TILE_WALL);
            dropSelf(ModBlocks.FLUMROCK_CAULDRON);

            dropSelf(ModBlocks.PENEBRIUM_SHROOM);
            dropWhenSilkTouch(ModBlocks.PENEBRIUM_SHROOM_STEM);
            dropWhenSilkTouch(ModBlocks.PENEBRIUM_SHROOM_BLOCK);
            add(ModBlocks.PENEBRIUM_SPORE_BLOCK, block -> createMushroomBlockDrop(block, ModBlocks.PENEBRIUM_SHROOM));

            dropSelf(ModBlocks.AURIC_SPORE_SPROUTS);
            dropWhenSilkTouch(ModBlocks.AURIC_SHROOM_STEM);
            add(ModBlocks.AURIC_SPORE_BLOCK, block -> createSingleItemTableWithSilkTouch(block, ModBlocks.AURIC_SPORE_SPROUTS));
            add(ModBlocks.AURIC_SHROOM_BLOCK, block -> createMushroomBlockDrop(block, ModBlocks.AURIC_SPORE_SPROUTS));

            this.add(
                    ModBlocks.AURIC_SPORE_LAYER,
                    block -> LootTable.lootTable()
                            .withPool(
                                    LootPool.lootPool()
                                            .when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS))
                                            .add(
                                                    AlternativesEntry.alternatives(
                                                            AlternativesEntry.alternatives(
                                                                            LayeredAuricSporeBlock.ALL_MODELS.getPossibleValues(),
                                                                            integer -> LootItem.lootTableItem(ModBlocks.AURIC_SPORE_SPROUTS)
                                                                                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LayeredAuricSporeBlock.ALL_MODELS, integer)))
                                                                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, (float) integer.intValue())))
                                                                    )
                                                                    .when(this.doesNotHaveSilkTouch()),
                                                            AlternativesEntry.alternatives(
                                                                    SnowLayerBlock.LAYERS.getPossibleValues(),
                                                                    integer -> LootItem.lootTableItem(ModBlocks.AURIC_SPORE_LAYER)
                                                                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly((float) integer.intValue())))
                                                                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LayeredAuricSporeBlock.ALL_MODELS, integer)))
                                                            )
                                                    )
                                            )
                            )
            );
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return BuiltInRegistries.BLOCK.stream()
                    .filter(b -> BuiltInRegistries.BLOCK.getKey(b).getNamespace().equals(SculkDepths.MOD_ID))
                    .filter(b -> this.map.containsKey(b.getLootTable()))
                    .toList();
        }
    }
}
