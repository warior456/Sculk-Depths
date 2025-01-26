package net.ugi.sculk_depths.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.entity.util.EntityPart;

import java.util.ArrayList;
import java.util.List;

public class AuricCentipedeEntity extends PathAwareEntity {

    private final List<EntityPart<AuricCentipedeEntity>> parts;

    public final EntityPart<AuricCentipedeEntity> head;
    public final EntityPart<AuricCentipedeEntity> body;
    public final EntityPart<AuricCentipedeEntity> end;

    public AuricCentipedeEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);

        // Initialize parts
        this.head = new EntityPart<>(ModEntities.AURIC_CENTIPEDE, this, 1.0f, 1.0f, world);
        this.body = new EntityPart<>(ModEntities.AURIC_CENTIPEDE, this, 0.8f, 0.8f, world);
        this.end = new EntityPart<>(ModEntities.AURIC_CENTIPEDE, this, 0.7f, 0.7f, world);

        this.parts = new ArrayList<>();
        this.parts.add(head);
        this.parts.add(body);
        this.parts.add(end);
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
        updatePartPositions();
    }

    private void updatePartPositions() {
        double offsetX = Math.cos(Math.toRadians(this.getYaw()));
        double offsetZ = Math.sin(Math.toRadians(this.getYaw()));

        head.updatePosition(this.getX() - offsetX, this.getY(), this.getZ() - offsetZ);
        body.updatePosition(head.getX() - offsetX, head.getY(), head.getZ() - offsetZ);
        end.updatePosition(body.getX() - offsetX, body.getY(), body.getZ() - offsetZ);
    }

    public List<EntityPart<AuricCentipedeEntity>> getParts() {
        return parts;
    }


    public EntityPart<AuricCentipedeEntity> getHead() {
        return head;
    }

    public EntityPart<AuricCentipedeEntity> getBody() {
        return body;
    }

    public EntityPart<AuricCentipedeEntity> getEnd() {
        return end;
    }
}
