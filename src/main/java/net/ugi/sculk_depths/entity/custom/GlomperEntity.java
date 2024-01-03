package net.ugi.sculk_depths.entity.custom;


import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.ModEntities;
import org.jetbrains.annotations.Nullable;


import java.util.function.Predicate;

public class GlomperEntity extends PathAwareEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private static final Predicate<LivingEntity> CAN_ATTACK_PREDICATE = entity -> entity.isPlayer() && entity.isFallFlying();
    protected float getOffGroundSpeed() {
        return this.getMovementSpeed() * 0.1f;//fix movement speed
    }


    public GlomperEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 0, true);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {

        super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.setNoGravity(true);
        return entityData;
    }

    @Override
    public boolean occludeVibrationSignals() {
        return true;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, SculkDepths.CONFIG.glomper_health)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, SculkDepths.CONFIG.glomper_damage)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.5f) // doubt this does anything
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.2f) //needed to not crash for some frigging reason
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void initGoals() {
        this.goalSelector.add(4, new FlyGoal(this, 1.0));
        //this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
        //this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(1, new GlomperEntity.GlomperTargetGoal(this, LivingEntity.class, 0, false, false, CAN_ATTACK_PREDICATE));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 9.0D, false));

    }

    public static class GlomperTargetGoal
            extends ActiveTargetGoal {

        private boolean pauseWhenMobIdle;

        public GlomperTargetGoal(MobEntity mob, Class targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable Predicate targetPredicate) {
            super(mob, targetClass, reciprocalChance, checkVisibility, checkCanNavigate, targetPredicate);
        }

        @Override
        public boolean shouldContinue() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null) {
                return false;
            }
            if (!livingEntity.isAlive()) {
                return false;
            }
            if (!this.pauseWhenMobIdle) {
                return !this.mob.getNavigation().isIdle();
            }
            assert this.target != null;
            if (!this.target.isFallFlying()) {
                return false;
            }
            return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity) livingEntity).isCreative();

        }

    }

    //todo: sounds
/*    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_FOX_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CAT_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_DOLPHIN_DEATH;
    }*/
}
