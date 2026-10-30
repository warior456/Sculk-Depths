package net.ugi.sculk_depths.particle;

import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class ModPortalAnimationParticle
        extends BaseAshSmokeParticle {



    protected ModPortalAnimationParticle(ClientLevel level, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteSet sprite, float colorMultiplier, int baseMaxAge, float gravity, boolean hasPhysics) {
        super(level, x, y, z, randomVelocityXMultiplier, randomVelocityYMultiplier, randomVelocityZMultiplier, velocityX, velocityY, velocityZ, scaleMultiplier, sprite, colorMultiplier, baseMaxAge, gravity, hasPhysics);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880; //look at original and it's methods to see why
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

            ModPortalParticle particleFromItem = new ModPortalParticle(clientLevel, x, y, z, 0.1f, 0.1f, 0.1f, velocityX, velocityY, velocityZ, 0.18f, this.sprite, 1f, 1, -0.003f, false);
            particleFromItem.setColor(1,1,1);
            particleFromItem.setLifetime(Mth.randomBetweenInclusive(clientLevel.random, 1, 60));
            //particle.gravityStrength = -0.01f;//not needed?
            return particleFromItem;
        }

    }
}