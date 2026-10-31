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
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.Structure;

import java.util.Optional;

public class GenerateStructureAPI {

    public static boolean generateStructure(World  originalWorld, RegistryKey<World> targetWorldKey, Identifier structure, BlockPos pos){
        if(originalWorld.isClient)return false;
        Optional<RegistryEntry.Reference<Structure>> structureReference = originalWorld.getRegistryManager().get(RegistryKeys.STRUCTURE).getEntry(structure);
        if(structureReference.isEmpty()) return false;
        ServerWorld serverWorld = originalWorld.getServer().getWorld(targetWorldKey);
        StructureStart structureStart = structureStart(originalWorld,  targetWorldKey, structure, pos);
        if(structureStart == null)return false;
        BlockBox blockBox = structureStart.getBoundingBox();
        ChunkPos chunkPos = new ChunkPos(ChunkSectionPos.getSectionCoord(blockBox.getMinX()), ChunkSectionPos.getSectionCoord(blockBox.getMinZ()));
        ChunkPos chunkPos2 = new ChunkPos(ChunkSectionPos.getSectionCoord(blockBox.getMaxX()), ChunkSectionPos.getSectionCoord(blockBox.getMaxZ()));
        ChunkPos[] chunkPosArray =  ChunkPos.stream(chunkPos, chunkPos2).toArray(ChunkPos[]::new);
        return  generate(serverWorld, structureStart, chunkPosArray);
    }
    public static boolean generateStructure(World  originalWorld, RegistryKey<World> targetWorldKey, RegistryKey<Structure> structure, BlockPos pos){
        return generateStructure(originalWorld, targetWorldKey, structure.getValue(), pos);
    }


    public static boolean generateStructure(World  originalWorld, RegistryKey<World> targetWorldKey, Identifier structure, StructureStart structureStart){
        if(originalWorld.isClient)return false;
        if(structureStart == null)return false;
        Optional<RegistryEntry.Reference<Structure>> structureReference = originalWorld.getRegistryManager().get(RegistryKeys.STRUCTURE).getEntry(structure);
        if(structureReference.isEmpty()) return false;
        ServerWorld serverWorld = originalWorld.getServer().getWorld(targetWorldKey);
        BlockBox blockBox = structureStart.getBoundingBox();
        ChunkPos chunkPos = new ChunkPos(ChunkSectionPos.getSectionCoord(blockBox.getMinX()), ChunkSectionPos.getSectionCoord(blockBox.getMinZ()));
        ChunkPos chunkPos2 = new ChunkPos(ChunkSectionPos.getSectionCoord(blockBox.getMaxX()), ChunkSectionPos.getSectionCoord(blockBox.getMaxZ()));
        ChunkPos[] chunkPosArray =  ChunkPos.stream(chunkPos, chunkPos2).toArray(ChunkPos[]::new);
        return  generate(serverWorld, structureStart, chunkPosArray);
    }
    public static boolean generateStructure(World  originalWorld, RegistryKey<World> targetWorldKey, RegistryKey<Structure> structure, StructureStart structureStart){
        return generateStructure(originalWorld, targetWorldKey, structure.getValue(), structureStart);
    }



    public static boolean generateStructurePartial(World  originalWorld, RegistryKey<World> targetWorldKey, Identifier structure, StructureStart structureStart, ChunkPos[] chunkPosArray){

        if(originalWorld.isClient)return false;
        if(structureStart == null)return false;
        Optional<RegistryEntry.Reference<Structure>> structureReference = originalWorld.getRegistryManager().get(RegistryKeys.STRUCTURE).getEntry(structure);
        if(structureReference.isEmpty()) return false;
        ServerWorld serverWorld = originalWorld.getServer().getWorld(targetWorldKey);
        return  generate( serverWorld, structureStart, chunkPosArray);
    }

