package com.treyzania.mc.zaniportals.adapters;

import com.treyzania.mc.zaniportals.portal.Portal;

public interface PortalPlayer extends PortalEntity, PortalCommandSender, Wrapper {
	
	public String getDisplayName();
	public void addItem(PortalItem item);
	
	public boolean isSneaking();
	
	public default boolean isOwner(Portal p) {
		return p.owner.equals(this.getUniqueId());
	}
	
	public PortalPlayerInventory getInventory();
	
}
