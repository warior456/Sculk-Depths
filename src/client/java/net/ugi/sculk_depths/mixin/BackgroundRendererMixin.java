package net.ugi.sculk_depths.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.ugi.sculk_depths.tags.ModTags;
import net.ugi.sculk_depths.world.biome.ModBiomes;
import net.ugi.sculk_depths.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Unique private static int sculk_depths$spectatorMultiplier = 1;

    @Inject(at = @At("TAIL"), method = "applyFog")
    private static void afterSetupFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        Entity entity = camera.getFocusedEntity();
        boolean mobEffect = entity instanceof LivingEntity livingEntity &&
                (livingEntity.hasStatusEffect(StatusEffects.BLINDNESS) || livingEntity.hasStatusEffect(StatusEffects.DARKNESS));
        if (entity.isSpectator()) {
            sculk_depths$spectatorMultiplier = 2;
        } else {
            sculk_depths$spectatorMultiplier = 1;
        }

        if (FabricLoader.getInstance().isModLoaded("distanthorizons")) { //dh compat
            if (RenderSystem.getShaderFogStart() == 4.20694194E14F) return;
        }
        if (cameraSubmersionType == CameraSubmersionType.WATER) {
            if (entity.isSubmergedIn(ModTags.Fluids.KRYSLUM)) { //fluid fog
                sculk_depths$overrideWaterToKryslum(viewDistance, entity);
            }
        } else if (cameraSubmersionType == CameraSubmersionType.NONE) {
            if (entity.getEntityWorld().getDimensionEntry().getKey().get() == ModDimensions.SCULK_DEPTHS_TYPE) {
                BlockPos pos = entity.getBlockPos();
                float y = pos.getY();

                int countInfectedColumns = 0;
                int remainder = 0;
                int radius = 10;
                for (int i = pos.getX() - radius; i < pos.getX() + radius + 1; i += 1) {
                    for (int j = pos.getZ() - radius; j < pos.getZ() + radius + 1; j += 1) {
                        BlockPos pos2 = new BlockPos(i, pos.getY(), j);
                        RegistryKey<Biome> biome = entity.getEntityWorld().getBiome(pos2).getKey().get();
                        if (biome.equals(ModBiomes.INFECTED_COLUMNS)) {
                            countInfectedColumns += 1;
                        }  else {
                            remainder += 1;
                        }
                    }
                }

                float mul = (1f * remainder                                                           // default           mul = 1
                        + 2 / 3f * countInfectedColumns                                            //Infected Columns   mul = 2/3
                ) / ((radius * 2 + 1) * (radius * 2 + 1));

                if (y <= -200f) y = -200f;
                float start = ((y + 256) * (viewDistance / 426) * ((float) 1 / 10)) * mul * sculk_depths$spectatorMultiplier;
                float end = (start * 10f) * mul * sculk_depths$spectatorMultiplier;


                sculk_depths$overrideFog(viewDistance, start, end);
            }
        }
    }

    @Unique
    private static void sculk_depths$overrideWaterToKryslum(float viewDistance, Entity entity) {
        float fogStart = viewDistance * -20.0f * 0.01f * sculk_depths$spectatorMultiplier; // waterstart
        float fogEnd = viewDistance * 40.0f * 0.01f * sculk_depths$spectatorMultiplier; // waterend
        if (entity instanceof ClientPlayerEntity clientPlayerEntity) {
            RegistryEntry<Biome> biomeHolder = clientPlayerEntity.getWorld().getBiome(clientPlayerEntity.getBlockPos());
            if (biomeHolder.isIn(BiomeTags.HAS_CLOSER_WATER_FOG)) {
                fogEnd = viewDistance * 0.01f * 0.01f; // waterendswamp
            }
            fogEnd *= Math.max(0.25f, clientPlayerEntity.getUnderwaterVisibility());
        }
        //RenderSystem.setShaderFogColor(0,51,0);

        RenderSystem.setShaderFogStart(fogStart);
        RenderSystem.setShaderFogEnd(fogEnd);
    }

    @Unique
    private static void sculk_depths$overrideFog(float viewDistance, float start, float end) {
        RenderSystem.setShaderFogStart(start);
        RenderSystem.setShaderFogEnd(end);
    }
}