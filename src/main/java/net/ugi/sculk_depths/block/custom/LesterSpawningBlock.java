package net.ugi.sculk_depths.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.entity.custom.LesterEntity;

public class LesterSpawningBlock extends Block {
    public LesterSpawningBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if(world.isClient())return;
        Entity entity = new LesterEntity(ModEntities.LESTER, (World) world);
        System.out.println("yay");
        int y = raycastup(pos, (World)world);
        entity.setPosition(pos.getX(), pos.up(y).getY(), pos.getZ());
        world.spawnEntity(entity);
    }

    public int raycastup (BlockPos pos, World world){
        Block block = world.getBlockState(pos.up()).getBlock();
        int i= 0;
        System.out.println(i);
        while (block == Blocks.AIR && !(i > 20)){
            System.out.println(i);
                i++;
                block = world.getBlockState(pos.up(i)).getBlock();
        }
        return i;
    }
}
