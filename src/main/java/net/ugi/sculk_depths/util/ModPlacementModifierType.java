
package net.ugi.sculk_depths.util;

import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;
import net.ugi.sculk_depths.SculkDepths;

  public interface ModPlacementModifierType<P extends PlacementModifier> {

      PlacementModifierType<CountOnEveryLayerConstant> COUNT_ON_EVERY_LAYER_CONSTANT = register( "count_on_every_layer_constant", CountOnEveryLayerConstant.MODIFIER_CODEC);


      private static <P extends PlacementModifier> PlacementModifierType<P> register (String id, Codec<P> codec) {
          return Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, new Identifier(SculkDepths.MOD_ID, id), () -> codec);
      }

      public static void init () {
          PlacementModifierType<CountOnEveryLayerConstant> countOnEveryLayerConstant = COUNT_ON_EVERY_LAYER_CONSTANT;
      }
}