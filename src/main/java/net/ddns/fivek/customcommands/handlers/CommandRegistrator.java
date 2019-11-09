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

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.command.ICommand;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CommandRegistrator {

    private final FMLServerStartingEvent event;
    private Map<String, ICommand> commands;

    public CommandRegistrator(FMLServerStartingEvent event) {
        this.event = event;
    }

    public void registrateCommands(ICommand command) {
        if (command == null) return;
        if (command.getCommandName() == null) return;
        //this.commands.put(command.getCommandName(), command);
        this.event.registerServerCommand(command);
    }
}