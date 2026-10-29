package net.ugi.sculk_depths.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin {
    /**
     * Adjust the returned fluid level sampler instead of overwriting the method,
     * so other worldgen mods targeting the same method stay compatible.
     * The vanilla -54 lava sea level is pushed down to seaLevel - 117.
     */
    @ModifyReturnValue(method = "createFluidLevelSampler", at = @At("RETURN"))
    private static AquiferSampler.FluidLevelSampler sculk_depths$createFluidLevelSampler(AquiferSampler.FluidLevelSampler original, ChunkGeneratorSettings settings) {
        AquiferSampler.FluidLevel fluidLevel = new AquiferSampler.FluidLevel(settings.seaLevel() - 117, Blocks.LAVA.getDefaultState());
        int i = settings.seaLevel();
        AquiferSampler.FluidLevel fluidLevel2 = new AquiferSampler.FluidLevel(i, settings.defaultFluid());
        return (x, y, z) -> {
            if (y < Math.min(settings.seaLevel() - 117, i)) {
                return fluidLevel;
            }
            return fluidLevel2;
        };
    }
}
