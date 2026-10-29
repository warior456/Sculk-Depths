package net.ugi.sculk_depths.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.ugi.sculk_depths.sound.ConditionalSoundPlayerClient;
import org.jetbrains.annotations.Nullable;

public class SurfaceWindParticle extends BaseAshSmokeParticle {


    protected SurfaceWindParticle(ClientLevel world, double x, double y, double z, float randomVelocityXMultiplier, float randomVelocityYMultiplier, float randomVelocityZMultiplier, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteSet sprite, float colorMultiplier, int baseMaxAge, float gravity, boolean hasPhysics) {
        super(world, x, y, z, randomVelocityXMultiplier, randomVelocityYMultiplier, randomVelocityZMultiplier, velocityX, velocityY, velocityZ, scaleMultiplier, sprite, colorMultiplier, baseMaxAge, gravity, hasPhysics);
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
        public Particle createParticle(SimpleParticleType dispatchOptionalValue, ClientLevel clientLevel, double x, double y, double z, double vel_x, double h, double vel_z) {
            BlockPos pos = new BlockPos((int) x, (int) y, (int) z);
            // Check if the top Y position is greater than y, if so return null to spawn no particle
            if (clientLevel.getTopPosition(Heightmap.Types.MOTION_BLOCKING, pos).getY() > y) {
                return null;
            }

            SurfaceWindParticle particleFromItem = new SurfaceWindParticle(clientLevel, x, y, z, 0.5f, 0.05f, 0.5f, ConditionalSoundPlayerClient.getWindX(), -0.05f, ConditionalSoundPlayerClient.getWindZ(), 1.0f, this.sprite, 0.9f, 20, 0.002f, true);
            return particleFromItem;
        }


    }
}