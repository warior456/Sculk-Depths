package net.ugi.sculk_depths.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput withResultSlot, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(withResultSlot, registriesFuture);
    }
    //examples:
    ////https://github.com/Tutorials-By-Kaupenjoe/Fabric-Tutorial-1.20.X/blob/a1ce57390adb5ef0f1cd6ba7a0bf6b1b659074f5/src/main/java/net/kaupenjoe/tutorialmod/datagen/ModRecipeProvider.java



    @Override
    public void generate(RecipeOutput exporter) {
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_ZYGRIN, ModBlocks.ZYGRIN);
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_ZYGRIN, ModBlocks.ZYGRIN);
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ZYGRIN_PILLAR, ModBlocks.ZYGRIN);
        stonecutterResultFromBase(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ZYGRIN_BRICKS, ModBlocks.ZYGRIN);

        nineBlockStorageRecipes(exporter, RecipeCategory.MISC, ModItems.CRUX, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRUX_BLOCK);
        nineBlockStorageRecipes(exporter, RecipeCategory.MISC, ModItems.QUAZARITH_INGOT, RecipeCategory.BUILDING_BLOCKS, ModBlocks.QUAZARITH_BLOCK);

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
        oneToOneConversionRecipe(exporter,ModBlocks.VALTROX_BUTTON, ModBlocks.VALTROX_PLANKS, "wooden_button",1);
        DoorRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_DOOR, RecipeCategory.REDSTONE_BLOCKS, "wooden_door");
        FenceRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_FENCE, RecipeCategory.MISC,"wooden_fence");
        FenceGateRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_FENCE_GATE, RecipeCategory.MISC, "wooden_fence_gate");
        HanginSignRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModItems.VALTROX_HANGING_SIGN, RecipeCategory.MISC, "hanging_sign");
        planksFromLogs(exporter, ModBlocks.VALTROX_PLANKS, ModTags.Items.VALTROX_LOGS, 4);
        SignRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModItems.VALTROX_SIGN, RecipeCategory.MISC, "wooden_sign");
        SlabRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_SLAB);
        StairsRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_STAIRS);
        TrapDoorRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_TRAPDOOR, RecipeCategory.REDSTONE_BLOCKS, "wooden_trapdoor");
        WoodRecipe(exporter, ModBlocks.VALTROX_LOG, ModBlocks.VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        PressurePlateRecipe(exporter, ModBlocks.VALTROX_PLANKS, ModBlocks.VALTROX_PRESSURE_PLATE, RecipeCategory.REDSTONE_BLOCKS, "wooden_pressure_plate");


        //coated valtrox //TODO recipecategory
        CraftingTableCoatingRecipe(exporter, ModBlocks.STRIPPED_VALTROX_LOG.asItem(), ModBlocks.COATED_STRIPPED_VALTROX_LOG.asItem(), RecipeCategory.MISC,"glux_coating");

        WoodRecipe(exporter, ModBlocks.COATED_STRIPPED_VALTROX_LOG, ModBlocks.COATED_STRIPPED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        CraftingTableCoatingRecipe(exporter, ModBlocks.STRIPPED_VALTROX_WOOD.asItem(), ModBlocks.COATED_STRIPPED_VALTROX_WOOD.asItem(), RecipeCategory.BUILDING_BLOCKS, "glux_coating");

        oneToOneConversionRecipe(exporter,ModBlocks.COATED_VALTROX_BUTTON, ModBlocks.COATED_VALTROX_PLANKS, "wooden_button",1);
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_BUTTON.asItem(), ModBlocks.COATED_VALTROX_BUTTON.asItem(), RecipeCategory.MISC, "glux_coating");

        DoorRecipe(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModBlocks.COATED_VALTROX_DOOR, RecipeCategory.REDSTONE_BLOCKS, "wooden_door");
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

        TrapDoorRecipe(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModBlocks.COATED_VALTROX_TRAPDOOR, RecipeCategory.REDSTONE_BLOCKS, "wooden_trapdoor");
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_TRAPDOOR.asItem(), ModBlocks.COATED_VALTROX_TRAPDOOR.asItem(), RecipeCategory.REDSTONE_BLOCKS, "glux_coating");

        WoodRecipe(exporter, ModBlocks.COATED_VALTROX_LOG, ModBlocks.COATED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_WOOD.asItem(), ModBlocks.COATED_VALTROX_WOOD.asItem(), RecipeCategory.BUILDING_BLOCKS, "glux_coating");

        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_LOG.asItem(), ModBlocks.COATED_VALTROX_LOG.asItem(), RecipeCategory.BUILDING_BLOCKS, "glux_coating");

        planksFromLogs(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModTags.Items.COATED_VALTROX_LOGS, 4);
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_PLANKS.asItem(), ModBlocks.COATED_VALTROX_PLANKS.asItem(), RecipeCategory.MISC, "glux_coating");

        PressurePlateRecipe(exporter, ModBlocks.COATED_VALTROX_PLANKS, ModBlocks.COATED_VALTROX_PRESSURE_PLATE, RecipeCategory.REDSTONE_BLOCKS, "wooden_pressure_plate");
        CraftingTableCoatingRecipe(exporter, ModBlocks.VALTROX_PRESSURE_PLATE.asItem(), ModBlocks.COATED_VALTROX_PRESSURE_PLATE.asItem(), RecipeCategory.REDSTONE_BLOCKS, "glux_coating");

        //Dried valtrox
        WoodRecipe(exporter, ModBlocks.STRIPPED_DRIED_VALTROX_LOG, ModBlocks.STRIPPED_DRIED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        oneToOneConversionRecipe(exporter,ModBlocks.DRIED_VALTROX_BUTTON, ModBlocks.DRIED_VALTROX_PLANKS, "wooden_button",1);
        DoorRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_DOOR, RecipeCategory.REDSTONE_BLOCKS, "wooden_door");
        FenceRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_FENCE, RecipeCategory.MISC,"wooden_fence");
        FenceGateRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_FENCE_GATE, RecipeCategory.MISC, "wooden_fence_gate");
        HanginSignRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModItems.DRIED_VALTROX_HANGING_SIGN, RecipeCategory.MISC, "hanging_sign");
        planksFromLogs(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModTags.Items.DRIED_VALTROX_LOGS, 4);
        SignRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModItems.DRIED_VALTROX_SIGN, RecipeCategory.MISC, "wooden_sign");
        SlabRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_SLAB);
        StairsRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_STAIRS);
        TrapDoorRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_TRAPDOOR, RecipeCategory.REDSTONE_BLOCKS, "wooden_trapdoor");
        WoodRecipe(exporter, ModBlocks.DRIED_VALTROX_LOG, ModBlocks.DRIED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        PressurePlateRecipe(exporter, ModBlocks.DRIED_VALTROX_PLANKS, ModBlocks.DRIED_VALTROX_PRESSURE_PLATE, RecipeCategory.REDSTONE_BLOCKS, "wooden_pressure_plate");

        //petrified valtrox
        WoodRecipe(exporter, ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG, ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        oneToOneConversionRecipe(exporter,ModBlocks.PETRIFIED_VALTROX_BUTTON, ModBlocks.PETRIFIED_VALTROX_SLATES, "wooden_button",1);
        DoorRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_DOOR, RecipeCategory.REDSTONE_BLOCKS, "wooden_door");
        HanginSignRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModItems.PETRIFIED_VALTROX_HANGING_SIGN, RecipeCategory.MISC, "hanging_sign");
        SignRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModItems.PETRIFIED_VALTROX_SIGN, RecipeCategory.MISC, "wooden_sign");
        SlabRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_SLAB);
        planksFromLogs(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModTags.Items.PETRIFIED_VALTROX_LOGS, 4);
        StairsRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_STAIRS);
        TrapDoorRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_TRAPDOOR, RecipeCategory.REDSTONE_BLOCKS, "wooden_trapdoor");
        FenceRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_WALL, RecipeCategory.MISC,"wooden_fence");
        FenceGateRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_WALL_GATE, RecipeCategory.MISC, "wooden_fence_gate");
        WoodRecipe(exporter, ModBlocks.VALTROX_LOG, ModBlocks.PETRIFIED_VALTROX_WOOD, RecipeCategory.BUILDING_BLOCKS);
        PressurePlateRecipe(exporter, ModBlocks.PETRIFIED_VALTROX_SLATES, ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE, RecipeCategory.REDSTONE_BLOCKS, "wooden_pressure_plate");



        ShapedRecipeBuilder.create(RecipeCategory.DECORATIONS, Items.CANDLE)
                .input('S', Items.STRING_UTF8)
                .input('H', ModItems.GLOMPER_GLUX)
                .addPattern("S")
                .addPattern("H")
                .criteria("has_string", has(Items.STRING_UTF8))
                .criteria("has_glomper_glux", has(ModItems.GLOMPER_GLUX))
                .save(exporter);

        ShapedRecipeBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AURIC_SPORE_LAYER.asItem(), 6)
            .addPattern("###")
            .input('#', ModBlocks.AURIC_SPORE_BLOCK)
            .criteria(hasItem(ModBlocks.AURIC_SPORE_BLOCK), has(ModBlocks.AURIC_SPORE_BLOCK))
            .save(exporter, ResourceLocation.fromNamespaceAndPath(getSimpleRecipeName(ModBlocks.AURIC_SPORE_LAYER)));

