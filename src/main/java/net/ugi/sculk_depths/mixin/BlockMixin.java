package net.ugi.sculk_depths.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.ugi.sculk_depths.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "cannotConnect", at = @At("HEAD"), cancellable = true)
    private static void addZygrinLightBlockToCannotConnect(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.isOf(ModBlocks.ZYGRIN_LIGHT)) cir.setReturnValue(true);
    }
}
