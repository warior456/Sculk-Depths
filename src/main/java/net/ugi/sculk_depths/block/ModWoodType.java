package net.ugi.sculk_depths.block;

import net.minecraft.world.level.block.state.properties.WoodType;
import net.ugi.sculk_depths.SculkDepths;

public class ModWoodType {
    public static final WoodType VALTROX = WoodType.register(new WoodType(SculkDepths.id("valtrox").toString(), ModBlockSetType.VALTROX));
    public static final WoodType COATED_VALTROX = WoodType.register(new WoodType(SculkDepths.id("coated_valtrox").toString(), ModBlockSetType.COATED_VALTROX));
    public static final WoodType DRIED_VALTROX = WoodType.register(new WoodType(SculkDepths.id("dried_valtrox").toString(), ModBlockSetType.DRIED_VALTROX));
    public static final WoodType PETRIFIED_VALTROX = WoodType.register(new WoodType(SculkDepths.id("petrified_valtrox").toString(), ModBlockSetType.PETRIFIED_VALTROX));

    public void init() {
    }
}