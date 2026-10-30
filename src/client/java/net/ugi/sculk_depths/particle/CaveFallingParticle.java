package net.ugi.sculk_depths.particle;

import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class CaveFallingParticle extends BaseAshSmokeParticle {


    protected CaveFallingParticle(ClientLevel level, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteSet sprite, float colorMultiplier, int baseMaxAge, float gravity, boolean hasPhysics) {
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
        public Particle createParticle(SimpleParticleType dispatchOptionalValue, ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            CaveFallingParticle particleFromItem = new CaveFallingParticle(clientLevel, x, y, z, 0.4f, 0.1f, 0.4f, 0f, -0.05f, 0f, 1.0f, this.sprite, 0.12f, 40, 0.005f, false);
            return particleFromItem;
        }
    }
}