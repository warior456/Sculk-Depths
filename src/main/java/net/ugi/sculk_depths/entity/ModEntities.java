package net.ugi.sculk_depths.entity;


import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.custom.GlomperEntity;

public class ModEntities {
    public static final EntityType<GlomperEntity> GLOMPER = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(SculkDepths.MOD_ID, "glomper"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GlomperEntity::new)
                    .dimensions(EntityDimensions.fixed(1.5f, 1.5f)).build());
}