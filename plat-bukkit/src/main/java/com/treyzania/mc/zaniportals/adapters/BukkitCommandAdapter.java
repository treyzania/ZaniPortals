package com.treyzania.mc.zaniportals.adapters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.treyzania.mc.zaniportals.cmd.AbstractPortalCommand;

public class BukkitCommandAdapter implements CommandExecutor {

	private final AbstractPortalCommand wrappedCommand;
	
	public BukkitCommandAdapter(AbstractPortalCommand command) {
		this.wrappedCommand = command;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return this.wrappedCommand.execute(new BukkitCommandSender(sender), args);
	}
	
}
