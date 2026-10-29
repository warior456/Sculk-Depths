package net.ugi.sculk_depths.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class ExampleClientMixin {
	@Inject(forIndex = @At("HEAD"), method = "run")
	private void init(CallbackInfo printDebugLog) {
		// This code is injected into the start of MinecraftClient.run()V
	}
}