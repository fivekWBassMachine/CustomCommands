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

import net.ddns.fivek.customcommands.handlers.ConfigurationHandler;
import net.ddns.fivek.customcommands.reference.Reference;
import net.ddns.fivek.customcommands.utility.ICommand;
import net.ddns.fivek.customcommands.utility.LogHelper;
import net.ddns.fivek.customcommands.utility.Utils;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TestSelector implements ICommand {
    private String description = "returns all matches for this selector";
    private int permissionLevel = Reference.OP_PERMISSION_LEVEL;//permission level; 0-4;
    private String usage = "/cctestselector <selector|player|entity>";
    private List<String> aliases = new ArrayList<String>();
    private boolean commandEnabled = ConfigurationHandler.getConfig().get(this.getClass().getSimpleName() + ":" + this.aliases.get(0));

    public TestSelector() {
        this.aliases.add("cctestselector");//first alias need to be the command name
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
        boolean senderIsServer = (sender.getClass() == DedicatedServer.getServer().getClass());
        if (arguments.length == 0) {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + usage));
        } else {
            final List<String> argumentList = Utils.arrayToArryList(arguments);
            World world = sender.getEntityWorld();
            //switch between different selectors
            switch (argumentList.get(0).charAt(0) + "" + argumentList.get(0).charAt(1)) {
                case "@s":
                    if (senderIsServer) {
                        sender.addChatMessage(new ChatComponentText("@s: " + sender.getCommandSenderName()));
                    } else {
                        /*DEBUG*/sender.addChatMessage(new ChatComponentText((senderIsServer ? "" : EnumChatFormatting.YELLOW) + "@s: " + sender.getCommandSenderName()));
                        this.processPlayerCommand(sender);
                    }
                    break;
                case "@a":
                    for (EntityPlayer playerEntity : (ArrayList<EntityPlayer>) world.playerEntities) {
                        /*DEBUG*/sender.addChatMessage(new ChatComponentText((senderIsServer ? "" : EnumChatFormatting.YELLOW) + "@a: " + playerEntity));
                        this.processPlayerCommand(sender, playerEntity);
                    }
                    break;
                case "@p":
                    ChunkCoordinates coordinates = sender.getPlayerCoordinates();
                    EntityPlayer nearestPlayer = world.getClosestPlayer(coordinates.posX, coordinates.posY, coordinates.posZ, -1);
                    /*DEBUG*/sender.addChatMessage(new ChatComponentText((senderIsServer ? "" : EnumChatFormatting.YELLOW) + "@p: " + nearestPlayer.getDisplayName()));
                    this.processPlayerCommand(sender, nearestPlayer);
                    break;
                case "@r":
                    EntityPlayer randomPlayer = (EntityPlayer) world.playerEntities.get((int) (Math.random() * world.playerEntities.size()));
                    /*DEBUG*/sender.addChatMessage(new ChatComponentText((senderIsServer ? "" : EnumChatFormatting.YELLOW) + "@r: " + randomPlayer.getDisplayName()));
                    this.processPlayerCommand(sender, randomPlayer);
                    break;
                case "@e":
                    for (Entity loadedEntityList : (ArrayList<Entity>) world.loadedEntityList) {
                        /*DEBUG*/sender.addChatMessage(new ChatComponentText((senderIsServer ? "" : EnumChatFormatting.YELLOW) + "@e: " + loadedEntityList));
                        this.processEntityCommand(sender, loadedEntityList);
                    }
                    break;
                default:
                    ArrayList<String> playerDisplayNames = new ArrayList<String>();
                    ArrayList<String> playerSenderNames = new ArrayList<String>();
                    for (EntityPlayer playerEntity : (ArrayList<EntityPlayer>) world.playerEntities) {
                        playerDisplayNames.add(playerEntity.getDisplayName());
                        playerSenderNames.add(playerEntity.getCommandSenderName());
                    }
                    int pos = playerDisplayNames.indexOf(argumentList.get(0));
                    if (pos == -1) pos = playerSenderNames.indexOf(argumentList.get(0));
                    if (pos > -1) {
                        EntityPlayer player = (EntityPlayer) world.playerEntities.get(pos);
                        /*DEBUG*/sender.addChatMessage(new ChatComponentText((senderIsServer ? "" : EnumChatFormatting.YELLOW) + argumentList.get(0)));
                        this.processPlayerCommand(sender, player);
                    } else {
                        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "That player cannot be found"));
                    }

                    break;
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

    private void processEntityCommand(ICommandSender sender, Entity loadedEntityList) {

    }

    private void processPlayerCommand(ICommandSender sender) {

    }

    private void processPlayerCommand(ICommandSender sender, EntityPlayer player) {

    }
}