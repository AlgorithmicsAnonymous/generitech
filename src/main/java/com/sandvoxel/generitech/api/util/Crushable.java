package com.sandvoxel.generitech.api.util;

import net.minecraft.item.ItemStack;

public class Crushable {
    public ItemStack input;
    public ItemStack output;
    public float chance;

    public Crushable(ItemStack input, ItemStack output, float chance) {
        this.input = input;
        this.output = output;
        this.chance = chance;
    }
}
