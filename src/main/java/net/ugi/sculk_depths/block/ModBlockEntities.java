package net.ugi.sculk_depths.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.entity.CauldronBlockEntity;

import static net.ugi.sculk_depths.block.ModBlocks.FLUMROCK_CAULDRON;
import static net.ugi.sculk_depths.block.ModBlocks.KRYSLUM_FLUMROCK_CAULDRON;

public class ModBlockEntities {

    public static BlockEntityType<CauldronBlockEntity> FLUMROCK_CAULDRON_BLOCK_ENTITY;

    public static void registerBlockEntities() {
                FLUMROCK_CAULDRON_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(SculkDepths.MOD_ID, "flumrock_cauldron_block_entity"),
                FabricBlockEntityTypeBuilder.create(CauldronBlockEntity::new, FLUMROCK_CAULDRON , KRYSLUM_FLUMROCK_CAULDRON).build());
    }

}
