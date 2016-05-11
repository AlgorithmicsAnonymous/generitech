package com.Sandvoxel.GeneriTech;

import com.Sandvoxel.GeneriTech.GTBlock.GTBlocks;
import com.Sandvoxel.GeneriTech.Misc.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class GeneriTechTabs {
    public static final CreativeTabs GENERAL = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(GTBlocks.pulverizer);
        }

        @Override
        public String getTabLabel() {
            return Reference.MOD_ID + ".general";
        }
    };
}
