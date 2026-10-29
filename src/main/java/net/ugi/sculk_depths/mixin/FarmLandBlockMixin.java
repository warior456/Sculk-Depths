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
            method = "isWaterNearby",
            forIndex = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z")
    )
    private static boolean kryslumWetsFarmland(boolean source, @Local(ordinal = 0) LevelReader world, @Local(ordinal = 1) BlockPos blockPos) {
        return source || world.getBlockState(blockPos).isOf(ModBlocks.KRYSLUM_ENRICHED_SOIL);
    }


}
