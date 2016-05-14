package com.sandvoxel.generitech.common.enumtypes;

import net.minecraft.util.IStringSerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnumOres implements IStringSerializable{

    // Vanilla stuff
    IRON(0, "iron", EnumOreType.NUGGET, EnumOreType.DUST, EnumOreType.VANILLA),
    GOLD(1, "gold", EnumOreType.DUST, EnumOreType.VANILLA),
    //DIAMOND(2, "diamond", EnumOreType.NUGGET, EnumOreType.VANILLA),

    // Our stuff
    COPPER(2, "copper", EnumOreType.ORE, EnumOreType.DUST, EnumOreType.NUGGET, EnumOreType.INGOT),
    TIN(3, "tin", EnumOreType.ORE, EnumOreType.DUST, EnumOreType.NUGGET, EnumOreType.INGOT),
    LEAD(4, "lead", EnumOreType.ORE, EnumOreType.DUST, EnumOreType.NUGGET, EnumOreType.INGOT);

    private static final EnumOres[] META_LOOKUP = new EnumOres[values().length];

    static {
        for (EnumOres ore : values()) {
            META_LOOKUP[ore.getID()] = ore;
        }
    }

    private int ID;
    private String name;
    private final EnumOreType[] enumOresTypeList;

    private EnumOres(int ID, String name, EnumOreType... enumOreTypes){
        this.ID = ID;
        this.name = name;
        this.enumOresTypeList = enumOreTypes;
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

    public static List<EnumOres> byType(EnumOreType type) {
        List<EnumOres> result = new ArrayList<>();

        for (EnumOres ore : values()) {
            if (ore.isTypeSet(type)) {
                result.add(ore);
            }
        }

        return result;
    }

    public boolean isTypeSet(EnumOreType enumOreType) {
        return Arrays.asList(enumOresTypeList).contains(enumOreType);
    }

    public static EnumOres byID(int id) {
        if (id < 0 || id >= META_LOOKUP.length) {
            id = 0;
        }

        return META_LOOKUP[id];
    }
}
