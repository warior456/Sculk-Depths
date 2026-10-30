package net.ugi.sculk_depths.entity.custom;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.tags.ModTags;
import org.jetbrains.annotations.Nullable;

public class LesterEntity extends PathfinderMob {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public LesterEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
    }

/*    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }*/

    @Override
    public boolean dampensVibrations() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
        return true;
    }

    @Override
    public boolean isInWall() {
        if (this.noPhysics) {
            return false;
        } else {
            float f = 0.8F * 0.8F;
            AABB box = AABB.ofSize(this.getEyePosition(), (double)f, 1.0E-6, (double)f);
            return BlockPos.betweenClosedStream(box).anyMatch((pos) -> {
                BlockState block = this.level().getBlockState(pos);
                return !(block.isAir() || block.is(ModTags.Blocks.LESTER_SPAWN_BLOCKS)) && block.isSuffocating(this.level(), pos) && Shapes.joinIsNotEmpty(block.getCollisionShape(this.level(), pos).move((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()), Shapes.create(box), BooleanOp.AND);
            });
        }
    }


    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float posDelta) {
        float f = this.getPose() == Pose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    protected void registerGoals() {
        //this.goalSelector.addGoal(2, new TemptGoal(this, 1.25D, Ingredient.ofItems(ModItems.QUAZARITH_PIECES), false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2f, false));
/*        this.goalSelector.addGoal(0, new SwimGoal(this));




        this.goalSelector.addGoal(4, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.addGoal(5, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
        this.goalSelector.addGoal(6, new LookAroundGoal(this));*/
    }

    public static AttributeSupplier.Builder createLesterAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15)
                .add(Attributes.MOVEMENT_SPEED, 0.2f) //todo maybe speed up?
                .add(Attributes.ARMOR, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 5f);
    }



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
