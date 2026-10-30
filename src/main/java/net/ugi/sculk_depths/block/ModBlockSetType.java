package net.ugi.sculk_depths.block;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.ugi.sculk_depths.SculkDepths;

public class ModBlockSetType {
    public static final BlockSetType VALTROX = BlockSetType.register(new BlockSetType(SculkDepths.id("valtrox").toString()));
    public static final BlockSetType COATED_VALTROX = BlockSetType.register(new BlockSetType(SculkDepths.id("coated_valtrox").toString()));
    public static final BlockSetType DRIED_VALTROX = BlockSetType.register(new BlockSetType(SculkDepths.id("dried_valtrox").toString()));
    public static final BlockSetType PETRIFIED_VALTROX = BlockSetType.register(new BlockSetType(SculkDepths.id("petrified_valtrox").toString()));

    public void init() {
    }
}