package com.sandvoxel.generitech.common.enumtypes;

import net.minecraft.util.IStringSerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnumOres implements IStringSerializable{

    // Vanilla stuff
    IRON(0, "Iron", EnumOreType.NUGGET, EnumOreType.DUST, EnumOreType.VANILLA),
    GOLD(1, "Gold", EnumOreType.DUST, EnumOreType.VANILLA),
    //DIAMOND(2, "diamond", EnumOreType.NUGGET, EnumOreType.VANILLA),

    // Our stuff
    COPPER(2, "Copper", EnumOreType.ORE, EnumOreType.DUST, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.BLOCK),
    TIN(3, "Tin", EnumOreType.ORE, EnumOreType.DUST, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.BLOCK),
    LEAD(4, "Lead", EnumOreType.ORE, EnumOreType.DUST, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.BLOCK);

    private static final EnumOres[] META_LOOKUP = new EnumOres[values().length];

    static {
        for (EnumOres ore : values()) {
            META_LOOKUP[ore.getMeta()] = ore;
        }
    }

    private int meta;
    private String name;
    private final EnumOreType[] enumOresTypeList;

    private EnumOres(int meta, String name, EnumOreType... enumOreTypes){
        this.meta = meta;
        this.name = name;
        this.enumOresTypeList = enumOreTypes;
    }

    public static EnumOres byMeta(int id) {
        if (id < 0 || id >= META_LOOKUP.length) {
            id = 0;
        }

        return META_LOOKUP[id];
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

    public int getMeta() {
        return this.meta;
    }

    public String getUnlocalizedName() {
        return this.name.toLowerCase();
    }

    public String getName() {
        return this.name.toLowerCase();
    }

    public String getOreName() {
        return this.name;
    }

    public String toString() {
        return getName();
    }

    public boolean isTypeSet(EnumOreType enumOreType) {
        return Arrays.asList(enumOresTypeList).contains(enumOreType);
    }
}
