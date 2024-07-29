package net.ugi.sculk_depths.block;

import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModWoodType {
    public static final WoodType VALTROX = WoodTypeBuilder.copyOf(WoodType.OAK).register(
            SculkDepths.identifier( "valtrox"), ModBlockSetType.VALTROX);

    public static final WoodType COATED_VALTROX = WoodTypeBuilder.copyOf(WoodType.OAK).register(
            SculkDepths.identifier( "valtrox"), ModBlockSetType.COATED_VALTROX);

    public static final WoodType DRIED_VALTROX = WoodTypeBuilder.copyOf(WoodType.OAK).register(
            SculkDepths.identifier( "dried_valtrox"), ModBlockSetType.DRIED_VALTROX);

    public static final WoodType PETRIFIED_VALTROX = WoodTypeBuilder.copyOf(WoodType.OAK).register(
            SculkDepths.identifier( "petrified_valtrox"), ModBlockSetType.PETRIFIED_VALTROX);

    public void init() {
    }
}