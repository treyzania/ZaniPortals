package com.treyzania.mc.zaniportals.adapters;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class SpongeLocation implements PortalLocation {

	private Location<World> loc;
	
	public SpongeLocation(Location<World> loc) {
		this.loc = loc;
	}
	
	@Override
	public Object getWrappedObject() {
		return this.loc;
	}

	@Override
	public PortalWorld getWorld() {
		return new SpongeWorld(this.loc.getExtent());
	}

	@Override
	public double getX() {
		return this.loc.getX();
	}

	@Override
	public double getY() {
		return this.loc.getY();
	}

	@Override
	public double getZ() {
		return this.loc.getZ();
	}

	@Override
	public PortalBlock getBlock() {
		// TODO Auto-generated method stub
		return null;
	}

}
