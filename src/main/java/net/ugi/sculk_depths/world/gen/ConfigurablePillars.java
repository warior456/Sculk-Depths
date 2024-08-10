package net.ugi.sculk_depths.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;

public class ConfigurablePillars
        extends Feature<DefaultFeatureConfig> {

    public ConfigurablePillars(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {

        BlockPos blockPos = context.getOrigin();

        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        /*while (structureWorldAccess.isAir(blockPos) && blockPos.getY() > structureWorldAccess.getBottomY() + 2) {//downwards raycast
            blockPos = blockPos.down();
        }*/
        //raycast down 20 blocks
        int i = 0;
        while(i < 10 && !context.getWorld().getBlockState(blockPos).isOf(ModBlocks.AURIC_SPORE_BLOCK)){
            blockPos = blockPos.down();
            i++;
        }

        if (!context.getWorld().getBlockState(blockPos.down()).isOf(ModBlocks.AURIC_SPORE_BLOCK)) return false;

        //config
        int topOffsetRange = 0; //xz coordinaterange
        int maxHeight = random.nextInt(50) + 70; //height of spike

        int xTopOffset = random.nextBetween(-topOffsetRange, topOffsetRange);//x pos of top
        int zTopOffset = random.nextBetween(-topOffsetRange, topOffsetRange);//z pos of top

        Vec3d relativeVector = new Vec3d(xTopOffset, maxHeight, zTopOffset);//vector in the direction of the center line of blocks (base to top)
        Vec3d normalized3dVector = relativeVector.normalize(); //used for moving setblock one step at a time
        final int totalLength = (int) relativeVector.length();

        int length = totalLength;
        int radiusSetting = random.nextInt(5)+4; //radius first step of calculation, random for variation in same length spikes

        double x = blockPos.getX();
        double y = blockPos.getY()-30;
        double z = blockPos.getZ();
        //if top in stone => not generate
        if (!context.getWorld().getBlockState(new BlockPos((int)Math.round(x + relativeVector.x) ,(int)Math.round(y + relativeVector.y), (int)Math.round(z + relativeVector.z))).isOf(Blocks.AIR)) return false;

        int spawnCrux = random.nextInt(100);

        while (length>0){
            int radius = radiusSetting;
            
            //non rounded tracking of coordinates
            //nextstep
            x = x + normalized3dVector.getX();
            y = y + normalized3dVector.getY();
            z = z + normalized3dVector.getZ();

            int radiusSquared = radius * radius;
            Iterable<BlockPos> blockPosIterable = BlockPos.iterateOutwards(new BlockPos( (int)Math.round(x), (int)Math.round(y), (int)Math.round(z)), radius, 1, radius);
            double finalX = x;
            double finalY = y;
            double finalZ = z;
            blockPosIterable.forEach(blockPos1 -> {
                int distance = (int) ((blockPos1.getX() - finalX)*(blockPos1.getX() - finalX) + ((blockPos1.getY() - finalY)/1)*((blockPos1.getY() - finalY)/1) + (blockPos1.getZ() - finalZ)*(blockPos1.getZ() - finalZ));
                if (distance <= radiusSquared) {//ball instead of rectangle
                    this.setBlockState(structureWorldAccess, blockPos1, ModBlocks.AMALGAMITE.getDefaultState());
                }
            });
            length--;

        }
        for(x = 0; x < totalLength/20; x++){
            int randomY = random.nextInt(maxHeight-40);
            BlockPos blockPos2 = blockPos.up(randomY);
            Block[] canSpawnOn = {ModBlocks.AMALGAMITE};
            ConfigurableSpikes.genSpike(context,blockPos2,16, radiusSetting+2,random.nextInt(20) + 15,5,canSpawnOn);
        }
        return true;
    }

}