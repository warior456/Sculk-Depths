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
        return (!context.level().getBlockState(blockPos).is(Blocks.AIR)
                && !context.level().getBlockState(blockPos).is(ModBlocks.AURIC_SHROOM_BLOCK)
                && !context.level().getBlockState(blockPos).is(ModBlocks.AURIC_SHROOM_STEM));

    }
    public void generateMushroomCap(FeaturePlaceContext<NoneFeatureConfiguration> context,BlockPos blockPos, int radius){
        WorldGenLevel structureWorldAccess = context.level();

        Iterable<BlockPos> blockPosIterable = BlockPos.withinManhattan(new BlockPos( Math.round(blockPos.getX()), Math.round(blockPos.getY()), Math.round(blockPos.getZ())), radius, 0, radius);
        blockPosIterable.forEach(blockPos1 -> {
            int distanceToPlayer = (int) ((blockPos1.getX() - blockPos.getX())*(blockPos1.getX() - blockPos.getX()) + (blockPos1.getY() - blockPos.getY())*(blockPos1.getY() - blockPos.getY()) + (blockPos1.getZ() - blockPos.getZ())*(blockPos1.getZ() - blockPos.getZ()));
            if(distanceToPlayer <= radius*radius+1){
                if(!isNotValidBlock(context,blockPos1)){
                    this.setBlock(structureWorldAccess, blockPos1, ModBlocks.AURIC_SHROOM_BLOCK.defaultBlockState());
                }
            }
        });
    }
    public boolean generateMushroomStem(FeaturePlaceContext<NoneFeatureConfiguration> context,BlockPos blockPos,int splitchance){

        RandomSource random = context.random();
        WorldGenLevel structureWorldAccess = context.level();

        int heightunderSplit = random.nextIntBetweenInclusive(1,2);
        boolean split = random.nextIntBetweenInclusive(1,100) <= splitchance;
        int splitLenghtmain = random.nextIntBetweenInclusive(1,2);
        int splitLenghtside = random.nextIntBetweenInclusive(1,1);
        boolean splitLenghtsideAdd1toheight = random.nextIntBetweenInclusive(0,1) == 0;
        boolean splitDirection = random.nextIntBetweenInclusive(0,1) == 0;
        int splitDirectionPosNeg = random.nextIntBetweenInclusive(0,1) == 0? -1 : 1;

        if(isNotValidBlock(context,blockPos)) return false;
        this.setBlock(structureWorldAccess, blockPos, ModBlocks.AURIC_SHROOM_STEM.defaultBlockState());
        int i = 0;
        while(i < heightunderSplit){
            blockPos = blockPos.above();
            if(isNotValidBlock(context,blockPos)) return false;
            this.setBlock(structureWorldAccess, blockPos, ModBlocks.AURIC_SHROOM_STEM.defaultBlockState());
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
                this.setBlock(structureWorldAccess, blockPos, ModBlocks.AURIC_SHROOM_STEM.defaultBlockState());
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
                this.setBlock(structureWorldAccess, blockPos1, ModBlocks.AURIC_SHROOM_STEM.defaultBlockState());
                i++;
            }
            if (splitLenghtsideAdd1toheight) {
                blockPos1 = blockPos1.above();
                if(isNotValidBlock(context,blockPos1)) return false;
                this.setBlock(structureWorldAccess, blockPos1, ModBlocks.AURIC_SHROOM_STEM.defaultBlockState());
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
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos blockPos = context.origin();
        if (!context.level().getBlockState(blockPos.below()).is(ModBlocks.AURIC_SPORE_BLOCK)) return false;

        generateMushroomStem(context,blockPos,75);
        return true;
    }
}