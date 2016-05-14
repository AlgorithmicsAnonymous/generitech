package com.sandvoxel.generitech.common.blocks.machines;

import com.sandvoxel.generitech.GeneriTech;
import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.client.gui.GuiHandler;
import com.sandvoxel.generitech.common.blocks.BlockMachineBase;
import com.sandvoxel.generitech.common.tileentities.machines.TileEntityPulverizer;
import com.sandvoxel.generitech.common.util.LogHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPulverizer extends BlockMachineBase {

    public BlockPulverizer() {
        super(Material.rock, "machines/pulverizer");
        this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
        this.setTileEntity(TileEntityPulverizer.class);
        this.setCreativeTab(GeneriTechTabs.GENERAL);
        this.setInternalName("pulverizer");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(GeneriTech.instance, GuiHandler.PULVERIZER_GUI, world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, ACTIVE);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }
}
