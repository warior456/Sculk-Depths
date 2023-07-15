package net.ugi.sculk_depths.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.ugi.sculk_depths.block.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class CauldronBlockEntity extends BlockEntity implements SidedInventory {

    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);


    public CauldronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLUMROCK_CAULDRON_BLOCK_ENTITY , pos, state);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] result = new int[this.inventory.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }

        return result;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            ItemStack stack = getStack(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(this.inventory , slot, amount);
        if (!result.isEmpty()) {
            markDirty();
        }

        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }
}
