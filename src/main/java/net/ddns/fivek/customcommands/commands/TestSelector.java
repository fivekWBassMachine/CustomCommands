package net.ddns.fivek.customcommands.commands;

import net.ddns.fivek.customcommands.utility.LogHelper;
import net.ddns.fivek.customcommands.utility.Utils;
import net.minecraft.command.ICommand;
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
    private final List<String> aliases;
    private String usage;
    private String comment;
    private boolean requireOp;
    private boolean enableCommand = true;

    public TestSelector() {
        aliases = new ArrayList<String>();
        aliases.add("testselector");//first alias need to be the command name
        aliases.add("ccts");
        usage = "/testselector <player> <command>";
        comment = "for debugging; returns the player for the selector";
        requireOp = false;
    }

    @Override
    public String getCommandName() {
        return aliases.get(0);
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return usage;
    }

    @Override
    public List getCommandAliases() {
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] arguments) {
        boolean isServer = (sender.getClass() == DedicatedServer.getServer().getClass());
        if (arguments.length == 0) {
            if (isServer) {
                LogHelper.warn("TestSelector: processCommand: Usage: " + usage);
            } else {
                sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + usage));
            }
        } else {
            LogHelper.info("TestSelector: processCommand: " + sender.getCommandSenderName() + " executed " + aliases.get(0) + " with " + Utils.arrayToString(arguments));
            final List<String> argumentList = Utils.arrayToArryList(arguments);

            World world = sender.getEntityWorld();

            //switch between different selectors
            switch (argumentList.get(0).charAt(0) + "" + argumentList.get(0).charAt(1)) {
                case "@s":
                    if (isServer) {
                        LogHelper.warn("TestSelector: processCommand: You cannot execute @s as Server!");
                    } else {
                        ///*DEBUG*/sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "@s: " + sender.getCommandSenderName()));
                        this.processPlayerCommand(sender);
                    }
                    break;
                case "@a":
                    for (EntityPlayer playerEntity : (ArrayList<EntityPlayer>) world.playerEntities) {
                        ///*DEBUG*/sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "@a: " + playerEntity));
                        this.processPlayerCommand(sender, playerEntity);
                    }
                    break;
                case "@p":
                    ChunkCoordinates coordinates = sender.getPlayerCoordinates();
                    EntityPlayer nearestPlayer = world.getClosestPlayer(coordinates.posX, coordinates.posY, coordinates.posZ, -1);
                    ///*DEBUG*/sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "@p: " + nearestPlayer.getDisplayName()));
                    this.processPlayerCommand(sender, nearestPlayer);
                    break;
                case "@r":
                    EntityPlayer randomPlayer = (EntityPlayer) world.playerEntities.get((int) (Math.random() * world.playerEntities.size()));
                    ///*DEBUG*/sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "@r: " + randomPlayer.getDisplayName()));
                    this.processPlayerCommand(sender, randomPlayer);
                    break;
                case "@e":
                    for (Entity loadedEntityList : (ArrayList<Entity>) world.loadedEntityList) {
                        ///*DEBUG*/sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "@e: " + loadedEntityList));
                        this.processEntityCommand(sender, loadedEntityList);
                    }
                    break;
                default:
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
                        ///*DEBUG*/sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + argumentList.get(0)));
                        this.processPlayerCommand(sender, player);
                    } else if (isServer) {
                        LogHelper.warn("TestSelector: processCommand: Usage: " + usage);
                    } else {
                        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "That player cannot be found"));
                    }

                    break;
            }
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        return (enableCommand && (!requireOp || false));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
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

    private void processEntityCommand(ICommandSender sender, Entity loadedEntityList) {

    }

    private void processPlayerCommand(ICommandSender sender) {

    }

    private void processPlayerCommand(ICommandSender sender, EntityPlayer player) {

    }
}