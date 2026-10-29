
package net.ugi.sculk_depths.world.gen;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.ugi.sculk_depths.SculkDepths;

public interface ModPlacementModifierType<P extends PlacementModifier> {

    PlacementModifierType<CountOnEveryLayerConstant> COUNT_ON_EVERY_LAYER_CONSTANT = register("count_on_every_layer_constant",  CountOnEveryLayerConstant.MAP_CODEC);


    private static <P extends PlacementModifier> PlacementModifierType<P> register(String id, MapCodec<P> codec) {
        return Registry.register(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, SculkDepths.id( id), () -> {
            return codec;
        });
    }

    public static void init() {
        PlacementModifierType<CountOnEveryLayerConstant> countOnEveryLayerConstant = COUNT_ON_EVERY_LAYER_CONSTANT;
    }
}