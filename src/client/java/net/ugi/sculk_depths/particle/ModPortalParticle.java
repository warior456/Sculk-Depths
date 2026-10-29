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
import org.jetbrains.annotations.Nullable;

public class ModPortalParticle
        extends BaseAshSmokeParticle {



    protected ModPortalParticle(ClientLevel world, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteSet sprite, float colorMultiplier, int baseMaxAge, float gravity, boolean hasPhysics) {
        super(world, x, y, z, randomVelocityXMultiplier, randomVelocityYMultiplier, randomVelocityZMultiplier, velocityX, velocityY, velocityZ, scaleMultiplier, sprite, colorMultiplier, baseMaxAge, gravity, hasPhysics);
    }

    @Override
    protected int getBrightness(float color) {
        return 15728880; //look at original and it's methods to see why
    }

    @Environment(value = EnvType.CLIENT)
    public static class Factory
            implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType dispatchOptionalValue, ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {

            ModPortalParticle particleFromItem = new ModPortalParticle(clientLevel, x, y, z, 0.3f, 0.1f, 0.3f, velocityX, velocityY, velocityZ, 0.18f, this.sprite, 1f, 40, -0.001f, false);
            particleFromItem.setColor(1,1,1);
            particleFromItem.lifetime = Mth.nextBetween(clientLevel.random, 40, 200);
            //particle.gravityStrength = -0.01f;//not needed?
            return particleFromItem;
        }

    }
}