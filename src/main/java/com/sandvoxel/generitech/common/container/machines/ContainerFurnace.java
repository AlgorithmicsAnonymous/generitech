package com.sandvoxel.generitech.common.container.machines;

import com.sandvoxel.generitech.common.container.ContainerBase;
import com.sandvoxel.generitech.common.container.slot.SlotFuelInput;
import com.sandvoxel.generitech.common.container.slot.SlotNormal;
import com.sandvoxel.generitech.common.container.slot.SlotOutput;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerFurnace extends ContainerBase {
    IInventory inventory;

    public ContainerFurnace(InventoryPlayer inventoryPlayer, TileEntity tileEntity)
    {
        super(inventoryPlayer, tileEntity);
        this.inventory = (IInventory) tileEntity;

        bindPlayerInventory(inventoryPlayer, 0, 84);

        this.addSlotToContainer(new SlotNormal(inventory, 0, 56, 17));
        this.addSlotToContainer(new SlotOutput(inventory, 1, 116, 35));
        this.addSlotToContainer(new SlotFuelInput(inventory, 2, 56, 53, new ItemStack(Items.coal)));
    }
}
