package com.treyzania.mc.zaniportals.adapters;

import java.util.UUID;

public interface PortalEntity extends PossiblyPlayer, Wrapper {

	public UUID getUniqueId();
	public String getName();
	
	public void setLocation(PortalLocation loc);
	public PortalLocation getLocation();
	
	public default PortalWorld getWorld() {
		return this.getLocation().getWorld();
	}
	
	public boolean isPlayer();
	
	public void setPitch(float angle);
	public void setYaw(float angle);
	
}
