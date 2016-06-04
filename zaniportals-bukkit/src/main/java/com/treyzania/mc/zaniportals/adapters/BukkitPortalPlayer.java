package com.treyzania.mc.zaniportals.adapters;

import org.bukkit.entity.Player;

public class BukkitPortalPlayer extends BukkitEntity implements PortalPlayer, PortalCommandSender {
	
	private final Player player;
	
	public BukkitPortalPlayer(Player player) {
		
		super(player);
		
		this.player = player;
		
	}
	
	@Override
	public String getDisplayName() {
		return this.player.getDisplayName();
	}
	
	@Override
	public void sendMessage(String message) {
		this.player.sendMessage(message);
	}
	
	@Override
	public boolean isOp() {
		return this.player.isOp();
	}

	@Override
	public boolean hasPermission(String permission) {
		return this.player.hasPermission(permission);
	}
	
}
