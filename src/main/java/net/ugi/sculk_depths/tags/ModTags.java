package net.ugi.sculk_depths.tags;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.ugi.sculk_depths.SculkDepths;


public class ModTags {

    public static class Blocks {
/*                public static final TagKey<Block> METAL_DETECTOR_DETECTABLE_BLOCKS =
                        createTag("metal_detector_detectable_blocks");*/
        public static final TagKey<Block> COATABLE_BLOCKS =
            tag("coatable_blocks");

        public static final TagKey<Block> LESTER_SPAWN_BLOCKS =
                tag("lester_spawn_blocks");

        public static final TagKey<Block> INCORRECT_FOR_QUAZARITH_TOOL =
                tag("incorrect_for_quazarith_tool");
                
        public static final TagKey<Block> KRYSLUM_FLOWABLE_BLOCKS =
                tag("kryslum_flowable_blocks");

        public static final TagKey<Block> PORTAL_AIR =
                tag("portal_air");

        public static final TagKey<Block> SCULK_VEINS_REPLACABLE =
                tag("sculk_veins_replaceable");


        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, SculkDepths.id( name));
        }
    }

    public static class Items {

        public static final TagKey<Item> CRYSTALS =
                tag("crystals");

        public static final TagKey<Item> CRYSTAL_UPGRADE_ITEMS =
                tag("crystal_upgrade_items");


        public static final TagKey<Item> VALTROX_LOGS =
                tag("valtrox_logs");

        public static final TagKey<Item> COATED_VALTROX_LOGS =
                tag("coated_valtrox_logs");

        public static final TagKey<Item> DRIED_VALTROX_LOGS =
                tag("dried_valtrox_logs");

        public static final TagKey<Item> PETRIFIED_VALTROX_LOGS =
                tag("petrified_valtrox_logs");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, SculkDepths.id( name));
        }
    }

    public static class Fluids {
        public static final TagKey<Fluid> KRYSLUM = of("kryslum");

        private static TagKey<Fluid> of(String name) {
            return TagKey.create(Registries.FLUID, SculkDepths.id( name));
        }
    }

}
