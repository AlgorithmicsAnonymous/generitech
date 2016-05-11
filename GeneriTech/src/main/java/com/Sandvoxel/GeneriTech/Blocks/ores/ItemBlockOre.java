package com.Sandvoxel.GeneriTech.Blocks.ores;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockOre extends ItemBlock{


    public ItemBlockOre(Block block) {
        super(block);
        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
    }

    public int getMetadata(int damage){
        return damage;
    }

}
