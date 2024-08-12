package net.ugi.sculk_depths.tags;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;


public class ModTags {

    public static class Blocks {
/*                public static final TagKey<Block> METAL_DETECTOR_DETECTABLE_BLOCKS =
                        createTag("metal_detector_detectable_blocks");*/
        public static final TagKey<Block> COATABLE_BLOCKS =
            createTag("coatable_blocks");

        public static final TagKey<Block> LESTER_SPAWN_BLOCKS =
                createTag("lester_spawn_blocks");

        public static final TagKey<Block> INCORRECT_FOR_QUAZARITH_TOOL =
                createTag("incorrect_for_quazarith_tool");
                
        public static final TagKey<Block> KRYSLUM_FLOWABLE_BLOCKS =
                createTag("kryslum_flowable_blocks");


        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, SculkDepths.identifier( name));
        }
    }

    public static class Items {

        public static final TagKey<Item> CRYSTALS =
                createTag("crystals");

        public static final TagKey<Item> CRYSTAL_UPGRADE_ITEMS =
                createTag("crystal_upgrade_items");


        public static final TagKey<Item> VALTROX_LOGS =
                createTag("valtrox_logs");

        public static final TagKey<Item> COATED_VALTROX_LOGS =
                createTag("coated_valtrox_logs");

        public static final TagKey<Item> DRIED_VALTROX_LOGS =
                createTag("dried_valtrox_logs");

        public static final TagKey<Item> PETRIFIED_VALTROX_LOGS =
                createTag("petrified_valtrox_logs");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, SculkDepths.identifier( name));
        }
    }

    public static class Fluids {
        public static final TagKey<Fluid> KRYSLUM = of("kryslum");

        private static TagKey<Fluid> of(String name) {
            return TagKey.of(RegistryKeys.FLUID, SculkDepths.identifier( name));
        }
    }

}
