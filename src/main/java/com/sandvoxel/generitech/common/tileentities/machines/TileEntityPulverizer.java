package com.sandvoxel.generitech.common.tileentities.machines;

import com.sandvoxel.generitech.api.registries.PulverizerRegistry;
import com.sandvoxel.generitech.api.util.Crushable;
import com.sandvoxel.generitech.api.util.MachineTier;
import com.sandvoxel.generitech.common.integrations.waila.IWailaBodyMessage;
import com.sandvoxel.generitech.common.inventory.InternalInventory;
import com.sandvoxel.generitech.common.inventory.InventoryOperation;
import com.sandvoxel.generitech.common.tileentities.TileEntityMachineBase;
import com.sandvoxel.generitech.common.util.InventoryHelper;
import com.sandvoxel.generitech.common.util.LanguageHelper;
import com.sandvoxel.generitech.common.util.LogHelper;
import com.sandvoxel.generitech.common.util.MathHelper;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.darkhax.tesla.api.BaseTeslaContainer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.util.List;
import java.util.Random;

public class TileEntityPulverizer extends TileEntityMachineBase implements ITickable, IWailaBodyMessage {

    private BaseTeslaContainer container = new BaseTeslaContainer(50000, 50000, 50, 50);
    private InternalInventory inventory = new InternalInventory(this, 5);
    private int ticksRemaining = 0;
    private boolean machineActive = false;
    private int crushIndex = 0;
    private float crushRNG = 0;
    private boolean pulverizerPaused = false;
    private Random rnd = new Random();
    private long powerUsage = 50;
    private MachineTier machineTier;
    private int fuelRemaining = 0;
    private int fuelTotal = 0;
    private Item lastFuelType;
    private int lastFuelValue;


    public boolean isPulverizerPaused() {
        return pulverizerPaused;
    }

    public boolean isMachineActive() {
        return machineActive;
    }

    @Override
    public void markForUpdate() {
        super.markForUpdate();

        if (machineTier == MachineTier.TIER_0)
            this.markForLightUpdate();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);

        ticksRemaining = nbtTagCompound.getInteger("ticksRemaining");
        machineActive = nbtTagCompound.getBoolean("machineActive");
        crushIndex = nbtTagCompound.getInteger("crushIndex");
        pulverizerPaused = nbtTagCompound.getBoolean("pulverizerPaused");
        crushRNG = nbtTagCompound.getFloat("crushRNG");
        this.container = new BaseTeslaContainer(nbtTagCompound.getCompoundTag("TeslaContainer"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setInteger("ticksRemaining", ticksRemaining);
        nbtTagCompound.setBoolean("machineActive", machineActive);
        nbtTagCompound.setInteger("crushIndex", crushIndex);
        nbtTagCompound.setBoolean("pulverizerPaused", pulverizerPaused);
        nbtTagCompound.setFloat("crushRNG", crushRNG);
        nbtTagCompound.setTag("TeslaContainer", this.container.serializeNBT());
    }

    @Override
    public IInventory getInternalInventory() {
        return inventory;
    }

    @Override
    public void onChangeInventory(IInventory inv, int slot, InventoryOperation operation, ItemStack removed, ItemStack added) {

    }

/*    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        if(slot == 1 || slot == 2 || slot == 3)
            return false;

        if(slot == 4 && !(net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime(itemStack) > 0))
            return false;


        return true;
    }*/

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        if(slot == 4 && !(net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime(itemStack) > 0))
            return false;

        return super.isItemValidForSlot(slot, itemStack);
    }

    @Override
    public int[] getAccessibleSlotsBySide(EnumFacing side) {
        int[] slots = new int[0];
        float oreAngle = (this.getForward().getHorizontalAngle() + 90) >= 360 ? 0 : (this.getForward().getHorizontalAngle() + 90);

        if(side.getHorizontalAngle() == oreAngle) {
            slots = new int[1];
            slots[0] = 4;
        }
        if(side == EnumFacing.UP && machineTier == MachineTier.TIER_0) {
            slots = new int[1];
        }
        if(side == EnumFacing.DOWN) {
            slots = new int[2];
            slots[0] = 2;
            slots[1] = 3;
        }

        return slots;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if(direction == EnumFacing.DOWN && (index == 2 || index == 3))
            return true;

        return false;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

    @Override
    public boolean canBeRotated() {
        return true;
    }

    @Override
    public void update() {
        if (machineTier == null)
            machineTier = MachineTier.byMeta(getBlockMetadata());

        if (fuelRemaining == 0 && inventory.getStackInSlot(4) != null && net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(4)) > 0 && machineTier == MachineTier.TIER_0) {
            if (inventory.getStackInSlot(4).getItem() == lastFuelType) {
                fuelRemaining = lastFuelValue;
            } else {
                fuelRemaining = net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(4));
                lastFuelType = inventory.getStackInSlot(4).getItem();
                lastFuelValue = fuelRemaining;
            }
            fuelTotal = fuelRemaining;
            inventory.decrStackSize(4, 1);
            this.markDirty();
            this.markForUpdate();
        }

