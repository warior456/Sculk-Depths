package net.ugi.sculk_depths.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AscendingParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;

public class SurfaceWindParticle extends AscendingParticle {


    protected SurfaceWindParticle(ClientWorld world, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteProvider spriteProvider, float colorMultiplier, int baseMaxAge, float gravityStrength, boolean collidesWithWorld) {
        super(world, x, y, z, randomVelocityXMultiplier, randomVelocityYMultiplier, randomVelocityZMultiplier, velocityX, velocityY, velocityZ, scaleMultiplier, spriteProvider, colorMultiplier, baseMaxAge, gravityStrength, collidesWithWorld);
    }


    @Environment(value = EnvType.CLIENT)
    public static class Factory
            implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double g, double h, double i) {
            BlockPos pos = new BlockPos((int) x, (int) y, (int) z);
            // Check if the top Y position is greater than y, if so return null to spawn no particle
            if (clientWorld.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos).getY() > y) {
                return null;
            }

            SurfaceWindParticle particle = new SurfaceWindParticle(clientWorld, x, y, z, 0.5f, 0.05f, 0.5f, 0.4f, -0.05f, 0.4f, 1.0f, this.spriteProvider, 0.9f, 20, 0.002f, true);
            return particle;
        }


    }
}