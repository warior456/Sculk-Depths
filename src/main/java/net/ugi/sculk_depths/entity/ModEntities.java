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
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GlomperEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.0f)).build());

    public static final EntityType<LesterEntity> LESTER = Registry.register(Registries.ENTITY_TYPE,
            SculkDepths.identifier( "lester"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, LesterEntity::new)
                    .dimensions(EntityDimensions.fixed(0.7f, 1.6f)).build());

    public static final EntityType<ChomperColossusEntity> CHOMPER_COLOSSUS = Registry.register(Registries.ENTITY_TYPE,
            SculkDepths.identifier( "chomper_colossus"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChomperColossusEntity::new)
                    .dimensions(EntityDimensions.fixed(8,11)).build());

    public static final EntityType<AuricCentipedeEntity> AURIC_CENTIPEDE = Registry.register(Registries.ENTITY_TYPE,
            SculkDepths.identifier( "auric_centipede"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, AuricCentipedeEntity::new)
                    .dimensions(EntityDimensions.fixed(1,1)).build());
}