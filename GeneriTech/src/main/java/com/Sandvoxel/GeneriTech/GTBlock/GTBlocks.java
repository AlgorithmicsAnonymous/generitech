package com.Sandvoxel.GeneriTech.GTBlock;

import com.Sandvoxel.GeneriTech.Blocks.BlockPulverizer;
import com.Sandvoxel.GeneriTech.GeneriTech;
import com.Sandvoxel.GeneriTech.Misc.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by koval on 5/7/2016.
 * Made to actually work by CrazyGrape on the same date
 */
public class GTBlocks {

    public static Block pulverizer;



    public static void init(){


        pulverizer = new BlockPulverizer(Material.iron, SoundType.METAL, GeneriTech.TabGeneriTech);



    }

    public static void register(){
        registerBlock(pulverizer, "pulverizer");
    }


    /**
     * Registers a Block through Forge's GameRegistry. Makes an ItemBlock for you, too. Do note that
     * the ItemBlock's UnlocalizedName is the same as the resourceLocation.
     * @param blockIn The Block to register
     * @param resourceLocation The name of the block's blockstate .json file in the blockstates folder of your mod
     */
    public static void registerBlock(Block blockIn, String resourceLocation){
        //Replace Reference.MOD_ID to either the reference for your mod ID or the ID itself
        ResourceLocation blockRsLoc = new ResourceLocation(Reference.MOD_ID + ":" + resourceLocation);
        GameRegistry.register(blockIn, blockRsLoc);
        GameRegistry.register(new ItemBlock(blockIn), blockRsLoc);
        blockIn.setUnlocalizedName(resourceLocation);
    }

    /**
     * Same as RegisterBlock, except it doesn't register an ItemBlock of the item.
     * Useful if you're a control freak who hates granting people the ability to place
     * certain blocks from their inventory.
     * Refer to RegisterBlock if you are somehow confused about the parameters.
     * @param blockIn
     * @param resourceLocation
     */

    public static void registerBlockNoItem(Block blockIn, String resourceLocation){
        GameRegistry.register(blockIn, new ResourceLocation(Reference.MOD_ID + ":" + resourceLocation));
    }
}
