package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        getOrCreateTagBuilder(ModTags.Items.VALTROX_LOGS)
                .add(ModBlocks.VALTROX_LOG.asItem())
                .add(ModBlocks.VALTROX_WOOD.asItem())
                .add(ModBlocks.STRIPPED_VALTROX_LOG.asItem())
                .add(ModBlocks.STRIPPED_VALTROX_WOOD.asItem());

        getOrCreateTagBuilder(ModTags.Items.COATED_VALTROX_LOGS)
                .add(ModBlocks.COATED_VALTROX_LOG.asItem())
                .add(ModBlocks.COATED_VALTROX_WOOD.asItem())
                .add(ModBlocks.COATED_STRIPPED_VALTROX_LOG.asItem())
                .add(ModBlocks.COATED_STRIPPED_VALTROX_WOOD.asItem());

        getOrCreateTagBuilder(ModTags.Items.DRIED_VALTROX_LOGS)
                .add(ModBlocks.DRIED_VALTROX_LOG.asItem())
                .add(ModBlocks.DRIED_VALTROX_WOOD.asItem())
                .add(ModBlocks.STRIPPED_DRIED_VALTROX_LOG.asItem())
                .add(ModBlocks.STRIPPED_DRIED_VALTROX_WOOD.asItem());

        getOrCreateTagBuilder(ModTags.Items.PETRIFIED_VALTROX_LOGS)
                .add(ModBlocks.PETRIFIED_VALTROX_LOG.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_WOOD.asItem())
                .add(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG.asItem())
                .add(ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD.asItem());

        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ModItems.QUAZARITH_SHOVEL);

        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.QUAZARITH_PICKAXE);

        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModItems.QUAZARITH_AXE);

        getOrCreateTagBuilder(ItemTags.HOES)
                .add(ModItems.QUAZARITH_HOE);

        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModItems.QUAZARITH_SWORD);

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.QUAZARITH_HELMET)
                .add(ModItems.QUAZARITH_CHESTPLATE)
                .add(ModItems.QUAZARITH_LEGGINGS)
                .add(ModItems.QUAZARITH_BOOTS);

        getOrCreateTagBuilder(ItemTags.ARMOR_ENCHANTABLE)
                .add(ModItems.QUAZARITH_HELMET)
                .add(ModItems.QUAZARITH_CHESTPLATE)
                .add(ModItems.QUAZARITH_LEGGINGS)
                .add(ModItems.QUAZARITH_BOOTS);

        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR_ENCHANTABLE)
                .add(ModItems.QUAZARITH_HELMET);

        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR_ENCHANTABLE)
                .add(ModItems.QUAZARITH_CHESTPLATE);

        getOrCreateTagBuilder(ItemTags.LEG_ARMOR_ENCHANTABLE)
                .add(ModItems.QUAZARITH_LEGGINGS);

        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR_ENCHANTABLE)
                .add(ModItems.QUAZARITH_BOOTS);
    }
}