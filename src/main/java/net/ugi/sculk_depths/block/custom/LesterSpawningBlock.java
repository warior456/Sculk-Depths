package net.ugi.sculk_depths.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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

        entity.setPosition(pos.getX(),pos.getY(),pos.getZ());
        world.spawnEntity(entity);
    }
}
