package net.ugi.sculk_depths.particle;

import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;


public class PenebriumSporeParticle
        extends BaseAshSmokeParticle {



    protected PenebriumSporeParticle(ClientLevel level, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteSet sprite, float colorMultiplier, int baseMaxAge, float gravity, boolean hasPhysics) {
        super(level, x, y, z, randomVelocityXMultiplier, randomVelocityYMultiplier, randomVelocityZMultiplier, velocityX, velocityY, velocityZ, scaleMultiplier, sprite, colorMultiplier, baseMaxAge, gravity, hasPhysics);
    }


        public static class Factory
            implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType dispatchOptionalValue, ClientLevel clientLevel, double x, double y, double z, double g, double h, double i) {

            PenebriumSporeParticle particleFromItem = new PenebriumSporeParticle(clientLevel, x, y, z, 0.3f, 0.1f, 0.3f, 0f, -0.01f, 0f, 0.18f, this.sprite, 1f, 500, 0.01f, true);
            particleFromItem.setColor(1,1,1);
            particleFromItem.lifetime = Mth.randomBetweenInclusive(clientLevel.random, 500, 1000);
            //particle.gravityStrength = 0.01f;
            return particleFromItem;
        }

    }
}