package net.ugi.sculk_depths.portal;


import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.item.ModItems;

public class Portals {
    public static void registerModPortals() {
        CustomPortalBuilder.beginPortal()
                .frameBlock(Blocks.REINFORCED_DEEPSLATE)
                //.customPortalBlock(Blocks.SCULK)
                .destDimID(new Identifier("sculk_depths:sculk_depthsdim"))
                .tintColor(0, 115, 150)
                .lightWithItem(ModItems.SOUL_HEART)
                .setPortalSearchYRange(0,120)
                .registerIgniteEvent((player, world, portalPos, framePos, portalIgnitionSource) -> {
                    if (portalIgnitionSource.sourceType == PortalIgnitionSource.SourceType.USEITEM && player != null) {
                        if (player.isCreative())
                            return;
                        ItemStack heldItem = player.getMainHandStack().getItem() == ModItems.SOUL_HEART ?
                                player.getMainHandStack() : player.getOffHandStack();
                        heldItem.decrement(1);
                    }
                })
                .registerPortal();

    }
}
