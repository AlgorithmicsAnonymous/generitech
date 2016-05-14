package com.sandvoxel.generitech.common.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;

public abstract class TileEntityBase extends TileEntity {
    private String customName;
    private EnumFacing front = EnumFacing.NORTH;
    private int renderedFragment = 0;

    public String getCustomName()
    {
        return this.customName;
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    public boolean hasCustomName()
    {
        return customName != null && customName.length() > 0;
    }

    public void markForUpdate() {
        if (this.worldObj != null) {
            Block block = worldObj.getBlockState(this.pos).getBlock();

            this.worldObj.notifyBlockUpdate(this.pos, worldObj.getBlockState(this.pos), worldObj.getBlockState(this.pos), 3);

            int xCoord = this.pos.getX();
            int yCoord = this.pos.getY();
            int zCoord = this.pos.getZ();

            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord, yCoord - 1, zCoord), block);
            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord, yCoord + 1, zCoord), block);
            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord - 1, yCoord, zCoord), block);
            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord + 1, yCoord, zCoord), block);
            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord, yCoord - 1, zCoord - 1), block);
            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord, yCoord - 1, zCoord + 1), block);
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound data = new NBTTagCompound();
        writeToNBT(data);
        return new SPacketUpdateTileEntity(this.pos, 1, data);
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity s35PacketUpdateTileEntity) {
        readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
        worldObj.markBlockRangeForRenderUpdate(this.pos, this.pos);
        markForUpdate();
    }

    public TileEntity getTile() {
        return this;
    }

    public void markForLightUpdate() {
        if (this.worldObj.isRemote) {
            this.worldObj.notifyBlockUpdate(this.pos, worldObj.getBlockState(this.pos), worldObj.getBlockState(this.pos), 3);
        }

        this.worldObj.checkLightFor(EnumSkyBlock.BLOCK, this.pos);
    }

    @Override
    public void onChunkUnload() {
        if (!this.isInvalid())
            this.invalidate();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        if (nbtTagCompound.hasKey("CustomName"))
        {
            this.customName = nbtTagCompound.getString("CustomName");
        }

        if (canBeRotated()) {
            this.front = EnumFacing.values()[nbtTagCompound.getInteger("forward")];
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        if (this.hasCustomName())
        {
            nbtTagCompound.setString("CustomName", customName);
        }

        if (canBeRotated()) {
            nbtTagCompound.setInteger("forward", this.front.ordinal());
        }
    }

    public IBlockState getBlockState() {
        if (worldObj == null)
            return null;

        return worldObj.getBlockState(pos);
    }

    public boolean canBeRotated() {
        return false;
    }

    public EnumFacing getForward() {
        return front;
    }

    public void setOrientation(EnumFacing forward) {
        this.front = forward;
        markDirty();
        markForUpdate();
    }

    public EnumFacing getDirection() {
        return getForward();
    }

    public String getUnlocalizedName() {
        Item item = Item.getItemFromBlock(worldObj.getBlockState(this.pos).getBlock());
        ItemStack itemStack = new ItemStack(item, 1, getBlockMetadata());

        return itemStack.getUnlocalizedName() + ".name";
    }

}
