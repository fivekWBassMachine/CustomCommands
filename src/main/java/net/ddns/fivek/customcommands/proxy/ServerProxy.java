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
import net.ddns.fivek.customcommands.commands.TestSelector;
import net.ddns.fivek.customcommands.handlers.CommandRegistrator;
import net.ddns.fivek.customcommands.handlers.ConfigurationHandler;

import java.util.ArrayList;

public class ServerProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        String configDir = event.getModConfigurationDirectory().toString();
        ConfigurationHandler.init(configDir);
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

        ArrayList<String> aliases = new ArrayList<String>();
        CommandRegistrator commandRegistrator = new CommandRegistrator(event);
        commandRegistrator.registrateCommands(new TestSelector());
        commandRegistrator.registrateCommands(new AliasVanillaGamemode());
    }
}