package com.treyzania.mc.zaniportals.adapters;

public interface PortalPlayer extends PortalEntity, PortalCommandSender, Wrapper {
	
	public String getDisplayName();
	public void addItem(PortalItem item);
	
}
