package com.sandvoxel.generitech.api.util;

import com.sandvoxel.generitech.Reference;
import net.minecraft.util.IStringSerializable;

public enum MachineTier implements IStringSerializable {
    TIER_0("t0"),
    TIER_1("t1"),
    TIER_2("t2"),
    TIER_3("t3");
      // N/A

    private static final MachineTier[] META_LOOKUP = new MachineTier[values().length];

    static {
        for (MachineTier tier : values()) {
            META_LOOKUP[tier.getMeta()] = tier;
        }
    }

    private final String name;

    MachineTier(String name) {
        this.name = name;
    }

    public static MachineTier byMeta(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }

    public static MachineTier[] all() {
        return MachineTier.values();
    }

    public int getMeta() {
        return ordinal();
    }

    public String getName() {
        return this.name;
    }

    public String getUnlocalizedName() {
        return String.format("%s.%s.%s.%s", "label", Reference.MOD_ID, "machineTier", this.name);
    }
}