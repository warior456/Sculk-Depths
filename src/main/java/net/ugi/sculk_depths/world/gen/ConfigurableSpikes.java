package net.ugi.sculk_depths.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class ConfigurableSpikes
        extends Feature<DefaultFeatureConfig> { 
    Block[] canSpawnOn;

    public ConfigurableSpikes(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();

        Random random = context.getRandom();
        int topOffsetRangeMax = 16; //xz coordinaterange
        int topOffsetRangeMin = 0;
        int maxHeight = random.nextInt(20) + 27; //height of spike
        int cruxChance = 5; //percentage
        this.canSpawnOn = new Block[] {ModBlocks.AURIC_SPORE_BLOCK};

        return makeSpike(context,blockPos,topOffsetRangeMax, topOffsetRangeMin,maxHeight,cruxChance);
    }
    public static boolean genSpike(FeatureContext<DefaultFeatureConfig> context, BlockPos blockPos, int topOffsetRangeMax,int topOffsetRangeMin, int maxHeight, int cruxChance, Block[] canSpawnOn){
        ConfigurableSpikes obj = new ConfigurableSpikes(DefaultFeatureConfig.CODEC);
        obj.canSpawnOn = canSpawnOn;
        return obj.makeSpike(context, blockPos, topOffsetRangeMax,topOffsetRangeMin, maxHeight, cruxChance);

    }
    public boolean makeSpike(FeatureContext<DefaultFeatureConfig> context, BlockPos blockPos, int topOffsetRangeMax,int topOffsetRangeMin, int maxHeight, int cruxChance) {

        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        /*while (structureWorldAccess.isAir(blockPos) && blockPos.getY() > structureWorldAccess.getBottomY() + 2) {//downwards raycast
            blockPos = blockPos.down();
        }*/
        //raycast down 20 blocks
        int i = 0;
        while(i < 20 && context.getWorld().getBlockState(blockPos).isOf(Blocks.AIR)){
            blockPos = blockPos.down();
            i++;
        }

        for (Block block : this.canSpawnOn) {
            if(!context.getWorld().getBlockState(blockPos.down()).isOf(block)){
                return false;
            }
            
        }

        int radiusTopOffset = (int)Math.sqrt((random.nextBetween(0, topOffsetRangeMax-topOffsetRangeMin)+topOffsetRangeMin)*(random.nextBetween(0, topOffsetRangeMax-topOffsetRangeMin)+topOffsetRangeMin));
        int angleTopOffset =  random.nextBetween(0, 360);
        int xTopOffset = (int)Math.round(Math.cos(Math.toRadians(angleTopOffset))*radiusTopOffset);
        int zTopOffset = (int)Math.round(Math.sin(Math.toRadians(angleTopOffset))*radiusTopOffset);

        Vec3d relativeVector = new Vec3d(xTopOffset, maxHeight, zTopOffset);//vector in the direction of the center line of blocks (base to top)
        Vec3d normalized3dVector = relativeVector.normalize(); //used for moving setblock one step at a time
        final int totalLength = (int) relativeVector.length();

        int length = totalLength;
        int radiusSetting = totalLength / 10 + random.nextInt(2); //radius first step of calculation, random for variation in same length spikes

        double x = blockPos.getX();
        double y = blockPos.getY()-radiusSetting;
        double z = blockPos.getZ();
        //if top in stone => not generate
        if (!context.getWorld().getBlockState(new BlockPos((int)Math.round(x + relativeVector.x) ,(int)Math.round(y + relativeVector.y), (int)Math.round(z + relativeVector.z))).isOf(Blocks.AIR)) return false;

        int spawnCrux = random.nextInt(100);

        while (length>0){
            int radius = Math.round(radiusSetting * (length+3)/totalLength);
            
            //non rounded tracking of coordinates
            //nextstep
            x = x + normalized3dVector.getX();
            y = y + normalized3dVector.getY();
            z = z + normalized3dVector.getZ();

            int radiusSquared = radius * radius;
            Iterable<BlockPos> blockPosIterable = BlockPos.iterateOutwards(new BlockPos( (int)Math.round(x), (int)Math.round(y), (int)Math.round(z)), radius, (int) (radius*1.5) + 2, radius);
            double finalX = x;
            double finalY = y;
            double finalZ = z;
            blockPosIterable.forEach(blockPos1 -> {
                int distance = (int) ((blockPos1.getX() - finalX)*(blockPos1.getX() - finalX) + ((blockPos1.getY() - finalY)/1.5)*((blockPos1.getY() - finalY)/1.5) + (blockPos1.getZ() - finalZ)*(blockPos1.getZ() - finalZ));
                if (distance <= radiusSquared) {//ball instead of rectangle
                    if(context.getWorld().getBlockState(blockPos1).isOf(ModBlocks.LARGUTH)){}
                    else if (spawnCrux < cruxChance) {
                        if(!context.getWorld().getBlockState(blockPos1.up()).isOf(Blocks.AIR) &&
                                !context.getWorld().getBlockState(blockPos1.down()).isOf(Blocks.AIR) &&
                                !context.getWorld().getBlockState(blockPos1.north()).isOf(Blocks.AIR) &&
                                !context.getWorld().getBlockState(blockPos1.east()).isOf(Blocks.AIR) &&
                                !context.getWorld().getBlockState(blockPos1.south()).isOf(Blocks.AIR) &&
                                !context.getWorld().getBlockState(blockPos1.west()).isOf(Blocks.AIR) &&
                                random.nextInt(10) < 5 ||
                                random.nextInt(70) == 0)
                            this.setBlockState(structureWorldAccess, blockPos1, ModBlocks.CRUX_ORE.getDefaultState());
                        else this.setBlockState(structureWorldAccess, blockPos1, ModBlocks.ZYGRIN.getDefaultState());

                    }
                    else this.setBlockState(structureWorldAccess, blockPos1, ModBlocks.ZYGRIN.getDefaultState());
                }
            });
            length--;
        }
        return true;
    }

}