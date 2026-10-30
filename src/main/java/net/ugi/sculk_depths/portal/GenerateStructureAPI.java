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
import net.minecraft.world.level.chunk.ChunkGenerator;
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
     * Register a StructureStart stub into the world's StructureAccessor without forcing full chunk population.
     * If the target chunk is already loaded, the method will attempt to place the center piece only.
     * Returns true on success.
     */
    public static boolean registerStructureStartStub(Level originalWorld, ResourceKey<Level> targetWorldKey, ResourceLocation structureKey, BlockPos anchor){
        if (originalWorld.isClientSide) return false;
        try{
            Optional<Holder.Reference<Structure>> opt = originalWorld.registryAccess().registry(Registries.STRUCTURE).flatMap(r -> r.getHolder(structureKey));
            if (opt.isEmpty()) return false;
            Holder.Reference<Structure> structureRef = opt.get();
            Structure setStructures = structureRef.value();

            ServerLevel serverWorld = originalWorld.getServer().getLevel(targetWorldKey);
            if (serverWorld == null) return false;

            ChunkGenerator generator = serverWorld.getChunkSource().getGenerator();

            StructureStart start = setStructures.generate(
                    originalWorld.registryAccess(),
                    generator,
                    generator.getBiomeSource(),
                    serverWorld.getChunkSource().randomState(),
                    serverWorld.getStructureManager(),
                    serverWorld.getSeed(),
                    new ChunkPos(anchor),
                    0,
                    serverWorld,
                    biomes -> true
            );

            ChunkPos startChunk = new ChunkPos(anchor);

            // Try to register via StructureManager.setStartForStructure
            Object getMutable = serverWorld.structureManager();
            boolean registered = false;
            try{
                java.lang.reflect.Method height = getMutable.getClass().getMethod("setStartForStructure", SectionPos.class, Structure.class, StructureStart.class, net.minecraft.world.level.chunk.StructureAccess.class);
                SectionPos sectionPos = SectionPos.of(anchor);
                height.invoke(getMutable, sectionPos, setStructures, start, serverWorld.getChunk(startChunk.x, startChunk.z));
                registered = true;
            }catch(Exception e){
                // attempt alternative reflection fallback from upstream
                for (java.lang.reflect.Method m2 : getMutable.getClass().getMethods()){
                    Class<?>[] pts = m2.getParameterTypes();
                    if (pts.length == 4 && pts[0] == SectionPos.class && pts[1] == Structure.class && pts[2] == StructureStart.class){
                        SectionPos sectionPos = SectionPos.of(anchor);
                        m2.invoke(getMutable, sectionPos, setStructures, start, serverWorld.getChunk(startChunk.x, startChunk.z));
                        registered = true;
                        break;
                    }
                    if (pts.length == 2 && pts[0] == ChunkPos.class && pts[1] == StructureStart.class){
                        m2.invoke(getMutable, startChunk, start);
                        registered = true;
                        break;
                    }
                }
            }

            // If registered, try to persist/mark dirty only if the chunk is loaded already.
            if (registered){
                boolean chunkLoaded = serverWorld.getChunkSource().hasChunk(startChunk.x, startChunk.z);

                if (chunkLoaded){
                    // chunk is present: place center piece only by generating for the single chunk
                    generateStructurePartial(originalWorld, targetWorldKey, structureKey, start, new ChunkPos[]{startChunk});

                    // mark the chunk dirty so it will be saved
                    try{
                        var chunk = serverWorld.getChunk(startChunk.x, startChunk.z);
                        chunk.setUnsaved(true);
                    }catch(Exception ignored){}
                }

                return true;
            }

            // fallback: if unable to register via accessor, load the chunk and set on chunk instance
            try{
                var chunk = serverWorld.getChunk(startChunk.x, startChunk.z); // this may load/generate - fallback
                boolean setOnChunk = false;
                for (java.lang.reflect.Method m3 : chunk.getClass().getMethods()){
                    Class<?>[] pts = m3.getParameterTypes();
                    if (pts.length == 2 && Structure.class.isAssignableFrom(pts[0]) && StructureStart.class.isAssignableFrom(pts[1])){
                        m3.invoke(chunk, setStructures, start);
                        setOnChunk = true;
                        break;
                    }
                    if (pts.length == 2 && ResourceKey.class.isAssignableFrom(pts[0]) && StructureStart.class.isAssignableFrom(pts[1])){
                        ResourceKey<Structure> structKey = ResourceKey.create(Registries.STRUCTURE, structureKey);
                        m3.invoke(chunk, structKey, start);
                        setOnChunk = true;
                        break;
                    }
                }
                // mark chunk dirty
                try{
                    chunk.setUnsaved(true);
                }catch(Exception ignored){}

                if (setOnChunk){
                    // if chunk was present we also place the center
                    generateStructurePartial(originalWorld, targetWorldKey, structureKey, start, new ChunkPos[]{startChunk});
                    return true;
                }
            }catch(Exception ignored){/* final fallback failed */}

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return false;
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
