package net.ugi.sculk_depths.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.ugi.sculk_depths.entity.interfaces.MultiPartEntity;
import net.ugi.sculk_depths.entity.PartEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class AuricCentipedeEntity extends HostileEntity implements MultiPartEntity {
    private static final int SEGMENT_COUNT = 15;
    private static final int DELAY_FOR_SPACING_MULTIPLIER = 2;
    private static final int HISTORY_LIMIT = 1000;

    private final AuricCentipedeSegmentEntity[] segments = new AuricCentipedeSegmentEntity[SEGMENT_COUNT];

    private final List<double[]> positionHistory = new ArrayList<>();


    boolean hasBeenInitialized = false;

    public AuricCentipedeEntity(EntityType<? extends AuricCentipedeEntity> entityType, World world) {
        super(entityType, world);

        for (int i = 0; i < SEGMENT_COUNT; i++) {
            this.segments[i] = new AuricCentipedeSegmentEntity(this);
        }
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this)); // Swimming should have the highest priority
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0f, false)); // Attack goal
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, false)); // Prioritize attacking players
        this.targetSelector.add(3, new RevengeGoal(this, new Class[0])); // Revenge at priority 3
        this.targetSelector.add(4, new ActiveTargetGoal(this, VillagerEntity.class, false)); // Prioritize attacking villagers too
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F)); // Look at players
        this.goalSelector.add(4, new LookAroundGoal(this)); // Look around randomly
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0f)); // Wander around (you can add a condition to make this more dynamic)
    }


    public static DefaultAttributeContainer.Builder createAuricCentipedeAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
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
        if(!(positionHistory.get(0)[0] == this.getX() && positionHistory.get(0)[1] == this.getY() && positionHistory.get(0)[2] == this.getZ())){
            // Add the current position of the head to the history, this line is what causes sections to move
            positionHistory.add(0, new double[]{this.getX(), this.getY(), this.getZ(), this.getYaw()});
        }

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
        for (int i = 0; i < segments.length; i++) {
            AuricCentipedeSegmentEntity segment = segments[i];
            double segmentOffsetX = Math.cos(Math.toRadians(this.getYaw()+90))*(spacing);
            double segmentOffsetZ = Math.sin(Math.toRadians(this.getYaw()+90))*(spacing);
            segment.updatePosition(prevXPos - segmentOffsetX, prevYPos, prevZPos - segmentOffsetZ);
            prevXPos = segment.getX();
            prevYPos = segment.getY();
            prevZPos = segment.getZ();
        }
        positionHistory.clear();
        double movementSpeed = this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        int HISTORY_STEP = (int) Math.ceil(DELAY_FOR_SPACING_MULTIPLIER / movementSpeed);
        //add to position history from the end to the head
        for (int j = segments.length-1; j >=0; j--) {
            AuricCentipedeSegmentEntity segment = segments[j];
            for (int k = 0; k <= HISTORY_STEP; k++) {
                positionHistory.add(0, new double[]{segment.getX(), segment.getY(), segment.getZ(), segment.getYaw()});
            }
        }
    }

    private void updateSegments() {
        double spacing = 1.0;
        double prevXPos = this.getX();
        double prevYPos = this.getY();
        double prevZPos = this.getZ();
        // Ensure we have enough history for each segment
        double movementSpeed = this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        //if the movement speed is higher it will take fewer ticks to change by the same distance
        //each entry in positionHistory is a tick
        int HISTORY_STEP = (int) Math.ceil(DELAY_FOR_SPACING_MULTIPLIER / movementSpeed);
        for (int i = 0; i < segments.length; i++) {
            //this is kind of like a train track if you will
            double[] targetPosition = positionHistory.get(Math.min((i + 1) * HISTORY_STEP, positionHistory.size() - 1)); // Offset by 2 steps per segment
            AuricCentipedeSegmentEntity segment = segments[i];
            segment.updatePosition(targetPosition[0], targetPosition[1], targetPosition[2]);
            segment.setYaw((float) targetPosition[3]);
            prevXPos = segment.getX();
            prevYPos = segment.getY();
            prevZPos = segment.getZ();
        }
    }

    private void handleTerrainInteraction() {
        for (AuricCentipedeSegmentEntity segment : segments) {
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

    private void handleWallCollisions(AuricCentipedeSegmentEntity segment) {
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
    public boolean hasInvertedHealingAndHarm() {
        return true;
    }

    @Override
    public Box getVisibilityBoundingBox() {
        // Create a bounding box that encompasses all parts of the centipede
        double minX = this.getX();
        double minY = this.getY();
        double minZ = this.getZ();
        double maxX = this.getX();
        double maxY = this.getY();
        double maxZ = this.getZ();

        // Iterate through each segment and adjust the bounding box to include each segment
        for (AuricCentipedeSegmentEntity part : segments) {
            minX = Math.min(minX, part.getX());
            minY = Math.min(minY, part.getY());
            minZ = Math.min(minZ, part.getZ());
            maxX = Math.max(maxX, part.getX());
            maxY = Math.max(maxY, part.getY());
            maxZ = Math.max(maxZ, part.getZ());
        }

        return new Box(minX, minY, minZ, maxX, maxY, maxZ);  // Return a box that encompasses the entire entity
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        double movementSpeed = this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (this.horizontalCollision) {
            this.setVelocity(this.getVelocity().x, movementSpeed*0.75, this.getVelocity().z);
        }
    }

    @Override
    public void jump() {
        // Do nothing
    }


    @Override
    public PartEntity<?>[] getParts() {
        return this.segments;
    }
}
