package com.Sandvoxel.GeneriTech;

import com.Sandvoxel.GeneriTech.GTBlock.GTBlocks;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by CrazyGrape on 5/8/2016.
 */
public class GeneriTab extends CreativeTabs {

    private static Block iconBlock;

    public GeneriTab(String label, Block tabIconBlock) {
        super(label);
        iconBlock = tabIconBlock;
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(iconBlock);
    }


}