//todo recipecategory check
//todo recipegroups



    }

    void SlabRecipe(RecipeOutput exporter, Block input, Block withResultSlot){
        ShapedRecipeBuilder.create(RecipeCategory.BUILDING_BLOCKS, withResultSlot, 6)
                .addPattern("XXX")
                .input('X', input.asItem())
                .criteria(hasItem(input.asItem()), has(input.asItem()))
                .save(exporter, ResourceLocation.fromNamespaceAndPath(getSimpleRecipeName(withResultSlot.asItem())));

    }


    void StairsRecipe(RecipeOutput exporter, Block input, Block withResultSlot){
        ShapedRecipeBuilder.create(RecipeCategory.BUILDING_BLOCKS, withResultSlot, 4)
                .addPattern("X  ")
                .addPattern("XX ")
                .addPattern("XXX")
                .input('X', input.asItem())
                .criteria(hasItem(input.asItem()), has(input.asItem()))
                .save(exporter, ResourceLocation.fromNamespaceAndPath(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void CraftingTableCoatingRecipe(RecipeOutput exporter, Item uncoated, Item coated, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapelessRecipeBuilder.create(recipeCategory, coated)
                .input(ModItems.GLOMPER_GLUX)
                .input(uncoated)
                .criteria(hasItem(uncoated.asItem()), has(uncoated.asItem()))
                .recipeGroupPrefix(recipeGroupPrefix)
                .save(exporter, ResourceLocation.fromNamespaceAndPath(getSimpleRecipeName(coated.asItem()) + "_from_glux"));
    }

    void PressurePlateRecipe(RecipeOutput exporter, Block input, Block withResultSlot, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapedRecipeBuilder.create(recipeCategory, withResultSlot, 1)
                .addPattern("XX")
                .input('X', input.asItem())
                .criteria(hasItem(input.asItem()), has(input.asItem()))
                .recipeGroupPrefix(recipeGroupPrefix)
                .save(exporter, ResourceLocation.fromNamespaceAndPath(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void ChiseledRecipe(RecipeOutput exporter, Block input, Block withResultSlot, RecipeCategory recipeCategory){
        ShapedRecipeBuilder.create(recipeCategory, withResultSlot, 1)
                .addPattern("X")
                .addPattern("X")
                .input('X', input.asItem())
                .criteria(hasItem(input.asItem()), has(input.asItem()))
                .save(exporter, ResourceLocation.fromNamespaceAndPath(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void WoodRecipe(RecipeOutput exporter, Block input, Block withResultSlot, RecipeCategory recipeCategory){
        ShapedRecipeBuilder.create(recipeCategory, withResultSlot, 3)
                .addPattern("XX")
                .addPattern("XX")
                .input('X', input.asItem())
                .criteria(hasItem(input.asItem()), has(input.asItem()))
                .recipeGroupPrefix("bark")
                .save(exporter, ResourceLocation.fromNamespaceAndPath(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void DoorRecipe(RecipeOutput exporter, Block input, Block withResultSlot, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapedRecipeBuilder.create(recipeCategory, withResultSlot, 3)
                .addPattern("XX")
                .addPattern("XX")
                .addPattern("XX")
                .input('X', input.asItem())
                .criteria(hasItem(input.asItem()), has(input.asItem()))
                .recipeGroupPrefix(recipeGroupPrefix)
                .showNotification(true)
                .save(exporter, ResourceLocation.fromNamespaceAndPath(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void FenceRecipe(RecipeOutput exporter, Block input, Block withResultSlot, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapedRecipeBuilder.create(recipeCategory, withResultSlot, 3)
                .addPattern("WXW")
                .addPattern("WXW")
                .input('X', Items.STICK)
                .input('W', input.asItem())
                .criteria(hasItem(input.asItem()), has(input.asItem()))
                .recipeGroupPrefix(recipeGroupPrefix)
                .save(exporter, ResourceLocation.fromNamespaceAndPath(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void FenceGateRecipe(RecipeOutput exporter, Block input, Block withResultSlot, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapedRecipeBuilder.create(recipeCategory, withResultSlot, 1)
                .addPattern("XWX")
                .addPattern("XWX")
                .input('X', Items.STICK)
                .input('W', input.asItem())
                .criteria(hasItem(input.asItem()), has(input.asItem()))
                .recipeGroupPrefix(recipeGroupPrefix)
                .save(exporter, ResourceLocation.fromNamespaceAndPath(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void HanginSignRecipe(RecipeOutput exporter, Block input, Item withResultSlot, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapedRecipeBuilder.create(recipeCategory, withResultSlot, 1)
                .addPattern("X X")
                .addPattern("WWW")
                .addPattern("WWW")
                .input('X', Blocks.CHAIN)
                .input('W', input.asItem())
                .criteria(hasItem(input), has(input))
                .recipeGroupPrefix(recipeGroupPrefix)
                .save(exporter, ResourceLocation.fromNamespaceAndPath(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void SignRecipe(RecipeOutput exporter, Block input, Item withResultSlot, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapedRecipeBuilder.create(recipeCategory, withResultSlot, 3)
                .addPattern("WWW")
                .addPattern("WWW")
                .addPattern(" X ")
                .input('X', Items.STICK)
                .input('W', input.asItem())
                .criteria(hasItem(input), has(input))
                .recipeGroupPrefix(recipeGroupPrefix)
                .showNotification(true)
                .save(exporter, ResourceLocation.fromNamespaceAndPath(getSimpleRecipeName(withResultSlot.asItem())));
    }

    void TrapDoorRecipe(RecipeOutput exporter, Block input, Block withResultSlot, RecipeCategory recipeCategory, String recipeGroupPrefix){
        ShapedRecipeBuilder.create(recipeCategory, withResultSlot, 2)
                .addPattern("XXX")
                .addPattern("XXX")
                .input('X', input.asItem())
                .criteria(hasItem(input.asItem()), has(input.asItem()))
                .recipeGroupPrefix(recipeGroupPrefix)
                .showNotification(true)
                .save(exporter, ResourceLocation.fromNamespaceAndPath(getSimpleRecipeName(withResultSlot.asItem())));
    }

}