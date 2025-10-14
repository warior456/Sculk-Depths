package net.ugi.sculk_depths.block;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.custom.entity.ZygrinFurnaceBlockEntity;

public class ModBlockEntities {

    /*public static BlockEntityType<CauldronBlockEntity> FLUMROCK_CAULDRON_BLOCK_ENTITY;*/
    public static final BlockEntityType<ZygrinFurnaceBlockEntity> ZYGRIN_FURNACE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            SculkDepths.identifier( "flumrock_cauldron_block_entity"),
            BlockEntityType.Builder.create(ZygrinFurnaceBlockEntity::new, ModBlocks.ZYGRIN_FURNACE).build());

    public static void registerBlockEntities() {
/*        FLUMROCK_CAULDRON_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                SculkDepths.identifier( "flumrock_cauldron_block_entity"),
                FabricBlockEntityTypeBuilder.create(CauldronBlockEntity::new, FLUMROCK_CAULDRON, KRYSLUM_FLUMROCK_CAULDRON).build());*/
        //public static final BlockEntityType<FurnaceBlockEntity> FURNACE = BlockEntityType.create("furnace", BlockEntityType.Builder.create(FurnaceBlockEntity::new, Blocks.FURNACE));
    }
}
