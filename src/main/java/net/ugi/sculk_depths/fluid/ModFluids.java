package net.ugi.sculk_depths.fluid;

import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.ugi.sculk_depths.SculkDepths;

import java.util.Iterator;

public class ModFluids {
    public static final FlowableFluid FLOWING_SCULK_FLUID = register("flowing_sculk_fluid", new SculkFluidFluid.Flowing());

    public static final FlowableFluid STILL_SCULK_FLUID = register("still_sculk_fluid", new SculkFluidFluid.Still());
    public static final FlowableFluid SCULK_FLUID = register("sculk_fluid", new SculkFluidFluid.Still());


    private static <T extends Fluid> T register(String id, T value) {
        return Registry.register(Registries.FLUID, id, value);
    }

    static {
        Iterator var0 = Registries.FLUID.iterator();

        while(var0.hasNext()) {
            Fluid fluid = (Fluid)var0.next();
            UnmodifiableIterator var2 = fluid.getStateManager().getStates().iterator();

            while(var2.hasNext()) {
                FluidState fluidState = (FluidState)var2.next();
                Fluid.STATE_IDS.add(fluidState);
            }
        }

    }
    public static void registerModFluids() {
        SculkDepths.LOGGER.info("Registering ModFluids for " + SculkDepths.MOD_ID);
    }
}