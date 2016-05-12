package com.sandvoxel.generitech.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBase extends TileEntity {
    protected String customName;

    public String getCustomName()
    {
        return this.customName;
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    public boolean hasCustomName()
    {
        return customName != null && customName.length() > 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        if (nbtTagCompound.hasKey("CustomName"))
        {
            this.customName = nbtTagCompound.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        if (this.hasCustomName())
        {
            nbtTagCompound.setString("CustomName", customName);
        }
    }

}
