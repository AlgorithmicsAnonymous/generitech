package com.Sandvoxel.GeneriTech.TileEntitys;

import com.Sandvoxel.GeneriTech.Contaners.PulverizerContaner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
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
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by koval on 5/9/2016.
 */
public class TileEntityPulverizer extends TileEntityLockableLoot implements IInventory {

    private ItemStack[] PulverizerItemStacks = new ItemStack[3];
    private String PulverizerCustomName;





    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.PulverizerItemStacks = new ItemStack[this.getSizeInventory()];

        if (compound.hasKey("PulverizerCustomName", 8))
        {
            this.PulverizerCustomName = compound.getString("PulverizerCustomName");
        }

        if (!this.checkLootAndRead(compound))
        {
            NBTTagList nbttaglist = compound.getTagList("PulverizerItems", 6);

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound.getByte("PulverizerSlot") & 255;

                if (j >= 0 && j < this.PulverizerItemStacks.length)
                {
                    this.PulverizerItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
                }
            }
        }
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (!this.checkLootAndWrite(compound))
        {
            NBTTagList nbttaglist = new NBTTagList();

            for (int i = 0; i < this.PulverizerItemStacks.length; ++i)
            {
                if (this.PulverizerItemStacks[i] != null)
                {
                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                    nbttagcompound.setByte("PulverizerSlot", (byte)i);
                    this.PulverizerItemStacks[i].writeToNBT(nbttagcompound);
                    nbttaglist.appendTag(nbttagcompound);
                }
            }

            compound.setTag("PulverizerItems", nbttaglist);
        }

        if (this.hasCustomName())
        {
            compound.setString("PulverizerCustomName", this.PulverizerCustomName);
        }
    }


    @Override
    public int getSizeInventory() {
        return 3;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        this.fillWithLoot((EntityPlayer)null);
        return this.PulverizerItemStacks[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        this.fillWithLoot((EntityPlayer)null);
        ItemStack itemstack = ItemStackHelper.func_188382_a(this.PulverizerItemStacks, index, count);

        if (itemstack != null)
        {
            this.markDirty();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        this.fillWithLoot((EntityPlayer)null);
        return ItemStackHelper.func_188383_a(this.PulverizerItemStacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.fillWithLoot((EntityPlayer)null);
        this.PulverizerItemStacks[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
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
        return 0;
    }

    @Override
    public void clear() {
        this.fillWithLoot((EntityPlayer)null);

        for (int i = 0; i < this.PulverizerItemStacks.length; ++i)
        {
            this.PulverizerItemStacks[i] = null;
        }
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return null;
    }

    @Override
    public String getGuiID() {
        return null;
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.PulverizerCustomName : "container.pulverizer";
    }

    @Override
    public boolean hasCustomName() {
        return this.PulverizerCustomName != null && !this.PulverizerCustomName.isEmpty();
    }


    public void setCustomName(String name)
    {
        this.PulverizerCustomName = name;
    }
}
