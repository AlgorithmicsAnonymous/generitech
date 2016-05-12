package com.Sandvoxel.GeneriTech.container;

import com.Sandvoxel.GeneriTech.TileEntitys.TileEntityInventoryBase;
import com.Sandvoxel.GeneriTech.container.slot.SlotDisabled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPulverizer extends ContainerBase {

    public ContainerPulverizer(IInventory playerIn, TileEntityInventoryBase tileEntity)
    {
        super(playerIn, tileEntity);

        this.addSlotToContainer(new Slot(tileEntity, 0, 48, 35));
        this.addSlotToContainer(new Slot(tileEntity, 1, 124, 35));

        // Player Inventory, Slot 9-35, Slot IDs 9-35
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerIn, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // Player Inventory, Slot 0-8, Slot IDs 36-44
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerIn, x, 8 + x * 18, 142));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
        ItemStack previous = null;
        Slot slot = (Slot) this.inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            previous = current.copy();

            if (fromSlot < 3) {
                // From TE Inventory to Player Inventory
                if (!this.mergeItemStack(current, 3, 37, true))
                    return null;
            } else {
                // From Player Inventory to TE Inventory
                if (!this.mergeItemStack(current, 0, 1, false))
                    return null;
            }

            if (current.stackSize == 0)
                slot.putStack((ItemStack) null);
            else
                slot.onSlotChanged();

            if (current.stackSize == previous.stackSize)
                return null;
            slot.onPickupFromSlot(playerIn, current);
        }
        return previous;
    }
}
