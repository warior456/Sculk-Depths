package net.ugi.sculk_depths;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.ugi.sculk_depths.datagen.ModBlockTagProvider;
import net.ugi.sculk_depths.datagen.ModItemTagProvider;
import net.ugi.sculk_depths.datagen.ModLootTableProvider;
import net.ugi.sculk_depths.datagen.ModModelProvider;
import net.ugi.sculk_depths.datagen.ModRecipeProvider;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = SculkDepths.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class SculkDepthsDataGenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		ModBlockTagProvider blockTags = new ModBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
		generator.addProvider(event.includeServer(), blockTags);
		generator.addProvider(event.includeServer(), new ModItemTagProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
		generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, lookupProvider));
		generator.addProvider(event.includeServer(), new ModLootTableProvider(packOutput, lookupProvider));
		generator.addProvider(event.includeClient(), new ModModelProvider(packOutput));
	}
}
