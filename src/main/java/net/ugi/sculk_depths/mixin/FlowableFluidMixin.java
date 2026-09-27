package net.ugi.sculk_depths.mixin;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.ugi.sculk_depths.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FlowableFluid.class)
public abstract class FlowableFluidMixin {

    @ModifyReturnValue(
            method = "canFill",
            at = @At("RETURN")
    )
    private boolean allowOnTopOfKryslumEnrichedSoil(boolean original, @Local(ordinal = 0) BlockState state) {
        if (state.isOf(ModBlocks.SCULK_DEPTHS_PORTAL)) return false;
        else return original;
    }
}
