package com.sandvoxel.generitech.common.tileentities.power;

import com.sandvoxel.generitech.GeneriTech;
import com.sandvoxel.generitech.common.blocks.Blocks;
import com.sandvoxel.generitech.common.blocks.machines.BlockPulverizer;
import com.sandvoxel.generitech.common.inventory.InternalInventory;
import com.sandvoxel.generitech.common.inventory.InventoryOperation;
import com.sandvoxel.generitech.common.network.messages.power.PacketPower;
import com.sandvoxel.generitech.common.tileentities.TileEntityBase;
import com.sandvoxel.generitech.common.tileentities.TileEntityInventoryBase;
import com.sandvoxel.generitech.common.util.LogHelper;
import net.darkhax.tesla.api.BaseTeslaContainer;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TestPower extends TileEntityInventoryBase implements ITeslaProducer, net.minecraft.util.ITickable{
    private BaseTeslaContainer container = new BaseTeslaContainer(100,10000,1000,1000);
    private InternalInventory inventory = new InternalInventory(this, 1);
    private boolean [] flag ={false,false,false,false,false,false};

    @Override
    public long takePower(long power, boolean simulated) {
        return power;
    }

    @Override
    public void update() {
        BlockPos pos = getPos();
        World worldIn = getWorld();

        getPos();

        container.givePower(1000, false);
        //System.out.println(worldObj.getTileEntity(pos.up()));

        if (flag[0]&& container.getStoredPower()>container.getOutputRate())
        {
            GeneriTech.network.sendToServer(new PacketPower(container.getOutputRate(),pos.getX() ,pos.getZ(),pos.getY()+1));
            container.takePower(container.getOutputRate(),false);

        }







        flag[0]= this.canConnectTo(worldIn,pos.up());
        flag[1]= this.canConnectTo(worldIn,pos.down());
        flag[2]= this.canConnectTo(worldIn,pos.north());
        flag[3]= this.canConnectTo(worldIn,pos.south());
        flag[4]= this.canConnectTo(worldIn,pos.east());
        flag[5]= this.canConnectTo(worldIn,pos.west());

    }


    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos).getBlock();
        return block == Blocks.BLOCK_PULVERIZER.getBlock() ? true : block == Blocks.BLOCK_FURNACE.getBlock() ? true : false;
    }





    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        this.container = new BaseTeslaContainer(nbtTagCompound.getCompoundTag("TeslaContainer"));
        super.readFromNBT(nbtTagCompound);

    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {

        nbtTagCompound.setTag("TeslaContainer", this.container.serializeNBT());
        return super.writeToNBT(nbtTagCompound);
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

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }


}
