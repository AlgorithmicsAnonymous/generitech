package xyz.aadev.generitech.common.tileentities.power;


import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.api.implementation.BaseTeslaContainer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import xyz.aadev.aalib.api.common.integrations.waila.IWailaBodyMessage;
import xyz.aadev.aalib.common.inventory.InternalInventory;
import xyz.aadev.aalib.common.inventory.InventoryOperation;
import xyz.aadev.generitech.Reference;
import xyz.aadev.generitech.api.util.MachineTier;
import xyz.aadev.generitech.client.gui.power.GuiGenerator;
import xyz.aadev.generitech.common.container.power.ContanierGenerator;
import xyz.aadev.generitech.common.tileentities.TileEntityMachineBase;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityPower extends TileEntityMachineBase implements ITeslaProducer, net.minecraft.util.ITickable, IWailaBodyMessage {
    MachineTier machineTier;
    private BaseTeslaContainer container = new BaseTeslaContainer(0, 50000, 1000, 1000);
    private InternalInventory inventory = new InternalInventory(this, 1);
    private int[] sides = new int[6];
    private int T0transfer = 120;
    private int fuelRemaining = 0;
    private Item lastFuelType;
    private int lastFuelValue;
    private int fuelTotal = 0;
    private boolean machineActive = false;


    public boolean isMachineActive() {
        return machineActive;
    }

    @Override
    public boolean canBeRotated() {
        return true;
    }

    @Override
    protected void syncDataFrom(NBTTagCompound nbtTagCompound, SyncReason syncReason) {
        super.syncDataFrom(nbtTagCompound, syncReason);
        this.container = new BaseTeslaContainer(nbtTagCompound.getCompoundTag("TeslaContainer"));
        fuelRemaining = nbtTagCompound.getInteger("fuelRemaining");
    }

    @Override
    protected void syncDataTo(NBTTagCompound nbtTagCompound, SyncReason syncReason) {
        super.syncDataTo(nbtTagCompound, syncReason);
        nbtTagCompound.setInteger("fuelRemaining", fuelRemaining);
        nbtTagCompound.setTag("TeslaContainer", this.container.serializeNBT());
    }


    @Override
    public Object getClientGuiElement(int guiId, EntityPlayer player) {
        return new GuiGenerator(player.inventory, this);
    }

    @Override
    public Object getServerGuiElement(int guiId, EntityPlayer player) {
        return new ContanierGenerator(player.inventory, this);
    }

    @Override
    public long takePower(long power, boolean simulated) {
        return power;
    }


    public long powerStored() {
        return container.getStoredPower();
    }

    @Override
    public void update() {
        if (fuelRemaining == 0 && machineActive){
            machineActive=false;
        }else if (!machineActive && fuelRemaining > 0){
            machineActive=true;
        }

        BlockPos pos = getPos();
        World worldIn = getWorld();
        if (machineTier == null) {
            machineTier = MachineTier.byMeta(getBlockMetadata());
        }
        if (fuelRemaining != 0) fuelRemaining--;

        if (fuelRemaining > 0 && machineTier == MachineTier.TIER_1) {
            container.givePower(20, false);
        }
        if (fuelRemaining > 0 && machineTier == MachineTier.TIER_2){
            container.givePower(40, false);
        }if (fuelRemaining > 0 && machineTier == MachineTier.TIER_3){
            container.givePower(60, false);
        }
        if (container.getStoredPower() != container.getCapacity() && inventory.getStackInSlot(0) != null || container.getStoredPower() < container.getCapacity() && inventory.getStackInSlot(0) != null) {
            burnTime();
        }

        if (container.getStoredPower() != 0) {
            DistributePowerToFace.transferPower(pos, worldIn, T0transfer, container, sides);
        }


    }

    public void burnTime() {
        if (fuelRemaining == 0 && TileEntityFurnace.isItemFuel(inventory.getStackInSlot(0))) {
            if (inventory.getStackInSlot(0).getItem() == lastFuelType) {
                fuelRemaining = lastFuelValue;


            } else if (inventory.getStackInSlot(0).getItem() != lastFuelType) {
                fuelRemaining = net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(0));
                lastFuelType = inventory.getStackInSlot(0).getItem();
                lastFuelValue = fuelRemaining;
            }
            fuelRemaining = fuelRemaining / 10;
            fuelTotal = fuelRemaining;
            inventory.decrStackSize(0, 1);
            this.markDirty();
            this.markForUpdate();
        }
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
        if (capability == TeslaCapabilities.CAPABILITY_PRODUCER || capability == TeslaCapabilities.CAPABILITY_HOLDER)
            return true;

        return super.hasCapability(capability, facing);
    }

    public int getFuelOffset() {
        if (fuelTotal == 0)
            return +14;

        return Math.round((((float) fuelTotal - (float) fuelRemaining) / (float) fuelTotal) * 13);
    }
}
