package com.sandvoxel.generitech.container.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBase extends Slot{
    private boolean isEnabled = true;

    public SlotBase(IInventory inventory, int idx, int x, int y) {
        super(inventory, idx, x, y);
    }

    @Override
    public boolean canBeHovered() {
        return isEnabled;
    }

    @Override
    public ItemStack getStack() {
        if (this.inventory.getSizeInventory() <= getSlotIndex())
            return null;

        return super.getStack();
    }

    @Override
    public void putStack(ItemStack stack) {
        if (!isEnabled)
            return;

        super.putStack(stack);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (!isEnabled)
            return false;

        return super.isItemValid(stack);
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        if (!isEnabled)
            return false;

        return super.canTakeStack(playerIn);
    }

    public void clearStack() {
        super.putStack(null);
    }
}
