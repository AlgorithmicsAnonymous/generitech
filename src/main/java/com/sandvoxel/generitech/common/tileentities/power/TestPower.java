package com.sandvoxel.generitech.common.tileentities.power;

import com.sandvoxel.generitech.api.util.MachineTier;
import com.sandvoxel.generitech.common.blocks.Blocks;
import com.sandvoxel.generitech.common.integrations.waila.IWailaBodyMessage;
import com.sandvoxel.generitech.common.inventory.InternalInventory;
import com.sandvoxel.generitech.common.inventory.InventoryOperation;
import com.sandvoxel.generitech.common.tileentities.TileEntityInventoryBase;
import com.sandvoxel.generitech.common.util.LanguageHelper;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.darkhax.tesla.api.implementation.BaseTeslaContainer;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.darkhax.tesla.lib.TeslaUtils;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.List;

public class TestPower extends TileEntityInventoryBase implements ITeslaProducer, net.minecraft.util.ITickable ,IWailaBodyMessage,ITeslaConsumer,ITeslaHolder {
    private BaseTeslaContainer container = new BaseTeslaContainer(0,10000,1000,1000);
    private InternalInventory inventory = new InternalInventory(this, 1);
    private boolean [] flag ={false,false,false,false,false,false};
    private int T0transfer = 100;
    private MachineTier machineTier;

    @Override
    public long takePower(long power, boolean simulated) {
        return power;
    }

    @Override
    public void update() {
        BlockPos pos = getPos();
        if (machineTier == null)
            machineTier = MachineTier.byMeta(getBlockMetadata());
        World worldIn = getWorld();




        if (worldObj.isBlockPowered(pos)){
            container.givePower(50, false);
        }

        if (machineTier == MachineTier.TIER_0){
            container.setOutputRate(100);
        }
        long stored = getStoredPower();

        TileEntity te = worldObj.getTileEntity(pos);




        if (flag[0]) getBlockTransfure(pos.up());
        if (flag[1]) getBlockTransfure(pos.down());
        if (flag[2]) getBlockTransfure(pos.north());
        if (flag[3]) getBlockTransfure(pos.south());
        if (flag[4]) getBlockTransfure(pos.east());
        if (flag[5]) getBlockTransfure(pos.west());


        flag[0]=this.canConnectTo(worldIn,pos.up());
        flag[1]=this.canConnectTo(worldIn,pos.down());
        flag[2]= this.canConnectTo(worldIn,pos.north());
        flag[3]= this.canConnectTo(worldIn,pos.south());
        flag[4]= this.canConnectTo(worldIn,pos.east());
        flag[5]= this.canConnectTo(worldIn,pos.west());


            }




        public void getBlockTransfure(BlockPos pos){
            World worldIn = getWorld();
            BlockPos pos1 = getPos();

            TileEntity te = worldObj.getTileEntity(pos);
            if (te instanceof ITeslaHolder && worldObj.isRemote){
                long tesla = ((ITeslaHolder) te).getCapacity()-((ITeslaHolder) te).getStoredPower();

                if (tesla > container.getOutputRate()){
                    if (container.getStoredPower() < container.getOutputRate()&&container.getStoredPower()!=0)
                    {
                        TeslaUtils.distributePowerToAllFaces(worldIn,pos1,container.getStoredPower(),false);
                        container.takePower(container.getStoredPower(),false);
                    }
                    if (tesla > container.getOutputRate()&&container.getStoredPower()!=0){
                        TeslaUtils.distributePowerToAllFaces(worldIn,pos1,container.getOutputRate(),false);
                        container.takePower(container.getOutputRate(),false);
                    }
                    else
                    {
                        if (container.getStoredPower()!=0){
                            System.out.println("1");
                            TeslaUtils.distributePowerToAllFaces(worldIn,pos1,tesla,false);
                            container.takePower(tesla,false);
                        }
                    }
                }else {
                    if (tesla > 0){
                        TeslaUtils.distributePowerToAllFaces(worldIn,pos1,tesla,false);
                        container.takePower(tesla,false);
                    }
                }

                }

        }












    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos).getBlock();
        if (block == Blocks.BLOCK_FURNACE.getBlock() || block == Blocks.BLOCK_PULVERIZER.getBlock()){
            return true;
        }else {
            return false;
        }
    }





    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        this.container = new BaseTeslaContainer(nbtTagCompound.getCompoundTag("TeslaContainer"));
        super.readFromNBT(nbtTagCompound);

    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {

        nbtTagCompound.setTag("TeslaContainer", this.container.serializeNBT());
        return super.writeToNBT(nbtTagCompound);
    }










    @Override
    public IInventory getInternalInventory() {
        return inventory;
    }

    @Override
    public void onChangeInventory(IInventory inv, int slot, InventoryOperation operation, ItemStack removed, ItemStack added) {

    }

    @Override
    public int[] getAccessibleSlotsBySide(EnumFacing side) {
        return new int[0];
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }


    @Override
    public List<String> getWailaBodyToolTip(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        currentTip.add(LanguageHelper.LABEL.translateMessage("Power") + Long.toString(container.getStoredPower()));

        return currentTip;
    }

    @Override
    public long givePower(long power, boolean simulated) {
        System.out.println("packet sent :" + power);
        this.markForUpdate();
        this.markDirty();
        container.givePower(power,simulated);
        return power;
    }

    @Override
    public long getStoredPower() {
        return container.getStoredPower();
    }

    @Override
    public long getCapacity() {
        return container.getCapacity();
    }






    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

        // This method is where other things will try to access your TileEntity's Tesla
        // capability. In the case of the analyzer, is a consumer, producer and holder so we
        // can allow requests that are looking for any of those things. This example also does
        // not care about which side is being accessed, however if you wanted to restrict which
        // side can be used, for example only allow power input through the back, that could be
        // done here.
        if (capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_HOLDER)
            return (T) this.container;

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {

        // This method replaces the instanceof checks that would be used in an interface based
        // system. It can be used by other things to see if the TileEntity uses a capability or
        // not. This example is a Consumer, Producer and Holder, so we return true for all
        // three. This can also be used to restrict access on certain sides, for example if you
        // only accept power input from the bottom of the block, you would only return true for
        // Consumer if the facing parameter was down.
        if (capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_HOLDER)
            return true;

        return super.hasCapability(capability, facing);
    }
}
