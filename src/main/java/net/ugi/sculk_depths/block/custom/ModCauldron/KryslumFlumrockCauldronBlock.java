package net.ugi.sculk_depths.block.custom.ModCauldron;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.Containers;
import net.minecraft.core.NonNullList;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.gameevent.GameEvent;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.state.property.ModProperties;

import java.util.Map;

import static net.ugi.sculk_depths.state.property.ModProperties.CRUX_LEVEL;
import static net.ugi.sculk_depths.state.property.ModProperties.QUAZARITH_LEVEL;


public class KryslumFlumrockCauldronBlock extends AbstractCauldronBlock {
    @Override
    protected MapCodec<? extends AbstractCauldronBlock> codec() {
        return null;
    }

    public static final IntegerProperty LEVEL = ModProperties.KRYSLUM_LEVEL;

    public static final IntegerProperty QUAZARITH = ModProperties.QUAZARITH_LEVEL;
    public static final IntegerProperty CRUX = ModProperties.CRUX_LEVEL;


    private final Map<Item, CauldronInteraction> interactions;

    public KryslumFlumrockCauldronBlock(BlockBehaviour.Properties properties, Map<Item, CauldronInteraction> interactions) {
        super(properties, ModCauldronBehavior.KRYSLUM_FLUMROCK_CAULDRON_BEHAVIOR);
        this.interactions = interactions;
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 1));
    }

    public KryslumFlumrockCauldronBlock(BlockBehaviour.Properties properties, CauldronInteraction.InteractionMap interactions) {
        this(properties, interactions.map());
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(ModBlocks.FLUMROCK_CAULDRON);
    }

    private static final VoxelShape RAYCAST_SHAPE = AbstractCauldronBlock.box(1.5, 2.0, 1.5, 14.5, 16.0, 14.5);
    protected static final VoxelShape OUTLINE_SHAPE = Shapes.join(Shapes.block(),
            Shapes.or(
                    AbstractCauldronBlock.box(0.0, 0.0, 0.0, 16.0, 1.0, 2.0),
                    AbstractCauldronBlock.box(0.0, 0.0, 14.0, 16.0, 1.0, 16.0),
                    AbstractCauldronBlock.box(0.0, 0.0, 0.0, 2.0, 1.0, 16.0),
                    AbstractCauldronBlock.box(14.0, 0.0, 0.0, 16.0, 1.0, 16.0),
                    AbstractCauldronBlock.box(4.0, 0.0, 2.0, 12.0, 1.0, 14.0),
                    AbstractCauldronBlock.box(2.0, 0.0, 4.0, 14.0, 1.0, 12.0),
                    AbstractCauldronBlock.box(0.0, 0.0, 0.0, 16.0, 2.0, 1.0),
                    AbstractCauldronBlock.box(0.0, 0.0, 15.0, 16.0, 2.0, 16.0),
                    AbstractCauldronBlock.box(0.0, 0.0, 0.0, 1.0, 2.0, 16.0),
                    AbstractCauldronBlock.box(15.0, 0.0, 0.0, 16.0, 2.0, 16.0),
                    AbstractCauldronBlock.box(1.0, 2.0, 1.0, 15.0, 13.0, 15.0),
                    AbstractCauldronBlock.box(0.0, 13.0, 0.0, 16.0, 15.0, 1.0),
                    AbstractCauldronBlock.box(0.0, 13.0, 15.0, 16.0, 15.0, 16.0),
                    AbstractCauldronBlock.box(0.0, 13.0, 0.0, 1.0, 15.0, 16.0),
                    AbstractCauldronBlock.box(15.0, 13.0, 0.0, 16.0, 15.0, 16.0),
                    AbstractCauldronBlock.box(1.0, 15.0, 1.0, 15.0, 16.0, 15.0),
                    RAYCAST_SHAPE), BooleanOp.ONLY_FIRST);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return RAYCAST_SHAPE;
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.getValue(LEVEL) == 12;
    }



    @Override
    protected double getContentHeight(BlockState state) {
        return (6.0 + (double)state.getValue(LEVEL) * 3.0) / 16.0;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity.isOnFire() && this.isEntityInsideContent(state, pos, entity)) {

        }
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return state.getValue(LEVEL);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL).add(QUAZARITH).add(CRUX);

    }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack item = player.getMainHandItem();
        CauldronInteraction cauldronBehavior = this.interactions.get(item.getItem());
        if(item.getItem() == ModItems.KRYSLUM_BUCKET
                || item.getItem() == Items.BUCKET
                || item.getItem() == ModItems.QUAZARITH_PIECES
                || item.getItem() == ModItems.CRUX
                || item.getItem() == ModItems.QUAZARITH_INGOT
        ) {
            return cauldronBehavior.interact(state, level, pos, player, hand, item);
        }

        if((item.getItem() == Items.NETHERITE_HELMET
                && state.getValue(LEVEL) > SculkDepths.CONFIG.quazarith_helmet_kryslum_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.quazarith_helmet_crux_cost
                && state.getValue(QUAZARITH) >= SculkDepths.CONFIG.quazarith_helmet_quazarith_pieces_cost)
                || (item.getItem() == Items.NETHERITE_HELMET//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.quazarith_helmet_kryslum_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.quazarith_helmet_crux_cost
                && state.getValue(QUAZARITH) == SculkDepths.CONFIG.quazarith_helmet_quazarith_pieces_cost)){
            level.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1.0f);
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_HELMET);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.quazarith_helmet_quazarith_pieces_cost,
                    SculkDepths.CONFIG.quazarith_helmet_crux_cost,
                    SculkDepths.CONFIG.quazarith_helmet_kryslum_cost);
            return UpgradeItem(outputItem, player);
        }
        if((item.getItem() == Items.NETHERITE_CHESTPLATE
                && state.getValue(LEVEL) > SculkDepths.CONFIG.quazarith_chestplate_kryslum_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.quazarith_chestplate_crux_cost
                && state.getValue(QUAZARITH) >= SculkDepths.CONFIG.quazarith_chestplate_quazarith_pieces_cost)
                || (item.getItem() == Items.NETHERITE_CHESTPLATE//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.quazarith_chestplate_kryslum_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.quazarith_chestplate_crux_cost
                && state.getValue(QUAZARITH) == SculkDepths.CONFIG.quazarith_chestplate_quazarith_pieces_cost)){
            level.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1.0f);
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_CHESTPLATE);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.quazarith_chestplate_quazarith_pieces_cost,
                    SculkDepths.CONFIG.quazarith_chestplate_crux_cost,
                    SculkDepths.CONFIG.quazarith_chestplate_kryslum_cost);
            return UpgradeItem(outputItem, player);
        }
        if((item.getItem() == Items.NETHERITE_LEGGINGS
                && state.getValue(LEVEL) > SculkDepths.CONFIG.quazarith_leggings_kryslum_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.quazarith_leggings_crux_cost
                && state.getValue(QUAZARITH) >= SculkDepths.CONFIG.quazarith_leggings_quazarith_pieces_cost)
                || (item.getItem() == Items.NETHERITE_LEGGINGS//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.quazarith_leggings_kryslum_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.quazarith_leggings_crux_cost
                && state.getValue(QUAZARITH) == SculkDepths.CONFIG.quazarith_leggings_quazarith_pieces_cost)){
            level.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1.0f);
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_LEGGINGS);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.quazarith_leggings_quazarith_pieces_cost,
                    SculkDepths.CONFIG.quazarith_leggings_crux_cost,
                    SculkDepths.CONFIG.quazarith_leggings_kryslum_cost);
            return UpgradeItem(outputItem, player);
        }
        if((item.getItem() == Items.NETHERITE_BOOTS
                && state.getValue(LEVEL) > SculkDepths.CONFIG.quazarith_boots_kryslum_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.quazarith_boots_crux_cost
                && state.getValue(QUAZARITH) >= SculkDepths.CONFIG.quazarith_boots_quazarith_pieces_cost)
                || (item.getItem() == Items.NETHERITE_BOOTS//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.quazarith_boots_kryslum_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.quazarith_boots_crux_cost
                && state.getValue(QUAZARITH) == SculkDepths.CONFIG.quazarith_boots_quazarith_pieces_cost)){
            level.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1.0f);
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_BOOTS);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.quazarith_boots_quazarith_pieces_cost,
                    SculkDepths.CONFIG.quazarith_boots_crux_cost,
                    SculkDepths.CONFIG.quazarith_boots_kryslum_cost);
            return UpgradeItem(outputItem, player);
        }
        if((item.getItem() == Items.NETHERITE_SHOVEL
                && state.getValue(LEVEL) > SculkDepths.CONFIG.quazarith_shovel_kryslum_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.quazarith_shovel_crux_cost
                && state.getValue(QUAZARITH) >= SculkDepths.CONFIG.quazarith_shovel_quazarith_pieces_cost)
                || (item.getItem() == Items.NETHERITE_SHOVEL//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.quazarith_shovel_kryslum_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.quazarith_shovel_crux_cost
                && state.getValue(QUAZARITH) == SculkDepths.CONFIG.quazarith_shovel_quazarith_pieces_cost)){
            level.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1.0f);
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_SHOVEL);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.quazarith_shovel_quazarith_pieces_cost,
                    SculkDepths.CONFIG.quazarith_shovel_crux_cost,
                    SculkDepths.CONFIG.quazarith_shovel_kryslum_cost);
            return UpgradeItem(outputItem, player);
        }
        if((item.getItem() == Items.NETHERITE_PICKAXE
                && state.getValue(LEVEL) > SculkDepths.CONFIG.quazarith_pickaxe_kryslum_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.quazarith_pickaxe_crux_cost
                && state.getValue(QUAZARITH) >= SculkDepths.CONFIG.quazarith_pickaxe_quazarith_pieces_cost)
                || (item.getItem() == Items.NETHERITE_PICKAXE//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.quazarith_pickaxe_kryslum_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.quazarith_pickaxe_crux_cost
                && state.getValue(QUAZARITH) == SculkDepths.CONFIG.quazarith_pickaxe_quazarith_pieces_cost)){
            level.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1.0f);
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_PICKAXE);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.quazarith_pickaxe_quazarith_pieces_cost,
                    SculkDepths.CONFIG.quazarith_pickaxe_crux_cost,
                    SculkDepths.CONFIG.quazarith_pickaxe_kryslum_cost);
            return UpgradeItem(outputItem, player);
        }
        if((item.getItem() == Items.NETHERITE_AXE
                && state.getValue(LEVEL) > SculkDepths.CONFIG.quazarith_axe_kryslum_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.quazarith_axe_crux_cost
                && state.getValue(QUAZARITH) >= SculkDepths.CONFIG.quazarith_axe_quazarith_pieces_cost)
                || (item.getItem() == Items.NETHERITE_AXE//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.quazarith_axe_kryslum_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.quazarith_axe_crux_cost
                && state.getValue(QUAZARITH) == SculkDepths.CONFIG.quazarith_axe_quazarith_pieces_cost)){
            level.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1.0f);
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_AXE);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.quazarith_axe_quazarith_pieces_cost,
                    SculkDepths.CONFIG.quazarith_axe_crux_cost,
                    SculkDepths.CONFIG.quazarith_axe_kryslum_cost);
            return UpgradeItem(outputItem, player);
        }
        if((item.getItem() == Items.NETHERITE_HOE
                && state.getValue(LEVEL) > SculkDepths.CONFIG.quazarith_hoe_kryslum_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.quazarith_hoe_crux_cost
                && state.getValue(QUAZARITH) >= SculkDepths.CONFIG.quazarith_hoe_quazarith_pieces_cost)
                || (item.getItem() == Items.NETHERITE_HOE//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.quazarith_hoe_kryslum_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.quazarith_hoe_crux_cost
                && state.getValue(QUAZARITH) == SculkDepths.CONFIG.quazarith_hoe_quazarith_pieces_cost)){
            level.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1.0f);
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_HOE);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.quazarith_hoe_quazarith_pieces_cost,
                    SculkDepths.CONFIG.quazarith_hoe_crux_cost,
                    SculkDepths.CONFIG.quazarith_hoe_kryslum_cost);
            return UpgradeItem(outputItem, player);
        }
        if((item.getItem() == Items.NETHERITE_SWORD
                && state.getValue(LEVEL) > SculkDepths.CONFIG.quazarith_sword_kryslum_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.quazarith_sword_crux_cost
                && state.getValue(QUAZARITH) >= SculkDepths.CONFIG.quazarith_sword_quazarith_pieces_cost)
                || (item.getItem() == Items.NETHERITE_SWORD//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.quazarith_sword_kryslum_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.quazarith_sword_crux_cost
                && state.getValue(QUAZARITH) == SculkDepths.CONFIG.quazarith_sword_quazarith_pieces_cost)){
            level.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1.0f);
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_SWORD);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.quazarith_sword_quazarith_pieces_cost,
                    SculkDepths.CONFIG.quazarith_sword_crux_cost,
                    SculkDepths.CONFIG.quazarith_sword_kryslum_cost);
            return UpgradeItem(outputItem, player);
        }
        if((item.getItem() == Items.AIR
                && state.getValue(LEVEL) > SculkDepths.CONFIG.quazarith_ingot_kryslum_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.quazarith_ingot_crux_cost
                && state.getValue(QUAZARITH) >= SculkDepths.CONFIG.quazarith_ingot_quazarith_pieces_cost)
                || (item.getItem() == Items.AIR//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.quazarith_ingot_kryslum_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.quazarith_ingot_crux_cost
                && state.getValue(QUAZARITH) == SculkDepths.CONFIG.quazarith_ingot_quazarith_pieces_cost)){
            level.playSound(null, pos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 0.2f, 1.0f);
            ItemStack outputItem = new ItemStack(ModItems.QUAZARITH_INGOT);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.quazarith_ingot_quazarith_pieces_cost,
                    SculkDepths.CONFIG.quazarith_ingot_crux_cost,
                    SculkDepths.CONFIG.quazarith_ingot_kryslum_cost);
            return UpgradeItem(outputItem, player);
        }

        return ItemInteractionResult.FAIL;

    }

    public ItemInteractionResult UpgradeItem(ItemStack outputItem, Player player){

        DataComponentMap componentMap = player.getMainHandItem().getComponents();
        if (componentMap != null) {
            outputItem.applyComponents(componentMap);
        }
        player.setItemInHand(InteractionHand.MAIN_HAND, outputItem);
        return ItemInteractionResult.sidedSuccess(player.level().isClientSide);
    }

    public void RemoveUsedResources(BlockState state, Level level, BlockPos pos, int quazarith_pieces, int crux, int kryslum){
        int i = state.getValue(QUAZARITH_LEVEL) - quazarith_pieces;
        int j = state.getValue(CRUX_LEVEL) - crux;
        int k = state.getValue(LEVEL) - kryslum;

        if(k == 0){
            BlockState newBlockState = state.setValue(QUAZARITH_LEVEL, i).setValue(CRUX_LEVEL, j);
            level.setBlockAndUpdate(pos, newBlockState);
            level.setBlockAndUpdate(pos, ModBlocks.FLUMROCK_CAULDRON.defaultBlockState());
            return;
        }

        BlockState newBlockState = state.setValue(QUAZARITH_LEVEL, i).setValue(CRUX_LEVEL, j).setValue(LEVEL, k);
        level.setBlockAndUpdate(pos, newBlockState);


    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock() && !moved) {
            NonNullList<ItemStack> stacks = NonNullList.withSize(2, ItemStack.EMPTY);
            stacks.set( 0 , new ItemStack(ModItems.QUAZARITH_PIECES, state.getValue(QUAZARITH)));
            stacks.set(1 , new ItemStack(ModItems.CRUX, state.getValue(CRUX)));
            Containers.dropContents(level, pos, stacks);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));

        }
        super.onRemove(state, level, pos, newState, moved);
    }
}
