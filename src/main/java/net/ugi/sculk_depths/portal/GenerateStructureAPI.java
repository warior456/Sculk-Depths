package net.ugi.sculk_depths.portal;

import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Optional;

public class GenerateStructureAPI {

    public static boolean generateStructure(Level  originalWorld, ResourceKey<Level> targetWorldKey, ResourceLocation setStructures, BlockPos pos){
        if(originalWorld.isClientSide)return false;
        Optional<Holder.Reference<Structure>> structureReference = originalWorld.registryAccess().registry(Registries.STRUCTURE).flatMap(r -> r.getHolder(setStructures));
        if(structureReference.isEmpty()) return false;
        ServerLevel serverWorld = originalWorld.getServer().getLevel(targetWorldKey);
        if (serverWorld == null) return false;
        StructureStart structureStart = structureStart(originalWorld,  targetWorldKey, setStructures, pos);
        if(structureStart == null)return false;
        BoundingBox blockBox = structureStart.getBoundingBox();
        ChunkPos chunkPos = new ChunkPos(SectionPos.blockToSectionCoord(blockBox.minX()), SectionPos.blockToSectionCoord(blockBox.minZ()));
        ChunkPos chunkPos2 = new ChunkPos(SectionPos.blockToSectionCoord(blockBox.maxX()), SectionPos.blockToSectionCoord(blockBox.maxZ()));
        ChunkPos[] chunkPosArray =  ChunkPos.rangeClosed(chunkPos, chunkPos2).toArray(ChunkPos[]::new);
        return  generate(serverWorld, structureStart, chunkPosArray);
    }
    public static boolean generateStructure(Level  originalWorld, ResourceKey<Level> targetWorldKey, ResourceKey<Structure> setStructures, BlockPos pos){
        return generateStructure(originalWorld, targetWorldKey, setStructures.location(), pos);
    }


    public static boolean generateStructure(Level  originalWorld, ResourceKey<Level> targetWorldKey, ResourceLocation setStructures, StructureStart structureStart){
        if(originalWorld.isClientSide)return false;
        if(structureStart == null)return false;
        Optional<Holder.Reference<Structure>> structureReference = originalWorld.registryAccess().registry(Registries.STRUCTURE).flatMap(r -> r.getHolder(setStructures));
        if(structureReference.isEmpty()) return false;
        ServerLevel serverWorld = originalWorld.getServer().getLevel(targetWorldKey);
        if (serverWorld == null) return false;
        BoundingBox blockBox = structureStart.getBoundingBox();
        ChunkPos chunkPos = new ChunkPos(SectionPos.blockToSectionCoord(blockBox.minX()), SectionPos.blockToSectionCoord(blockBox.minZ()));
        ChunkPos chunkPos2 = new ChunkPos(SectionPos.blockToSectionCoord(blockBox.maxX()), SectionPos.blockToSectionCoord(blockBox.maxZ()));
        ChunkPos[] chunkPosArray =  ChunkPos.rangeClosed(chunkPos, chunkPos2).toArray(ChunkPos[]::new);
        return  generate(serverWorld, structureStart, chunkPosArray);
    }
    public static boolean generateStructure(Level  originalWorld, ResourceKey<Level> targetWorldKey, ResourceKey<Structure> setStructures, StructureStart structureStart){
        return generateStructure(originalWorld, targetWorldKey, setStructures.location(), structureStart);
    }



    public static boolean generateStructurePartial(Level  originalWorld, ResourceKey<Level> targetWorldKey, ResourceLocation setStructures, StructureStart structureStart, ChunkPos[] chunkPosArray){

        if(originalWorld.isClientSide)return false;
        if(structureStart == null)return false;
        Optional<Holder.Reference<Structure>> structureReference = originalWorld.registryAccess().registry(Registries.STRUCTURE).flatMap(r -> r.getHolder(setStructures));
        if(structureReference.isEmpty()) return false;
        ServerLevel serverWorld = originalWorld.getServer().getLevel(targetWorldKey);
        if (serverWorld == null) return false;
        return  generate( serverWorld, structureStart, chunkPosArray);
    }

