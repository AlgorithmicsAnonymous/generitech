package xyz.aadev.generitech.common.blocks.power;


import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.aadev.aalib.common.util.TileHelper;
import xyz.aadev.generitech.GeneriTech;
import xyz.aadev.generitech.GeneriTechTabs;
import xyz.aadev.generitech.Reference;
import xyz.aadev.generitech.api.util.MachineTier;
import xyz.aadev.generitech.common.blocks.BlockMachineBase;
import xyz.aadev.generitech.common.tileentities.power.TileEntityPower;

public class BlockGenerator extends BlockMachineBase {


    public BlockGenerator() {
        super(Material.ROCK, "machines/pulverizer/pulverizer", MachineTier.allexeptTier_0());
        this.setDefaultState(blockState.getBaseState().withProperty(MACHINETIER, MachineTier.TIER_0));
        this.setTileEntity(TileEntityPower.class);
        this.setCreativeTab(GeneriTechTabs.GENERAL);
        this.setInternalName("power");
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

        if (!world.isRemote) {
            player.openGui(GeneriTech.getInstance(), Reference.GUI_ID.GENERATOR_GUI, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity tileEntity = TileHelper.getTileEntity(worldIn, pos, TileEntity.class);
        if (tileEntity instanceof TileEntityPower && ((TileEntityPower) tileEntity).canBeRotated()) {
            return state.withProperty(FACING, ((TileEntityPower) tileEntity).getForward());
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
        TileEntityPower tileEntity = TileHelper.getTileEntity(world, blockPos, TileEntityPower.class);

        TileHelper.DropItems(tileEntity, 0, 0);

    }

}
