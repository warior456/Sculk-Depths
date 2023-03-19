package net.ugi.sculk_depths.portal;

import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.item.ModItems;

public class Portals {
    public static void registerModPortals() {
        CustomPortalBuilder.beginPortal()
                .frameBlock(Blocks.REINFORCED_DEEPSLATE)
                //.customPortalBlock(Blocks.SCULK)
                .destDimID(new Identifier("sculk_depths:sculk_depthsdim"))
                .tintColor(0, 115, 150)
                .lightWithItem(ModItems.CITRINE)
                .registerPortal();
    }
}
