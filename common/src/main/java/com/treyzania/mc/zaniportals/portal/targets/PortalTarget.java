package com.treyzania.mc.zaniportals.portal.targets;

import com.treyzania.mc.zaniportals.adapters.PortalEntity;
import com.treyzania.mc.zaniportals.adapters.PortalLocation;
import com.treyzania.mc.zaniportals.adapters.PortalPlayer;

public interface PortalTarget {

	public String getName();
	public PortalLocation getDestination();
	
	public default String getExpression() {
		return this.getName();
	}
	
	public default void teleport(PortalEntity ent) {
		
		PortalLocation baseLocation = this.getDestination();
		if (baseLocation == null) {
			
			if (ent instanceof PortalEntity) {
				
				PortalPlayer pp = (PortalPlayer) ent;
				pp.sendMessage("The portal this portal links to has been removed.  Please try again later.");
				
			}
			
			return;
			
		}
		
		ent.setLocation(baseLocation.getLocationAtOffset(0.5, 0, 0.5));
		ent.setYaw(this.getDestinationYaw());
		
	}
	
	public default float getDestinationYaw() {
		return 0F;
	}
	
}
