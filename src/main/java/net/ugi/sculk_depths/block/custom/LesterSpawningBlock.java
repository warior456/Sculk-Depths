package net.ugi.sculk_depths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.ugi.sculk_depths.entity.ModEntities;
import net.ugi.sculk_depths.entity.custom.LesterEntity;
import net.ugi.sculk_depths.tags.ModTags;

public class LesterSpawningBlock extends Block {
    public static final MapCodec<LesterSpawningBlock> CODEC = simpleCodec(LesterSpawningBlock::new);

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    public LesterSpawningBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) return;
        pos = checkForRoof(level, pos, "Up", 0, 300);
        if (pos == null) return;
        int count = Mth.nextInt(RandomSource.create(), 4, 9);

        for (; count > 0; count--) {
            Entity entity = new LesterEntity(ModEntities.LESTER, (Level) level);

            int XOffset = Mth.nextInt(RandomSource.create(), -5, 5);
            pos = pos.east(XOffset);
            int ZOffset = Mth.nextInt(RandomSource.create(), -5, 5);
            pos = pos.south(ZOffset);
            BlockPos candidate;
            if (!level.getBlockState(pos).is(ModTags.Blocks.LESTER_SPAWN_BLOCKS)) {
                candidate = checkForRoof(level, pos, "Up", 0, 300);
            } else {
                candidate = checkForRoof(level, pos, "Down", 0, 10);
            }

            if (candidate == null) {
                continue;
            }
            pos = candidate;

            entity.setPos(pos.getX() + 0.5, pos.getY() + 0.99, pos.getZ() + 0.5);
            level.addFreshEntity(entity);
        }
    }

    BlockPos checkForRoof(LevelAccessor level, BlockPos pos, String opPlayers, int minValue, int max) {
        int i = 0;
        BlockPos pos2 = pos;
        if ("Up".equals(opPlayers)) {
            do {
                if (i > max) return null;
                pos2 = pos2.above(1);
                i++;
            } while (!level.getBlockState(pos2).is(ModTags.Blocks.LESTER_SPAWN_BLOCKS));
        }

        if ("Down".equals(opPlayers)) {
            do {
                if (i > max) return null;
                pos2 = pos2.below(1);
                i++;
            } while (level.getBlockState(pos2).is(ModTags.Blocks.LESTER_SPAWN_BLOCKS));
        }
        if (i < minValue) return null;
        if (pos2 == pos) return null;
        return pos2;
    }
}
