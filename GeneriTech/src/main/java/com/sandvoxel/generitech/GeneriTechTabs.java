package com.sandvoxel.generitech;

import com.sandvoxel.generitech.blocks.GTBlocks;
import com.sandvoxel.generitech.items.GTItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class GeneriTechTabs {
    public static final CreativeTabs GENERAL = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(GTBlocks.BLOCK_PULVERIZER.getBlock());
        }

        @Override
        public String getTabLabel() {
            return Reference.MOD_ID + ".general";
        }
    };

    public static final CreativeTabs ORE = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return GTItems.ITEM_ORE_DUST.item;
        }

        @Override
        public String getTabLabel() {
            return Reference.MOD_ID + ".ore";
        }
    };
    
}
