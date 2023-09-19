package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.block.ModBlocks;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Mod;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        /*
        getOrCreateTagBuilder(ModTags.Blocks.METAL_DETECTOR_DETECTABLE_BLOCKS)
                .add(ModBlocks.RUBY_ORE)
                .forceAddTag(BlockTags.GOLD_ORES)
                .forceAddTag(BlockTags.EMERALD_ORES)
                .forceAddTag(BlockTags.REDSTONE_ORES)
                .forceAddTag(BlockTags.LAPIS_ORES)
                .forceAddTag(BlockTags.DIAMOND_ORES)
                .forceAddTag(BlockTags.IRON_ORES)
                .forceAddTag(BlockTags.COPPER_ORES)
                .forceAddTag(BlockTags.COAL_ORES);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.RAW_RUBY_BLOCK)
                .add(ModBlocks.RUBY_BLOCK)
                .add(ModBlocks.RUBY_ORE)
                .add(ModBlocks.DEEPSLATE_RUBY_ORE)
                .add(ModBlocks.NETHER_RUBY_ORE)
                .add(ModBlocks.END_STONE_RUBY_ORE)
                .add(ModBlocks.SOUND_BLOCK);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.DEEPSLATE_RUBY_ORE);

         */

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("fabric", "needs_tool_level_4")))
                .add(ModBlocks.QUAZARITH_BLOCK)
                .add(ModBlocks.QUAZARITH_ORE);


        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS)
                .add(ModBlocks.VALTROX_SIGN)

                .add(ModBlocks.DRIED_VALTROX_SIGN)

                .add(ModBlocks.PETRIFIED_VALTROX_SIGN);

        getOrCreateTagBuilder(BlockTags.WALL_SIGNS)
                .add(ModBlocks.VALTROX_WALL_SIGN)

                .add(ModBlocks.DRIED_VALTROX_WALL_SIGN)

                .add(ModBlocks.PETRIFIED_VALTROX_WALL_SIGN);

        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS)
                .add(ModBlocks.VALTROX_HANGING_SIGN)

                .add(ModBlocks.DRIED_VALTROX_HANGING_SIGN)

                .add(ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN);


        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS)
                .add(ModBlocks.VALTROX_WALL_HANGING_SIGN)

                .add(ModBlocks.DRIED_VALTROX_WALL_HANGING_SIGN)

                .add(ModBlocks.PETRIFIED_VALTROX_WALL_HANGING_SIGN);
    }
}