package com.Sandvoxel.GeneriTech.GTBlock;

import com.Sandvoxel.GeneriTech.EnumTypes.EnumMachine;
import com.Sandvoxel.GeneriTech.GeneriTab;
import com.Sandvoxel.GeneriTech.TileEntitys.TileEntityPulverizer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

import static com.Sandvoxel.GeneriTech.EnumTypes.EnumMachine.ON;

/**
 * Created by koval on 5/8/2016.
 */
public class BaceMachine extends DirectionalMachine {

    public static final PropertyEnum ONOFF = PropertyEnum.<EnumMachine>create("onoff", EnumMachine.class);
    private final boolean isOn;


    public BaceMachine(Material blockMaterial, SoundType stepSound, CreativeTabs tab, boolean isOn) {
        super(blockMaterial, stepSound, tab);
        this.isOn = isOn;
        this.setDefaultState(this.blockState.getBaseState().withProperty(ONOFF, EnumMachine.OFF));
        System.out.println(isOn);

    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityPulverizer();
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(GTBlocks.pulverizer);
    }


    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        if (!worldIn.isRemote) {
            if (!this.isOn && !worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(ONOFF, EnumMachine.OFF).withProperty(FACING, iblockstate.getValue(FACING)), 2);
            } else if (!this.isOn && worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(ONOFF, EnumMachine.ON).withProperty(FACING, iblockstate.getValue(FACING)), 2);
            }
        }
    }

    /**
     * Called when a neighboring block changes.
     */
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        if (!worldIn.isRemote) {
            if (!this.isOn && !worldIn.isBlockPowered(pos)) {
                worldIn.scheduleUpdate(pos, this, 4);
            } else if (!this.isOn && worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(ONOFF, EnumMachine.ON).withProperty(FACING, iblockstate.getValue(FACING)), 2);
            }
        }
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        if (!worldIn.isRemote) {
            if (!this.isOn && !worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(ONOFF, EnumMachine.OFF).withProperty(FACING, iblockstate.getValue(FACING)), 2);
            }
        }
    }





    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {ONOFF,FACING});
    }



    @Override
    public int getMetaFromState(IBlockState state)
    {
        EnumMachine type = (EnumMachine) state.getValue(ONOFF);

        int i = 0;

        i = i | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();

        if (state.getValue(ONOFF) == EnumMachine.ON){
            i |= 4;
        }


        return i;
    }
    @Override
    public IBlockState getStateFromMeta(int meta ) {
        return getDefaultState().withProperty(ONOFF,(meta & 4) > 0 ? EnumMachine.ON : EnumMachine.OFF ).withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

}
