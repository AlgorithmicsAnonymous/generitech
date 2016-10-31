package xyz.aadev.generitech.common.network.messages.power;

import io.netty.buffer.ByteBuf;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketPower implements IMessage {

    private long test;
    private int x;
    private int z;
    private int y;


    public PacketPower() {
    }

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


    public static class ServerHandler implements IMessageHandler<PacketPower, IMessage> {


        @Override
        public IMessage onMessage(PacketPower message, MessageContext ctx) {
            TileEntity tileEntity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(new BlockPos(message.x, message.y, message.z));


            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;

            mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {


                    if (tileEntity instanceof ITeslaConsumer) {

                        ((ITeslaConsumer) tileEntity).givePower(message.test, false);


                    }


                }
            });

            return null;
        }
    }


    public static class ClientHandler implements IMessageHandler<PacketPower, IMessage> {


        @Override
        public IMessage onMessage(PacketPower message, MessageContext ctx) {

            IThreadListener mainThread = Minecraft.getMinecraft();
            mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    System.out.println(String.format("Received %s", message.test));
                }
            });

            return null;
        }
    }


}
