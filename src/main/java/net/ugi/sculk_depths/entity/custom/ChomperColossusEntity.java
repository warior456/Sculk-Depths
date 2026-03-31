package net.ugi.sculk_depths.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ChomperColossusEntity extends PathAwareEntity {

    private static final float YAW_TO_RAD = 0.017453292F;
    private static final int EXPECTED_PART_COUNT = 11;
    private static final double LEG_PROBE_UP = 3.0;
    private static final double LEG_PROBE_DOWN = 12.0;
    private static final double MAX_VERTICAL_STEP = 0.6;
    private static final double[][] LEG_OFFSETS = {
            {3.5, -2.9},
            {-3.5, -2.9},
            {3.7, 1.2},
            {-3.7, 1.2},
            {3.5, 5.3},
            {-3.5, 5.3}
    };
    private boolean acceptingPartDamage;

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    private ChomperColossusPartEntity bodyFrontPart;
    private ChomperColossusPartEntity bodyBackPart;
    private ChomperColossusPartEntity bodyPart;
    private ChomperColossusPartEntity headPart;
    private ChomperColossusPartEntity mouthPart;
    private ChomperColossusPartEntity leftFrontLegPart;
    private ChomperColossusPartEntity rightFrontLegPart;
    private ChomperColossusPartEntity leftMiddleLegPart;
    private ChomperColossusPartEntity rightMiddleLegPart;
    private ChomperColossusPartEntity leftBackLegPart;
    private ChomperColossusPartEntity rightBackLegPart;
    private ChomperColossusPartEntity[] parts = new ChomperColossusPartEntity[0];

	public ChomperColossusEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
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

        if (!this.getWorld().isClient()) {
            this.ensurePartsExist();
            this.alignBodyToLegGround();
            this.updatePartPositions();
        }

        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }

    private void ensurePartsExist() {
        if (this.parts.length == EXPECTED_PART_COUNT && this.headPart != null && this.headPart.isAlive()) {
            return;
        }

        // Body parts are solid platforms so players can stand on the colossus' torso.
        this.bodyFrontPart = this.createPart("body_front", 7f, 5f, 1.0f, true, false);
        this.bodyBackPart = this.createPart("body_back", 7f, 5f, 1.0f, true, false);
        this.bodyPart = this.createPart("body_core", 7f, 5f, 1.0f, true, false);
        // Head/mouth are push-only: no hard collision so players can fall through like normal mob interaction.
        this.headPart = this.createPart("head", 4f, 4f, 1.15f, false, true);
        this.mouthPart = this.createPart("mouth", 4f, 2.2f, 1.05f, false, true);

        this.leftFrontLegPart = this.createPart("left_front_leg", 2.2f, 4.0f, 0.9f, false, true);
        this.rightFrontLegPart = this.createPart("right_front_leg", 2.2f, 4.0f, 0.9f, false, true);
        this.leftMiddleLegPart = this.createPart("left_middle_leg", 2.2f, 4.0f, 0.9f, false, true);
        this.rightMiddleLegPart = this.createPart("right_middle_leg", 2.2f, 4.0f, 0.9f, false, true);
        this.leftBackLegPart = this.createPart("left_back_leg", 2.2f, 4.0f, 0.9f, false, true);
        this.rightBackLegPart = this.createPart("right_back_leg", 2.2f, 4.0f, 0.9f, false, true);

        this.parts = new ChomperColossusPartEntity[]{
                this.bodyFrontPart,
                this.bodyBackPart,
                this.bodyPart,
                this.headPart,
                this.mouthPart,
                this.leftFrontLegPart,
                this.rightFrontLegPart,
                this.leftMiddleLegPart,
                this.rightMiddleLegPart,
                this.leftBackLegPart,
                this.rightBackLegPart
        };

        if (this.getWorld() instanceof ServerWorld serverWorld) {
            for (ChomperColossusPartEntity part : this.parts) {
                if (!part.isAlive()) {
                    continue;
                }
                serverWorld.spawnEntity(part);
            }
        }
    }

    private ChomperColossusPartEntity createPart(String name, float width, float height, float damageMultiplier, boolean collidablePart, boolean softCollisionPart) {
        return new ChomperColossusPartEntity(this.getWorld(), this, name, width, height, damageMultiplier, collidablePart, softCollisionPart);
    }

    private void alignBodyToLegGround() {
        Double targetY = this.sampleGroundYFromLegs();
        if (targetY == null) {
            return;
        }

        double delta = MathHelper.clamp(targetY - this.getY(), -MAX_VERTICAL_STEP, MAX_VERTICAL_STEP);
        this.setPosition(this.getX(), this.getY() + delta, this.getZ());
        this.fallDistance = 0.0f;
    }

    private Double sampleGroundYFromLegs() {
        float yawRad = this.bodyYaw * YAW_TO_RAD;
        float sin = MathHelper.sin(yawRad);
        float cos = MathHelper.cos(yawRad);

        Double bestSupportY = null;
        for (double[] legOffset : LEG_OFFSETS) {
            double localX = legOffset[0];
            double localZ = legOffset[1];
            double legX = this.getX() + localX * cos - localZ * sin;
            double legZ = this.getZ() + localX * sin + localZ * cos;

            Double groundY = this.probeGroundY(legX, legZ);
            if (groundY != null && (bestSupportY == null || groundY > bestSupportY)) {
                bestSupportY = groundY;
            }
        }

        return bestSupportY;
    }

    private Double probeGroundY(double x, double z) {
        Vec3d start = new Vec3d(x, this.getY() + LEG_PROBE_UP, z);
        Vec3d end = new Vec3d(x, this.getY() - LEG_PROBE_DOWN, z);
        BlockHitResult hit = this.getWorld().raycast(new net.minecraft.world.RaycastContext(
                start,
                end,
                net.minecraft.world.RaycastContext.ShapeType.COLLIDER,
                net.minecraft.world.RaycastContext.FluidHandling.NONE,
                this
        ));

        if (hit.getType() != HitResult.Type.BLOCK) {
            return null;
        }

        return hit.getPos().y;
    }

    private void updatePartPositions() {
        if (this.parts.length != EXPECTED_PART_COUNT) {
            return;
        }

        float yawRad = this.bodyYaw * YAW_TO_RAD;
        float sin = MathHelper.sin(yawRad);
        float cos = MathHelper.cos(yawRad);

        // Tuned to align roughly with the exported model proportions (large torso, forward head/mouth, 3 leg pairs).
        this.placePart(this.bodyFrontPart, 0.0, 3.4, -0.6, sin, cos);
        this.placePart(this.bodyBackPart, 0.0, 6, 4.4, sin, cos);
        this.placePart(this.bodyPart, 0.0, 6, 1.8, sin, cos);
        this.placePart(this.headPart, 0.0, 4.7, 5.2, sin, cos);
        this.placePart(this.mouthPart, 0.0, 3.4, 7.0, sin, cos);

        this.placePart(this.leftFrontLegPart, 3.5, 0, -2.9, sin, cos);
        this.placePart(this.rightFrontLegPart, -3.5, 0, -2.9, sin, cos);
        this.placePart(this.leftMiddleLegPart, 3.7, 0, 1.2, sin, cos);
        this.placePart(this.rightMiddleLegPart, -3.7, 0, 1.2, sin, cos);
        this.placePart(this.leftBackLegPart, 3.5, 0, 5.3, sin, cos);
        this.placePart(this.rightBackLegPart, -3.5, 0, 5.3, sin, cos);
    }

    private void placePart(ChomperColossusPartEntity part, double offsetX, double offsetY, double offsetZ, float sin, float cos) {
        double x = this.getX() + offsetX * cos - offsetZ * sin;
        double y = this.getY() + offsetY;
        double z = this.getZ() + offsetX * sin + offsetZ * cos;

        part.refreshPositionAndAngles(x, y, z, this.getYaw(), this.getPitch());
        part.setVelocity(this.getVelocity());
    }

    public boolean damageFromPart(ChomperColossusPartEntity part, DamageSource source, float amount) {
        if (!this.isAlive() || this.isInvulnerableTo(source)) {
            return false;
        }

        float scaledDamage = amount * part.getDamageMultiplier();
        this.acceptingPartDamage = true;
        try {
            return super.damage(source, scaledDamage);
        } finally {
            this.acceptingPartDamage = false;
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        // Parts-only combat: direct hits to parent body never apply damage.
        if (!this.acceptingPartDamage) {
            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean canHit() {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public boolean collidesWith(Entity other) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void remove(RemovalReason reason) {
        this.discardParts();
        super.remove(reason);
    }

    private void discardParts() {
        for (ChomperColossusPartEntity part : this.parts) {
            if (part != null && part.isAlive()) {
                part.discard();
            }
        }
        this.parts = new ChomperColossusPartEntity[0];
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add(2, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createChomperColossusAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ARMOR, 5f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2);
    }
}
