package com.sandvoxel.generitech.common.blocks.container.slot;

import com.sandvoxel.generitech.api.registries.PulverizerRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotPulverizerInput extends SlotBase{
    public SlotPulverizerInput(IInventory inventory, int idx, int x, int y) {
        super(inventory, idx, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return PulverizerRegistry.containsInput(stack);
    }
}
