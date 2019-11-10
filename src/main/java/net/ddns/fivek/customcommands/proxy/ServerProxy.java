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

package net.ddns.fivek.customcommands.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.ddns.fivek.customcommands.commands.AliasVanillaGamemode;
import net.ddns.fivek.customcommands.handlers.CommandRegistrator;
import net.ddns.fivek.customcommands.handlers.ConfigurationHandler;
import net.ddns.fivek.customcommands.utility.ICommand;

import java.util.ArrayList;
import java.util.Iterator;

public class ServerProxy extends CommonProxy {
    private ArrayList<String> commandNames = new ArrayList<String>();
    private ArrayList<ICommand> commands = new ArrayList<ICommand>();

    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);


        this.commandNames = new ArrayList<String>();

        //commands here: classname:command
        this.commandNames.add("AliasVanillaGamemode:ccgamemode");

        String configDir = event.getModConfigurationDirectory().toString();
        ConfigurationHandler.init(configDir, this.commandNames);
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    public void serverLoad(FMLServerStartingEvent event) {
        super.serverLoad(event);

        //commands here
        this.commands.add(new AliasVanillaGamemode());

        CommandRegistrator commandRegistrator = new CommandRegistrator(event);
        Iterator it = this.commands.iterator();
        while (it.hasNext()) {
            commandRegistrator.registrateCommands((ICommand) it.next());
        }

        commandRegistrator.buildHelpText();
    }
}