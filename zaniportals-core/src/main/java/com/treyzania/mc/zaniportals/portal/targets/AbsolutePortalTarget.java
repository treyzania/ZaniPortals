package com.treyzania.mc.zaniportals.portal.targets;

import com.treyzania.mc.zaniportals.adapters.PortalLocation;

public class AbsolutePortalTarget implements PortalTarget {

	public final PortalLocation destination;
	
	public AbsolutePortalTarget(PortalLocation dest) {
		this.destination = dest;
	}
	
	@Override
	public String getName() {
		return String.format("loc:%s,%s,%s", this.destination.getBlockX(), this.destination.getBlockY(), this.destination.getBlockZ());
	}

	@Override
	public PortalLocation getDestination() {
		return this.destination;
	}

}
