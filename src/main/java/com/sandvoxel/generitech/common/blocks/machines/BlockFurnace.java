package com.sandvoxel.generitech.common.blocks.machines;

import com.sandvoxel.generitech.GeneriTech;
import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.api.util.MachineTier;
import com.sandvoxel.generitech.client.gui.GuiHandler;
import com.sandvoxel.generitech.common.blocks.BlockMachineBase;
import com.sandvoxel.generitech.common.tileentities.machines.TileEntityFurnace;
import com.sandvoxel.generitech.common.tileentities.machines.TileEntityPulverizer;
import com.sandvoxel.generitech.common.util.TileHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFurnace extends BlockMachineBase {
    private static final PropertyBool ACTIVE = PropertyBool.create("active");

    public BlockFurnace() {
        super(Material.rock, "machines/furnace/furnace", MachineTier.TIER_1, MachineTier.TIER_2, MachineTier.TIER_3);
        this.setDefaultState(blockState.getBaseState().withProperty(MACHINETIER, MachineTier.TIER_1).withProperty(FACING, EnumFacing.NORTH));
        this.setTileEntity(TileEntityFurnace.class);
        this.setCreativeTab(GeneriTechTabs.GENERAL);
        this.setInternalName("furnace");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(GeneriTech.instance, GuiHandler.FURNACE_GUI, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntityFurnace tileEntity = TileHelper.getTileEntity(worldIn, pos, TileEntityFurnace.class);
        if (tileEntity != null && tileEntity.canBeRotated()) {
            return state.withProperty(FACING, tileEntity.getForward()).withProperty(ACTIVE, tileEntity.isMachineActive());
        }
        return state.withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, MACHINETIER, FACING, ACTIVE);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }
}
