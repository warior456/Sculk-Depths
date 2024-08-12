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

public class AuricMushroom
        extends Feature<DefaultFeatureConfig> {


    public AuricMushroom(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }
    public boolean isNotValidBlock(FeatureContext<DefaultFeatureConfig> context,BlockPos blockPos){
        return (!context.getWorld().getBlockState(blockPos).isOf(Blocks.AIR)
                && !context.getWorld().getBlockState(blockPos).isOf(ModBlocks.AURIC_SHROOM_BLOCK)
                && !context.getWorld().getBlockState(blockPos).isOf(ModBlocks.AURIC_SHROOM_STEM));

    }
    public void generateMushroomCap(FeatureContext<DefaultFeatureConfig> context,BlockPos blockPos, int radius){
        StructureWorldAccess structureWorldAccess = context.getWorld();

        Iterable<BlockPos> blockPosIterable = BlockPos.iterateOutwards(new BlockPos( Math.round(blockPos.getX()), Math.round(blockPos.getY()), Math.round(blockPos.getZ())), radius, 0, radius);
        blockPosIterable.forEach(blockPos1 -> {
            int distance = (int) ((blockPos1.getX() - blockPos.getX())*(blockPos1.getX() - blockPos.getX()) + (blockPos1.getY() - blockPos.getY())*(blockPos1.getY() - blockPos.getY()) + (blockPos1.getZ() - blockPos.getZ())*(blockPos1.getZ() - blockPos.getZ()));
            if(distance <= radius*radius+1){
                if(!isNotValidBlock(context,blockPos1)){
                    this.setBlockState(structureWorldAccess, blockPos1, ModBlocks.AURIC_SHROOM_BLOCK.getDefaultState());
                }
            }
        });
    }
    public boolean generateMushroomStem(FeatureContext<DefaultFeatureConfig> context,BlockPos blockPos,int splitchance){

        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();

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
            blockPos = blockPos.up();
            if(isNotValidBlock(context,blockPos)) return false;
            this.setBlockState(structureWorldAccess, blockPos, ModBlocks.AURIC_SHROOM_STEM.getDefaultState());
            i++;
        }
        if (split) {
             BlockPos blockPos1 = blockPos;
            i = 0;
            while(i < splitLenghtmain){
                blockPos = blockPos.up();
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
            if(isNotValidBlock(context,blockPos.up())) return false;
            generateMushroomStem(context,blockPos.up(), splitchance * 7/12);

            i = 0;
            while(i < splitLenghtside){
                blockPos1 = blockPos1.up();
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
                blockPos1 = blockPos1.up();
                if(isNotValidBlock(context,blockPos1)) return false;
                this.setBlockState(structureWorldAccess, blockPos1, ModBlocks.AURIC_SHROOM_STEM.getDefaultState());
            }
            if(isNotValidBlock(context,blockPos1.up())) return false;
            generateMushroomCap(context, blockPos1.up(),1 );
        }
        else {
            if(isNotValidBlock(context,blockPos.up())) return false;
            generateMushroomCap(context, blockPos.up(),2 );
        }
        return true;
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        if (!context.getWorld().getBlockState(blockPos.down()).isOf(ModBlocks.AURIC_SPORE_BLOCK)) return false;

        generateMushroomStem(context,blockPos,75);
        return true;
    }
}