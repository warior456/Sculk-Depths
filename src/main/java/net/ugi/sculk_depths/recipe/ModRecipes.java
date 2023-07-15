package net.ugi.sculk_depths.recipe;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModRecipes {
    public static void register() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(SculkDepths.MOD_ID, FlumRockCauldronRecipe.Serializer.ID),
                FlumRockCauldronRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(SculkDepths.MOD_ID, FlumRockCauldronRecipe.Type.ID),
                FlumRockCauldronRecipe.Type.INSTANCE);
    }
}
