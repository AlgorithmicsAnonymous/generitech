package xyz.aadev.generitech.common.blocks.power;


import xyz.aadev.generitech.common.tileentities.power.TileEntityPower;
import xyz.aadev.generitech.common.tileentities.machines.TileEntityPulverizer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.aadev.aalib.common.blocks.BlockMachineBase;
import xyz.aadev.aalib.common.util.TileHelper;
import xyz.aadev.generitech.GeneriTechTabs;
import xyz.aadev.generitech.api.util.MachineTier;

import java.util.Random;

public class BlockCables extends BlockMachineBase {

    public BlockCables() {
        super(Material.ROCK, "machines/pulverizer/pulverizer", MachineTier.all());
        this.setDefaultState(blockState.getBaseState().withProperty(MACHINETIER, MachineTier.TIER_0));
        this.setTileEntity(TileEntityPower.class);
        this.setCreativeTab(GeneriTechTabs.GENERAL);
        this.setInternalName("power");
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {

        }
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntityPower tileEntity = TileHelper.getTileEntity(worldIn, pos, TileEntityPower.class);
        if (tileEntity != null && tileEntity.canBeRotated()) {
            return state.withProperty(FACING, tileEntity.getForward());
        }
        return state.withProperty(FACING, EnumFacing.NORTH);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        IBlockState blockState = getActualState(state, world, pos);
        return (blockState.getValue(MACHINETIER) == MachineTier.TIER_0) ? 15 : 0;
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

        TileHelper.DropItems(tileEntity,0,0);

    }

    @Override
    public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand) {
        TileEntityPulverizer tileEntity = TileHelper.getTileEntity(worldIn, pos, TileEntityPulverizer.class);
        if (tileEntity == null)
            return;

        if (!tileEntity.isMachineActive() || tileEntity.isPulverizerPaused())
            return;

        EnumFacing enumfacing = tileEntity.getForward();
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
        double d2 = (double) pos.getZ() + 0.5D;
        double d3 = 0.52D;
        double d4 = rand.nextDouble() * 0.6D - 0.3D;

        EnumParticleTypes particleTypes = null;
        switch (tileEntity.getBlockMetadata()) {
            case 0: // Stone
                particleTypes = EnumParticleTypes.SMOKE_NORMAL;
                break;
            default:
                break;
        }

        if (particleTypes != null) {
            switch (enumfacing) {
                case WEST:
                    worldIn.spawnParticle(particleTypes, d0 - d3, d1 + 0.7f, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case EAST:
                    worldIn.spawnParticle(particleTypes, d0 + d3, d1 + 0.7f, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case NORTH:
                    worldIn.spawnParticle(particleTypes, d0 + d4, d1 + 0.7f, d2 - d3, 0.0D, 0.0D, 0.0D);
                    break;
                case SOUTH:
                    worldIn.spawnParticle(particleTypes, d0 + d4, d1 + 0.7f, d2 + d3, 0.0D, 0.0D, 0.0D);
                    break;
                default:
                    break;
            }
        }
    }
}
