package xyz.aadev.generitech.common.network.messages.power;

import io.netty.buffer.ByteBuf;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import xyz.aadev.aalib.common.logging.Logger;
import xyz.aadev.aalib.common.network.PacketBase;
import xyz.aadev.aalib.common.network.PacketBaseThreadSafe;

public class PacketPower extends PacketBaseThreadSafe {

    private long test;
    private int x;
    private int z;
    private int y;

    public PacketPower(long test, int x, int z, int y) {
        this.test = test;
        this.x = x;
        this.z = z;
        this.y = y;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        test = buf.readLong();
        x = buf.readInt();
        z = buf.readInt();
        y = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(test);
        buf.writeInt(x);
        buf.writeInt(z);
        buf.writeInt(y);
    }

    @Override
    public void handleClientSafe(NetHandlerPlayClient netHandler) {
        Logger.debug(String.format("Received %s", test));
    }

    @Override
    public void handleServerSafe(NetHandlerPlayServer netHandler) {
        TileEntity tileEntity = netHandler.playerEntity.worldObj.getTileEntity(new BlockPos(x, y, z));

        if (tileEntity instanceof ITeslaConsumer) {
            ((ITeslaConsumer) tileEntity).givePower(test, false);
        }
    }
}
