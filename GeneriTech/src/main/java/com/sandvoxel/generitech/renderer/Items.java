package com.sandvoxel.generitech.renderer;


import com.sandvoxel.generitech.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class Items {



    public static void registerItemRenderer() {
/*        reg(GTItems.ITEM_DUST_COPPER);
        reg(GTItems.ITEM_DUST_TIN);
        reg(GTItems.ITEM_DUST_LEAD);
        reg(GTItems.ITEM_DUST_IRON);
        reg(GTItems.ITEM_DUST_GOLD);*/

    }


    public static void reg(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }


}
