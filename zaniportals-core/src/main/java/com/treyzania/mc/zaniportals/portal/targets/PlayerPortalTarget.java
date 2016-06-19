package com.treyzania.mc.zaniportals.portal.targets;

import java.util.UUID;

import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.adapters.PortalLocation;
import com.treyzania.mc.zaniportals.adapters.PortalPlayer;

public class PlayerPortalTarget implements PortalTarget {
	
	public UUID player;
	
	public PlayerPortalTarget(UUID player) {
		this.player = player;
	}
	
	@Override
	public String getName() {
		return this.player.toString().substring(0, 8);
	}

	@Override
	public PortalLocation getDestination() {
		
		PortalPlayer pp = ZaniPortals.server.getPlayer(this.player);
		if (pp == null) return null;
		
		return pp.getLocation();
		
	}
	
	@Override
	public String getExpression() {
		return this.player.toString();
	}
	
}
