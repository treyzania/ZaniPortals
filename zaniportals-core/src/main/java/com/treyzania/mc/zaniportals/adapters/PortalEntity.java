package com.treyzania.mc.zaniportals.adapters;

import java.util.UUID;

public interface PortalEntity extends Wrapper {

	public UUID getUniqueId();
	public String getName();
	
	public void setLocation(PortalGameLocation loc);
	public PortalGameLocation getLocation();
	
	public boolean isPlayer();
	
}
