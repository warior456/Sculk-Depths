package net.ugi.sculk_depths.util;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;

import java.util.Set;

public class POIs {
    public static final ResourceKey<PoiType> QUAZARITH_OSCILLATOR_POI = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, SculkDepths.id("quazarith_oscillator"));

    public static void register() {
        Registry.register(BuiltInRegistries.POINT_OF_INTEREST_TYPE, QUAZARITH_OSCILLATOR_POI,
                new PoiType(getBlockStates(ModBlocks.QUAZARITH_OSCILLATOR), 0, 1));
    }

    public static Set<BlockState> getBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }
}
