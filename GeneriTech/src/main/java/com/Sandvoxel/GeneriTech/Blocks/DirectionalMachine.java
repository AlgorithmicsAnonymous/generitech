package com.sandvoxel.generitech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DirectionalMachine extends BlockContainer {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    private static boolean keepInventory;

    public DirectionalMachine(Material blockMaterial, SoundType stepSound, CreativeTabs tab){
        super(blockMaterial);
        this.setStepSound(stepSound);
        this.setCreativeTab(tab);
        this.isBlockContainer = true;
    }


    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return null;
    }



    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }



    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            Block block = worldIn.getBlockState(pos.north()).getBlock();
            Block block1 = worldIn.getBlockState(pos.south()).getBlock();
            Block block2 = worldIn.getBlockState(pos.west()).getBlock();
            Block block3 = worldIn.getBlockState(pos.east()).getBlock();
            EnumFacing enumfacing = state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && block.isFullCube(state) && !block1.isFullBlock(state))
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock(state) && !block.isFullBlock(state))
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && block2.isFullBlock(state) && !block3.isFullBlock(state))
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && block3.isFullBlock(state) && !block2.isFullBlock(state))
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        keepInventory = true;

        if (active)
        {
            worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }
        else
        {
            worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }

        keepInventory = false;

        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }



    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {

        if(placer.isSneaking())
            return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());

        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }



    /**
     * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

     /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(FACING)).getIndex();
    }






}
