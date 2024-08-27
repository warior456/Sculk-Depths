package net.ugi.sculk_depths.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class EnergyParticle
        extends AscendingParticle {



    protected EnergyParticle(ClientWorld world, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteProvider spriteProvider, float colorMultiplier, int baseMaxAge, float gravityStrength, boolean collidesWithWorld) {
        super(world, x, y, z, randomVelocityXMultiplier, randomVelocityYMultiplier, randomVelocityZMultiplier, velocityX, velocityY, velocityZ, scaleMultiplier, spriteProvider, colorMultiplier, baseMaxAge, gravityStrength, collidesWithWorld);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected int getBrightness(float tint) {
        return 15728880; //look at original and it's methods to see why
    }

    @Environment(value = EnvType.CLIENT)
    public static class Factory
            implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType parameters, ClientWorld clientWorld, double x, double y, double z,double velocityX, double velocityY, double velocityZ) {
            EnergyParticle particle = new EnergyParticle(clientWorld, x, y, z, 0.05f, 0.05f, 0.05f, velocityX, velocityY, velocityZ, 0.18f, this.spriteProvider, 1f, 10, -0.001f, false);
            particle.maxAge = MathHelper.nextBetween(clientWorld.random, 500, 1000);
            return particle;
        }

    }
}