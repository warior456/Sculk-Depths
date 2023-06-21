package net.ugi.sculk_depths.mixin;


import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.ugi.sculk_depths.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FarmlandBlock.class)


public abstract class FarmLandBlockMixin {


    @ModifyExpressionValue(
            method = "isWaterNearby",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z")
    )
    private static boolean addBlockMoistTagForFarmLand(boolean original, @Local(ordinal = 0) WorldView world, @Local(ordinal = 1) BlockPos blockPos ) {
        return original || world.getBlockState(blockPos).isOf(ModBlocks.KRYSLUM_ENRICHED_SOIL);
    }


}
