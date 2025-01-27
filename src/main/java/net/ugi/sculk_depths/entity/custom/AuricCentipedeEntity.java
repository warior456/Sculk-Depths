package net.ugi.sculk_depths.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.entity.util.EntityPart;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AuricCentipedeEntity extends PathAwareEntity {

    private final List<EntityPart<AuricCentipedeEntity>> parts;

    private final List<double[]> positionHistory = new ArrayList<>();
    private static final int HISTORY_LIMIT = 1000;

    public AuricCentipedeEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);

        this.parts = new ArrayList<>();

        // Initialize body segments
        int segmentCount = 10;
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
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, false));
        this.targetSelector.add(2, new ActiveTargetGoal(this, VillagerEntity.class, false));
        this.targetSelector.add(3, new RevengeGoal(this, new Class[0]));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0D)); // Consider adding a condition here
    }

    public static DefaultAttributeContainer.Builder createAuricCentipedeAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ARMOR, 5f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2);
    }

    @Override
    public void tick() {
        super.tick();

        // Add the current position of the head to the history
        positionHistory.add(0, new double[]{this.getX(), this.getY(), this.getZ(), this.getYaw()});

        // Limit the size of the history to avoid memory overflow
        if (positionHistory.size() > HISTORY_LIMIT) {
            positionHistory.remove(positionHistory.size() - 1);
        }
        updatePartPositions();
//        handleTerrainInteraction();
    }

    private void resetPartPositions() {
        double spacing = 1.0;
        double prevXPos = this.getX();
        double prevYPos = this.getY();
        double prevZPos = this.getZ();
        positionHistory.clear();
        for (EntityPart<AuricCentipedeEntity> segment : parts) {
            double segmentOffsetX = Math.cos(Math.toRadians(this.getYaw()+90))*(spacing);
            double segmentOffsetZ = Math.sin(Math.toRadians(this.getYaw()+90))*(spacing);
            segment.updatePosition(prevXPos - segmentOffsetX, prevYPos, prevZPos - segmentOffsetZ);
            positionHistory.add(0, new double[]{segment.getX(), segment.getY(), segment.getZ(), segment.getYaw()});
            prevXPos = segment.getX();
            prevYPos = segment.getY();
            prevZPos = segment.getZ();
        }
    }

    private void updatePartPositions() {
        double spacing = 1.0;
        double prevXPos = this.getX();
        double prevYPos = this.getY();
        double prevZPos = this.getZ();
        // Ensure we have enough history for each segment
        double movementSpeed = this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        int HISTORY_STEP = (int) Math.ceil((1.0 / movementSpeed)*2.4);
        for (int i = 0; i < parts.size(); i++) {
            if (i < positionHistory.size()) {
                double[] targetPosition = positionHistory.get(Math.min((i + 1) * HISTORY_STEP, positionHistory.size() - 1)); // Offset by 2 steps per segment
                EntityPart<AuricCentipedeEntity> segment = parts.get(i);
                if(segment.squaredDistanceTo(prevXPos,prevYPos,prevZPos)>spacing){
                    segment.updatePosition(targetPosition[0], targetPosition[1], targetPosition[2]);
                }
                segment.setYaw((float) targetPosition[3]);
                prevXPos = segment.getX();
                prevYPos = segment.getY();
                prevZPos = segment.getZ();
            }
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
