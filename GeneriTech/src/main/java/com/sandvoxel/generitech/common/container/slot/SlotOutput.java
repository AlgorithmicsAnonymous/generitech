package com.sandvoxel.generitech.common.blocks.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotOutput extends SlotBase {
    public SlotOutput(IInventory inventory, int idx, int x, int y) {
        super(inventory, idx, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }
}