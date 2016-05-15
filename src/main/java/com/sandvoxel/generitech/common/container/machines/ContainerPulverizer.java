package com.sandvoxel.generitech.common.container.machines;

import com.sandvoxel.generitech.common.container.slot.SlotOutput;
import com.sandvoxel.generitech.common.container.slot.SlotNormal;
import com.sandvoxel.generitech.common.container.ContainerBase;
import com.sandvoxel.generitech.common.tileentities.TileEntityInventoryBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerPulverizer extends ContainerBase {
    IInventory inventory;

    public ContainerPulverizer(InventoryPlayer inventoryPlayer, TileEntity tileEntity)
    {
        super(inventoryPlayer, tileEntity);
        this.inventory = (IInventory) tileEntity;

        bindPlayerInventory(inventoryPlayer, 0, 84);

        this.addSlotToContainer(new SlotNormal(inventory, 0, 48, 35));
        this.addSlotToContainer(new SlotOutput(inventory, 2, 124, 35));
        this.addSlotToContainer(new SlotOutput(inventory, 3, 143, 35));
    }
}
