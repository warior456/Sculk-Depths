package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ModTags.Blocks.KRYSLUM_FLOWABLE_BLOCKS)
                .add(ModBlocks.ZYGRIN_FLOWBLOCK);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.AMALGAMITE)

                .add(ModBlocks.UMBRUSK)
                .add(ModBlocks.UMBRUSK_STAIRS)
                .add(ModBlocks.UMBRUSK_SLAB)
                .add(ModBlocks.UMBRUSK_WALL)

                .add(ModBlocks.UMBRUSK_BRICKS)
                .add(ModBlocks.UMBRUSK_BRICK_STAIRS)
                .add(ModBlocks.UMBRUSK_BRICK_SLAB)
                .add(ModBlocks.UMBRUSK_BRICK_WALL)

                .add(ModBlocks.FLUMROCK)
                .add(ModBlocks.FLUMROCK_CAULDRON)

                .add(ModBlocks.QUAZARITH_BLOCK)
                .add(ModBlocks.QUAZARITH_ORE)

                .add(ModBlocks.CRUX_BLOCK)
                .add(ModBlocks.CRUX_ORE)

                .add(ModBlocks.PETRIFIED_VALTROX_LOG)
                .add(ModBlocks.PETRIFIED_VALTROX_WOOD)
                .add(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG)
                .add(ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD)
                .add(ModBlocks.PETRIFIED_VALTROX_SLATES)
                .add(ModBlocks.PETRIFIED_VALTROX_STAIRS)
                .add(ModBlocks.PETRIFIED_VALTROX_SLAB)
                .add(ModBlocks.PETRIFIED_VALTROX_WALL)
                .add(ModBlocks.PETRIFIED_VALTROX_WALL_GATE)
                .add(ModBlocks.PETRIFIED_VALTROX_DOOR)
                .add(ModBlocks.PETRIFIED_VALTROX_TRAPDOOR)
                .add(ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE)
                .add(ModBlocks.PETRIFIED_VALTROX_BUTTON)

                .add(ModBlocks.ZYGRIN)
                .add(ModBlocks.ZYGRIN_STAIRS)
                .add(ModBlocks.ZYGRIN_SLAB)
                .add(ModBlocks.ZYGRIN_WALL)
                .add(ModBlocks.ZYGRIN_LIGHT)
                .add(ModBlocks.ZYGRIN_FLOWBLOCK)

                .add(ModBlocks.POLISHED_ZYGRIN)
                .add(ModBlocks.POLISHED_ZYGRIN_STAIRS)
                .add(ModBlocks.POLISHED_ZYGRIN_SLAB)
                .add(ModBlocks.POLISHED_ZYGRIN_WALL)

                .add(ModBlocks.ZYGRIN_BRICKS)
                .add(ModBlocks.ZYGRIN_BRICK_STAIRS)
                .add(ModBlocks.ZYGRIN_BRICK_SLAB)
                .add(ModBlocks.ZYGRIN_BRICK_WALL)

                .add(ModBlocks.ZYGRIN_PILLAR)
                .add(ModBlocks.CHISELED_ZYGRIN)

                .add(ModBlocks.LARGUTH);

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(ModBlocks.VALTROX_LOG)
                .add(ModBlocks.VALTROX_WOOD)
                .add(ModBlocks.STRIPPED_VALTROX_LOG)
                .add(ModBlocks.STRIPPED_VALTROX_WOOD)
                .add(ModBlocks.VALTROX_PLANKS)
                .add(ModBlocks.VALTROX_STAIRS)
                .add(ModBlocks.VALTROX_SLAB)
                .add(ModBlocks.VALTROX_FENCE)
                .add(ModBlocks.VALTROX_FENCE_GATE)
                .add(ModBlocks.VALTROX_DOOR)
                .add(ModBlocks.VALTROX_TRAPDOOR)
                .add(ModBlocks.VALTROX_PRESSURE_PLATE)
                .add(ModBlocks.VALTROX_BUTTON)


                .add(ModBlocks.COATED_VALTROX_LOG)
                .add(ModBlocks.COATED_VALTROX_WOOD)
                .add(ModBlocks.COATED_STRIPPED_VALTROX_LOG)
                .add(ModBlocks.COATED_STRIPPED_VALTROX_WOOD)
                .add(ModBlocks.COATED_VALTROX_PLANKS)
                .add(ModBlocks.COATED_VALTROX_STAIRS)
                .add(ModBlocks.COATED_VALTROX_SLAB)
                .add(ModBlocks.COATED_VALTROX_FENCE)
                .add(ModBlocks.COATED_VALTROX_FENCE_GATE)
                .add(ModBlocks.COATED_VALTROX_DOOR)
                .add(ModBlocks.COATED_VALTROX_TRAPDOOR)
                .add(ModBlocks.COATED_VALTROX_PRESSURE_PLATE)
                .add(ModBlocks.COATED_VALTROX_BUTTON)

                .add(ModBlocks.DRIED_VALTROX_LOG)
                .add(ModBlocks.DRIED_VALTROX_WOOD)
                .add(ModBlocks.STRIPPED_DRIED_VALTROX_LOG)
                .add(ModBlocks.STRIPPED_DRIED_VALTROX_WOOD)
                .add(ModBlocks.DRIED_VALTROX_PLANKS)
                .add(ModBlocks.DRIED_VALTROX_STAIRS)
                .add(ModBlocks.DRIED_VALTROX_SLAB)
                .add(ModBlocks.DRIED_VALTROX_FENCE)
                .add(ModBlocks.DRIED_VALTROX_FENCE_GATE)
                .add(ModBlocks.DRIED_VALTROX_DOOR)
                .add(ModBlocks.DRIED_VALTROX_TRAPDOOR)
                .add(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE)
                .add(ModBlocks.DRIED_VALTROX_BUTTON)

                .add(ModBlocks.PENEBRIUM_SHROOM_BLOCK)
                .add(ModBlocks.PENEBRIUM_SHROOM_STEM)
                .add(ModBlocks.PENEBRIUM_SPORE_BLOCK);

/*



        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.DEEPSLATE_RUBY_ORE);

         */

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("fabric", "needs_tool_level_4")))
                .add(ModBlocks.QUAZARITH_BLOCK)
                .add(ModBlocks.QUAZARITH_ORE);


        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS)
                .add(ModBlocks.VALTROX_SIGN)
                .add(ModBlocks.COATED_VALTROX_SIGN)

                .add(ModBlocks.DRIED_VALTROX_SIGN)

                .add(ModBlocks.PETRIFIED_VALTROX_SIGN);

        getOrCreateTagBuilder(BlockTags.WALL_SIGNS)
                .add(ModBlocks.VALTROX_WALL_SIGN)
                .add(ModBlocks.COATED_VALTROX_WALL_SIGN)

                .add(ModBlocks.DRIED_VALTROX_WALL_SIGN)

                .add(ModBlocks.PETRIFIED_VALTROX_WALL_SIGN);

        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS)
                .add(ModBlocks.VALTROX_HANGING_SIGN)
                .add(ModBlocks.COATED_VALTROX_HANGING_SIGN)

                .add(ModBlocks.DRIED_VALTROX_HANGING_SIGN)

                .add(ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN);


        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS)
                .add(ModBlocks.VALTROX_WALL_HANGING_SIGN)
                .add(ModBlocks.COATED_VALTROX_WALL_HANGING_SIGN)

                .add(ModBlocks.DRIED_VALTROX_WALL_HANGING_SIGN)

                .add(ModBlocks.PETRIFIED_VALTROX_WALL_HANGING_SIGN);
    }
}