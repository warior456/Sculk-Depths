package net.ugi.sculk_depths.tags;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;


public class ModTags {

        public static class Blocks {
/*                public static final TagKey<Block> METAL_DETECTOR_DETECTABLE_BLOCKS =
                        createTag("metal_detector_detectable_blocks");*/

                private static TagKey<Block> createTag(String name) {
                        return TagKey.of(RegistryKeys.BLOCK, new Identifier(SculkDepths.MOD_ID, name));
                }
        }

        public static class Items {

                public static final TagKey<Item> CRYSTALS =
                        createTag("crystals");

                public static final TagKey<Item> CRYSTAL_UPGRADE_ITEMS =
                        createTag("crystal_upgrade_items");
                private static TagKey<Item> createTag(String name) {
                        return TagKey.of(RegistryKeys.ITEM, new Identifier(SculkDepths.MOD_ID, name));
                }
        }

}
