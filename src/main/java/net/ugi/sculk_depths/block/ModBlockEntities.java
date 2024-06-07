package net.ugi.sculk_depths.block;


import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.custom.entity.ZygrinFurnaceBlockEntity;

public class ModBlockEntities <T extends BlockEntity>{

    /*public static BlockEntityType<CauldronBlockEntity> FLUMROCK_CAULDRON_BLOCK_ENTITY;*/
    public static BlockEntityType<ZygrinFurnaceBlockEntity> ZYGRIN_FURNACE_BLOCK_ENTITY;

    public static void registerBlockEntities() {
/*        FLUMROCK_CAULDRON_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(SculkDepths.MOD_ID, "flumrock_cauldron_block_entity"),
                FabricBlockEntityTypeBuilder.create(CauldronBlockEntity::new, FLUMROCK_CAULDRON, KRYSLUM_FLUMROCK_CAULDRON).build());*/
        ZYGRIN_FURNACE_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(SculkDepths.MOD_ID, "flumrock_cauldron_block_entity"),
                FabricBlockEntityTypeBuilder.create(ZygrinFurnaceBlockEntity::new, ModBlocks.ZYGRIN_FURNACE).build());
        //public static final BlockEntityType<FurnaceBlockEntity> FURNACE = BlockEntityType.create("furnace", BlockEntityType.Builder.create(FurnaceBlockEntity::new, Blocks.FURNACE));
    }

}
