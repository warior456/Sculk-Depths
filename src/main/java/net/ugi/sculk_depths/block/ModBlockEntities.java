package net.ugi.sculk_depths.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.custom.entity.ZygrinFurnaceBlockEntity;

public class ModBlockEntities <T extends BlockEntity>{

    /*public static BlockEntityType<CauldronBlockEntity> FLUMROCK_CAULDRON_BLOCK_ENTITY;*/
    public static BlockEntityType<ZygrinFurnaceBlockEntity> ZYGRIN_FURNACE_BLOCK_ENTITY;

    public static void registerBlockEntities() {
/*        FLUMROCK_CAULDRON_BLOCK_ENTITY = Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                SculkDepths.id("flumrock_cauldron_block_entity"),
                BlockEntityType.Builder.of(CauldronBlockEntity::new, FLUMROCK_CAULDRON, KRYSLUM_FLUMROCK_CAULDRON).build(null));*/
        ZYGRIN_FURNACE_BLOCK_ENTITY = Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                SculkDepths.id("flumrock_cauldron_block_entity"),
                BlockEntityType.Builder.of(ZygrinFurnaceBlockEntity::new, ModBlocks.ZYGRIN_FURNACE).build(null));
    }
}
