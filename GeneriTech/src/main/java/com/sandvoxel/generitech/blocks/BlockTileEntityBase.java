package com.sandvoxel.generitech.blocks;

import com.sandvoxel.generitech.Reference;
import com.sandvoxel.generitech.tileentities.TileEntityBase;
import com.sandvoxel.generitech.tileentities.TileEntityInventoryBase;
import com.sandvoxel.generitech.util.IBlockRenderer;
import com.sandvoxel.generitech.util.TileHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.annotation.Nonnull;

/**
 * Created by Sean on 13/05/2016.
 */
public abstract class BlockTileEntityBase extends BlockBase implements IBlockRenderer, ITileEntityProvider{
    protected static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    @Nonnull
    private Class<? extends TileEntity> tileEntityClass;

    public BlockTileEntityBase(Material material, String resourcePath) {
        super(material, resourcePath);
    }

    protected void setTileEntity(final Class<? extends TileEntity> clazz) {
        this.tileEntityClass = clazz;
        this.setTileProvider(true);

        String tileName = "tileentity." + Reference.MOD_ID + "." + clazz.getSimpleName();
        GameRegistry.registerTileEntity(this.tileEntityClass, tileName);
    }

    private void setTileProvider(final boolean b) {
        ReflectionHelper.setPrivateValue(Block.class, this, b, "isTileProvider");
    }

    public Class<? extends TileEntity> getTileEntityClass() {
        return this.tileEntityClass;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        final TileEntityInventoryBase tileEntity = (TileEntityInventoryBase)worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileEntityInventoryBase)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, tileEntity);
        }
    }

    @Override
    public EnumFacing[] getValidRotations(World world, BlockPos pos) {
        final TileEntityBase tileEntity = (TileEntityBase) world.getTileEntity(pos);
        if (tileEntity != null && tileEntity.canBeRotated())
            return EnumFacing.HORIZONTALS;

        return super.getValidRotations(world, pos);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntityBase tileEntity = TileHelper.getTileEntity(worldIn, pos, TileEntityBase.class);
        if (tileEntity != null && tileEntity.canBeRotated()) {
            return state.withProperty(FACING, tileEntity.getForward());
        }
        return state.withProperty(FACING, EnumFacing.NORTH);
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

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState state, EntityLivingBase placer, ItemStack itemStack) {
        TileEntityBase tileEntity = (TileEntityBase)world.getTileEntity(blockPos);

        if (tileEntity == null)
            return;

        if (itemStack.hasDisplayName()) {
            tileEntity.setCustomName(itemStack.getDisplayName());
        }

        if (tileEntity.canBeRotated()) {
            if (placer.isSneaking()) {
                tileEntity.setOrientation(placer.getHorizontalFacing());
            } else {
                tileEntity.setOrientation(placer.getHorizontalFacing().getOpposite());
            }
        }
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

    @Override
    public void registerBlockRenderer() {
        super.registerBlockRenderer();
    }

    @Override
    public void registerBlockItemRenderer() {
        super.registerBlockItemRenderer();
    }


}
