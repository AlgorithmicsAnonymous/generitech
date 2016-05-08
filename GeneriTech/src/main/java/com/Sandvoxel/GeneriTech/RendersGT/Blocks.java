package com.Sandvoxel.GeneriTech.RendersGT;

import com.Sandvoxel.GeneriTech.GTBlock.BaceBlockGT;
import com.Sandvoxel.GeneriTech.GTBlock.GTBlocks;
import com.Sandvoxel.GeneriTech.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class Blocks {


    public static void registerRenders(){
        registerRender(GTBlocks.pulverizer, "pulverizer");
    }

    public static void registerRender(Block blockIn, String resourceLocation){
        //Item item = Item.getItemFromBlock(blockIn);
        //Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + resourceLocation));

        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(blockIn), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + resourceLocation, "inventory"));
    }





}
