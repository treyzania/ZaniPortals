package com.treyzania.mc.zaniportals.adapters;

import org.bukkit.Location;

public class BukkitLocation implements PortalGameLocation {

	private final Location location;
	
	public BukkitLocation(Location loc) {
		this.location = loc;
	}
	
	@Override
	public PortalWorld getWorld() {
		return new BukkitWorld(this.location.getWorld());
	}

	@Override
	public double getX() {
		return this.location.getX();
	}

	@Override
	public double getY() {
		return this.location.getY();
	}

	@Override
	public double getZ() {
		return this.location.getZ();
	}

	@Override
	public PortalGameBlock getBlock() {
		return new BukkitBlock(this.location.getBlock());
	}

	@Override
	public Object getWrappedObject() {
		return this.location;
	}

}
