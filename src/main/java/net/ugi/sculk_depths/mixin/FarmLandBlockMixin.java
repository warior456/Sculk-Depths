package net.ugi.sculk_depths.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.ugi.sculk_depths.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FarmBlock.class)
public abstract class FarmLandBlockMixin {

    @ModifyExpressionValue(
            method = "isNearWater",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;canBeHydrated(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/core/BlockPos;)Z")
    )
    private static boolean kryslumWetsFarmland(boolean source, @Local(ordinal = 0) LevelReader level, @Local(ordinal = 1) BlockPos blockPos) {
        return source || level.getBlockState(blockPos).is(ModBlocks.KRYSLUM_ENRICHED_SOIL);
    }
}
