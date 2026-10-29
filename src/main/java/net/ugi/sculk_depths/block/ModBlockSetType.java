package net.ugi.sculk_depths.block;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.ugi.sculk_depths.SculkDepths;

public class ModBlockSetType {
    public static final BlockSetType VALTROX = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).register(
            SculkDepths.id( "valtrox"));

    public static final BlockSetType COATED_VALTROX = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).register(
            SculkDepths.id( "valtrox"));
    public static final BlockSetType DRIED_VALTROX = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).register(
            SculkDepths.id( "dried_valtrox"));

    public static final BlockSetType PETRIFIED_VALTROX = BlockSetTypeBuilder.copyOf(BlockSetType.STONE).register(
            SculkDepths.id( "petrified_valtrox"));

    public void init() {
    }
}