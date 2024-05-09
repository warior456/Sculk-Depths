package net.ugi.sculk_depths.state.property;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.ugi.sculk_depths.block.enums.CrystalType;

public class ModProperties {

    public static final IntProperty KRYSLUM_LEVEL = IntProperty.of("kryslum", 1, 12);
    public static final IntProperty QUAZARITH_LEVEL = IntProperty.of("quazarith", 0, 12);
    public static final IntProperty CRUX_LEVEL = IntProperty.of("crux", 0, 12);

    public static final IntProperty SPORE_LEVEL = IntProperty.of("spore", 1, 12);
    public static final EnumProperty<CrystalType> CRYSTAL_TYPE = EnumProperty.of("crystal", CrystalType.class);

}
