package com.sandvoxel.generitech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;


public class BlockGTBase extends Block {

    public BlockGTBase(Material materialIn){
        super(materialIn);
        this.setCreativeTab(CreativeTabs.tabTransport);
        this.setStepSound(SoundType.METAL);

    }


}
