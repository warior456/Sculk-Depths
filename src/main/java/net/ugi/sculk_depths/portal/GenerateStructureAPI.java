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
        System.out.println("1");
        ServerWorld serverWorld = originalWorld.getServer().getWorld(targetWorldKey);
        ChunkGenerator chunkGenerator = serverWorld.getChunkManager().getChunkGenerator();
        Structure structure2 = structure.value();
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
        return structureStart;
    }
    public static StructureStart structureStart (World  originalWorld, RegistryKey<World> targetWorldKey, RegistryKey<Structure> structureKey, BlockPos pos){
        return structureStart (originalWorld,targetWorldKey,structureKey.getValue(),pos);
    }

    private static boolean generate(ServerWorld serverWorld, StructureStart structureStart, ChunkPos[] chunkPosArray){
        ChunkGenerator chunkGenerator = serverWorld.getChunkManager().getChunkGenerator();
        if(!structureStart.hasChildren()){
            System.out.println("false1");
            return false;
        }else {
            forceLoadNearbyChunks(chunkPosArray, serverWorld);
            /*            if(FalseOnUnloadedPos(serverWorld, chunkPos, chunkPos2)){
                System.out.println("unloaded pos");
                return false;
            }*/
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

    private static boolean FalseOnUnloadedPos(ServerWorld world, ChunkPos pos1, ChunkPos pos2)  {
        if (ChunkPos.stream(pos1, pos2).anyMatch(pos -> !world.canSetBlock(pos.getStartPos()))) {
            return false;
        }
        return true;
    }

    private static void forceLoadNearbyChunks(ChunkPos[] chunkPosArray, ServerWorld world) {
        for (ChunkPos chunkPos : chunkPosArray) {
            world.setChunkForced(chunkPos.x, chunkPos.z, true);
        }
    }
    private static void unloadNearbyChunks(ChunkPos[] chunkPosArray, ServerWorld world) {
        for (ChunkPos chunkPos : chunkPosArray) {
            world.setChunkForced(chunkPos.x, chunkPos.z, true);
        }
    }
}