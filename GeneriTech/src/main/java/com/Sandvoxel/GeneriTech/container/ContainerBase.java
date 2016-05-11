package com.Sandvoxel.GeneriTech.container;

import com.Sandvoxel.GeneriTech.TileEntitys.TileEntityInventoryBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class ContainerBase extends Container {
    private TileEntityInventoryBase tileEntity;

    public ContainerBase(IInventory playerInv, TileEntityInventoryBase tileEntity) {
        this.tileEntity = tileEntity;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileEntity.isUseableByPlayer(playerIn);
    }
}
