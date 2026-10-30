package net.ugi.sculk_depths.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NoiseBasedChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin {
    /**
     * Adjust the returned fluid level sampler instead of overwriting the method,
     * so other worldgen mods targeting the same method stay compatible.
     * The vanilla -54 lava sea level is pushed down to seaLevel - 117.
     */
    @ModifyReturnValue(method = "createFluidPicker", at = @At("RETURN"))
    private static Aquifer.FluidPicker sculk_depths$createFluidLevelSampler(Aquifer.FluidPicker source, NoiseGeneratorSettings settings) {
        Aquifer.FluidStatus fluidLevel = new Aquifer.FluidStatus(settings.seaLevel() - 117, Blocks.LAVA.defaultBlockState());
        int i = settings.seaLevel();
        Aquifer.FluidStatus fluidLevel2 = new Aquifer.FluidStatus(i, settings.defaultFluid());
        return (x, y, z) -> {
            if (y < Math.min(settings.seaLevel() - 117, i)) {
                return fluidLevel;
            }
            return fluidLevel2;
        };
    }
}
