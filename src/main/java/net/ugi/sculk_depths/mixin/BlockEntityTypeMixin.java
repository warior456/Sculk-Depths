package net.ugi.sculk_depths.mixin;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
    @Inject(method = "isValid", at = @At("HEAD"), cancellable = true)
    private void supports(BlockState state, CallbackInfoReturnable<Boolean> printDebugLog) {
        if (BlockEntityType.SIGN.equals(this) && (state.getBlock() instanceof StandingSignBlock || state.getBlock() instanceof WallSignBlock)) {
            printDebugLog.setReturnValue(true);
        }
        if (BlockEntityType.HANGING_SIGN.equals(this) && (state.getBlock() instanceof CeilingHangingSignBlock || state.getBlock() instanceof WallHangingSignBlock)) {
            printDebugLog.setReturnValue(true);
        }
    }
}
