package net.ugi.sculk_depths.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class FlumRockCauldronRecipe implements Recipe<Inventory> {

    private final DefaultedList<ItemStack> recipeItems;
    private final ItemStack outputStack;
    private final Identifier id;


    public FlumRockCauldronRecipe(DefaultedList<ItemStack> recipeItems, ItemStack outputStack, Identifier id) {
        this.recipeItems = recipeItems;
        this.outputStack = outputStack;
        this.id = id;
    }



    @Override
    public boolean matches(Inventory inventory, World world) {
        if(recipeItems.get(0).getItem() == inventory.getStack(0).getItem() && recipeItems.get(0).getCount() <= inventory.getStack(0).getCount()){
            inventory.removeStack(0,recipeItems.get(0).getCount());
            return true;
        }
        return false;
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return this.getOutput(DynamicRegistryManager.EMPTY).copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return outputStack;
    }

    public DefaultedList<ItemStack> getRecipeItems() {
        return recipeItems;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FlumrockCauldronRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<FlumRockCauldronRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "flumrock_cauldron_recipe";
    }


}
