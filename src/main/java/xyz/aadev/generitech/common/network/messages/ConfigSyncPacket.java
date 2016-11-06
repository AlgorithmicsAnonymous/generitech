package xyz.aadev.generitech.common.network.messages;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import xyz.aadev.aalib.common.network.PacketBase;
import xyz.aadev.generitech.common.config.ConfigSync;

import java.util.List;

public class ConfigSyncPacket extends PacketBase {

    public List<ConfigCategory> categoryList = Lists.newLinkedList();

    public ConfigSyncPacket() {
    }

    @Override
    public IMessage handleClient(NetHandlerPlayClient netHandler) {
        ConfigSync.syncConfig(categoryList);
        return null;
    }

    @Override
    public IMessage handleServer(NetHandlerPlayServer netHandler) {
        return null;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        short catCount = buf.readShort();
        for (short i = 0; i < catCount; i++) {
            int propCount = buf.readInt();
            String catName = ByteBufUtils.readUTF8String(buf);
            ConfigCategory category = new ConfigCategory(catName);
            categoryList.add(category);
            for (int j = 0; j < propCount; j++) {
                String propName = ByteBufUtils.readUTF8String(buf);
                char propType = buf.readChar();
                String propValue = ByteBufUtils.readUTF8String(buf);
                category.put(propName, new Property(propName, propValue, Property.Type.tryParse(propType)));
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeShort(categoryList.size());
        for (ConfigCategory cat : categoryList) {
            buf.writeInt(cat.values().size());
            ByteBufUtils.writeUTF8String(buf, cat.getName());
            for (Property prop : cat.values()) {
                ByteBufUtils.writeUTF8String(buf, prop.getName());
                buf.writeChar(prop.getType().getID());
                ByteBufUtils.writeUTF8String(buf, prop.getString());
            }
        }
    }
}
