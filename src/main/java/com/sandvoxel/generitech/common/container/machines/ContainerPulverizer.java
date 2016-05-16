package com.sandvoxel.generitech.common.container.machines;

import com.sandvoxel.generitech.api.util.MachineTier;
import com.sandvoxel.generitech.common.container.slot.SlotFuelInput;
import com.sandvoxel.generitech.common.container.slot.SlotOutput;
import com.sandvoxel.generitech.common.container.slot.SlotNormal;
import com.sandvoxel.generitech.common.container.ContainerBase;
import com.sandvoxel.generitech.common.tileentities.TileEntityInventoryBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerPulverizer extends ContainerBase {
    IInventory inventory;
    MachineTier machineTier;

    public ContainerPulverizer(InventoryPlayer inventoryPlayer, TileEntity tileEntity)
    {
        super(inventoryPlayer, tileEntity);
        this.inventory = (IInventory) tileEntity;
        this.machineTier = MachineTier.byMeta(tileEntity.getBlockMetadata());

        bindPlayerInventory(inventoryPlayer, 0, 84);

        this.addSlotToContainer(new SlotNormal(inventory, 0, 48, 35));
        this.addSlotToContainer(new SlotOutput(inventory, 2, 124, 35));
        this.addSlotToContainer(new SlotOutput(inventory, 3, 143, 35));

        if(machineTier == MachineTier.TIER_0)
        {
            this.addSlotToContainer(new SlotFuelInput(inventory, 4, 26, 64, null));
        }
    }
}
