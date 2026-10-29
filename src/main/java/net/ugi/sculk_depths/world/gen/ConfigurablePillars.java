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

public class ConfigurablePillars
        extends Feature<NoneFeatureConfiguration> {

    public ConfigurablePillars(Codec<NoneFeatureConfiguration> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        BlockPos blockPos = context.origin();

        RandomSource random = context.getRandom();
        WorldGenLevel structureWorldAccess = context.getWorld();
        /*while (structureWorldAccess.isAir(blockPos) && blockPos.getY() > structureWorldAccess.getBottomY() + 2) {//downwards raycast
            blockPos = blockPos.down();
        }*/
        //raycast down 20 blocks
        int i = 0;
        while(i < 10 && !context.getWorld().getBlockState(blockPos).isOf(ModBlocks.AURIC_SPORE_BLOCK)){
            blockPos = blockPos.below();
            i++;
        }

        if (!context.getWorld().getBlockState(blockPos.below()).isOf(ModBlocks.AURIC_SPORE_BLOCK)) return false;

        //config
        int topOffsetRange = random.getInt(10); //xz coordinaterange
        int maxHeight = random.getInt(50) + 70; //height of spike

        int xTopOffset = random.nextBetween(-topOffsetRange, topOffsetRange);//x pos of top
        int zTopOffset = random.nextBetween(-topOffsetRange, topOffsetRange);//z pos of top

        Vec3 relativeVector = new Vec3(xTopOffset, maxHeight, zTopOffset);//vector in the direction of the center line of blocks (base to top)
        Vec3 normalized3dVector = relativeVector.normalize(); //used for moving setblock one step at a time
        final int totalLength = (int) relativeVector.length();

        int length = totalLength;
        int radiusSetting = random.getInt(5)+4; //radius first step of calculation, random for variation in same length spikes

        double x = blockPos.getX();
        double y = blockPos.getY()-30;
        double z = blockPos.getZ();
        //if top in stone => not generate
        if (!context.getWorld().getBlockState(new BlockPos((int)Math.round(x + relativeVector.x) ,(int)Math.round(y + relativeVector.y), (int)Math.round(z + relativeVector.z))).isOf(Blocks.AIR)) return false;

        int spawnCrux = random.getInt(100);
        int j = 0;
        while (length - j>0){
            int radius = radiusSetting;
            
            //non rounded tracking of coordinates
            //nextstep
            x = x + normalized3dVector.getX();
            y = y + normalized3dVector.getY();
            z = z + normalized3dVector.getZ();

            int radiusSquared = radius * radius;
            Iterable<BlockPos> blockPosIterable = BlockPos.withinManhattan(new BlockPos( (int)Math.round(x), (int)Math.round(y), (int)Math.round(z)), radius, 1, radius);
            double finalX = x;
            double finalY = y;
            double finalZ = z;
            blockPosIterable.forEach(blockPos1 -> {
                int distanceToPlayer = (int) ((blockPos1.getX() - finalX)*(blockPos1.getX() - finalX) + ((blockPos1.getY() - finalY)/1)*((blockPos1.getY() - finalY)/1) + (blockPos1.getZ() - finalZ)*(blockPos1.getZ() - finalZ));
                if (distanceToPlayer <= radiusSquared) {//ball instead of rectangle
                    this.setBlockState(structureWorldAccess, blockPos1, ModBlocks.CRUMBLING_FLUMROCK_TILES.getDefaultState());
                }
            });
            if (j > 40){
                if (random.getInt(10) == 0){
                    int angleTopOffset =  random.nextBetween(0, 360);
                    int holeX = (int)Math.round(Math.cos(Math.toRadians(angleTopOffset))*radius);
                    int holeZ = (int)Math.round(Math.sin(Math.toRadians(angleTopOffset))*radius);

                    int holeRadius = random.getInt((int)(radius/4))+2;

                    int finalX2 = (int)Math.round(x+holeX);
                    int finalY2 = (int)Math.round(y-5);
                    int finalZ2 = (int)Math.round(z+holeZ);

                    Iterable<BlockPos> blockPosIterable2 = BlockPos.withinManhattan(new BlockPos( (int)Math.round(finalX2), (int)Math.round(finalY2), (int)Math.round(finalZ2)), (holeRadius*2) + 2, holeRadius+1, (holeRadius*2) + 2);

                    blockPosIterable2.forEach(blockPos2 -> {
                        int distanceToPlayer = (int) (((blockPos2.getX() - finalX2)/2)*((blockPos2.getX() - finalX2)/2) + ((blockPos2.getY() - finalY2)/1)*((blockPos2.getY() - finalY2)/1) + ((blockPos2.getZ() - finalZ2)/2)*((blockPos2.getZ() - finalZ2)/2));
                        if (Math.round(distanceToPlayer) <= (holeRadius-1)*(holeRadius-1) && context.getWorld().getBlockState(blockPos2).isOf(ModBlocks.CRUMBLING_FLUMROCK_TILES)) {//ball instead of rectangle
                            this.setBlockState(structureWorldAccess, blockPos2, Blocks.AIR.getDefaultState());
                        }
                        else if (Math.round(distanceToPlayer) <= holeRadius*holeRadius && context.getWorld().getBlockState(blockPos2).isOf(ModBlocks.CRUMBLING_FLUMROCK_TILES) && random.getInt(5) != 0) {//ball instead of rectangle
                            this.setBlockState(structureWorldAccess, blockPos2, ModBlocks.LARGUTH.getDefaultState());
                        }
                    });
                }
            }
            j++;

        }
        for(x = 0; x < totalLength/20; x++){
            int randomY = random.getInt(maxHeight-40);
            BlockPos blockPos2 = blockPos.above(randomY);
            Block[] canSpawnOn = {ModBlocks.CRUMBLING_FLUMROCK_TILES};
            ConfigurableSpikes.genSpike(context,blockPos2,16, radiusSetting+2,random.getInt(20) + 15,5,canSpawnOn);
        }
        return true;
    }

}