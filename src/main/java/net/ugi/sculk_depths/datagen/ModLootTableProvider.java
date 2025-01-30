package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantment;
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
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.LayeredAuricSporeBlock;
import net.ugi.sculk_depths.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {


    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.QUAZARITH_BLOCK, drops(ModBlocks.QUAZARITH_BLOCK.asItem()));
        addDrop(ModBlocks.CRUX_BLOCK, drops(ModBlocks.CRUX_BLOCK.asItem()));

        addDrop(ModBlocks.QUAZARITH_ORE, oreDrops(ModBlocks.QUAZARITH_ORE,ModItems.QUAZARITH_PIECES));
        addDrop(ModBlocks.CRUX_ORE, oreDrops(ModBlocks.CRUX_ORE,ModItems.CRUX));


        addDrop(ModBlocks.LARGUTH);
        addDrop(ModBlocks.ZYGRIN_FURNACE, this::nameableContainerDrops);
        addDrop(ModBlocks.CEPHLERA_LIGHT);
        addDrop(ModBlocks.CRUMBLING_DIRT);
        addDrop(ModBlocks.KRYSLUM_ENRICHED_SOIL);
        addDrop(ModBlocks.VALTROX_SAPLING);
        addDrop(ModBlocks.AURIC_VINES_END);
        addDrop(ModBlocks.VALTROX_LEAVES, leavesDrops(ModBlocks.VALTROX_LEAVES,ModBlocks.VALTROX_SAPLING, 0.1F));
        addDrop(ModBlocks.DRIED_GRASS, dropsWithSilkTouch(ModBlocks.DRIED_GRASS));
        addDrop(ModBlocks.QUAZARITH_OSCILLATOR);

        addPottedPlantDrops(ModBlocks.POTTED_AURIC_SPORE_SPROUTS);
        addPottedPlantDrops(ModBlocks.POTTED_PENEBRIUM_SHROOM);
        addPottedPlantDrops(ModBlocks.POTTED_VALTROX_SAPLING);


        addDrop(ModBlocks.VALTROX_LOG);
        addDrop(ModBlocks.VALTROX_WOOD);
        addDrop(ModBlocks.STRIPPED_VALTROX_LOG);
        addDrop(ModBlocks.STRIPPED_VALTROX_WOOD);
        addDrop(ModBlocks.VALTROX_PLANKS);
        addDrop(ModBlocks.VALTROX_STAIRS);
        addDrop(ModBlocks.VALTROX_SLAB, slabDrops(ModBlocks.VALTROX_SLAB));
        addDrop(ModBlocks.VALTROX_FENCE);
        addDrop(ModBlocks.VALTROX_FENCE_GATE);
        addDrop(ModBlocks.VALTROX_DOOR, doorDrops(ModBlocks.VALTROX_DOOR));
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
        addDrop(ModBlocks.COATED_VALTROX_SLAB, slabDrops(ModBlocks.COATED_VALTROX_SLAB));
        addDrop(ModBlocks.COATED_VALTROX_FENCE);
        addDrop(ModBlocks.COATED_VALTROX_FENCE_GATE);
        addDrop(ModBlocks.COATED_VALTROX_DOOR, doorDrops(ModBlocks.COATED_VALTROX_DOOR));
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
        addDrop(ModBlocks.DRIED_VALTROX_SLAB, slabDrops(ModBlocks.DRIED_VALTROX_SLAB));
        addDrop(ModBlocks.DRIED_VALTROX_FENCE);
        addDrop(ModBlocks.DRIED_VALTROX_FENCE_GATE);
        addDrop(ModBlocks.DRIED_VALTROX_DOOR, doorDrops(ModBlocks.DRIED_VALTROX_DOOR));
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
        addDrop(ModBlocks.PETRIFIED_VALTROX_SLAB, slabDrops(ModBlocks.PETRIFIED_VALTROX_SLAB));
        addDrop(ModBlocks.PETRIFIED_VALTROX_WALL);
        addDrop(ModBlocks.PETRIFIED_VALTROX_WALL_GATE);
        addDrop(ModBlocks.PETRIFIED_VALTROX_DOOR, doorDrops(ModBlocks.PETRIFIED_VALTROX_DOOR));
        addDrop(ModBlocks.PETRIFIED_VALTROX_TRAPDOOR);
        addDrop(ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE);
        addDrop(ModBlocks.PETRIFIED_VALTROX_BUTTON);
        addDrop(ModBlocks.PETRIFIED_VALTROX_SIGN);
        addDrop(ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN);

        addDrop(ModBlocks.AMALGAMITE);
        addDrop(ModBlocks.POLISHED_AMALGAMITE);
        addDrop(ModBlocks.POLISHED_AMALGAMITE_STAIRS);
        addDrop(ModBlocks.POLISHED_AMALGAMITE_SLAB, slabDrops(ModBlocks.POLISHED_AMALGAMITE_SLAB));
        addDrop(ModBlocks.POLISHED_AMALGAMITE_WALL);
        addDrop(ModBlocks.AMALGAMITE_BRICKS);
        addDrop(ModBlocks.CRACKED_AMALGAMITE_BRICKS);
        addDrop(ModBlocks.AMALGAMITE_BRICK_STAIRS);
        addDrop(ModBlocks.AMALGAMITE_BRICK_SLAB, slabDrops(ModBlocks.AMALGAMITE_BRICK_SLAB));
        addDrop(ModBlocks.AMALGAMITE_BRICK_WALL);
        addDrop(ModBlocks.CHISELED_AMALGAMITE);

        addDrop(ModBlocks.ZYGRIN);
        addDrop(ModBlocks.ZYGRIN_STAIRS);
        addDrop(ModBlocks.ZYGRIN_SLAB, slabDrops(ModBlocks.ZYGRIN_SLAB));
        addDrop(ModBlocks.ZYGRIN_WALL);
        addDrop(ModBlocks.POLISHED_ZYGRIN);
        addDrop(ModBlocks.POLISHED_ZYGRIN_STAIRS);
        addDrop(ModBlocks.POLISHED_ZYGRIN_SLAB, slabDrops(ModBlocks.POLISHED_ZYGRIN_SLAB));
        addDrop(ModBlocks.POLISHED_ZYGRIN_WALL);
        addDrop(ModBlocks.ZYGRIN_BRICKS);
        addDrop(ModBlocks.ZYGRIN_BRICK_STAIRS);
        addDrop(ModBlocks.ZYGRIN_BRICK_SLAB, slabDrops(ModBlocks.ZYGRIN_BRICK_SLAB));
        addDrop(ModBlocks.ZYGRIN_BRICK_WALL);
        addDrop(ModBlocks.ZYGRIN_PILLAR);
        addDrop(ModBlocks.CHISELED_ZYGRIN);
        addDrop(ModBlocks.ZYGRIN_BRICKS);
        addDrop(ModBlocks.ZYGRIN_LIGHT);
        addDrop(ModBlocks.ZYGRIN_FLOWBLOCK);
        addDrop(ModBlocks.ZYGRIN_FURNACE);

        addDrop(ModBlocks.UMBRUSK);
        addDrop(ModBlocks.UMBRUSK_STAIRS);
        addDrop(ModBlocks.UMBRUSK_SLAB, slabDrops(ModBlocks.UMBRUSK_SLAB));
        addDrop(ModBlocks.UMBRUSK_WALL);
        addDrop(ModBlocks.UMBRUSK_BRICKS);
        addDrop(ModBlocks.UMBRUSK_BRICK_STAIRS);
        addDrop(ModBlocks.UMBRUSK_BRICK_SLAB, slabDrops(ModBlocks.UMBRUSK_BRICK_SLAB));
        addDrop(ModBlocks.UMBRUSK_BRICK_WALL);

        addDrop(ModBlocks.FLUMROCK);
        addDrop(ModBlocks.FLUMROCK_TILES);
        addDrop(ModBlocks.CRUMBLING_FLUMROCK_TILES);
        addDrop(ModBlocks.FLUMROCK_TILE_STAIRS);
        addDrop(ModBlocks.FLUMROCK_TILE_SLAB, slabDrops(ModBlocks.FLUMROCK_TILE_SLAB));
        addDrop(ModBlocks.FLUMROCK_TILE_WALL);
        addDrop(ModBlocks.FLUMROCK_CAULDRON);


        addDrop(ModBlocks.PENEBRIUM_SHROOM);
        addDropWithSilkTouch(ModBlocks.PENEBRIUM_SHROOM_STEM);
        addDropWithSilkTouch(ModBlocks.PENEBRIUM_SHROOM_BLOCK);
        addDrop(ModBlocks.PENEBRIUM_SPORE_BLOCK, mushroomBlockDrops(ModBlocks.PENEBRIUM_SPORE_BLOCK, ModBlocks.PENEBRIUM_SHROOM));

        addDrop(ModBlocks.AURIC_SPORE_SPROUTS);
        addDropWithSilkTouch(ModBlocks.AURIC_SHROOM_STEM);
        addDrop(ModBlocks.AURIC_SPORE_BLOCK, silkTouchOrItemDrops(ModBlocks.AURIC_SPORE_BLOCK,ModBlocks.AURIC_SPORE_SPROUTS.asItem()));
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

    public LootTable.Builder silkTouchOrItemDrops(Block withSilkTouch, Item withoutSilkTouch) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(withSilkTouch, (LootPoolEntry.Builder)this.applyExplosionDecay(withSilkTouch, ItemEntry.builder(withoutSilkTouch)));
    }

}