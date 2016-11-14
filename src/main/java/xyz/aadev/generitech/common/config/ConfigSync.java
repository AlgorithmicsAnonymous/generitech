package xyz.aadev.generitech.common.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.aadev.aalib.common.logging.Logger;
import xyz.aadev.generitech.GeneriTech;
import xyz.aadev.generitech.common.network.Network;
import xyz.aadev.generitech.common.network.messages.ConfigSyncPacket;
import xyz.aadev.generitech.common.util.LanguageHelper;

import java.util.List;
import java.util.Map;

public class ConfigSync {

    @SideOnly(Side.CLIENT)
    private static boolean needsRestart;

    public static void syncConfig(List<ConfigCategory> categoryList) {
        needsRestart = false;
        boolean changed = false;
        GeneriTech.Logger.info("Synchronizing configs with the server.");

        for (ConfigCategory serverCat : categoryList) {
            ConfigCategory localCat = Config.config.getCategory(serverCat.getName());

            for (Map.Entry<String, Property> entry : serverCat.entrySet()) {
                String propName = entry.getKey();
                Property serverProp = entry.getValue();

                Property localProp = localCat.get(propName);
                if (localProp == null) {
                    localCat.put(propName, serverProp);
                } else {
                    if (!localProp.getString().equals(serverProp.getString())) {
                        localProp.setValue(serverProp.getString());
                        needsRestart |= localProp.requiresMcRestart();
                        changed = true;
                        GeneriTech.Logger.debug(String.format("Syncing %s - %s: %s", localCat.getName(), localProp.getName(), localProp.getString()));
                    }
                }
            }
        }

        if (Config.config.hasChanged()) {
            Config.config.save();
        }

        if (changed) {
            MinecraftForge.EVENT_BUS.register(new ConfigSync());
        }
    }

    @SubscribeEvent
    @SideOnly(Side.SERVER)
    public void onPLayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player == null || !(event.player instanceof EntityPlayerMP) || FMLCommonHandler.instance().getSide().isClient())
            return;

        ConfigSyncPacket syncPacket = new ConfigSyncPacket();
        syncPacket.categoryList.add(Config.General);
        syncPacket.categoryList.add(ConfigWorldGen.Worldgen);

        Network.sendTo(syncPacket, (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onPlayerJoinedWorld(TickEvent.ClientTickEvent event) {
        EntityPlayerSP entityPlayer = Minecraft.getMinecraft().thePlayer;
        if (needsRestart) {
            entityPlayer.addChatMessage(new TextComponentString("[GeneriTech] " + LanguageHelper.MESSAGE.translateMessage("configsync.restart")));
        } else {
            entityPlayer.addChatMessage(new TextComponentString("[GeneriTech] " + LanguageHelper.MESSAGE.translateMessage("configsync.ok")));
        }

        MinecraftForge.EVENT_BUS.unregister(this);
    }

}
