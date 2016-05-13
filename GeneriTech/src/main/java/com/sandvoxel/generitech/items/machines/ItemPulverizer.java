package com.sandvoxel.generitech.items.machines;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemPulverizer extends ItemBlock {
    public ItemPulverizer(Block block) {
        super(block);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
