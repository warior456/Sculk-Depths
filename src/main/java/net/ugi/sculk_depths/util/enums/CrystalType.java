package net.ugi.sculk_depths.util.enums;

import net.minecraft.util.StringRepresentable;

public enum CrystalType implements StringRepresentable {
    NONE("none"),
    WHITE("white"),
    COLOR_BLUE("blue"),
    ORANGE("orange"),
    COLOR_LIGHT_GREEN("lime");


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

