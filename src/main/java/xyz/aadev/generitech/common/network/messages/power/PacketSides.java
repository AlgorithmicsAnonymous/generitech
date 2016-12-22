package xyz.aadev.generitech.common.network.messages.power;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import xyz.aadev.aalib.common.inventory.InventoryOperation;
import xyz.aadev.aalib.common.network.PacketBaseThreadSafe;
import xyz.aadev.generitech.common.tileentities.TileEntityMachineBase;

import javax.annotation.Nullable;

public class PacketSides extends PacketBaseThreadSafe {

    private int side0;
    private int side1;
    private int side2;
    private int side3;
    private int side4;
    private int side5;
    private int side;
    private int x;
    private int y;
    private int z;

    public PacketSides() {

    }

    public PacketSides(int[] sides , int side, BlockPos pos) {
        this.side = side;
        this.side0 = sides[0];
        this.side1 = sides[1];
        this.side2 = sides[2];
        this.side3 = sides[3];
        this.side4 = sides[4];
        this.side5 = sides[5];
        this.x = pos.getX();
        this.y= pos.getY();
        this.z =pos.getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.side = buf.readInt();
        this.side0 = buf.readInt();
        this.side1 = buf.readInt();
        this.side2 = buf.readInt();
        this.side3 = buf.readInt();
        this.side4 = buf.readInt();
        this.side5 = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(side);
        buf.writeInt(side0);
        buf.writeInt(side1);
        buf.writeInt(side2);
        buf.writeInt(side3);
        buf.writeInt(side4);
        buf.writeInt(side5);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);

    }


    @Override
    public void handleClientSafe(NetHandlerPlayClient netHandler) {

    }

    @Override
    public void handleServerSafe(NetHandlerPlayServer netHandler) {

        int[] sides =new int[]{side0, side1, side2, side3, side4, side5};
        TileEntity tile = netHandler.playerEntity.getEntityWorld().getTileEntity(new BlockPos(x,y,z));

        if (tile instanceof TileEntityMachineBase){
            sides[side]++;
            if (sides[side]>=3){
                sides[side]=0;
            }
            ((TileEntityMachineBase) tile).setSides(sides);
        }




    }
}
