package net.ugi.sculk_depths.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.KryslumEnrichedSoilBLock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Mixin(CropBlock.class)
public abstract class CropBlockMixin {

    @ModifyReturnValue(
            method = "canPlantOnTop",
            at = @At("RETURN")
    )
    private boolean allowOnTopOfKryslumEnrichedSoil(boolean original, @Local(ordinal = 0) BlockState floor) {
        return original || floor.getBlock() instanceof KryslumEnrichedSoilBLock;
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



