package net.ugi.sculk_depths.block.custom.ModCauldron;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.state.property.ModProperties;
import net.ugi.sculk_depths.tags.ModTags;
import net.ugi.sculk_depths.util.enums.CrystalType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static net.ugi.sculk_depths.state.property.ModProperties.CRUX_LEVEL;


public class SporeFlumrockCauldronBlock extends AbstractCauldronBlock {
    @Override
    protected MapCodec<? extends AbstractCauldronBlock> codec() {
        return null;
    }

    Item[] crystalItemArray = {ModItems.WHITE_CRYSTAL, ModItems.BLUE_CRYSTAL, ModItems.ORANGE_CRYSTAL, ModItems.LIME_CRYSTAL};
    CrystalType[] crystalStateArray = {CrystalType.WHITE, CrystalType.COLOR_BLUE, CrystalType.ORANGE, CrystalType.COLOR_LIGHT_GREEN};


    List<String> crystalItemNbt = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");

    List<Item> crystalItemList = Arrays.asList(crystalItemArray);
    List<CrystalType> crystalStateList = Arrays.asList(crystalStateArray);


    public static final IntegerProperty LEVEL = ModProperties.SPORE_LEVEL;

    public static final EnumProperty<CrystalType> CRYSTAL = ModProperties.CRYSTAL_TYPE;

    public static final IntegerProperty CRUX = ModProperties.CRUX_LEVEL;


    private final Map<Item, CauldronInteraction> interactions;

