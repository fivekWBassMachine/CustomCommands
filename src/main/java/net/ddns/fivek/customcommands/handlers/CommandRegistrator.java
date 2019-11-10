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

package net.ddns.fivek.customcommands.handlers;

import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.ddns.fivek.customcommands.commands.CustomCommandsHelp;
import net.ddns.fivek.customcommands.reference.Reference;
import net.ddns.fivek.customcommands.utility.ICommand;
import net.ddns.fivek.customcommands.utility.LogHelper;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;

public class CommandRegistrator {
    public static List<String> help = new ArrayList<String>();
    private static Map<String, ICommand> commands = new HashMap<String, ICommand>();
    private final FMLServerStartingEvent event;

    public CommandRegistrator(FMLServerStartingEvent event) {
        this.event = event;
        this.registrateCommands(new CustomCommandsHelp());
    }

    public static Map<String, ICommand> getCommands() {
        return commands;
    }

    public void registrateCommands(ICommand command) {
        commands.put(command.getCommandName(), command);
        this.event.registerServerCommand(command);
        LogHelper.info("Registered command " + command.getCommandName() + ".");
    }

    public void buildHelpText() {
        help.add(EnumChatFormatting.DARK_GREEN + "--- Showing Custom Commands help ---");
        List<String> permissionValues = new ArrayList<String>();
        permissionValues.add("DISABLED]");
        permissionValues.add("EVERYONE");
        permissionValues.add("OPERATORS");
        int permissionValuesMaxLength = permissionValues.stream().map(String::length).max(Integer::compareTo).get();
        Iterator it = commands.entrySet().iterator();
        while (it.hasNext()) {
            ICommand command = (ICommand) ((Map.Entry) it.next()).getValue();
            int permissions = command.getPermissions();
            String permission = (permissions == -1 ? permissionValues.get(0) : (permissions == 0 ? permissionValues.get(1) : (permissions == Reference.OP_PERMISSION_LEVEL ? permissionValues.get(2) : String.valueOf(permissions))));
            String indent = String.join("", Collections.nCopies(permissionValuesMaxLength - permission.length() + 1, " "));
            String line = EnumChatFormatting.GREEN + "[" + permission + "]" + EnumChatFormatting.RESET + indent + command.getCommandUsage(null);
            help.add(line);
        }
        LogHelper.info("Mod-specific help built.");
    }
}