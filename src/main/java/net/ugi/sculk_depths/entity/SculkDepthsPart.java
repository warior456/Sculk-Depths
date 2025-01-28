package net.ugi.sculk_depths.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;

public class SculkDepthsPart<T extends Entity> extends PartEntity<T> {
    protected EntityDimensions realSize;

    protected int newPosRotationIncrements;
    protected double interpTargetX;
    protected double interpTargetY;
    protected double interpTargetZ;
    protected double interpTargetYaw;
    protected double interpTargetPitch;
    public float renderYawOffset;
    public float prevRenderYawOffset;

    public int deathTime;
    public int hurtTime;

    public SculkDepthsPart(T parent) {
        super(parent);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        // No custom NBT logic needed for the parts
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        // No custom NBT logic needed for the parts
    }

//    @Override
//    public EntityDimensions getDimensions(EntityPose pose) {
//        return EntityDimensions.fixed(this.width, this.height);
//    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.getParent() != null) { // Ensure it has a parent entity
            return this.getParent().damage(source, amount); // Delegate damage to the main entity
        }
        return false; // If the owner is null, the segment takes no damage
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    public void updateBoundingBox() {
        this.setBoundingBox(this.calculateBoundingBox());
    }

    @Override
    public void refreshPositionAndAngles(double x, double y, double z, float yaw, float pitch) {
        super.refreshPositionAndAngles(x, y, z, yaw, pitch);
        this.updateBoundingBox(); // Align bounding box with new position
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

}
