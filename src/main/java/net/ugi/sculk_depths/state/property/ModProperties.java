package net.ugi.sculk_depths.state.property;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.ugi.sculk_depths.util.enums.CrystalType;

public class ModProperties {

    public static final IntegerProperty KRYSLUM_LEVEL = IntegerProperty.of("kryslum", 1, 12);
    public static final IntegerProperty QUAZARITH_LEVEL = IntegerProperty.of("quazarith", 0, 12);
    public static final IntegerProperty CRUX_LEVEL = IntegerProperty.of("crux", 0, 12);
    public static final BooleanProperty HAS_ENERGY_ESSENCE = BooleanProperty.of("has_energy_essence");

    public static final IntegerProperty SPORE_LEVEL = IntegerProperty.of("spore", 1, 12);
    public static final EnumProperty<CrystalType> CRYSTAL_TYPE = EnumProperty.of("crystal", CrystalType.class);

}
