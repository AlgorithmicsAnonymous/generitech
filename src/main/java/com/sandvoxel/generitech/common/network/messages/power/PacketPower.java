package com.sandvoxel.generitech.common.network.messages.power;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import scala.collection.parallel.ParIterableLike;
import scala.tools.nsc.doc.model.Public;

public class PacketPower implements IMessage {

    private long test;

    public PacketPower(){}

    public PacketPower(long test){
        this.test = test;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        test = buf.readLong();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(test);
    }

        public static class ServerHandler implements IMessageHandler<PacketPower,IMessage> {


            @Override
            public IMessage onMessage(PacketPower message, MessageContext ctx) {

                IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
                mainThread.addScheduledTask(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(String.format("Received %s from %s", message.test, ctx.getServerHandler().playerEntity.getDisplayName()));
                    }
                });

                return null;
            }
        }


            public static class ClientHandler implements IMessageHandler<PacketPower,IMessage>{


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
