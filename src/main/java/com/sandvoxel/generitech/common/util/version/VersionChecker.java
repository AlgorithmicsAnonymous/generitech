package com.sandvoxel.generitech.common.util.version;

import com.sandvoxel.generitech.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by Sean on 15/05/2016.
 */
public class VersionChecker {

    private static final int FLAVOUR_MESSAGES = 65;

    public static boolean doneChecking = false;
    public static String onlineVersion = "";
    public static boolean triedToWarnPlayer = false;

    public void init() {
        new ThreadVersionChecker();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(doneChecking && event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().thePlayer != null && !triedToWarnPlayer) {
            if(!onlineVersion.isEmpty()) {
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                int onlineBuild = Integer.parseInt(onlineVersion.split("-")[1]);
                int clientBuild = Reference.VERSION_BUILD.contains("@VERSION@") ? 0 : Integer.parseInt(Reference.VERSION_BUILD.split("-")[1]);
                if(onlineBuild > clientBuild) {
                    player.addChatComponentMessage(new TextComponentTranslation("generitech.version.flavour" + player.worldObj.rand.nextInt(FLAVOUR_MESSAGES)).setChatStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
                    player.addChatComponentMessage(new TextComponentTranslation("generitech.version.outdated", clientBuild, onlineBuild));

                    ITextComponent component = ITextComponent.Serializer.jsonToComponent(I18n.translateToLocal("generitech.version.updateMesage").replaceAll("%version%", onlineVersion));
                    player.addChatComponentMessage(component);
                }
            }

            triedToWarnPlayer = true;
        }
    }

}
