package net.ugi.sculk_depths.mixin;

import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;


@Mixin({NoiseChunkGenerator.class})
public class NoiseChunkGeneratorMixin {
    /**
     * @author Andrew6rant (Andrew Grant)
     * @reason Remove the hardcoded -54 lava sea level
     */
    @Overwrite
    private static AquiferSampler.FluidLevelSampler createFluidLevelSampler(ChunkGeneratorSettings settings) {
        return (x, y, z) -> new AquiferSampler.FluidLevel(settings.seaLevel(), settings.defaultFluid());
    }
}