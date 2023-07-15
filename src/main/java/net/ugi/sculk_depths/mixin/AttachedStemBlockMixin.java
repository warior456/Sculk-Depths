package net.ugi.sculk_depths.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.BlockState;
import net.ugi.sculk_depths.block.custom.KryslumEnrichedSoilBLock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(AttachedStemBlock.class)
public abstract class AttachedStemBlockMixin {

    @ModifyReturnValue(
            method = "canPlantOnTop",
            at = @At("RETURN")
    )
    private boolean allowOnTopOfKryslumEnrichedSoil(boolean original, @Local(ordinal = 0) BlockState floor) {
        return original || floor.getBlock() instanceof KryslumEnrichedSoilBLock;
    }
}