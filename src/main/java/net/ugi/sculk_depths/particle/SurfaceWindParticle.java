package net.ugi.sculk_depths.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;

public class SurfaceWindParticle extends AscendingParticle {


    protected SurfaceWindParticle(ClientWorld world, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteProvider spriteProvider, float colorMultiplier, int baseMaxAge, float gravityStrength, boolean collidesWithWorld) {
        super(world, x, y, z, randomVelocityXMultiplier, randomVelocityYMultiplier, randomVelocityZMultiplier, velocityX, velocityY, velocityZ, scaleMultiplier, spriteProvider, colorMultiplier, baseMaxAge, gravityStrength, collidesWithWorld);
    }


    @Environment(value= EnvType.CLIENT)
    public static class Factory
            implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new SurfaceWindParticle(clientWorld, d, e, f, 0.1f, 0.01f, 0.1f, 0.4f, -0.05f, 0.4f, 1.0f, this.spriteProvider, 0.9f, 20, 0.002f, true);
        }


    }
}