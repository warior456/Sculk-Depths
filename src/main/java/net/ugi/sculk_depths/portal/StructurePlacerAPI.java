package net.ugi.sculk_depths.portal;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.structure.processor.BlockRotStructureProcessor;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

//@SuppressWarnings("unused")
public class StructurePlacerAPI {

    private final StructureWorldAccess world;
    private final Identifier templateName;
    private final BlockPos blockPos;
    private final BlockMirror mirror;
    private final BlockRotation rotation;
    private final boolean ignoreEntities;
    private final float integrity;
    private final BlockPos offset;
    private Vec3i size = Vec3i.ZERO;

    private final Logger LOGGER = LoggerFactory.getLogger("structureplacerapi");

    /**
     * With this you can create placer object which will spawn
     * a new structure from an nbt file located in /data/modid/structures
     *
     * @param world The StructureWorldAccess in which to place the structure
     * @param templateName The identifier of the structure to place, like <code>new Identifier(MOD_ID, structure_name)</code>
     * @param blockPos The position of the structure
     * @param mirror Use this to mirror the structure using <code>BlockMirror.#</code>
     * @param rotation Use this to rotate the structure using <code>BlockRotation.#</code>
     * @param ignoreEntities Set to true to block the spawning of entities saved in the structure file
     * @param integrity Set this to a value between 0f and 1f to remove some blocks from the placed structure. (All blocks = 1f)
     * @param offset Use this to offset the placing of the structure.
     * */
    public StructurePlacerAPI(StructureWorldAccess world, Identifier templateName, BlockPos blockPos, BlockMirror mirror, BlockRotation rotation, boolean ignoreEntities, float integrity, BlockPos offset){
        this.world = world;
        this.templateName = templateName;
        this.blockPos = blockPos;
        this.mirror = mirror;
        this.rotation = rotation;
        this.ignoreEntities = ignoreEntities;
        this.integrity = integrity;
        this.offset = offset;

    }

    /**
     * With this you can create placer object which will spawn
     * a new structure from an nbt file located in /data/modid/structures
     *
     * @param world The StructureWorldAccess in which to place the structure
     * @param templateName The identifier of the structure to place, like <code>new Identifier(MOD_ID, structure_name)</code>
     * @param blockPos The position of the structure
     * */
    public StructurePlacerAPI(StructureWorldAccess world, Identifier templateName, BlockPos blockPos) {
        this.world = world;
        this.templateName = templateName;
        this.blockPos = blockPos;
        this.mirror = BlockMirror.NONE;
        this.rotation = BlockRotation.NONE;
        this.ignoreEntities = true;
        this.integrity = 1.0f;
        this.offset = new BlockPos(0, 0, 0);
    }

    /**
     * With this you can create placer object which will spawn
     * a new structure from an nbt file located in /data/modid/structures
     *
     * @param world The StructureWorldAccess in which to place the structure
     * @param templateName The identifier of the structure to place, like <code>new Identifier(MOD_ID, structure_name)</code>
     * @param blockPos The position of the structure
     * @param offset Use this to offset the placing of the structure.
     * */
    public StructurePlacerAPI(StructureWorldAccess world, Identifier templateName, BlockPos blockPos, BlockPos offset){
        this.world = world;
        this.templateName = templateName;
        this.blockPos = blockPos;
        this.mirror = BlockMirror.NONE;
        this.rotation = BlockRotation.NONE;
        this.ignoreEntities = true;
        this.integrity = 1.0f;
        this.offset = offset;
    }

    /**
     * With this you can create placer object which will spawn
     * a new structure from an nbt file located in /data/modid/structures
     *
     * @param world The StructureWorldAccess in which to place the structure
     * @param templateName The identifier of the structure to place, like <code>new Identifier(MOD_ID, structure_name)</code>
     * @param blockPos The position of the structure
     * @param mirror Use this to mirror the structure using <code>BlockMirror.#</code>
     */
    public StructurePlacerAPI(StructureWorldAccess world, Identifier templateName, BlockPos blockPos, BlockMirror mirror){
        this.world = world;
        this.templateName = templateName;
        this.blockPos = blockPos;
        this.mirror = mirror;
        this.rotation = BlockRotation.NONE;
        this.ignoreEntities = true;
        this.integrity = 1.0f;
        this.offset = new BlockPos(0, 0, 0);
    }

