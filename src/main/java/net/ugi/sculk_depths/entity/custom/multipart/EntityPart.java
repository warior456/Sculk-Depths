package net.ugi.sculk_depths.entity.custom.multipart;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.ugi.sculk_depths.entity.custom.multipart.MultipartEntity;
import org.jetbrains.annotations.Nullable;

public class EntityPart
        extends Entity {
    public final MultipartEntity owner;
    public final String name;
    private final EntityDimensions partDimensions;

    public EntityPart(MultipartEntity owner, String name, float width, float height) {
        super(owner.getType(), owner.getWorld());
        this.partDimensions = EntityDimensions.changing(width, height);
        this.calculateDimensions();
        this.owner = owner;
        this.name = name;
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    @Nullable
    public ItemStack getPickBlockStack() {
        return this.owner.getPickBlockStack();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        return this.owner.damagePart(this, source, amount);
    }

    @Override
    public boolean isPartOf(Entity entity) {
        return this == entity || this.owner == entity;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return this.partDimensions;
    }

    @Override
    public boolean shouldSave() {
        return false;
    }
}