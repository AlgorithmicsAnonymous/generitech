package xyz.aadev.generitech.common.tileentities.power;


import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.api.implementation.BaseTeslaContainer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.darkhax.tesla.lib.TeslaUtils;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import xyz.aadev.aalib.api.common.integrations.waila.IWailaBodyMessage;
import xyz.aadev.aalib.client.util.LanguageHelper;
import xyz.aadev.aalib.common.inventory.InternalInventory;
import xyz.aadev.aalib.common.inventory.InventoryOperation;
import xyz.aadev.aalib.common.tileentities.TileEntityInventoryBase;
import xyz.aadev.generitech.Reference;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityPower extends TileEntityInventoryBase implements ITeslaProducer, net.minecraft.util.ITickable, IWailaBodyMessage {
    private BaseTeslaContainer container = new BaseTeslaContainer(0, 50000, 1000, 1000);
    private InternalInventory inventory = new InternalInventory(this, 1);
    private int T0transfer = 120;
    private int fuelRemaining = 0;
    private Item lastFuelType;
    private int lastFuelValue;
    private int fuelTotal = 0;

    @Override
    public long takePower(long power, boolean simulated) {
        return power;
    }


    public long powerStored(){
        return container.getStoredPower();
    }

    @Override
    public void update() {
        BlockPos pos = getPos();
        World worldIn = getWorld();

        if (fuelRemaining!=0)fuelRemaining--;
        if ( fuelRemaining > 0){
         container.givePower(60,false);
        }
        if (container.getStoredPower() != container.getCapacity()&& inventory.getStackInSlot(0)!=null||container.getStoredPower() < container.getCapacity() && inventory.getStackInSlot(0)!=null){
            burnTime();
        }

        if (container.getStoredPower()!=0){
            transferPower();
        }

    }

    public void burnTime() {
        if (fuelRemaining == 0) {
            if (inventory.getStackInSlot(0).getItem() == lastFuelType ) {
                fuelRemaining = lastFuelValue;


            } else if (inventory.getStackInSlot(0).getItem() != lastFuelType) {
                fuelRemaining = net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(0));
                lastFuelType = inventory.getStackInSlot(0).getItem();
                lastFuelValue = fuelRemaining;
            }
            fuelRemaining = fuelRemaining/10;
            fuelTotal = fuelRemaining;
            inventory.decrStackSize(0, 1);
            this.markDirty();
            this.markForUpdate();
        }
    }

    public void transferPower(){
        BlockPos pos = getPos();
        World worldIn = getWorld();
        long inputba = TeslaUtils.distributePowerToAllFaces(worldIn,pos,T0transfer,true);
        long test = inputba / T0transfer;
        if (test==0)test=1;
        if (inputba!=0 && test != 0 && container.getStoredPower() > test){
            long input = inputba/test;
            if (container.getStoredPower()>=inputba){
                    container.takePower(inputba,false);
                    TeslaUtils.distributePowerToAllFaces(worldIn,pos,input,false);
            }else if (container.getStoredPower()<inputba){
                    long toMoveUnder = container.getStoredPower()/test;
                    container.takePower(container.getStoredPower(),false);
                    TeslaUtils.distributePowerToAllFaces(worldIn,pos,toMoveUnder,false);
            }

        }



    }




    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        this.container = new BaseTeslaContainer(nbtTagCompound.getCompoundTag("TeslaContainer"));
        fuelRemaining = nbtTagCompound.getInteger("fuelRemaining");
        super.readFromNBT(nbtTagCompound);

    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger("fuelRemaining", fuelRemaining);
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
        int[] slots = new int[0];
        float oreAngle = (this.getForward().getHorizontalAngle() + 90) >= 360 ? 0 : (this.getForward().getHorizontalAngle() + 90);

        if (Math.abs(side.getHorizontalAngle() - oreAngle) < Reference.EPSILON) {
            slots = new int[1];
            slots[0] = 0;
        }

        return slots;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }


    @Override
    public List<String> getWailaBodyToolTip(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        currentTip.add(Long.toString(container.getStoredPower()));

        return currentTip;
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
    public int getFuelOffset() {
        if (fuelTotal == 0)
            return +14;

        return Math.round((((float) fuelTotal - (float) fuelRemaining) / (float) fuelTotal) * 13);
    }
}
