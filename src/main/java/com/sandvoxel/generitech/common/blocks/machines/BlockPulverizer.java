package com.sandvoxel.generitech.common.blocks.machines;

import com.sandvoxel.generitech.GeneriTech;
import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.api.util.MachineTier;
import com.sandvoxel.generitech.client.gui.GuiHandler;
import com.sandvoxel.generitech.common.blocks.BlockMachineBase;
import com.sandvoxel.generitech.common.tileentities.machines.TileEntityPulverizer;
import com.sandvoxel.generitech.common.util.LogHelper;
import com.sandvoxel.generitech.common.util.TileHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPulverizer extends BlockMachineBase {

    public BlockPulverizer() {
        super(Material.rock, "machines/pulverizer", MachineTier.TIER_0, MachineTier.TIER_1, MachineTier.TIER_2, MachineTier.TIER_3);
        this.setDefaultState(blockState.getBaseState().withProperty(MACHINETIER, MachineTier.TIER_0).withProperty(FACING, EnumFacing.NORTH));
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
        return new BlockStateContainer(this, MACHINETIER, FACING);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public void breakBlock(World world, BlockPos blockPos, IBlockState blockState) {
        TileEntityPulverizer tileEntity = TileHelper.getTileEntity(world, blockPos, TileEntityPulverizer.class);
        if (tileEntity != null && !tileEntity.isPulverizerPaused()) {
            super.breakBlock(world, blockPos, blockState);
            return;
        }

        TileHelper.DropItems(tileEntity, 0, 0);
        TileHelper.DropItems(tileEntity, 2, 3);
    }
}
