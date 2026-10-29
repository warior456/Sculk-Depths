package net.ugi.sculk_depths.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ChomperColossusEntity extends PathfinderMob {

    private static double viewScale = 1.0;
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public ChomperColossusEntity(EntityType<? extends PathfinderMob> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 50;
    }

/*    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }*/

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.getInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public boolean shouldRender(double distanceToPlayer) {
        double d = 30;
        return distanceToPlayer < (d *= 64.0 * viewScale) * d;
    }


    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getState() == EntityPose.STANDING ? Math.minValue(posDelta * 6.0f, 1.0f) : 0.0f;
        this.walkAnimation.updateLimbs(f, 0.2f);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getWorld().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.add(1, new WaterAvoidingRandomStrollGoal(this, 1D));
        this.goalSelector.add(2, new RandomLookAroundGoal(this));
/*        this.goalSelector.add(0, new SwimGoal(this));




        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
        this.goalSelector.add(6, new LookAroundGoal(this));*/
    }

    public static AttributeSupplier.Builder createChomperColossusAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.ARMOR, 5f)
                .add(Attributes.ATTACK_DAMAGE, 2);
    }



/*    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BLAZE_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENCHANT_THORNS_HIT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BEE_DEATH;
    }*/
}
