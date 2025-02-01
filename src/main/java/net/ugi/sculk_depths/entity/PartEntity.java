package net.ugi.sculk_depths.entity;

import net.minecraft.entity.Entity;

public abstract class PartEntity<T extends Entity> extends Entity {
    //Don't edit this, edit SculkDepthsEntityPart
    private final T parent;
    public PartEntity(T parent) {
        super(parent.getType(), parent.getWorld());
        this.parent = parent;
    }

    public T getParent() {
        return parent;
    }
}