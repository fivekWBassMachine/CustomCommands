/*
 * Copyright 2019 5kWBassMachine aka fivek <5kwbassmachine@gmail.com>
 *
 * This file is part of MinecraftModTemplate.
 *
 * MinecraftModTemplate is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MinecraftModTemplate is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MinecraftCommand.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.ddns.fivek.customcommands.handlers;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.ddns.fivek.customcommands.reference.Reference;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler {

    public static final String COMMANDS = "commands";
    private static Configuration configuration;
    private static ConfigCategory general;

    public static void init(String configDir) {
        if (configuration == null) {
            configuration = new Configuration(new File(configDir + '/' + Reference.CONFIG_FILE));
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        if (configuration.hasChanged()) {
            general = configuration.getCategory(COMMANDS);
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfiguration();
        }
    }

    public static Configuration getConfiguration() { return configuration; }
}
