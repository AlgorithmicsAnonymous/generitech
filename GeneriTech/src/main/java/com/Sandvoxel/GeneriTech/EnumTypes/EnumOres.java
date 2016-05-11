package com.Sandvoxel.GeneriTech.EnumTypes;

import net.minecraft.util.IStringSerializable;

public enum EnumOres implements IStringSerializable{

    COPPER,
    TIN,
    LEAD;




    @Override
    public String getName() {
        return name().toLowerCase();
    }

    public int getID() {
        return ordinal();
    }

    @Override
    public String toString() {
        return getName();
    }
}
