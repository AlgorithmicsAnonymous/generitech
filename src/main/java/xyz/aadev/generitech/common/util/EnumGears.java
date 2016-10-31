package xyz.aadev.generitech.common.util;

import net.minecraft.util.IStringSerializable;

public enum EnumGears implements IStringSerializable {
    COBBLESTONE("Stone"),
    IRON("Iron"),
    COPPER("Copper"),
    TIN("Tin"),
    GOLD("Gold");

    private final String name;

    EnumGears(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name.toLowerCase();
    }

    public String getOreName() {
        return this.name;
    }

    public int getMeta() {
        return this.ordinal();
    }

    public String toString() {
        return getName();
    }
}
