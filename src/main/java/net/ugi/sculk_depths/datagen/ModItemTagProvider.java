package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput withResultSlot, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(withResultSlot, completableFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider arg) {

        tag(ModTags.Items.VALTROX_LOGS)
                .add(ModBlocks.VALTROX_LOG.asItem())
                .add(ModBlocks.VALTROX_WOOD.asItem())
                .add(ModBlocks.STRIPPED_VALTROX_LOG.asItem())
                .add(ModBlocks.STRIPPED_VALTROX_WOOD.asItem());

        tag(ModTags.Items.COATED_VALTROX_LOGS)
                .add(ModBlocks.COATED_VALTROX_LOG.asItem())
                .add(ModBlocks.COATED_VALTROX_WOOD.asItem())
                .add(ModBlocks.COATED_STRIPPED_VALTROX_LOG.asItem())
                .add(ModBlocks.COATED_STRIPPED_VALTROX_WOOD.asItem());

        tag(ModTags.Items.DRIED_VALTROX_LOGS)
                .add(ModBlocks.DRIED_VALTROX_LOG.asItem())
                .add(ModBlocks.DRIED_VALTROX_WOOD.asItem())
                .add(ModBlocks.STRIPPED_DRIED_VALTROX_LOG.asItem())
                .add(ModBlocks.STRIPPED_DRIED_VALTROX_WOOD.asItem());

        tag(ModTags.Items.PETRIFIED_VALTROX_LOGS)
                .add(ModBlocks.PETRIFIED_VALTROX_LOG.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_WOOD.asItem())
                .add(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG.asItem())
                .add(ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD.asItem());

        tag(ItemTags.SHOVELS)
                .add(ModItems.QUAZARITH_SHOVEL);

        tag(ItemTags.PICKAXES)
                .add(ModItems.QUAZARITH_PICKAXE);

        tag(ItemTags.AXIS_VALUES)
                .add(ModItems.QUAZARITH_AXE);

        tag(ItemTags.HOES)
                .add(ModItems.QUAZARITH_HOE);

        tag(ItemTags.SWORDS)
                .add(ModItems.QUAZARITH_SWORD);

        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.QUAZARITH_HELMET)
                .add(ModItems.QUAZARITH_CHESTPLATE)
                .add(ModItems.QUAZARITH_LEGGINGS)
                .add(ModItems.QUAZARITH_BOOTS);

        tag(ItemTags.ARMOR_ENCHANTABLE)
                .add(ModItems.QUAZARITH_HELMET)
                .add(ModItems.QUAZARITH_CHESTPLATE)
                .add(ModItems.QUAZARITH_LEGGINGS)
                .add(ModItems.QUAZARITH_BOOTS);

        tag(ItemTags.HEAD_ARMOR_ENCHANTABLE)
                .add(ModItems.QUAZARITH_HELMET);

        tag(ItemTags.CHEST_ARMOR_ENCHANTABLE)
                .add(ModItems.QUAZARITH_CHESTPLATE);

        tag(ItemTags.LEG_ARMOR_ENCHANTABLE)
                .add(ModItems.QUAZARITH_LEGGINGS);

        tag(ItemTags.FOOT_ARMOR_ENCHANTABLE)
                .add(ModItems.QUAZARITH_BOOTS);

        // ------------------- saplings -------------------
        tag(ItemTags.SAPLINGS)
                .add(ModBlocks.VALTROX_SAPLING.asItem());

        // ------------------- logs (any) -------------------
        tag(ItemTags.LOGS)
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
        tag(ItemTags.LOGS_THAT_BURN)
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
        tag(ItemTags.PLANKS)
                .add(ModBlocks.VALTROX_PLANKS.asItem())
                .add(ModBlocks.COATED_VALTROX_PLANKS.asItem())
                .add(ModBlocks.DRIED_VALTROX_PLANKS.asItem());

        // ------------------- stairs (any) ------------------- //todo the rest
        tag(ItemTags.STAIRS_STRAIGHT)
                .add(ModBlocks.VALTROX_STAIRS.asItem())
                .add(ModBlocks.COATED_VALTROX_STAIRS.asItem())
                .add(ModBlocks.DRIED_VALTROX_STAIRS.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_STAIRS.asItem());

        // ------------------- stairs (burnable) -------------------
        tag(ItemTags.WOODEN_STAIRS)
                .add(ModBlocks.VALTROX_STAIRS.asItem())
                .add(ModBlocks.COATED_VALTROX_STAIRS.asItem())
                .add(ModBlocks.DRIED_VALTROX_STAIRS.asItem());

        // ------------------- slabs (any) ------------------- //todo the rest
        tag(ItemTags.SLABS)
                .add(ModBlocks.VALTROX_SLAB.asItem())
                .add(ModBlocks.COATED_VALTROX_SLAB.asItem())
                .add(ModBlocks.DRIED_VALTROX_SLAB.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_SLAB.asItem());

        // ------------------- slabs (burnable) -------------------
        tag(ItemTags.WOODEN_SLABS)
                .add(ModBlocks.VALTROX_SLAB.asItem())
                .add(ModBlocks.COATED_VALTROX_SLAB.asItem())
                .add(ModBlocks.DRIED_VALTROX_SLAB.asItem());

        // ------------------- fences (any) -------------------
        tag(ItemTags.FENCES)
                .add(ModBlocks.VALTROX_FENCE.asItem())
                .add(ModBlocks.COATED_VALTROX_FENCE.asItem())
                .add(ModBlocks.DRIED_VALTROX_FENCE.asItem());

        // ------------------- fences (burnable) -------------------
        tag(ItemTags.WOODEN_FENCES)
                .add(ModBlocks.VALTROX_FENCE.asItem())
                .add(ModBlocks.COATED_VALTROX_FENCE.asItem())
                .add(ModBlocks.DRIED_VALTROX_FENCE.asItem());

        // ------------------- fence_gates (burnable?) -------------------
        tag(ItemTags.FENCE_GATES)
                .add(ModBlocks.VALTROX_FENCE_GATE.asItem())
                .add(ModBlocks.COATED_VALTROX_FENCE_GATE.asItem())
                .add(ModBlocks.DRIED_VALTROX_FENCE_GATE.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_WALL_GATE.asItem());

        // ------------------- doors (any) -------------------
        tag(ItemTags.DOORS)
                .add(ModBlocks.VALTROX_DOOR.asItem())
                .add(ModBlocks.COATED_VALTROX_DOOR.asItem())
                .add(ModBlocks.DRIED_VALTROX_DOOR.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_DOOR.asItem());

        // ------------------- doors (burnable) -------------------
        tag(ItemTags.WOODEN_DOORS)
                .add(ModBlocks.VALTROX_DOOR.asItem())
                .add(ModBlocks.COATED_VALTROX_DOOR.asItem())
                .add(ModBlocks.DRIED_VALTROX_DOOR.asItem());

        // ------------------- trapdoors (any) -------------------
        tag(ItemTags.TRAPDOORS)
                .add(ModBlocks.VALTROX_TRAPDOOR.asItem())
                .add(ModBlocks.COATED_VALTROX_TRAPDOOR.asItem())
                .add(ModBlocks.DRIED_VALTROX_TRAPDOOR.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_TRAPDOOR.asItem());

        // ------------------- trapdoors (any) -------------------
        tag(ItemTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.VALTROX_TRAPDOOR.asItem())
                .add(ModBlocks.COATED_VALTROX_TRAPDOOR.asItem())
                .add(ModBlocks.DRIED_VALTROX_TRAPDOOR.asItem());

        // ------------------- pressure_plates (wooden) -------------------
        tag(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.VALTROX_PRESSURE_PLATE.asItem())
                .add(ModBlocks.COATED_VALTROX_PRESSURE_PLATE.asItem())
                .add(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE.asItem());

        // ------------------- buttons (any) -------------------
        tag(ItemTags.BUTTONS)
                .add(ModBlocks.VALTROX_BUTTON.asItem())
                .add(ModBlocks.COATED_VALTROX_BUTTON.asItem())
                .add(ModBlocks.DRIED_VALTROX_BUTTON.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_BUTTON.asItem());

        // ------------------- buttons (wooden) -------------------
        tag(ItemTags.WOODEN_BUTTONS)
                .add(ModBlocks.VALTROX_BUTTON.asItem())
                .add(ModBlocks.COATED_VALTROX_BUTTON.asItem())
                .add(ModBlocks.DRIED_VALTROX_BUTTON.asItem());

        // ------------------- signs (normal) -------------------
        tag(ItemTags.SIGNS)
                .add(ModBlocks.VALTROX_SIGN.asItem())
                .add(ModBlocks.COATED_VALTROX_SIGN.asItem())
                .add(ModBlocks.DRIED_VALTROX_SIGN.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_SIGN.asItem());

        // ------------------- hanging signs -------------------
        tag(ItemTags.HANGING_SIGNS)
                .add(ModBlocks.VALTROX_HANGING_SIGN.asItem())
                .add(ModBlocks.COATED_VALTROX_HANGING_SIGN.asItem())
                .add(ModBlocks.DRIED_VALTROX_HANGING_SIGN.asItem())
                .add(ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN.asItem());

        // ------------------- non flammable wood -------------------
        tag(ItemTags.NON_FLAMMABLE_WOOD)
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