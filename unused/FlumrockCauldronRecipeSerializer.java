package net.ugi.sculk_depths.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;


public class FlumrockCauldronRecipeSerializer implements RecipeSerializer<FlumRockCauldronRecipe> {
    @Override
    // Turns json into Recipe
    public FlumRockCauldronRecipe read(Identifier recipeId, JsonObject json) {
        FlumrockCauldronRecipeJsonFormat recipeJson = new Gson().fromJson(json, FlumrockCauldronRecipeJsonFormat.class);
        if (recipeJson.ingredient1 == null || recipeJson.outputItem == null || recipeJson.ingredient_counts == null) {
            throw new JsonSyntaxException("A required attribute is missing!");
        }
        // We'll allow to not specify the output, and default it to 1.
        if (recipeJson.outputAmount == 0) recipeJson.outputAmount = 1;

        JsonArray ingredient_counts = JsonHelper.getArray(json, "ingredient_counts");

        DefaultedList<ItemStack> inputs = DefaultedList.ofSize(3, ItemStack.EMPTY);


        Item ingredient1Item = Registries.ITEM.getOrEmpty(new Identifier(recipeJson.ingredient1))
            .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.ingredient1));
        inputs.set(0, new ItemStack(ingredient1Item, ingredient_counts.get(0).getAsInt()));

        Item ingredient2Item = Registries.ITEM.getOrEmpty(new Identifier(recipeJson.ingredient2))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.ingredient2));
        inputs.set(1, new ItemStack(ingredient2Item, ingredient_counts.get(1).getAsInt()));

        Item ingredient3Item = Registries.ITEM.getOrEmpty(new Identifier(recipeJson.ingredient3))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.ingredient3));
        inputs.set(2, new ItemStack(ingredient3Item, ingredient_counts.get(2).getAsInt()));


        Item outputItem = Registries.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem))
                // Validate the inputted item actually exists
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.outputItem));
        ItemStack output = new ItemStack(outputItem, recipeJson.outputAmount);

        return new FlumRockCauldronRecipe(inputs, output, recipeId);
    }
    @Override
    public FlumRockCauldronRecipe read(Identifier id, PacketByteBuf buf) {
        DefaultedList<ItemStack> inputs = DefaultedList.ofSize(buf.readInt(), ItemStack.EMPTY);

        for (int i = 0; i < inputs.size(); i++) {
            inputs.set(i, buf.readItemStack());
        }

        ItemStack output = buf.readItemStack();
        return new FlumRockCauldronRecipe(inputs, output, id);
    }

    @Override
    public void write(PacketByteBuf buf, FlumRockCauldronRecipe recipe) {
        buf.writeInt(recipe.getRecipeItems().size());
        for (ItemStack ing : recipe.getRecipeItems()) {
            buf.writeItemStack(ing);
        }
        buf.writeItemStack(recipe.getOutput(DynamicRegistryManager.EMPTY));
    }

    private FlumrockCauldronRecipeSerializer() {
    }

    public static final FlumrockCauldronRecipeSerializer INSTANCE = new FlumrockCauldronRecipeSerializer();

    // This will be the "type" field in the json
    public static final String ID = "flumrock_cauldron_recipe";


}
