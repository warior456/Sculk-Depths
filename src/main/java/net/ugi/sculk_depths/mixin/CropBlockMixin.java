package net.ugi.sculk_depths.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.KryslumEnrichedSoilBLock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin {

    @ModifyReturnValue(
            method = "mayPlaceOn",
            at = @At("RETURN")
    )
    private boolean allowOnTopOfKryslumEnrichedSoil(boolean source, @Local(ordinal = 0) BlockState pointingUp) {
        return source || pointingUp.getBlock() instanceof KryslumEnrichedSoilBLock;
    }

    @ModifyVariable(
            method = "getGrowthSpeed",
            at = @At(
                    value = "STORE"
            ),
            ordinal = 0
    )
    private static float changeMoistureOnKryslumEnrichedSoil(float originalValue, BlockState state, BlockGetter level, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        if (belowState.is(ModBlocks.KRYSLUM_ENRICHED_SOIL)) {
            return 7.0f;
        }
        return originalValue;
    }
}
