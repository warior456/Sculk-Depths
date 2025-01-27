package net.ugi.sculk_depths.entity.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class EntityPart<T extends Entity> extends Entity {
    private final T owner; // The parent entity this part belongs to
    private final float width;
    private final float height;

    public EntityPart(EntityType<? extends Entity> type, T owner, float width, float height, World world) {
        super(type, world);
        this.owner = owner;
        this.width = width;
        this.height = height;
    }

    public T getOwner() {
        return owner;
    }

    @Override
    public boolean isPartOf(Entity entity) {
        return entity == owner;
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

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed(this.width, this.height);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.owner != null) { // Ensure it has a parent entity
            return this.owner.damage(source, amount); // Delegate damage to the main entity
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
