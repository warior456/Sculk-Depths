package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
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

        // ------------------- saplings -------------------
        getOrCreateTagBuilder(ItemTags.SAPLINGS)
                .add(ModBlocks.VALTROX_SAPLING.asItem());

        // ------------------- logs (any) -------------------
        getOrCreateTagBuilder(ItemTags.LOGS)
                .add(ModBlocks.VALTROX_LOG.asItem())
                .add(ModBlocks.VALTROX_WOOD.asItem())
                .add(ModBlocks.STRIPPED_VALTROX_LOG.asItem())
                .add(ModBlocks.STRIPPED_VALTROX_WOOD.asItem())

                .add(ModBlocks.COATED_VALTROX_LOG.asItem())
                .add(ModBlocks.COATED_VALTROX_WOOD.asItem())
                .add(ModBlocks.COATED_STRIPPED_VALTROX_LOG.asItem())
                .add(ModBlocks.COATED_STRIPPED_VALTROX_WOOD.asItem())

                .add(ModBlocks.DRIED_VALTROX_LOG.asItem())
                .add(ModBlocks.DRIED_VALTROX_WOOD.asItem())
                .add(ModBlocks.STRIPPED_DRIED_VALTROX_LOG.asItem())
                .add(ModBlocks.STRIPPED_DRIED_VALTROX_WOOD.asItem())

                .add(ModBlocks.PETRIFIED_VALTROX_LOG.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_WOOD.asItem())
                .add(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG.asItem())
                .add(ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD.asItem());

        // ------------------- logs (burnable) -------------------
        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.VALTROX_LOG.asItem())
                .add(ModBlocks.VALTROX_WOOD.asItem())
                .add(ModBlocks.STRIPPED_VALTROX_LOG.asItem())
                .add(ModBlocks.STRIPPED_VALTROX_WOOD.asItem())

                .add(ModBlocks.COATED_VALTROX_LOG.asItem())
                .add(ModBlocks.COATED_VALTROX_WOOD.asItem())
                .add(ModBlocks.COATED_STRIPPED_VALTROX_LOG.asItem())
                .add(ModBlocks.COATED_STRIPPED_VALTROX_WOOD.asItem())

                .add(ModBlocks.DRIED_VALTROX_LOG.asItem())
                .add(ModBlocks.DRIED_VALTROX_WOOD.asItem())
                .add(ModBlocks.STRIPPED_DRIED_VALTROX_LOG.asItem())
                .add(ModBlocks.STRIPPED_DRIED_VALTROX_WOOD.asItem());

        // ------------------- planks -------------------
        getOrCreateTagBuilder(ItemTags.PLANKS)
                .add(ModBlocks.VALTROX_PLANKS.asItem())
                .add(ModBlocks.COATED_VALTROX_PLANKS.asItem())
                .add(ModBlocks.DRIED_VALTROX_PLANKS.asItem());

        // ------------------- stairs (any) ------------------- //todo the rest
        getOrCreateTagBuilder(ItemTags.STAIRS)
                .add(ModBlocks.VALTROX_STAIRS.asItem())
                .add(ModBlocks.COATED_VALTROX_STAIRS.asItem())
                .add(ModBlocks.DRIED_VALTROX_STAIRS.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_STAIRS.asItem());

        // ------------------- stairs (burnable) -------------------
        getOrCreateTagBuilder(ItemTags.WOODEN_STAIRS)
                .add(ModBlocks.VALTROX_STAIRS.asItem())
                .add(ModBlocks.COATED_VALTROX_STAIRS.asItem())
                .add(ModBlocks.DRIED_VALTROX_STAIRS.asItem());

        // ------------------- slabs (any) ------------------- //todo the rest
        getOrCreateTagBuilder(ItemTags.SLABS)
                .add(ModBlocks.VALTROX_SLAB.asItem())
                .add(ModBlocks.COATED_VALTROX_SLAB.asItem())
                .add(ModBlocks.DRIED_VALTROX_SLAB.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_SLAB.asItem());

        // ------------------- slabs (burnable) -------------------
        getOrCreateTagBuilder(ItemTags.WOODEN_SLABS)
                .add(ModBlocks.VALTROX_SLAB.asItem())
                .add(ModBlocks.COATED_VALTROX_SLAB.asItem())
                .add(ModBlocks.DRIED_VALTROX_SLAB.asItem());

        // ------------------- fences (any) -------------------
        getOrCreateTagBuilder(ItemTags.FENCES)
                .add(ModBlocks.VALTROX_FENCE.asItem())
                .add(ModBlocks.COATED_VALTROX_FENCE.asItem())
                .add(ModBlocks.DRIED_VALTROX_FENCE.asItem());

        // ------------------- fences (burnable) -------------------
        getOrCreateTagBuilder(ItemTags.WOODEN_FENCES)
                .add(ModBlocks.VALTROX_FENCE.asItem())
                .add(ModBlocks.COATED_VALTROX_FENCE.asItem())
                .add(ModBlocks.DRIED_VALTROX_FENCE.asItem());

        // ------------------- fence_gates (burnable?) -------------------
        getOrCreateTagBuilder(ItemTags.FENCE_GATES)
                .add(ModBlocks.VALTROX_FENCE_GATE.asItem())
                .add(ModBlocks.COATED_VALTROX_FENCE_GATE.asItem())
                .add(ModBlocks.DRIED_VALTROX_FENCE_GATE.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_WALL_GATE.asItem());

        // ------------------- doors (any) -------------------
        getOrCreateTagBuilder(ItemTags.DOORS)
                .add(ModBlocks.VALTROX_DOOR.asItem())
                .add(ModBlocks.COATED_VALTROX_DOOR.asItem())
                .add(ModBlocks.DRIED_VALTROX_DOOR.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_DOOR.asItem());

        // ------------------- doors (burnable) -------------------
        getOrCreateTagBuilder(ItemTags.WOODEN_DOORS)
                .add(ModBlocks.VALTROX_DOOR.asItem())
                .add(ModBlocks.COATED_VALTROX_DOOR.asItem())
                .add(ModBlocks.DRIED_VALTROX_DOOR.asItem());

        // ------------------- trapdoors (any) -------------------
        getOrCreateTagBuilder(ItemTags.TRAPDOORS)
                .add(ModBlocks.VALTROX_TRAPDOOR.asItem())
                .add(ModBlocks.COATED_VALTROX_TRAPDOOR.asItem())
                .add(ModBlocks.DRIED_VALTROX_TRAPDOOR.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_TRAPDOOR.asItem());

        // ------------------- trapdoors (any) -------------------
        getOrCreateTagBuilder(ItemTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.VALTROX_TRAPDOOR.asItem())
                .add(ModBlocks.COATED_VALTROX_TRAPDOOR.asItem())
                .add(ModBlocks.DRIED_VALTROX_TRAPDOOR.asItem());

        // ------------------- pressure_plates (wooden) -------------------
        getOrCreateTagBuilder(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.VALTROX_PRESSURE_PLATE.asItem())
                .add(ModBlocks.COATED_VALTROX_PRESSURE_PLATE.asItem())
                .add(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE.asItem());

        // ------------------- buttons (any) -------------------
        getOrCreateTagBuilder(ItemTags.BUTTONS)
                .add(ModBlocks.VALTROX_BUTTON.asItem())
                .add(ModBlocks.COATED_VALTROX_BUTTON.asItem())
                .add(ModBlocks.DRIED_VALTROX_BUTTON.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_BUTTON.asItem());

        // ------------------- buttons (wooden) -------------------
        getOrCreateTagBuilder(ItemTags.WOODEN_BUTTONS)
                .add(ModBlocks.VALTROX_BUTTON.asItem())
                .add(ModBlocks.COATED_VALTROX_BUTTON.asItem())
                .add(ModBlocks.DRIED_VALTROX_BUTTON.asItem());

        // ------------------- signs (normal) -------------------
        getOrCreateTagBuilder(ItemTags.SIGNS)
                .add(ModBlocks.VALTROX_SIGN.asItem())
                .add(ModBlocks.COATED_VALTROX_SIGN.asItem())
                .add(ModBlocks.DRIED_VALTROX_SIGN.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_SIGN.asItem());

        // ------------------- hanging signs -------------------
        getOrCreateTagBuilder(ItemTags.HANGING_SIGNS)
                .add(ModBlocks.VALTROX_HANGING_SIGN.asItem())
                .add(ModBlocks.COATED_VALTROX_HANGING_SIGN.asItem())
                .add(ModBlocks.DRIED_VALTROX_HANGING_SIGN.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN.asItem());

        // ------------------- non flammable wood -------------------
        getOrCreateTagBuilder(ItemTags.NON_FLAMMABLE_WOOD)
                .add(ModBlocks.PETRIFIED_VALTROX_LOG.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_WOOD.asItem())
                .add(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG.asItem())
                .add(ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_SLATES.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_STAIRS.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_SLAB.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_WALL.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_WALL_GATE.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_DOOR.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_TRAPDOOR.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_BUTTON.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_SIGN.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN.asItem());
    }
}