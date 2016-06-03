package com.treyzania.mc.zaniportals.adapters;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BukkitPortalPlayer extends BukkitCommandSender implements PortalPlayer {
	
	private final Player player;
	
	public BukkitPortalPlayer(Player player) {
		
		super(player);
		
		this.player = player;
		
	}

	@Override
	public UUID getUniqueId() {
		return this.player.getUniqueId();
	}

	@Override
	public String getName() {
		return this.player.getName();
	}

	@Override
	public String getDisplayName() {
		return this.player.getDisplayName();
	}

	@Override
	public void setLocation(PortalGameLocation loc) {
		this.player.teleport((Location) loc.getWrappedObject()); // Don't expect to be doing anything with alien classes.
	}

	@Override
	public PortalGameLocation getLocation() {
		return new BukkitLocation(this.player.getLocation());
	}
	
	@Override
	public Object getWrappedObject() {
		return this.player;
	}
	
}
