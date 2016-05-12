package com.sandvoxel.generitech.blocks;

import com.sandvoxel.generitech.blocks.machines.BlockPulverizer;
import com.sandvoxel.generitech.blocks.ores.BlockOre;
import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.Reference;
import com.sandvoxel.generitech.items.ore.ItemOre;
import com.sandvoxel.generitech.util.IBlockRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GTBlocks {

    public static Block pulverizer;
    //public static Block testBlock;
    public static BlockOre blockOre;
    public static ItemOre itemOre;



    public static final String[] subNames = new String[] {"copper", "tin", "lead"};

    public static void init(){

        pulverizer = new BlockPulverizer(Material.iron, SoundType.METAL, GeneriTechTabs.GENERAL);
        //testBlock = new TestBlock(Material.rock, SoundType.ANVIL, GeneriTechTabs.GENERAL);
        blockOre = new BlockOre("blockOre", Material.rock, 1, 2);
        itemOre = new ItemOre("blockOre", blockOre);



    }

    public static void register(){
        registerBlock(pulverizer, "pulverizer");
        //registerBlock(testBlock, "testBlock");
        GameRegistry.register(blockOre.setRegistryName("blockOre"));
        GameRegistry.register(itemOre.setRegistryName(blockOre.getRegistryName()));



    }

    public static void registerRenderers()
    {
        ((IBlockRenderer) blockOre).registerBlockRenderer();
        ((IBlockRenderer) blockOre).registerBlockItemRenderer();
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
        blockIn.setUnlocalizedName(resourceLocation);
        GameRegistry.register(new ItemBlock(blockIn), blockRsLoc);

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
