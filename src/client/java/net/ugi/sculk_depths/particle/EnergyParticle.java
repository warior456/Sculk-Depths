package net.ugi.sculk_depths.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class EnergyParticle
        extends AscendingParticle {



    protected EnergyParticle(ClientLevel world, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteProvider sprite, float colorMultiplier, int baseMaxAge, float gravity, boolean hasPhysics) {
        super(world, x, y, z, randomVelocityXMultiplier, randomVelocityYMultiplier, randomVelocityZMultiplier, velocityX, velocityY, velocityZ, scaleMultiplier, sprite, colorMultiplier, baseMaxAge, gravity, hasPhysics);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected int getBrightness(float color) {
        return 15728880; //look at original and it's methods to see why
    }

    @Environment(value = EnvType.CLIENT)
    public static class Factory
            implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider sprite;

        public Factory(SpriteProvider sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType dispatchOptionalValue, ClientLevel clientLevel, double x, double y, double z,double velocityX, double gravityY, double velocityZ) {
            EnergyParticle particleFromItem = new EnergyParticle(clientLevel, x, y, z, 0.05f, 0.05f, 0.05f, velocityX, 0, velocityZ, 0.18f, this.sprite, 1f, 200, - (float)gravityY, false);
            particleFromItem.setColor(1,1,1);
            particleFromItem.lifetime = Mth.nextBetween(clientLevel.random, 200, 1000);
            return particleFromItem;
        }

    }
}