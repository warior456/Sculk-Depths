package net.ugi.sculk_depths.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.entity.custom.LesterEntity;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.Objects;

public class LesterSpawningBlock extends Block {
    public LesterSpawningBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if(world.isClient())return;
        pos = new BlockPos(pos.getX(),(int)Math.round(world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 15.0d, false).getPos().getY()),pos.getZ());
        int count = MathHelper.nextInt(Random.create(), 4, 9);

        for(;count > 0; count--){
            BlockPos pos2 = pos;
            Entity entity = new LesterEntity(ModEntities.LESTER, (World) world);

            int XOffset = MathHelper.nextInt(Random.create(), -5, 5);
            pos2 = pos2.east(XOffset);
            int ZOffset = MathHelper.nextInt(Random.create(), -5, 5);
            pos2 = pos2.south(ZOffset);

            pos2 = checkForRoof(world,pos2, 3,300);
            if(pos2 == null){ continue;}


            entity.setPosition(pos2.getX()+ 0.5,pos2.getY(),pos2.getZ() + 0.5);
            world.spawnEntity(entity);


        }

    }
    BlockPos checkForRoof(WorldAccess world, BlockPos pos, int min, int max){
        int i = 0;
        do{
            pos = pos.up(1);
            i++;
        }while(world.getBlockState(pos.up(1)).isIn(ModTags.Blocks.LESTER_SPAWN_AIR_BLOCKS));
        if (i < min){return null;}
        return pos;
    }

}
