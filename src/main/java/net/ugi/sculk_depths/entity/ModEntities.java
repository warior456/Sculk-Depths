package net.ugi.sculk_depths.entity;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.custom.ChomperColossusEntity;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;
import net.ugi.sculk_depths.entity.custom.LesterEntity;

public class ModEntities {
    public static final EntityType<GlomperEntity> GLOMPER = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            SculkDepths.id( "glomper"),
            EntityType.Builder.create(GlomperEntity::new, MobCategory.MONSTER)
                    .material(1.0f, 1.0f).build());

    public static final EntityType<LesterEntity> LESTER = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            SculkDepths.id( "lester"),
            EntityType.Builder.create(LesterEntity::new, MobCategory.MONSTER)
                    .material(0.7f, 1.6f).build());

    public static final EntityType<ChomperColossusEntity> CHOMPER_COLOSSUS = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            SculkDepths.id( "chomper_colossus"),
            EntityType.Builder.create(ChomperColossusEntity::new, MobCategory.MONSTER)
                    .material(8,10).build());
}