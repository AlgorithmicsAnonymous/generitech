package com.sandvoxel.generitech.common.command;

import com.sandvoxel.generitech.Reference;
import com.sandvoxel.generitech.common.util.LanguageHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class CommandGetBiomeId extends CommandBase {
    @Override
    public int getRequiredPermissionLevel() {
        return super.getRequiredPermissionLevel();
    }

    @Override
    public String getCommandName() {
        return Reference.Commands.GETBIOMEID;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return LanguageHelper.COMMAND.translateMessage(String.format("%s.", Reference.Commands.GETBIOMEID.toLowerCase()));
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        Entity player = sender.getCommandSenderEntity();
        World world = sender.getEntityWorld();

        Biome biome = world.getBiome(player.getPosition());

        String response = LanguageHelper.COMMAND.translateMessage(String.format("%s.success", Reference.Commands.GETBIOMEID.toLowerCase(), biome.getRegistryName()));

        response += " " + biome.getRegistryName();

        if (args.length == 2) {
            Boolean copyToClip = parseBoolean(args[1]);
            if (copyToClip) {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Clipboard clipboard = toolkit.getSystemClipboard();
                StringSelection strSel = new StringSelection(biome.getRegistryName().toString());
                clipboard.setContents(strSel, null);
            }
        }

        sender.addChatMessage(new TextComponentString(response));
    }
}
