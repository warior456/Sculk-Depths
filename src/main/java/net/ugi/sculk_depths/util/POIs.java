package net.ugi.sculk_depths.util;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.poi.PointOfInterestType;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;

import java.util.Set;


public class POIs{


    public static void register(){
        PointOfInterestHelper.register(SculkDepths.identifier("quazarith_oscillator"),0,1, getStatesOfBlock(ModBlocks.QUAZARITH_OSCILLATOR));
    }

    public static Set<BlockState> getStatesOfBlock(Block block) {
        return ImmutableSet.copyOf(block.getStateManager().getStates());
    }

    public static final RegistryKey<PointOfInterestType> QUAZARITH_OSCILLATOR_POI = RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, SculkDepths.identifier("quazarith_oscillator"));
}
