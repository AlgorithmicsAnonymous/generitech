package com.sandvoxel.generitech.common.command;

import com.google.common.base.Joiner;
import com.sandvoxel.generitech.Reference;
import com.sandvoxel.generitech.common.util.LanguageHelper;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sean on 11/07/16.
 */
public class CommandGeneritech extends CommandBase {
    private static List<CommandBase> modCommands = new ArrayList<>();
    private static List<String> commands = new ArrayList<>();

    @Override
    public String getCommandName() {
        return Reference.Commands.BASE_COMMAND;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return LanguageHelper.COMMAND.translateMessage("usage");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        boolean found = false;

        if(args.length >= 1) {
            for(CommandBase command : modCommands) {
                if(command.getCommandName().equalsIgnoreCase(args[0]) && checkPermission(server, sender)) {
                    found = true;
                    command.execute(server, sender, args);
                }
            }
        }

        if(!found) {
            throw new WrongUsageException(String.format("Invalid command. Usage: /gtech %s", Joiner.on(" ").join(commands)));
        }
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        if(args.length == 1) {
            return getListOfStringsMatchingLastWord(args, commands);
        } else if (args.length >= 2) {
            for(CommandBase command : modCommands) {
                if(command.getCommandName().equalsIgnoreCase(args[0])) {
                    return command.getTabCompletionOptions(server, sender, args, pos);
                }
            }
        }

        return null;
    }

    static {
        modCommands.add(new CommandGetBiomeId());

        commands.addAll(modCommands.stream().map(ICommand::getCommandName).collect(Collectors.toList()));
    }
}

