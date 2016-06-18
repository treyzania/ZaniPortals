package com.treyzania.mc.zaniportals.portal.targets;

import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.adapters.PortalLocation;
import com.treyzania.mc.zaniportals.portal.Portal;

public class NamedPortalTarget implements PortalTarget {

	public final String name;
	
	public NamedPortalTarget(String portalName) {
		this.name = portalName;
	}
	
	public NamedPortalTarget(Portal dest) {
		this(dest.name);
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public PortalLocation getDestination() {
		
		Portal p = ZaniPortals.portals.getPortal(this.name);
		if (p == null) return null;
		
		return p.getLandingLocation(); // FIXME This is totally incorrect behavior.
		
	}

}
