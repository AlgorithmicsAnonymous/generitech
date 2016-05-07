package com.Sandvoxel.GeneriTech.GTBlock;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by koval on 5/7/2016.
 */
public class BaceBlockGT extends Block {

    public BaceBlockGT(String pulverizer){
        super(Material.glass);
        this.setCreativeTab(CreativeTabs.tabTransport);
        this.setStepSound(SoundType.METAL);

    }


}
