package com.sandvoxel.generitech.blocks.ores;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemOre extends ItemBlock{
    public ItemOre(String unlocalizedName, Block block) {
        super(block);
        this.setUnlocalizedName(unlocalizedName);
        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
