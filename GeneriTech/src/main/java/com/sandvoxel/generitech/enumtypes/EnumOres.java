package com.sandvoxel.generitech.enumtypes;

import net.minecraft.util.IStringSerializable;

public enum EnumOres implements IStringSerializable{

    COPPER(0, "copper"),
    TIN(1, "tin"),
    LEAD(2, "lead");

    private int ID;
    private String name;

    private EnumOres(int ID, String name){
        this.ID = ID;
        this.name = name;
    }


    @Override
    public String getName() {
        return name().toLowerCase();
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return getName();
    }
}
