package com.sandvoxel.generitech.tileentities;

import com.sandvoxel.generitech.api.registries.PulverizerRegistry;
import net.minecraft.item.ItemStack;

public class TileEntityPulverizer extends TileEntityMachineBase {

    public TileEntityPulverizer()
    {
        super(3);
    }

    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        if(index == 0  && PulverizerRegistry.containsInput(stack))
            return true;

        return false;
    }


}