    public static boolean generateStructurePartial(World  originalWorld, RegistryKey<World> targetWorldKey, RegistryKey<Structure> structure, StructureStart structureStart, ChunkPos[] chunkPosArray){
        return generateStructurePartial(originalWorld, targetWorldKey, structure.getValue(), structureStart, chunkPosArray);
    }

    public static StructureStart structureStart (World  originalWorld, RegistryKey<World> targetWorldKey, Identifier structureKey, BlockPos pos){
        if(originalWorld.isClient)return null;
        Optional<RegistryEntry.Reference<Structure>> structureReference = originalWorld.getRegistryManager().get(RegistryKeys.STRUCTURE).getEntry(structureKey);
        if(structureReference.isEmpty())return null;
        RegistryEntry.Reference<Structure> structure = structureReference.get();
        ServerWorld serverWorld = originalWorld.getServer().getWorld(targetWorldKey);
        ChunkGenerator chunkGenerator = serverWorld.getChunkManager().getChunkGenerator();
        Structure structure2 = structure.value();
        StructureStart structureStart = structure2.createStructureStart(
                originalWorld.getRegistryManager(),
                chunkGenerator,
                chunkGenerator.getBiomeSource(),
                serverWorld.getChunkManager().getNoiseConfig(),
                serverWorld.getStructureTemplateManager(),
                originalWorld.getServer().getWorld(World.OVERWORLD).getSeed(),
                new ChunkPos(pos),
                0,
                serverWorld,
                biome -> true
        );
        return structureStart;
    }
    public static StructureStart structureStart (World  originalWorld, RegistryKey<World> targetWorldKey, RegistryKey<Structure> structureKey, BlockPos pos){
        return structureStart (originalWorld,targetWorldKey,structureKey.getValue(),pos);
    }

    /**
     * Attaches a structure start to the target level the same way worldgen does: the start is stored
     * on the anchor chunk while that chunk is still at {@code STRUCTURE_STARTS}, and vanilla then
     * creates the chunk references and runs {@code StructureStart#place} while the surrounding chunks
     * are generated normally.
     * <p>
     * Nothing is scheduled from an async thread. If the anchor chunk was already generated before
     * the start existed, worldgen never runs {@code FEATURES} for it again, so its center part is
     * written immediately as a backup.
     *
     * @return true when the start was registered
     */
    public static boolean placeStructureStart(World originalWorld, RegistryKey<World> targetWorldKey, Identifier structureKey, BlockPos anchor){
        if (originalWorld.isClient) return false;
        Optional<RegistryEntry.Reference<Structure>> opt = originalWorld.getRegistryManager().get(RegistryKeys.STRUCTURE).getEntry(structureKey);
        if (opt.isEmpty()) return false;
        Structure structure = opt.get().value();

        ServerWorld serverWorld = originalWorld.getServer().getWorld(targetWorldKey);
        if (serverWorld == null) return false;

        ChunkGenerator generator = serverWorld.getChunkManager().getChunkGenerator();
        ChunkPos startChunk = new ChunkPos(anchor);

        StructureStart start = structure.createStructureStart(
                serverWorld.getRegistryManager(),
                generator,
                generator.getBiomeSource(),
                serverWorld.getChunkManager().getNoiseConfig(),
                serverWorld.getStructureTemplateManager(),
                serverWorld.getSeed(),
                startChunk,
                0,
                serverWorld,
                biome -> true
        );
        if (start == null || !start.hasChildren()) return false;

        // Create the anchor chunk only up to STRUCTURE_STARTS: no noise, surface or features are generated here.
        Chunk chunk = serverWorld.getChunkManager().getChunk(startChunk.x, startChunk.z, ChunkStatus.STRUCTURE_STARTS, true);
        if (chunk == null) return false;

        serverWorld.getStructureAccessor().setStructureStart(ChunkSectionPos.from(chunk), structure, start, chunk);
        chunk.setNeedsSaving(true);

        placeCenterIfGenerated(serverWorld, startChunk, start);

        // The start currently lives on a proto chunk. Proto chunks below FULL are discarded
        // instead of saved when they unload, so finish the anchor chunk: this persists the start and
        // lets its own pieces go through the normal FEATURES step. Only this one chunk is forced.
        serverWorld.getChunkManager().getChunk(startChunk.x, startChunk.z, ChunkStatus.FULL, true);
        return true;
    }

