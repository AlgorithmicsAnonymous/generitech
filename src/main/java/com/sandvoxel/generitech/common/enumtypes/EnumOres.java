package com.sandvoxel.generitech.common.enumtypes;

import net.minecraft.util.IStringSerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnumOres implements IStringSerializable{

    // Vanilla stuff
    IRON("Iron", 0,EnumOreType.NUGGET, EnumOreType.DUST, EnumOreType.VANILLA),
    GOLD("Gold", 1, EnumOreType.DUST, EnumOreType.VANILLA),
    //DIAMOND(2, "diamond", EnumOreType.NUGGET, EnumOreType.VANILLA),

    // Our stuff
    COPPER("Copper", 2, EnumOreType.ORE, EnumOreType.DUST, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.BLOCK),
    TIN("Tin", 3, EnumOreType.ORE, EnumOreType.DUST, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.BLOCK),
    LEAD("Lead", 4, EnumOreType.ORE, EnumOreType.DUST, EnumOreType.NUGGET, EnumOreType.INGOT, EnumOreType.BLOCK);

    private static final EnumOres[] META_LOOKUP = new EnumOres[values().length];

    static {
        for (EnumOres ore : values()) {
            META_LOOKUP[ore.getMeta()] = ore;
        }
    }

    private final String name;
    private final int meta;
    private final EnumOreType[] enumOresTypeList;

    EnumOres(String name, int meta, EnumOreType... oreTypes) {
        this.name = name;
        this.meta = meta;
        this.enumOresTypeList = oreTypes;
    }

    public static EnumOres byMeta(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }

        return META_LOOKUP[meta];
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
