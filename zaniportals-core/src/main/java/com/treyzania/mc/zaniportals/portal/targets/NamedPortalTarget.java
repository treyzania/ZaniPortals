package com.treyzania.mc.zaniportals.portal.targets;

import com.treyzania.mc.zaniportals.adapters.PortalLocation;
import com.treyzania.mc.zaniportals.portal.Portal;

public class NamedPortalTarget implements PortalTarget {

	public final Portal portal;
	
	public NamedPortalTarget(Portal dest) {
		this.portal = dest;
	}
	
	@Override
	public String getName() {
		return portal.name;
	}

	@Override
	public PortalLocation getDestination() {
		return this.portal.world.createLocation(this.portal.getPortalLocations()[0]); // FIXME This is totally incorrect behavior.
	}

}
