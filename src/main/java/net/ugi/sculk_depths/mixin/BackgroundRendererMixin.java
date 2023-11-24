package net.ugi.sculk_depths.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.render.FogShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.listener.GameEventListener;
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
    @Inject(at = @At("TAIL"), method = "applyFog")
    private static void afterSetupFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        Entity entity = camera.getFocusedEntity();
        boolean mobEffect = (  (entity instanceof LivingEntity)
                && (  ((LivingEntity)entity).hasStatusEffect(StatusEffects.BLINDNESS)
                || ((LivingEntity)entity).hasStatusEffect(StatusEffects.DARKNESS)
        )
        );

        /*if(entity.isSpectator()){
            return;
        }*/
        if (cameraSubmersionType == CameraSubmersionType.WATER) {
            if(entity.isSubmergedIn(ModTags.Fluids.KRYSLUM)){ //fluid fog
                overrideWaterToKryslum(viewDistance, entity);
            }

        }  else if (cameraSubmersionType == CameraSubmersionType.NONE) {
            if(entity.getEntityWorld().getDimensionKey() == ModDimensions.SCULK_DEPTHS_TYPE) {

                BlockPos pos = entity.getBlockPos();
                float y_modifier = pos.getY()+256;// value between 0 and one when y is between -256 and 160
                System.out.println(y_modifier);

  /*              if(y_modifier < 0.3) y_modifier = 0.3f;
                if(y_modifier > 1.5) y_modifier = 1.5f;*/


                float start = viewDistance * 0.03F ; //viewDistance - MathHelper.clamp(viewDistance / 10.0F, 4.0F, 64.0F);
                float end = y_modifier * viewDistance/512 * 0.5f; //*x to stop wierd black border on the surface
                if(end <= 32) end = 32;//TODO make the last couple render distances more optimized to their limit
                System.out.println(viewDistance);
                System.out.println(start);
                System.out.println(end);
                System.out.println("//");
                overrideFog(viewDistance, start, end);
            } /*else if(entity.getEntityWorld().getBiome(pos).getKey().get() == ModBiomes.SCULK_CAVES|| entity.getEntityWorld().getBiome(pos).getKey().get() == ModBiomes.CEPHLERA_CAVES){
                float start = viewDistance * 0.05F;
                float end = (float) (Math.min(viewDistance, 192.0F) * 0.5);
                overrideFog(viewDistance, start, end);
            }*/
        }
    }
    /*
       if (cameraSubmersionType == CameraSubmersionType.LAVA) {
        } else if (cameraSubmersionType == CameraSubmersionType.POWDER_SNOW) {

        } else if (mobEffect) {

        } else*/
        /*else if (thickFog) {
            overrideNetherFog(viewDistance);
        }*/
    private static void overrideWaterToKryslum(float viewDistance, Entity entity) {
        float fogStart, fogEnd;
        fogStart = viewDistance * -20.0f * 0.01f;//waterstart
        fogEnd = viewDistance *  40.0f * 0.01f;//waterend
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