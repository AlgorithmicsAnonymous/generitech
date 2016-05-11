package com.Sandvoxel.GeneriTech.Blocks;

import com.Sandvoxel.GeneriTech.GTBlock.BaceMachine;
import com.Sandvoxel.GeneriTech.GTBlock.GTBlocks;
import com.Sandvoxel.GeneriTech.TileEntitys.TileEntityPulverizer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by koval on 5/10/2016.
 */
public class BlockPulverizer extends BaceMachine {



    public BlockPulverizer(Material blockMaterial, SoundType stepSound, CreativeTabs tab, boolean isOn) {
        super(blockMaterial, stepSound, tab, isOn);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityPulverizer();
    }


    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(GTBlocks.pulverizer);
    }
}
