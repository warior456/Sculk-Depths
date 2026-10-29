package net.ugi.sculk_depths.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.block.custom.KryslumEnrichedSoilBLock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Mixin(CropBlock.class)
public abstract class CropBlockMixin {

    @ModifyReturnValue(
            method = "canPlantOnTop",
            forIndex = @At("RETURN")
    )
    private boolean allowOnTopOfKryslumEnrichedSoil(boolean source, @Local(ordinal = 0) BlockState pointingUp) {
        return source || pointingUp.getBlock() instanceof KryslumEnrichedSoilBLock;
    }


    @ModifyVariable(
            method = "getAvailableMoisture",
            forIndex = @At(
                    value = "STORE"
            ),
            ordinal = 0
    )
    private static float changeMoistureOnKryslumEnrichedSoil(float originalValue, Block block, BlockGetter world, BlockPos pos) {
        BlockState block = world.getBlockState(pos.below());
        if (block.isOf(ModBlocks.KRYSLUM_ENRICHED_SOIL)) {
            return 7.0f;
        }
        return originalValue;
    }
}



