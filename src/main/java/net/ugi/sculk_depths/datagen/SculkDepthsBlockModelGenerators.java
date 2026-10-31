package net.ugi.sculk_depths.datagen;

import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.ugi.sculk_depths.block.ModBlocks;

/**
 * Port of the Fabric {@code ModModelProvider} blockstate pass. A few vanilla helpers
 * ({@code family}, {@code woodProvider}, {@code createDoor}, ...) are package-private; they are widened by
 * {@code META-INF/accesstransformer.cfg} so this provider stays a thin wrapper over the vanilla generator and
 * keeps producing exactly the checked-in assets.
 */
final class SculkDepthsBlockModelGenerators extends BlockModelGenerators {
    private final Consumer<BlockStateGenerator> blockStates;
    private final BiConsumer<ResourceLocation, Supplier<JsonElement>> models;

    private SculkDepthsBlockModelGenerators(
            Consumer<BlockStateGenerator> blockStateOutput,
            BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput,
            Consumer<Item> skippedAutoModelsOutput) {
        super(blockStateOutput, modelOutput, skippedAutoModelsOutput);
        this.blockStates = blockStateOutput;
        this.models = modelOutput;
    }

    static void register(
            Consumer<BlockStateGenerator> blockStateOutput,
            BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput,
            Consumer<Item> skippedAutoModelsOutput) {
        new SculkDepthsBlockModelGenerators(blockStateOutput, modelOutput, skippedAutoModelsOutput).runModModels();
    }

