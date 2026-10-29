package net.ugi.sculk_depths.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.ugi.sculk_depths.block.ModBlocks;

public class ConfigurableSpikes
        extends Feature<NoneFeatureConfiguration> { 
    Block[] canSpawnOn;

    public ConfigurableSpikes(Codec<NoneFeatureConfiguration> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos blockPos = context.origin();

        RandomSource random = context.getRandom();
        int topOffsetRangeMax = 16; //xz coordinaterange
        int topOffsetRangeMin = 0;
        int maxHeight = random.getInt(20) + 27; //height of spike
        int cruxChance = 5; //percentage
        this.canSpawnOn = new Block[] {ModBlocks.AURIC_SPORE_BLOCK};

        return makeSpike(context,blockPos,topOffsetRangeMax, topOffsetRangeMin,maxHeight,cruxChance);
    }
    public static boolean genSpike(FeaturePlaceContext<NoneFeatureConfiguration> context, BlockPos blockPos, int topOffsetRangeMax,int topOffsetRangeMin, int maxHeight, int cruxChance, Block[] canSpawnOn){
        ConfigurableSpikes obj = new ConfigurableSpikes(NoneFeatureConfiguration.CODEC);
        obj.canSpawnOn = canSpawnOn;
        return obj.makeSpike(context, blockPos, topOffsetRangeMax,topOffsetRangeMin, maxHeight, cruxChance);

    }
    public boolean makeSpike(FeaturePlaceContext<NoneFeatureConfiguration> context, BlockPos blockPos, int topOffsetRangeMax,int topOffsetRangeMin, int maxHeight, int cruxChance) {

        RandomSource random = context.getRandom();
        WorldGenLevel structureWorldAccess = context.getWorld();
        /*while (structureWorldAccess.isAir(blockPos) && blockPos.getY() > structureWorldAccess.getBottomY() + 2) {//downwards raycast
            blockPos = blockPos.down();
        }*/
        //raycast down 20 blocks
        int i = 0;
        while(i < 20 && context.getWorld().getBlockState(blockPos).isOf(Blocks.AIR)){
            blockPos = blockPos.below();
            i++;
        }

        for (Block block : this.canSpawnOn) {
            if(!context.getWorld().getBlockState(blockPos.below()).isOf(block)){
                return false;
            }
            
        }

        int radiusTopOffset = (int)Math.ceil(Math.sqrt(random.nextBetween(2, (topOffsetRangeMax - topOffsetRangeMin) * 16) + topOffsetRangeMin * 16));
        int angleTopOffset =  random.nextBetween(0, 360);
        int xTopOffset = (int)Math.round(Math.cos(Math.toRadians(angleTopOffset))*radiusTopOffset);
        int zTopOffset = (int)Math.round(Math.sin(Math.toRadians(angleTopOffset))*radiusTopOffset);

        Vec3 relativeVector = new Vec3(xTopOffset, maxHeight, zTopOffset);//vector in the direction of the center line of blocks (base to top)
        Vec3 normalized3dVector = relativeVector.normalize(); //used for moving setblock one step at a time
        final int totalLength = (int) relativeVector.length();

        int length = totalLength;
        int radiusSetting = totalLength / 10 + random.getInt(2); //radius first step of calculation, random for variation in same length spikes

        double x = blockPos.getX();
        double y = blockPos.getY()-radiusSetting;
        double z = blockPos.getZ();
        //if top in stone => not generate
        if (!context.getWorld().getBlockState(new BlockPos((int)Math.round(x + relativeVector.x) ,(int)Math.round(y + relativeVector.y), (int)Math.round(z + relativeVector.z))).isOf(Blocks.AIR)) return false;

        int spawnCrux = random.getInt(100);

        while (length>0){
            int radius = Math.round(radiusSetting * (length+3)/totalLength);
            
            //non rounded tracking of coordinates
            //nextstep
            x = x + normalized3dVector.getX();
            y = y + normalized3dVector.getY();
            z = z + normalized3dVector.getZ();

            int radiusSquared = radius * radius;
            Iterable<BlockPos> blockPosIterable = BlockPos.withinManhattan(new BlockPos( (int)Math.round(x), (int)Math.round(y), (int)Math.round(z)), radius, (int) (radius*1.5) + 2, radius);
            double finalX = x;
            double finalY = y;
            double finalZ = z;
            blockPosIterable.forEach(blockPos1 -> {
                int distanceToPlayer = (int) ((blockPos1.getX() - finalX)*(blockPos1.getX() - finalX) + ((blockPos1.getY() - finalY)/1.5)*((blockPos1.getY() - finalY)/1.5) + (blockPos1.getZ() - finalZ)*(blockPos1.getZ() - finalZ));
                if (distanceToPlayer <= radiusSquared) {//ball instead of rectangle
                    if(context.getWorld().getBlockState(blockPos1).isOf(ModBlocks.LARGUTH)){}
                    else if (spawnCrux < cruxChance) {
                        if(!context.getWorld().getBlockState(blockPos1.above()).isOf(Blocks.AIR) &&
                                !context.getWorld().getBlockState(blockPos1.below()).isOf(Blocks.AIR) &&
                                !context.getWorld().getBlockState(blockPos1.north()).isOf(Blocks.AIR) &&
                                !context.getWorld().getBlockState(blockPos1.east()).isOf(Blocks.AIR) &&
                                !context.getWorld().getBlockState(blockPos1.south()).isOf(Blocks.AIR) &&
                                !context.getWorld().getBlockState(blockPos1.west()).isOf(Blocks.AIR) &&
                                random.getInt(10) < 5 ||
                                random.getInt(70) == 0)
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