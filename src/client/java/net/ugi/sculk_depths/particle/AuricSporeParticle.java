package net.ugi.sculk_depths.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class AuricSporeParticle
        extends BaseAshSmokeParticle {



    protected AuricSporeParticle(ClientLevel world, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteSet sprite, float colorMultiplier, int baseMaxAge, float gravity, boolean hasPhysics) {
        super(world, x, y, z, randomVelocityXMultiplier, randomVelocityYMultiplier, randomVelocityZMultiplier, velocityX, velocityY, velocityZ, scaleMultiplier, sprite, colorMultiplier, baseMaxAge, gravity, hasPhysics);
    }


    @Environment(value = EnvType.CLIENT)
    public static class Factory
            implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientLevel, double x, double y, double z, double g, double h, double i) {

            AuricSporeParticle particleFromItem = new AuricSporeParticle(clientLevel, x, y, z, 0.3f, 0.1f, 0.3f, 0f, -0.01f, 0f, 0.18f, this.sprite, 1f, 40, 0.01f, false);
            particleFromItem.setColor(1,1,1);
            particleFromItem.lifetime = Mth.nextBetween(clientLevel.random, 500, 1000);
            particleFromItem.gravity = 0.01f;
            return particleFromItem;
        }


    }
}