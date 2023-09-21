package net.ugi.sculk_depths.entity.custom;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.ModEntities;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.function.Predicate;


public class GlomperEntity extends PathAwareEntity implements GeoEntity {

    private static final Predicate<LivingEntity> CAN_ATTACK_PREDICATE = entity -> entity.isPlayer() && entity.isFallFlying();

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public GlomperEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 0, true);
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

    protected float getOffGroundSpeed() {
        return this.getMovementSpeed() * 0.1f;//fix movement speed
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
        //this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.0, 40, 20.0F));
        this.goalSelector.add(4, new FlyGoal(this, 1.0));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
        //this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(1, new GlomperTargetGoal(this, LivingEntity.class, 0, false, false, CAN_ATTACK_PREDICATE));
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

    /*
    @Override
    protected void mobTick() {
        if (this.getTarget() != null) {
            if (this.getTarget().isFallFlying()) {
                this.setTarget(null);
            }
        }
    }
    */

    /*
    @Override
    public void initGoals() {



        //
        //this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.75f, 1));

        //this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, ChickenEntity.class, true));
        //this.goalSelector.add(3, new FlyGoal(this, 1));
        //this.goalSelector.add(2, new MeleeAttackGoal(this, 1.2D, false));
        //this.goalSelector.add(1, new FollowMobGoal(this,3,2, ChickenEntity.class.getModifiers()));
    } */


    public MobEntity createChild(ServerWorld world, MobEntity entity) {
        return ModEntities.GLOMPER.create(world);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        /*
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.glomper.walk", Animation.LoopType.LOOP));
        }
        */
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.glomper.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}