    private void runModModels() {
        BlockFamilyProvider valtroxPlankPool = this.family(ModBlocks.VALTROX_PLANKS);
        BlockFamilyProvider coatedValtroxPlankPool = this.family(ModBlocks.COATED_VALTROX_PLANKS);
        BlockFamilyProvider driedValtroxPlankPool = this.family(ModBlocks.DRIED_VALTROX_PLANKS);
        BlockFamilyProvider petrifiedValtroxPlankPool = this.family(ModBlocks.PETRIFIED_VALTROX_SLATES);
        BlockFamilyProvider polishedAmalgamitePool = this.family(ModBlocks.POLISHED_AMALGAMITE);
        BlockFamilyProvider amalgamiteBrickPool = this.family(ModBlocks.AMALGAMITE_BRICKS);
        BlockFamilyProvider flumrockTilesPool = this.family(ModBlocks.FLUMROCK_TILES);

        this.createTrivialCube(ModBlocks.QUAZARITH_BLOCK);
        this.createTrivialCube(ModBlocks.CRUX_BLOCK);

        polishedAmalgamitePool.stairs(ModBlocks.POLISHED_AMALGAMITE_STAIRS);
        polishedAmalgamitePool.slab(ModBlocks.POLISHED_AMALGAMITE_SLAB);
        polishedAmalgamitePool.wall(ModBlocks.POLISHED_AMALGAMITE_WALL);

        this.createTrivialCube(ModBlocks.CRACKED_AMALGAMITE_BRICKS);
        amalgamiteBrickPool.stairs(ModBlocks.AMALGAMITE_BRICK_STAIRS);
        amalgamiteBrickPool.slab(ModBlocks.AMALGAMITE_BRICK_SLAB);
        amalgamiteBrickPool.wall(ModBlocks.AMALGAMITE_BRICK_WALL);

        this.createSingleton(ModBlocks.ACTIVATED_AMALGAMITE, ModelTemplates.CUBE_COLUMN, TextureMapping.column(ModBlocks.ACTIVATED_AMALGAMITE));

        this.createTrivialCube(ModBlocks.CRUMBLING_FLUMROCK_TILES);
        flumrockTilesPool.stairs(ModBlocks.FLUMROCK_TILE_STAIRS);
        flumrockTilesPool.slab(ModBlocks.FLUMROCK_TILE_SLAB);
        flumrockTilesPool.wall(ModBlocks.FLUMROCK_TILE_WALL);

        this.createCrossBlockWithDefaultItem(ModBlocks.DEAD_GRASS, TintState.NOT_TINTED);

        this.createPlant(ModBlocks.VALTROX_SAPLING, ModBlocks.POTTED_VALTROX_SAPLING, TintState.NOT_TINTED);
        this.createPlant(ModBlocks.PENEBRIUM_SHROOM, ModBlocks.POTTED_PENEBRIUM_SHROOM, TintState.NOT_TINTED);
        this.createPlant(ModBlocks.AURIC_SPORE_SPROUTS, ModBlocks.POTTED_AURIC_SPORE_SPROUTS, TintState.NOT_TINTED);

        valtroxPlankPool.fenceGate(ModBlocks.VALTROX_FENCE_GATE);
        valtroxPlankPool.fence(ModBlocks.VALTROX_FENCE);
        valtroxPlankPool.button(ModBlocks.VALTROX_BUTTON);
        valtroxPlankPool.slab(ModBlocks.VALTROX_SLAB);
        valtroxPlankPool.stairs(ModBlocks.VALTROX_STAIRS);
        this.woodProvider(ModBlocks.VALTROX_LOG).logWithHorizontal(ModBlocks.VALTROX_LOG).wood(ModBlocks.VALTROX_WOOD);
        this.woodProvider(ModBlocks.STRIPPED_VALTROX_LOG).logWithHorizontal(ModBlocks.STRIPPED_VALTROX_LOG).wood(ModBlocks.STRIPPED_VALTROX_WOOD);
        valtroxPlankPool.pressurePlate(ModBlocks.VALTROX_PRESSURE_PLATE);
        valtroxPlankPool.generateFor(new BlockFamily.Builder(ModBlocks.VALTROX_PLANKS)
                .sign(ModBlocks.VALTROX_SIGN, ModBlocks.VALTROX_WALL_SIGN)
                .getFamily());
        this.createHangingSign(ModBlocks.STRIPPED_VALTROX_LOG, ModBlocks.VALTROX_HANGING_SIGN, ModBlocks.VALTROX_WALL_HANGING_SIGN);
        this.createDoor(ModBlocks.VALTROX_DOOR);
        this.createOrientableTrapdoor(ModBlocks.VALTROX_TRAPDOOR);

        coatedValtroxPlankPool.fenceGate(ModBlocks.COATED_VALTROX_FENCE_GATE);
        coatedValtroxPlankPool.fence(ModBlocks.COATED_VALTROX_FENCE);
        coatedValtroxPlankPool.button(ModBlocks.COATED_VALTROX_BUTTON);
        coatedValtroxPlankPool.slab(ModBlocks.COATED_VALTROX_SLAB);
        coatedValtroxPlankPool.stairs(ModBlocks.COATED_VALTROX_STAIRS);
        this.woodProvider(ModBlocks.COATED_VALTROX_LOG).logWithHorizontal(ModBlocks.COATED_VALTROX_LOG).wood(ModBlocks.COATED_VALTROX_WOOD);
        this.woodProvider(ModBlocks.COATED_STRIPPED_VALTROX_LOG).logWithHorizontal(ModBlocks.COATED_STRIPPED_VALTROX_LOG).wood(ModBlocks.COATED_STRIPPED_VALTROX_WOOD);
        coatedValtroxPlankPool.pressurePlate(ModBlocks.COATED_VALTROX_PRESSURE_PLATE);
        coatedValtroxPlankPool.generateFor(new BlockFamily.Builder(ModBlocks.COATED_VALTROX_PLANKS)
                .sign(ModBlocks.COATED_VALTROX_SIGN, ModBlocks.COATED_VALTROX_WALL_SIGN)
                .getFamily());
        this.createHangingSign(ModBlocks.COATED_STRIPPED_VALTROX_LOG, ModBlocks.COATED_VALTROX_HANGING_SIGN, ModBlocks.COATED_VALTROX_WALL_HANGING_SIGN);
        this.createDoor(ModBlocks.COATED_VALTROX_DOOR);
        this.createOrientableTrapdoor(ModBlocks.COATED_VALTROX_TRAPDOOR);

        driedValtroxPlankPool.fenceGate(ModBlocks.DRIED_VALTROX_FENCE_GATE);
        driedValtroxPlankPool.fence(ModBlocks.DRIED_VALTROX_FENCE);
        driedValtroxPlankPool.button(ModBlocks.DRIED_VALTROX_BUTTON);
        driedValtroxPlankPool.slab(ModBlocks.DRIED_VALTROX_SLAB);
        driedValtroxPlankPool.stairs(ModBlocks.DRIED_VALTROX_STAIRS);
        this.woodProvider(ModBlocks.DRIED_VALTROX_LOG).logWithHorizontal(ModBlocks.DRIED_VALTROX_LOG).wood(ModBlocks.DRIED_VALTROX_WOOD);
        this.woodProvider(ModBlocks.STRIPPED_DRIED_VALTROX_LOG).logWithHorizontal(ModBlocks.STRIPPED_DRIED_VALTROX_LOG).wood(ModBlocks.STRIPPED_DRIED_VALTROX_WOOD);
        driedValtroxPlankPool.pressurePlate(ModBlocks.DRIED_VALTROX_PRESSURE_PLATE);
        driedValtroxPlankPool.generateFor(new BlockFamily.Builder(ModBlocks.DRIED_VALTROX_PLANKS)
                .sign(ModBlocks.DRIED_VALTROX_SIGN, ModBlocks.DRIED_VALTROX_WALL_SIGN)
                .getFamily());
        this.createHangingSign(ModBlocks.STRIPPED_DRIED_VALTROX_LOG, ModBlocks.DRIED_VALTROX_HANGING_SIGN, ModBlocks.DRIED_VALTROX_WALL_HANGING_SIGN);
        this.createDoor(ModBlocks.DRIED_VALTROX_DOOR);
        this.createOrientableTrapdoor(ModBlocks.DRIED_VALTROX_TRAPDOOR);

        petrifiedValtroxPlankPool.fenceGate(ModBlocks.PETRIFIED_VALTROX_WALL_GATE);
        petrifiedValtroxPlankPool.wall(ModBlocks.PETRIFIED_VALTROX_WALL);
        petrifiedValtroxPlankPool.button(ModBlocks.PETRIFIED_VALTROX_BUTTON);
        petrifiedValtroxPlankPool.slab(ModBlocks.PETRIFIED_VALTROX_SLAB);
        petrifiedValtroxPlankPool.stairs(ModBlocks.PETRIFIED_VALTROX_STAIRS);
        this.woodProvider(ModBlocks.PETRIFIED_VALTROX_LOG).logWithHorizontal(ModBlocks.PETRIFIED_VALTROX_LOG).wood(ModBlocks.PETRIFIED_VALTROX_WOOD);
        this.woodProvider(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG).logWithHorizontal(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG).wood(ModBlocks.STRIPPED_PETRIFIED_VALTROX_WOOD);
        petrifiedValtroxPlankPool.pressurePlate(ModBlocks.PETRIFIED_VALTROX_PRESSURE_PLATE);
        petrifiedValtroxPlankPool.generateFor(new BlockFamily.Builder(ModBlocks.PETRIFIED_VALTROX_SLATES)
                .sign(ModBlocks.PETRIFIED_VALTROX_SIGN, ModBlocks.PETRIFIED_VALTROX_WALL_SIGN)
                .getFamily());
        this.createHangingSign(ModBlocks.STRIPPED_PETRIFIED_VALTROX_LOG, ModBlocks.PETRIFIED_VALTROX_HANGING_SIGN, ModBlocks.PETRIFIED_VALTROX_WALL_HANGING_SIGN);
        this.createDoor(ModBlocks.PETRIFIED_VALTROX_DOOR);
        this.createOrientableTrapdoor(ModBlocks.PETRIFIED_VALTROX_TRAPDOOR);

        this.createBlockStateWithVariants(ModBlocks.AMALGAMITE, List.of("", "2", "3", "4"));
    }

    private void createSingleton(Block block, ModelTemplate template, TextureMapping mapping) {
        ResourceLocation model = template.create(ModelLocationUtils.getModelLocation(block), mapping, this.models);
        this.blockStates.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)));
    }

    private void createBlockStateWithVariants(Block block, List<String> suffixes) {
        List<ResourceLocation> models = new ArrayList<>();
        for (String suffix : suffixes) {
            ResourceLocation modelId = ModelLocationUtils.getModelLocation(block).withSuffix(suffix);
            TextureMapping mapping = new TextureMapping()
                    .put(TextureSlot.END, ModelLocationUtils.getModelLocation(block).withSuffix("_end" + suffix))
                    .put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(block).withSuffix("_side" + suffix));
            ModelTemplates.CUBE_COLUMN.create(modelId, mapping, this.models);
            models.add(modelId);
        }

        Variant[] variants = new Variant[models.size()];
        for (int i = 0; i < models.size(); i++) {
            variants[i] = Variant.variant().with(VariantProperties.MODEL, models.get(i));
        }
        this.blockStates.accept(MultiVariantGenerator.multiVariant(block, variants));
    }
}
