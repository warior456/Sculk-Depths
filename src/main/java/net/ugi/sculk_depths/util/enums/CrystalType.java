package net.ugi.sculk_depths.util.enums;

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

