package net.ugi.sculk_depths.entity;


import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.custom.ChomperColossusEntity;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;
import net.ugi.sculk_depths.entity.custom.LesterEntity;

public class ModEntities {
    public static EntityType<GlomperEntity> GLOMPER;
    public static EntityType<LesterEntity> LESTER;
    public static EntityType<ChomperColossusEntity> CHOMPER_COLOSSUS;

    public static void registerModEntities() {
        GLOMPER = Registry.register(BuiltInRegistries.ENTITY_TYPE,
                SculkDepths.id("glomper"),
                EntityType.Builder.of(GlomperEntity::new, MobCategory.MONSTER)
                        .sized(1.0f, 1.0f).build("glomper"));

        LESTER = Registry.register(BuiltInRegistries.ENTITY_TYPE,
                SculkDepths.id("lester"),
                EntityType.Builder.of(LesterEntity::new, MobCategory.MONSTER)
                        .sized(0.7f, 1.6f).build("lester"));

        CHOMPER_COLOSSUS = Registry.register(BuiltInRegistries.ENTITY_TYPE,
                SculkDepths.id("chomper_colossus"),
                EntityType.Builder.of(ChomperColossusEntity::new, MobCategory.MONSTER)
                        .sized(8, 10).build("chomper_colossus"));
    }
}
