package com.treyzania.mc.zaniportals.adapters;

import org.bukkit.command.CommandSender;

public class BukkitCommandSender implements PortalCommandSender {

	private final CommandSender sender;
	
	public BukkitCommandSender(CommandSender sender) {
		this.sender = sender;
	}

	@Override
	public void sendMessage(String message) {
		Thread.dumpStack();
		this.sender.sendMessage(message);
	}

	@Override
	public boolean isOp() {
		return this.sender.isOp();
	}

	@Override
	public boolean hasPermission(String permission) {
		return this.sender.hasPermission(permission);
	}

	@Override
	public Object getWrappedObject() {
		return this.sender;
	}
	
}
