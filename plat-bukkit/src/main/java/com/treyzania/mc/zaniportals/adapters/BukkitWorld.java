package com.treyzania.mc.zaniportals.adapters;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.World;

public class BukkitWorld implements PortalWorld {

	private final World world;
	
	public BukkitWorld(World world) {
		this.world = world;
	}
	
	@Override
	public String getName() {
		return this.world.getName();
	}

	@Override
	public File getFile() {
		return this.world.getWorldFolder();
	}
	
	@Override
	public Object getWrappedObject() {
		return this.world;
	}

	@Override
	public PortalLocation createLocation(double x, double y, double z) {
		return new BukkitLocation(new Location(this.world, x, y, z));
	}
	
}
