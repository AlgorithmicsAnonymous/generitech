package com.Sandvoxel.GeneriTech.TileEntitys;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by koval on 5/9/2016.
 */
public class TileEntityPulverizer extends TileEntityLockable {


    private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {2, 1};
    private static final int[] slotsSides = new int[] {1};
    private ItemStack[] PulverizerItemStacks = new ItemStack[3];
    private String PulverizerCustomName;


    @Override
    public int getSizeInventory() {
        return this.PulverizerItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.PulverizerItemStacks[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.func_188382_a(this.PulverizerItemStacks, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.func_188383_a(this.PulverizerItemStacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        stack.stackSize = this.getInventoryStackLimit();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 4;
    }

    @Override
    public void clear() {
        {
            for (int i = 0; i < this.PulverizerItemStacks.length; ++i)
            {
                this.PulverizerItemStacks[i] = null;
            }
        }
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerFurnace(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return "generitech:furnace";
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.PulverizerCustomName : "container.pulverizer";
    }

    @Override
    public boolean hasCustomName() {
        return this.PulverizerCustomName != null && !this.PulverizerCustomName.isEmpty();
    }


    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.PulverizerItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");

            if (j >= 0 && j < this.PulverizerItemStacks.length)
            {
                this.PulverizerItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }

        if (compound.hasKey("CustomName", 8))
        {
            this.PulverizerCustomName = compound.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.PulverizerItemStacks.length; ++i)
        {
            if (this.PulverizerItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.PulverizerItemStacks[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        compound.setTag("Items", nbttaglist);

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.PulverizerCustomName);
        }
    }
























}
