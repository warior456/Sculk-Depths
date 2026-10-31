package net.ugi.sculk_depths.datagen;

import com.google.gson.JsonElement;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.ugi.sculk_depths.item.ModItems;

/**
 * Port of the Fabric {@code FabricModelProvider} model pass. Only the mod's own blocks are generated; all other
 * blockstates/models are handwritten resources and are left untouched.
 */
public class ModModelProvider implements DataProvider {
    private final PackOutput.PathProvider blockStatePathProvider;
    private final PackOutput.PathProvider modelPathProvider;

    public ModModelProvider(PackOutput output) {
        this.blockStatePathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        this.modelPathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Map<Block, BlockStateGenerator> blockStates = new HashMap<>();
        Map<ResourceLocation, Supplier<JsonElement>> models = new HashMap<>();
        Set<Item> skippedAutoModels = new HashSet<>();

        SculkDepthsBlockModelGenerators.register(
                generator -> {
                    BlockStateGenerator previous = blockStates.put(generator.getBlock(), generator);
                    if (previous != null) {
                        throw new IllegalStateException("Duplicate blockstate definition for " + generator.getBlock());
                    }
                },
                (location, model) -> {
                    Supplier<JsonElement> previous = models.put(location, model);
                    if (previous != null) {
                        throw new IllegalStateException("Duplicate model definition for " + location);
                    }
                },
                skippedAutoModels::add);

        createFlatItemModel(ModItems.CRUX, models);
        createFlatItemModel(ModItems.MUSIC_DISC_ZINNIA, models);

        // Same delegation as vanilla ModelProvider, but only for the blocks this provider generated models for.
        // All other mod blocks (zygrin, umbrusk, ...) have handwritten assets and must not get a generated item model.
        blockStates.keySet().forEach(block -> {
            Item item = Item.BY_BLOCK.get(block);
            if (item == null || item == Items.AIR || skippedAutoModels.contains(item)) {
                return;
            }
            ResourceLocation itemModel = ModelLocationUtils.getModelLocation(item);
            if (!models.containsKey(itemModel)) {
                models.put(itemModel, new DelegatedModel(ModelLocationUtils.getModelLocation(block)));
            }
        });

        return CompletableFuture.allOf(
                this.saveCollection(cache, blockStates,
                        block -> this.blockStatePathProvider.json(block.builtInRegistryHolder().key().location())),
                this.saveCollection(cache, models, this.modelPathProvider::json));
    }

    private static void createFlatItemModel(Item item, Map<ResourceLocation, Supplier<JsonElement>> models) {
        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(item), models::put);
    }

    private <T> CompletableFuture<?> saveCollection(
            CachedOutput cache, Map<T, ? extends Supplier<JsonElement>> collection, Function<T, Path> pathProvider) {
        return CompletableFuture.allOf(collection.entrySet().stream().map(entry -> {
            Path path = pathProvider.apply(entry.getKey());
            return DataProvider.saveStable(cache, entry.getValue().get(), path);
        }).toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Model Definitions";
    }
}
