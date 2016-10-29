/*
 * LIMITED USE SOFTWARE LICENSE AGREEMENT
 *
 * This Limited Use Software License Agreement (the "Agreement") is a legal agreement between you, the end-user, and the AlgorithmicsAnonymous Team ("AlgorithmicsAnonymous"). By downloading or purchasing the software material, which includes source code (the "Source Code"), artwork data, music and software tools (collectively, the "Software"), you are agreeing to be bound by the terms of this Agreement. If you do not agree to the terms of this Agreement, promptly destroy the Software you may have downloaded or copied.
 *
 * AlgorithmicsAnonymous SOFTWARE LICENSE
 *
 * 1. Grant of License. AlgorithmicsAnonymous grants to you the right to use the Software. You have no ownership or proprietary rights in or to the Software, or the Trademark. For purposes of this section, "use" means loading the Software into RAM, as well as installation on a hard disk or other storage device. The Software, together with any archive copy thereof, shall be destroyed when no longer used in accordance with this Agreement, or when the right to use the Software is terminated. You agree that the Software will not be shipped, transferred or exported into any country in violation of the U.S. Export Administration Act (or any other law governing such matters) and that you will not utilize, in any other manner, the Software in violation of any applicable law.
 *
 * 2. Permitted Uses. For educational purposes only, you, the end-user, may use portions of the Source Code, such as particular routines, to develop your own software, but may not duplicate the Source Code, except as noted in paragraph 4. The limited right referenced in the preceding sentence is hereinafter referred to as "Educational Use." By so exercising the Educational Use right you shall not obtain any ownership, copyright, proprietary or other interest in or to the Source Code, or any portion of the Source Code. You may dispose of your own software in your sole discretion. With the exception of the Educational Use right, you may not otherwise use the Software, or an portion of the Software, which includes the Source Code, for commercial gain.
 *
 * 3. Prohibited Uses: Under no circumstances shall you, the end-user, be permitted, allowed or authorized to commercially exploit the Software. Neither you nor anyone at your direction shall do any of the following acts with regard to the Software, or any portion thereof:
 *
 * Rent;
 *
 * Sell;
 *
 * Lease;
 *
 * Offer on a pay-per-play basis;
 *
 * Distribute for money or any other consideration; or
 *
 * In any other manner and through any medium whatsoever commercially exploit or use for any commercial purpose.
 *
 * Notwithstanding the foregoing prohibitions, you may commercially exploit the software you develop by exercising the Educational Use right, referenced in paragraph 2. hereinabove.
 *
 * 4. Copyright. The Software and all copyrights related thereto (including all characters and other images generated by the Software or depicted in the Software) are owned by AlgorithmicsAnonymous and is protected by United States copyright laws and international treaty provisions. AlgorithmicsAnonymous shall retain exclusive ownership and copyright in and to the Software and all portions of the Software and you shall have no ownership or other proprietary interest in such materials. You must treat the Software like any other copyrighted material. You may not otherwise reproduce, copy or disclose to others, in whole or in any part, the Software. You may not copy the written materials accompanying the Software. You agree to use your best efforts to see that any user of the Software licensed hereunder complies with this Agreement.
 *
 * 5. NO WARRANTIES. AlgorithmicsAnonymous DISCLAIMS ALL WARRANTIES, BOTH EXPRESS IMPLIED, INCLUDING BUT NOT LIMITED TO, IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE WITH RESPECT TO THE SOFTWARE. THIS LIMITED WARRANTY GIVES YOU SPECIFIC LEGAL RIGHTS. YOU MAY HAVE OTHER RIGHTS WHICH VARY FROM JURISDICTION TO JURISDICTION. AlgorithmicsAnonymous DOES NOT WARRANT THAT THE OPERATION OF THE SOFTWARE WILL BE UNINTERRUPTED, ERROR FREE OR MEET YOUR SPECIFIC REQUIREMENTS. THE WARRANTY SET FORTH ABOVE IS IN LIEU OF ALL OTHER EXPRESS WARRANTIES WHETHER ORAL OR WRITTEN. THE AGENTS, EMPLOYEES, DISTRIBUTORS, AND DEALERS OF AlgorithmicsAnonymous ARE NOT AUTHORIZED TO MAKE MODIFICATIONS TO THIS WARRANTY, OR ADDITIONAL WARRANTIES ON BEHALF OF AlgorithmicsAnonymous.
 *
 * Exclusive Remedies. The Software is being offered to you free of any charge. You agree that you have no remedy against AlgorithmicsAnonymous, its affiliates, contractors, suppliers, and agents for loss or damage caused by any defect or failure in the Software regardless of the form of action, whether in contract, tort, includinegligence, strict liability or otherwise, with regard to the Software. Copyright and other proprietary matters will be governed by United States laws and international treaties. IN ANY CASE, AlgorithmicsAnonymous SHALL NOT BE LIABLE FOR LOSS OF DATA, LOSS OF PROFITS, LOST SAVINGS, SPECIAL, INCIDENTAL, CONSEQUENTIAL, INDIRECT OR OTHER SIMILAR DAMAGES ARISING FROM BREACH OF WARRANTY, BREACH OF CONTRACT, NEGLIGENCE, OR OTHER LEGAL THEORY EVEN IF AlgorithmicsAnonymous OR ITS AGENT HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES, OR FOR ANY CLAIM BY ANY OTHER PARTY. Some jurisdictions do not allow the exclusion or limitation of incidental or consequential damages, so the above limitation or exclusion may not apply to you.
 */

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
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.implementation.BaseTeslaContainer;
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

/*
* Slots
*
* 0 Input
* 1 Processing
* 2-3 Output
* 4 Fuel
* 5-7 Upgrades
*/

