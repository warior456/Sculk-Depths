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
     * Register a StructureStart stub into the world's StructureAccessor without forcing full chunk population.
     * If the target chunk is already loaded, the method will attempt to place the center piece only.
     * Returns true on success.
     */
    public static boolean registerStructureStartStub(World originalWorld, RegistryKey<World> targetWorldKey, Identifier structureKey, BlockPos anchor){
        if (originalWorld.isClient) return false;
        try{
            java.util.Optional<RegistryEntry.Reference<Structure>> opt = originalWorld.getRegistryManager().get(RegistryKeys.STRUCTURE).getEntry(structureKey);
            if (opt.isEmpty()) return false;
            RegistryEntry.Reference<Structure> structureRef = opt.get();
            Structure structure = structureRef.value();

            ServerWorld serverWorld = originalWorld.getServer().getWorld(targetWorldKey);
            if (serverWorld == null) return false;

            ChunkGenerator chunkGenerator = serverWorld.getChunkManager().getChunkGenerator();

            StructureStart start = structure.createStructureStart(
                    originalWorld.getRegistryManager(),
                    chunkGenerator,
                    chunkGenerator.getBiomeSource(),
                    serverWorld.getChunkManager().getNoiseConfig(),
                    serverWorld.getStructureTemplateManager(),
                    serverWorld.getSeed(),
                    new ChunkPos(anchor),
                    0,
                    serverWorld,
                    biome -> true
            );

            ChunkPos startChunk = new ChunkPos(anchor);

            // Try to register via StructureAccessor.setStartForStructure(ChunkPos, RegistryKey<Structure>, StructureStart)
            Object accessor = serverWorld.getStructureAccessor();
            boolean registered = false;
            try{
                java.lang.reflect.Method m = accessor.getClass().getMethod("setStartForStructure", ChunkPos.class, RegistryKey.class, StructureStart.class);
                // build RegistryKey<Structure>
                RegistryKey<Structure> structKey = RegistryKey.of(RegistryKeys.STRUCTURE, structureKey);
                m.invoke(accessor, startChunk, structKey, start);
                registered = true;
            }catch(NoSuchMethodException e){
                // attempt alternative method name: setStart
                for (java.lang.reflect.Method m2 : accessor.getClass().getMethods()){
                    Class<?>[] pts = m2.getParameterTypes();
                    if (pts.length == 2 && pts[0] == ChunkPos.class && pts[1] == StructureStart.class){
                        m2.invoke(accessor, startChunk, start);
                        registered = true;
                        break;
                    }
                }
            }

            // If registered, try to persist/mark dirty only if the chunk is loaded already.
            if (registered){
                Object chunkManager = serverWorld.getChunkManager();
                boolean chunkLoaded = false;
                // try common method signatures to check whether chunk is loaded without forcing generation
                try{
                    java.lang.reflect.Method isLoadedMethod = chunkManager.getClass().getMethod("isChunkLoaded", int.class, int.class);
                    Object res = isLoadedMethod.invoke(chunkManager, startChunk.x, startChunk.z);
                    if (res instanceof Boolean) chunkLoaded = (Boolean) res;
                }catch(Exception ignored){
                    try{
                        java.lang.reflect.Method isLoadedMethod2 = chunkManager.getClass().getMethod("isLoaded", ChunkPos.class);
                        Object res = isLoadedMethod2.invoke(chunkManager, startChunk);
                        if (res instanceof Boolean) chunkLoaded = (Boolean) res;
                    }catch(Exception ignored2){
                        // could not determine; assume not loaded to avoid forcing chunk generation
                        chunkLoaded = false;
                    }
                }

                if (chunkLoaded){
                    // chunk is present: place center piece only by generating for the single chunk
                    generateStructurePartial(originalWorld, targetWorldKey, structureKey, start, new ChunkPos[]{startChunk});

                    // mark the chunk dirty so it will be saved
                    try{
                        var chunk = serverWorld.getChunk(startChunk.x, startChunk.z);
                        // prefer markDirty if available
                        try{
                            java.lang.reflect.Method md = chunk.getClass().getMethod("markDirty");
                            md.invoke(chunk);
                        }catch(NoSuchMethodException ex){
                            try{
                                java.lang.reflect.Method md2 = chunk.getClass().getMethod("setNeedsSaving", boolean.class);
                                md2.invoke(chunk, true);
                            }catch(Exception ignored3){}
                        }
                    }catch(Exception ignored){}
                }

                return true;
            }

            // fallback: if unable to register via accessor, load the chunk and set on chunk instance
            try{
                var chunk = serverWorld.getChunk(startChunk.x, startChunk.z); // this may load/generate - fallback
                // try to find chunk.setStructureStart(RegistryKey, StructureStart) or similar
                boolean setOnChunk = false;
                for (java.lang.reflect.Method m3 : chunk.getClass().getMethods()){
                    Class<?>[] pts = m3.getParameterTypes();
                    if (pts.length == 2 && RegistryKey.class.isAssignableFrom(pts[0]) && StructureStart.class.isAssignableFrom(pts[1])){
                        RegistryKey<Structure> structKey = RegistryKey.of(RegistryKeys.STRUCTURE, structureKey);
                        m3.invoke(chunk, structKey, start);
                        setOnChunk = true;
                        break;
                    }
                }
                // mark chunk dirty
                try{
                    java.lang.reflect.Method md = chunk.getClass().getMethod("markDirty");
                    md.invoke(chunk);
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
