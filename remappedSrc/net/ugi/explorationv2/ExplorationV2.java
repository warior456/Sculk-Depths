package net.ugi.explorationv2;

import net.fabricmc.api.ModInitializer;
import net.ugi.explorationv2.block.ModBlocks;
import net.ugi.explorationv2.item.ModItemGroup;
import net.ugi.explorationv2.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplorationV2 implements ModInitializer {
	public  static final String MOD_ID = "explorationv2";
	public static final Logger LOGGER = LoggerFactory.getLogger("explorationv2");

	@Override
	public void onInitialize() {
		ModItemGroup.registerItemgroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		LOGGER.info("explorationv2 has loaded");
	}
}
