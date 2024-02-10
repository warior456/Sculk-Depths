/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package net.ugi.sculk_depths.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum CrystalType implements StringIdentifiable {
    NONE("none"),
    WHITE("white"),
    BLUE("blue"),
    ORANGE("orange"),
    LIME("lime");


    private final String name;

    private CrystalType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}

