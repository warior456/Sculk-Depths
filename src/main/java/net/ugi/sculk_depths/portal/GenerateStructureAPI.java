package net.ugi.sculk_depths.portal;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.StructureSpawns;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.Structure;

import java.util.Optional;

public class GenerateStructureAPI {

    public static boolean generateStructure(World  originalWorld, RegistryKey<World> targetWorldKey, Identifier structure, BlockPos pos){
        Optional<RegistryEntry.Reference<Structure>> structureReference = originalWorld.getRegistryManager().get(RegistryKeys.STRUCTURE).getEntry(structure);
        if(structureReference.isEmpty()) return false;
        return  generate(originalWorld, targetWorldKey, structureReference.get(), pos);
/*        originalWorld.getRegistryManager()// gets the dynamic registry manager
                .getOptional(RegistryKeys.STRUCTURE)// gets an optional wrapped structure registry from it
                .flatMap(registry-> // if it exists, map it to:
                        registry.getEntry(SculkDepths.identifier("portal_structure")))// an optional registry entry reference. thats the end of the mapping function
                .ifPresent(structure_-> {// if the value is present at all, do something with the remapped value
                    System.out.println("0");
                    GenerateStructureAPI.generateStructure(originalWorld, ModDimensions.SCULK_DEPTHS_LEVEL_KEY, structure_, pos);
                });*/
    }

    public static boolean generateStructure(World  originalWorld, RegistryKey<World> targetWorldKey, RegistryKey<Structure> structure, BlockPos pos){
        Optional<RegistryEntry.Reference<Structure>> structureReference = originalWorld.getRegistryManager().get(RegistryKeys.STRUCTURE).getEntry(structure);
        if(structureReference.isEmpty()) return false;
        return  generate(originalWorld, targetWorldKey, structureReference.get(), pos);
    }



    private static boolean generate(World  originalWorld, RegistryKey<World> targetWorldKey, RegistryEntry.Reference<Structure> structure, BlockPos pos){
        if(originalWorld.isClient)return false;
        System.out.println("1");
        ServerWorld serverWorld = originalWorld.getServer().getWorld(targetWorldKey);
        Structure structure2 = structure.value();
        ChunkGenerator chunkGenerator = serverWorld.getChunkManager().getChunkGenerator();
        StructureStart structureStart = structure2.createStructureStart(
                originalWorld.getRegistryManager(),
                chunkGenerator,
                chunkGenerator.getBiomeSource(),
                serverWorld.getChunkManager().getNoiseConfig(),
                serverWorld.getStructureTemplateManager(),
                originalWorld.getServer().getWorld(World.OVERWORLD).getSeed(),//maybe fix rotation
                new ChunkPos(pos),
                0,
                serverWorld,
                biome -> true
        );
        if(!structureStart.hasChildren()){
            System.out.println("false1");
            return false;
        }else {
            BlockBox blockBox = structureStart.getBoundingBox();
            ChunkPos chunkPos = new ChunkPos(ChunkSectionPos.getSectionCoord(blockBox.getMinX()), ChunkSectionPos.getSectionCoord(blockBox.getMinZ()));
            ChunkPos chunkPos2 = new ChunkPos(ChunkSectionPos.getSectionCoord(blockBox.getMaxX()), ChunkSectionPos.getSectionCoord(blockBox.getMaxZ()));
            forceLoadNearbyChunks(blockBox, serverWorld);//probably not needed
/*            if(FalseOnUnloadedPos(serverWorld, chunkPos, chunkPos2)){
                System.out.println("unloaded pos");
                return false;
            }*/
            ChunkPos.stream(chunkPos, chunkPos2)
                    .forEach(
                            chunkPosx -> structureStart.place(
                                    serverWorld,
                                    serverWorld.getStructureAccessor(),
                                    chunkGenerator,
                                    serverWorld.getRandom(),
                                    new BlockBox(chunkPosx.getStartX(), serverWorld.getBottomY(), chunkPosx.getStartZ(), chunkPosx.getEndX(), serverWorld.getTopY(), chunkPosx.getEndZ()),
                                    chunkPosx
                            )
                    );
            String string = structure.registryKey().getValue().toString();
            System.out.println(string);
            unloadNearbyChunks(blockBox, serverWorld);
            System.out.println("2");
            return true;
        }
    }

    private static boolean FalseOnUnloadedPos(ServerWorld world, ChunkPos pos1, ChunkPos pos2)  {
        if (ChunkPos.stream(pos1, pos2).anyMatch(pos -> !world.canSetBlock(pos.getStartPos()))) {
            return false;
        }
        return true;
    }

    private static void forceLoadNearbyChunks(BlockBox box, ServerWorld world) {
        System.out.println("loading chunks");
        box.streamChunkPos().forEach(chunkPos -> world.setChunkForced(chunkPos.x, chunkPos.z, true));
    }
    private static void unloadNearbyChunks(BlockBox box, ServerWorld world) {
        System.out.println("unloading chunks");
        box.streamChunkPos().forEach(chunkPos -> world.setChunkForced(chunkPos.x, chunkPos.z, false));
    }
}
