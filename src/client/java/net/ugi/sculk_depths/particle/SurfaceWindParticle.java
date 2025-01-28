package net.ugi.sculk_depths.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AscendingParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.ugi.sculk_depths.sound.ConditionalSoundPlayerClient;
import org.jetbrains.annotations.Nullable;

public class SurfaceWindParticle extends AscendingParticle {


    protected SurfaceWindParticle(ClientWorld world, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteProvider spriteProvider, float colorMultiplier, int baseMaxAge, float gravityStrength, boolean collidesWithWorld) {
        super(world, x, y, z, randomVelocityXMultiplier, randomVelocityYMultiplier, randomVelocityZMultiplier, velocityX, velocityY, velocityZ, scaleMultiplier, spriteProvider, colorMultiplier, baseMaxAge, gravityStrength, collidesWithWorld);
    }


    @Environment(value = EnvType.CLIENT)
    public static class Factory
            implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType parameters, ClientWorld clientWorld, double x, double y, double z, double vel_x, double h, double vel_z) {
            BlockPos pos = new BlockPos((int) x, (int) y, (int) z);
            // Check if the top Y position is greater than y, if so return null to spawn no particle
            if (clientWorld.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos).getY() > y) {
                return null;
            }

            SurfaceWindParticle particle = new SurfaceWindParticle(clientWorld, x, y, z, 0.5f, 0.05f, 0.5f, ConditionalSoundPlayerClient.getWindX(), -0.05f, ConditionalSoundPlayerClient.getWindZ(), 1.0f, this.spriteProvider, 0.9f, 20, 0.002f, true);
            return particle;
        }


    }
}