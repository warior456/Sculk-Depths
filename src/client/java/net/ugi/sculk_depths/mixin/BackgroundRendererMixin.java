package net.ugi.sculk_depths.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FogType;
import net.neoforged.fml.ModList;
import net.ugi.sculk_depths.tags.ModTags;
import net.ugi.sculk_depths.world.biome.ModBiomes;
import net.ugi.sculk_depths.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class BackgroundRendererMixin {
    private static int spectatorMultiplier = 1;

    @Inject(at = @At("TAIL"), method = "setupFog")
    private static void afterSetupFog(Camera mainCamera, FogRenderer.FogMode mode, float chunkRadius, boolean thickFog, float tickDelta, CallbackInfo ci) {
        FogType cameraSubmersionType = mainCamera.getFluidInCamera();
        Entity entity = mainCamera.getEntity();
        boolean mobEffect = ((entity instanceof LivingEntity living)
                && (living.hasEffect(MobEffects.BLINDNESS)
                || living.hasEffect(MobEffects.DARKNESS)));

        if (entity.isSpectator()) {
            spectatorMultiplier = 2;
        } else {
            spectatorMultiplier = 1;
        }

        if (ModList.get().isLoaded("distanthorizons")) { // dh compat
            if (RenderSystem.getShaderFogStart() == 4.20694194E14F) return;
        }

        if (cameraSubmersionType == FogType.WATER) {
            if (entity.isEyeInFluid(ModTags.Fluids.KRYSLUM)) { // fluid fog
                overrideWaterToKryslum(chunkRadius, entity);
            }
        } else if (cameraSubmersionType == FogType.NONE) {
            if (entity.level().dimensionTypeRegistration().is(ModDimensions.SCULK_DEPTHS_TYPE)) {
                BlockPos pos = entity.blockPosition();
                float y = pos.getY();
                float mul = 1;

                int countInfectedColumns = 0;
                int remainder = 0;
                int radius = 10;
                for (int i = pos.getX() - radius; i <= pos.getX() + radius; i++) {
                    for (int j = pos.getZ() - radius; j <= pos.getZ() + radius; j++) {
                        BlockPos pos2 = new BlockPos(i, pos.getY(), j);
                        if (entity.level().getBiome(pos2).is(ModBiomes.INFECTED_COLUMNS)) {
                            countInfectedColumns += 1;
                        } else {
                            remainder += 1;
                        }
                    }
                }

                mul = (1f * remainder + (2f / 3f) * countInfectedColumns) / ((radius * 2 + 1) * (radius * 2 + 1));

                if (y <= -200f) y = -200f;
                float start = ((y + 256) * (chunkRadius / 426) * 0.1f) * mul * spectatorMultiplier;
                float end = (start * 10f) * mul * spectatorMultiplier;

                overrideFog(chunkRadius, start, end);
            }
        }
    }

    private static void overrideWaterToKryslum(float chunkRadius, Entity entity) {
        float fogStart = chunkRadius * -20.0f * 0.01f * spectatorMultiplier;
        float fogEnd = chunkRadius * 40.0f * 0.01f * spectatorMultiplier;
        if (entity instanceof LocalPlayer clientPlayerEntity) {
            Holder<Biome> biomeHolder = clientPlayerEntity.level().getBiome(clientPlayerEntity.blockPosition());
            if (biomeHolder.is(BiomeTags.HAS_CLOSER_WATER_FOG)) {
                fogEnd = chunkRadius * 0.01f * 0.01f;
            }
            fogEnd *= Math.max(0.25f, clientPlayerEntity.getWaterVision());
        }

        RenderSystem.setShaderFogStart(fogStart);
        RenderSystem.setShaderFogEnd(fogEnd);
    }

    private static void overrideFog(float chunkRadius, float start, float end) {
        RenderSystem.setShaderFogStart(start);
        RenderSystem.setShaderFogEnd(end);
    }
}
