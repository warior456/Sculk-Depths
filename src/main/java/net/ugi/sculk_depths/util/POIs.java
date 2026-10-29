package net.ugi.sculk_depths.util;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;

import java.util.Set;


public class POIs{


    public static void register(){
        PointOfInterestHelper.register(SculkDepths.id("quazarith_oscillator"),0,1, getBlockStates(ModBlocks.QUAZARITH_OSCILLATOR));
    }

    public static Set<BlockState> getBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getStates());
    }

    public static final ResourceKey<PoiType> QUAZARITH_OSCILLATOR_POI = ResourceKey.of(Registries.POINT_OF_INTEREST_TYPE, SculkDepths.id("quazarith_oscillator"));
}
