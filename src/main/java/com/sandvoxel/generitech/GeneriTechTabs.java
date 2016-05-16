package com.sandvoxel.generitech;
import com.sandvoxel.generitech.common.items.Items;
import com.sandvoxel.generitech.common.blocks.Blocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class GeneriTechTabs {
    public static final CreativeTabs GENERAL = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.BLOCK_PULVERIZER.getBlock());
        }

        @Override
        public String getTabLabel() {
            return Reference.MOD_ID + ".general";
        }
    };

    public static final CreativeTabs ORE = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Items.ITEM_ORE_DUST.getItem();
        }

        @Override
        public String getTabLabel() {
            return Reference.MOD_ID + ".ore";
        }
    };

    public static final CreativeTabs TOOL = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Items.ITEM_TOOL_WRENCH.getItem();
        }

        @Override
        public String getTabLabel() {
            return Reference.MOD_ID + ".tools";
        }
    };

    public static final CreativeTabs FLUID = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return net.minecraft.init.Items.bucket;
        }

        @Override
        public String getTabLabel() {
            return Reference.MOD_ID + ".fluids";
        }
    };

    private GeneriTechTabs()
    {

    }
}
