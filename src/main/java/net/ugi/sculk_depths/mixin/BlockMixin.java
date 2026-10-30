package net.ugi.sculk_depths.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.ugi.sculk_depths.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "isExceptionForConnection", at = @At("HEAD"), cancellable = true)
    private static void addZygrinLightBlockToCannotConnect(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(ModBlocks.ZYGRIN_LIGHT)) cir.setReturnValue(true);
    }
}
