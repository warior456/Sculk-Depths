package net.ugi.sculk_depths.portal;


/*import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;*/
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.block.ModBlocks;

public class Portals {
    /*public final static PortalIgnitionSource SOUL_FIRE = PortalIgnitionSource.CustomSource(new Identifier("sculk_depths", "soul_fire"));//todo portalapi

    public static void registerModPortals() {
        CustomPortalBuilder.beginPortal()
                .frameBlock(Blocks.REINFORCED_DEEPSLATE)
                .customPortalBlock(ModBlocks.SCULK_DEPTHS_PORTAL)
                .destDimID(new Identifier("sculk_depths:sculk_depths"))
                .tintColor(1, 69, 86)
                .customIgnitionSource(SOUL_FIRE)
                //.lightWithItem(ModItems.ENERGISED_FLINT_AND_STEEL)
                .setPortalSearchYRange(0, 120)
                *//*
                .registerIgniteEvent((player, world, portalPos, framePos, portalIgnitionSource) -> {
                    if (portalIgnitionSource.sourceType == PortalIgnitionSource.SourceType.USEITEM && player != null) {
                        if (player.isCreative())
                            return;
                        ItemStack heldItem = player.getMainHandStack().getItem() == ModItems.ENERGISED_FLINT_AND_STEEL ?
                                player.getMainHandStack() : player.getOffHandStack();
                        heldItem.damage(SculkDepths.CONFIG.activate_portal_durability_usage , player, p -> p.sendToolBreakStatus(player.getActiveHand()));
                        //heldItem.setDamage(heldItem.getDamage() + 10); //if above breaks use this with a custom durability check
                    }
                })
                 *//*
                .registerPortal();

    }*/
}
