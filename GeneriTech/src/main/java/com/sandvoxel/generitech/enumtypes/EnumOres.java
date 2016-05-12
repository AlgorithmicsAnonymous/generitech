package com.sandvoxel.generitech.enumtypes;

import net.minecraft.util.IStringSerializable;

public enum EnumOres implements IStringSerializable{

    COPPER(0, "copper"),
    TIN(1, "tin"),
    LEAD(2, "lead");

    private static final EnumOres[] META_LOOKUP = new EnumOres[values().length];

    static {
        for (EnumOres ore : values()) {
            META_LOOKUP[ore.getID()] = ore;
        }
    }

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

    public static EnumOres byID(int id) {
        if (id < 0 || id >= META_LOOKUP.length) {
            id = 0;
        }

        return META_LOOKUP[id];
    }
}