    public static boolean generateStructurePartial(Level  originalWorld, ResourceKey<Level> targetWorldKey, ResourceKey<Structure> setStructures, StructureStart structureStart, ChunkPos[] chunkPosArray){
        return generateStructurePartial(originalWorld, targetWorldKey, setStructures.location(), structureStart, chunkPosArray);
    }

    public static StructureStart structureStart (Level  originalWorld, ResourceKey<Level> targetWorldKey, ResourceLocation structureKey, BlockPos pos){
        if(originalWorld.isClientSide)return null;
        Optional<Holder.Reference<Structure>> structureReference = originalWorld.registryAccess().registry(Registries.STRUCTURE).flatMap(r -> r.getHolder(structureKey));
        if(structureReference.isEmpty())return null;
        Holder.Reference<Structure> setStructures = structureReference.get();
        ServerLevel serverWorld = originalWorld.getServer().getLevel(targetWorldKey);
        if (serverWorld == null) return null;
        ChunkGenerator generator = serverWorld.getChunkSource().getGenerator();
        Structure structure2 = setStructures.value();
        ServerLevel overworld = originalWorld.getServer().getLevel(Level.OVERWORLD);
        long seed = overworld != null ? overworld.getSeed() : serverWorld.getSeed();
        StructureStart structureStart = structure2.generate(
                originalWorld.registryAccess(),
                generator,
                generator.getBiomeSource(),
                serverWorld.getChunkSource().randomState(),
                serverWorld.getStructureManager(),
                seed,
                new ChunkPos(pos),
                0,
                serverWorld,
                biomes -> true
        );
        return structureStart;
    }
    public static StructureStart structureStart (Level  originalWorld, ResourceKey<Level> targetWorldKey, ResourceKey<Structure> structureKey, BlockPos pos){
        return structureStart (originalWorld,targetWorldKey,structureKey.location(),pos);
    }

    /**
     * Attaches a structure start to the target level the same way worldgen does: the start is stored
     * on the anchor chunk while that chunk is still at {@code STRUCTURE_STARTS}, and vanilla then
     * creates the chunk references and runs {@code placeInChunk} while the surrounding chunks are
     * generated normally.
     * <p>
     * Nothing is scheduled from an async thread. If the anchor chunk was already generated before
     * the start existed, worldgen never runs {@code FEATURES} for it again, so its center part is
     * written immediately as a backup.
     *
     * @return true when the start was registered
     */
    public static boolean placeStructureStart(Level originalWorld, ResourceKey<Level> targetWorldKey, ResourceLocation structureKey, BlockPos anchor){
        if (originalWorld.isClientSide) return false;
        Optional<Holder.Reference<Structure>> opt = originalWorld.registryAccess().registry(Registries.STRUCTURE).flatMap(r -> r.getHolder(structureKey));
        if (opt.isEmpty()) return false;
        Structure structure = opt.get().value();

        ServerLevel serverWorld = originalWorld.getServer().getLevel(targetWorldKey);
        if (serverWorld == null) return false;

        ChunkGenerator generator = serverWorld.getChunkSource().getGenerator();
        ChunkPos startChunk = new ChunkPos(anchor);

        StructureStart start = structure.generate(
                serverWorld.registryAccess(),
                generator,
                generator.getBiomeSource(),
                serverWorld.getChunkSource().randomState(),
                serverWorld.getStructureManager(),
                serverWorld.getSeed(),
                startChunk,
                0,
                serverWorld,
                biomes -> true
        );
        if (!start.isValid()) return false;

        // Create the anchor chunk only up to STRUCTURE_STARTS: no noise, surface or features are generated here.
        ChunkAccess chunk = serverWorld.getChunkSource().getChunk(startChunk.x, startChunk.z, ChunkStatus.STRUCTURE_STARTS, true);
        if (chunk == null) return false;

        serverWorld.structureManager().setStartForStructure(SectionPos.bottomOf(chunk), structure, start, chunk);
        chunk.setUnsaved(true);

        placeCenterIfGenerated(serverWorld, startChunk, start);

        // The start currently lives on a proto chunk. Proto chunks below LEVELCHUNK are discarded
        // instead of saved when they unload, so finish the anchor chunk: this persists the start and
        // lets its own pieces go through the normal FEATURES step. Only this one chunk is forced.
        serverWorld.getChunkSource().getChunk(startChunk.x, startChunk.z, ChunkStatus.FULL, true);
        return true;
    }

