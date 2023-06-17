package net.ugi.sculk_depths.mixin;

import net.minecraft.block.*;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.ugi.sculk_depths.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(CropBlock.class)
public abstract class CropBlockMixin {


    @Inject(at = @At("RETURN"), method = "canPlantOnTop", cancellable = true)
    protected void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() || floor.getBlock() instanceof FarmlandBlock);
    }


    @ModifyVariable(
            method = "getAvailableMoisture",
            at = @At(
                    value = "STORE"
            ),
            ordinal = 0
    )
    private static float changeMoistureOnKryslumEnrichedSoil(float originalValue, Block block, BlockView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.isOf(ModBlocks.KRYSLUM_ENRICHED_SOIL)) {
            return 7.0f;

        }
        return originalValue;
    }
}