public class TileEntityPulverizer extends TileEntityMachineBase implements ITickable, IWailaBodyMessage, ITeslaConsumer, ITeslaHolder {

    private BaseTeslaContainer container = new BaseTeslaContainer(1000, 50000, 1000, 1000);
    private InternalInventory inventory = new InternalInventory(this, 8);
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
    private float speedMultiplier = 0.0f;
    private float fortuneMultiplier = 0.5f;
    private int currentTotalProcessTime = 0;
    private boolean test = true;

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

    protected void checkUpgradeSlots() {

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
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setInteger("ticksRemaining", ticksRemaining);
        nbtTagCompound.setBoolean("machineActive", machineActive);
        nbtTagCompound.setInteger("crushIndex", crushIndex);
        nbtTagCompound.setBoolean("pulverizerPaused", pulverizerPaused);
        nbtTagCompound.setFloat("crushRNG", crushRNG);
        nbtTagCompound.setTag("TeslaContainer", this.container.serializeNBT());

        return nbtTagCompound;
    }

    @Override
    public IInventory getInternalInventory() {
        return inventory;
    }

    @Override
    public void onChangeInventory(IInventory inv, int slot, InventoryOperation operation, ItemStack removed, ItemStack added) {
        if (slot >= 2 && this.pulverizerPaused) pulverizerPaused = false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        if (slot == 1 || slot == 2 || slot == 3)
            return false;


        if (slot == 1 || slot == 2 || slot == 3)
            return false;

        return !(slot == 4 && !(net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime(itemStack) > 0));


    }

    @Override
    public int[] getAccessibleSlotsBySide(EnumFacing side) {
        int[] slots = new int[0];
        float oreAngle = (this.getForward().getHorizontalAngle() + 90) >= 360 ? 0 : (this.getForward().getHorizontalAngle() + 90);

        if (side.getHorizontalAngle() == oreAngle) {
            slots = new int[1];
            slots[0] = 4;
        }
        if (side == EnumFacing.UP && machineTier == MachineTier.TIER_0) {
            slots = new int[1];
        }
        if (side == EnumFacing.DOWN) {
            slots = new int[2];
            slots[0] = 2;
            slots[1] = 3;
        }

        return slots;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return direction == EnumFacing.DOWN && (index == 2 || index == 3);

    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

    @Override
    public boolean canBeRotated() {
        return true;
    }

    public long getPower() {
        return container.getStoredPower();
    }


    @Override
    public void update() {
        this.markForUpdate();
        this.markDirty();


        if (machineTier == null)
            machineTier = MachineTier.byMeta(getBlockMetadata());


        if (container.getStoredPower() <= 0 && machineTier != MachineTier.TIER_0 && machineActive != false || fuelRemaining == 0 && machineTier == MachineTier.TIER_0 && machineActive != false )
        {
                machineActive = false;
                this.markForUpdate();
        }
        if ( fuelRemaining > 0 && machineTier == MachineTier.TIER_0 && machineActive == false)
        {
            machineActive =true;
            this.markForUpdate();
        }
        if (container.getStoredPower() > 0 && machineTier != MachineTier.TIER_0 && machineActive == false)
        {
            if (inventory.getStackInSlot(1)!=null)
            {
                machineActive = true;
                this.markForUpdate();

            }
        }



        //for finding the fuel value
        if (fuelRemaining == 0 && inventory.getStackInSlot(4) != null && net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(4)) > 0 && machineTier == MachineTier.TIER_0 )
        {
            if (inventory.getStackInSlot(0) != null || inventory.getStackInSlot(1) != null) {
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
        }

        //for taking power/fuel from the block
        if ((this.container.takePower(powerUsage, true) == powerUsage && machineTier != MachineTier.TIER_0) || fuelRemaining > 0) {
            if (machineActive && !pulverizerPaused) {
                ticksRemaining--;

                if (machineTier != MachineTier.TIER_0)
                    this.container.takePower(powerUsage, false);


            }
        }
        if (fuelRemaining > 0)
            fuelRemaining--;


            //code for item in
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



            if (fuelRemaining == 0 && machineActive && machineTier == machineTier.TIER_0){
                ticksRemaining = 0;
                test = true;

            }else if (ticksRemaining == 0) {
                test = true;
            }


            if(container.getStoredPower() ==0 && ticksRemaining > 0){
                ticksRemaining--;
                System.out.println("wasd");
                this.markForUpdate();
            }


            if (ticksRemaining <= 0 && machineActive) {

                if (test){
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
                        boolean itemFortune = crushable.luckMultiplier == 1.0f;

                        if (crushRNG == -1) crushRNG = rnd.nextFloat();

                        if (itemFortune)
                            itemChance = itemChance + fortuneMultiplier;

                        outItem.stackSize = (int) Math.round(Math.floor(itemChance) + crushRNG * itemChance % 1);
                        if (outItem.stackSize == 0) outItem.stackSize = 1;


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

                test = true;
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

        currentTip.add(LanguageHelper.LABEL.translateMessage("Power") + Long.toString(getPower()));


        if (ticksRemaining == 0)
            return currentTip;

        float timePercent = ((((float) getTotalProcessTime() - (float) ticksRemaining) / (float) getTotalProcessTime())) * 100;
        int secondsLeft = (ticksRemaining / 20) * 1000;

                LanguageHelper.LABEL.translateMessage("time_left"),
                DurationFormatUtils.formatDuration(secondsLeft, "mm:ss"),
                Math.round(timePercent)));


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

    @Override
    public long givePower(long power, boolean simulated) {
        //System.out.println(power);
        container.givePower(power, simulated);
        this.markDirty();
        this.markForUpdate();
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
}


