package net.ugi.sculk_depths.portal;

import com.sun.source.tree.WhileLoopTree;
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

import java.util.Date;
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
        RegistryEntry.Reference<Structure> structure = originalWorld.getRegistryManager().get(RegistryKeys.STRUCTURE).getEntry(structureKey).get();
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