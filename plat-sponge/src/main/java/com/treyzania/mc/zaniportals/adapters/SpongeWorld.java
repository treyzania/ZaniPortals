package com.treyzania.mc.zaniportals.adapters;

import java.io.File;

import org.spongepowered.api.world.World;

public class SpongeWorld implements PortalWorld {

	private World world;
	
	public SpongeWorld(World w) {
		this.world = w;
	}
	
	@Override
	public Object getWrappedObject() {
		return this.world;
	}

	@Override
	public String getName() {
		return this.world.getName();
	}

	@Override
	public File getFile() {
		return this.world.getDirectory().toFile();
	}

	@Override
	public PortalLocation createLocation(double x, double y, double z) {
		// TODO Auto-generated method stub
		return null;
	}

}
