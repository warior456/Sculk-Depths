package net.ugi.sculk_depths.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.entity.custom.LesterEntity;
import net.ugi.sculk_depths.tags.ModTags;

public class LesterSpawningBlock extends Block {
    public LesterSpawningBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (world.isClient()) return;
        pos = checkForRoof(world, pos, 1, 0, 300);
        if (pos == null) return;
        int count = MathHelper.nextInt(Random.create(), 4, 9);

        for (; count > 0; count--) {
            Entity entity = new LesterEntity(ModEntities.LESTER, (World) world);

            int xOffset = MathHelper.nextInt(Random.create(), -5, 5);
            pos = pos.east(xOffset);
            int zOffset = MathHelper.nextInt(Random.create(), -5, 5);
            pos = pos.south(zOffset);
            if (!world.getBlockState(pos).isIn(ModTags.Blocks.LESTER_SPAWN_BLOCKS)) {
                pos = checkForRoof(world, pos, 1, 0, 300);
            } else {
                pos = checkForRoof(world, pos, -1, 0, 10);
            }

            if (pos == null) {
                continue;
            }

            entity.setPosition(pos.getX() + 0.5, pos.getY() + 0.99, pos.getZ() + 0.5);
            world.spawnEntity(entity);
        }
    }

    private static BlockPos checkForRoof(WorldAccess world, BlockPos pos, int yOffset, int min, int max) {
        int i = 0;
        BlockPos pos2 = pos;
        do {
            if (i > max) return null;
            pos2 = pos2.up(yOffset);
            i++;
        } while (!world.getBlockState(pos2).isIn(ModTags.Blocks.LESTER_SPAWN_BLOCKS));

        if (i < min) return null;
        if (pos2 == pos) return null;
        return pos2;
    }
}
