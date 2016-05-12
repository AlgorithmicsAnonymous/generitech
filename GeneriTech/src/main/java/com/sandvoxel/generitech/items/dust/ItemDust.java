package com.sandvoxel.generitech.items.dust;

import com.sandvoxel.generitech.GeneriTechTabs;
import net.minecraft.item.Item;


public class ItemDust extends Item {




    public ItemDust(String UnlocalizedName){
        super();

        this.setCreativeTab(GeneriTechTabs.DUST);
        this.setUnlocalizedName(UnlocalizedName);

    }

}
