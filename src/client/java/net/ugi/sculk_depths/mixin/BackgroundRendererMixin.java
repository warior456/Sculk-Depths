package net.ugi.sculk_depths.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.impl.resource.loader.FabricLifecycledResourceManager;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FogShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.event.listener.GameEventListener;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.fluid.ModFluids;
import net.ugi.sculk_depths.tags.ModTags;
import net.ugi.sculk_depths.world.biome.ModBiomes;
import net.ugi.sculk_depths.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EventListenerProxy;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    private static int spectatorMultiplier = 1;
    @Inject(at = @At("TAIL"), method = "applyFog")
    private static void afterSetupFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        Entity entity = camera.getFocusedEntity();
        boolean mobEffect = (  (entity instanceof LivingEntity)
                && (  ((LivingEntity)entity).hasStatusEffect(StatusEffects.BLINDNESS)
                || ((LivingEntity)entity).hasStatusEffect(StatusEffects.DARKNESS)
        )
        );
        if(entity.isSpectator()){
            spectatorMultiplier = 2;
        }

        if(FabricLoader.getInstance().isModLoaded("distanthorizons")){ //dh compat
            if(RenderSystem.getShaderFogStart() == 4.20694194E14F) return;
        }
        if (cameraSubmersionType == CameraSubmersionType.WATER) {
            if(entity.isSubmergedIn(ModTags.Fluids.KRYSLUM)){ //fluid fog
                overrideWaterToKryslum(viewDistance, entity);
            }
        }  else if (cameraSubmersionType == CameraSubmersionType.NONE) {
            if(entity.getEntityWorld().getDimensionEntry().getKey().get() == ModDimensions.SCULK_DEPTHS_TYPE) {
                BlockPos pos = entity.getBlockPos();
                float y = pos.getY();
                float mul = 1;

                int countInfectedColumns = 0;
                int remainder = 0;
                int radius = 10;
                for(int i = pos.getX() - radius;i < pos.getX() + radius + 1;i += 1){
                    for(int j = pos.getZ() - radius;j < pos.getZ() + radius + 1;j += 1){
                        BlockPos pos2 = new BlockPos(i,pos.getY(),j);
                        RegistryKey<Biome> biome = entity.getEntityWorld().getBiome(pos2).getKey().get();
                        if (biome.equals(ModBiomes.INFECTED_COLUMNS)) {
                            countInfectedColumns += 1;
                        }  else {
                            remainder += 1;
                        }
                    }
                }

                mul = (   1f * remainder                                                         // default           mul = 1
                        + 2/3f * countInfectedColumns                                            //Infected Columns   mul = 2/3
                ) / ((radius*2+1) * (radius*2+1));

                if(y <= -200f) y = -200f;
                float start = ((y+256) * (viewDistance/426) * ((float)1/10)) * mul * spectatorMultiplier;
                float end = (start * 10f ) * mul * spectatorMultiplier;


                overrideFog(viewDistance, start, end);
            }
        }
    }

    private static void overrideWaterToKryslum(float viewDistance, Entity entity) {
        float fogStart, fogEnd;
        fogStart = viewDistance * -20.0f * 0.01f * spectatorMultiplier;//waterstart
        fogEnd = viewDistance *  40.0f * 0.01f * spectatorMultiplier;//waterend
        if (entity instanceof ClientPlayerEntity) {
            ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) entity;
            RegistryEntry<Biome> biomeHolder = clientPlayerEntity.getWorld().getBiome(clientPlayerEntity.getBlockPos());
            if (biomeHolder.isIn(BiomeTags.HAS_CLOSER_WATER_FOG)) {
                fogEnd = viewDistance * 0.01f * 0.01f;//waterendswamp
            }
            fogEnd *= Math.max(0.25f, clientPlayerEntity.getUnderwaterVisibility());
        }
        //RenderSystem.setShaderFogColor(0,51,0);

        RenderSystem.setShaderFogStart(fogStart);
        RenderSystem.setShaderFogEnd(fogEnd);
    }

    private static void overrideFog(float viewDistance, float start, float end) {
        float fogStart, fogEnd;
        fogStart = start;
        fogEnd = end;
        RenderSystem.setShaderFogStart(fogStart);
        RenderSystem.setShaderFogEnd(fogEnd);
    }
}