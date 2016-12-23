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

package xyz.aadev.generitech.common.tileentities.machines;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.implementation.BaseTeslaContainer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import org.apache.commons.lang3.time.DurationFormatUtils;
import xyz.aadev.aalib.api.common.integrations.waila.IWailaBodyMessage;
import xyz.aadev.aalib.common.inventory.InternalInventory;
import xyz.aadev.aalib.common.inventory.InventoryOperation;
import xyz.aadev.aalib.common.util.InventoryHelper;
import xyz.aadev.generitech.Reference;
import xyz.aadev.generitech.api.registries.PulverizerRegistry;
import xyz.aadev.generitech.api.util.Crushable;
import xyz.aadev.generitech.api.util.MachineTier;
import xyz.aadev.generitech.client.gui.machines.GuiPulverizer;
import xyz.aadev.generitech.client.gui.upgrade.GuiUpgradeScreen;
import xyz.aadev.generitech.common.container.machines.ContainerPulverizer;
import xyz.aadev.generitech.common.container.upgrade.ContanierUpgradeStorage;
import xyz.aadev.generitech.common.tileentities.TileEntityMachineBase;
import xyz.aadev.generitech.common.util.LanguageHelper;

import java.util.List;
import java.util.Random;

/*
* Slots
*
* 0 Input
* 1 Processing
* 2-3 Output
* 4 Fuel
* 5-9 Upgrades
*/

public class TileEntityPulverizer extends TileEntityMachineBase implements ITickable, IWailaBodyMessage, ITeslaConsumer, ITeslaHolder {

    private BaseTeslaContainer container = new BaseTeslaContainer(0, 50000, 10000, 10000);
    private InternalInventory inventory = new InternalInventory(this, 10);
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
    private float fortuneMultiplier = 0.5f;

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
    protected void syncDataTo(NBTTagCompound nbtTagCompound, SyncReason syncReason) {
        super.syncDataTo(nbtTagCompound, syncReason);
        nbtTagCompound.setInteger("ticksRemaining", ticksRemaining);
        nbtTagCompound.setBoolean("machineActive", machineActive);
        nbtTagCompound.setInteger("crushIndex", crushIndex);
        nbtTagCompound.setBoolean("pulverizerPaused", pulverizerPaused);
        nbtTagCompound.setFloat("crushRNG", crushRNG);
        nbtTagCompound.setTag("TeslaContainer", this.container.serializeNBT());

    }

    @Override
    protected void syncDataFrom(NBTTagCompound nbtTagCompound, SyncReason syncReason) {
        super.syncDataFrom(nbtTagCompound, syncReason);
        ticksRemaining = nbtTagCompound.getInteger("ticksRemaining");
        machineActive = nbtTagCompound.getBoolean("machineActive");
        crushIndex = nbtTagCompound.getInteger("crushIndex");
        pulverizerPaused = nbtTagCompound.getBoolean("pulverizerPaused");
        crushRNG = nbtTagCompound.getFloat("crushRNG");
        this.container = new BaseTeslaContainer(nbtTagCompound.getCompoundTag("TeslaContainer"));
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
        if (slot == 0 && PulverizerRegistry.containsInput(itemStack)) {
            return true;
        }

        return !(slot == 4 && !(net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime(itemStack) > 0));


    }

    @Override
    public int[] getAccessibleSlotsBySide(EnumFacing side) {
        int[] slots = new int[0];
        float oreAngle = (this.getForward().getHorizontalAngle() + 90) >= 360 ? 0 : (this.getForward().getHorizontalAngle() + 90);

        if (Math.abs(side.getHorizontalAngle() - oreAngle) < Reference.EPSILON) {
            slots = new int[1];
            slots[0] = 4;
        }
        if (side == EnumFacing.UP && machineTier == MachineTier.TIER_0) {
            slots = new int[1];
        }
        if (side == EnumFacing.DOWN && machineTier == MachineTier.TIER_0) {
            slots = new int[2];
            slots[0] = 2;
            slots[1] = 3;
        }
        int i = 0;
        for (final EnumFacing sidea : EnumFacing.VALUES) {
            if (sidea == side && (getSides()[i] == 1 || getSides()[i] == 0) && machineTier != MachineTier.TIER_0) {
                slots = new int[3];
                slots[0] = 2;
                slots[1] = 3;
                slots[2] = 0;
            }
            i++;
        }

        return slots;
    }


    @Override
    public Object getClientGuiElement(int guiId, EntityPlayer player) {
        if (guiId == 0) {
            return new GuiPulverizer(player.inventory, this);
        }
        if (guiId == 3) {
            return new GuiUpgradeScreen(player.inventory, this, getSides(), 5, player);
        }
        return null;
    }