    /**
     * With this you can create placer object which will spawn
     * a new structure from an nbt file located in /data/modid/structures
     *
     * @param world The StructureWorldAccess in which to place the structure
     * @param templateName The identifier of the structure to place, like <code>new Identifier(MOD_ID, structure_name)</code>
     * @param blockPos The position of the structure
     * @param rotation Use this to rotate the structure using <code>BlockRotation.#</code>
     * */
    public StructurePlacerAPI(StructureWorldAccess world, Identifier templateName, BlockPos blockPos, BlockRotation rotation){
        this.world = world;
        this.templateName = templateName;
        this.blockPos = blockPos;
        this.mirror = BlockMirror.NONE;
        this.rotation = rotation;
        this.ignoreEntities = true;
        this.integrity = 1.0f;
        this.offset = new BlockPos(0, 0, 0);
    }

    /**
     * With this you can create placer object which will spawn
     * a new structure from an nbt file located in /data/modid/structures
     *
     * @param world The StructureWorldAccess in which to place the structure
     * @param templateName The identifier of the structure to place, like <code>new Identifier(MOD_ID, structure_name)</code>
     * @param blockPos The position of the structure
     * @param mirror Use this to mirror the structure using <code>BlockMirror.#</code>
     * @param rotation Use this to rotate the structure using <code>BlockRotation.#</code>
     * */
    public StructurePlacerAPI(StructureWorldAccess world, Identifier templateName, BlockPos blockPos, BlockMirror mirror, BlockRotation rotation){
        this.world = world;
        this.templateName = templateName;
        this.blockPos = blockPos;
        this.mirror = mirror;
        this.rotation = rotation;
        this.ignoreEntities = true;
        this.integrity = 1.0f;
        this.offset = new BlockPos(0, 0, 0);
    }

    /**
     * With this you can create placer object which will spawn
     * a new structure from an nbt file located in /data/modid/structures
     *
     * @param world The StructureWorldAccess in which to place the structure
     * @param templateName The identifier of the structure to place, like <code>new Identifier(MOD_ID, structure_name)</code>
     * @param blockPos The position of the structure
     * @param integrity Set this to a value between 0f and 1f to remove some blocks from the placed structure. (All blocks = 1f)
     * */
    public StructurePlacerAPI(StructureWorldAccess world, Identifier templateName, BlockPos blockPos, float integrity){
        this.world = world;
        this.templateName = templateName;
        this.blockPos = blockPos;
        this.mirror = BlockMirror.NONE;
        this.rotation = BlockRotation.NONE;
        this.ignoreEntities = true;
        this.integrity = integrity;
        this.offset = new BlockPos(0, 0, 0);
    }

    /**Use this method to load the structure into the world and
     * place it. You can check to see if the placing was successful.
     * <p>
     * This method DOES NOT support regenerating the old terrain, use <code>loadAndRestore()</code> instead
     * if you want to do it. This method however consumes less resources.
     */
    public boolean loadStructure() {
        if (this.templateName != null && world.getServer() != null) {
            StructureTemplateManager structureTemplateManager = world.getServer().getStructureTemplateManager();
            Optional<StructureTemplate> optional;
            System.out.println("1");
            try {
                optional = structureTemplateManager.getTemplate(this.templateName);
                System.out.println(optional);
                System.out.println("2");
            } catch (InvalidIdentifierException e) {
                return false;
            }
            System.out.println("3");
            System.out.println(optional.isPresent());
            //System.out.println(this.place(optional.get());
            return optional.isPresent() && this.place(optional.get());
        } else {
            return false;
        }
    }

