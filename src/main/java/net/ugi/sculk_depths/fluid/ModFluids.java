package net.ugi.sculk_depths.fluid;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModFluids {
    public static final FlowableFluid SCULK_FLUID_STILL = register("sculk_fluid_still", new SculkFluid.Still());
    public static final FlowableFluid SCULK_FLUID_FLOWING = register("sculk_fluid_flowing", new SculkFluid.Flowing());

    private static FlowableFluid register(String name, FlowableFluid flowableFluid){
        return Registry.register(Registries.FLUID, new Identifier(SculkDepths.MOD_ID, name), flowableFluid);
    }
}
