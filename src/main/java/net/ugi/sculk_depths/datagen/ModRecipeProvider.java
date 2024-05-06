package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }
    //examples:
    ////https://github.com/Tutorials-By-Kaupenjoe/Fabric-Tutorial-1.20.X/blob/a1ce57390adb5ef0f1cd6ba7a0bf6b1b659074f5/src/main/java/net/kaupenjoe/tutorialmod/datagen/ModRecipeProvider.java


    @Override
    public void generate(RecipeExporter exporter) {
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_ZYGRIN, ModBlocks.ZYGRIN);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_ZYGRIN, ModBlocks.ZYGRIN);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ZYGRIN_PILLAR, ModBlocks.ZYGRIN);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ZYGRIN_BRICKS, ModBlocks.ZYGRIN);

        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.CRUX, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRUX_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.QUAZARITH_INGOT, RecipeCategory.BUILDING_BLOCKS, ModBlocks.QUAZARITH_BLOCK);

        StairsRecipe(exporter, ModBlocks.UMBRUSK, ModBlocks.UMBRUSK_STAIRS);
        SlabRecipe(exporter, ModBlocks.UMBRUSK, ModBlocks.UMBRUSK_SLAB);
//todo recipecategory check
//todo recipegroups


        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_LOG.asItem(), ModBlocks.COATED_VALTROX_LOG.asItem(), RecipeCategory.MISC, "todo");
        CraftingTableCoatingRecipe(exporter, ModBlocks.STRIPPED_VALTROX_LOG.asItem(), ModBlocks.COATED_STRIPPED_VALTROX_LOG.asItem(), RecipeCategory.MISC,"todo");
        CraftingTableCoatingRecipe(exporter, ModBlocks.STRIPPED_VALTROX_WOOD.asItem(), ModBlocks.COATED_STRIPPED_VALTROX_WOOD.asItem(), RecipeCategory.MISC, "todo");
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_BUTTON.asItem(), ModBlocks.COATED_VALTROX_BUTTON.asItem(), RecipeCategory.MISC, "todo");
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_DOOR.asItem(), ModBlocks.COATED_VALTROX_DOOR.asItem(), RecipeCategory.MISC, "todo");
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_FENCE.asItem(), ModBlocks.COATED_VALTROX_FENCE.asItem(), RecipeCategory.MISC, "todo");
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_FENCE_GATE.asItem(), ModBlocks.COATED_VALTROX_FENCE_GATE.asItem(), RecipeCategory.MISC, "todo");
        CraftingTableCoatingRecipe(exporter, ModItems.VALTROX_HANGING_SIGN, ModItems.COATED_VALTROX_HANGING_SIGN, RecipeCategory.MISC, "todo");
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_PLANKS.asItem(), ModBlocks.COATED_VALTROX_PLANKS.asItem(), RecipeCategory.MISC, "todo");
        }

    void SlabRecipe(RecipeExporter exporter, Block input, Block output){
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("XXX")
                .input('X', input.asItem())
                .criterion(hasItem(input.asItem()), conditionsFromItem(input.asItem()))
                .offerTo(exporter, new Identifier(getRecipeName(output.asItem())));
    }

    void StairsRecipe(RecipeExporter exporter, Block input, Block output){
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .input('X', input.asItem())
                .criterion(hasItem(input.asItem()), conditionsFromItem(input.asItem()))
                .offerTo(exporter, new Identifier(getRecipeName(output.asItem())));
    }

    void CraftingTableCoatingRecipe(RecipeExporter exporter, Item uncoated, Item coated, RecipeCategory recipeCategory, String group){
        ShapelessRecipeJsonBuilder.create(recipeCategory, coated)
                .input(ModItems.GLOMPER_GLUX)
                .input(uncoated)
                .criterion(hasItem(uncoated.asItem()), conditionsFromItem(uncoated.asItem()))
                .group(group)
                .offerTo(exporter, new Identifier(getRecipeName(coated.asItem()) + "_from_glux"));
    }



}