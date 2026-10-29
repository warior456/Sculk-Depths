package net.ugi.sculk_depths.entity.custom;


import net.minecraft.entity.*;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.ugi.sculk_depths.SculkDepths;
import org.jetbrains.annotations.Nullable;


import java.util.function.Predicate;

public class GlomperEntity extends PathfinderMob {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR = entity -> entity.isAlwaysTicking() && entity.isFallFlying();
    protected float getFlyingSpeed() {
        return this.getMovementSpeed() * 0.1f;//fix movement speed
    }


    public GlomperEntity(EntityType<? extends PathfinderMob> entityType, Level world) {
        super(entityType, world);
        this.moveControl = new FlyingMoveControl(this, 0, true);
        this.setNoGravity(true);
    }

    @Override
    protected int getXpToDrop() {
        return 1 + this.getWorld().random.getInt(3);
    }

/*    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {

        super.initialize(world, difficulty, spawnReason, entityData);
        this.setNoGravity(true);
        return entityData;
    }*/

    @Override
    public boolean dampensVibrations() {
        return true;
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, SculkDepths.CONFIG.glomper_health)
                .add(Attributes.ATTACK_DAMAGE, SculkDepths.CONFIG.glomper_damage)
                .add(Attributes.FOLLOW_RANGE, 100)
                .add(Attributes.ATTACK_SPEED, 0.5f) // doubt this does anything
                .add(Attributes.FLYING_SPEED, 0.2f) //needed to not crash for some frigging reason
                .add(Attributes.MOVEMENT_SPEED, 0.2f);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.getInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
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

    protected PathNavigation createNavigation(Level world) {
        FlyingPathNavigation birdNavigation = new FlyingPathNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanFloat(true);
        birdNavigation.setCanPassDoors(true);
        return birdNavigation;
    }

    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
        return false;
    }

    protected void registerGoals() {
        this.goalSelector.add(4, new FlyGoal(this, 1.0));
        //this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
        //this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(1, new GlomperEntity.GlomperTargetGoal(this, LivingEntity.class, 0, false, false, LIVING_ENTITY_SELECTOR));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 9.0D, false));

    }

    public static class GlomperTargetGoal
            extends ActiveTargetGoal {

        private boolean followingTargetEvenIfNotSeen;

        public GlomperTargetGoal(Mob mob, Class targetType, int randomInterval, boolean mustSee, boolean mustReach, @Nullable Predicate targetPredicate) {
            super(mob, targetType, randomInterval, mustSee, mustReach, targetPredicate);
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null) {
                return false;
            }
            if (!livingEntity.isAlive()) {
                return false;
            }
            if (!this.followingTargetEvenIfNotSeen) {
                return !this.mob.getNavigation().isIdle();
            }
            assert this.target != null;
            if (!this.target.isFallFlying()) {
                return false;
            }
            return !(livingEntity instanceof Player) || !livingEntity.isSpectator() && !((Player) livingEntity).isCreative();

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
