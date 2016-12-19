package xyz.aadev.generitech.common.network.messages.power;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.EnumFacing;
import xyz.aadev.aalib.common.inventory.InventoryOperation;
import xyz.aadev.aalib.common.network.PacketBaseThreadSafe;
import xyz.aadev.generitech.GeneriTech;
import xyz.aadev.generitech.common.tileentities.TileEntityMachineBase;
import xyz.aadev.generitech.common.tileentities.power.TileEntityPowerStorage;

import javax.annotation.Nullable;

public class PacketSides extends PacketBaseThreadSafe {

    private int side0;
    private int side1;
    private int side2;
    private int side3;
    private int side4;
    private int side5;


    public PacketSides() {

    }

    public PacketSides(int[] sides) {
        this.side0 = sides[0];
        this.side1 = sides[1];
        this.side2 = sides[2];
        this.side3 = sides[3];
        this.side4 = sides[4];
        this.side5 = sides[5];

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.side0 = buf.readInt();
        this.side1 = buf.readInt();
        this.side2 = buf.readInt();
        this.side3 = buf.readInt();
        this.side4 = buf.readInt();
        this.side5 = buf.readInt();


    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(side0);
        buf.writeInt(side1);
        buf.writeInt(side2);
        buf.writeInt(side3);
        buf.writeInt(side4);
        buf.writeInt(side5);


    }

    @Override
    public void handleClientSafe(NetHandlerPlayClient netHandler) {
        GeneriTech.Logger.debug(String.format("Received %s", new int[]{side0, side1, side2, side3, side4, side5}));
    }

    @Override
    public void handleServerSafe(NetHandlerPlayServer netHandler) {
        TileEntityMachineBase tile = new TileEntityMachineBase() {
            @Nullable
            @Override
            public ItemStack removeStackFromSlot(int index) {
                return null;
            }

            @Override
            public IInventory getInternalInventory() {
                return null;
            }

            @Override
            public void onChangeInventory(IInventory inv, int slot, InventoryOperation operation, ItemStack removed, ItemStack added) {

            }

            @Override
            public int[] getAccessibleSlotsBySide(EnumFacing side) {
                return new int[0];
            }
        };
        tile.setSides(new int[]{side0, side1, side2, side3, side4, side5});
    }
}
