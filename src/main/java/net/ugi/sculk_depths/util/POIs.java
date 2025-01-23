package net.ugi.sculk_depths.util;

import com.google.common.collect.ImmutableSet;
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


    public static PointOfInterestType oscillatorPointOfInterestType = new PointOfInterestType(getStatesOfBlock(ModBlocks.QUAZARITH_OSCILLATOR), 0, 1);
    public static void register(){
        Registry.register(Registries.POINT_OF_INTEREST_TYPE, "quazarith_oscillator", oscillatorPointOfInterestType);
    }



    public static Set<BlockState> getStatesOfBlock(Block block) {
        return ImmutableSet.copyOf(block.getStateManager().getStates());
    }
    private static RegistryKey<PointOfInterestType> of(String id) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, SculkDepths.identifier(id));
    }

    public static final RegistryKey<PointOfInterestType> QUAZARITH_OSCILLATOR_POI = of("quazarith_oscillator");
}
