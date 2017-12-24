package com.treyzania.mc.zaniportals.portal.targets;

import com.treyzania.mc.zaniportals.adapters.PortalEntity;
import com.treyzania.mc.zaniportals.adapters.PortalLocation;
import com.treyzania.mc.zaniportals.adapters.PortalPlayer;

public class NotifyInvalidPortalTarget implements PortalTarget {

	@Override
	public String getName() {
		return "(invalid)";
	}

	@Override
	public PortalLocation getDestination() {
		return null;
	}

	@Override
	public void teleport(PortalEntity ent) {
		
		if (ent.isPlayer()) {
			
			PortalPlayer pp = (PortalPlayer) ent;
			pp.sendMessage("This portal does not have a set destination.");
			
		}
		
	}

}