    /**
     * Backup for destinations whose anchor chunk already existed before the start was attached:
     * worldgen will never run {@code FEATURES} for that chunk again, so the pieces intersecting it
     * (the center part of the structure) are written now. Only the anchor chunk is touched; the
     * rest of the structure stays worldgen's job, and the center part may later grow into several
     * pieces.
     */
    private static void placeCenterIfGenerated(ServerWorld level, ChunkPos startChunk, StructureStart start){
        WorldChunk chunk = level.getChunkManager().getWorldChunk(startChunk.x, startChunk.z);
        if (chunk == null || !chunk.getStatus().isAtLeast(ChunkStatus.FEATURES)) {
            // Not loaded yet or still below FEATURES: the normal worldgen pass will place the pieces.
            return;
        }
        ChunkGenerator generator = level.getChunkManager().getChunkGenerator();
        start.place(
                level,
                level.getStructureAccessor(),
                generator,
                level.getRandom(),
                new BlockBox(startChunk.getStartX(), level.getBottomY(), startChunk.getStartZ(),
                        startChunk.getEndX(), level.getTopY(), startChunk.getEndZ()),
                startChunk
        );
        chunk.setNeedsSaving(true);
    }

    private static boolean generate(ServerWorld serverWorld, StructureStart structureStart, ChunkPos[] chunkPosArray){
        ChunkGenerator chunkGenerator = serverWorld.getChunkManager().getChunkGenerator();
        if(!structureStart.hasChildren()){
            return false;
        }else {
            for (ChunkPos chunkPosx : chunkPosArray) {
                structureStart.place(
                        serverWorld,
                        serverWorld.getStructureAccessor(),
                        chunkGenerator,
                        serverWorld.getRandom(),
                        new BlockBox(chunkPosx.getStartX(), serverWorld.getBottomY(), chunkPosx.getStartZ(), chunkPosx.getEndX(), serverWorld.getTopY(), chunkPosx.getEndZ()),
                        chunkPosx
                );
            }
            unloadNearbyChunks(chunkPosArray, serverWorld);
            return true;
        }
    }

    public static void forceLoadNearbyChunks(ChunkPos[] chunkPosArray, ServerWorld world) {
        for (ChunkPos chunkPos : chunkPosArray) {
            world.setChunkForced(chunkPos.x, chunkPos.z, true);
        }
    }
    public static void unloadNearbyChunks(ChunkPos[] chunkPosArray, ServerWorld world) {
        for (ChunkPos chunkPos : chunkPosArray) {
            world.setChunkForced(chunkPos.x, chunkPos.z, false);
        }
    }

    public static ChunkPos[] generateChunkArray(ChunkPos min, ChunkPos max, int offset, int delta){
        int arrLengthX = (max.x-min.x+delta-1-offset*2)/delta +1;
        int arrLengthZ = (max.z-min.z+delta-1-offset*2)/delta +1;
        int arrLength = arrLengthX * arrLengthZ;
        ChunkPos[] arr = new ChunkPos[arrLength];
        int x = min.x + offset;
        int z = min.z + offset;
        int i = 0;
        while(x <= max.x - offset){
            while(z <= max.z - offset){
                arr[i] = new ChunkPos(x,z);
                if (max.z - offset == z){
                    z++;
                }
                else if (max.z - offset - z < delta){
                    z = max.z - offset;
                }
                else{
                    z += delta;
                }
                i++;

            }
            if (max.x - offset == x){
                break;
            }
            else if (max.x - offset - x < delta){
                z = min.z + offset;
                x = max.x - offset;
            }
            else{
                z = min.z + offset;
                x += delta;
            }
        }
        return arr;
    }
}
