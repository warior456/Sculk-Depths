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
import net.ugi.sculk_depths.block.ModBlocks;

public class ConfigurableSpikes
        extends Feature<DefaultFeatureConfig> {

    public ConfigurableSpikes(Codec<DefaultFeatureConfig> configCodec) {
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

        if (context.getWorld().getBlockState(blockPos.down()).isOf(ModBlocks.PETRIFIED_VALTROX_LOG)) return false;
        if (context.getWorld().getBlockState(blockPos.down()).isOf(Blocks.AIR)) return false;

        int topRange = 20; //xz coordinaterange
        int yTop = random.nextInt(20) + 10; //height multiplier (size is bound atm) //original 4 + 7
        int xTopOffset = random.nextBetween(-topRange, topRange);
        int zTopOffset = random.nextBetween(-topRange, topRange);
        Vec3d relativeVector = new Vec3d(xTopOffset, yTop, zTopOffset);//vector in the shape of the center line of blocks

        final int totalLength = (int) Math.sqrt( Math.pow(yTop,2) + Math.sqrt( Math.pow(xTopOffset,2) + Math.pow(zTopOffset,2))); //pythagoras length
        int length = totalLength;

        int radiusSetting = totalLength / 10 + random.nextInt(2);//radius first step of calculation


        Vec3d normalized3dVector = relativeVector.normalize(); //used for moving setblock one step at a time
        double x = blockPos.getX();
        double y = blockPos.getY();
        double z = blockPos.getZ();

        while (length>0){
            int radius = MathHelper.ceil(radiusSetting * (length+0.5f)/totalLength);//todo second step of radius calculation based on local location relative to length

            BlockPos currentCenterBlockPos = new BlockPos((int)Math.round(x), (int)Math.round(y), (int)Math.round(z)); //current blockpos
            //sets a block in the center of the spike/column
            this.setBlockState(structureWorldAccess, currentCenterBlockPos, ModBlocks.PETRIFIED_VALTROX_LOG.getDefaultState());
            //non rounded tracking of coordinates
            x = x + normalized3dVector.getX();
            y = y + normalized3dVector.getY();
            z = z + normalized3dVector.getZ();

            int radiusSquared = radius * radius;
            Iterable<BlockPos> blockPosIterable = BlockPos.iterateOutwards(new BlockPos( (int)Math.round(x), (int)Math.round(y), (int)Math.round(z)), radius, radius, radius);
            blockPosIterable.forEach(blockPos1 -> {
                int distanceSquared = (int) blockPos1.getSquaredDistance(currentCenterBlockPos);
                if (distanceSquared <= radiusSquared) {//ball instead of rectangle
                    this.setBlockState(structureWorldAccess, blockPos1, ModBlocks.PETRIFIED_VALTROX_LOG.getDefaultState());
                }
            });
            length--;
        }
        return true;
    }


    /*@Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        int l;
        int yOfsset;
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        while (structureWorldAccess.isAir(blockPos) && blockPos.getY() > structureWorldAccess.getBottomY() + 2) {//downwards raycast
            blockPos = blockPos.down();
        }
        blockPos = blockPos.up(random.nextInt(4)); //height ofsett with wierd support pillar (missing blocks) //original 4
        int i = random.nextInt(8) + 8; //height multiplier (size is bound atm) //original 4 + 7
        int j = i / 4 + random.nextInt(1); //pancake multiplier (higher = flatter) //original 2
        if (j > 1 && random.nextInt(60) == 0) { //chance to start higher? //original 60
            blockPos = blockPos.up(10 + random.nextInt(30));
        }
        for (yOfsset = 0; yOfsset < i; ++yOfsset) {
            float f = (1.0f - (float)yOfsset / i) * j;
            l = MathHelper.ceil(f);
            for (int xOfsset = -l; xOfsset <= l; ++xOfsset) {
                float g = (float)MathHelper.abs(xOfsset) - 0.25f;

                for (int zOfsset = -l; zOfsset <= l; ++zOfsset) {
                    float h = (float)MathHelper.abs(zOfsset) - 0.25f;

                    if ((xOfsset != 0 || zOfsset != 0) && g * g + h * h > f * f || (xOfsset == -l || xOfsset == l || zOfsset == -l || zOfsset == l) && random.nextFloat() > 0.75f) continue;

                    BlockState blockState = structureWorldAccess.getBlockState(blockPos.add(xOfsset, yOfsset, zOfsset));

                    if (blockState.isAir() || blockState.isOf(ModBlocks.AMALGAMITE)) { //check if the block is allowed to be replaced
                        this.setBlockState(structureWorldAccess, blockPos.add(xOfsset, yOfsset, zOfsset), ModBlocks.PETRIFIED_VALTROX_LOG.getDefaultState());
                    }
                    //   yoffset is 0 or l? smaller than 1 OR nOt(set blockstate = blockstatefrompos)=air
                    if (yOfsset == 0 || l <= 1 || !(structureWorldAccess.getBlockState(blockPos.add(xOfsset, -yOfsset, zOfsset))).isAir() && !blockState.isOf(ModBlocks.AMALGAMITE)) continue;
                    this.setBlockState(structureWorldAccess, blockPos.add(xOfsset, -yOfsset, zOfsset), ModBlocks.COATED_STRIPPED_VALTROX_WOOD.getDefaultState());
                }
            }
        }
        yOfsset = j - 1;
        if (yOfsset < 0) {
            yOfsset = 0;
        } else if (yOfsset > 1) {
            yOfsset = 1;
        }
        for (int o = -yOfsset; o <= yOfsset; ++o) {
            for (l = -yOfsset; l <= yOfsset; ++l) {
                BlockState blockState2;
                BlockPos blockPos2 = blockPos.add(o, -1, l);
                int p = 50;
                if (Math.abs(o) == 1 && Math.abs(l) == 1) {
                    p = random.nextInt(5);
                }
                while (blockPos2.getY() > 50 && ((blockState2 = structureWorldAccess.getBlockState(blockPos2)).isAir() || net.minecraft.world.gen.feature.IceSpikeFeature.isSoil(blockState2) || blockState2.isOf(Blocks.SNOW_BLOCK) || blockState2.isOf(Blocks.ICE) || blockState2.isOf(Blocks.PACKED_ICE))) {
                    this.setBlockState(structureWorldAccess, blockPos2, ModBlocks.CRUX_BLOCK.getDefaultState()); //no idea what triggers this
                    blockPos2 = blockPos2.down();
                    if (--p > 0) continue;
                    blockPos2 = blockPos2.down(random.nextInt(5) + 1);
                    p = random.nextInt(5);
                }
            }
        }
        return true;
    }*/

}