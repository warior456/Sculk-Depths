package net.ugi.sculk_depths.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

import java.util.Optional;

public class PenebriumSporeParticle
        extends AscendingParticle {



    protected PenebriumSporeParticle(ClientWorld world, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteProvider spriteProvider, float colorMultiplier, int baseMaxAge, float gravityStrength, boolean collidesWithWorld) {
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

            PenebriumSporeParticle particle = new PenebriumSporeParticle(clientWorld, x, y, z, 0.3f, 0.1f, 0.3f, 0f, -0.01f, 0f, 0.18f, this.spriteProvider, 1f, 40, 0.01f, false);
            particle.setColor(1,1,1);
            particle.maxAge = MathHelper.nextBetween(clientWorld.random, 500, 1000);
            particle.gravityStrength = 0.01f;
            return particle;
        }


    }
}