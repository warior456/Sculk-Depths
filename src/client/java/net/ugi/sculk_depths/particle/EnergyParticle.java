package net.ugi.sculk_depths.particle;

import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class EnergyParticle extends BaseAshSmokeParticle {

    protected EnergyParticle(ClientLevel level, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteSet sprite, float colorMultiplier, int baseMaxAge, float gravity, boolean hasPhysics) {
        super(level, x, y, z, randomVelocityXMultiplier, randomVelocityYMultiplier, randomVelocityZMultiplier, velocityX, velocityY, velocityZ, scaleMultiplier, sprite, colorMultiplier, baseMaxAge, gravity, hasPhysics);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880; // look at original and its methods to see why
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel clientLevel, double x, double y, double z, double velocityX, double gravityY, double velocityZ) {
            EnergyParticle particleFromItem = new EnergyParticle(clientLevel, x, y, z, 0.05f, 0.05f, 0.05f, velocityX, 0, velocityZ, 0.18f, this.sprite, 1f, 200, -(float) gravityY, false);
            particleFromItem.setColor(1, 1, 1);
            particleFromItem.lifetime = Mth.randomBetweenInclusive(clientLevel.random, 200, 1000);
            return particleFromItem;
        }
    }
}