    /**
     * Backup for destinations whose anchor chunk already existed before the start was attached:
     * worldgen will never run {@code FEATURES} for that chunk again, so the pieces intersecting it
     * (the center part of the structure) are written now. Only the anchor chunk is touched; the
     * rest of the structure stays worldgen's job, and the center part may later grow into several
     * pieces.
     */
    private static void placeCenterIfGenerated(ServerLevel level, ChunkPos startChunk, StructureStart start){
        LevelChunk chunk = level.getChunkSource().getChunkNow(startChunk.x, startChunk.z);
        if (chunk == null || !chunk.getPersistedStatus().isOrAfter(ChunkStatus.FEATURES)) {
            // Not loaded yet or still below FEATURES: the normal worldgen pass will place the pieces.
            return;
        }
        ChunkGenerator generator = level.getChunkSource().getGenerator();
        start.placeInChunk(
                level,
                level.structureManager(),
                generator,
                level.getRandom(),
                new BoundingBox(startChunk.getMinBlockX(), level.getMinBuildHeight(), startChunk.getMinBlockZ(),
                        startChunk.getMaxBlockX(), level.getMaxBuildHeight(), startChunk.getMaxBlockZ()),
                startChunk
        );
        chunk.setUnsaved(true);
    }

    private static boolean generate(ServerLevel serverWorld, StructureStart structureStart, ChunkPos[] chunkPosArray){
        ChunkGenerator generator = serverWorld.getChunkSource().getGenerator();
        if(!structureStart.isValid()){
            return false;
        }else {
            for (ChunkPos chunkPosx : chunkPosArray) {
                structureStart.placeInChunk(
                        serverWorld,
                        serverWorld.structureManager(),
                        generator,
                        serverWorld.getRandom(),
                        new BoundingBox(chunkPosx.getMinBlockX(), serverWorld.getMinBuildHeight(), chunkPosx.getMinBlockZ(), chunkPosx.getMaxBlockX(), serverWorld.getMaxBuildHeight(), chunkPosx.getMaxBlockZ()),
                        chunkPosx
                );
            }
            unloadNearbyChunks(chunkPosArray, serverWorld);
            return true;
        }
    }

    public static void forceLoadChunks(ChunkPos[] chunkPosArray, ServerLevel level) {
        for (ChunkPos chunkPos : chunkPosArray) {
            level.setChunkForced(chunkPos.x, chunkPos.z, true);
        }
    }
    public static void unloadNearbyChunks(ChunkPos[] chunkPosArray, ServerLevel level) {
        for (ChunkPos chunkPos : chunkPosArray) {
            level.setChunkForced(chunkPos.x, chunkPos.z, false);
        }
    }

    public static ChunkPos[] generateChunkArray(ChunkPos minValue, ChunkPos max, int offset, int fadeDirection){
        int arrLengthX = (max.x-minValue.x+fadeDirection-1-offset*2)/fadeDirection +1;
        int arrLengthZ = (max.z-minValue.z+fadeDirection-1-offset*2)/fadeDirection +1;
        int arrLength = arrLengthX * arrLengthZ;
        ChunkPos[] arr = new ChunkPos[arrLength];
        int x = minValue.x + offset;
        int z = minValue.z + offset;
        int i = 0;
        while(x <= max.x - offset){
            while(z <= max.z - offset){
                arr[i] = new ChunkPos(x,z);
                if (max.z - offset == z){
                    z++;
                }
                else if (max.z - offset - z < fadeDirection){
                    z = max.z - offset;
                }
                else{
                    z += fadeDirection;
                }
                i++;

            }
            if (max.x - offset == x){
                break;
            }
            else if (max.x - offset - x < fadeDirection){
                z = minValue.z + offset;
                x = max.x - offset;
            }
            else{
                z = minValue.z + offset;
                x += fadeDirection;
            }
        }
        return arr;
    }
}
