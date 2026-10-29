package net.ugi.sculk_depths.block;

import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.ugi.sculk_depths.SculkDepths;

public class ModWoodType {
    public static final WoodType VALTROX = WoodTypeBuilder.copyOf(WoodType.OAK).register(
            SculkDepths.id( "valtrox"), ModBlockSetType.VALTROX);

    public static final WoodType COATED_VALTROX = WoodTypeBuilder.copyOf(WoodType.OAK).register(
            SculkDepths.id( "valtrox"), ModBlockSetType.COATED_VALTROX);

    public static final WoodType DRIED_VALTROX = WoodTypeBuilder.copyOf(WoodType.OAK).register(
            SculkDepths.id( "dried_valtrox"), ModBlockSetType.DRIED_VALTROX);

    public static final WoodType PETRIFIED_VALTROX = WoodTypeBuilder.copyOf(WoodType.OAK).register(
            SculkDepths.id( "petrified_valtrox"), ModBlockSetType.PETRIFIED_VALTROX);

    public void init() {
    }
}