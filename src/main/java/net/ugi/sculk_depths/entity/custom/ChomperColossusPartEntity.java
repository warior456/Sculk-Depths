package net.ugi.sculk_depths.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.ugi.sculk_depths.entity.ModEntities;

import java.util.UUID;

public class ChomperColossusPartEntity extends Entity {
    private static final TrackedData<String> PART_NAME_TRACKED = DataTracker.registerData(ChomperColossusPartEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Float> DAMAGE_MULTIPLIER_TRACKED = DataTracker.registerData(ChomperColossusPartEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> PART_WIDTH_TRACKED = DataTracker.registerData(ChomperColossusPartEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> PART_HEIGHT_TRACKED = DataTracker.registerData(ChomperColossusPartEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Boolean> COLLIDABLE_TRACKED = DataTracker.registerData(ChomperColossusPartEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SOFT_COLLISION_TRACKED = DataTracker.registerData(ChomperColossusPartEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> OWNER_ID_TRACKED = DataTracker.registerData(ChomperColossusPartEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private ChomperColossusEntity owner;
    private UUID ownerUuid;
    private String partName = "part";
    private float damageMultiplier = 1.0f;
    private float partWidth = 1.0f;
    private float partHeight = 1.0f;
    private boolean collidablePart = true;
    private boolean softCollisionPart = true;

    public ChomperColossusPartEntity(EntityType<? extends ChomperColossusPartEntity> type, World world) {
        super(type, world);
        this.noClip = false;
    }

    public ChomperColossusPartEntity(World world, ChomperColossusEntity owner, String partName, float width, float height, float damageMultiplier) {
        this(ModEntities.CHOMPER_COLOSSUS_PART, world);
        this.configure(owner, partName, width, height, damageMultiplier, true, true);
    }

    public ChomperColossusPartEntity(World world, ChomperColossusEntity owner, String partName, float width, float height, float damageMultiplier, boolean collidablePart, boolean softCollisionPart) {
        this(ModEntities.CHOMPER_COLOSSUS_PART, world);
        this.configure(owner, partName, width, height, damageMultiplier, collidablePart, softCollisionPart);
    }

    public ChomperColossusPartEntity configure(ChomperColossusEntity owner, String partName, float width, float height, float damageMultiplier, boolean collidablePart, boolean softCollisionPart) {
        String normalizedPartName = (partName == null || partName.isBlank()) ? "part" : partName;
        this.owner = owner;
        this.ownerUuid = owner.getUuid();
        this.partName = normalizedPartName;
        this.damageMultiplier = damageMultiplier;
        this.partWidth = width;
        this.partHeight = height;
        this.collidablePart = collidablePart;
        this.softCollisionPart = softCollisionPart;

        this.dataTracker.set(PART_NAME_TRACKED, normalizedPartName);
        this.dataTracker.set(DAMAGE_MULTIPLIER_TRACKED, damageMultiplier);
        this.dataTracker.set(PART_WIDTH_TRACKED, width);
        this.dataTracker.set(PART_HEIGHT_TRACKED, height);
        this.dataTracker.set(COLLIDABLE_TRACKED, collidablePart);
        this.dataTracker.set(SOFT_COLLISION_TRACKED, softCollisionPart);
        this.dataTracker.set(OWNER_ID_TRACKED, owner.getId());

        this.calculateDimensions();
        this.setBoundingBox(this.calculateBoundingBox());
        return this;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.owner == null || !this.owner.isAlive()) {
            Entity trackedOwner = this.getWorld().getEntityById(this.dataTracker.get(OWNER_ID_TRACKED));
            if (trackedOwner instanceof ChomperColossusEntity colossus && colossus.isAlive()) {
                this.owner = colossus;
                this.ownerUuid = colossus.getUuid();
            }

            if (!this.getWorld().isClient() && (this.owner == null || !this.owner.isAlive())) {
                this.discard();
                return;
            }
        }

        if (this.hasSoftCollision()) {
            this.pushOverlappingEntities();
        }

        //not relevant
//        if (this.squaredDistanceTo(this.owner) > 64.0) {
//            this.setPosition(this.owner.getX(), this.owner.getY(), this.owner.getZ());
//        }
    }

    private void pushOverlappingEntities() {
        Box box = this.getBoundingBox();
        for (Entity other : this.getWorld().getOtherEntities(this, box, this::canSoftPush)) {
            this.pushAwayFrom(other);
        }
    }

    private boolean canSoftPush(Entity other) {
        if (!other.isPushable() || other.isSpectator()) {
            return false;
        }

        if (other == this.owner) {
            return false;
        }

        if (other instanceof ChomperColossusPartEntity otherPart && otherPart.getOwner() == this.owner) {
            return false;
        }

        return true;
    }

    public String getPartName() {
        return this.dataTracker.get(PART_NAME_TRACKED);
    }

    public float getDamageMultiplier() {
        return this.dataTracker.get(DAMAGE_MULTIPLIER_TRACKED);
    }

    public boolean isCollidablePart() {
        return this.dataTracker.get(COLLIDABLE_TRACKED);
    }

    public boolean hasSoftCollision() {
        return this.dataTracker.get(SOFT_COLLISION_TRACKED);
    }

    // Backward-compatible accessor for existing client/debug code.
    public boolean isPushablePart() {
        return this.hasSoftCollision();
    }

    public ChomperColossusEntity getOwner() {
        return this.owner;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.owner != null && this.owner.isAlive()) {
            return this.owner.damageFromPart(this, source, amount);
        }
        return false;
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean isPartOf(Entity entity) {
        return this == entity || this.owner == entity;
    }

    @Override
    public boolean isCollidable() {
        return this.isCollidablePart();
    }

    @Override
    public boolean collidesWith(Entity other) {
        if (!this.isCollidablePart()) {
            return false;
        }

        if (other == this.owner) {
            return false;
        }

        if (other instanceof ChomperColossusPartEntity otherPart && otherPart.getOwner() == this.owner) {
            return false;
        }

        return other.isCollidable();
    }

    @Override
    public boolean isPushable() {
        return this.hasSoftCollision();
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed(this.dataTracker.get(PART_WIDTH_TRACKED), this.dataTracker.get(PART_HEIGHT_TRACKED));
    }

    @Override
    public ItemStack getPickBlockStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean shouldSave() {
        return false;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        // Use literal defaults because this runs during super-construction before subclass fields are initialized.
        builder.add(PART_NAME_TRACKED, "");
        builder.add(DAMAGE_MULTIPLIER_TRACKED, 1.0f);
        builder.add(PART_WIDTH_TRACKED, 1.0f);
        builder.add(PART_HEIGHT_TRACKED, 1.0f);
        builder.add(COLLIDABLE_TRACKED, true);
        builder.add(SOFT_COLLISION_TRACKED, true);
        builder.add(OWNER_ID_TRACKED, -1);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (data == PART_WIDTH_TRACKED || data == PART_HEIGHT_TRACKED) {
            this.calculateDimensions();
            this.setBoundingBox(this.calculateBoundingBox());
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.partName = nbt.getString("PartName");
        if (this.partName == null || this.partName.isBlank()) {
            this.partName = "part";
        }
        if (nbt.containsUuid("Owner")) {
            this.ownerUuid = nbt.getUuid("Owner");
        }
        this.damageMultiplier = nbt.getFloat("DamageMultiplier");
        this.partWidth = nbt.getFloat("PartWidth");
        this.partHeight = nbt.getFloat("PartHeight");
        this.collidablePart = nbt.getBoolean("CollidablePart");
        this.softCollisionPart = nbt.getBoolean("SoftCollisionPart");

        this.dataTracker.set(PART_NAME_TRACKED, this.partName);
        this.dataTracker.set(DAMAGE_MULTIPLIER_TRACKED, this.damageMultiplier);
        this.dataTracker.set(PART_WIDTH_TRACKED, this.partWidth);
        this.dataTracker.set(PART_HEIGHT_TRACKED, this.partHeight);
        this.dataTracker.set(COLLIDABLE_TRACKED, this.collidablePart);
        this.dataTracker.set(SOFT_COLLISION_TRACKED, this.softCollisionPart);

        if (!this.getWorld().isClient() && this.owner == null && this.ownerUuid != null && this.getWorld() instanceof ServerWorld serverWorld) {
            Entity candidate = serverWorld.getEntity(this.ownerUuid);
            if (candidate instanceof ChomperColossusEntity colossus) {
                this.owner = colossus;
                this.dataTracker.set(OWNER_ID_TRACKED, colossus.getId());
            } else {
                this.discard();
            }
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putString("PartName", this.partName);
        if (this.ownerUuid != null) {
            nbt.putUuid("Owner", this.ownerUuid);
        }
        nbt.putFloat("DamageMultiplier", this.damageMultiplier);
        nbt.putFloat("PartWidth", this.partWidth);
        nbt.putFloat("PartHeight", this.partHeight);
        nbt.putBoolean("CollidablePart", this.collidablePart);
        nbt.putBoolean("SoftCollisionPart", this.softCollisionPart);
    }
}












