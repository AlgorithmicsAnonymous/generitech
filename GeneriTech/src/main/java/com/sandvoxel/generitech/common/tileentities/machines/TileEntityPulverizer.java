package com.sandvoxel.generitech.common.tileentities.machines;

import com.sandvoxel.generitech.api.registries.PulverizerRegistry;
import com.sandvoxel.generitech.api.util.Crushable;
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
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.util.List;
import java.util.Random;

public class TileEntityPulverizer extends TileEntityMachineBase implements ITickable, IWailaBodyMessage {

    private InternalInventory inventory = new InternalInventory(this, 4);
    private int ticksRemaining = 0;
    private boolean machineActive = false;
    private int crushIndex = 0;
    private float crushRNG = 0;
    private boolean pulverizerPaused = false;
    private Random rnd = new Random();

    public boolean isPulverizerPaused() {
        return pulverizerPaused;
    }

    public boolean isMachineActive() {
        return machineActive;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);

        ticksRemaining = nbtTagCompound.getInteger("ticksRemaining");
        machineActive = nbtTagCompound.getBoolean("machineActive");
        crushIndex = nbtTagCompound.getInteger("crushIndex");
        pulverizerPaused = nbtTagCompound.getBoolean("pulverizerPaused");
        crushRNG = nbtTagCompound.getFloat("crushRNG");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setInteger("ticksRemaining", ticksRemaining);
        nbtTagCompound.setBoolean("machineActive", machineActive);
        nbtTagCompound.setInteger("crushIndex", crushIndex);
        nbtTagCompound.setBoolean("pulverizerPaused", pulverizerPaused);
        nbtTagCompound.setFloat("crushRNG", crushRNG);
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
        if(machineActive)
            ticksRemaining--;

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

                if(crushRNG <= itemChance)
                    outItem.stackSize += outItem.stackSize;

                // Simulate placing into output slot...
                if (InventoryHelper.addItemStackToInventory(outItem, inventory, 2, 3, true) != null) {
                    this.pulverizerPaused = true;
                    return;
                }

                if(pulverizerPaused)
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
}


