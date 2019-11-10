/*
 * Copyright 2019 5kWBassMachine <5kWBassMachine@gmail.com> <fivek.ddns.net>
 *
 * This file is part of CustomCommands.
 *
 * CustomCommands is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CustomCommands is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CustomCommands.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.ddns.fivek.customcommands.commands;

import net.ddns.fivek.customcommands.handlers.CommandRegistrator;
import net.ddns.fivek.customcommands.utility.ICommand;
import net.ddns.fivek.customcommands.utility.LogHelper;
import net.ddns.fivek.customcommands.utility.Utils;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomCommandsHelp implements ICommand {
    private String description = "displays help texts";
    private int permissionLevel = 0;//permission level; 0-4;
    private String usage = "/cchelp [command]";
    private boolean commandEnabled = true;
    private List<String> aliases = new ArrayList<String>();

    public CustomCommandsHelp() {
        this.aliases.add("cchelp");//first alias need to be the command name
    }

    @Override
    public String getCommandName() {
        return this.aliases.get(0);
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return this.usage;
    }

    @Override
    public List getCommandAliases() {
        return this.aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] arguments) {
        LogHelper.info(sender.getCommandSenderName() + " executed " + aliases.get(0) + " " + Utils.arrayToString(arguments));
        boolean isServer = (sender.getClass() == DedicatedServer.getServer().getClass());
        if (arguments.length == 0) {
            Iterator it = CommandRegistrator.help.iterator();
            while (it.hasNext()) {
                sender.addChatMessage(new ChatComponentText((isServer ? ((String) it.next()).replaceAll("(§[0-9a-fk-or])", "") : (String) it.next())));
            }
        } else {
            if (CommandRegistrator.getCommands().keySet().contains(arguments[0])) {
                ICommand command = CommandRegistrator.getCommands().get(arguments[0]);
                sender.addChatMessage(new ChatComponentText((isServer ? "" : EnumChatFormatting.DARK_RED) + command.getCommandUsage(null)));
                sender.addChatMessage(new ChatComponentText((isServer ? "" : EnumChatFormatting.RED) + command.getDescription()));
                sender.addChatMessage(new ChatComponentText((isServer ? "" : EnumChatFormatting.RED) + "Aliases: " + Utils.arrayToString(command.getCommandAliases())));
            } else {
                sender.addChatMessage(new ChatComponentText((isServer ? "" : EnumChatFormatting.RED) + "Unknown command. Try /" + this.aliases.get(0) + " for a list of commands"));
            }
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return (this.commandEnabled && sender.canCommandSenderUseCommand(this.permissionLevel, this.aliases.get(0)));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] p_71516_2_) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public int getPermissions() {
        return (this.commandEnabled ? this.permissionLevel : -1);
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}