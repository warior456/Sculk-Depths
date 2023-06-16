package net.ugi.sculk_depths.fluid;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModFluids {
    public static final FlowableFluid KRYSLUM_STILL = register("kryslum_still", new KryslumFluid.Still());
    public static final FlowableFluid KRYSLUM_FLOWING = register("kryslum_flow", new KryslumFluid.Flowing());


    private static FlowableFluid register(String name, FlowableFluid flowableFluid) {
        return Registry.register(Registries.FLUID, new Identifier(SculkDepths.MOD_ID, name), flowableFluid);
    }
}