        if ((this.container.takePower(powerUsage, true) == powerUsage && machineTier != MachineTier.TIER_0) || fuelRemaining > 0) {
            if (machineActive && !pulverizerPaused) {
                ticksRemaining--;

                if (machineTier != MachineTier.TIER_0)
                    this.container.takePower(powerUsage, false);

                if (fuelRemaining > 0)
                    fuelRemaining--;
            }

            if (inventory.getStackInSlot(0) != null && inventory.getStackInSlot(1) == null) {
                ItemStack itemIn = inventory.getStackInSlot(0);
                ItemStack itemOut;

                if (!PulverizerRegistry.containsInput(itemIn))
                    return;

                if (itemIn.stackSize - 1 <= 0) {
                    itemOut = itemIn.copy();
                    itemIn = null;
                } else {
                    itemOut = itemIn.copy();

                    itemOut.stackSize = 1;
                    itemIn.stackSize = itemIn.stackSize - 1;
                }

                if (itemIn != null && itemIn.stackSize == 0) itemIn = null;
                if (itemOut.stackSize == 0) itemOut = null;

                inventory.setInventorySlotContents(0, itemIn);
                inventory.setInventorySlotContents(1, itemOut);

                ticksRemaining = 200;
                machineActive = true;

                this.markForUpdate();
                this.markDirty();
            }

            if (ticksRemaining <= 0 && machineActive) {
                ticksRemaining = 0;

                if (worldObj.isRemote)
                    return;

                ItemStack processItem = inventory.getStackInSlot(1);
                if (processItem == null) {
                    return;
                }

                List<Crushable> outputs = PulverizerRegistry.getOutputs(processItem);

                if (outputs.isEmpty())
                    return;

                for (int i = this.crushIndex; i < outputs.size(); i++) {
                    this.crushIndex = i;
                    Crushable crushable = outputs.get(this.crushIndex);

                    ItemStack outItem = crushable.output.copy();
                    float itemChance = crushable.chance;

                    if (crushRNG == -1) crushRNG = rnd.nextFloat();

                    if (crushRNG <= itemChance)
                        outItem.stackSize += outItem.stackSize;

                    // Simulate placing into output slot...
                    if (InventoryHelper.addItemStackToInventory(outItem, inventory, 2, 3, true) != null) {
                        this.pulverizerPaused = true;
                        return;
                    }

                    if (pulverizerPaused)
                        pulverizerPaused = !pulverizerPaused;

                    InventoryHelper.addItemStackToInventory(outItem, inventory, 2, 3);
                    this.crushRNG = -1;
                }

                this.crushIndex = 0;
                inventory.setInventorySlotContents(1, null);

                machineActive = false;

                this.markForUpdate();
                this.markDirty();
            }
        }
    }

    public int getTicksRemaining() {
        return ticksRemaining;
    }

    public int getTotalProcessTime() {
        if (ticksRemaining == 0)
            return 0;

        return 200;
    }

    @Override
    public List<String> getWailaBodyToolTip(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (ticksRemaining == 0)
            return currentTip;

        float timePercent = ((((float) getTotalProcessTime() - (float) ticksRemaining) / (float) getTotalProcessTime())) * 100;
        int secondsLeft = (ticksRemaining / 20) * 1000;

        currentTip.add(String.format("%s: %s (%d%%)",
                LanguageHelper.LABEL.translateMessage("time_left"),
                DurationFormatUtils.formatDuration(secondsLeft, "mm:ss"),
                Math.round(timePercent)
        ));

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
            return +12;

        return Math.round((((float) fuelTotal - (float) fuelRemaining) / (float) fuelTotal) * 11);

    }
}


