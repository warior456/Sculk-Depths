package net.ugi.sculk_depths.entity.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
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
}
