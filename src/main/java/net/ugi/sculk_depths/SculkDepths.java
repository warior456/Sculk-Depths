package net.ugi.sculk_depths;

import net.fabricmc.api.ModInitializer;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItemGroup;
import net.ugi.sculk_depths.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SculkDepths implements ModInitializer {
	public  static final String MOD_ID = "sculk_depths";
	public static final Logger LOGGER = LoggerFactory.getLogger("sculk_depths");

	@Override
	public void onInitialize() {
		ModItemGroup.registerItemgroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		CustomPortalBuilder.beginPortal()
				.frameBlock(Blocks.REINFORCED_DEEPSLATE)
				//.customPortalBlock(Blocks.SCULK)
				.destDimID(new Identifier("sculk_depths:sculk_depthsdim"))
				.tintColor(0, 115, 150)
				.lightWithItem(ModItems.CITRINE)
				.registerPortal();
		LOGGER.info("sculk_depths has loaded");
	}
}
