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