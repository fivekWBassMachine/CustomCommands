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

package net.ddns.fivek.customcommands.reference;

import net.minecraft.server.MinecraftServer;

public class Reference {
    public static final String MOD_ID = "5kCustomCommands";
    public static final String MOD_NAME = "Custom Commands";
    public static final String MOD_VERSION = "0.0.1";
    public static final String MOD_MC_VERSION = "1.7.10";
    public static final boolean MOD_CAN_BE_DEACTIVATED = true;

    public static final String CONFIG_FILE = "CustomCommands.cfg";
    public static final String PROXY_CLIENT = "net.ddns.fivek.customcommands.proxy.ClientProxy";
    public static final String PROXY_SERVER = "net.ddns.fivek.customcommands.proxy.ServerProxy";
    public static final int OP_PERMISSION_LEVEL = MinecraftServer.getServer().getOpPermissionLevel();
}
