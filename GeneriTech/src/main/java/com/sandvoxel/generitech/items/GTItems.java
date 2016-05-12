package com.sandvoxel.generitech.items;

import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.Reference;
import com.sandvoxel.generitech.blocks.machines.BlockPulverizer;
import com.sandvoxel.generitech.items.dust.ItemDust;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class GTItems {

    public static Item ITEM_DUST_COPPER;
    public static Item ITEM_DUST_TIN;
    public static Item ITEM_DUST_LEAD;
    public static Item ITEM_DUST_GOLD;
    public static Item ITEM_DUST_IRON;


    public static void init(){

        ITEM_DUST_COPPER = new ItemDust("dustcopper");
        ITEM_DUST_TIN = new ItemDust("dusttin");
        ITEM_DUST_LEAD = new ItemDust("dustlead");
        ITEM_DUST_GOLD = new ItemDust("dustgold");
        ITEM_DUST_IRON = new ItemDust("dustiron");
    }


    public static void register(){
        registerItem(ITEM_DUST_COPPER, "dustcopper");
        registerItem(ITEM_DUST_TIN, "dusttin");
        registerItem(ITEM_DUST_LEAD, "dustlead");
        registerItem(ITEM_DUST_GOLD, "dustgold");
        registerItem(ITEM_DUST_IRON, "dustiron");

    }

    public static void registerItem(Item itemIn, String resourceLocation){

        ResourceLocation itemRsLoc = new ResourceLocation(Reference.MOD_ID + ":" + resourceLocation);
        GameRegistry.register(itemIn, itemRsLoc);


    }
}
