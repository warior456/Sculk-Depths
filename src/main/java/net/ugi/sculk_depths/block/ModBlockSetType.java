package net.ugi.sculk_depths.block;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.block.BlockSetType;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModBlockSetType {
    public static final BlockSetType VALTROX = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).register(
            new Identifier(SculkDepths.MOD_ID, "valtrox"));

    public static final BlockSetType DRIED_VALTROX = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).register(
            new Identifier(SculkDepths.MOD_ID, "dried_valtrox"));

    public static final BlockSetType PETRIFIED_VALTROX = BlockSetTypeBuilder.copyOf(BlockSetType.STONE).register(
            new Identifier(SculkDepths.MOD_ID, "petrified_valtrox"));

    public void init() {
    }
}