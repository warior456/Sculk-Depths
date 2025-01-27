package net.ugi.sculk_depths.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Difficulty;
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
    private static final int SEGMENT_COUNT = 25;
    private static final int HISTORY_LIMIT = 1000;

    private final List<EntityPart<AuricCentipedeEntity>> segments;

    private final List<double[]> positionHistory = new ArrayList<>();

    boolean hasBeenInitialized = false;

    public AuricCentipedeEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);

        this.segments = new ArrayList<>();

        // Initialize body segments
        for (int i = 0; i < SEGMENT_COUNT; i++) {
            if (i < SEGMENT_COUNT-1) {
                EntityPart<AuricCentipedeEntity> bodySegment = new EntityPart<>(ModEntities.AURIC_CENTIPEDE, this, 1.0f, 1.0f, world);
                this.segments.add(bodySegment);
            } else {
                EntityPart<AuricCentipedeEntity> endSegment = new EntityPart<>(ModEntities.AURIC_CENTIPEDE, this, 1.0f, 1.0f, world);
                this.segments.add(endSegment);
            }
        }
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this)); // Swimming should have the highest priority
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0f, false)); // Attack goal
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, false)); // Prioritize attacking players
        this.targetSelector.add(3, new RevengeGoal(this, new Class[0])); // Revenge at priority 3
        this.targetSelector.add(4, new ActiveTargetGoal(this, VillagerEntity.class, false)); // Prioritize attacking villagers too
//        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F)); // Look at players
//        this.goalSelector.add(4, new LookAroundGoal(this)); // Look around randomly
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0f)); // Wander around (you can add a condition to make this more dynamic)
    }


    public static DefaultAttributeContainer.Builder createAuricCentipedeAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ARMOR, 5f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 10000f)
                .add(EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE, 10000f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2);
    }

    @Override
    public void tick() {
        super.tick();
        //this can't go in the constructor, it doesn't work as things aren't ready yet
        if(!(hasBeenInitialized)){
            resetSegments();  // Ensure the segments are properly initialized after the spawn
            hasBeenInitialized = true;
        }
        // Add the current position of the head to the history, this line is what causes sections to move
        positionHistory.add(0, new double[]{this.getX(), this.getY(), this.getZ(), this.getYaw()});

        // Limit the size of the history to avoid memory overflow
        if (positionHistory.size() > HISTORY_LIMIT) {
            positionHistory.remove(positionHistory.size() - 1);
        }
        updateSegments();
//        handleTerrainInteraction();
    }

    private void resetSegments() {
        double spacing = 1.0;
        double prevXPos = this.getX();
        double prevYPos = this.getY();
        double prevZPos = this.getZ();
        for (int i = 0; i < segments.size(); i++) {
            EntityPart<AuricCentipedeEntity> segment = segments.get(i);
            double segmentOffsetX = Math.cos(Math.toRadians(this.getYaw()+90))*(spacing);
            double segmentOffsetZ = Math.sin(Math.toRadians(this.getYaw()+90))*(spacing);
            segment.updatePosition(prevXPos - segmentOffsetX, prevYPos, prevZPos - segmentOffsetZ);
            prevXPos = segment.getX();
            prevYPos = segment.getY();
            prevZPos = segment.getZ();
        }
        positionHistory.clear();
        //add to position history from the end to the head
        for (int j = segments.size()-1; j >=0; j--) {
            EntityPart<AuricCentipedeEntity> segment = segments.get(j);
            positionHistory.add(0, new double[]{segment.getX(), segment.getY(), segment.getZ(), segment.getYaw()});
        }
    }

    private void updateSegments() {
        double spacing = 1.0;
        double prevXPos = this.getX();
        double prevYPos = this.getY();
        double prevZPos = this.getZ();
        // Ensure we have enough history for each segment
        double movementSpeed = this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        int HISTORY_STEP = (int) Math.ceil((1.0 / movementSpeed)*2.4);
        for (int i = 0; i < segments.size(); i++) {
            //this is kind of like a train track if you will
            double[] targetPosition = positionHistory.get(Math.min((i + 1) * HISTORY_STEP, positionHistory.size() - 1)); // Offset by 2 steps per segment
            EntityPart<AuricCentipedeEntity> segment = segments.get(i);
            if(segment.squaredDistanceTo(prevXPos,prevYPos,prevZPos)>spacing){
                segment.updatePosition(targetPosition[0], targetPosition[1], targetPosition[2]);
            }
            segment.setYaw((float) targetPosition[3]);
            prevXPos = segment.getX();
            prevYPos = segment.getY();
            prevZPos = segment.getZ();
        }
    }

    private void handleTerrainInteraction() {
        for (EntityPart<AuricCentipedeEntity> segment : segments) {
            // Get the segment's bounding box
            Box segmentBox = segment.getBoundingBox();

            // Check for collision with blocks
            List<VoxelShape> collisions = StreamSupport.stream(segment.getWorld().getBlockCollisions(null, segmentBox).spliterator(), false)
                    .toList();

            // Resolve collisions (example: prevent the segment from sinking into the ground)
            for (VoxelShape collision : collisions) {
                double maxY = collision.getBoundingBox().maxY;
                if (segment.getY() < maxY) {
                    segment.updatePosition(segment.getX(), maxY, segment.getZ());
                }
            }
            handleWallCollisions(segment);
        }
    }

    private void handleWallCollisions(EntityPart<AuricCentipedeEntity> segment) {
        Box segmentBox = segment.getBoundingBox();
        boolean colliding = StreamSupport.stream(segment.getWorld().getBlockCollisions(null, segmentBox).spliterator(), false)
                .findAny()
                .isPresent();

        if (colliding) {
            // Push the segment back slightly to avoid the collision
            double offsetX = Math.cos(Math.toRadians(this.getYaw())) * -0.1;
            double offsetZ = Math.sin(Math.toRadians(this.getYaw())) * -0.1;
            segment.updatePosition(segment.getX() + offsetX, segment.getY(), segment.getZ() + offsetZ);
        }
    }


    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        // Check the distance to the head first
        double distanceSq = this.squaredDistanceTo(cameraX, cameraY, cameraZ);
        if (distanceSq < 256.0) { // Adjust this value based on how far you want the entity to render
            return true;
        }

        // Check all segments as well
        for (EntityPart<AuricCentipedeEntity> segment : segments) {
            distanceSq = segment.squaredDistanceTo(cameraX, cameraY, cameraZ);
            if (distanceSq < 256.0) { // Adjust this value based on how far you want the entity to render
                return true;
            }
        }

        // If none of the segments or the head are within range, return false
        return false;
    }

    @Override
    public boolean hasInvertedHealingAndHarm() {
        return true;
    }

    public List<EntityPart<AuricCentipedeEntity>> getSegments() {
        return segments;
    }
}
