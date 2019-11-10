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

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.ddns.fivek.customcommands.reference.Reference;
import net.ddns.fivek.customcommands.utility.LogHelper;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConfigurationHandler {

    private static String CATEGORY_COMMANDS = "commands";
    private static Configuration configuration;
    private static ConfigCategory general;
    private static List<String> commands = new ArrayList<String>();

    public static void init(String configDir, ArrayList<String> commands) {
        if (configuration == null) {
            configuration = new Configuration(new File(configDir + '/' + Reference.CONFIG_FILE));
            ConfigurationHandler.commands = commands;
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        Iterator it = commands.iterator();
        while (it.hasNext()) {
            String commandName = (String) it.next();
            LogHelper.info("Setup config for " + commandName);
            configuration.getBoolean(commandName, CATEGORY_COMMANDS, true, "Enable or disable this command.");
        }
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfiguration();
        }
    }
}
