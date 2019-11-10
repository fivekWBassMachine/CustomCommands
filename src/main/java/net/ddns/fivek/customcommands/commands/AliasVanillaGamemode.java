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

import net.ddns.fivek.customcommands.reference.Reference;
import net.ddns.fivek.customcommands.utility.ICommand;
import net.ddns.fivek.customcommands.utility.LogHelper;
import net.ddns.fivek.customcommands.utility.Utils;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

import java.util.ArrayList;
import java.util.List;

public class AliasVanillaGamemode implements ICommand {
    public final String description = "set gamemode for yourself or a player";
    public final int permissionLevel = Reference.OP_PERMISSION_LEVEL;//permission level; 0-4;
    private final String usage = "/gamemode <mode> [player]";
    private final boolean comandEnabled = true;
    private List<String> aliases = new ArrayList<String>();

    public AliasVanillaGamemode() {
        this.aliases.add("gm");//first alias need to be the command name
        this.aliases.add("ccgamemode");
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
        LogHelper.info(sender.getCommandSenderName() + " executed " + aliases.get(0) + Utils.arrayToString(arguments));
        boolean isServer = (sender.getClass() == DedicatedServer.getServer().getClass());
        if (arguments.length == 0) {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + usage));
        } else {
            final List<String> argumentList = Utils.arrayToArryList(arguments);
            World world = sender.getEntityWorld();
            WorldSettings.GameType gameType = gameType = WorldSettings.GameType.NOT_SET;
            ArrayList<String> gameTypes = new ArrayList<String>();
            gameTypes.add("0");
            gameTypes.add("1");
            gameTypes.add("2");
            if (gameTypes.contains(argumentList.get(0))) {
                gameType = WorldSettings.GameType.getByID(Integer.parseInt(argumentList.get(0)));
            } else {
                gameTypes.set(0, "survival");
                gameTypes.set(1, "creative");
                gameTypes.set(2, "adventure");
                if (gameTypes.contains(argumentList.get(0))) {
                    gameType = WorldSettings.GameType.getByName(argumentList.get(0));
                }
            }
            if (gameType == WorldSettings.GameType.NOT_SET) {
                try {
                    Integer.parseInt(argumentList.get(0));
                    sender.addChatMessage(new ChatComponentText((isServer ? "" : EnumChatFormatting.RED) + "The number you have entered is out of range, it must be at between 0 and 2."));
                } catch (NumberFormatException e) {
                    sender.addChatMessage(new ChatComponentText((isServer ? "" : EnumChatFormatting.RED) + argumentList.get(0) + " is not a valid number."));
                }
            } else {
                if (argumentList.size() == 1) {
                    if (isServer) {
                        sender.addChatMessage(new ChatComponentText("You must specify which player you wish to perform this action on."));
                    } else {
                        this.processPlayerCommand((EntityPlayer) sender, gameType);
                    }
                } else {
                    //switch between different selectors
                    switch (argumentList.get(1).charAt(0) + "" + argumentList.get(1).charAt(1)) {
                        case "@s":
                            if (isServer) {
                                sender.addChatMessage(new ChatComponentText("You must specify which player you wish to perform this action on."));
                            } else {
                                this.processPlayerCommand((EntityPlayer) sender, gameType);
                            }
                            break;
                        case "@a":
                            for (EntityPlayer playerEntity : (ArrayList<EntityPlayer>) world.playerEntities) {
                                this.processPlayerCommand(playerEntity, gameType);
                            }
                            break;
                        case "@p":
                            ChunkCoordinates coordinates = sender.getPlayerCoordinates();
                            EntityPlayer nearestPlayer = world.getClosestPlayer(coordinates.posX, coordinates.posY, coordinates.posZ, -1);
                            this.processPlayerCommand(nearestPlayer, gameType);
                            break;
                        case "@r":
                            EntityPlayer randomPlayer = (EntityPlayer) world.playerEntities.get((int) (Math.random() * world.playerEntities.size()));
                            this.processPlayerCommand(randomPlayer, gameType);
                            break;
                        default:
                            if (isServer) {
                                sender.addChatMessage(new ChatComponentText("You must specify which player you wish to perform this action on."));
                            } else {
                                ArrayList<String> playerDisplayNames = new ArrayList<>();
                                ArrayList<String> playerSenderNames = new ArrayList<>();
                                for (EntityPlayer playerEntity : (ArrayList<EntityPlayer>) world.playerEntities) {
                                    playerDisplayNames.add(playerEntity.getDisplayName());
                                    playerSenderNames.add(playerEntity.getCommandSenderName());
                                }
                                int pos = playerDisplayNames.indexOf(argumentList.get(0));
                                if (pos == -1) pos = playerSenderNames.indexOf(argumentList.get(0));
                                if (pos > -1) {
                                    EntityPlayer player = (EntityPlayer) world.playerEntities.get(pos);
                                    this.processPlayerCommand(player, gameType);
                                } else {
                                    sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "That player cannot be found"));
                                }
                            }
                            break;
                    }
                }
            }
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return (this.comandEnabled && sender.canCommandSenderUseCommand(this.permissionLevel, this.aliases.get(0)));
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
        return (this.comandEnabled ? this.permissionLevel : -1);
    }

    private void processPlayerCommand(EntityPlayer player, WorldSettings.GameType gamemode) {
        player.setGameType(gamemode);
        //@TODO logging?
    }
}