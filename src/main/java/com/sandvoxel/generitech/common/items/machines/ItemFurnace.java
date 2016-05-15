package com.sandvoxel.generitech.common.items.machines;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemFurnace extends ItemBlock {
    public ItemFurnace(Block block) {
        super(block);
        this.setMaxDamage(0);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
