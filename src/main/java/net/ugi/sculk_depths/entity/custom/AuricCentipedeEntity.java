package net.ugi.sculk_depths.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.entity.util.EntityPart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AuricCentipedeEntity extends PathAwareEntity {

    private final List<EntityPart<AuricCentipedeEntity>> parts;

    public AuricCentipedeEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);

        this.parts = new ArrayList<>();

        // Initialize body segments
        int segmentCount = 10; // Example: 5 body segments
        for (int i = 0; i < segmentCount; i++) {
            if (i < segmentCount-1) {
                EntityPart<AuricCentipedeEntity> bodySegment = new EntityPart<>(ModEntities.AURIC_CENTIPEDE, this, 1.0f, 1.0f, world);
                this.parts.add(bodySegment);
            } else {
                EntityPart<AuricCentipedeEntity> endSegment = new EntityPart<>(ModEntities.AURIC_CENTIPEDE, this, 1.0f, 1.0f, world);
                this.parts.add(endSegment);
            }
        }
        resetPartPositions();
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.5D, false));
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, false));
        this.targetSelector.add(3, new RevengeGoal(this, new Class[0]));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D)); // Consider adding a condition here
    }

    public static DefaultAttributeContainer.Builder createAuricCentipedeAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_ARMOR, 5f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2);
    }

    @Override
    public void tick() {
        super.tick();
        resetPartPositions();
//        handleTerrainInteraction();
    }

    private void resetPartPositions() {
        double spacing = 1.0;
        double prevXPos = this.getX();
        double prevYPos = this.getY();
        double prevZPos = this.getZ();
        for (EntityPart<AuricCentipedeEntity> segment : parts) {
            double segmentOffsetX = Math.cos(Math.toRadians(this.getYaw()+90))*(spacing);
            double segmentOffsetZ = Math.sin(Math.toRadians(this.getYaw()+90))*(spacing);
            segment.updatePosition(prevXPos - segmentOffsetX, prevYPos, prevZPos - segmentOffsetZ);

            prevXPos = segment.getX();
            prevYPos = segment.getY();
            prevZPos = segment.getZ();
        }
    }


    private void handleTerrainInteraction() {
        for (EntityPart<AuricCentipedeEntity> part : parts) {
            // Get the part's bounding box
            Box partBox = part.getBoundingBox();

            // Check for collision with blocks
            List<VoxelShape> collisions = StreamSupport.stream(part.getWorld().getBlockCollisions(null, partBox).spliterator(), false)
                    .toList();

            // Resolve collisions (example: prevent the part from sinking into the ground)
            for (VoxelShape collision : collisions) {
                double maxY = collision.getBoundingBox().maxY;
                if (part.getY() < maxY) {
                    part.updatePosition(part.getX(), maxY, part.getZ());
                }
            }
            handleWallCollisions(part);
        }
    }

    private void handleWallCollisions(EntityPart<AuricCentipedeEntity> part) {
        Box partBox = part.getBoundingBox();
        boolean colliding = StreamSupport.stream(part.getWorld().getBlockCollisions(null, partBox).spliterator(), false)
                .findAny()
                .isPresent();

        if (colliding) {
            // Push the part back slightly to avoid the collision
            double offsetX = Math.cos(Math.toRadians(this.getYaw())) * -0.1;
            double offsetZ = Math.sin(Math.toRadians(this.getYaw())) * -0.1;
            part.updatePosition(part.getX() + offsetX, part.getY(), part.getZ() + offsetZ);
        }
    }


    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        double distanceSq = this.squaredDistanceTo(cameraX, cameraY, cameraZ);
        if (distanceSq < 256.0) { // Adjust this value based on how far you want the entity to render
            return true;
        }

        for (EntityPart<AuricCentipedeEntity> part : parts) {
            distanceSq = part.squaredDistanceTo(cameraX, cameraY, cameraZ);
            if (distanceSq < 256.0) { // Adjust this value based on how far you want the entity to render
                return true;
            }
        }
        return super.shouldRender(cameraX, cameraY, cameraZ);
    }


    public List<EntityPart<AuricCentipedeEntity>> getParts() {
        return parts;
    }
}
