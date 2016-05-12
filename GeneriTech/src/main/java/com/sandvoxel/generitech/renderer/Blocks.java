package com.sandvoxel.generitech.renderer;

import com.sandvoxel.generitech.blocks.GTBlocks;
import com.sandvoxel.generitech.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;


public class Blocks {


    public static void preInit(){
        ModelBakery.registerItemVariants(Item.getItemFromBlock(GTBlocks.blockOre), new ModelResourceLocation(Reference.MOD_ID + ":" + "copper_ore"));
    }

    public static void registerRenders(){
        registerRender(GTBlocks.pulverizer, "pulverizer");
        //registerRender(GTBlocks.blockOre, "blockOre");
        //
        reg(GTBlocks.blockOre, 0, "copper_ore");

    }

    public static void reg(Block block, int meta, String file){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(Reference.MOD_ID + ":" + file, "inventory"));
    }

    public static void registerRender(Block blockIn, String resourceLocation){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(blockIn), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + resourceLocation, "inventory"));
    }
}
