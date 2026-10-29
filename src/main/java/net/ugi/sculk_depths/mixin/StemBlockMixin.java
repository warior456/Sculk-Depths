package net.ugi.sculk_depths.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.StemBlock;
import net.ugi.sculk_depths.block.custom.KryslumEnrichedSoilBLock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(StemBlock.class)
public abstract class StemBlockMixin {

    @ModifyReturnValue(
            method = "canPlantOnTop",
            forIndex = @At("RETURN")
    )
    private boolean allowOnTopOfKryslumEnrichedSoil(boolean source, @Local(ordinal = 0) BlockState pointingUp) {
        return source || pointingUp.getBlock() instanceof KryslumEnrichedSoilBLock;
    }
}