package com.treyzania.mc.zaniportals.portal.targets;

import com.treyzania.mc.zaniportals.adapters.PortalEntity;
import com.treyzania.mc.zaniportals.adapters.PortalLocation;

public interface PortalTarget {

	public String getName();
	public PortalLocation getDestination();
	
	public default void teleport(PortalEntity ent) {
		ent.setLocation(this.getDestination());
	}
	
}