    @Override
    public Object getServerGuiElement(int guiId, EntityPlayer player) {
        if (guiId == 0) {
            return new ContainerPulverizer(player.inventory, this);
        }
        if (guiId == 3) {
            return new ContanierUpgradeStorage(player.inventory, this, 5);
        }
        return null;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if (machineTier == MachineTier.TIER_0) {
            return direction == EnumFacing.DOWN && (index == 2 || index == 3);
        }
        if (getSides()[direction.getIndex()] == 0 && (index == 2 || index == 3)) {
                return true;
            }
        return false;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {

        if (getSides()[direction.getIndex()] == 1 && index == 0 && PulverizerRegistry.containsInput(itemStackIn)) {
                return true;
            }
        return true;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

    @Override
    public boolean canBeRotated() {
        return true;
    }


    public boolean canWork() {
        if (machineTier == MachineTier.TIER_0) {
            return fuelRemaining > 0;
        } else {
            return container.getStoredPower() >= powerUsage;
        }
    }

    //checks if it can crush
    public boolean cancrush(ItemStack item) {
        return PulverizerRegistry.containsInput(item);
    }

    public void burnTime(int i) {
        if (fuelRemaining == 0) {
            if (inventory.getStackInSlot(4).getItem() == lastFuelType && cancrush(inventory.getStackInSlot(i))) {
                fuelRemaining = lastFuelValue;


            } else if (inventory.getStackInSlot(4).getItem() != lastFuelType && cancrush(inventory.getStackInSlot(i))) {
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


    @Override
    public void update() {

/*
        System.out.println(getSides()[1]);
*/

        //checking for the machine type
        if (machineTier == null)
            machineTier = MachineTier.byMeta(getBlockMetadata());

        if (machineTier == MachineTier.TIER_0 && container.getInputRate() != 0) container.setInputRate(0);

        if (!canWork()) {
            machineActive = false;
            this.markForUpdate();
        }

        if (ticksRemaining == 0 && inventory.getStackInSlot(0) == null && machineTier != MachineTier.TIER_0) {
            machineActive = false;
        }

        if (this.canWork()) {
            if (machineTier == MachineTier.TIER_0) {
                fuelRemaining--;
                machineActive = fuelRemaining > 0;
            } else if (ticksRemaining > 0) {
                container.takePower(powerUsage, false);
                machineActive = true;
            }
            if (machineActive && ticksRemaining > 0) {
                ticksRemaining--;
            }
            this.markForUpdate();
            this.markDirty();
        }

        if (fuelRemaining == 0 && machineTier == MachineTier.TIER_0 && inventory.getStackInSlot(4) != null && net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(4)) > 0) {
            if (inventory.getStackInSlot(1) != null) {
                burnTime(1);
            }
            if (inventory.getStackInSlot(0) != null) {
                burnTime(0);
            }
        }


        if (this.canWork() && inventory.getStackInSlot(0) != null && inventory.getStackInSlot(1) == null) {
            if (!cancrush(inventory.getStackInSlot(0)))
                return;

            ItemStack itemIn = inventory.getStackInSlot(0);
            ItemStack itemOut;

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

        if (inventory.getStackInSlot(1) != null && ticksRemaining <= 0) {

            ticksRemaining = 0;

            if (worldObj.isRemote) {
                return;
            }

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
                boolean itemFortune = Math.abs(crushable.luckMultiplier - 1.0f) < Reference.EPSILON;

                if (Math.abs(crushRNG - (-1)) < Reference.EPSILON) crushRNG = rnd.nextFloat();

                if (itemFortune)
                    itemChance = itemChance + fortuneMultiplier;

                outItem.stackSize = (int) Math.round(Math.floor(itemChance) + crushRNG * itemChance % 1);
                if (outItem.stackSize == 0) outItem.stackSize = 1;


                // Simulate placing into output slot...
                if (InventoryHelper.addItemStackToInventory(outItem, inventory, 2, 3, true) != null) {
                    this.pulverizerPaused = true;
                    return;
                }

                InventoryHelper.addItemStackToInventory(outItem, inventory, 2, 3);
                this.crushRNG = -1;
            }
            this.crushIndex = 0;
            inventory.setInventorySlotContents(1, null);

            if (inventory.getStackInSlot(0) != null) {
                machineActive = cancrush(inventory.getStackInSlot(0));
            }
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

        float timePercent = ((float) getTotalProcessTime() - (float) ticksRemaining) / (float) getTotalProcessTime() * 100;
        int secondsLeft = (ticksRemaining / 20) * 1000;

        currentTip.add(String.format("%s: %s (%d%%)",
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

    public long getPower() {
        return container.getStoredPower();
    }


}


