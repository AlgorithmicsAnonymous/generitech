package com.sandvoxel.generitech.tileentities;

public abstract class TileEntityMachineBase extends TileEntityInventoryBase {
    public TileEntityMachineBase(int invSize)
    {
        super(invSize, 64);
    }

    @Override
    public boolean canBeRotated() {
        return true;
    }

}
