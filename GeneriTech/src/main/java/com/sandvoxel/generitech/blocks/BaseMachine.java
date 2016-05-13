package com.sandvoxel.generitech.blocks;

import com.sandvoxel.generitech.Reference;
import com.sandvoxel.generitech.tileentities.TileEntityBase;
import com.sandvoxel.generitech.tileentities.TileEntityInventoryBase;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.annotation.Nonnull;
import java.util.Random;


public class BaseMachine extends DirectionalMachine implements ITileEntityProvider {


    public static final PropertyBool ONOFF = PropertyBool.create("onoff");

    @Nonnull
    private Class<? extends TileEntity> tileEntityClass;

    public BaseMachine(Material blockMaterial, SoundType stepSound, CreativeTabs tab) {
        super(blockMaterial, stepSound, tab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ONOFF, false));
    }

    protected void setTileEntity(final Class<? extends TileEntity> clazz) {
        this.tileEntityClass = clazz;
        this.setTileProvider(true);

        String tileName = "tileentity." + Reference.MOD_ID + "." + clazz.getSimpleName();
        GameRegistry.registerTileEntity(this.tileEntityClass, tileName);
    }

    public Class<? extends TileEntity> getTileEntityClass() {
        return this.tileEntityClass;
    }
    private void setTileProvider(final boolean b) {
        ReflectionHelper.setPrivateValue(Block.class, this, b, "isTileProvider");
    }

    @Override
    public final TileEntity createNewTileEntity(World worldIn, int meta) {
        try {
            return this.tileEntityClass.newInstance();
        } catch (final InstantiationException ex) {
            throw new IllegalStateException("Failed to create a new instance of an illegal class " + this.tileEntityClass, ex);
        } catch (final IllegalAccessException ex) {
            throw new IllegalStateException("Failed to create a new instance of " + this.tileEntityClass + " because of a lack of permissions", ex);
        }
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntityInventoryBase tileentity = (TileEntityInventoryBase)worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityInventoryBase)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
        worldIn.setBlockToAir(pos);
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        if (!worldIn.isRemote) {
            if (!worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(ONOFF, false).withProperty(FACING, iblockstate.getValue(FACING)), 2);
            } else if (worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(ONOFF, true).withProperty(FACING, iblockstate.getValue(FACING)), 2);
            }
        }
    }

    /**
     * Called when a neighboring block changes.
     */
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        if (!worldIn.isRemote) {
            if (!worldIn.isBlockPowered(pos)) {
                worldIn.scheduleUpdate(pos, this, 4);
            } else if (worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(ONOFF, true).withProperty(FACING, iblockstate.getValue(FACING)), 2);
            }
        }
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        if (!worldIn.isRemote) {
            if (!worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, GTBlocks.pulverizer.getDefaultState().withProperty(ONOFF, false).withProperty(FACING, iblockstate.getValue(FACING)), 2);
            }

        }
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ONOFF,FACING);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {

        int i = 0;

        i = i | state.getValue(FACING).getHorizontalIndex();

        if (state.getValue(ONOFF) == true){
            i |= 4;
        }


        return i;
    }
    @Override
    public IBlockState getStateFromMeta(int meta ) {
        return getDefaultState().withProperty(ONOFF,(meta & 4) > 0 ? true : false ).withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState state, EntityLivingBase placer, ItemStack itemStack) {
        TileEntityBase tileEntity = (TileEntityBase) world.getTileEntity(blockPos);

        if (tileEntity == null)
            return;

        if (itemStack.hasDisplayName()) {
            tileEntity.setCustomName(itemStack.getDisplayName());
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, false);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }

}