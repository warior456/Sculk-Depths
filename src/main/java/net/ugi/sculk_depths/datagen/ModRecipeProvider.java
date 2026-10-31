package net.ugi.sculk_depths.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput withResultSlot, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(withResultSlot, registriesFuture);
    }
    //examples:
    ////https://github.com/Tutorials-By-Kaupenjoe/Fabric-Tutorial-1.20.X/blob/a1ce57390adb5ef0f1cd6ba7a0bf6b1b659074f5/src/main/java/net/kaupenjoe/tutorialmod/datagen/ModRecipeProvider.java



    @Override
    protected void buildRecipes(RecipeOutput exporter) {
        stonecutterRecipe(exporter, ModBlocks.CHISELED_ZYGRIN, ModBlocks.ZYGRIN);
        stonecutterRecipe(exporter, ModBlocks.POLISHED_ZYGRIN, ModBlocks.ZYGRIN);
        stonecutterRecipe(exporter, ModBlocks.ZYGRIN_PILLAR, ModBlocks.ZYGRIN);
        stonecutterRecipe(exporter, ModBlocks.ZYGRIN_BRICKS, ModBlocks.ZYGRIN);

        // Vanilla nineBlockStorageRecipes derives bare item paths as ids; pass explicit mod ids so the
        // recipes and their advancements stay in data/sculk_depths instead of shadowing vanilla recipes.
        nineBlockStorageRecipes(exporter, RecipeCategory.MISC, ModItems.CRUX, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRUX_BLOCK,
                "sculk_depths:crux_block", null, "sculk_depths:crux", null);
        nineBlockStorageRecipes(exporter, RecipeCategory.MISC, ModItems.QUAZARITH_INGOT, RecipeCategory.BUILDING_BLOCKS, ModBlocks.QUAZARITH_BLOCK,
                "sculk_depths:quazarith_block", null, "sculk_depths:quazarith_ingot", null);

        //tools

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
        ButtonRecipe(exporter, ModBlocks.VALTROX_BUTTON, ModBlocks.VALTROX_PLANKS, "wooden_button");
        DoorRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_DOOR, RecipeCategory.REDSTONE, "wooden_door");
        FenceRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_FENCE, RecipeCategory.MISC,"wooden_fence");
        FenceGateRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_FENCE_GATE, RecipeCategory.MISC, "wooden_fence_gate");
        HanginSignRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModItems.VALTROX_HANGING_SIGN, RecipeCategory.MISC, "hanging_sign");
        planksRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModTags.Items.VALTROX_LOGS, 4);
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

        ButtonRecipe(exporter, ModBlocks.COATED_VALTROX_BUTTON, ModBlocks.COATED_VALTROX_PLANKS, "wooden_button");
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

        planksRecipe(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModTags.Items.COATED_VALTROX_LOGS, 4);
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_PLANKS.asItem(), ModBlocks.COATED_VALTROX_PLANKS.asItem(), RecipeCategory.MISC, "glux_coating");

        PressurePlateRecipe(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModBlocks.COATED_VALTROX_PRESSURE_PLATE, RecipeCategory.REDSTONE, "wooden_pressure_plate");
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_PRESSURE_PLATE.asItem(), ModBlocks.COATED_VALTROX_PRESSURE_PLATE.asItem(), RecipeCategory.REDSTONE, "glux_coating");

        //Dried valtrox
        WoodRecipe(exporter, ModBlocks.STRIPPED_DRIED_VALTROX_LOG, ModBlocks.STRIPPED_DRIED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        ButtonRecipe(exporter, ModBlocks.DRIED_VALTROX_BUTTON, ModBlocks.DRIED_VALTROX_PLANKS, "wooden_button");
        DoorRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_DOOR, RecipeCategory.REDSTONE, "wooden_door");
        FenceRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_FENCE, RecipeCategory.MISC,"wooden_fence");
        FenceGateRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_FENCE_GATE, RecipeCategory.MISC, "wooden_fence_gate");
        HanginSignRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModItems.DRIED_VALTROX_HANGING_SIGN, RecipeCategory.MISC, "hanging_sign");
        planksRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModTags.Items.DRIED_VALTROX_LOGS, 4);
        SignRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModItems.DRIED_VALTROX_SIGN, RecipeCategory.MISC, "wooden_sign");
        SlabRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_SLAB);
        StairsRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_STAIRS);
        TrapDoorRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_TRAPDOOR, RecipeCategory.REDSTONE, "wooden_trapdoor");
        WoodRecipe(exporter, ModBlocks.DRIED_VALTROX_LOG, ModBlocks.DRIED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        PressurePlateRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_PRESSURE_PLATE, RecipeCategory.REDSTONE, "wooden_pressure_plate");

        //petrified valtrox
        WoodRecipe(exporter, ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG, ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        ButtonRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_BUTTON, ModBlocks.PETRIFIED_VALTROX_SLATES, "wooden_button");
        DoorRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_DOOR, RecipeCategory.REDSTONE, "wooden_door");
        HanginSignRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModItems.PETRIFIED_VALTROX_HANGING_SIGN, RecipeCategory.MISC, "hanging_sign");
        SignRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModItems.PETRIFIED_VALTROX_SIGN, RecipeCategory.MISC, "wooden_sign");
        SlabRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_SLAB);
        planksRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModTags.Items.PETRIFIED_VALTROX_LOGS, 4);
        StairsRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_STAIRS);
        TrapDoorRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_TRAPDOOR, RecipeCategory.REDSTONE, "wooden_trapdoor");
        FenceRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_WALL, RecipeCategory.MISC,"wooden_fence");
        FenceGateRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_WALL_GATE, RecipeCategory.MISC, "wooden_fence_gate");
        WoodRecipe(exporter, ModBlocks.VALTROX_LOG, ModBlocks.PETRIFIED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        PressurePlateRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE, RecipeCategory.REDSTONE, "wooden_pressure_plate");



        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.CANDLE)
                .define('S', Items.STRING)
                .define('H', ModItems.GLOMPER_GLUX)
                .pattern("S")
                .pattern("H")
                .unlockedBy("has_string", has(Items.STRING))
                .unlockedBy("has_glomper_glux", has(ModItems.GLOMPER_GLUX))
                .save(exporter, SculkDepths.id("candle"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AURIC_SPORE_LAYER.asItem(), 6)
            .pattern("###")
            .define('#', ModBlocks.AURIC_SPORE_BLOCK)
            .unlockedBy(getHasName(ModBlocks.AURIC_SPORE_BLOCK), has(ModBlocks.AURIC_SPORE_BLOCK))
            .save(exporter, SculkDepths.id(getSimpleRecipeName(ModBlocks.AURIC_SPORE_LAYER)));

//todo recipecategory check
//todo recipegroups



    }

    void SlabRecipe(RecipeOutput exporter, Block input, Block withResultSlot){
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, withResultSlot, 6)
                .pattern("XXX")
                .define('X', input.asItem())
                .unlockedBy(getHasName(input.asItem()), has(input.asItem()))
                .save(exporter, SculkDepths.id(getSimpleRecipeName(withResultSlot.asItem())));

    }


    void StairsRecipe(RecipeOutput exporter, Block input, Block withResultSlot){
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, withResultSlot, 4)
                .pattern("X  ")
                .pattern("XX ")
                .pattern("XXX")
                .define('X', input.asItem())
                .unlockedBy(getHasName(input.asItem()), has(input.asItem()))
                .save(exporter, SculkDepths.id(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void CraftingTableCoatingRecipe(RecipeOutput exporter, Item uncoated, Item coated, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapelessRecipeBuilder.shapeless(recipeCategory, coated)
                .requires(ModItems.GLOMPER_GLUX)
                .requires(uncoated)
                .unlockedBy(getHasName(uncoated.asItem()), has(uncoated.asItem()))
                .group(recipeGroupPrefix)
                .save(exporter, SculkDepths.id(getSimpleRecipeName(coated.asItem()) + "_from_glux"));
    }

    void PressurePlateRecipe(RecipeOutput exporter, Block input, Block withResultSlot, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapedRecipeBuilder.shaped(recipeCategory, withResultSlot, 1)
                .pattern("XX")
                .define('X', input.asItem())
                .unlockedBy(getHasName(input.asItem()), has(input.asItem()))
                .group(recipeGroupPrefix)
                .save(exporter, SculkDepths.id(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void ChiseledRecipe(RecipeOutput exporter, Block input, Block withResultSlot, RecipeCategory recipeCategory){
        ShapedRecipeBuilder.shaped(recipeCategory, withResultSlot, 1)
                .pattern("X")
                .pattern("X")
                .define('X', input.asItem())
                .unlockedBy(getHasName(input.asItem()), has(input.asItem()))
                .save(exporter, SculkDepths.id(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void WoodRecipe(RecipeOutput exporter, Block input, Block withResultSlot, RecipeCategory recipeCategory){
        ShapedRecipeBuilder.shaped(recipeCategory, withResultSlot, 3)
                .pattern("XX")
                .pattern("XX")
                .define('X', input.asItem())
                .unlockedBy(getHasName(input.asItem()), has(input.asItem()))
                .group("bark")
                .save(exporter, SculkDepths.id(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void DoorRecipe(RecipeOutput exporter, Block input, Block withResultSlot, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapedRecipeBuilder.shaped(recipeCategory, withResultSlot, 3)
                .pattern("XX")
                .pattern("XX")
                .pattern("XX")
                .define('X', input.asItem())
                .unlockedBy(getHasName(input.asItem()), has(input.asItem()))
                .group(recipeGroupPrefix)
                .showNotification(true)
                .save(exporter, SculkDepths.id(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void FenceRecipe(RecipeOutput exporter, Block input, Block withResultSlot, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapedRecipeBuilder.shaped(recipeCategory, withResultSlot, 3)
                .pattern("WXW")
                .pattern("WXW")
                .define('X', Items.STICK)
                .define('W', input.asItem())
                .unlockedBy(getHasName(input.asItem()), has(input.asItem()))
                .group(recipeGroupPrefix)
                .save(exporter, SculkDepths.id(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void FenceGateRecipe(RecipeOutput exporter, Block input, Block withResultSlot, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapedRecipeBuilder.shaped(recipeCategory, withResultSlot, 1)
                .pattern("XWX")
                .pattern("XWX")
                .define('X', Items.STICK)
                .define('W', input.asItem())
                .unlockedBy(getHasName(input.asItem()), has(input.asItem()))
                .group(recipeGroupPrefix)
                .save(exporter, SculkDepths.id(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void HanginSignRecipe(RecipeOutput exporter, Block input, Item withResultSlot, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapedRecipeBuilder.shaped(recipeCategory, withResultSlot, 1)
                .pattern("X X")
                .pattern("WWW")
                .pattern("WWW")
                .define('X', Blocks.CHAIN)
                .define('W', input.asItem())
                .unlockedBy(getHasName(input), has(input))
                .group(recipeGroupPrefix)
                .save(exporter, SculkDepths.id(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void SignRecipe(RecipeOutput exporter, Block input, Item withResultSlot, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapedRecipeBuilder.shaped(recipeCategory, withResultSlot, 3)
                .pattern("WWW")
                .pattern("WWW")
                .pattern(" X ")
                .define('X', Items.STICK)
                .define('W', input.asItem())
                .unlockedBy(getHasName(input), has(input))
                .group(recipeGroupPrefix)
                .showNotification(true)
                .save(exporter, SculkDepths.id(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void TrapDoorRecipe(RecipeOutput exporter, Block input, Block withResultSlot, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapedRecipeBuilder.shaped(recipeCategory, withResultSlot, 2)
                .pattern("XXX")
                .pattern("XXX")
                .define('X', input.asItem())
                .unlockedBy(getHasName(input.asItem()), has(input.asItem()))
                .group(recipeGroupPrefix)
                .showNotification(true)
                .save(exporter, SculkDepths.id(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void stonecutterRecipe(RecipeOutput exporter, Block result, Block base){
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, result)
                .unlockedBy(getHasName(base), has(base))
                .save(exporter, SculkDepths.id(getConversionRecipeName(result, base) + "_stonecutting"));
    }

    void ButtonRecipe(RecipeOutput exporter, Block button, Block planks, String recipeGroupPrefix){
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, button)
                .requires(planks)
                .group(recipeGroupPrefix)
                .unlockedBy(getHasName(planks), has(planks))
                .save(exporter, SculkDepths.id(getConversionRecipeName(button, planks)));
    }

    void planksRecipe(RecipeOutput exporter, Block planks, TagKey<Item> logs, int count){
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, count)
                .requires(logs)
                .group("planks")
                .unlockedBy("has_logs", has(logs))
                .save(exporter, SculkDepths.id(getSimpleRecipeName(planks)));
    }

}
