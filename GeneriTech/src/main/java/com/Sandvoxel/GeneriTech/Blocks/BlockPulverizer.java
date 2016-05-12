package com.Sandvoxel.GeneriTech.Blocks;

import com.Sandvoxel.GeneriTech.GTBlock.BaseMachine;
import com.Sandvoxel.GeneriTech.GTBlock.GTBlocks;
import com.Sandvoxel.GeneriTech.GeneriTech;
import com.Sandvoxel.GeneriTech.TileEntitys.TileEntityPulverizer;
import com.Sandvoxel.GeneriTech.handler.GuiHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static sun.audio.AudioPlayer.player;

/**
 * Created by koval on 5/10/2016.
 */
public class BlockPulverizer extends BaseMachine {

    public BlockPulverizer(Material blockMaterial, SoundType stepSound, CreativeTabs tab) {
        super(blockMaterial, stepSound, tab, TileEntityPulverizer.class);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(GTBlocks.pulverizer);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(GeneriTech.Instance, GuiHandler.PULVERIZER_GUI, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

}
