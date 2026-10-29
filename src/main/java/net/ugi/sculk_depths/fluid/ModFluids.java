package net.ugi.sculk_depths.fluid;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.ugi.sculk_depths.SculkDepths;

public class ModFluids {
    public static final FlowingFluid KRYSLUM_STILL = register("kryslum_still", new KryslumFluid.Still());
    public static final FlowingFluid KRYSLUM_FLOWING = register("kryslum_flow", new KryslumFluid.Flowing());


    private static FlowingFluid register(String name, FlowingFluid flowableFluid) {
        return Registry.register(BuiltInRegistries.FLUID, SculkDepths.id( name), flowableFluid);
    }
}
