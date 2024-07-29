package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.List;
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

        //tools
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ENERGISED_FLINT_AND_STEEL)
                .input(ModItems.GLOMPER_GLUX)
                .input(ModItems.ENERGY_ESSENCE)
                .input(Items.FLINT_AND_STEEL)
                .criterion(hasItem(ModItems.ENERGY_ESSENCE), conditionsFromItem(ModItems.ENERGY_ESSENCE))
                //.group(group)
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.ENERGISED_FLINT_AND_STEEL)));

        //crux
/*
        offerBlasting(exporter, ModBlocks.CRUX_ORE.asItem(), RecipeCategory.MISC, ModItems.CRUX, 10, 100, null);//todo (recipe fixed in normal)
*/

        //umbrusk
        StairsRecipe(exporter, ModBlocks.UMBRUSK, ModBlocks.UMBRUSK_STAIRS);
        SlabRecipe(exporter, ModBlocks.UMBRUSK, ModBlocks.UMBRUSK_SLAB);

        //zygrin
        ChiseledRecipe(exporter, ModBlocks.POLISHED_ZYGRIN_SLAB, ModBlocks.CHISELED_ZYGRIN, RecipeCategory.BUILDING_BLOCKS);

        //valtrox

        WoodRecipe(exporter, ModBlocks.STRIPPED_VALTROX_LOG, ModBlocks.STRIPPED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        offerShapelessRecipe(exporter,ModBlocks.VALTROX_BUTTON, ModBlocks.VALTROX_PLANKS, "wooden_button",1);
        DoorRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_DOOR, RecipeCategory.REDSTONE, "wooden_door");
        FenceRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_FENCE, RecipeCategory.MISC,"wooden_fence");
        FenceGateRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_FENCE_GATE, RecipeCategory.MISC, "wooden_fence_gate");
        HanginSignRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModItems.VALTROX_HANGING_SIGN, RecipeCategory.MISC, "hanging_sign");
        offerPlanksRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModTags.Items.VALTROX_LOGS, 4);
        SignRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModItems.VALTROX_SIGN, RecipeCategory.MISC, "wooden_sign");
        SlabRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_SLAB);
        StairsRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_STAIRS);
        TrapDoorRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_TRAPDOOR, RecipeCategory.REDSTONE, "wooden_trapdoor");
        WoodRecipe(exporter, ModBlocks.VALTROX_LOG, ModBlocks.VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        PressurePlateRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_PRESSURE_PLATE, RecipeCategory.REDSTONE, "wooden_pressure_plate");


        //coated valtrox //TODO recipecategory
        CraftingTableCoatingRecipe(exporter, ModBlocks.STRIPPED_VALTROX_LOG.asItem(), ModBlocks.COATED_STRIPPED_VALTROX_LOG.asItem(), RecipeCategory.MISC,"glux_coating");

        WoodRecipe(exporter, ModBlocks.COATED_STRIPPED_VALTROX_LOG, ModBlocks.COATED_STRIPPED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        CraftingTableCoatingRecipe(exporter, ModBlocks.STRIPPED_VALTROX_WOOD.asItem(), ModBlocks.COATED_STRIPPED_VALTROX_WOOD.asItem(), RecipeCategory.BUILDING_BLOCKS, "glux_coating");

        offerShapelessRecipe(exporter,ModBlocks.COATED_VALTROX_BUTTON, ModBlocks.COATED_VALTROX_PLANKS, "wooden_button",1);
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_BUTTON.asItem(), ModBlocks.COATED_VALTROX_BUTTON.asItem(), RecipeCategory.MISC, "glux_coating");

        DoorRecipe(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModBlocks.COATED_VALTROX_DOOR, RecipeCategory.REDSTONE, "wooden_door");
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_DOOR.asItem(), ModBlocks.COATED_VALTROX_DOOR.asItem(), RecipeCategory.MISC, "glux_coating");

        FenceRecipe(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModBlocks.COATED_VALTROX_FENCE, RecipeCategory.MISC,"wooden_fence");
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_FENCE.asItem(), ModBlocks.COATED_VALTROX_FENCE.asItem(), RecipeCategory.MISC, "glux_coating");

        FenceGateRecipe(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModBlocks.COATED_VALTROX_FENCE_GATE, RecipeCategory.MISC, "wooden_fence_gate");
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_FENCE_GATE.asItem(), ModBlocks.COATED_VALTROX_FENCE_GATE.asItem(), RecipeCategory.MISC, "glux_coating");

        HanginSignRecipe(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModItems.COATED_VALTROX_HANGING_SIGN, RecipeCategory.MISC, "hanging_sign");
        CraftingTableCoatingRecipe(exporter, ModItems.VALTROX_HANGING_SIGN, ModItems.COATED_VALTROX_HANGING_SIGN, RecipeCategory.MISC, "glux_coating");

        SignRecipe(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModItems.COATED_VALTROX_SIGN, RecipeCategory.MISC, "wooden_sign");
        CraftingTableCoatingRecipe(exporter, ModItems.VALTROX_SIGN, ModItems.COATED_VALTROX_SIGN, RecipeCategory.MISC, "glux_coating");

        SlabRecipe(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModBlocks.COATED_VALTROX_SLAB);
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_SLAB.asItem(), ModBlocks.COATED_VALTROX_SLAB.asItem(), RecipeCategory.BUILDING_BLOCKS, "glux_coating");

        StairsRecipe(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModBlocks.COATED_VALTROX_STAIRS);
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_STAIRS.asItem(), ModBlocks.COATED_VALTROX_STAIRS.asItem(), RecipeCategory.BUILDING_BLOCKS, "glux_coating");

        TrapDoorRecipe(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModBlocks.COATED_VALTROX_TRAPDOOR, RecipeCategory.REDSTONE, "wooden_trapdoor");
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_TRAPDOOR.asItem(), ModBlocks.COATED_VALTROX_TRAPDOOR.asItem(), RecipeCategory.REDSTONE, "glux_coating");

        WoodRecipe(exporter, ModBlocks.COATED_VALTROX_LOG, ModBlocks.COATED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_WOOD.asItem(), ModBlocks.COATED_VALTROX_WOOD.asItem(), RecipeCategory.BUILDING_BLOCKS, "glux_coating");

        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_LOG.asItem(), ModBlocks.COATED_VALTROX_LOG.asItem(), RecipeCategory.BUILDING_BLOCKS, "glux_coating");

        offerPlanksRecipe(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModTags.Items.COATED_VALTROX_LOGS, 4);
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_PLANKS.asItem(), ModBlocks.COATED_VALTROX_PLANKS.asItem(), RecipeCategory.MISC, "glux_coating");

        PressurePlateRecipe(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModBlocks.COATED_VALTROX_PRESSURE_PLATE, RecipeCategory.REDSTONE, "wooden_pressure_plate");
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_PRESSURE_PLATE.asItem(), ModBlocks.COATED_VALTROX_PRESSURE_PLATE.asItem(), RecipeCategory.REDSTONE, "glux_coating");

        //Dried valtrox
        WoodRecipe(exporter, ModBlocks.STRIPPED_DRIED_VALTROX_LOG, ModBlocks.STRIPPED_DRIED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        offerShapelessRecipe(exporter,ModBlocks.DRIED_VALTROX_BUTTON, ModBlocks.DRIED_VALTROX_PLANKS, "wooden_button",1);
        DoorRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_DOOR, RecipeCategory.REDSTONE, "wooden_door");
        FenceRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_FENCE, RecipeCategory.MISC,"wooden_fence");
        FenceGateRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_FENCE_GATE, RecipeCategory.MISC, "wooden_fence_gate");
        HanginSignRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModItems.DRIED_VALTROX_HANGING_SIGN, RecipeCategory.MISC, "hanging_sign");
        offerPlanksRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModTags.Items.DRIED_VALTROX_LOGS, 4);
        SignRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModItems.DRIED_VALTROX_SIGN, RecipeCategory.MISC, "wooden_sign");
        SlabRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_SLAB);
        StairsRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_STAIRS);
        TrapDoorRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_TRAPDOOR, RecipeCategory.REDSTONE, "wooden_trapdoor");
        WoodRecipe(exporter, ModBlocks.DRIED_VALTROX_LOG, ModBlocks.DRIED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        PressurePlateRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_PRESSURE_PLATE, RecipeCategory.REDSTONE, "wooden_pressure_plate");

        //petrified valtrox
        WoodRecipe(exporter, ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG, ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        offerShapelessRecipe(exporter,ModBlocks.PETRIFIED_VALTROX_BUTTON, ModBlocks.PETRIFIED_VALTROX_SLATES, "wooden_button",1);
        DoorRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_DOOR, RecipeCategory.REDSTONE, "wooden_door");
        HanginSignRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModItems.PETRIFIED_VALTROX_HANGING_SIGN, RecipeCategory.MISC, "hanging_sign");
        SignRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModItems.PETRIFIED_VALTROX_SIGN, RecipeCategory.MISC, "wooden_sign");
        SlabRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_SLAB);
        offerPlanksRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModTags.Items.PETRIFIED_VALTROX_LOGS, 4);
        StairsRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_STAIRS);
        TrapDoorRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_TRAPDOOR, RecipeCategory.REDSTONE, "wooden_trapdoor");
        FenceRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_WALL, RecipeCategory.MISC,"wooden_fence");
        FenceGateRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_WALL_GATE, RecipeCategory.MISC, "wooden_fence_gate");
        WoodRecipe(exporter, ModBlocks.VALTROX_LOG, ModBlocks.PETRIFIED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        PressurePlateRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE, RecipeCategory.REDSTONE, "wooden_pressure_plate");





        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AURIC_SPORE_LAYER.asItem(), 6)
            .pattern("###")
            .input('#', ModBlocks.AURIC_SPORE_BLOCK)
            .criterion(hasItem(ModBlocks.AURIC_SPORE_BLOCK), conditionsFromItem(ModBlocks.AURIC_SPORE_BLOCK))
            .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.AURIC_SPORE_LAYER)));

