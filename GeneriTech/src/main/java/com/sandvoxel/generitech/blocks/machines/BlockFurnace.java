package com.sandvoxel.generitech.blocks.machines;

import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.blocks.BlockMachineBase;
import com.sandvoxel.generitech.tileentities.machines.TileEntityFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class BlockFurnace extends BlockMachineBase {
    public BlockFurnace() {
        super(Material.rock, "machines/furnace");
        this.setTileEntity(TileEntityFurnace.class);
        this.setCreativeTab(GeneriTechTabs.GENERAL);
    }

/*    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(GeneriTech.instance, GuiHandler.PULVERIZER_GUI, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }*/

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, ACTIVE);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }
}
