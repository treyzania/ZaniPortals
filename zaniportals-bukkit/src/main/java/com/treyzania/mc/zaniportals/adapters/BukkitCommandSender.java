package com.treyzania.mc.zaniportals.adapters;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BukkitCommandSender implements PortalCommandSender {

	private final CommandSender sender;
	
	public BukkitCommandSender(CommandSender sender) {
		this.sender = sender;
	}

	@Override
	public void sendMessage(String message) {
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

	@Override
	public boolean isPlayer() {
		return this.sender instanceof Player;
	}

	@Override
	public PortalPlayer getAsPlayer() {
		return new BukkitPortalPlayer((Player) this.sender);
	}
	
}
