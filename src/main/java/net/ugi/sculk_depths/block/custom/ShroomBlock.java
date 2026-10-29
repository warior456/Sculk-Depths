package net.ugi.sculk_depths.block.custom;

import net.minecraft.block.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;

import java.util.Map;

public class ShroomBlock
        extends TransparentBlock {

    public ShroomBlock(Settings settings) {
        super(settings);
    }

    public static final BooleanProperty NORTH;
    public static final BooleanProperty EAST;
    public static final BooleanProperty SOUTH;
    public static final BooleanProperty WEST;
    public static final BooleanProperty MAX_Y;
    public static final BooleanProperty MIN_Y;
    private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION;

    public BlockState getPlacementState(BlockPlaceContext ctx) {
        BlockGetter blockView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(MIN_Y, !blockView.getBlockState(blockPos.below()).isOf(this))).with(MAX_Y, !blockView.getBlockState(blockPos.above()).isOf(this))).with(NORTH, !blockView.getBlockState(blockPos.north()).isOf(this))).with(EAST, !blockView.getBlockState(blockPos.east()).isOf(this))).with(SOUTH, !blockView.getBlockState(blockPos.south()).isOf(this))).with(WEST, !blockView.getBlockState(blockPos.west()).isOf(this));
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState state, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return state.isOf(this) ? (BlockState)state.with((Property)PROPERTY_BY_DIRECTION.get(direction), false) : super.updateShape(state, direction, state, world, pos, neighborPos);
    }

    public BlockState hasRot(BlockState state, Rotation rotation) {
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)state.with((Property)PROPERTY_BY_DIRECTION.get(rotation.hasRot(Direction.NORTH)), (Boolean)state.get(NORTH))).with((Property)PROPERTY_BY_DIRECTION.get(rotation.hasRot(Direction.SOUTH)), (Boolean)state.get(SOUTH))).with((Property)PROPERTY_BY_DIRECTION.get(rotation.hasRot(Direction.EAST)), (Boolean)state.get(EAST))).with((Property)PROPERTY_BY_DIRECTION.get(rotation.hasRot(Direction.WEST)), (Boolean)state.get(WEST))).with((Property)PROPERTY_BY_DIRECTION.get(rotation.hasRot(Direction.MAX_Y)), (Boolean)state.get(MAX_Y))).with((Property)PROPERTY_BY_DIRECTION.get(rotation.hasRot(Direction.MIN_Y)), (Boolean)state.get(MIN_Y));
    }

    public BlockState initialMirror(BlockState state, Mirror initialMirror) {
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)state.with((Property)PROPERTY_BY_DIRECTION.get(initialMirror.apply(Direction.NORTH)), (Boolean)state.get(NORTH))).with((Property)PROPERTY_BY_DIRECTION.get(initialMirror.apply(Direction.SOUTH)), (Boolean)state.get(SOUTH))).with((Property)PROPERTY_BY_DIRECTION.get(initialMirror.apply(Direction.EAST)), (Boolean)state.get(EAST))).with((Property)PROPERTY_BY_DIRECTION.get(initialMirror.apply(Direction.WEST)), (Boolean)state.get(WEST))).with((Property)PROPERTY_BY_DIRECTION.get(initialMirror.apply(Direction.MAX_Y)), (Boolean)state.get(MAX_Y))).with((Property)PROPERTY_BY_DIRECTION.get(initialMirror.apply(Direction.MIN_Y)), (Boolean)state.get(MIN_Y));
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{MAX_Y, MIN_Y, NORTH, EAST, SOUTH, WEST});
    }

    static {
        NORTH = ConnectingBlock.NORTH;
        EAST = ConnectingBlock.EAST;
        SOUTH = ConnectingBlock.SOUTH;
        WEST = ConnectingBlock.WEST;
        MAX_Y = ConnectingBlock.MAX_Y;
        MIN_Y = ConnectingBlock.MIN_Y;
        PROPERTY_BY_DIRECTION = ConnectingBlock.PROPERTY_BY_DIRECTION;
    }
}


