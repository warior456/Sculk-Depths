package net.ugi.sculk_depths.fluid;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.ugi.sculk_depths.SculkDepths;

public class ModFluids {
    public static final FluidType KRYSLUM_FLUID_TYPE = registerType("kryslum", new FluidType(FluidType.Properties.create()));
    public static final FlowingFluid KRYSLUM_STILL = register("kryslum_still", new KryslumFluid.Still());
    public static final FlowingFluid KRYSLUM_FLOWING = register("kryslum_flow", new KryslumFluid.Flowing());

    public static void init() {}

    private static FluidType registerType(String name, FluidType fluidType) {
        return Registry.register(NeoForgeRegistries.FLUID_TYPES, SculkDepths.id(name), fluidType);
    }

    private static FlowingFluid register(String name, FlowingFluid flowableFluid) {
        return Registry.register(BuiltInRegistries.FLUID, SculkDepths.id(name), flowableFluid);
    }
}