//todo recipecategory check
//todo recipegroups



    }

    void SlabRecipe(RecipeExporter exporter, Block input, Block output){
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("XXX")
                .input('X', input.asItem())
                .criterion(hasItem(input.asItem()), conditionsFromItem(input.asItem()))
                .offerTo(exporter, Identifier.of(getRecipeName(output.asItem())));

    }


    void StairsRecipe(RecipeExporter exporter, Block input, Block output){
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .input('X', input.asItem())
                .criterion(hasItem(input.asItem()), conditionsFromItem(input.asItem()))
                .offerTo(exporter, Identifier.of(getRecipeName(output.asItem())));
    }

    void CraftingTableCoatingRecipe(RecipeExporter exporter, Item uncoated, Item coated, RecipeCategory recipeCategory, String group){
        ShapelessRecipeJsonBuilder.create(recipeCategory, coated)
                .input(ModItems.GLOMPER_GLUX)
                .input(uncoated)
                .criterion(hasItem(uncoated.asItem()), conditionsFromItem(uncoated.asItem()))
                .group(group)
                .offerTo(exporter, Identifier.of(getRecipeName(coated.asItem()) + "_from_glux"));
    }

    void PressurePlateRecipe(RecipeExporter exporter, Block input, Block output, RecipeCategory recipeCategory, String group){
        ShapedRecipeJsonBuilder.create(recipeCategory, output, 1)
                .pattern("XX")
                .input('X', input.asItem())
                .criterion(hasItem(input.asItem()), conditionsFromItem(input.asItem()))
                .group(group)
                .offerTo(exporter, Identifier.of(getRecipeName(output.asItem())));
    }

    void ChiseledRecipe(RecipeExporter exporter, Block input, Block output, RecipeCategory recipeCategory){
        ShapedRecipeJsonBuilder.create(recipeCategory, output, 1)
                .pattern("X")
                .pattern("X")
                .input('X', input.asItem())
                .criterion(hasItem(input.asItem()), conditionsFromItem(input.asItem()))
                .offerTo(exporter, Identifier.of(getRecipeName(output.asItem())));
    }

    void WoodRecipe(RecipeExporter exporter, Block input, Block output, RecipeCategory recipeCategory){
        ShapedRecipeJsonBuilder.create(recipeCategory, output, 3)
                .pattern("XX")
                .pattern("XX")
                .input('X', input.asItem())
                .criterion(hasItem(input.asItem()), conditionsFromItem(input.asItem()))
                .group("bark")
                .offerTo(exporter, Identifier.of(getRecipeName(output.asItem())));
    }

    void DoorRecipe(RecipeExporter exporter, Block input, Block output, RecipeCategory recipeCategory, String group){
        ShapedRecipeJsonBuilder.create(recipeCategory, output, 3)
                .pattern("XX")
                .pattern("XX")
                .pattern("XX")
                .input('X', input.asItem())
                .criterion(hasItem(input.asItem()), conditionsFromItem(input.asItem()))
                .group(group)
                .showNotification(true)
                .offerTo(exporter, Identifier.of(getRecipeName(output.asItem())));
    }

    void FenceRecipe(RecipeExporter exporter, Block input, Block output, RecipeCategory recipeCategory, String group){
        ShapedRecipeJsonBuilder.create(recipeCategory, output, 3)
                .pattern("WXW")
                .pattern("WXW")
                .input('X', Items.STICK)
                .input('W', input.asItem())
                .criterion(hasItem(input.asItem()), conditionsFromItem(input.asItem()))
                .group(group)
                .offerTo(exporter, Identifier.of(getRecipeName(output.asItem())));
    }

    void FenceGateRecipe(RecipeExporter exporter, Block input, Block output, RecipeCategory recipeCategory, String group){
        ShapedRecipeJsonBuilder.create(recipeCategory, output, 1)
                .pattern("XWX")
                .pattern("XWX")
                .input('X', Items.STICK)
                .input('W', input.asItem())
                .criterion(hasItem(input.asItem()), conditionsFromItem(input.asItem()))
                .group(group)
                .offerTo(exporter, Identifier.of(getRecipeName(output.asItem())));
    }

    void HanginSignRecipe(RecipeExporter exporter, Block input, Item output, RecipeCategory recipeCategory, String group){
        ShapedRecipeJsonBuilder.create(recipeCategory, output, 1)
                .pattern("X X")
                .pattern("WWW")
                .pattern("WWW")
                .input('X', Blocks.CHAIN)
                .input('W', input.asItem())
                .criterion(hasItem(input), conditionsFromItem(input))
                .group(group)
                .offerTo(exporter, Identifier.of(getRecipeName(output.asItem())));
    }

    void SignRecipe(RecipeExporter exporter, Block input, Item output, RecipeCategory recipeCategory, String group){
        ShapedRecipeJsonBuilder.create(recipeCategory, output, 3)
                .pattern("WWW")
                .pattern("WWW")
                .pattern(" X ")
                .input('X', Items.STICK)
                .input('W', input.asItem())
                .criterion(hasItem(input), conditionsFromItem(input))
                .group(group)
                .showNotification(true)
                .offerTo(exporter, Identifier.of(getRecipeName(output.asItem())));
    }

    void TrapDoorRecipe(RecipeExporter exporter, Block input, Block output, RecipeCategory recipeCategory, String group){
        ShapedRecipeJsonBuilder.create(recipeCategory, output, 2)
                .pattern("XXX")
                .pattern("XXX")
                .input('X', input.asItem())
                .criterion(hasItem(input.asItem()), conditionsFromItem(input.asItem()))
                .group(group)
                .showNotification(true)
                .offerTo(exporter, Identifier.of(getRecipeName(output.asItem())));
    }

}