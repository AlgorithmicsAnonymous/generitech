package com.sandvoxel.generitech.tileentities;

import com.sandvoxel.generitech.api.registries.PulverizerRegistry;
import net.minecraft.item.ItemStack;

public class TileEntityPulverizer extends TileEntityMachineBase {

    public TileEntityPulverizer()
    {
        super(3);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        if(i == 0  && PulverizerRegistry.containsInput(itemStack))
            return true;

        return false;
    }

}