    public SporeFlumrockCauldronBlock(BlockBehaviour.Properties properties, Map<Item, CauldronInteraction> interactions) {
        super(properties, ModCauldronBehavior.SPORE_FLUMROCK_CAULDRON_BEHAVIOR);
        this.interactions = interactions;
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 1));
    }

    public SporeFlumrockCauldronBlock(BlockBehaviour.Properties properties, CauldronInteraction.InteractionMap interactions) {
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
        return (6.0 + (double) state.getValue(LEVEL) * 3.0) / 16.0;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return state.getValue(LEVEL);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL).add(CRYSTAL).add(CRUX);

    }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack item = player.getMainHandItem();
        CauldronInteraction cauldronBehavior = this.interactions.get(item.getItem());
        if (item.getItem() == ModItems.PENEBRIUM_SPORE_BUCKET
                || item.getItem() == ModItems.CRUX
                || item.getItem() == Items.BUCKET
        ) {
            return cauldronBehavior.interact(state, level, pos, player, hand, item);
        }
        if (item.is(ModTags.Items.CRYSTALS)) {
            if (!level.isClientSide()) {


                int k = crystalItemList.indexOf(player.getMainHandItem().getItem());
                CrystalType l = crystalStateArray[k];


                player.getMainHandItem().shrink(1);

                if (state.getValue(CRYSTAL) != CrystalType.NONE) {
                    int i = crystalStateList.indexOf(state.getValue(CRYSTAL));
                    Item j = crystalItemArray[i];

                    ItemStack outputItem = new ItemStack(j, 1);
                    boolean gaveItem = player.addItem(outputItem);
                    if (!gaveItem) {
                        player.drop(outputItem, true);
                    }
                }
                level.playSound(null, pos, SoundEvents.MOSS_STEP, SoundSource.BLOCKS, 1.0f, 1.0f);
                BlockState blockState1 = state.setValue(CRYSTAL, l);
                level.setBlockAndUpdate(pos, blockState1);
            }


            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        CustomData nbtComponent = item.get(DataComponents.CUSTOM_DATA);

        if (nbtComponent != null) {
            if (nbtComponent.contains("sculk_depths.crystal")) {
                return ItemInteractionResult.FAIL;
            }
        }

        if ((item.getItem() == ModItems.QUAZARITH_HELMET
                && state.getValue(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_helmet_spore_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_helmet_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)
                || (item.getItem() == ModItems.QUAZARITH_HELMET//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_helmet_spore_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_helmet_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)) {
            level.playSound(null, pos, SoundEvents.MOSS_STEP, SoundSource.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_helmet_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_helmet_spore_cost);
            return ItemInteractionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((item.getItem() == ModItems.QUAZARITH_CHESTPLATE
                && state.getValue(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_chestplate_spore_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_chestplate_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)
                || (item.getItem() == ModItems.QUAZARITH_CHESTPLATE//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_chestplate_spore_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.quazarith_chestplate_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)) {
            level.playSound(null, pos, SoundEvents.MOSS_STEP, SoundSource.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_chestplate_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_chestplate_spore_cost);
            return ItemInteractionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((item.getItem() == ModItems.QUAZARITH_LEGGINGS
                && state.getValue(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_leggings_spore_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_leggings_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)
                || (item.getItem() == ModItems.QUAZARITH_LEGGINGS//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_leggings_spore_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_leggings_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)) {
            level.playSound(null, pos, SoundEvents.MOSS_STEP, SoundSource.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_leggings_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_leggings_spore_cost);
            return ItemInteractionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((item.getItem() == ModItems.QUAZARITH_BOOTS
                && state.getValue(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_boots_spore_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_boots_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)
                || (item.getItem() == ModItems.QUAZARITH_BOOTS//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_boots_spore_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_boots_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)) {
            level.playSound(null, pos, SoundEvents.MOSS_STEP, SoundSource.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_boots_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_boots_spore_cost);
            return ItemInteractionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((item.getItem() == ModItems.QUAZARITH_SHOVEL
                && state.getValue(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_shovel_spore_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_shovel_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)
                || (item.getItem() == ModItems.QUAZARITH_SHOVEL//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_shovel_spore_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_shovel_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)) {
            level.playSound(null, pos, SoundEvents.MOSS_STEP, SoundSource.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_shovel_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_shovel_spore_cost);
            return ItemInteractionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((item.getItem() == ModItems.QUAZARITH_PICKAXE
                && state.getValue(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_pickaxe_spore_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_pickaxe_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)
                || (item.getItem() == ModItems.QUAZARITH_PICKAXE//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_pickaxe_spore_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_pickaxe_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)) {
            level.playSound(null, pos, SoundEvents.MOSS_STEP, SoundSource.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_pickaxe_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_pickaxe_spore_cost);
            return ItemInteractionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((item.getItem() == ModItems.QUAZARITH_AXE
                && state.getValue(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_axe_spore_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_axe_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)
                || (item.getItem() == ModItems.QUAZARITH_AXE//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_axe_spore_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_axe_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)) {
            level.playSound(null, pos, SoundEvents.MOSS_STEP, SoundSource.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_axe_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_axe_spore_cost);
            return ItemInteractionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((item.getItem() == ModItems.QUAZARITH_HOE
                && state.getValue(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_hoe_spore_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_hoe_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)
                || (item.getItem() == ModItems.QUAZARITH_HOE//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_hoe_spore_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_hoe_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)) {
            level.playSound(null, pos, SoundEvents.MOSS_STEP, SoundSource.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_hoe_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_hoe_spore_cost);
            return ItemInteractionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }
        if ((item.getItem() == ModItems.QUAZARITH_SWORD
                && state.getValue(LEVEL) > SculkDepths.CONFIG.crystal_upgrade_quazarith_sword_spore_cost
                && state.getValue(CRUX) >= SculkDepths.CONFIG.crystal_upgrade_quazarith_sword_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)
                || (item.getItem() == ModItems.QUAZARITH_SWORD//========================================
                && state.getValue(LEVEL) == SculkDepths.CONFIG.crystal_upgrade_quazarith_sword_spore_cost
                && state.getValue(CRUX) == SculkDepths.CONFIG.crystal_upgrade_quazarith_sword_crux_cost
                && state.getValue(CRYSTAL) != CrystalType.NONE)) {
            level.playSound(null, pos, SoundEvents.MOSS_STEP, SoundSource.BLOCKS, 1.0f, 1.0f);
            RemoveUsedResources(state, level, pos,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_sword_crux_cost,
                    SculkDepths.CONFIG.crystal_upgrade_quazarith_sword_spore_cost);
            return ItemInteractionResult.FAIL;//CrystalUpgrade.createCrystalUpgrade(itemStack, player, state.get(CRYSTAL));
        }

        return ItemInteractionResult.FAIL;

    }

    public void RemoveUsedResources(BlockState state, Level level, BlockPos pos, int crux, int spore) {
        int j = state.getValue(CRUX_LEVEL) - crux;
        int k = state.getValue(LEVEL) - spore;

        if (k == 0) {
            BlockState newBlockState = state.setValue(CRUX_LEVEL, j).setValue(CRYSTAL, CrystalType.NONE);
            level.setBlockAndUpdate(pos, newBlockState);
            level.setBlockAndUpdate(pos, ModBlocks.FLUMROCK_CAULDRON.defaultBlockState());
            return;
        }
        BlockState newBlockState = state.setValue(CRUX_LEVEL, j).setValue(LEVEL, k).setValue(CRYSTAL, CrystalType.NONE);
        level.setBlockAndUpdate(pos, newBlockState);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock() && !moved) {
            NonNullList<ItemStack> stacks = NonNullList.withSize(2, ItemStack.EMPTY);
            stacks.set(0, new ItemStack(ModItems.CRUX, state.getValue(CRUX)));

            if (state.getValue(CRYSTAL) != CrystalType.NONE) {
                int i = crystalStateList.indexOf(state.getValue(CRYSTAL));
                Item j = crystalItemArray[i];

                stacks.set(1, new ItemStack(j, 1));
            }


            Containers.dropContents(level, pos, stacks);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));

        }
        super.onRemove(state, level, pos, newState, moved);
    }

}
