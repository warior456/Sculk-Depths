package net.ugi.sculk_depths.block;

import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModWoodType {
    public static final WoodType VALTROX = WoodTypeBuilder.copyOf(WoodType.OAK).register(
            new Identifier(SculkDepths.MOD_ID, "valtrox"), ModBlockSetType.VALTROX);

    public static final WoodType DRIED_VALTROX = WoodTypeBuilder.copyOf(WoodType.OAK).register(
            new Identifier(SculkDepths.MOD_ID, "dried_valtrox"), ModBlockSetType.DRIED_VALTROX);

    public static final WoodType PETRIFIED_VALTROX = WoodTypeBuilder.copyOf(WoodType.OAK).register(
            new Identifier(SculkDepths.MOD_ID, "petrified_valtrox"), ModBlockSetType.PETRIFIED_VALTROX);

    public void init() {
    }
}