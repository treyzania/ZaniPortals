package com.treyzania.mc.zaniportals.adapters;

import java.util.UUID;

public interface PortalPlayer extends PortalCommandSender, Wrapper {

	public UUID getUniqueId();
	public String getName();
	public String getDisplayName();
	
	public void setLocation(PortalGameLocation loc);
	public PortalGameLocation getLocation();
	
}
