package com.sandvoxel.generitech.container.slot;

import com.sandvoxel.generitech.api.registries.PulverizerRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * Created by Sean on 13/05/2016.
 */
public class SlotPulverizerInput extends SlotBase{
    public SlotPulverizerInput(IInventory inventory, int idx, int x, int y) {
        super(inventory, idx, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return PulverizerRegistry.containsInput(stack);
    }
}