    /**This method is used by the <code>loadStructure()</code> method,
     * which already checks if the structure exists or not, so use that instead*/
    public boolean place(StructureTemplate template) {
        System.out.println("4");
        try {
            System.out.println("5");
            StructurePlacementData structurePlacementData = (new StructurePlacementData()).setMirror(this.mirror).setRotation(this.rotation).setIgnoreEntities(this.ignoreEntities);
            if (this.integrity < 1.0F) {
                structurePlacementData.clearProcessors().addProcessor(new BlockRotStructureProcessor(MathHelper.clamp(this.integrity, 0.0F, 1.0F))).setRandom(createRandom(this.world.getSeed()));
            }
            BlockPos pos = blockPos.add(this.offset);
            template.place(world, pos, pos, structurePlacementData, createRandom(this.world.getSeed()), 2);
            unloadStructure();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**This method unloads the structure after it has been placed.
     * No need to use it on your own, included during placement*/
    private void unloadStructure() {
        if (this.templateName != null && world.getServer() != null) {
            StructureTemplateManager structureTemplateManager = world.getServer().getStructureTemplateManager();
            structureTemplateManager.unloadTemplate(this.templateName);
        }
    }

    /**This method creates a random seed for the integrity run-down effect.
     * No need to use it on your own, included during placement*/
    public static Random createRandom(long seed) {
        return seed == 0L ? Random.create(Util.getMeasuringTimeMs()) : Random.create(seed);
    }


    /**Use this method to load the structure into the world and
     * spawn it. You can check to see if the placing was successful.
     * <p>
     * It will also restore the blocks it replaced after <i>restore_ticks</i>
     * Calling this function from the client only, will not regenerate the old terrain.
     * And will probably cause other issues.
     *<p>
     * <b>Notice:</b> Using this could lead to performance issues, especially with very large structures!
     *
     * @param restore_ticks Number of ticks (1 second = 20 ticks) after which the world would be restored.
     */
    public boolean loadAndRestoreStructure(int restore_ticks) {
        if (this.templateName != null && world.getServer() != null) {
            StructureTemplateManager structureTemplateManager = world.getServer().getStructureTemplateManager();
            Optional<StructureTemplate> optional;
            try {
                optional = structureTemplateManager.getTemplate(this.templateName);
            } catch (InvalidIdentifierException var6) {
                return false;
            }

            optional.ifPresent(structureTemplate -> this.size = structureTemplate.getSize());

            if(!this.world.isClient()){
                scheduleReplacement(restore_ticks, saveFromWorld(this.world, this.blockPos.add(this.offset), size));
            }
            return optional.isPresent() && this.place(optional.get());
        } else {
            return false;
        }
    }

    /**Use this method to load the structure into the world and
     * spawn it. You can check to see if the placing was successful.
     *<p>
     * It will also restore the blocks it replaced after <i>restore_ticks</i> with an animation
     *<p>
     * Calling this function from the client only will not regenerate the old terrain.
     * And will probably cause other issues.
     *<p>
     * <b>Notice:</b> Using this could lead to performance issues, especially with very large structures!
     *
     * @param restore_ticks Number of ticks (1 second = 20 ticks) after which the blocks will begin to be restored
     * @param blocks_per_tick How many blocks to restore per tick, the more, the faster the animation will go.
     * @param random Weather or not the blocks will be removed at random. If false, they will be removed from one corner to the other in sequence
     */
    public boolean loadAndRestoreStructureAnimated(int restore_ticks, int blocks_per_tick, boolean random) {
        if (this.templateName != null && world.getServer() != null) {
            StructureTemplateManager structureTemplateManager = world.getServer().getStructureTemplateManager();
            Optional<StructureTemplate> optional;
            try {
                optional = structureTemplateManager.getTemplate(this.templateName);
            } catch (InvalidIdentifierException var6) {
                return false;
            }
            optional.ifPresent(structureTemplate -> this.size = structureTemplate.getSize());

            if(!this.world.isClient()){
                scheduleReplacementAnimated(restore_ticks, blocks_per_tick, random, saveFromWorld(this.world, this.blockPos.add(this.offset), size));
            }
            return optional.isPresent() && this.place(optional.get());
        } else {
            return false;
        }
    }



    /**Schedules the replacement of the older terrain/world and
     * after <i>ticks</i> it executes the replacement.
     *<p>
     * TO CALL SERVER SIDE ONLY*/
    private void scheduleReplacement(int ticks, List<StructureTemplate.StructureBlockInfo> blockInfoList){
        AtomicInteger counter = new AtomicInteger();
        counter.set(0);

        ServerTickEvents.END_SERVER_TICK.register((server -> {

            if(counter.get() == -1){
                return;
            }
            if(counter.get() == ticks){

                for(StructureTemplate.StructureBlockInfo info : blockInfoList){
                    world.setBlockState(info.pos(), info.state(), Block.NOTIFY_ALL);

                    if (info.nbt() != null) {
                        //The blockentities check needs to be done on the main thread
                        server.execute( () -> {
                            BlockEntity blockEntity = world.getBlockEntity(info.pos());
                            if (blockEntity != null) {
                                if (blockEntity instanceof LootableContainerBlockEntity) {
                                    info.nbt().putLong("LootTableSeed", Objects.requireNonNull(blockEntity.getWorld()).getRandom().nextLong());
                                }

                                blockEntity.read(info.nbt(), world.getRegistryManager());
                            }
                        });
                    }
                }
                counter.set(-1);

            }else{
                counter.getAndIncrement();
            }

        }));

    }


    /**Schedules the replacement of the older terrain/world and
     * after <i>ticks</i> it executes the replacement.
     * It also does this with a basic animation
     *<p>
     * TO CALL SERVER SIDE ONLY
     *
     * @param ticks Number of ticks after which the blocks will begin to be restored
     * @param blocks_per_tick How many blocks to restore per tick, the more, the faster the animation will go
     * @param rand Weather or not the blocks will be removed at random. If false, they will be removed from one corner to the other in sequence
     * */
    private void scheduleReplacementAnimated(int ticks, int blocks_per_tick, boolean rand, List<StructureTemplate.StructureBlockInfo> blockInfoList){

        List<StructureTemplate.StructureBlockInfo> infoList = new ArrayList<>();
        AtomicInteger count = new AtomicInteger();

        ServerTickEvents.END_SERVER_TICK.register((server -> {

            if(count.get() == -1){
                return;
            }

            if(count.get() >= ticks){
                //If the length of the list is the same as the blocks placed it means it already placed the last one

                if(count.get() == ticks){
                    infoList.addAll(blockInfoList);
                }
                if(infoList.isEmpty()){
                    count.set(-1);
                    return;
                }

                for(int j = 0; j<blocks_per_tick; j++){
                    if(infoList.isEmpty()){
                        count.set(-1);
                        return;
                    }
                    StructureTemplate.StructureBlockInfo info;
                    if(rand){
                        int i;
                        if(infoList.size()-1 == 0){
                            i = 0;
                        } else{
                            i = this.world.getRandom().nextInt(infoList.size()-1);
                        }
                        info = infoList.get(i);
                        infoList.remove(i);
                    }else{
                        info = infoList.get(count.get() - ticks);
                    }

                    world.setBlockState(info.pos(), info.state(), Block.NOTIFY_ALL);

                    if (info.nbt() != null) {
                        //The blockentities check needs to be done on the main thread
                        server.execute( () -> {
                            BlockEntity blockEntity = world.getBlockEntity(info.pos());
                            if (blockEntity != null) {
                                if (blockEntity instanceof LootableContainerBlockEntity) {
                                    info.nbt().putLong("LootTableSeed", Objects.requireNonNull(blockEntity.getWorld()).getRandom().nextLong());
                                }
                                blockEntity.read(info.nbt(), world.getRegistryManager());
                            }
                        });
                    }
                }


            }
            count.getAndIncrement();
        }));
        infoList.clear();
    }


    /**Saves the block infos to <i>blockInfoLists</i>
     *<p>
     * Will also debug log the time it took to save the structure.
     */
    private List<StructureTemplate.StructureBlockInfo> saveFromWorld(StructureWorldAccess world, BlockPos start, Vec3i dimensions) {
        List<StructureTemplate.StructureBlockInfo> blockInfoList = new ArrayList<>();
        Instant start_time = Instant.now();
        LOGGER.debug("Saving terrain to later restore it...");
        BlockPos blockPos = start.add(dimensions).add(-1, -1, -1);
        BlockPos min_pos = new BlockPos(Math.min(start.getX(), blockPos.getX()), Math.min(start.getY(), blockPos.getY()), Math.min(start.getZ(), blockPos.getZ()));
        BlockPos max_pos = new BlockPos(Math.max(start.getX(), blockPos.getX()), Math.max(start.getY(), blockPos.getY()), Math.max(start.getZ(), blockPos.getZ()));

        //Iterates through all the block positions and adds them to list
        BlockPos.iterate(min_pos, max_pos).iterator().forEachRemaining(pos -> {
                    //Copying the pos, so it doesn't mutate and make a mess
                    BlockPos save_pos;
                    save_pos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

                    BlockEntity blockEntity = world.getBlockEntity(save_pos);
                    StructureTemplate.StructureBlockInfo structureBlockInfo;

                    //This means the block has an inventory, that it has already dropped, so don't copy its nbt

                    if (blockEntity != null) {
                        boolean has_inventory = Inventory.class.isAssignableFrom(blockEntity.getClass());
                        if(has_inventory){
                            structureBlockInfo = new StructureTemplate.StructureBlockInfo(save_pos, world.getBlockState(save_pos), null);
                        }else{
                            structureBlockInfo = new StructureTemplate.StructureBlockInfo(save_pos, world.getBlockState(save_pos), blockEntity.createNbtWithId(world.getRegistryManager()));
                        }
                    } else {
                        structureBlockInfo = new StructureTemplate.StructureBlockInfo(save_pos, world.getBlockState(save_pos), null);
                    }
                    blockInfoList.add(structureBlockInfo);
                }
        );

        Instant end_time = Instant.now();
        Duration timeElapsed = Duration.between(start_time, end_time);

        LOGGER.debug("Terrain saved! It took: " + timeElapsed.toMillis() + "ms");
        return blockInfoList;

    }
}