package net.ugi.sculk_depths.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.entity.custom.LesterEntity;
import net.ugi.sculk_depths.tags.ModTags;


public class LesterSpawningBlock extends Block {
    public LesterSpawningBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBroken(LevelAccessor world, BlockPos pos, BlockState state) {
        if(world.isClientSide())return;
        pos = checkForRoof(world,pos, "Up",0,300);
        if(pos == null)return;
        int count = Mth.getInt(RandomSource.create(), 4, 9);

        for(;count > 0; count--){
            Entity entity = new LesterEntity(ModEntities.LESTER, (Level) world);

            int XOffset = Mth.getInt(RandomSource.create(), -5, 5);
            pos = pos.east(XOffset);
            int ZOffset = Mth.getInt(RandomSource.create(), -5, 5);
            pos = pos.south(ZOffset);
            BlockPos candidate;
            if(!world.getBlockState(pos).is(ModTags.Blocks.LESTER_SPAWN_BLOCKS)){
                candidate = checkForRoof(world,pos, "Up",0,300);
            }
            else candidate = checkForRoof(world,pos, "Down",0,10);


            if(candidate == null){ continue;}
            pos = candidate;


            entity.setPosition(pos.getX()+ 0.5,pos.getY()+0.99,pos.getZ() + 0.5);
            world.spawnEntity(entity);


        }

    }
    BlockPos checkForRoof(LevelAccessor world, BlockPos pos, String opPlayers, int minValue, int max){
        int i = 0;
        BlockPos pos2 = pos;
        if(opPlayers == "Up"){
            do{
                if(i > max) return null;
                pos2 = pos2.above(1);
                i++;
            }while(!world.getBlockState(pos2).is(ModTags.Blocks.LESTER_SPAWN_BLOCKS));
        }

        if(opPlayers == "Down"){
            do{
                if(i > max) return null;
                pos2 = pos2.below(1);
                i++;
            }while(world.getBlockState(pos2).is(ModTags.Blocks.LESTER_SPAWN_BLOCKS));
        }
        if (i < minValue) return null;
        if (pos2 == pos) return null;
        return pos2;

    }

}
