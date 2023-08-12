package net.ugi.sculk_depths.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import net.ugi.sculk_depths.SculkDepths;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class ExampleMixin {
    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        SculkDepths.LOGGER.info("This line is printed by Sculk Depths mixin!");
    }
}

