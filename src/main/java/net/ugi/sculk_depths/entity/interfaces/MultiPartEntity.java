package net.ugi.sculk_depths.entity.interfaces;

import net.ugi.sculk_depths.entity.PartEntity;

public interface MultiPartEntity {

    PartEntity<?>[] getParts();

    default boolean hasParts() {
        return getParts().length != 0;
    }
}