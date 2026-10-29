package net.ugi.sculk_depths.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.ugi.sculk_depths.block.ModBlocks;

public class AuricMushroom
        extends Feature<NoneFeatureConfiguration> {


    public AuricMushroom(Codec<NoneFeatureConfiguration> configCodec) {
        super(configCodec);
    }
    public boolean isNotValidBlock(FeaturePlaceContext<NoneFeatureConfiguration> context,BlockPos blockPos){
        return (!context.getWorld().getBlockState(blockPos).isOf(Blocks.AIR)
                && !context.getWorld().getBlockState(blockPos).isOf(ModBlocks.AURIC_SHROOM_BLOCK)
                && !context.getWorld().getBlockState(blockPos).isOf(ModBlocks.AURIC_SHROOM_STEM));

    }
    public void generateMushroomCap(FeaturePlaceContext<NoneFeatureConfiguration> context,BlockPos blockPos, int radius){
        WorldGenLevel structureWorldAccess = context.getWorld();

        Iterable<BlockPos> blockPosIterable = BlockPos.withinManhattan(new BlockPos( Math.round(blockPos.getX()), Math.round(blockPos.getY()), Math.round(blockPos.getZ())), radius, 0, radius);
        blockPosIterable.forEach(blockPos1 -> {
            int distanceToPlayer = (int) ((blockPos1.getX() - blockPos.getX())*(blockPos1.getX() - blockPos.getX()) + (blockPos1.getY() - blockPos.getY())*(blockPos1.getY() - blockPos.getY()) + (blockPos1.getZ() - blockPos.getZ())*(blockPos1.getZ() - blockPos.getZ()));
            if(distanceToPlayer <= radius*radius+1){
                if(!isNotValidBlock(context,blockPos1)){
                    this.setBlockState(structureWorldAccess, blockPos1, ModBlocks.AURIC_SHROOM_BLOCK.getDefaultState());
                }
            }
        });
    }
    public boolean generateMushroomStem(FeaturePlaceContext<NoneFeatureConfiguration> context,BlockPos blockPos,int splitchance){

        RandomSource random = context.getRandom();
        WorldGenLevel structureWorldAccess = context.getWorld();

        int heightunderSplit = random.nextBetween(1,2);
        boolean split = random.nextBetween(1,100) <= splitchance;
        int splitLenghtmain = random.nextBetween(1,2);
        int splitLenghtside = random.nextBetween(1,1);
        boolean splitLenghtsideAdd1toheight = random.nextBetween(0,1) == 0;
        boolean splitDirection = random.nextBetween(0,1) == 0;
        int splitDirectionPosNeg = random.nextBetween(0,1) == 0? -1 : 1;

        if(isNotValidBlock(context,blockPos)) return false;
        this.setBlockState(structureWorldAccess, blockPos, ModBlocks.AURIC_SHROOM_STEM.getDefaultState());
        int i = 0;
        while(i < heightunderSplit){
            blockPos = blockPos.above();
            if(isNotValidBlock(context,blockPos)) return false;
            this.setBlockState(structureWorldAccess, blockPos, ModBlocks.AURIC_SHROOM_STEM.getDefaultState());
            i++;
        }
        if (split) {
             BlockPos blockPos1 = blockPos;
            i = 0;
            while(i < splitLenghtmain){
                blockPos = blockPos.above();
                if(splitDirection) {
                    blockPos = blockPos.east(splitDirectionPosNeg);
                }
                else {
                    blockPos = blockPos.north(splitDirectionPosNeg);
                }
                if(isNotValidBlock(context,blockPos)) return false;
                this.setBlockState(structureWorldAccess, blockPos, ModBlocks.AURIC_SHROOM_STEM.getDefaultState());
                i++;
            }
            if(isNotValidBlock(context,blockPos.above())) return false;
            generateMushroomStem(context,blockPos.above(), splitchance * 7/12);

            i = 0;
            while(i < splitLenghtside){
                blockPos1 = blockPos1.above();
                if(splitDirection) {
                    blockPos1 = blockPos1.east(-splitDirectionPosNeg);
                }
                else {
                    blockPos1 = blockPos1.north(-splitDirectionPosNeg);
                }
                if(isNotValidBlock(context,blockPos1)) return false;
                this.setBlockState(structureWorldAccess, blockPos1, ModBlocks.AURIC_SHROOM_STEM.getDefaultState());
                i++;
            }
            if (splitLenghtsideAdd1toheight) {
                blockPos1 = blockPos1.above();
                if(isNotValidBlock(context,blockPos1)) return false;
                this.setBlockState(structureWorldAccess, blockPos1, ModBlocks.AURIC_SHROOM_STEM.getDefaultState());
            }
            if(isNotValidBlock(context,blockPos1.above())) return false;
            generateMushroomCap(context, blockPos1.above(),1 );
        }
        else {
            if(isNotValidBlock(context,blockPos.above())) return false;
            generateMushroomCap(context, blockPos.above(),2 );
        }
        return true;
    }

    @Override
    public boolean generate(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos blockPos = context.origin();
        if (!context.getWorld().getBlockState(blockPos.below()).isOf(ModBlocks.AURIC_SPORE_BLOCK)) return false;

        generateMushroomStem(context,blockPos,75);
        return true;
    }
}