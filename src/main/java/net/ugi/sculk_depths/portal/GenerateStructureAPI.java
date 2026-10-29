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
        Optional<Holder.Reference<Structure>> structureReference = originalWorld.getRegistryManager().get(Registries.STRUCTURE).getEntry(setStructures);
        if(structureReference.isEmpty()) return false;
        ServerLevel serverWorld = originalWorld.getServer().getWorld(targetWorldKey);
        StructureStart structureStart = structureStart(originalWorld,  targetWorldKey, setStructures, pos);
        if(structureStart == null)return false;
        BoundingBox blockBox = structureStart.getBoundingBox();
        ChunkPos chunkPos = new ChunkPos(SectionPos.getSectionCoord(blockBox.getMinX()), SectionPos.getSectionCoord(blockBox.getMinZ()));
        ChunkPos chunkPos2 = new ChunkPos(SectionPos.getSectionCoord(blockBox.getMaxX()), SectionPos.getSectionCoord(blockBox.getMaxZ()));
        ChunkPos[] chunkPosArray =  ChunkPos.stream(chunkPos, chunkPos2).adjustArgs(ChunkPos[]::new);
        return  generate(serverWorld, structureStart, chunkPosArray);
    }
    public static boolean generateStructure(Level  originalWorld, ResourceKey<Level> targetWorldKey, ResourceKey<Structure> setStructures, BlockPos pos){
        return generateStructure(originalWorld, targetWorldKey, setStructures.getValue(), pos);
    }


    public static boolean generateStructure(Level  originalWorld, ResourceKey<Level> targetWorldKey, ResourceLocation setStructures, StructureStart structureStart){
        if(originalWorld.isClientSide)return false;
        if(structureStart == null)return false;
        Optional<Holder.Reference<Structure>> structureReference = originalWorld.getRegistryManager().get(Registries.STRUCTURE).getEntry(setStructures);
        if(structureReference.isEmpty()) return false;
        ServerLevel serverWorld = originalWorld.getServer().getWorld(targetWorldKey);
        BoundingBox blockBox = structureStart.getBoundingBox();
        ChunkPos chunkPos = new ChunkPos(SectionPos.getSectionCoord(blockBox.getMinX()), SectionPos.getSectionCoord(blockBox.getMinZ()));
        ChunkPos chunkPos2 = new ChunkPos(SectionPos.getSectionCoord(blockBox.getMaxX()), SectionPos.getSectionCoord(blockBox.getMaxZ()));
        ChunkPos[] chunkPosArray =  ChunkPos.stream(chunkPos, chunkPos2).adjustArgs(ChunkPos[]::new);
        return  generate(serverWorld, structureStart, chunkPosArray);
    }
    public static boolean generateStructure(Level  originalWorld, ResourceKey<Level> targetWorldKey, ResourceKey<Structure> setStructures, StructureStart structureStart){
        return generateStructure(originalWorld, targetWorldKey, setStructures.getValue(), structureStart);
    }



    public static boolean generateStructurePartial(Level  originalWorld, ResourceKey<Level> targetWorldKey, ResourceLocation setStructures, StructureStart structureStart, ChunkPos[] chunkPosArray){

        if(originalWorld.isClientSide)return false;
        if(structureStart == null)return false;
        Optional<Holder.Reference<Structure>> structureReference = originalWorld.getRegistryManager().get(Registries.STRUCTURE).getEntry(setStructures);
        if(structureReference.isEmpty()) return false;
        ServerLevel serverWorld = originalWorld.getServer().getWorld(targetWorldKey);
        return  generate( serverWorld, structureStart, chunkPosArray);
    }

    public static boolean generateStructurePartial(Level  originalWorld, ResourceKey<Level> targetWorldKey, ResourceKey<Structure> setStructures, StructureStart structureStart, ChunkPos[] chunkPosArray){
        return generateStructurePartial(originalWorld, targetWorldKey, setStructures.getValue(), structureStart, chunkPosArray);
    }

    public static StructureStart structureStart (Level  originalWorld, ResourceKey<Level> targetWorldKey, ResourceLocation structureKey, BlockPos pos){
        if(originalWorld.isClientSide)return null;
        Optional<Holder.Reference<Structure>> structureReference = originalWorld.getRegistryManager().get(Registries.STRUCTURE).getEntry(structureKey);
        if(structureReference.isEmpty())return null;
        Holder.Reference<Structure> setStructures = structureReference.get();
        ServerLevel serverWorld = originalWorld.getServer().getWorld(targetWorldKey);
        ChunkGenerator generator = serverWorld.getChunkSource().getChunkGenerator();
        Structure structure2 = setStructures.value();
        StructureStart structureStart = structure2.generate(
                originalWorld.getRegistryManager(),
                generator,
                generator.getBiomeSource(),
                serverWorld.getChunkSource().randomState(),
                serverWorld.getStructureManager(),
                originalWorld.getServer().getWorld(Level.OVERWORLD).getSeed(),
                new ChunkPos(pos),
                0,
                serverWorld,
                biomes -> true
        );
        return structureStart;
    }
    public static StructureStart structureStart (Level  originalWorld, ResourceKey<Level> targetWorldKey, ResourceKey<Structure> structureKey, BlockPos pos){
        return structureStart (originalWorld,targetWorldKey,structureKey.getValue(),pos);
    }

    /**
     * Register a StructureStart stub into the world's StructureAccessor without forcing full chunk population.
     * If the target chunk is already loaded, the method will attempt to place the center piece only.
     * Returns true on success.
     */
    public static boolean registerStructureStartStub(Level originalWorld, ResourceKey<Level> targetWorldKey, ResourceLocation structureKey, BlockPos anchor){
        if (originalWorld.isClientSide) return false;
        try{
            java.util.Optional<Holder.Reference<Structure>> opt = originalWorld.getRegistryManager().get(Registries.STRUCTURE).getEntry(structureKey);
            if (opt.isEmpty()) return false;
            Holder.Reference<Structure> structureRef = opt.get();
            Structure setStructures = structureRef.value();

            ServerLevel serverWorld = originalWorld.getServer().getWorld(targetWorldKey);
            if (serverWorld == null) return false;

            ChunkGenerator generator = serverWorld.getChunkSource().getChunkGenerator();

            StructureStart start = setStructures.generate(
                    originalWorld.getRegistryManager(),
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

            // Try to register via StructureAccessor.setStartForStructure(ChunkPos, RegistryKey<Structure>, StructureStart)
            Object getMutable = serverWorld.structureManager();
            boolean registered = false;
            try{
                java.lang.reflect.Method height = getMutable.getClass().getMethod("setStartForStructure", ChunkPos.class, ResourceKey.class, StructureStart.class);
                // build RegistryKey<Structure>
                ResourceKey<Structure> structKey = ResourceKey.of(Registries.STRUCTURE, structureKey);
                height.invoke(getMutable, startChunk, structKey, start);
                registered = true;
            }catch(NoSuchMethodException e){
                // attempt alternative method name: setStart
                for (java.lang.reflect.Method m2 : getMutable.getClass().getMethods()){
                    Class<?>[] pts = m2.getParameterTypes();
                    if (pts.length == 2 && pts[0] == ChunkPos.class && pts[1] == StructureStart.class){
                        m2.invoke(getMutable, startChunk, start);
                        registered = true;
                        break;
                    }
                }
            }

            // If registered, try to persist/mark dirty only if the chunk is loaded already.
            if (registered){
                Object chunkSource = serverWorld.getChunkSource();
                boolean chunkLoaded = false;
                // try common method signatures to check whether chunk is loaded without forcing generation
                try{
                    java.lang.reflect.Method isLoadedMethod = chunkSource.getClass().getMethod("isChunkLoaded", int.class, int.class);
                    Object res = isLoadedMethod.invoke(chunkSource, startChunk.x, startChunk.z);
                    if (res instanceof Boolean) chunkLoaded = (Boolean) res;
                }catch(Exception ignored){
                    try{
                        java.lang.reflect.Method isLoadedMethod2 = chunkSource.getClass().getMethod("isLoaded", ChunkPos.class);
                        Object res = isLoadedMethod2.invoke(chunkSource, startChunk);
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
                    if (pts.length == 2 && ResourceKey.class.isAssignableFrom(pts[0]) && StructureStart.class.isAssignableFrom(pts[1])){
                        ResourceKey<Structure> structKey = ResourceKey.of(Registries.STRUCTURE, structureKey);
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

    private static boolean generate(ServerLevel serverWorld, StructureStart structureStart, ChunkPos[] chunkPosArray){
        ChunkGenerator generator = serverWorld.getChunkSource().getChunkGenerator();
        if(!structureStart.isValid()){
            return false;
        }else {
            for (ChunkPos chunkPosx : chunkPosArray) {
                structureStart.place(
                        serverWorld,
                        serverWorld.structureManager(),
                        generator,
                        serverWorld.getRandom(),
                        new BoundingBox(chunkPosx.getMinBlockX(), serverWorld.getBottomY(), chunkPosx.getMinBlockZ(), chunkPosx.getMaxBlockX(), serverWorld.getTopY(), chunkPosx.getMaxBlockZ()),
                        chunkPosx
                );
            }
            unloadNearbyChunks(chunkPosArray, serverWorld);
            return true;
        }
    }

    public static void forceLoadChunks(ChunkPos[] chunkPosArray, ServerLevel world) {
        for (ChunkPos chunkPos : chunkPosArray) {
            world.updateChunkForced(chunkPos.x, chunkPos.z, true);
        }
    }
    public static void unloadNearbyChunks(ChunkPos[] chunkPosArray, ServerLevel world) {
        for (ChunkPos chunkPos : chunkPosArray) {
            world.updateChunkForced(chunkPos.x, chunkPos.z, false);
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
