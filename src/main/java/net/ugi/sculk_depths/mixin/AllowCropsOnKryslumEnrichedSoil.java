package net.ugi.sculk_depths.mixin;

import net.minecraft.block.BlockState;

import net.minecraft.block.CropBlock;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.ugi.sculk_depths.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public abstract class AllowCropsOnKryslumEnrichedSoil {
    @Shadow protected abstract boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos);

    @Inject(at = @At("HEAD"), method = "canPlantOnTop", cancellable = true)
    protected void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if(floor.isOf(ModBlocks.KRYSLUM_ENRICHED_SOIL)) cir.setReturnValue(true);
    }

}


