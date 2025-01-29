package net.ugi.sculk_depths.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.SculkDepthsEntityPart;

import java.util.List;

public class AuricCentipedeSegmentEntity extends SculkDepthsEntityPart<AuricCentipedeEntity> {
    public AuricCentipedeSegmentEntity(AuricCentipedeEntity auricCentipedeEntity) {
        super(auricCentipedeEntity);
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed(1.0F, 1.0F); // Width: 1 block, Height: 1 block
    }

    private void collideWithOthers() {
        List<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox());

        for (Entity entity : entities) {
            if (entity.isPushable()) {
                this.collideWithEntity(entity);
            }
        }
    }

    private void collideWithEntity(Entity entity) {
        entity.pushAwayFrom(this);
    }

    @Override
    public void tick() {
        super.tick();
        this.collideWithOthers();
        this.setBoundingBox(this.calculateBoundingBox()); // Sync hitbox
    }

    @Override
    protected Box calculateBoundingBox() {
        return new Box(
                this.getX() - 0.5, this.getY(), this.getZ() - 0.5,
                this.getX() + 0.5, this.getY() + 1.0, this.getZ() + 0.5
        );
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.getParent() != null) {
            return this.getParent().damage(source, amount); // Redirect to main centipede
        }
        return false;
    }

}