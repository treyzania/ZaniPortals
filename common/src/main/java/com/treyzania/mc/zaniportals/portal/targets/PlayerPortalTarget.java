package com.treyzania.mc.zaniportals.portal.targets;

import java.util.UUID;

import com.treyzania.mc.zaniportals.ZaniPortals;
import com.treyzania.mc.zaniportals.adapters.PortalEntity;
import com.treyzania.mc.zaniportals.adapters.PortalLocation;
import com.treyzania.mc.zaniportals.adapters.PortalPlayer;

public class PlayerPortalTarget implements PortalTarget {
	
	public UUID player;
	
	public PlayerPortalTarget(UUID player) {
		this.player = player;
	}
	
	@Override
	public String getName() {
		return ZaniPortals.server.getUsername(this.player);
	}

	@Override
	public PortalLocation getDestination() {
		
		PortalPlayer pp = ZaniPortals.server.getPlayer(this.player);
		if (pp == null) return null;
		
		return pp.getLocation();
		
	}
	
	@Override
	public void teleport(PortalEntity ent) {
		
		PortalLocation dest = this.getDestination();
		
		if (dest != null) {
			PortalTarget.super.teleport(ent);
		} else {
			
			if (ent instanceof PortalPlayer) {
				
				PortalPlayer pp = (PortalPlayer) ent;
				pp.sendMessage("The player this portal links to is not online.  Please try again later.");
				
			}
			
		}
		
	}

	@Override
	public String getExpression() {
		return this.player.toString();
	}
	
}
