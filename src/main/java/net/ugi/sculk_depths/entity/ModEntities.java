package net.ugi.sculk_depths.entity;


import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.custom.AuricCentipedeEntity;
import net.ugi.sculk_depths.entity.custom.ChomperColossusEntity;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;
import net.ugi.sculk_depths.entity.custom.LesterEntity;

public class ModEntities {
    public static final EntityType<GlomperEntity> GLOMPER = Registry.register(Registries.ENTITY_TYPE,
            SculkDepths.identifier( "glomper"),
            EntityType.Builder.create(GlomperEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.0f, 1.0f).build());

    public static final EntityType<LesterEntity> LESTER = Registry.register(Registries.ENTITY_TYPE,
            SculkDepths.identifier( "lester"),
            EntityType.Builder.create(LesterEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.7f, 1.6f).build());

    public static final EntityType<ChomperColossusEntity> CHOMPER_COLOSSUS = Registry.register(Registries.ENTITY_TYPE,
            SculkDepths.identifier( "chomper_colossus"),
            EntityType.Builder.create(ChomperColossusEntity::new, SpawnGroup.MONSTER)
                    .dimensions(8,10).build());

    public static final EntityType<AuricCentipedeEntity> AURIC_CENTIPEDE = Registry.register(Registries.ENTITY_TYPE,
            SculkDepths.identifier( "auric_centipede"),
            EntityType.Builder.create(AuricCentipedeEntity::new, SpawnGroup.MONSTER)
                    .dimensions(8,10).build());
}